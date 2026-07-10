/**
 * @file Onboarding.kt
 * @description First-run flow S1–S5 (§5.1): language grid, trust cards,
 *              passphrase creation with strength meter, Recovery Kit, quick
 *              unlock opt-in.
 *
 * [TABLE OF CONTENTS]
 * 1. SHARED ONBOARDING STATE
 * 2. S1 LANGUAGE
 * 3. S2 TRUST CARDS
 * 4. S3 CREATE PASSPHRASE (+ strength meter)
 * 5. S4 RECOVERY KIT (+ PDF export)
 * 6. S5 QUICK UNLOCK OPT-IN
 */
package org.bharatvault.app.ui.onboarding

// #region Imports
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.bharatvault.app.BharatVaultApp
import org.bharatvault.app.R
import org.bharatvault.app.pdf.RecoveryKitPdf
import org.bharatvault.app.quickunlock.QuickUnlockManager
// #endregion

// #region Shared onboarding state (in-memory only; wiped after S5)
class OnboardingState {
    var passphrase: ByteArray? = null
    var recoveryKey: String? = null

    fun wipe() {
        passphrase?.fill(0)
        passphrase = null
        recoveryKey = null
    }
}
// #endregion

// #region S1 Language picker
private data class Language(val tag: String, val nameInScript: String, val available: Boolean)

/** §5.1 S1: grid in own script. v1 ships en+hi; the rest appear at ≥95% translation (§12). */
private val Languages = listOf(
    Language("en", "English", true),
    Language("hi", "हिन्दी", true),
    Language("bn", "বাংলা", false),
    Language("te", "తెలుగు", false),
    Language("mr", "मराठी", false),
    Language("ta", "தமிழ்", false),
    Language("gu", "ગુજરાતી", false),
    Language("kn", "ಕನ್ನಡ", false),
    Language("ml", "മലയാളം", false),
    Language("or", "ଓଡ଼ିଆ", false),
    Language("pa", "ਪੰਜਾਬੀ", false),
)

@Composable
fun LanguageScreen(app: BharatVaultApp, onDone: () -> Unit) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Spacer(Modifier.height(32.dp))
        Text(stringResource(R.string.scr_language_title), style = MaterialTheme.typography.displaySmall)
        Text(stringResource(R.string.scr_language_subtitle), style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(24.dp))
        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.weight(1f)) {
            items(Languages.filter { it.available }) { lang ->
                Card(
                    onClick = {
                        app.prefs.languageTag = lang.tag
                        (context as? Activity)?.recreate()
                        onDone()
                    },
                    modifier = Modifier.padding(6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (app.prefs.languageTag == lang.tag || (app.prefs.languageTag.isEmpty() && lang.tag == "en"))
                            MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surfaceVariant,
                    ),
                ) {
                    Text(
                        lang.nameInScript,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }
        }
        Button(onClick = onDone, modifier = Modifier.fillMaxWidth().height(52.dp)) {
            Text(stringResource(R.string.scr_language_continue))
        }
    }
}
// #endregion

// #region S2 Trust explainer (3 swipe cards + no-cloud diagram)
@Composable
fun TrustScreen(onDone: () -> Unit) {
    val cards = listOf(
        Triple(R.string.scr_trust_card1_title, R.string.scr_trust_card1_body, Icons.Outlined.PhoneAndroid),
        Triple(R.string.scr_trust_card2_title, R.string.scr_trust_card2_body, Icons.Outlined.CloudOff),
        Triple(R.string.scr_trust_card3_title, R.string.scr_trust_card3_body, Icons.Outlined.Lock),
    )
    val pager = rememberPagerState { cards.size }
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        HorizontalPager(state = pager, modifier = Modifier.weight(1f)) { page ->
            val (title, body, icon) = cards[page]
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(icon, contentDescription = null, modifier = Modifier.size(96.dp), tint = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(32.dp))
                Text(stringResource(title), style = MaterialTheme.typography.displaySmall, textAlign = TextAlign.Center)
                Spacer(Modifier.height(16.dp))
                Text(stringResource(body), style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
            }
        }
        Row(modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp)) {
            repeat(cards.size) { i ->
                Icon(
                    Icons.Filled.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(10.dp).padding(horizontal = 1.dp),
                    tint = if (i == pager.currentPage) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        }
        Button(onClick = onDone, modifier = Modifier.fillMaxWidth().height(52.dp)) {
            Text(stringResource(R.string.scr_trust_continue))
        }
    }
}
// #endregion

