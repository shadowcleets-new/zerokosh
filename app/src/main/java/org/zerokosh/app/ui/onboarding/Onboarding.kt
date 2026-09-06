/**
 * @file Onboarding.kt
 * @description First-run flow S2–S6, rebuilt screen-for-screen against the
 *              Lovable mockup pack ("Act I · Onboarding"). Every screen shares
 *              the same chrome: M3 small top bar, six-segment step progress,
 *              serif headline with an italic emphasis run, scrollable body, and
 *              a pinned bottom action bar over a hairline rule.
 *
 * [TABLE OF CONTENTS]
 * 1. IMPORTS & DEPENDENCIES
 * 2. SHARED ONBOARDING STATE
 * 3. SHARED CHROME (scaffold, filled field, option card)
 * 4. S2 LANGUAGE
 * 5. S3 TRUST / THREAT MODEL
 * 6. S4 CREATE PASSPHRASE (+ strength, entropy, Argon2id cost)
 * 7. S5 RECOVERY KIT (+ QR, PDF export)
 * 8. S6 QUICK UNLOCK OPT-IN
 * 9. HELPER UTILITIES
 */
package org.zerokosh.app.ui.onboarding

// #region Imports
import android.app.Activity
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import android.view.WindowManager
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Memory
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.PictureAsPdf
import androidx.compose.material.icons.outlined.QrCode2
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.zerokosh.app.ZerokoshApp
import androidx.compose.ui.res.stringResource
import org.zerokosh.app.MainActivity
import org.zerokosh.app.R
import org.zerokosh.app.pdf.RecoveryKitPdf
import org.zerokosh.app.quickunlock.QuickUnlockManager
import org.zerokosh.app.ui.common.BottomActionBar
import org.zerokosh.app.ui.common.EmphasisSpan
import org.zerokosh.app.ui.common.GroupCard
import org.zerokosh.app.ui.common.InkCard
import org.zerokosh.app.ui.common.MicroBadge
import org.zerokosh.app.ui.common.NoticeCard
import org.zerokosh.app.ui.common.NoticeTone
import org.zerokosh.app.ui.common.OnboardingTopBar
import org.zerokosh.app.ui.common.PrimaryPillButton
import org.zerokosh.app.ui.common.RowDivider
import org.zerokosh.app.ui.common.StepProgress
import org.zerokosh.app.ui.common.SubtleTextButton
import org.zerokosh.app.ui.common.VaultToggleRow
import org.zerokosh.app.ui.theme.CornerGroup
import org.zerokosh.app.ui.theme.CornerHero
import org.zerokosh.app.ui.theme.JetBrainsMono
import org.zerokosh.app.ui.theme.SecretTextStyle
import org.zerokosh.app.ui.theme.VaultTheme
import org.zerokosh.core.passphrase.PassphraseSuggestions
import org.zerokosh.core.passphrase.isWeakPinPattern
import org.zerokosh.core.passphrase.pinScore
import kotlin.math.ln
import kotlin.math.pow
// #endregion

// #region Shared onboarding state (in-memory only; wiped after S6)
class OnboardingState {
    var passphrase: ByteArray? = null
    var recoveryKey: String? = null

    /** S4's "Seal to this device" switch — pre-selects biometrics on S6. */
    var sealToDevice: Boolean = true

    fun wipe() {
        passphrase?.fill(0)
        passphrase = null
        recoveryKey = null
    }
}
// #endregion

// #region Shared chrome
/**
 * The chrome every onboarding step shares. Body scrolls between a fixed header
 * (top bar + progress + headline) and the pinned bottom action bar, which is how
 * the mockup lays out all five steps.
 */
@Composable
private fun OnboardingScaffold(
    step: Int,
    onBack: (() -> Unit)?,
    headline: AnnotatedString,
    subhead: String?,
    bottomBar: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    afterHeader: (@Composable ColumnScope.() -> Unit)? = null,
    scrollBody: Boolean = true,
    body: @Composable ColumnScope.() -> Unit,
) {
    val c = VaultTheme.colors
    // The header is fixed and the action bar pinned, so the body lives on
    // whatever is left. In portrait that is most of the screen; on a landscape
    // phone it is roughly 40dp, which is not a cramped field — it is an
    // unusable one. Below the threshold the header scrolls with the body
    // instead, and only the action bar stays pinned.
    BoxWithConstraints(modifier = modifier.fillMaxSize().background(c.paper)) {
        val compact = maxHeight < 600.dp
        val bodyScroll = rememberScrollState()
        val header: @Composable ColumnScope.() -> Unit = {
        Column(Modifier.widthIn(max = 560.dp).align(Alignment.CenterHorizontally).fillMaxWidth()) {
            OnboardingTopBar(
                stepLabel = stringResource(R.string.ob_step_label, step),
                onBack = onBack,
                actionLabel = actionLabel,
                onAction = onAction,
            )
            StepProgress(current = step)
            Column(Modifier.padding(start = 24.dp, end = 24.dp, top = 20.dp, bottom = 16.dp)) {
                Text(
                    headline,
                    style = MaterialTheme.typography.displayMedium,
                    color = c.ink,
                )
                if (subhead != null) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        subhead,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 20.sp,
                        color = c.ink(0.6f),
                    )
                }
            }
            afterHeader?.invoke(this)
        }
        }

        // BV-23: cap before filling, or fillMaxWidth pins the minimum to the
        // parent width and the 560dp cap never applies.
        val bodyWidth = Modifier.widthIn(max = 560.dp).fillMaxWidth()

        Column(Modifier.fillMaxSize().statusBarsPadding()) {
            if (compact) {
                // One scroll region for header and body together: squeezing the
                // body to nothing to keep the header fixed is the wrong trade
                // when the header alone is taller than the viewport.
                Column(Modifier.weight(1f).verticalScroll(bodyScroll)) {
                    header()
                    Column(
                        modifier = bodyWidth.align(Alignment.CenterHorizontally),
                        content = body,
                    )
                }
            } else {
                header()
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .then(bodyWidth)
                        .align(Alignment.CenterHorizontally)
                        .then(if (scrollBody) Modifier.verticalScroll(bodyScroll) else Modifier),
                    content = body,
                )
            }

            BottomActionBar(
                Modifier.widthIn(max = 560.dp).align(Alignment.CenterHorizontally),
                content = bottomBar,
            )
        }
    }
}

/**
 * M3 filled text field as drawn in the mockup: tinted container with square-ish
 * bottom corners, a 2dp primary underline, a small primary label and a mono
 * character counter.
 */
