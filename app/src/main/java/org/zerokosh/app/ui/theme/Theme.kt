/**
 * @file Theme.kt
 * @description Design tokens transcribed 1:1 from the Zerokosh Lovable mockup
 *              pack ("Organic Editorial — warm + serif"). Colours are the exact
 *              sRGB resolution of the mockup's OKLCH values; type is the real
 *              Newsreader / Instrument Sans / JetBrains Mono family.
 *
 * [TABLE OF CONTENTS]
 * 1. IMPORTS & DEPENDENCIES
 * 2. COLOUR TOKENS (light + dark)
 * 3. EXTENDED PALETTE (VaultColors + CompositionLocal)
 * 4. FONT FAMILIES (variable axes)
 * 5. TYPE SCALE
 * 6. SHAPES & MOTION
 * 7. THEME ENTRY POINT
 */
@file:OptIn(androidx.compose.ui.text.ExperimentalTextApi::class) // Font(variationSettings=)

package org.zerokosh.app.ui.theme

// #region Imports
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.zerokosh.app.R
// #endregion

// #region Colour tokens
// Light — from :root in the mockup stylesheet.
val VaultPaper = Color(0xFFFDFAF6)      // oklch(.985 .006 75)  off-white paper
val VaultSurface = Color(0xFFF6F3EF)    // oklch(.965 .006 75)  slight tone
val VaultInk = Color(0xFF180F0D)        // oklch(.18  .015 40)  near-black warm
val VaultMute = Color(0xFF787069)       // oklch(.55  .015 60)  muted zinc-warm
val VaultLine = Color(0xFFE1DDDA)       // oklch(.90  .006 70)  hairline
val VaultPrimary = Color(0xFFBB4717)    // oklch(.55  .16  40)  burnt orange
val VaultAccent = Color(0xFF018D87)     // oklch(.58  .10  190) teal
val VaultDock = Color(0xFF120C09)       // oklch(.16  .012 40)  charcoal dock
val VaultCard = Color(0xFFFFFFFF)       // pure white card face
val VaultErrorLight = Color(0xFFD40C1A) // oklch(.55  .22  27)
val VaultPrimarySoft = Color(0x14BB4717) // --vault-primary-soft: primary / 8%
val VaultAccentSoft = Color(0x1A018D87)  // --vault-accent-soft:  accent  / 10%

// Dark — from .dark in the mockup stylesheet.
val VaultPaperDark = Color(0xFF120C09)
val VaultSurfaceDark = Color(0xFF241D1B)
val VaultInkDark = Color(0xFFF4F1ED)
val VaultMuteDark = Color(0xFFA9A39E)
val VaultCardDark = Color(0xFF1B1412)
val VaultPrimaryDark = Color(0xFFF0834E)
val VaultAccentDark = Color(0xFF1DBCB5)
val VaultErrorDark = Color(0xFFFF6367)
// #endregion

// #region Extended palette
/**
 * Tokens the mockup leans on that M3's ColorScheme has no slot for. Held in a
 * CompositionLocal so a single `VaultTheme.colors` read flips with dark mode.
 */
@Immutable
data class VaultColors(
    val paper: Color,
    val surface: Color,
    val card: Color,
    val ink: Color,
    val mute: Color,
    val line: Color,
    val primary: Color,
    val primarySoft: Color,
    val accent: Color,
    val accentSoft: Color,
    val dock: Color,
    val onDock: Color,
) {
    /** `text-vault-ink/45` and friends — the mockup's opacity ramp on ink. */
    fun ink(alpha: Float): Color = ink.copy(alpha = alpha)
}

private val LightVaultColors = VaultColors(
    paper = VaultPaper,
    surface = VaultSurface,
    card = VaultCard,
    ink = VaultInk,
    mute = VaultMute,
    line = VaultLine,
    primary = VaultPrimary,
    primarySoft = VaultPrimary.copy(alpha = 0.08f),
    accent = VaultAccent,
    accentSoft = VaultAccent.copy(alpha = 0.10f),
    dock = VaultDock,
    onDock = VaultPaper,
)

