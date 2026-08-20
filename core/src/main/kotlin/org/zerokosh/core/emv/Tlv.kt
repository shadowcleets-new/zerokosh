/**
 * @file Tlv.kt
 * @description BER-TLV reader for EMV responses. Pure Kotlin so it can be unit
 *              tested off-device — this parses data coming off a payment card,
 *              which is not somewhere to find out about an off-by-one on a phone.
 */
package org.zerokosh.core.emv

// #region Model
/** One BER-TLV element. [children] is non-empty only for constructed tags. */
data class TlvNode(
    val tag: Int,
    val value: ByteArray,
    val children: List<TlvNode>,
) {
    // ByteArray gives identity equals, which makes tests lie. Compare contents.
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TlvNode) return false
        return tag == other.tag && value.contentEquals(other.value) && children == other.children
    }

    override fun hashCode(): Int =
        (tag * 31 + value.contentHashCode()) * 31 + children.hashCode()
}
// #endregion

// #region Parser
object Tlv {

    /**
     * Parses a sequence of TLV elements. Returns what it managed to read rather
     * than throwing: cards do emit padding and the occasional field this reader
     * has no interest in, and one odd trailing byte should not cost us the PAN.
     */
    fun parse(bytes: ByteArray): List<TlvNode> = parse(bytes, 0, bytes.size)

    private fun parse(bytes: ByteArray, from: Int, until: Int): List<TlvNode> {
        val out = mutableListOf<TlvNode>()
        var i = from
        while (i < until) {
            // 0x00 and 0xFF are padding between elements, not tags.
            if (bytes[i] == 0x00.toByte() || bytes[i] == 0xFF.toByte()) {
                i++
                continue
            }
            val tagStart = i
            var tag = bytes[i].toInt() and 0xFF
            val constructed = (tag and 0x20) != 0
            i++
            if ((tag and 0x1F) == 0x1F) {
                // Multi-byte tag: continue while the high bit is set.
                do {
                    if (i >= until) return out
                    tag = (tag shl 8) or (bytes[i].toInt() and 0xFF)
                    i++
                } while ((bytes[i - 1].toInt() and 0x80) != 0)
            }
            if (i >= until) return out

            var length = bytes[i].toInt() and 0xFF
            i++
            if (length > 0x7F) {
                val lengthBytes = length and 0x7F
                if (lengthBytes == 0 || lengthBytes > 4 || i + lengthBytes > until) return out
                length = 0
                repeat(lengthBytes) {
                    length = (length shl 8) or (bytes[i].toInt() and 0xFF)
                    i++
                }
            }
            if (length < 0 || i + length > until) return out

            val value = bytes.copyOfRange(i, i + length)
            i += length
            out += TlvNode(
                tag = tag,
                value = value,
                children = if (constructed) parse(value, 0, value.size) else emptyList(),
            )
            if (i <= tagStart) return out // defensive: never loop on a malformed element
        }
        return out
    }

    /** Depth-first search for the first element carrying [tag]. */
    fun find(nodes: List<TlvNode>, tag: Int): TlvNode? {
        for (node in nodes) {
            if (node.tag == tag) return node
            find(node.children, tag)?.let { return it }
        }
        return null
    }

    /** Every element carrying [tag], depth first. */
    fun findAll(nodes: List<TlvNode>, tag: Int): List<TlvNode> {
        val out = mutableListOf<TlvNode>()
        fun walk(list: List<TlvNode>) {
            for (n in list) {
                if (n.tag == tag) out += n
                walk(n.children)
            }
        }
        walk(nodes)
        return out
    }

    fun ByteArray.toHex(): String = joinToString("") { "%02X".format(it) }
}
// #endregion
