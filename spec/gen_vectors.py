# pip install pynacl
import nacl.pwhash, nacl.bindings, binascii
hx = lambda b: binascii.hexlify(b).decode()
key = nacl.pwhash.argon2id.kdf(32, b"correct-horse-battery-staple",
        bytes(range(16)), opslimit=3, memlimit=67108864)
assert hx(key) == "9acedbbff8ce68cae902e239bdccd32578ccc20eb9054e679f9b8cf5a9f0e19a"
ct = nacl.bindings.crypto_aead_xchacha20poly1305_ietf_encrypt(
        b"namaste india", b"BVLT", bytes(range(24)), bytes(range(32)))
assert hx(ct) == "f0a3621ee3a6e88e5a2a42a7aa69c591ec4cf4b20dae58d36bcf83fdc8"
wrapped = nacl.bindings.crypto_aead_xchacha20poly1305_ietf_encrypt(
        b"\xaa"*32, b"BVLT-KEYWRAP", b"\x24"*24, key)
assert hx(wrapped) == ("f68ef02b2231e71a1bf6088d69ecbbc5af74cc70df18e6bc11fb1752"
                       "16a9c21a522d9ddd9b237a446d680e306fd1426b")
print("all vectors OK — extend this file to add fixture vaults")