@Composable
private fun FilledSecretField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    counter: String? = null,
    keyboardType: KeyboardType = KeyboardType.Password,
    visible: Boolean = false,
    onToggleVisible: (() -> Unit)? = null,
    isError: Boolean = false,
) {
    val c = VaultTheme.colors
    val underline = if (isError) MaterialTheme.colorScheme.error else c.primary
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 4.dp, bottomEnd = 4.dp))
            .background(c.ink(0.045f))
            .drawBehind {
                drawLine(
                    underline,
                    Offset(0f, size.height - 1f),
                    Offset(size.width, size.height - 1f),
                    strokeWidth = 2 * density,
                )
            }
            .padding(start = 16.dp, end = 8.dp, top = 10.dp, bottom = 12.dp),
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(label, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = underline)
            if (counter != null) {
                Text(counter, fontFamily = JetBrainsMono, fontSize = 11.sp, color = c.ink(0.45f))
            }
        }
        Spacer(Modifier.height(6.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                singleLine = true,
                textStyle = SecretTextStyle.copy(color = c.ink),
                cursorBrush = SolidColor(c.primary),
                visualTransformation = if (visible) VisualTransformation.None
                else PasswordVisualTransformation('•'),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            )
            if (onToggleVisible != null) {
                Box(
                    Modifier
                        // 48dp minimum tap target; the painted circle stays small.
                        .size(48.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onToggleVisible, role = Role.Button),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        if (visible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                        contentDescription = if (visible) "Hide" else "Show",
                        tint = c.ink(0.55f),
                        modifier = Modifier.size(18.dp),
                    )
                }
            }
        }
    }
}

/** The selectable option card used by S6 — tinted and ringed when chosen. */
@Composable
private fun OptionCard(
    icon: ImageVector,
    title: String,
    body: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    recommended: Boolean = false,
    enabled: Boolean = true,
) {
    val c = VaultTheme.colors
    val shape = RoundedCornerShape(CornerGroup)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(if (selected) c.primary.copy(alpha = 0.08f) else c.surface)
            .border(1.dp, if (selected) c.primary.copy(alpha = 0.25f) else c.line, shape)
            .clickable(enabled = enabled, onClick = onClick)
            .padding(16.dp)
            .alpha(if (enabled) 1f else 0.45f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Box(
            Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(if (selected) c.primary else c.ink(0.05f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = if (selected) c.paper else c.ink,
                modifier = Modifier.size(20.dp),
            )
        }
        Column(Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(title, style = MaterialTheme.typography.titleMedium, color = c.ink)
                if (recommended) {
                    Box(
                        Modifier
                            .height(20.dp)
                            .clip(CircleShape)
                            .background(c.primary.copy(alpha = 0.15f))
                            .padding(horizontal = 8.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            stringResource(R.string.ob_recommended),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = c.primary,
                        )
                    }
                }
            }
            Spacer(Modifier.height(2.dp))
            Text(body, fontSize = 12.sp, color = c.ink(0.55f))
        }
        Box(
            Modifier
                .size(20.dp)
                .clip(CircleShape)
                .background(if (selected) c.primary else Color.Transparent)
                .border(1.5.dp, if (selected) c.primary else c.ink(0.3f), CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            if (selected) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = null,
                    tint = c.paper,
                    modifier = Modifier.size(12.dp),
                )
            }
        }
    }
}
// #endregion

// #region S2 Language
internal data class Language(
    val tag: String,
    val script: String,
    val native: String,
    val english: String,
    val available: Boolean,
)

internal val Languages = listOf(
    Language("en", "Aa", "English", "English", true),
    Language("hi", "अ", "हिन्दी", "Hindi", true),
    Language("bn", "অ", "বাংলা", "Bengali", true),
    Language("mr", "म", "मराठी", "Marathi", true),
    Language("te", "అ", "తెలుగు", "Telugu", true),
    Language("ta", "அ", "தமிழ்", "Tamil", true),
    Language("gu", "અ", "ગુજરાતી", "Gujarati", true),
    Language("ur", "ا", "اردو", "Urdu", true),
    Language("kn", "ಅ", "ಕನ್ನಡ", "Kannada", true),
    Language("or", "ଓ", "ଓଡ଼ିଆ", "Odia", true),
    Language("ml", "മ", "മലയാളം", "Malayalam", true),
    Language("pa", "ਪ", "ਪੰਜਾਬੀ", "Punjabi", true),
    Language("as", "অ", "অসমীয়া", "Assamese", true),
    Language("mai", "म", "मैथिली", "Maithili", true),
    Language("ne", "न", "नेपाली", "Nepali", true),
    Language("kok", "क", "कोंकणी", "Konkani", true),
    Language("doi", "ड", "डोगरी", "Dogri", true),
)

