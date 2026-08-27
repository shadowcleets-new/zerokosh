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

    /** A field read off the wire, and where the cursor now sits. */
    private class Read(val value: Int, val next: Int)

    /**
     * BER tag. One byte, unless the low five bits are all set, in which case it
     * continues while the high bit of each following byte is set.
     */
    private fun readTag(bytes: ByteArray, from: Int, until: Int): Read? {
        var tag = bytes[from].toInt() and 0xFF
        var i = from + 1
        if ((tag and 0x1F) != 0x1F) return Read(tag, i)
        do {
            if (i >= until) return null
            tag = (tag shl 8) or (bytes[i].toInt() and 0xFF)
            i++
        } while ((bytes[i - 1].toInt() and 0x80) != 0)
        return Read(tag, i)
    }

    /**
     * BER length. Short form is the byte itself; long form is a count of
     * following bytes, capped at four because nothing this app reads is larger
     * and an unbounded shift is how a parser becomes an allocation bug.
     */
    private fun readLength(bytes: ByteArray, from: Int, until: Int): Read? {
        val first = bytes[from].toInt() and 0xFF
        if (first <= 0x7F) return Read(first, from + 1)
        val count = first and 0x7F
        if (count == 0 || count > 4 || from + 1 + count > until) return null
        var length = 0
        var i = from + 1
        repeat(count) {
            length = (length shl 8) or (bytes[i].toInt() and 0xFF)
            i++
        }
        return Read(length, i)
    }

    /** 0x00 and 0xFF are padding between elements, not tags. */
    private fun isPadding(b: Byte): Boolean = b == 0x00.toByte() || b == 0xFF.toByte()

    private fun parse(bytes: ByteArray, from: Int, until: Int): List<TlvNode> {
        val out = mutableListOf<TlvNode>()
        var i = from
        while (i < until) {
            if (isPadding(bytes[i])) {
                i++
                continue
            }
            val tagStart = i
            val constructed = (bytes[i].toInt() and 0x20) != 0
            val tag = readTag(bytes, i, until) ?: return out
            i = tag.next
            if (i >= until) return out

            val length = readLength(bytes, i, until) ?: return out
            i = length.next
            if (length.value < 0 || i + length.value > until) return out

            val value = bytes.copyOfRange(i, i + length.value)
            i += length.value
            out += TlvNode(
                tag = tag.value,
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
