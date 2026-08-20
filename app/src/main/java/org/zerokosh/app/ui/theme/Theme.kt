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
@file:OptIn(
    androidx.compose.ui.text.ExperimentalTextApi::class, // Font(variationSettings=)
    androidx.compose.material3.ExperimentalMaterial3ExpressiveApi::class,
)

package org.zerokosh.app.ui.theme

// #region Imports
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.LocalRippleThemeConfiguration
import androidx.compose.material3.RippleDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.ShapeDefaults
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

// Opaque container tones. M3 composites container roles over surfaces it picks
// itself, so a translucent primaryContainer turned the floating toolbar muddy
// where it sat over the record hero. These are the same tints pre-flattened
// onto paper, so they read identically but survive being drawn anywhere.
val VaultPrimaryContainer = Color(0xFFF8EDE5)   // primary 7.5% on paper -> 4.53:1 with onPrimaryContainer
val VaultSecondaryContainer = Color(0xFFDFEDE9) // accent 12% on paper; teal-on-paper caps at ~3.4:1
val VaultTertiaryContainer = Color(0xFFEDE9E5)  // warm neutral, ink-family
val VaultErrorContainer = Color(0xFFFAE6E3)     // error 8.5% on paper -> 4.52:1 with onErrorContainer
val VaultSurfaceBright = Color(0xFFFFFFFF)
val VaultSurfaceDim = Color(0xFFEFEBE6)
val VaultSurfaceHigh = Color(0xFFF0ECE7)
val VaultSurfaceHighest = Color(0xFFEAE6E1)

// Dark — from .dark in the mockup stylesheet.
val VaultPaperDark = Color(0xFF120C09)
val VaultSurfaceDark = Color(0xFF241D1B)
val VaultInkDark = Color(0xFFF4F1ED)
val VaultMuteDark = Color(0xFFA9A39E)
val VaultCardDark = Color(0xFF1B1412)
val VaultPrimaryDark = Color(0xFFF0834E)
val VaultAccentDark = Color(0xFF1DBCB5)
val VaultErrorDark = Color(0xFFFF6367)
val VaultPrimaryContainerDark = Color(0xFF351F14)   // primaryDark 16% on paperDark
val VaultSecondaryContainerDark = Color(0xFF142824) // accentDark 16% on paperDark
val VaultTertiaryContainerDark = Color(0xFF2A2320)
val VaultErrorContainerDark = Color(0xFF381A18)
val VaultSurfaceBrightDark = Color(0xFF2E2724)
val VaultSurfaceDimDark = Color(0xFF0D0806)
val VaultSurfaceHighDark = Color(0xFF2B2220)
val VaultSurfaceHighestDark = Color(0xFF362C29)
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
// The mockup's own corner values, expressed through the Expressive shape scale
// rather than five loose literals. ShapeDefaults supplies the two sizes M3
// added for Expressive — LargeIncreased and ExtraLargeIncreased — which the
// hand-written Shapes() could not name at all, so components asking for them
// were silently falling back.
private val BharatShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),          // rounded-2xl
    large = RoundedCornerShape(24.dp),           // rounded-3xl
    largeIncreased = ShapeDefaults.LargeIncreased,
    extraLarge = RoundedCornerShape(26.dp),      // the mockup's grouped list card
    extraLargeIncreased = ShapeDefaults.ExtraLargeIncreased,
    extraExtraLarge = ShapeDefaults.ExtraExtraLarge,
)

/** `--radius` roles lifted from the mockup token set. */
val CornerCard = 24.dp
val CornerGroup = 26.dp
val CornerTile = 16.dp
val CornerHero = 28.dp

// The mockup's hand-tuned `cubic-bezier(0.32, 0.72, 0, 1)` easing and its three
// duration constants are gone: MaterialExpressiveTheme installs a MotionScheme,
// and MaterialTheme.motionScheme.defaultSpatialSpec() / fastEffectsSpec() give
// physically-modelled springs that a fixed bezier cannot. Read the scheme at the
// call site rather than reaching for a constant.
// #endregion

// #region Theme entry point
// GENERATED by design/gen_tonal_scheme.py — do not hand-edit.
//
// A complete Material 3 tonal scheme derived from the brand seeds, rather than
// the ~24 hand-picked roles this replaces. Every role is now a tone of one of
// six ramps, so no role can be left undefined and silently fall back to the M3
// baseline purple — which is exactly the bug that produced a pink FAB.
//
//   primary          #BB4717   the burnt orange
//   secondary        #018D87   the teal accent
//   tertiary         #647400   primary hue rotated 60 degrees, per the M3 spec
//   error            #D40C1A
//   neutral          #736B68   primary hue at 5.5% chroma, so greys stay warm
//   neutral variant  #7B6962   primary hue at 13% chroma
//
// on*Container sits at tone 30 in light and tone 90 in dark. That is the one
// thing expressiveLightColorScheme() actually changes over the baseline, applied
// here to our own ramps instead of Google's purple.