// #region S3 Create passphrase
/** zxcvbn-style score 0–3 without the library: length + variety + anti-patterns. */
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

@Composable
fun CreatePassphraseScreen(app: BharatVaultApp, onboarding: OnboardingState, onDone: () -> Unit) {
    var pass by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var pinMode by remember { mutableStateOf(false) }
    var busy by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val pinAllowed = remember { QuickUnlockManager.hardwareBackedBiometricsAvailable(app) }

    val valid = if (pinMode) pass.length == 6 && pass.all { it.isDigit() } && pass == confirm
    else pass.length >= 10 && pass == confirm

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
    ) {
        Spacer(Modifier.height(32.dp))
        Text(stringResource(R.string.scr_create_title), style = MaterialTheme.typography.displaySmall)
        Spacer(Modifier.height(8.dp))
        Text(stringResource(R.string.scr_create_body), style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = pass,
            onValueChange = { pass = if (pinMode) it.filter(Char::isDigit).take(6) else it },
            label = { Text(stringResource(if (pinMode) R.string.scr_create_pin_hint else R.string.scr_create_hint)) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = if (pinMode) KeyboardType.NumberPassword else KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        if (!pinMode) {
            val score = passphraseScore(pass)
            val (labelRes, color) = when (score) {
                0 -> R.string.scr_create_strength_weak to MaterialTheme.colorScheme.error
                1 -> R.string.scr_create_strength_fair to MaterialTheme.colorScheme.secondary
                2 -> R.string.scr_create_strength_good to MaterialTheme.colorScheme.primary
                else -> R.string.scr_create_strength_strong to MaterialTheme.colorScheme.tertiary
            }
            Spacer(Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { (score + 1) / 4f },
                modifier = Modifier.fillMaxWidth(),
                color = color,
            )
            Text(
                text = if (pass.isNotEmpty() && pass.length < 10) stringResource(R.string.scr_create_too_short)
                else if (pass.isNotEmpty()) stringResource(labelRes) else "",
                style = MaterialTheme.typography.labelSmall,
                color = color,
            )
        }
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = confirm,
            onValueChange = { confirm = if (pinMode) it.filter(Char::isDigit).take(6) else it },
            label = { Text(stringResource(R.string.scr_create_confirm_hint)) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = if (pinMode) KeyboardType.NumberPassword else KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = confirm.isNotEmpty() && confirm != pass,
            supportingText = {
                if (confirm.isNotEmpty() && confirm != pass) {
                    Text(stringResource(R.string.scr_create_mismatch), color = MaterialTheme.colorScheme.error)
                }
            },
        )

        Spacer(Modifier.height(8.dp))
        // §5.1 S3: PIN only when hardware keystore + biometrics exist
        TextButton(onClick = {
            if (pinAllowed) {
                pinMode = !pinMode
                pass = ""
                confirm = ""
            }
        }) {
            Text(
                if (pinAllowed) stringResource(R.string.scr_create_pin_option)
                else stringResource(R.string.scr_create_pin_unavailable),
            )
        }
        if (pinMode) {
            Text(stringResource(R.string.scr_create_pin_why), style = MaterialTheme.typography.labelSmall)
        }

        Spacer(Modifier.height(24.dp))
        Button(
            enabled = valid && !busy,
            onClick = {
                busy = true
                scope.launch {
                    val bytes = pass.toByteArray()
                    val recovery = app.repository.createVault(bytes)
                    onboarding.passphrase = bytes // kept until S5 for quick-unlock enrolment
                    onboarding.recoveryKey = recovery
                    busy = false
                    onDone()
                }
            },
            modifier = Modifier.fillMaxWidth().height(52.dp),
        ) {
            if (busy) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = MaterialTheme.colorScheme.onPrimary)
                Spacer(Modifier.size(8.dp))
                Text(stringResource(R.string.scr_create_working))
            } else {
                Text(stringResource(R.string.scr_create_button))
            }
        }
        Spacer(Modifier.height(32.dp))
    }
}
// #endregion

