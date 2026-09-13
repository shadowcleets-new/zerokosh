/**
 * @file BrandName.kt
 * @description The brand behind a record's name, for the tile that marks it.
 *
 * Records typed in this app carry an institution — "HDFC Bank", "Zomato" — and
 * the tile looks up logo_<that>. Records imported from another password manager
 * carry no institution at all, only a title, and that title is usually a URL:
 * "accounts.google.com", "https://candidate.accenture.com/8c7f…". Normalising
 * those whole produced "accountsgooglecom", which matches no logo, and a
 * monogram of the first four characters, which is how a vault of imported
 * logins ends up a column of ACCO, ACCE and HTTP — the last one naming the
 * protocol.
 *
 * So: find the brand first, then look up the logo and draw the monogram from
 * that. Guesses stay conservative, because a wrong logo on someone's bank is
 * worse than a monogram.
 */
package org.zerokosh.app.ui.common

// #region Public suffixes
/**
 * Enough of the list to cover where this app is used. Only two-label suffixes
 * belong here: one-label ones (.com, .in) fall out of the last-dot rule.
 */
private val TWO_LABEL_SUFFIXES = setOf(
    "co.in", "net.in", "org.in", "gen.in", "firm.in", "ind.in", "ac.in", "edu.in", "res.in",
    "gov.in", "nic.in", "mil.in",
    "co.uk", "org.uk", "me.uk", "ac.uk", "gov.uk",
    "com.au", "net.au", "org.au", "edu.au", "gov.au",
    "co.jp", "ne.jp", "or.jp", "ac.jp", "go.jp",
    "com.br", "com.sg", "com.my", "com.hk", "com.cn", "com.tr", "com.mx", "com.ar",
    "co.za", "co.nz", "co.kr", "co.id", "co.th", "com.pk", "com.bd", "com.np", "lk.com",
)
// #endregion

// #region Host
/** Everything a URL wraps around a host: scheme, credentials, port, path. */
private fun hostOf(raw: String): String? {
    var s = raw.trim()
    if (s.isEmpty() || s.contains(' ')) return null
    s = s.substringAfter("://", s)
    s = s.substringBefore('/').substringBefore('?').substringBefore('#')
    s = s.substringAfterLast('@') // user:pass@host
    s = s.substringBefore(':') // port
    if (!s.contains('.') || s.endsWith(".")) return null
    val labels = s.split('.')
    if (labels.any { it.isEmpty() }) return null
    return s.lowercase()
}

private fun isIpAddress(host: String): Boolean {
    val parts = host.split('.')
    return parts.size == 4 && parts.all { p -> p.isNotEmpty() && p.all(Char::isDigit) }
}

/**
 * The label that names the brand: the one in front of the public suffix.
 * "accounts.google.com" and "google.co.in" both give "google".
 */
private fun registrableName(host: String): String? {
    val labels = host.split('.')
    if (labels.size < 2) return null
    val lastTwo = labels.takeLast(2).joinToString(".")
    val index = if (lastTwo in TWO_LABEL_SUFFIXES) labels.size - 3 else labels.size - 2
    return labels.getOrNull(index)?.takeIf { it.isNotBlank() }
}
// #endregion

// #region Brand
/**
 * What to draw for [name]: the key a bundled logo would be filed under, and the
 * short mark to fall back on.
 *
 * @param logoKey normalised for `logo_<key>` lookup; empty when there is
 *   nothing to look up, such as a bare IP address.
 * @param monogram one or two characters — initials for a multi-word brand,
 *   otherwise the opening of the single word.
 */
data class Brand(val logoKey: String, val monogram: String)

fun brandOf(name: String): Brand {
    val trimmed = name.trim()
    if (trimmed.isEmpty()) return Brand("", "?")

    val host = hostOf(trimmed)
    if (host != null) {
        // An address names no brand, and "20" says more than the four
        // characters of a scheme ever did.
        if (isIpAddress(host)) return Brand("", host.take(2))
        val registrable = registrableName(host)
        if (registrable != null) return Brand(normalise(registrable), monogramOf(registrable))
    }
    return Brand(normalise(trimmed), monogramOf(trimmed))
}

/** The drawable-name normalisation this app has always used. */
private fun normalise(s: String): String = s.lowercase().replace(Regex("[^a-z0-9_]"), "")

private fun monogramOf(brand: String): String {
    val words = brand.split(' ', '-', '_', '.')
        .filter { it.isNotBlank() && it.first().isLetterOrDigit() }
    if (words.isEmpty()) return brand.take(2).uppercase()
    if (words.size >= 2) return (words[0].first().toString() + words[1].first()).uppercase()
    return words[0].take(2).uppercase()
}
// #endregion