private val DarkVaultColors = VaultColors(
    paper = VaultPaperDark,
    surface = VaultSurfaceDark,
    card = VaultCardDark,
    ink = VaultInkDark,
    mute = VaultMuteDark,
    line = Color(0x1AFFFFFF),
    primary = VaultPrimaryDark,
    primarySoft = VaultPrimaryDark.copy(alpha = 0.12f),
    accent = VaultAccentDark,
    accentSoft = VaultAccentDark.copy(alpha = 0.14f),
    dock = Color(0xFF0C0806),
    onDock = VaultInkDark,
)

val LocalVaultColors = staticCompositionLocalOf { LightVaultColors }

object VaultTheme {
    val colors: VaultColors
        @Composable @ReadOnlyComposable get() = LocalVaultColors.current
}
// #endregion

// #region Font families
// All three faces ship as variable TTFs; each weight is a named instance on the
// wght axis rather than a separate file.
private fun wght(weight: Int) = FontVariation.Settings(FontVariation.weight(weight))
private fun wghtWdth(weight: Int) =
    FontVariation.Settings(FontVariation.weight(weight), FontVariation.width(100f))

/** Newsreader — headlines, display type, the italic emphasis runs. */
val Newsreader = FontFamily(
    Font(R.font.newsreader, FontWeight.Light, variationSettings = wght(300)),
    Font(R.font.newsreader, FontWeight.Normal, variationSettings = wght(400)),
    Font(R.font.newsreader, FontWeight.Medium, variationSettings = wght(500)),
    Font(R.font.newsreader, FontWeight.SemiBold, variationSettings = wght(600)),
    Font(R.font.newsreader, FontWeight.Bold, variationSettings = wght(700)),
    Font(R.font.newsreader_italic, FontWeight.Normal, FontStyle.Italic, variationSettings = wght(400)),
    Font(R.font.newsreader_italic, FontWeight.Medium, FontStyle.Italic, variationSettings = wght(500)),
    Font(R.font.newsreader_italic, FontWeight.SemiBold, FontStyle.Italic, variationSettings = wght(600)),
)

/** Instrument Sans — body copy, labels, buttons. */
val InstrumentSans = FontFamily(
    Font(R.font.instrument_sans, FontWeight.Normal, variationSettings = wghtWdth(400)),
    Font(R.font.instrument_sans, FontWeight.Medium, variationSettings = wghtWdth(500)),
    Font(R.font.instrument_sans, FontWeight.SemiBold, variationSettings = wghtWdth(600)),
    Font(R.font.instrument_sans, FontWeight.Bold, variationSettings = wghtWdth(700)),
)

/** JetBrains Mono — every number, key, code and metadata line. */
val JetBrainsMono = FontFamily(
    Font(R.font.jetbrains_mono, FontWeight.Normal, variationSettings = wght(400)),
    Font(R.font.jetbrains_mono, FontWeight.Medium, variationSettings = wght(500)),
    Font(R.font.jetbrains_mono, FontWeight.SemiBold, variationSettings = wght(600)),
    Font(R.font.jetbrains_mono, FontWeight.Bold, variationSettings = wght(700)),
)
// #endregion