private val LightColors = lightColorScheme(
    primary = Color(0xFFBB4717),  // brand anchor
    onPrimary = Color(0xFFFFFFFF),  // P100
    primaryContainer = Color(0xFFFFDBCD),  // P90
    onPrimaryContainer = Color(0xFF832700),  // P30
    inversePrimary = Color(0xFFFFB598),  // P80
    secondary = Color(0xFF018D87),  // brand anchor
    onSecondary = Color(0xFFFFFFFF),  // S100
    secondaryContainer = Color(0xFF89F4EC),  // S90
    onSecondaryContainer = Color(0xFF00504C),  // S30
    tertiary = Color(0xFF576500),  // T40
    onTertiary = Color(0xFFFFFFFD),  // T100
    tertiaryContainer = Color(0xFFDEEB7E),  // T90
    onTertiaryContainer = Color(0xFF414C00),  // T30
    error = Color(0xFFD40C1A),  // brand anchor
    onError = Color(0xFFFFFFFF),  // E100
    errorContainer = Color(0xFFFFDAD3),  // E90
    onErrorContainer = Color(0xFF93000D),  // E30
    background = Color(0xFFFFFBFA),  // N99
    onBackground = Color(0xFF201A18),  // N10
    surface = Color(0xFFFFFBFA),  // N99
    onSurface = Color(0xFF201A18),  // N10
    surfaceVariant = Color(0xFFF3DED6),  // NV90
    onSurfaceVariant = Color(0xFF54433D),  // NV30
    surfaceTint = Color(0xFFA93806),  // P40
    inverseSurface = Color(0xFF362F2C),  // N20
    inverseOnSurface = Color(0xFFF8EFEB),  // N95
    outline = Color(0xFF86736C),  // NV50
    outlineVariant = Color(0xFFD7C2BA),  // NV80
    scrim = Color(0xFF000000),  // N0
    surfaceBright = Color(0xFFFFF8F5),  // N98
    surfaceDim = Color(0xFFE1D8D5),  // N87
    surfaceContainerLowest = Color(0xFFFFFFFF),  // N100
    surfaceContainerLow = Color(0xFFFBF2EE),  // N96
    surfaceContainer = Color(0xFFF5ECE8),  // N94
    surfaceContainerHigh = Color(0xFFF0E6E3),  // N92
    surfaceContainerHighest = Color(0xFFEAE1DD),  // N90
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFF0834E),  // brand anchor
    onPrimary = Color(0xFF591C00),  // P20
    primaryContainer = Color(0xFF832700),  // P30
    onPrimaryContainer = Color(0xFFFFDBCD),  // P90
    inversePrimary = Color(0xFFA93806),  // P40
    secondary = Color(0xFF1DBCB5),  // brand anchor
    onSecondary = Color(0xFF003734),  // S20
    secondaryContainer = Color(0xFF00504C),  // S30
    onSecondaryContainer = Color(0xFF89F4EC),  // S90
    tertiary = Color(0xFFC2CE63),  // T80
    onTertiary = Color(0xFF2D3400),  // T20
    tertiaryContainer = Color(0xFF414C00),  // T30
    onTertiaryContainer = Color(0xFFDEEB7E),  // T90
    error = Color(0xFFFF6367),  // brand anchor
    onError = Color(0xFF690002),  // E20
    errorContainer = Color(0xFF93000D),  // E30
    onErrorContainer = Color(0xFFFFDAD3),  // E90
    background = Color(0xFF201A18),  // N10
    onBackground = Color(0xFFEAE1DD),  // N90
    surface = Color(0xFF201A18),  // N10
    onSurface = Color(0xFFEAE1DD),  // N90
    surfaceVariant = Color(0xFF54433D),  // NV30
    onSurfaceVariant = Color(0xFFD7C2BA),  // NV80
    surfaceTint = Color(0xFFFFB598),  // P80
    inverseSurface = Color(0xFFEAE1DD),  // N90
    inverseOnSurface = Color(0xFF362F2C),  // N20
    outline = Color(0xFFA08D85),  // NV60
    outlineVariant = Color(0xFF54433D),  // NV30
    scrim = Color(0xFF000000),  // N0
    surfaceBright = Color(0xFF3F3835),  // N24
    surfaceDim = Color(0xFF19120F),  // N6
    surfaceContainerLowest = Color(0xFF150C08),  // N4
    surfaceContainerLow = Color(0xFF201A18),  // N10
    surfaceContainer = Color(0xFF251E1C),  // N12
    surfaceContainerHigh = Color(0xFF2F2926),  // N17
    surfaceContainerHighest = Color(0xFF3A3331),  // N22
)

@Composable
fun ZerokoshTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalVaultColors provides if (darkTheme) DarkVaultColors else LightVaultColors,
    ) {
        // Expressive as mechanism, Lovable as identity: we take M3's motion
        // scheme and component behaviour but keep our own OKLCH-derived colour
        // scheme, Newsreader/Instrument Sans/JetBrains Mono type and corner
        // geometry, so the Organic Editorial direction survives the makeover.
        // Expressive replaces the opacity wash that used to mark keyboard focus
        // with a drawn inset ring. The wash was nearly invisible against our
        // tinted surfaces, so this is an accessibility fix, not a restyle.
        CompositionLocalProvider(
            LocalRippleThemeConfiguration provides
                RippleDefaults.InsetFocusRingRippleThemeConfiguration,
        ) {
        MaterialExpressiveTheme(
            colorScheme = if (darkTheme) DarkColors else LightColors,
            motionScheme = MotionScheme.expressive(),
            shapes = BharatShapes,
            typography = BharatTypography,
            content = content,
        )
        }
    }
}
// #endregion
