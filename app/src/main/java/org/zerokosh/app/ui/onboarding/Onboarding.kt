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
                stepLabel = "Step $step of 6",
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
                            "Recommended",
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
private data class Language(
    val tag: String,
    val script: String,
    val native: String,
    val english: String,
    val available: Boolean,
)

private val Languages = listOf(
    Language("en", "Aa", "English", "English", true),
    Language("hi", "अ", "हिन्दी", "Hindi", true),
    Language("ta", "அ", "தமிழ்", "Tamil", false),
    Language("te", "అ", "తెలుగు", "Telugu", false),
    Language("mr", "म", "मराठी", "Marathi", false),
    Language("bn", "অ", "বাংলা", "Bengali", false),
    Language("kn", "ಅ", "ಕನ್ನಡ", "Kannada", false),
    Language("gu", "અ", "ગુજરાતી", "Gujarati", false),
    Language("ml", "മ", "മലയാളം", "Malayalam", false),
    Language("or", "ଓ", "ଓଡ଼ିଆ", "Odia", false),
    Language("pa", "ਪ", "ਪੰਜਾਬੀ", "Punjabi", false),
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
            append("Choose your ")
            withStyle(EmphasisSpan) { append("language.") }
        },
        subhead = "Vault labels, templates and warnings adapt instantly. " +
            "Change it anytime in Settings.",
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
                            "Search ${Languages.size} languages",
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
            PrimaryPillButton("Continue in ${selected.english}", onDone)
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
            MicroBadge("SOON")
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
    val key: String,
    val value: String,
    val detail: String,
    val icon: ImageVector,
    val tone: NoticeTone,
)

