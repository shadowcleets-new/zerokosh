/**
 * @file Components.kt
 * @description Shared UI atoms: template icons (§10.2), institution monograms,
 *              masking per §2.3, sensitivity helpers.
 *
 * [TABLE OF CONTENTS]
 * 1. TEMPLATE ICONS (§10.2)
 * 2. INSTITUTION MONOGRAM
 * 3. MASKING (§2.3 / §10.4)
 * 4. SMALL WIDGETS
 */
package org.bharatvault.app.ui.common

// #region Imports
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.CurrencyRupee
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.ShowChart
import androidx.compose.material.icons.outlined.SimCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.bharatvault.core.model.Sensitivity
import org.bharatvault.core.model.TemplateField
import kotlin.math.absoluteValue
// #endregion

// #region Template icons (§10.2)
val TemplateIcons: Map<String, ImageVector> = mapOf(
    "bank" to Icons.Outlined.AccountBalance,
    "card" to Icons.Outlined.CreditCard,
    "upi" to Icons.Outlined.CurrencyRupee,
    "chart" to Icons.Outlined.ShowChart,
    "shield" to Icons.Outlined.Shield,
    "id" to Icons.Outlined.Badge,
    "savings" to Icons.Outlined.Savings,
    "bolt" to Icons.Outlined.Bolt,
    "sim" to Icons.Outlined.SimCard,
    "cart" to Icons.Outlined.ShoppingCart,
    "key" to Icons.Outlined.Key,
    "note" to Icons.Outlined.Description,
)

@Composable
fun TemplateIcon(iconKey: String, modifier: Modifier = Modifier, tint: Color = MaterialTheme.colorScheme.primary) {
    Icon(TemplateIcons[iconKey] ?: Icons.Outlined.Key, contentDescription = null, modifier = modifier, tint = tint)
}
// #endregion

// #region Institution monogram (§10.2: colored circle from name hash)
private val MonogramPalette = listOf(
    Color(0xFF1A6B54), Color(0xFF8E4585), Color(0xFF2F5D9E), Color(0xFFB55A30),
    Color(0xFF6A5ACD), Color(0xFF00796B), Color(0xFFC2185B), Color(0xFF5D4037),
)

@Composable
fun InstitutionMonogram(name: String, modifier: Modifier = Modifier) {
    val color = MonogramPalette[name.hashCode().absoluteValue % MonogramPalette.size]
    Box(
        modifier = modifier.size(40.dp).background(color, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = name.trim().take(1).uppercase().ifEmpty { "?" },
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}
// #endregion

// #region Masking (§2.3 / §10.4)
/** Masked rendering: M shows last 4 by default, H fully masked. */
fun maskedValue(value: String, field: TemplateField?, revealed: Boolean): String {
    if (revealed) return value
    val sensitivity = field?.sensitivity ?: Sensitivity.M
    return when {
        sensitivity == Sensitivity.L -> value
        field?.m == "last4" && value.length > 4 -> "••••" + value.takeLast(4)
        else -> "•".repeat(value.length.coerceIn(6, 12))
    }
}

fun isMaskable(field: TemplateField?): Boolean =
    (field?.sensitivity ?: Sensitivity.L) != Sensitivity.L

/** Group a card number in 4s for display. */
fun groupCardNumber(digits: String): String = digits.chunked(4).joinToString(" ")
// #endregion

// #region Small widgets
@Composable
fun FavoriteStar(favorite: Boolean, modifier: Modifier = Modifier) {
    if (favorite) {
        Icon(
            Icons.Filled.Star,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = modifier.size(16.dp),
        )
    }
}
// #endregion
