package org.zerokosh.core.crypto

/**
 * RecoveryKey encoding (Appendix C): 16 random bytes → Crockford Base32
 * (26 chars, no padding) + 1 checksum char (Crockford digit of sum of the 16
 * raw bytes mod 32), grouped as KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX.
 */
object RecoveryKey {

    private const val ALPHABET = "0123456789ABCDEFGHJKMNPQRSTVWXYZ" // Crockford: no I, L, O, U
    const val RAW_BYTES = 16

    fun encode(bytes: ByteArray): String {
        require(bytes.size == RAW_BYTES) { "RecoveryKey must be 16 bytes" }
        val sb = StringBuilder(27)
        var buffer = 0
        var bits = 0
        for (b in bytes) {
            buffer = (buffer shl 8) or (b.toInt() and 0xFF)
            bits += 8
            while (bits >= 5) {
                bits -= 5
                sb.append(ALPHABET[(buffer shr bits) and 31])
            }
        }
        if (bits > 0) sb.append(ALPHABET[(buffer shl (5 - bits)) and 31]) // last 3 bits, 2 zero-padded
        sb.append(ALPHABET[checksum(bytes)])
        return "KSH-" + sb.chunked(5).joinToString("-")
    }

    /** Case-insensitive; strips KSH-/hyphens/spaces; maps I→1, L→1, O→0. Null when malformed or checksum fails. */
    fun decode(input: String): ByteArray? {
        var s = input.trim().uppercase()
            .replace("-", "").replace(" ", "")
            .replace('I', '1').replace('L', '1').replace('O', '0')
        if (s.startsWith("KSH")) s = s.substring(3)
        if (s.length != 27) return null

        val out = ByteArray(RAW_BYTES)
        var buffer = 0
        var bits = 0
        var idx = 0
        for (c in s.substring(0, 26)) {
            val v = ALPHABET.indexOf(c)
            if (v < 0) return null
            buffer = (buffer shl 5) or v
            bits += 5
            if (bits >= 8) {
                bits -= 8
                out[idx++] = ((buffer shr bits) and 0xFF).toByte()
            }
        }
        if ((buffer and ((1 shl bits) - 1)) != 0) return null // non-canonical trailing bits
        val check = ALPHABET.indexOf(s[26])
        if (check != checksum(out)) return null
        return out
    }

    private fun checksum(bytes: ByteArray): Int = bytes.sumOf { it.toInt() and 0xFF } % 32
}