private val TrustFacts = listOf(
    TrustFact(
        "Encryption", "XChaCha20-Poly1305", "Authenticated, per-vault nonce",
        Icons.Outlined.Lock, NoticeTone.Neutral,
    ),
    TrustFact(
        "Key stretching", "Argon2id · 64 MB · t=3", "Measured on your phone at setup",
        Icons.Outlined.Memory, NoticeTone.Neutral,
    ),
    TrustFact(
        "Quick unlock", "Hardware keystore", "Biometric never leaves the enclave",
        Icons.Outlined.Fingerprint, NoticeTone.Positive,
    ),
    TrustFact(
        "Network permission", "Not requested", "The app literally cannot phone home",
        Icons.Outlined.CloudOff, NoticeTone.Positive,
    ),
    TrustFact(
        "If you lose your keys", "Nobody can recover it", "No reset link. No support backdoor.",
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
            append("Exactly what we ")
            withStyle(EmphasisSpan) { append("don't") }
            append(" know.")
        },
        subhead = "Read this once. It is the whole security model, in plain words.",
        bottomBar = { PrimaryPillButton("I understand · Continue", onDone) },
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
                "0" to "servers contacted",
                "0" to "trackers or SDKs",
                "1" to ".kosh file on device",
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
                            append("You hold the keys. ")
                        }
                        append(
                            "Forget your passphrase and lose the Recovery Kit, and the vault " +
                                "stays sealed — for you, for us, for anyone.",
                        )
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 12.5.sp,
                    lineHeight = 19.sp,
                    color = c.paper,
                )
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Audited build", "Reproducible APK").forEach { tag ->
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
                f.key.uppercase(),
                fontSize = 11.sp,
                letterSpacing = 0.66.sp,
                color = c.ink(0.45f),
            )
            Spacer(Modifier.height(4.dp))
            Text(
                f.value,
                style = MaterialTheme.typography.titleMedium,
                color = when (f.tone) {
                    NoticeTone.Positive -> c.accent
                    NoticeTone.Warn -> c.primary
                    NoticeTone.Neutral -> c.ink
                },
            )
            Spacer(Modifier.height(4.dp))
            Text(f.detail, style = MaterialTheme.typography.bodySmall, color = c.ink(0.5f))
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
            append("One secret, ")
            withStyle(EmphasisSpan) { append("held only") }
            append(" by you.")
        },
        subhead = "Three or four unrelated words beat one clever word. " +
            "Nothing leaves this screen.",
        bottomBar = {
            PrimaryPillButton(
                label = if (busy) "Sealing…" else "Seal the vault",
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
                "We never see this. There is no reset link.",
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
                    options = listOf("Passphrase", "6-digit PIN"),
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
                label = if (pinMode) "6-digit PIN" else "Passphrase",
                value = pass,
                onValueChange = { pass = if (pinMode) it.filter(Char::isDigit).take(6) else it },
                counter = if (pinMode) "${pass.length} / 6" else "${pass.length} / 10",
                keyboardType = if (pinMode) KeyboardType.NumberPassword else KeyboardType.Password,
                visible = visible,
                onToggleVisible = { visible = !visible },
            )

            // The mockup shows a single field. A confirm field stays because a
            // silent typo here means a permanently unopenable vault.
            Spacer(Modifier.height(8.dp))
            FilledSecretField(
                label = "Confirm",
                value = confirm,
                onValueChange = { confirm = if (pinMode) it.filter(Char::isDigit).take(6) else it },
                counter = if (confirm.isNotEmpty() && confirm != pass) "no match" else null,
                keyboardType = if (pinMode) KeyboardType.NumberPassword else KeyboardType.Password,
                visible = visible,
                onToggleVisible = { visible = !visible },
                isError = confirm.isNotEmpty() && confirm != pass,
            )

            if (!pinMode) {
                val score = passphraseScore(pass)
                val bits = passphraseEntropyBits(pass)
                val meterColor = when {
                    pass.isEmpty() -> c.ink(0.12f)
                    score == 0 -> MaterialTheme.colorScheme.error
                    score == 1 -> c.primary
                    else -> c.accent
                }

                Spacer(Modifier.height(12.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    val lit = if (pass.isEmpty()) 0 else (score + 2).coerceAtMost(5)
                    repeat(5) { i ->
                        Box(
                            Modifier
                                .weight(1f)
                                .height(6.dp)
                                .background(
                                    if (i < lit) meterColor else c.ink(0.12f),
                                    CircleShape,
                                ),
                        )
                    }
                }
                Spacer(Modifier.height(8.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = when {
                            pass.isEmpty() -> "Pick something only you would say"
                            score == 0 -> "Too short · needs 10 characters"
                            score == 1 -> "Weak · $bits bits of entropy"
                            score == 2 -> "Good · $bits bits of entropy"
                            else -> "Strong · $bits bits of entropy"
                        },
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

                Spacer(Modifier.height(12.dp))
                PassphraseCheck("10 characters or more", pass.length >= 10)
                Spacer(Modifier.height(6.dp))
                PassphraseCheck(
                    "Not a single dictionary word",
                    pass.length >= 10 && (pass.contains(' ') || pass.any { !it.isLetter() }),
                )
                Spacer(Modifier.height(6.dp))
                PassphraseCheck("Not reused from another app", null)

                // Illustrative patterns only — deliberately not tappable, since a
                // published example passphrase is a published passphrase.
                Spacer(Modifier.height(16.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        "TRY",
                        fontSize = 11.sp,
                        letterSpacing = 0.88.sp,
                        color = c.ink(0.4f),
                    )
                    listOf("chai ledger tiger", "peepal 9 rickshaw").forEach { s ->
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
                        "Argon2id hardness",
                        style = MaterialTheme.typography.titleSmall,
                        color = c.ink,
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        if (mb == 0) "Measuring this device…" else "$mb MB · t=$ops · measured on this device",
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
                Text("Faster unlock", fontSize = 10.5.sp, color = c.ink(0.4f))
                Text("Harder to attack", fontSize = 10.5.sp, color = c.ink(0.4f))
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
            Text("Seal to this device", style = MaterialTheme.typography.titleSmall, color = c.ink)
            Spacer(Modifier.height(4.dp))
            Text(
                if (enabled) "Wrapping key lives in the hardware Keystore. Biometrics come next."
                else "This device has no hardware-backed biometrics.",
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
        actionLabel = if (rotating) "Working…" else "Regenerate",
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
            append("One key. ")
            withStyle(EmphasisSpan) { append("On paper.") }
            append(" Never online.")
        },
        subhead = "Generated on this device, shown once. It is the only way back in " +
            "if the passphrase slips away.",
        bottomBar = {
            PrimaryPillButton(
                label = "I've saved my kit",
                onClick = {
                    onboarding.recoveryKey = null
                    onDone()
                },
                enabled = confirmed,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "This key is shown once and never stored in plain text.",
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
                title = "Save PDF",
                detail = "Printable one-page kit",
                onClick = { pdfLauncher.launch("Zerokosh-Recovery-Kit.pdf") },
            )
            RowDivider()
            SaveOptionRow(
                icon = Icons.Outlined.QrCode2,
                title = "QR image",
                detail = "To an offline gallery",
                onClick = { pngLauncher.launch("Zerokosh-Recovery-Key.png") },
            )
        }

        Spacer(Modifier.height(12.dp))
        NoticeCard(
            title = "Keep it off the internet",
            body = "Not Gmail, not WhatsApp, not a screenshot. A safe, a bank locker, " +
                "or a steel plate.",
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
                    append("I've stored this offline. ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Medium, color = c.ink)) {
                        append("No one — including Zerokosh — can recover it for me.")
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
                    "ZEROKOSH RECOVERY",
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
                            contentDescription = "Recovery key QR code",
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
                Text(
                    "Scan or type it back. Works after a reinstall, a factory reset, " +
                        "or a lost phone.",
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
@Composable
fun QuickUnlockScreen(app: ZerokoshApp, onboarding: OnboardingState, onDone: () -> Unit) {
    val c = VaultTheme.colors
    val available = remember { QuickUnlockManager.hardwareBackedBiometricsAvailable(app) }
    // BV-14: LocalContext can be a wrapper; LocalActivity resolves the host.
    val activity = LocalActivity.current as FragmentActivity
    val scope = rememberCoroutineScope()
    var useBiometrics by remember { mutableStateOf(available && onboarding.sealToDevice) }

    var finishing by remember { mutableStateOf(false) }
    val finish: (Boolean) -> Unit = { enable ->
        if (!finishing) {
            finishing = true
            scope.launch {
                val pass = onboarding.passphrase
                if (enable && pass != null) QuickUnlockManager.enable(activity, app, pass)
                // BV-25: the session is opened here, not in createVault, so S5 and
                // S6 get to render first. Must run before wipe() zeroes the array.
                if (pass != null) app.repository.completeOnboarding(pass)
                onboarding.wipe()
                onDone()
            }
        }
    }

    OnboardingScaffold(
        step = 6,
        onBack = null,
        headline = buildAnnotatedString {
            append("Unlock in a touch. ")
            withStyle(EmphasisSpan) { append("Without the cloud.") }
        },
        subhead = "Pick a quick way back in. Your passphrase still guards the vault; " +
            "this only unlocks the key on this device.",
        bottomBar = {
            PrimaryPillButton(
                label = when {
                    finishing -> "Opening your vault…"
                    useBiometrics -> "Enable quick unlock"
                    else -> "Continue with passphrase"
                },
                onClick = { finish(useBiometrics) },
                enabled = !finishing,
                showArrow = !finishing,
                loading = finishing,
            )
            Spacer(Modifier.height(10.dp))
            SubtleTextButton("Skip for now — I'll type my passphrase", onClick = { finish(false) })
        },
    ) {
        Column(
            Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            OptionCard(
                icon = Icons.Outlined.Fingerprint,
                title = "Fingerprint",
                body = if (available) "Fast, hardware-backed unlock."
                else "No hardware-backed sensor on this device.",
                selected = useBiometrics,
                recommended = available,
                enabled = available,
                onClick = { useBiometrics = true },
            )
            OptionCard(
                icon = Icons.Outlined.Password,
                title = "Passphrase only",
                body = "Type it every time. Most secure.",
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
                        "Hardware-backed",
                        style = MaterialTheme.typography.titleSmall,
                        color = c.paper,
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        "Android Keystore / StrongBox. No biometric data ever reaches Zerokosh.",
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
                Text("Touch the sensor", style = MaterialTheme.typography.titleSmall, color = c.ink)
                Spacer(Modifier.height(4.dp))
                Text(
                    "Your fingerprint is stored inside your phone's secure enclave. " +
                        "It never leaves this device.",
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
fun passphraseEntropyBits(p: String): Int {
    if (p.isBlank()) return 0
    val words = p.trim().split(Regex("\\s+")).filter { it.isNotBlank() }
    if (words.size >= 2) {
        val bitsPerWord = ln(7776.0) / ln(2.0)
        val phraseBits = words.size * bitsPerWord
        // A phrase carrying punctuation or mixed case is worth a little more than
        // its bare words, but nothing like the full charset estimate.
        val bonus = if (p.any { !it.isLetterOrDigit() && it != ' ' } || p.any(Char::isUpperCase)) 6 else 0
        return (phraseBits + bonus).toInt()
    }
    var pool = 0
    if (p.any { it.isLowerCase() }) pool += 26
    if (p.any { it.isUpperCase() }) pool += 26
    if (p.any { it.isDigit() }) pool += 10
    if (p.any { !it.isLetterOrDigit() }) pool += 24
    if (pool <= 1) return 0
    return (p.length * ln(pool.toDouble()) / ln(2.0)).toInt()
}

/**
 * Coarse offline-attack estimate. Argon2id at 64 MiB is memory-hard, so even a
 * well-funded attacker is far below a billion guesses a second; 100k/s is a
 * deliberately pessimistic ceiling.
 */
fun crackTimeLabel(bits: Int): String {
    if (bits <= 0) return "instant"
    val seconds = 2.0.pow((bits - 1).toDouble()) / 1e5
    val years = seconds / 31_557_600.0
    return when {
        seconds < 60 -> "~seconds to crack"
        seconds < 86_400 -> "~hours to crack"
        years < 1 -> "~${(seconds / 86_400).toInt()} days to crack"
        years < 100 -> "~${years.toInt()} years to crack"
        years < 1e6 -> "~${(years / 100).toInt()} centuries to crack"
        else -> "longer than the sun"
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
