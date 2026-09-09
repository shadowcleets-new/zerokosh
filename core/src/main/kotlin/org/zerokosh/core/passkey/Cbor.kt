/**
 * @file Cbor.kt
 * @description Just enough CBOR to write what WebAuthn asks for.
 *
 * The attestation object and the COSE public key inside it are CBOR, and there
 * is no way around producing it byte-exactly: a relying party hashes and parses
 * these, so a structure that merely decodes to the right values but encodes
 * differently will fail verification.
 *
 * Writing only, and only the five major types those structures use. A general
 * CBOR library would be a dependency and a much larger surface for something
 * that emits maps of byte strings.
 *
 * Canonical ordering is the caller's job: [map] writes pairs in the order given,
 * because both structures here have a fixed, specified order.
 */
package org.zerokosh.core.passkey

import java.io.ByteArrayOutputStream

internal object Cbor {

    private const val MAJOR_UNSIGNED = 0
    private const val MAJOR_NEGATIVE = 1
    private const val MAJOR_BYTES = 2
    private const val MAJOR_TEXT = 3
    private const val MAJOR_MAP = 5

    /** A CBOR map of the given pairs, in the order given. */
    fun map(vararg pairs: Pair<Any, Any>): ByteArray {
        val out = ByteArrayOutputStream()
        out.write(header(MAJOR_MAP, pairs.size.toLong()))
        for ((k, v) in pairs) {
            out.write(value(k))
            out.write(value(v))
        }
        return out.toByteArray()
    }

    fun bytes(value: ByteArray): ByteArray =
        header(MAJOR_BYTES, value.size.toLong()) + value

    fun text(value: String): ByteArray {
        val utf8 = value.toByteArray(Charsets.UTF_8)
        return header(MAJOR_TEXT, utf8.size.toLong()) + utf8
    }

    fun int(value: Long): ByteArray = if (value >= 0) {
        header(MAJOR_UNSIGNED, value)
    } else {
        // CBOR stores a negative as -1 minus the encoded value, so -7 is
        // written as 6 under major type 1.
        header(MAJOR_NEGATIVE, -1 - value)
    }

    private fun value(v: Any): ByteArray = when (v) {
        is ByteArray -> bytes(v)
        is String -> text(v)
        is Int -> int(v.toLong())
        is Long -> int(v)
        is PreEncoded -> v.bytes
        else -> error("unsupported CBOR value: ${v::class.simpleName}")
    }

    /** Something already in CBOR form, spliced in as-is. */
    class PreEncoded(val bytes: ByteArray)

    /**
     * The initial byte and any following length bytes.
     *
     * Shortest form throughout, which is what canonical CBOR requires and what
     * every WebAuthn verifier expects.
     */
    private fun header(major: Int, n: Long): ByteArray {
        val m = major shl 5
        return when {
            n < 24 -> byteArrayOf((m or n.toInt()).toByte())
            n < 0x100 -> byteArrayOf((m or 24).toByte(), n.toByte())
            n < 0x10000 -> byteArrayOf((m or 25).toByte(), (n shr 8).toByte(), n.toByte())
            n < 0x1_0000_0000L -> byteArrayOf(
                (m or 26).toByte(),
                (n shr 24).toByte(), (n shr 16).toByte(), (n shr 8).toByte(), n.toByte(),
            )
            else -> error("length too large for this writer: $n")
        }
    }
}
