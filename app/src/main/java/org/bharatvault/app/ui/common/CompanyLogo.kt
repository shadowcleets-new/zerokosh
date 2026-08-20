package org.bharatvault.app.ui.common

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
import org.bharatvault.app.ui.theme.VaultTheme
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

@SuppressLint("DiscouragedApi")
@Composable
fun CompanyLogo(name: String, modifier: Modifier = Modifier, size: Dp = 44.dp) {
    val context = LocalContext.current
    val normalizedName = name.lowercase().replace(Regex("[^a-z0-9_]"), "")

    val resId = remember(normalizedName) {
        logoResIds.getOrPut(normalizedName) {
            context.resources.getIdentifier("logo_$normalizedName", "drawable", context.packageName)
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
        val shortName = name.take(4).uppercase()
        val tints = listOf(c.ink(0.75f), c.primary, c.accent)
        val tint = tints[kotlin.math.abs(name.hashCode()) % tints.size]

        Box(
            modifier = modifier.size(size),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = shortName,
                color = tint,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                letterSpacing = (-0.5).sp
            )
        }
    }
}
