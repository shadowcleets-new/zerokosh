/**
 * @file LockScreen.kt
 * @description S13 (§5.1): passphrase/PIN + biometric prompt; 5 fails → 30 s
 *              cooldown doubling; NEVER wipes. Rebuilt against the Lovable
 *              mockup "Locked": a fixed charcoal surface with two blurred
 *              glows, a serif welcome, and a 128dp pulsing sensor target.
 *
 * [TABLE OF CONTENTS]
 * 1. IMPORTS & DEPENDENCIES
 * 2. FIXED DARK PALETTE
 * 3. SCREEN + UNLOCK LOGIC
 * 4. SENSOR TARGET
 */
@file:OptIn(androidx.compose.material3.ExperimentalMaterial3ExpressiveApi::class)

package org.zerokosh.app.ui.lock

// #region Imports
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.material3.ContainedLoadingIndicator
import org.zerokosh.app.ui.common.RevealToggle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.R
import org.zerokosh.app.data.UnlockOutcome
import org.zerokosh.app.quickunlock.QuickUnlockManager
import org.zerokosh.app.ui.common.Kicker
import org.zerokosh.app.ui.theme.Newsreader
import org.zerokosh.app.ui.theme.VaultColors
import org.zerokosh.app.ui.theme.VaultTheme
import org.zerokosh.core.vault.UnlockResult
// #endregion

// #region Palette
/**
 * This screen used to be charcoal in both themes, because the mockup drew it
 * that way. On a phone set to light that is the one screen that ignores the
 * choice, and it arrives unannounced at the moment the app is opened — so it
 * now takes the ordinary theme tokens like every other screen.
 *
 * Two things fall out of that. The error colour goes back to the active scheme
 * (BV-10 forced the dark one because the background was always dark; with the
 * background following the theme, so must the error). And the accent is now
 * the theme's, which in dark is the lighter #F0834E rather than the #BB4717
 * this screen was drawing on charcoal at about 3:1.
 */
private fun VaultColors.isDark(): Boolean = paper.luminance() < 0.5f
// #endregion

// #region Screen + unlock logic
/**
 * The clock behind the periodic "do you still remember it?" check.
 *
 * Only a typed passphrase resets it. A biometric unlock deliberately does not,
 * because unlocking without typing is exactly how the passphrase slips away
 * unnoticed — counting it would defeat the check entirely.
 */
private fun recordPassphraseUse(app: ZerokoshApp, recoveryMode: Boolean, outcome: UnlockOutcome) {
    if (!recoveryMode && outcome == UnlockOutcome.SUCCESS) {
        app.prefs.lastPassphraseUseMs = System.currentTimeMillis()
    }
}

// #region Naming the secret
// A vault opened with six digits should not be asked for a passphrase. Kept as
// four small functions rather than four `when`s inside the screen, which is
// already the largest function in the app and may not grow.

/** "…with your PIN once" or "…with your passphrase once". */
internal fun usePassphraseOnce(isPin: Boolean): Int =
    if (isPin) R.string.scr_lock_use_pin_once else R.string.scr_lock_use_passphrase_once

internal fun fieldLabel(recoveryMode: Boolean, isPin: Boolean): Int = when {
    recoveryMode -> R.string.scr_lock_recovery_hint
    isPin -> R.string.ob_pass_tab_pin
    else -> R.string.scr_lock_hint
}

internal fun wrongSecret(recoveryMode: Boolean, isPin: Boolean): Int = when {
    recoveryMode -> R.string.scr_lock_recovery_invalid
    isPin -> R.string.scr_lock_wrong_pin
    else -> R.string.scr_lock_wrong
}

/** Six digits deserve a digit keypad, not a full keyboard. */
internal fun keyboardFor(recoveryMode: Boolean, isPin: Boolean): KeyboardType = when {
    recoveryMode -> KeyboardType.Text
    isPin -> KeyboardType.NumberPassword
    else -> KeyboardType.Password
}
// #endregion

