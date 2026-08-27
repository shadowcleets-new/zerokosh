/**
 * @file CardIins.kt
 * @description Offline IIN → issuer / kind / variant lookup.
 *
 *              Bundled at build time, never fetched. The app declares no
 *              INTERNET permission and "0 servers contacted" is a verifiable
 *              manifest fact, so a runtime BIN API is not an option — the data
 *              ships in the APK and is refreshed per release by
 *              design/gen_card_iins.py.
 *
 *              Every field is independently optional. A BIN that cannot name a
 *              bank very often still names debit vs credit, which is the case
 *              that matters: the chip's Application Label carries the kind on
 *              some issuers and not others.
 *
 * [TABLE OF CONTENTS]
 * 1. RESULT MODEL
 * 2. TABLE + PARSER
 */
package org.zerokosh.core.card

// #region Result model
/**
 * What the bundled table knows about one IIN. Null means "not known", never
 * "none" — a wrong bank on a user's card is worse than an empty field.
 */
data class IinInfo(
    val bank: String? = null,
    /** Debit / Credit / Prepaid, matching the card_kinds picker. */
    val kind: String? = null,
    /** Classic / Platinum / Signature …, for the card_variant field. */
    val variant: String? = null,
) {
    val isEmpty: Boolean get() = bank == null && kind == null && variant == null
}
// #endregion

// #region Table + parser
class CardIins private constructor(
    private val banks: List<String>,
    private val variants: List<String>,
    private val rows: Map<String, IntArray>,
) {
    val size: Int get() = rows.size

    /**
     * Looks up by the card number's first six digits. Accepts a full PAN or a
     * bare IIN, and tolerates the spaces the card-number field renders with.
     */
    fun lookup(panOrIin: String): IinInfo? {
        val digits = panOrIin.filter(Char::isDigit)
        if (digits.length < 6) return null
        val row = rows[digits.substring(0, 6)] ?: return null
        return IinInfo(
            bank = row[0].takeIf { it >= 0 }?.let(banks::getOrNull),
            kind = when (row[1]) {
                KIND_DEBIT -> "Debit"
                KIND_CREDIT -> "Credit"
                KIND_PREPAID -> "Prepaid"
                else -> null
            },
            variant = row[2].takeIf { it >= 0 }?.let(variants::getOrNull),
        )
    }

    companion object {
        private const val KIND_DEBIT = 'D'.code
        private const val KIND_CREDIT = 'C'.code
        private const val KIND_PREPAID = 'P'.code

        /**
         * Parses the bundled asset. Malformed lines are skipped rather than
         * thrown on: a damaged table should cost the prefill, not the app.
         */
        /**
         * `<6-digit bin> <bank index> <kind> <variant index>`, or null for any
         * line that is not one. Pulled out of [parse] so the row format lives in
         * one place and the reader stays a reader.
         */
        private fun parseRow(line: String): Pair<String, IntArray>? {
            val parts = line.split(' ')
            if (parts.size != 4) return null
            val bin = parts[0]
            if (bin.length != 6) return null
            val bank = parts[1].toIntOrNull() ?: return null
            val kind = parts[2].firstOrNull()?.code ?: return null
            val variant = parts[3].toIntOrNull() ?: return null
            return bin to intArrayOf(bank, kind, variant)
        }

        fun parse(text: String): CardIins {
            var banks = emptyList<String>()
            var variants = emptyList<String>()
            val rows = HashMap<String, IntArray>(16384)

            for (line in text.lineSequence()) {
                when {
                    line.isEmpty() || line == "v1" -> Unit
                    line.startsWith("B\t") -> banks = line.substring(2).split('\t')
                    line.startsWith("V\t") -> variants = line.substring(2).split('\t')
                    else -> parseRow(line)?.let { (bin, row) -> rows[bin] = row }
                }
            }
            return CardIins(banks, variants, rows)
        }

        val EMPTY = CardIins(emptyList(), emptyList(), emptyMap())
    }
}
// #endregion
