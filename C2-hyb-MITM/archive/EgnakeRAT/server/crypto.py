import os
import hashlib
import base64
from Crypto.Cipher import AES
from Crypto.Util.Padding import pad, unpad

DEFAULT_PASSPHRASE = "EgnakeRAT_v2_SecureKey_2026"


class CryptoManager:
    def __init__(self, passphrase: str = None):
        self.passphrase = passphrase or DEFAULT_PASSPHRASE
        self.key = self._derive_key(self.passphrase)

    @staticmethod
    def _derive_key(passphrase: str) -> bytes:
        return hashlib.sha256(passphrase.encode("utf-8")).digest()

    @staticmethod
    def generate_iv() -> bytes:
        return os.urandom(16)

    def encrypt(self, plaintext: str) -> str:
        iv = self.generate_iv()
        cipher = AES.new(self.key, AES.MODE_CBC, iv)
        padded = pad(plaintext.encode("utf-8"), AES.block_size)
        ciphertext = cipher.encrypt(padded)
        return base64.b64encode(iv + ciphertext).decode("utf-8")

    def decrypt(self, encrypted_b64: str) -> str:
        raw = base64.b64decode(encrypted_b64)
        iv = raw[:16]
        ciphertext = raw[16:]
        cipher = AES.new(self.key, AES.MODE_CBC, iv)
        decrypted = unpad(cipher.decrypt(ciphertext), AES.block_size)
        return decrypted.decode("utf-8")

    def encrypt_bytes(self, data: bytes) -> bytes:
        iv = self.generate_iv()
        cipher = AES.new(self.key, AES.MODE_CBC, iv)
        padded = pad(data, AES.block_size)
        return iv + cipher.encrypt(padded)

    def decrypt_bytes(self, data: bytes) -> bytes:
        iv = data[:16]
        ciphertext = data[16:]
        cipher = AES.new(self.key, AES.MODE_CBC, iv)
        return unpad(cipher.decrypt(ciphertext), AES.block_size)

    @staticmethod
    def get_key_hash(passphrase: str) -> str:
        key = hashlib.sha256(passphrase.encode("utf-8")).digest()
        return hashlib.md5(key).hexdigest()