// #region Type scale
// Sizes mirror the mockup's px values 1:1 (the mockups are drawn at 340dp wide,
// which is the design width of a Pixel in dp — so px maps straight to sp).
private val BharatTypography = Typography(
    // Welcome hero — font-serif text-[52px] leading-[0.92] tracking-[-0.02em]
    displayLarge = TextStyle(
        fontFamily = Newsreader, fontWeight = FontWeight.Normal,
        fontSize = 52.sp, lineHeight = 48.sp, letterSpacing = (-1.04).sp,
    ),
    // Onboarding headlines — text-[34px] leading-[1.05]
    displayMedium = TextStyle(
        fontFamily = Newsreader, fontWeight = FontWeight.Normal,
        fontSize = 34.sp, lineHeight = 36.sp, letterSpacing = (-0.68).sp,
    ),
    // Home / Authenticator headlines — text-[32px] leading-[1.05]
    displaySmall = TextStyle(
        fontFamily = Newsreader, fontWeight = FontWeight.Normal,
        fontSize = 32.sp, lineHeight = 34.sp, letterSpacing = (-0.64).sp,
    ),
    // Sheet title — text-[28px] leading-[1.06]
    headlineMedium = TextStyle(
        fontFamily = Newsreader, fontWeight = FontWeight.Normal,
        fontSize = 28.sp, lineHeight = 30.sp, letterSpacing = (-0.56).sp,
    ),
    // Card titles — font-serif text-2xl
    headlineSmall = TextStyle(
        fontFamily = Newsreader, fontWeight = FontWeight.Normal,
        fontSize = 24.sp, lineHeight = 28.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = Newsreader, fontWeight = FontWeight.Normal,
        fontSize = 20.sp, lineHeight = 24.sp,
    ),
    // List row title — text-[14.5px] font-medium
    titleMedium = TextStyle(
        fontFamily = InstrumentSans, fontWeight = FontWeight.Medium,
        fontSize = 14.5.sp, lineHeight = 18.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = InstrumentSans, fontWeight = FontWeight.Medium,
        fontSize = 13.5.sp, lineHeight = 17.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = InstrumentSans, fontWeight = FontWeight.Normal,
        fontSize = 15.sp, lineHeight = 22.sp,
    ),
    // The workhorse — text-[13px] leading-relaxed
    bodyMedium = TextStyle(
        fontFamily = InstrumentSans, fontWeight = FontWeight.Normal,
        fontSize = 13.sp, lineHeight = 19.sp,
    ),
    // Sub-labels — text-[11.5px] leading-snug
    bodySmall = TextStyle(
        fontFamily = InstrumentSans, fontWeight = FontWeight.Normal,
        fontSize = 11.5.sp, lineHeight = 15.sp,
    ),
    // Pill buttons — text-[15px] font-semibold tracking-tight
    labelLarge = TextStyle(
        fontFamily = InstrumentSans, fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp, lineHeight = 20.sp, letterSpacing = (-0.15).sp,
    ),
    // Chips — text-[12.5px] font-medium
    labelMedium = TextStyle(
        fontFamily = InstrumentSans, fontWeight = FontWeight.Medium,
        fontSize = 12.5.sp, lineHeight = 16.sp,
    ),
    // Kickers — text-[10px] font-mono uppercase tracking-[0.24em]
    labelSmall = TextStyle(
        fontFamily = JetBrainsMono, fontWeight = FontWeight.Medium,
        fontSize = 10.sp, lineHeight = 14.sp, letterSpacing = 2.4.sp,
    ),
)

/** Metadata under a record title — text-[11.5px] font-mono. */
val MetaTextStyle = TextStyle(
    fontFamily = JetBrainsMono, fontWeight = FontWeight.Normal,
    fontSize = 11.5.sp, lineHeight = 15.sp,
)

/** Revealed secrets and account numbers — text-[15px] font-mono. */
val SecretTextStyle = TextStyle(
    fontFamily = JetBrainsMono, fontWeight = FontWeight.Normal,
    fontSize = 15.sp, lineHeight = 22.sp, letterSpacing = 0.3.sp,
)

/** Field captions — text-[10px] uppercase tracking-widest font-semibold. */
val FieldLabelStyle = TextStyle(
    fontFamily = InstrumentSans, fontWeight = FontWeight.SemiBold,
    fontSize = 10.sp, lineHeight = 13.sp, letterSpacing = 1.2.sp,
)

