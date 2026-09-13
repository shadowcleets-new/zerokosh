package org.zerokosh.app.ui.common

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.zerokosh.app.ui.theme.VaultTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * BV-08: getIdentifier is a slow reflective lookup and this sits on the scroll
 * path — every row of the vault list, the authenticator list and the template
 * grid called it on each composition. Resolved names are cached process-wide;
 * drawable ids are stable for the life of the process.
 */
private val logoResIds = java.util.concurrent.ConcurrentHashMap<String, Int>()

/**
 * Picker entries whose display name does not normalise onto the drawable that
 * ships for that brand. Kept explicit rather than fuzzy — a wrong logo on a
 * user's bank is worse than a monogram.
 */
private val logoAliases = mapOf(
    "niyoglobal" to "niyo",
    "unicard" to "unicards",
    "fampay" to "famapp",
    // Domains whose name is not the brand's: shorteners, old names, and the
    // hosts a sign-in actually lands on.
    "twitter" to "x",
    "youtu" to "youtube",
    "fb" to "facebook",
    "messenger" to "facebook",
    "wa" to "whatsapp",
    "goo" to "google",
    "googleusercontent" to "google",
    "gstatic" to "google",
    "protonmail" to "proton",
    "githubusercontent" to "github",
    "stackexchange" to "stackoverflow",
    "redditmedia" to "reddit",
    "chatgpt" to "openai",
)

@SuppressLint("DiscouragedApi")
@Composable
fun CompanyLogo(name: String, modifier: Modifier = Modifier, size: Dp = 44.dp) {
    val context = LocalContext.current
    // An imported record's name is usually a URL, so the brand has to be found
    // before anything can be looked up under it — see BrandName.kt.
    val brand = remember(name) { brandOf(name) }

    val resId = remember(brand.logoKey) {
        if (brand.logoKey.isEmpty()) 0
        else logoResIds.getOrPut(brand.logoKey) {
            val key = logoAliases[brand.logoKey] ?: brand.logoKey
            context.resources.getIdentifier("logo_$key", "drawable", context.packageName)
        }
    }
    val shape = RoundedCornerShape(16.dp)

    if (resId != 0) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = "$name logo",
            modifier = modifier.size(size)
        )
    } else {
        // Monogram fallback for institutions with no bundled vector.
        // The tints were hard-coded light-theme tokens, so in dark mode this drew
        // dark initials on a dark tile and the mark was effectively invisible.
        // They come from the theme now and flip with it.
        val c = VaultTheme.colors
        // Initials of the brand, not the first four characters of whatever the
        // record happens to be called: that is what printed HTTP on every
        // imported login and ACCO on eight different Google accounts.
        val shortName = brand.monogram
        val tints = listOf(c.ink(0.75f), c.primary, c.accent)
        val tint = tints[kotlin.math.abs(brand.logoKey.ifEmpty { name }.hashCode()) % tints.size]

        Box(
            modifier = modifier.size(size),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = shortName,
                color = tint,
                // 12sp was sized for four cramped characters; two initials can
                // have the room the tile always had.
                fontSize = (size.value * 0.36f).sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                letterSpacing = (-0.5).sp
            )
        }
    }
}
