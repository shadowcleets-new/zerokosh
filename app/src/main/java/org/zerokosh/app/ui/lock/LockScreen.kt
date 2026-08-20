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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
import org.zerokosh.app.ui.theme.VaultAccent
import org.zerokosh.app.ui.theme.VaultDock
import org.zerokosh.app.ui.theme.VaultErrorDark
import org.zerokosh.app.ui.theme.VaultPaper
import org.zerokosh.app.ui.theme.VaultPrimary
import org.zerokosh.core.vault.UnlockResult
// #endregion

// #region Fixed dark palette
// The mockup draws the lock screen on ink in both themes, so this screen uses
// the literal tokens rather than the theme-flipping ones.
private val LockSurface = VaultDock
private val LockOnSurface = VaultPaper

/**
 * BV-10: this screen is charcoal in both themes, but it was pulling the error
 * colour from the active scheme — which in light theme is #D40C1A, about 3.6:1
 * against #120C09. That is the "wrong passphrase" message, so it takes the
 * dark-theme error unconditionally.
 */
private val LockError = VaultErrorDark
// #endregion

// #region Screen + unlock logic
@Composable
fun LockScreen(app: ZerokoshApp) {
    var passphrase by remember { mutableStateOf("") }
    var wrong by remember { mutableStateOf(false) }
    var damagedRestored by remember { mutableStateOf(false) }
    var busy by remember { mutableStateOf(false) }
    var recoveryMode by remember { mutableStateOf(false) }
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
    var showCredential by remember { mutableStateOf(!biometricReady) }

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
            handleOutcome(outcome)
        }
    }
    val canSubmit = !busy && cooldown == 0 &&
        (if (recoveryMode) recoveryInput.isNotBlank() else passphrase.isNotBlank())

    Box(Modifier.fillMaxSize().background(LockSurface)) {
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
                .background(VaultPrimary.copy(alpha = 0.25f), CircleShape),
        )
        Box(
            Modifier
                .size(288.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 48.dp, y = 96.dp)
                .blur(48.dp, BlurredEdgeTreatment.Unbounded)
                .background(VaultAccent.copy(alpha = 0.20f), CircleShape),
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
            Kicker("Locked", color = LockOnSurface.copy(alpha = 0.6f))

            Spacer(Modifier.height(20.dp))
            Text(
                buildAnnotatedString {
                    append("Welcome back.\n")
                    withStyle(
                        SpanStyle(
                            fontFamily = Newsreader,
                            fontStyle = FontStyle.Italic,
                            color = VaultPrimary,
                        ),
                    ) { append("Your vault is sealed.") }
                },
                style = MaterialTheme.typography.displayMedium,
                color = LockOnSurface,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Argon2id · XChaCha20-Poly1305 · nothing left this device",
                fontSize = 12.sp,
                color = LockOnSurface.copy(alpha = 0.6f),
                textAlign = TextAlign.Center,
            )

            if (damagedRestored) {
                Spacer(Modifier.height(16.dp))
                Text(
                    stringResource(R.string.msg_file_damaged),
                    color = LockError,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                )
            }
            if (biometricFellBack) {
                Spacer(Modifier.height(16.dp))
                Text(
                    stringResource(R.string.scr_lock_use_passphrase_once),
                    style = MaterialTheme.typography.bodyMedium,
                    color = LockOnSurface.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                )
            }

            if (biometricReady && cooldown == 0 && !recoveryMode) {
                Spacer(Modifier.height(48.dp))
                SensorTarget(enabled = !busy) { scope.launch { tryBiometric() } }
                Spacer(Modifier.height(20.dp))
                Text(
                    "Touch to unlock",
                    fontSize = 12.sp,
                    color = LockOnSurface.copy(alpha = 0.7f),
                )
            }

            if (showCredential || recoveryMode) {
                Spacer(Modifier.height(32.dp))
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
                            stringResource(
                                if (recoveryMode) R.string.scr_lock_recovery_hint
                                else R.string.scr_lock_hint,
                            ),
                        )
                    },
                    visualTransformation = if (recoveryMode) androidx.compose.ui.text.input.VisualTransformation.None
                    else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = if (recoveryMode) KeyboardType.Text else KeyboardType.Password,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = if (recoveryMode) recoveryInvalid else wrong,
                    supportingText = {
                        val err = if (recoveryMode) recoveryInvalid else wrong
                        if (err) {
                            Text(
                                stringResource(
                                    if (recoveryMode) R.string.scr_lock_recovery_invalid
                                    else R.string.scr_lock_wrong,
                                ),
                                color = LockError,
                            )
                        }
                    },
                    enabled = !busy && cooldown == 0,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = LockOnSurface,
                        unfocusedTextColor = LockOnSurface,
                        disabledTextColor = LockOnSurface.copy(alpha = 0.4f),
                        cursorColor = VaultPrimary,
                        focusedBorderColor = VaultPrimary,
                        unfocusedBorderColor = LockOnSurface.copy(alpha = 0.15f),
                        focusedLabelColor = VaultPrimary,
                        unfocusedLabelColor = LockOnSurface.copy(alpha = 0.55f),
                        focusedContainerColor = LockOnSurface.copy(alpha = 0.06f),
                        unfocusedContainerColor = LockOnSurface.copy(alpha = 0.06f),
                    ),
                )

                Spacer(Modifier.height(16.dp))
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (canSubmit) VaultPrimary else LockOnSurface.copy(alpha = 0.10f))
                        .clickable(enabled = canSubmit) { submit() },
                    contentAlignment = Alignment.Center,
                ) {
                    if (busy) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = LockOnSurface,
                        )
                    } else {
                        Text(
                            stringResource(R.string.scr_lock_unlock),
                            style = MaterialTheme.typography.labelLarge,
                            color = if (canSubmit) VaultPaper else LockOnSurface.copy(alpha = 0.5f),
                        )
                    }
                }
            }

            if (cooldown > 0) {
                Spacer(Modifier.height(16.dp))
                Text(
                    stringResource(R.string.scr_lock_cooldown, cooldown),
                    style = MaterialTheme.typography.bodyMedium,
                    color = LockError,
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(Modifier.height(32.dp))
            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                if (!showCredential && !recoveryMode) {
                    DarkOutlineButton(stringResource(R.string.scr_lock_use_passphrase_once)) {
                        showCredential = true
                    }
                }
                DarkOutlineButton(stringResource(R.string.scr_lock_use_recovery)) {
                    recoveryMode = !recoveryMode
                    showCredential = true
                    wrong = false
                    recoveryInvalid = false
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun DarkOutlineButton(label: String, onClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(LockOnSurface.copy(alpha = 0.10f))
            .border(1.dp, LockOnSurface.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = LockOnSurface,
            textAlign = TextAlign.Center,
        )
    }
}
// #endregion

// #region Sensor target
/** The 128dp fingerprint target with the breathing glow behind it. */
@Composable
private fun SensorTarget(enabled: Boolean, onClick: () -> Unit) {
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
                .background(VaultPrimary.copy(alpha = 0.30f), CircleShape),
        )
        Box(
            Modifier
                .size(128.dp)
                .clip(CircleShape)
                .background(LockOnSurface.copy(alpha = 0.08f))
                .border(1.dp, LockOnSurface.copy(alpha = 0.15f), CircleShape)
                .clickable(enabled = enabled, onClick = onClick),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                Icons.Outlined.Fingerprint,
                contentDescription = stringResource(R.string.scr_lock_biometric_title),
                tint = VaultPrimary,
                modifier = Modifier.size(64.dp),
            )
        }
    }
}
// #endregion