/** Section headers — text-[11px] font-semibold uppercase tracking-[0.18em]. */
val SectionLabelStyle = TextStyle(
    fontFamily = InstrumentSans, fontWeight = FontWeight.SemiBold,
    fontSize = 11.sp, lineHeight = 14.sp, letterSpacing = 1.98.sp,
)

/** The rotating TOTP digits — text-[26px] font-mono tracking-[0.16em]. */
val TotpTextStyle = TextStyle(
    fontFamily = JetBrainsMono, fontWeight = FontWeight.Medium,
    fontSize = 26.sp, lineHeight = 30.sp, letterSpacing = 4.16.sp,
)
// #endregion

// #region Shapes & motion
private val BharatShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),   // rounded-2xl
    large = RoundedCornerShape(24.dp),    // rounded-3xl
    extraLarge = RoundedCornerShape(26.dp), // the mockup's grouped list card
)

/** `--radius` roles lifted from the mockup token set. */
val CornerCard = 24.dp
val CornerGroup = 26.dp
val CornerTile = 16.dp
val CornerHero = 28.dp

/** `cubic-bezier(0.32, 0.72, 0, 1)` — the "ios-spring" easing used throughout. */
val IosSpring = CubicBezierEasing(0.32f, 0.72f, 0f, 1f)
const val DurationFast = 180
const val DurationBase = 300
const val DurationSlow = 600
// #endregion

// #region Theme entry point
private val LightColors = lightColorScheme(
    primary = VaultPrimary,
    onPrimary = VaultPaper,
    primaryContainer = VaultPrimary.copy(alpha = 0.10f),
    onPrimaryContainer = VaultPrimary,
    secondary = VaultAccent,
    onSecondary = VaultPaper,
    secondaryContainer = VaultAccent.copy(alpha = 0.12f),
    onSecondaryContainer = VaultAccent,
    tertiary = VaultInk,
    onTertiary = VaultPaper,
    background = VaultPaper,
    onBackground = VaultInk,
    surface = VaultSurface,
    onSurface = VaultInk,
    surfaceContainerLowest = VaultCard,
    surfaceContainer = VaultSurface,
    surfaceVariant = VaultSurface,
    onSurfaceVariant = VaultMute,
    outline = VaultLine,
    outlineVariant = VaultLine,
    error = VaultErrorLight,
    onError = VaultPaper,
    inverseSurface = VaultInk,
    inverseOnSurface = VaultPaper,
)

private val DarkColors = darkColorScheme(
    primary = VaultPrimaryDark,
    onPrimary = VaultPaperDark,
    primaryContainer = VaultPrimaryDark.copy(alpha = 0.16f),
    onPrimaryContainer = VaultPrimaryDark,
    secondary = VaultAccentDark,
    onSecondary = VaultPaperDark,
    secondaryContainer = VaultAccentDark.copy(alpha = 0.16f),
    onSecondaryContainer = VaultAccentDark,
    tertiary = VaultInkDark,
    onTertiary = VaultPaperDark,
    background = VaultPaperDark,
    onBackground = VaultInkDark,
    surface = VaultSurfaceDark,
    onSurface = VaultInkDark,
    surfaceContainerLowest = VaultCardDark,
    surfaceContainer = VaultSurfaceDark,
    surfaceVariant = VaultSurfaceDark,
    onSurfaceVariant = VaultMuteDark,
    outline = Color(0x1AFFFFFF),
    outlineVariant = Color(0x1AFFFFFF),
    error = VaultErrorDark,
    onError = VaultPaperDark,
    inverseSurface = VaultInkDark,
    inverseOnSurface = VaultPaperDark,
)

@Composable
fun ZerokoshTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalVaultColors provides if (darkTheme) DarkVaultColors else LightVaultColors,
    ) {
        MaterialTheme(
            colorScheme = if (darkTheme) DarkColors else LightColors,
            typography = BharatTypography,
            shapes = BharatShapes,
            content = content,
        )
    }
}
// #endregion