@Composable
fun LockScreen(app: ZerokoshApp) {
    val c = VaultTheme.colors
    // Ask for what the user actually set. Saying "passphrase" to someone whose
    // vault opens with six digits is the screen describing a different app.
    val isPin = app.prefs.secretIsPin
    val dark = c.isDark()
    var passphrase by remember { mutableStateOf("") }
    var wrong by remember { mutableStateOf(false) }
    var damagedRestored by remember { mutableStateOf(false) }
    var busy by remember { mutableStateOf(false) }
    // Saveable, unlike the two fields below: a rotation or a theme switch used
    // to throw the user back to the passphrase view mid-recovery. The secrets
    // themselves stay in plain remember deliberately — rememberSaveable writes
    // to the instance-state bundle, which is the last place a passphrase or a
    // recovery key should be.
    var recoveryMode by rememberSaveable { mutableStateOf(false) }
    var recoveryInput by remember { mutableStateOf("") }
    var recoveryInvalid by remember { mutableStateOf(false) }
    var cooldown by remember { mutableIntStateOf(app.repository.cooldownRemainingSeconds()) }
    val scope = rememberCoroutineScope()
    // BV-14: LocalContext can be a wrapper; LocalActivity resolves the host.
    val activity = LocalActivity.current as FragmentActivity
    val biometricReady = remember {
        app.prefs.quickUnlockEnabled && QuickUnlockManager.isEnrolled(activity) &&
            QuickUnlockManager.hardwareBackedBiometricsAvailable(activity)
    }
    var biometricFellBack by remember { mutableStateOf(false) }
    // Biometric-first, exactly as the mockup: the credential field only appears
    // when the sensor is unavailable or the user asks for it.
    var showCredential by rememberSaveable { mutableStateOf(!biometricReady) }

    // cooldown ticker
    LaunchedEffect(cooldown) {
        if (cooldown > 0) {
            delay(1000)
            cooldown = app.repository.cooldownRemainingSeconds()
        }
    }

    suspend fun tryBiometric() {
        when (val result = QuickUnlockManager.unlock(activity, app)) {
            is UnlockResult.Success -> app.repository.adoptBiometricUnlock(result)
            null -> {
                // key invalidated → passphrase once (§6.5)
                biometricFellBack = !QuickUnlockManager.isEnrolled(activity)
                if (biometricFellBack) showCredential = true
            }
            else -> {}
        }
    }

    // auto-fire biometric prompt on cold arrival (S13 → S7)
    LaunchedEffect(Unit) {
        if (biometricReady && cooldown == 0) tryBiometric()
    }

    fun handleOutcome(outcome: UnlockOutcome) {
        when (outcome) {
            UnlockOutcome.SUCCESS -> {}
            UnlockOutcome.WRONG_CREDENTIAL -> {
                wrong = !recoveryMode
                recoveryInvalid = recoveryMode
                cooldown = app.repository.cooldownRemainingSeconds()
            }
            UnlockOutcome.DAMAGED_RESTORED -> damagedRestored = true
            UnlockOutcome.DAMAGED -> {}
            UnlockOutcome.COOLDOWN -> cooldown = app.repository.cooldownRemainingSeconds()
        }
    }

    val submit = {
        busy = true
        wrong = false
        scope.launch {
            val outcome = if (recoveryMode) {
                app.repository.unlockWithRecoveryKey(recoveryInput.trim())
            } else {
                app.repository.unlockWithPassphrase(passphrase.toByteArray())
            }
            busy = false
            recordPassphraseUse(app, recoveryMode, outcome)
            handleOutcome(outcome)
        }
    }
    val canSubmit = !busy && cooldown == 0 &&
        (if (recoveryMode) recoveryInput.isNotBlank() else passphrase.isNotBlank())

    Box(Modifier.fillMaxSize().background(c.paper)) {
        // Two blurred glows, per the mockup.
        // Modifier.blur clips at the layer bounds by default, which drew these as
        // hard-edged rectangles instead of soft glows. Unbounded lets the blur
        // bleed past the box the way a CSS blur does.
        Box(
            Modifier
                .size(256.dp)
                .align(Alignment.TopStart)
                .offset(x = (-64).dp, y = (-80).dp)
                .blur(48.dp, BlurredEdgeTreatment.Unbounded)
                // A wash that reads as a glow on ink is a stain on paper.
                .background(c.primary.copy(alpha = if (dark) 0.25f else 0.14f), CircleShape),
        )
        Box(
            Modifier
                .size(288.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 48.dp, y = 96.dp)
                .blur(48.dp, BlurredEdgeTreatment.Unbounded)
                .background(c.accent.copy(alpha = if (dark) 0.20f else 0.11f), CircleShape),
        )

        Column(
            modifier = Modifier
                // BV-23: cap before filling (see Onboarding.kt).
                .widthIn(max = 560.dp)
                .fillMaxSize()
                .align(Alignment.Center)
                .systemBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 32.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(48.dp))
            Kicker(stringResource(R.string.kicker_locked), color = c.ink.copy(alpha = 0.6f))

            Spacer(Modifier.height(20.dp))
            Text(
                buildAnnotatedString {
                    append(stringResource(R.string.lk_welcome_lead) + "\n")
                    withStyle(
                        SpanStyle(
                            fontFamily = Newsreader,
                            fontStyle = FontStyle.Italic,
                            color = c.primary,
                        ),
                    ) { append(stringResource(R.string.lk_welcome_emph)) }
                },
                style = MaterialTheme.typography.displayMedium,
                color = c.ink,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                stringResource(R.string.lk_crypto_note),
                fontSize = 12.sp,
                color = c.ink.copy(alpha = 0.6f),
                textAlign = TextAlign.Center,
            )

            if (damagedRestored) {
                Spacer(Modifier.height(16.dp))
                Text(
                    stringResource(R.string.msg_file_damaged),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                )
            }
            if (biometricFellBack) {
                Spacer(Modifier.height(16.dp))
                Text(
                    stringResource(usePassphraseOnce(isPin)),
                    style = MaterialTheme.typography.bodyMedium,
                    color = c.ink.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                )
            }

            if (biometricReady && cooldown == 0 && !recoveryMode) {
                Spacer(Modifier.height(48.dp))
                SensorTarget(enabled = !busy) { scope.launch { tryBiometric() } }
                Spacer(Modifier.height(20.dp))
                Text(
                    stringResource(R.string.lk_touch_unlock),
                    fontSize = 12.sp,
                    color = c.ink.copy(alpha = 0.7f),
                )
            }

            if (showCredential || recoveryMode) {
                Spacer(Modifier.height(32.dp))
                var revealed by remember { mutableStateOf(false) }
                OutlinedTextField(
                    value = if (recoveryMode) recoveryInput else passphrase,
                    onValueChange = {
                        if (recoveryMode) {
                            recoveryInput = it; recoveryInvalid = false
                        } else {
                            passphrase = it; wrong = false
                        }
                    },
                    label = {
                        Text(
                            stringResource(fieldLabel(recoveryMode, isPin)),
                        )
                    },
                    visualTransformation =
                        if (recoveryMode || revealed) androidx.compose.ui.text.input.VisualTransformation.None
                        else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = keyboardFor(recoveryMode, isPin)),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = if (recoveryMode) recoveryInvalid else wrong,
                    supportingText = {
                        val err = if (recoveryMode) recoveryInvalid else wrong
                        if (err) {
                            Text(
                                stringResource(wrongSecret(recoveryMode, isPin)),
                                color = MaterialTheme.colorScheme.error,
                            )
                        }
                    },
                    enabled = !busy && cooldown == 0,
                    trailingIcon = if (busy) {
                        {
                            // Argon2id at 64 MB takes visible time on a cold
                            // start; the contained indicator gives that wait a
                            // home inside the field rather than a dead control.
                            ContainedLoadingIndicator(
                                modifier = Modifier.size(40.dp),
                                indicatorColor = c.ink,
                            )
                        }
                    } else if (recoveryMode) null else {
                        {
                            RevealToggle(
                                visible = revealed,
                                onToggle = { revealed = !revealed },
                                tint = c.ink.copy(alpha = 0.7f),
                            )
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = c.ink,
                        unfocusedTextColor = c.ink,
                        disabledTextColor = c.ink.copy(alpha = 0.4f),
                        cursorColor = c.primary,
                        focusedBorderColor = c.primary,
                        unfocusedBorderColor = c.ink.copy(alpha = 0.15f),
                        focusedLabelColor = c.primary,
                        unfocusedLabelColor = c.ink.copy(alpha = 0.55f),
                        focusedContainerColor = c.ink.copy(alpha = 0.06f),
                        unfocusedContainerColor = c.ink.copy(alpha = 0.06f),
                    ),
                )

                Spacer(Modifier.height(16.dp))
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (canSubmit) c.primary else c.ink.copy(alpha = 0.10f))
                        .clickable(enabled = canSubmit) { submit() },
                    contentAlignment = Alignment.Center,
                ) {
                    if (busy) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = c.ink,
                        )
                    } else {
                        Text(
                            stringResource(R.string.scr_lock_unlock),
                            style = MaterialTheme.typography.labelLarge,
                            color = if (canSubmit) MaterialTheme.colorScheme.onPrimary
                            else c.ink.copy(alpha = 0.5f),
                        )
                    }
                }
            }

            if (cooldown > 0) {
                Spacer(Modifier.height(16.dp))
                Text(
                    stringResource(R.string.scr_lock_cooldown, cooldown),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(Modifier.height(32.dp))
            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                if (!showCredential && !recoveryMode) {
                    LockOutlineButton(stringResource(usePassphraseOnce(isPin))) {
                        showCredential = true
                    }
                }
                // The label has to name where this button GOES, not where it has
                // been: it toggles the mode, so leaving it permanently reading
                // "Use Recovery Key" meant that once you were in recovery mode
                // nothing on screen said so, and the only way back was a button
                // claiming to do the thing you were already doing.
                LockOutlineButton(
                    stringResource(
                        if (recoveryMode) R.string.scr_lock_use_passphrase
                        else R.string.scr_lock_use_recovery,
                    ),
                ) {
                    recoveryMode = !recoveryMode
                    showCredential = true
                    wrong = false
                    recoveryInvalid = false
                    // Drop whatever was typed for the other mode. Without this a
                    // passphrase typed by mistake stays sitting in the recovery
                    // field, failing against a format it was never meant to match.
                    passphrase = ""
                    recoveryInput = ""
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun LockOutlineButton(label: String, onClick: () -> Unit) {
    val c = VaultTheme.colors
    Box(
        Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(c.ink.copy(alpha = 0.10f))
            .border(1.dp, c.ink.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = c.ink,
            textAlign = TextAlign.Center,
        )
    }
}
// #endregion

// #region Sensor target
/** The 128dp fingerprint target with the breathing glow behind it. */
@Composable
private fun SensorTarget(enabled: Boolean, onClick: () -> Unit) {
    val c = VaultTheme.colors
    val pulse by rememberInfiniteTransition(label = "sensor").animateFloat(
        initialValue = 0.94f,
        targetValue = 1.10f,
        animationSpec = infiniteRepeatable(tween(1600), RepeatMode.Reverse),
        label = "sensorPulse",
    )
    Box(contentAlignment = Alignment.Center) {
        Box(
            Modifier
                .size(128.dp)
                .scale(pulse)
                .blur(24.dp)
                .background(c.primary.copy(alpha = 0.30f), CircleShape),
        )
        Box(
            Modifier
                .size(128.dp)
                .clip(CircleShape)
                .background(c.ink.copy(alpha = 0.08f))
                .border(1.dp, c.ink.copy(alpha = 0.15f), CircleShape)
                .clickable(enabled = enabled, onClick = onClick),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                Icons.Outlined.Fingerprint,
                contentDescription = stringResource(R.string.scr_lock_biometric_title),
                tint = c.primary,
                modifier = Modifier.size(64.dp),
            )
        }
    }
}
// #endregion
