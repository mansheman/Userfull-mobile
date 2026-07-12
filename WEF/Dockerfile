FROM kalilinux/kali-rolling:latest

# Update packages and install all requirements
RUN apt-get update && apt-get install -y git gcc iproute2 iw macchanger aircrack-ng mdk4 sed gawk xterm jq pciutils usbutils ethtool bsdmainutils curl procps john hashcat hcxtools hcxdumptool reaver pixiewps hostapd hostapd-wpe dnsmasq lighttpd bettercap python3

# Set working directory
WORKDIR /app

# Clone WEF repository
RUN git clone https://github.com/D3Ext/WEF

# Change working directory to repository path
WORKDIR /app/WEF

# Execute WEF
RUN bash wef