@Composable
fun LanguageScreen(app: ZerokoshApp, onBack: (() -> Unit)? = null, onDone: () -> Unit) {
    val c = VaultTheme.colors
    val context = LocalContext.current
    var query by remember { mutableStateOf("") }
    val selectedTag = app.prefs.languageTag.ifEmpty { "en" }
    val selected = Languages.firstOrNull { it.tag == selectedTag } ?: Languages.first()
    val shown = remember(query) {
        if (query.isBlank()) Languages
        else Languages.filter {
            it.english.contains(query, true) || it.native.contains(query, true)
        }
    }

    OnboardingScaffold(
        step = 2,
        onBack = onBack,
        headline = buildAnnotatedString {
            append(stringResource(R.string.ob_lang_head_lead))
            withStyle(EmphasisSpan) { append(stringResource(R.string.ob_lang_head_emph)) }
        },
        subhead = stringResource(R.string.ob_lang_subhead),
        afterHeader = {
            // M3 search bar.
            Row(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 12.dp)
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(CircleShape)
                    .background(c.surface)
                    .border(1.dp, c.line, CircleShape)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Icon(
                    Icons.Outlined.Search,
                    contentDescription = null,
                    tint = c.ink(0.45f),
                    modifier = Modifier.size(18.dp),
                )
                Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                    if (query.isEmpty()) {
                        Text(
                            stringResource(R.string.ob_lang_search, Languages.size),
                            style = MaterialTheme.typography.bodyMedium,
                            color = c.ink(0.4f),
                        )
                    }
                    BasicTextField(
                        value = query,
                        onValueChange = { query = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = LocalTextStyle.current.merge(
                            MaterialTheme.typography.bodyMedium.copy(color = c.ink),
                        ),
                        cursorBrush = SolidColor(c.primary),
                    )
                }
            }
        },
        bottomBar = {
            PrimaryPillButton(
                stringResource(R.string.ob_lang_continue, selected.native),
                onDone,
            )
        },
    ) {
        Column(
            Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            shown.forEach { lang ->
                LanguageRow(
                    lang = lang,
                    selected = lang.tag == selectedTag,
                    onClick = {
                        app.prefs.languageTag = lang.tag
                        (context as? Activity)?.recreate()
                    },
                )
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun LanguageRow(lang: Language, selected: Boolean, onClick: () -> Unit) {
    val c = VaultTheme.colors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(if (selected) c.primary.copy(alpha = 0.10f) else Color.Transparent)
            .clickable(enabled = lang.available, onClick = onClick)
            .padding(start = 12.dp, end = 16.dp, top = 10.dp, bottom = 10.dp)
            .alpha(if (lang.available) 1f else 0.45f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Box(
            Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(if (selected) c.primary else c.surface)
                .then(if (selected) Modifier else Modifier.border(1.dp, c.line, CircleShape)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                lang.script,
                fontSize = 15.sp,
                color = if (selected) c.paper else c.ink(0.7f),
            )
        }
        Column(Modifier.weight(1f)) {
            Text(lang.native, style = MaterialTheme.typography.bodyLarge, color = c.ink)
            Spacer(Modifier.height(2.dp))
            Text(lang.english, fontSize = 11.5.sp, color = c.ink(0.5f))
        }
        if (!lang.available) {
            MicroBadge(stringResource(R.string.ob_soon))
        } else {
            Box(
                Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(if (selected) c.primary else Color.Transparent)
                    .border(2.dp, if (selected) c.primary else c.ink(0.25f), CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                if (selected) Box(Modifier.size(8.dp).background(c.paper, CircleShape))
            }
        }
    }
}
// #endregion

// #region S3 Trust / threat model
private data class TrustFact(
    @StringRes val key: Int,
    @StringRes val value: Int,
    @StringRes val detail: Int,
    val icon: ImageVector,
    val tone: NoticeTone,
)

private val TrustFacts = listOf(
    TrustFact(
        R.string.ob_fact_encryption_title, R.string.ob_fact_encryption_value,
        R.string.ob_fact_encryption_note,
        Icons.Outlined.Lock, NoticeTone.Neutral,
    ),
    TrustFact(
        R.string.ob_fact_kdf_title, R.string.ob_fact_kdf_value, R.string.ob_fact_kdf_note,
        Icons.Outlined.Memory, NoticeTone.Neutral,
    ),
    TrustFact(
        R.string.ob_fact_quick_title, R.string.ob_fact_quick_value, R.string.ob_fact_quick_note,
        Icons.Outlined.Fingerprint, NoticeTone.Positive,
    ),
    TrustFact(
        R.string.ob_fact_network_title, R.string.ob_fact_network_value,
        R.string.ob_fact_network_note,
        Icons.Outlined.CloudOff, NoticeTone.Positive,
    ),
    TrustFact(
        R.string.ob_fact_lost_title, R.string.ob_fact_lost_value, R.string.ob_fact_lost_note,
        Icons.Outlined.WarningAmber, NoticeTone.Warn,
    ),
)

@Composable
fun TrustScreen(onBack: (() -> Unit)? = null, onDone: () -> Unit) {
    val c = VaultTheme.colors
    OnboardingScaffold(
        step = 3,
        onBack = onBack,
        headline = buildAnnotatedString {
            append(stringResource(R.string.ob_trust_head_lead))
            withStyle(EmphasisSpan) { append(stringResource(R.string.ob_trust_head_emph)) }
            append(stringResource(R.string.ob_trust_head_tail))
        },
        subhead = stringResource(R.string.ob_trust_subhead),
        bottomBar = { PrimaryPillButton(stringResource(R.string.ob_trust_continue), onDone) },
    ) {
        // Expressive stat band.
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(CornerGroup))
                .background(c.accent.copy(alpha = 0.12f))
                .border(1.dp, c.accent.copy(alpha = 0.20f), RoundedCornerShape(CornerGroup))
                .padding(vertical = 16.dp),
        ) {
            listOf(
                "0" to stringResource(R.string.ob_trust_stat_servers),
                "0" to stringResource(R.string.ob_trust_stat_trackers),
                "1" to stringResource(R.string.ob_trust_stat_files),
            ).forEachIndexed { i, (n, label) ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .then(
                            if (i < 2) Modifier.drawBehind {
                                drawLine(
                                    c.accent.copy(alpha = 0.20f),
                                    Offset(size.width, 0f),
                                    Offset(size.width, size.height),
                                    strokeWidth = 1f,
                                )
                            } else Modifier,
                        )
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        n,
                        style = MaterialTheme.typography.displaySmall,
                        fontSize = 30.sp,
                        color = c.accent,
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        label,
                        fontSize = 10.5.sp,
                        lineHeight = 13.sp,
                        color = c.ink(0.6f),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Grouped list of facts.
        Column(
            Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            TrustFacts.forEach { f -> TrustFactRow(f) }
        }

        Spacer(Modifier.height(12.dp))

        InkCard(Modifier.padding(horizontal = 16.dp)) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    buildAnnotatedString {
                        withStyle(EmphasisSpan.copy(color = c.paper)) {
                            append(stringResource(R.string.ob_trust_keys_emph))
                        }
                        append(stringResource(R.string.ob_trust_keys_body))
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 12.5.sp,
                    lineHeight = 19.sp,
                    color = c.paper,
                )
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(
                        stringResource(R.string.ob_trust_tag_audited),
                        stringResource(R.string.ob_trust_tag_reproducible),
                    ).forEach { tag ->
                        Box(
                            Modifier
                                .height(28.dp)
                                .clip(CircleShape)
                                .background(c.paper.copy(alpha = 0.12f))
                                .padding(horizontal = 12.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                tag,
                                fontFamily = JetBrainsMono,
                                fontSize = 11.sp,
                                color = c.paper,
                            )
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun TrustFactRow(f: TrustFact) {
    val c = VaultTheme.colors
    val warn = f.tone == NoticeTone.Warn
    val shape = RoundedCornerShape(16.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(if (warn) c.primary.copy(alpha = 0.10f) else c.surface)
            .border(1.dp, if (warn) c.primary.copy(alpha = 0.20f) else c.line, shape)
            .padding(start = 12.dp, end = 16.dp, top = 12.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Box(
            Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    when (f.tone) {
                        NoticeTone.Positive -> c.accent.copy(alpha = 0.18f)
                        NoticeTone.Warn -> c.primary
                        NoticeTone.Neutral -> c.ink(0.08f)
                    },
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                f.icon,
                contentDescription = null,
                tint = when (f.tone) {
                    NoticeTone.Positive -> c.accent
                    NoticeTone.Warn -> c.paper
                    NoticeTone.Neutral -> c.ink(0.7f)
                },
                modifier = Modifier.size(18.dp),
            )
        }
        Column(Modifier.weight(1f)) {
            Text(
                stringResource(f.key).uppercase(),
                fontSize = 11.sp,
                letterSpacing = 0.66.sp,
                color = c.ink(0.45f),
            )
            Spacer(Modifier.height(4.dp))
            Text(
                stringResource(f.value),
                style = MaterialTheme.typography.titleMedium,
                color = when (f.tone) {
                    NoticeTone.Positive -> c.accent
                    NoticeTone.Warn -> c.primary
                    NoticeTone.Neutral -> c.ink
                },
            )
            Spacer(Modifier.height(4.dp))
            Text(stringResource(f.detail), style = MaterialTheme.typography.bodySmall, color = c.ink(0.5f))
        }
    }
}
// #endregion

// #region S4 Create passphrase
fun passphraseScore(p: String): Int {
    if (p.length < 10) return 0
    var score = 1
    val classes = listOf(
        p.any { it.isUpperCase() }, p.any { it.isLowerCase() },
        p.any { it.isDigit() }, p.any { !it.isLetterOrDigit() },
    ).count { it }
    if (p.length >= 14 || classes >= 3) score++
    if (p.length >= 18 && classes >= 3) score++
    val lower = p.lowercase()
    if (Regex("(.)\\1{3,}").containsMatchIn(p) ||
        listOf("password", "123456", "qwerty", "india@", "abcd").any { lower.contains(it) }
    ) score = 1
    return score
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreatePassphraseScreen(
    app: ZerokoshApp,
    onboarding: OnboardingState,
    onBack: (() -> Unit)? = null,
    onDone: () -> Unit,
) {
    val c = VaultTheme.colors
    var pass by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var visible by remember { mutableStateOf(false) }
    var pinMode by remember { mutableStateOf(false) }
    var busy by remember { mutableStateOf(false) }
    var seal by remember { mutableStateOf(onboarding.sealToDevice) }
    val scope = rememberCoroutineScope()
    val pinAllowed = remember { QuickUnlockManager.hardwareBackedBiometricsAvailable(app) }

    // Benchmark Argon2id up front so the hardness card shows this phone's real
    // numbers — and so vault creation doesn't pay for the benchmark later.
    var kdfMem by remember { mutableStateOf(app.prefs.kdfMem) }
    var kdfOps by remember { mutableStateOf(app.prefs.kdfOps) }
    LaunchedEffect(Unit) {
        if (app.prefs.kdfMem == 0L) {
            val (ops, mem) = withContext(Dispatchers.Default) { app.repository.crypto.chooseKdfParams() }
            app.prefs.kdfOps = ops
            app.prefs.kdfMem = mem
            kdfOps = ops
            kdfMem = mem
        }
    }

    val valid = if (pinMode) pass.length == 6 && pass.all { it.isDigit() } && pass == confirm
    else pass.length >= 10 && pass == confirm

    OnboardingScaffold(
        step = 4,
        // Steps 5 and 6 deliberately keep no back arrow: the vault exists by then
        // and stepping backwards would offer to create a second one.
        onBack = onBack,
        headline = buildAnnotatedString {
            append(stringResource(R.string.ob_pass_head_lead))
            withStyle(EmphasisSpan) { append(stringResource(R.string.ob_pass_head_emph)) }
            append(stringResource(R.string.ob_pass_head_tail))
        },
        subhead = stringResource(R.string.ob_pass_subhead),
        bottomBar = {
            PrimaryPillButton(
                label = stringResource(if (busy) R.string.ob_pass_sealing else R.string.ob_pass_seal),
                loading = busy,
                onClick = {
                    busy = true
                    onboarding.sealToDevice = seal
                    scope.launch {
                        val bytes = pass.toByteArray()
                        val recovery = app.repository.createVault(bytes)
                        onboarding.passphrase = bytes
                        onboarding.recoveryKey = recovery
                        busy = false
                        onDone()
                    }
                },
                enabled = valid && !busy,
                showArrow = !busy,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                stringResource(R.string.ob_pass_footer),
                fontSize = 11.sp,
                color = c.ink(0.45f),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        },
    ) {
        Column(Modifier.padding(horizontal = 16.dp)) {
            if (pinAllowed) {
                VaultToggleRow(
                    options = listOf(
                        stringResource(R.string.ob_pass_tab_passphrase),
                        stringResource(R.string.ob_pass_tab_pin),
                    ),
                    selectedIndex = if (pinMode) 1 else 0,
                    onSelect = { index ->
                        pinMode = index == 1
                        pass = ""
                        confirm = ""
                    },
                )
                Spacer(Modifier.height(16.dp))
            }

            FilledSecretField(
                label = stringResource(
                    if (pinMode) R.string.ob_pass_tab_pin else R.string.ob_pass_tab_passphrase,
                ),
                value = pass,
                onValueChange = { pass = if (pinMode) it.filter(Char::isDigit).take(6) else it },
                counter = stringResource(
                    R.string.ob_pass_counter, pass.length, if (pinMode) 6 else 10,
                ),
                keyboardType = if (pinMode) KeyboardType.NumberPassword else KeyboardType.Password,
                visible = visible,
                onToggleVisible = { visible = !visible },
            )

            // The mockup shows a single field. A confirm field stays because a
            // silent typo here means a permanently unopenable vault.
            Spacer(Modifier.height(8.dp))
            FilledSecretField(
                label = stringResource(R.string.ob_pass_confirm),
                value = confirm,
                onValueChange = { confirm = if (pinMode) it.filter(Char::isDigit).take(6) else it },
                counter = if (confirm.isNotEmpty() && confirm != pass) {
                    stringResource(R.string.ob_pass_no_match)
                } else {
                    null
                },
                keyboardType = if (pinMode) KeyboardType.NumberPassword else KeyboardType.Password,
                visible = visible,
                onToggleVisible = { visible = !visible },
                isError = confirm.isNotEmpty() && confirm != pass,
            )

            PassphraseStrengthPanel(pass = pass, pinMode = pinMode)

            Spacer(Modifier.height(16.dp))
            ArgonHardnessCard(memBytes = kdfMem, ops = kdfOps)

            Spacer(Modifier.height(12.dp))
            SealToDeviceCard(
                checked = seal && pinAllowed,
                enabled = pinAllowed,
                onToggle = { seal = it; onboarding.sealToDevice = it },
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}


/**
 * Everything on S4 that reacts to what has been typed.
 *
 * Split out of CreatePassphraseScreen, which had reached a cyclomatic
 * complexity of 47. The split is along a real seam rather than by line count:
 * the meter reads the value, the checklist judges it, and the two modes judge
 * it by different rules — so each is its own function and none of them branches
 * more than a reader can hold.
 */
@Composable
private fun ColumnScope.PassphraseStrengthPanel(pass: String, pinMode: Boolean) {
    StrengthMeter(pass = pass, pinMode = pinMode)
    Spacer(Modifier.height(12.dp))
    if (pinMode) PinChecklist(pass) else PassphraseChecklist(pass)
}

/** 0 = unusable, 1 = weak, and upward on a scale that differs per mode. */
private fun strengthScore(pass: String, pinMode: Boolean): Int =
    if (pinMode) pinScore(pass) else passphraseScore(pass)

/**
 * A PIN caps at three of five lit: six digits is ~19.9 bits, and a full bar
 * beside a 60-bit passphrase would misrepresent the choice on this very screen.
 */
private fun litSegments(pass: String, pinMode: Boolean, score: Int): Int = when {
    pass.isEmpty() -> 0
    pinMode -> score + 1
    else -> (score + 2).coerceAtMost(5)
}

@StringRes
private fun pinStrengthLabel(score: Int): Int = when (score) {
    0 -> R.string.ob_strength_pin_short
    1 -> R.string.ob_strength_pin_weak
    else -> R.string.ob_strength_pin_fair
}

@StringRes
private fun passphraseStrengthLabel(score: Int): Int = when (score) {
    0 -> R.string.ob_strength_pass_short
    1 -> R.string.ob_strength_pass_weak
    2 -> R.string.ob_strength_pass_good
    else -> R.string.ob_strength_pass_strong
}

/**
 * Kept out of the composable so the branching is testable and countable.
 *
 * Returns a resource id rather than a String: the caller formats it with the
 * bit count, and the two labels that carry no count simply ignore the extra
 * argument, which java.util.Formatter allows.
 */
@StringRes
private fun strengthLabel(pass: String, pinMode: Boolean, score: Int): Int = when {
    pass.isNotEmpty() && pinMode -> pinStrengthLabel(score)
    pass.isNotEmpty() -> passphraseStrengthLabel(score)
    pinMode -> R.string.ob_strength_pin_empty
    else -> R.string.ob_strength_pass_empty
}

@Composable
private fun ColumnScope.StrengthMeter(pass: String, pinMode: Boolean) {
    val c = VaultTheme.colors
    val score = strengthScore(pass, pinMode)
    val bits = passphraseEntropyBits(pass)
    val meterColor = when {
        pass.isEmpty() -> c.ink(0.12f)
        score == 0 -> MaterialTheme.colorScheme.error
        score == 1 -> c.primary
        else -> c.accent
    }
    val lit = litSegments(pass, pinMode, score)

    Spacer(Modifier.height(12.dp))
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        repeat(5) { i ->
            Box(
                Modifier
                    .weight(1f)
                    .height(6.dp)
                    .background(if (i < lit) meterColor else c.ink(0.12f), CircleShape),
            )
        }
    }
    Spacer(Modifier.height(8.dp))
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = stringResource(strengthLabel(pass, pinMode, score), bits),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = if (pass.isEmpty()) c.ink(0.45f) else meterColor,
        )
        if (pass.isNotEmpty()) {
            Text(
                crackTimeLabel(bits),
                fontFamily = JetBrainsMono,
                fontSize = 11.sp,
                color = c.ink(0.45f),
            )
        }
    }
}

@Composable
private fun ColumnScope.PinChecklist(pass: String) {
    PassphraseCheck(stringResource(R.string.ob_check_pin_digits), pass.length == 6)
    Spacer(Modifier.height(6.dp))
    PassphraseCheck(
        stringResource(R.string.ob_check_pin_pattern),
        pass.length == 6 && !isWeakPinPattern(pass),
    )
    Spacer(Modifier.height(6.dp))
    // Unverifiable from here, like "not reused" below — a prompt, not a tick
    // the app pretends to have checked.
    PassphraseCheck(stringResource(R.string.ob_check_pin_birthday), null)
}

@Composable
private fun ColumnScope.PassphraseChecklist(pass: String) {
    val c = VaultTheme.colors
    PassphraseCheck(stringResource(R.string.ob_check_pass_length), pass.length >= 10)
    Spacer(Modifier.height(6.dp))
    PassphraseCheck(
        stringResource(R.string.ob_check_pass_dictionary),
        pass.length >= 10 && (pass.contains(' ') || pass.any { !it.isLetter() }),
    )
    Spacer(Modifier.height(6.dp))
    PassphraseCheck(stringResource(R.string.ob_check_pass_reuse), null)

        val suggestions = remember { PassphraseSuggestions.suggest() }
        Spacer(Modifier.height(16.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                stringResource(R.string.ob_try_label),
                fontSize = 11.sp,
                letterSpacing = 0.88.sp,
                color = c.ink(0.4f),
            )
            suggestions.forEach { s ->
                Box(
                    Modifier
                        .height(32.dp)
                        .clip(CircleShape)
                        .background(c.surface)
                        .border(1.dp, c.line, CircleShape)
                        .padding(horizontal = 12.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        s,
                        fontFamily = JetBrainsMono,
                        fontSize = 11.5.sp,
                        color = c.ink(0.7f),
                    )
                }
            }
        }
}

@Composable
private fun PassphraseCheck(label: String, ok: Boolean?) {
    val c = VaultTheme.colors
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(
            Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(if (ok == true) c.accent else Color.Transparent)
                .then(if (ok == true) Modifier else Modifier.border(1.dp, c.ink(0.2f), CircleShape)),
            contentAlignment = Alignment.Center,
        ) {
            if (ok == true) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = null,
                    tint = c.paper,
                    modifier = Modifier.size(10.dp),
                )
            }
        }
        Text(
            label,
            fontSize = 12.5.sp,
            color = if (ok == true) c.ink(0.7f) else c.ink(0.45f),
        )
    }
}

/** Shows this phone's actual Argon2id cost, benchmarked on entry. */
@Composable
private fun ArgonHardnessCard(memBytes: Long, ops: Long) {
    val c = VaultTheme.colors
    val mb = (memBytes / (1024 * 1024)).toInt()
    // BV-26: CryptoProvider.DEFAULT_MEM_BYTES is 64 MiB and chooseKdfParams only
    // ever steps *down* from it (64 → 48 → 32), so that is the real ceiling. The
    // bar used to read against 512 MB, which drew a maxed-out device at 12.5%.
    val fraction = (mb / 64f).coerceIn(0.25f, 1f)
    GroupCard(Modifier.fillMaxWidth(), corner = CornerGroup) {
        Column(Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box(
                    Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(c.ink(0.08f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.Outlined.Memory,
                        contentDescription = null,
                        tint = c.ink(0.7f),
                        modifier = Modifier.size(18.dp),
                    )
                }
                Column(Modifier.weight(1f)) {
                    Text(
                        stringResource(R.string.ob_argon_title),
                        style = MaterialTheme.typography.titleSmall,
                        color = c.ink,
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        if (mb == 0) {
                            stringResource(R.string.ob_argon_measuring)
                        } else {
                            stringResource(R.string.ob_argon_detail, mb, ops)
                        },
                        fontSize = 11.5.sp,
                        color = c.ink(0.5f),
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(CircleShape)
                    .background(c.ink(0.10f)),
            ) {
                Box(
                    Modifier
                        .fillMaxWidth(fraction)
                        .height(6.dp)
                        .background(c.primary, CircleShape),
                )
            }
            Spacer(Modifier.height(6.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(stringResource(R.string.ob_argon_faster), fontSize = 10.5.sp, color = c.ink(0.4f))
                Text(stringResource(R.string.ob_argon_harder), fontSize = 10.5.sp, color = c.ink(0.4f))
            }
        }
    }
}

@Composable
private fun SealToDeviceCard(checked: Boolean, enabled: Boolean, onToggle: (Boolean) -> Unit) {
    val c = VaultTheme.colors
    val shape = RoundedCornerShape(CornerGroup)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(c.accent.copy(alpha = 0.12f))
            .border(1.dp, c.accent.copy(alpha = 0.22f), shape)
            .clickable(enabled = enabled) { onToggle(!checked) }
            .padding(16.dp)
            .alpha(if (enabled) 1f else 0.5f),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(c.accent.copy(alpha = 0.20f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                Icons.Outlined.Lock,
                contentDescription = null,
                tint = c.accent,
                modifier = Modifier.size(18.dp),
            )
        }
        Column(Modifier.weight(1f)) {
            Text(
                stringResource(R.string.ob_seal_title),
                style = MaterialTheme.typography.titleSmall,
                color = c.ink,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                stringResource(
                    if (enabled) R.string.ob_seal_body else R.string.ob_seal_unavailable,
                ),
                style = MaterialTheme.typography.bodySmall,
                color = c.ink(0.6f),
            )
        }
        // M3 switch, drawn to the mockup's 46x26dp geometry.
        Box(
            Modifier
                .width(46.dp)
                .height(26.dp)
                .clip(CircleShape)
                .background(if (checked) c.accent else c.ink(0.18f)),
        ) {
            Box(
                Modifier
                    .padding(3.dp)
                    .size(20.dp)
                    .align(if (checked) Alignment.CenterEnd else Alignment.CenterStart)
                    .background(c.paper, CircleShape),
            )
        }
    }
}
// #endregion

// #region S5 Recovery Kit
@Composable
fun RecoveryKitScreen(app: ZerokoshApp, onboarding: OnboardingState, onDone: () -> Unit) {
    val c = VaultTheme.colors
    val key = onboarding.recoveryKey ?: return
    var confirmed by remember { mutableStateOf(false) }
    var rotating by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Onboarding otherwise leaves screenshots on throughout, so the one screen
    // that puts the Recovery Key on display is also the one screen a recorder
    // would want. The card below tells the user "not a screenshot" in as many
    // words; leaving the capture path open while saying that is the app not
    // meaning it. Clamped for this step only, and handed straight back to the
    // normal rule on the way out — the Save PDF and QR image routes are how the
    // key is meant to leave, and neither goes through the framebuffer.
    DisposableEffect(Unit) {
        val activity = context as? MainActivity
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE,
        )
        onDispose { activity?.applyScreenPrivacy() }
    }

    // BV-09: a 320x320 QR is a ~102k-iteration pixel loop; building it inside
    // remember{} ran it during composition and dropped frames on entry.
    val qr by produceState<ImageBitmap?>(initialValue = null, key) {
        value = withContext(Dispatchers.Default) { qrImage(key, 320) }
    }

    val pdfLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("application/pdf"),
    ) { uri ->
        if (uri != null) {
            // BV-09: PDF generation and the stream write are both I/O.
            scope.launch(Dispatchers.IO) {
                runCatching {
                    context.contentResolver.openOutputStream(uri)?.use { out ->
                        RecoveryKitPdf.write(context, key, out)
                    }
                }
            }
        }
    }
    val pngLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("image/png"),
    ) { uri ->
        if (uri != null) {
            // BV-09: 1024x1024 is a ~1M-iteration loop plus a PNG encode.
            scope.launch(Dispatchers.IO) {
                runCatching {
                    context.contentResolver.openOutputStream(uri)?.use { out ->
                        qrBitmap(key, 1024).compress(Bitmap.CompressFormat.PNG, 100, out)
                    }
                }
            }
        }
    }

    OnboardingScaffold(
        step = 5,
        onBack = null,
        actionLabel = stringResource(
            if (rotating) R.string.ob_kit_working else R.string.ob_kit_regenerate,
        ),
        onAction = {
            val pass = onboarding.passphrase
            if (pass != null && !rotating) {
                rotating = true
                scope.launch {
                    app.repository.rotateRecoveryKey(pass)?.let { onboarding.recoveryKey = it }
                    rotating = false
                }
            }
        },
        headline = buildAnnotatedString {
            append(stringResource(R.string.ob_kit_head_lead))
            withStyle(EmphasisSpan) { append(stringResource(R.string.ob_kit_head_emph)) }
            append(stringResource(R.string.ob_kit_head_tail))
        },
        subhead = stringResource(R.string.ob_kit_subhead),
        bottomBar = {
            PrimaryPillButton(
                label = stringResource(R.string.ob_kit_saved),
                onClick = {
                    onboarding.recoveryKey = null
                    onDone()
                },
                enabled = confirmed,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                stringResource(R.string.ob_kit_footer),
                fontSize = 11.sp,
                color = c.ink(0.45f),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        },
    ) {
        RecoveryCard(key = key, qr = qr, modifier = Modifier.padding(horizontal = 16.dp))

        Spacer(Modifier.height(12.dp))
        GroupCard(Modifier.padding(horizontal = 16.dp)) {
            SaveOptionRow(
                icon = Icons.Outlined.PictureAsPdf,
                title = stringResource(R.string.ob_kit_save_pdf),
                detail = stringResource(R.string.ob_kit_save_pdf_note),
                onClick = { pdfLauncher.launch("Zerokosh-Recovery-Kit.pdf") },
            )
            RowDivider()
            SaveOptionRow(
                icon = Icons.Outlined.QrCode2,
                title = stringResource(R.string.ob_kit_qr),
                detail = stringResource(R.string.ob_kit_qr_note),
                onClick = { pngLauncher.launch("Zerokosh-Recovery-Key.png") },
            )
        }

        Spacer(Modifier.height(12.dp))
        NoticeCard(
            title = stringResource(R.string.ob_kit_offline_title),
            body = stringResource(R.string.ob_kit_offline_body),
            icon = Icons.Outlined.WarningAmber,
            tone = NoticeTone.Warn,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        Spacer(Modifier.height(12.dp))
        // Confirmation.
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(CornerGroup))
                .background(c.surface)
                .border(1.dp, c.line, RoundedCornerShape(CornerGroup))
                .clickable { confirmed = !confirmed }
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Box(
                Modifier
                    .size(20.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (confirmed) c.ink else Color.Transparent)
                    .then(
                        if (confirmed) Modifier
                        else Modifier.border(1.5.dp, c.ink(0.3f), RoundedCornerShape(6.dp)),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                if (confirmed) {
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = null,
                        tint = c.paper,
                        modifier = Modifier.size(12.dp),
                    )
                }
            }
            Text(
                buildAnnotatedString {
                    append(stringResource(R.string.ob_kit_confirm_emph))
                    withStyle(SpanStyle(fontWeight = FontWeight.Medium, color = c.ink)) {
                        append(stringResource(R.string.ob_kit_confirm_body))
                    }
                },
                fontSize = 12.5.sp,
                lineHeight = 18.sp,
                color = c.ink(0.75f),
            )
        }
        Spacer(Modifier.height(16.dp))
    }
}

/** The dark recovery card: glow washes, grouped key, and a scannable QR. */
@Composable
private fun RecoveryCard(key: String, qr: ImageBitmap?, modifier: Modifier = Modifier) {
    val c = VaultTheme.colors
    val parts = key.split("-")
    Box(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(CornerHero))
            .background(c.ink)
            .drawBehind {
                drawCircle(
                    c.primary.copy(alpha = 0.30f),
                    radius = size.minDimension * 0.55f,
                    center = Offset(size.width * 0.95f, -size.height * 0.15f),
                )
                drawCircle(
                    c.accent.copy(alpha = 0.20f),
                    radius = size.minDimension * 0.55f,
                    center = Offset(size.width * 0.05f, size.height * 1.15f),
                )
            }
            .padding(20.dp),
    ) {
        Column {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    stringResource(R.string.ob_kit_card_label),
                    fontFamily = JetBrainsMono,
                    fontSize = 9.5.sp,
                    letterSpacing = 2.47.sp,
                    color = c.paper.copy(alpha = 0.5f),
                )
                Box(
                    Modifier
                        .height(24.dp)
                        .clip(CircleShape)
                        .background(c.paper.copy(alpha = 0.12f))
                        .padding(horizontal = 10.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("offline", fontFamily = JetBrainsMono, fontSize = 10.sp, color = c.accent)
                }
            }

            Spacer(Modifier.height(16.dp))
            KeyGroups(parts)

            Spacer(Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Box(
                    Modifier
                        .size(84.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(c.paper)
                        .padding(8.dp),
                ) {
                    if (qr != null) {
                        Image(
                            bitmap = qr,
                            contentDescription = stringResource(R.string.ob_kit_qr_cd),
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
                Text(
                    stringResource(R.string.ob_kit_card_note),
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    color = c.paper.copy(alpha = 0.65f),
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun KeyGroups(parts: List<String>) {
    val c = VaultTheme.colors
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        parts.forEachIndexed { i, part ->
            if (i == 0) {
                Text(
                    part,
                    fontFamily = JetBrainsMono,
                    fontSize = 19.sp,
                    letterSpacing = 2.28.sp,
                    color = c.paper.copy(alpha = 0.45f),
                )
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "-",
                        fontFamily = JetBrainsMono,
                        fontSize = 19.sp,
                        color = c.paper.copy(alpha = 0.30f),
                    )
                    Spacer(Modifier.width(6.dp))
                    Box(
                        Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(c.paper.copy(alpha = 0.08f))
                            .padding(horizontal = 6.dp, vertical = 1.dp),
                    ) {
                        Text(
                            part,
                            fontFamily = JetBrainsMono,
                            fontSize = 19.sp,
                            letterSpacing = 2.28.sp,
                            color = c.paper,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SaveOptionRow(
    icon: ImageVector,
    title: String,
    detail: String,
    onClick: () -> Unit,
) {
    val c = VaultTheme.colors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(c.primary.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(icon, contentDescription = null, tint = c.primary, modifier = Modifier.size(17.dp))
        }
        Column(Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.titleSmall, color = c.ink)
            Spacer(Modifier.height(2.dp))
            Text(detail, style = MaterialTheme.typography.bodySmall, color = c.ink(0.5f))
        }
        Icon(
            Icons.AutoMirrored.Outlined.KeyboardArrowRight,
            contentDescription = null,
            tint = c.ink(0.3f),
            modifier = Modifier.size(18.dp),
        )
    }
}
// #endregion

// #region S6 Quick unlock opt-in
/**
 * Turn on quick unlock if it was asked for, then open the vault.
 *
 * Returns false only when the user asked for a fingerprint and did not end up
 * with one. Dismissing the system prompt used to fall straight through to a
 * finished vault with quick unlock quietly off — asked for a fingerprint, given
 * none, told nothing. The caller keeps them on the step instead, so the choice
 * is still theirs: press again, or take the Skip that says what it does.
 */
private suspend fun sealOnboarding(
    activity: FragmentActivity,
    app: ZerokoshApp,
    onboarding: OnboardingState,
    enable: Boolean,
): Boolean {
    val pass = onboarding.passphrase
    if (enable && (pass == null || !QuickUnlockManager.enable(activity, app, pass))) return false
    // BV-25: the session is opened here, not in createVault, so S5 and S6 get to
    // render first. Must run before wipe() zeroes the array.
    if (pass != null) app.repository.completeOnboarding(pass)
    onboarding.wipe()
    return true
}

@Composable
fun QuickUnlockScreen(app: ZerokoshApp, onboarding: OnboardingState, onDone: () -> Unit) {
    val c = VaultTheme.colors
    val available = remember { QuickUnlockManager.hardwareBackedBiometricsAvailable(app) }
    // BV-14: LocalContext can be a wrapper; LocalActivity resolves the host.
    val activity = LocalActivity.current as FragmentActivity
    val scope = rememberCoroutineScope()
    var useBiometrics by remember { mutableStateOf(available && onboarding.sealToDevice) }

    var finishing by remember { mutableStateOf(false) }
    var quickUnlockRefused by remember { mutableStateOf(false) }
    val finish: (Boolean) -> Unit = { enable ->
        if (!finishing) {
            finishing = true
            quickUnlockRefused = false
            scope.launch {
                if (sealOnboarding(activity, app, onboarding, enable)) {
                    onDone()
                } else {
                    quickUnlockRefused = true
                    finishing = false
                }
            }
        }
    }

    OnboardingScaffold(
        step = 6,
        onBack = null,
        headline = buildAnnotatedString {
            append(stringResource(R.string.ob_quick_head_lead))
            withStyle(EmphasisSpan) { append(stringResource(R.string.ob_quick_head_emph)) }
        },
        subhead = stringResource(R.string.ob_quick_subhead),
        bottomBar = {
            PrimaryPillButton(
                label = when {
                    finishing -> stringResource(R.string.ob_quick_opening)
                    useBiometrics -> stringResource(R.string.ob_quick_enable)
                    else -> stringResource(R.string.ob_quick_continue_pass)
                },
                onClick = { finish(useBiometrics) },
                enabled = !finishing,
                showArrow = !finishing,
                loading = finishing,
            )
            if (quickUnlockRefused) {
                Spacer(Modifier.height(10.dp))
                Text(
                    stringResource(R.string.ob_quick_refused),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                )
            }
            Spacer(Modifier.height(10.dp))
            SubtleTextButton(
                stringResource(R.string.ob_quick_skip),
                onClick = { finish(false) },
            )
        },
    ) {
        Column(
            Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            OptionCard(
                icon = Icons.Outlined.Fingerprint,
                title = stringResource(R.string.ob_quick_fingerprint),
                body = stringResource(
                    if (available) R.string.ob_quick_fingerprint_body else R.string.ob_quick_no_sensor,
                ),
                selected = useBiometrics,
                recommended = available,
                enabled = available,
                onClick = { useBiometrics = true },
            )
            OptionCard(
                icon = Icons.Outlined.Password,
                title = stringResource(R.string.ob_quick_pass_only),
                body = stringResource(R.string.ob_quick_pass_only_body),
                selected = !useBiometrics,
                onClick = { useBiometrics = false },
            )
        }

        Spacer(Modifier.height(16.dp))
        LiveScanHint(Modifier.padding(horizontal = 16.dp))

        Spacer(Modifier.height(12.dp))
        InkCard(Modifier.padding(horizontal = 16.dp)) {
            Row(
                Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box(
                    Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(c.paper.copy(alpha = 0.10f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.Outlined.Shield,
                        contentDescription = null,
                        tint = c.accent,
                        modifier = Modifier.size(20.dp),
                    )
                }
                Column {
                    Text(
                        stringResource(R.string.ob_quick_hw_title),
                        style = MaterialTheme.typography.titleSmall,
                        color = c.paper,
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        stringResource(R.string.ob_quick_hw_body),
                        fontSize = 11.5.sp,
                        lineHeight = 15.sp,
                        color = c.paper.copy(alpha = 0.6f),
                    )
                }
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}

/** The pulsing sensor prompt from the mockup. */
@Composable
private fun LiveScanHint(modifier: Modifier = Modifier) {
    val c = VaultTheme.colors
    val pulse by rememberInfiniteTransition(label = "sensor").animateFloat(
        initialValue = 0.35f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1400), RepeatMode.Reverse),
        label = "sensorPulse",
    )
    GroupCard(modifier) {
        Row(
            Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Box(contentAlignment = Alignment.Center) {
                Box(
                    Modifier
                        .size(64.dp)
                        .alpha(pulse)
                        .background(c.primary.copy(alpha = 0.10f), CircleShape),
                )
                Box(
                    Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(c.paper)
                        .border(1.dp, c.line, CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.Outlined.Fingerprint,
                        contentDescription = null,
                        tint = c.primary,
                        modifier = Modifier.size(40.dp),
                    )
                }
            }
            Column(Modifier.weight(1f)) {
                Text(
                    stringResource(R.string.ob_quick_touch_title),
                    style = MaterialTheme.typography.titleSmall,
                    color = c.ink,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    stringResource(R.string.ob_quick_touch_body),
                    style = MaterialTheme.typography.bodySmall,
                    color = c.ink(0.55f),
                )
            }
        }
    }
}
// #endregion

// #region Helper utilities
/**
 * BV-28: the charset formula (length × log2(pool)) assumes every character was
 * drawn uniformly at random. For a remembered phrase that is wildly optimistic —
 * it rated "monsoon brass lokhandwala 42" at 145 bits and told the user it would
 * outlast the sun. A multi-word phrase is guessed word-by-word, so it is scored
 * against a word list instead. 7776 is the standard diceware list size; everyday
 * vocabulary is smaller than that, so this stays generous without being absurd.
 */
/** A phrase is guessed word by word, so it is scored against a word list. */
private fun phraseBits(p: String, words: List<String>): Int {
    val bitsPerWord = ln(7776.0) / ln(2.0)
    // Punctuation or mixed case is worth a little more than the bare words,
    // but nothing like the full charset estimate.
    val decorated = p.any { !it.isLetterOrDigit() && it != ' ' } || p.any(Char::isUpperCase)
    return (words.size * bitsPerWord + if (decorated) 6 else 0).toInt()
}

/** A single token is scored on the character classes it actually uses. */
private fun charsetBits(p: String): Int {
    var pool = 0
    if (p.any { it.isLowerCase() }) pool += 26
    if (p.any { it.isUpperCase() }) pool += 26
    if (p.any { it.isDigit() }) pool += 10
    if (p.any { !it.isLetterOrDigit() }) pool += 24
    if (pool <= 1) return 0
    return (p.length * ln(pool.toDouble()) / ln(2.0)).toInt()
}

fun passphraseEntropyBits(p: String): Int {
    if (p.isBlank()) return 0
    val words = p.trim().split(Regex("""\s+""")).filter { it.isNotBlank() }
    return if (words.size >= 2) phraseBits(p, words) else charsetBits(p)
}

/**
 * Coarse offline-attack estimate. Argon2id at 64 MiB is memory-hard, so even a
 * well-funded attacker is far below a billion guesses a second; 100k/s is a
 * deliberately pessimistic ceiling.
 */
@Composable
fun crackTimeLabel(bits: Int): String {
    if (bits <= 0) return stringResource(R.string.ob_crack_seconds)
    val seconds = 2.0.pow((bits - 1).toDouble()) / 1e5
    val years = seconds / 31_557_600.0
    return when {
        seconds < 60 -> stringResource(R.string.ob_crack_seconds)
        seconds < 86_400 -> stringResource(R.string.ob_crack_hours)
        years < 1 -> stringResource(R.string.ob_crack_days, (seconds / 86_400).toInt())
        years < 100 -> stringResource(R.string.ob_crack_years, years.toInt())
        years < 1e6 -> stringResource(R.string.ob_crack_centuries, (years / 100).toInt())
        else -> stringResource(R.string.ob_crack_forever)
    }
}

private fun qrBitmap(text: String, size: Int): Bitmap {
    val matrix = QRCodeWriter().encode(
        text, BarcodeFormat.QR_CODE, size, size,
        mapOf(EncodeHintType.MARGIN to 1),
    )
    val pixels = IntArray(size * size)
    for (y in 0 until size) {
        val row = y * size
        for (x in 0 until size) {
            pixels[row + x] = if (matrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE
        }
    }
    return Bitmap.createBitmap(pixels, size, size, Bitmap.Config.ARGB_8888)
}

private fun qrImage(text: String, size: Int): ImageBitmap? =
    runCatching { qrBitmap(text, size).asImageBitmap() }.getOrNull()
// #endregion