// #region S4 Recovery Kit
@Composable
fun RecoveryKitScreen(app: BharatVaultApp, onboarding: OnboardingState, onDone: () -> Unit) {
    val key = onboarding.recoveryKey ?: return
    var confirmed by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var pdfSaved by remember { mutableStateOf(false) }
    val pdfLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("application/pdf"),
    ) { uri ->
        if (uri != null) {
            context.contentResolver.openOutputStream(uri)?.use { out ->
                RecoveryKitPdf.write(context, key, out)
            }
            pdfSaved = true
        }
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp)) {
        Spacer(Modifier.height(32.dp))
        Text(stringResource(R.string.scr_recovery_title), style = MaterialTheme.typography.displaySmall)
        Spacer(Modifier.height(8.dp))
        Text(stringResource(R.string.scr_recovery_body), style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(24.dp))

        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
            Text(
                key,
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                style = MaterialTheme.typography.titleMedium.copy(fontFamily = FontFamily.Monospace),
                textAlign = TextAlign.Center,
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            stringResource(R.string.scr_recovery_never_again),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.error,
        )
        Spacer(Modifier.height(24.dp))

        OutlinedButton(
            onClick = { pdfLauncher.launch("BharatVault-Recovery-Kit.pdf") },
            modifier = Modifier.fillMaxWidth().height(52.dp),
        ) {
            Text(stringResource(if (pdfSaved) R.string.scr_recovery_pdf_saved else R.string.scr_recovery_save_pdf))
        }
        Spacer(Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = confirmed, onCheckedChange = { confirmed = it })
            Text(stringResource(R.string.scr_recovery_confirm_check), style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(Modifier.height(16.dp))
        Button(
            enabled = confirmed,
            onClick = {
                onboarding.recoveryKey = null // NEVER shown again (§5.1 S4)
                onDone()
            },
            modifier = Modifier.fillMaxWidth().height(52.dp),
        ) { Text(stringResource(R.string.scr_recovery_done)) }
        Spacer(Modifier.height(32.dp))
    }
}
// #endregion

// #region S5 Quick unlock opt-in
@Composable
fun QuickUnlockScreen(app: BharatVaultApp, onboarding: OnboardingState, onDone: () -> Unit) {
    val available = remember { QuickUnlockManager.hardwareBackedBiometricsAvailable(app) }
    val activity = LocalContext.current as androidx.fragment.app.FragmentActivity
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(Icons.Outlined.Fingerprint, null, modifier = Modifier.size(96.dp), tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(32.dp))
        Text(stringResource(R.string.scr_quickunlock_title), style = MaterialTheme.typography.displaySmall, textAlign = TextAlign.Center)
        Spacer(Modifier.height(16.dp))
        Text(stringResource(R.string.scr_quickunlock_body), style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
        Spacer(Modifier.height(40.dp))
        if (available) {
            Button(
                onClick = {
                    scope.launch {
                        val pass = onboarding.passphrase
                        if (pass != null) {
                            QuickUnlockManager.enable(activity, app, pass)
                        }
                        onboarding.wipe()
                        onDone()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
            ) { Text(stringResource(R.string.scr_quickunlock_enable)) }
        }
        TextButton(onClick = {
            onboarding.wipe()
            onDone()
        }) { Text(stringResource(R.string.scr_quickunlock_skip)) }
    }
}
// #endregion
