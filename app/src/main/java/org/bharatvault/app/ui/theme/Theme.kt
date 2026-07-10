package org.bharatvault.app.ui.theme

// #region Imports
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// #endregion

// #region §10.1 tokens
val Primary = Color(0xFF1A6B54) // deep green — trust/finance
val OnPrimary = Color(0xFFFFFFFF)
val Accent = Color(0xFFE8A317) // marigold
val ErrorRed = Color(0xFFB3261E)
val SurfaceLight = Color(0xFFFAFAF7)
val SurfaceDark = Color(0xFF0F1412)
val Success = Color(0xFF2E7D32)

val CornerRadius = 12.dp
// #endregion

private val LightColors = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    secondary = Accent,
    onSecondary = Color(0xFF231A00),
    tertiary = Success,
    error = ErrorRed,
    background = SurfaceLight,
    surface = SurfaceLight,
    surfaceVariant = Color(0xFFEDEFE8),
    primaryContainer = Color(0xFFA8F2D8),
    onPrimaryContainer = Color(0xFF002117),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF8CD5B9),
    onPrimary = Color(0xFF003828),
    secondary = Accent,
    tertiary = Color(0xFF97D695),
    error = Color(0xFFF2B8B5),
    background = SurfaceDark,
    surface = SurfaceDark,
    surfaceVariant = Color(0xFF1C2420),
    primaryContainer = Color(0xFF00513C),
    onPrimaryContainer = Color(0xFFA8F2D8),
)

// §10.1 type scale: display 28/semibold, title 20/semibold, body 16/regular, caption 13.
private val BharatTypography = Typography(
    displaySmall = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.SemiBold, lineHeight = 36.sp),
    titleLarge = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold, lineHeight = 28.sp),
    titleMedium = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold, lineHeight = 24.sp),
    bodyLarge = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal, lineHeight = 24.sp),
    bodyMedium = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal, lineHeight = 20.sp),
    labelSmall = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Medium, lineHeight = 16.sp),
    labelMedium = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Medium, lineHeight = 16.sp),
)

/** §10.1: monospace for all SECRET/PIN/CARDNUM/TOTP values. */
val SecretTextStyle = TextStyle(
    fontFamily = FontFamily.Monospace,
    fontSize = 16.sp,
    lineHeight = 24.sp,
)

private val BharatShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(CornerRadius),
    medium = RoundedCornerShape(CornerRadius),
    large = RoundedCornerShape(16.dp),
)

@Composable
fun BharatVaultTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // dark mode mandatory, follows system (§10.1)
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = BharatTypography,
        shapes = BharatShapes,
        content = content,
    )
}
