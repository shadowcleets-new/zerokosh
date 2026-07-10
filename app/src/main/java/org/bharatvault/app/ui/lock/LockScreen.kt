package org.bharatvault.app.ui.lock

// #region Imports
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.bharatvault.app.BharatVaultApp
import org.bharatvault.app.R
import org.bharatvault.app.data.UnlockOutcome
import org.bharatvault.app.quickunlock.QuickUnlockManager
import org.bharatvault.core.vault.UnlockResult
// #endregion

/** S13 (§5.1): passphrase/PIN + biometric prompt; 5 fails → 30 s cooldown doubling; NEVER wipes. */
@Composable
fun LockScreen(app: BharatVaultApp) {
    var passphrase by remember { mutableStateOf("") }
    var wrong by remember { mutableStateOf(false) }
    var damagedRestored by remember { mutableStateOf(false) }
    var busy by remember { mutableStateOf(false) }
    var recoveryMode by remember { mutableStateOf(false) }
    var recoveryInput by remember { mutableStateOf("") }
    var recoveryInvalid by remember { mutableStateOf(false) }
    var cooldown by remember { mutableIntStateOf(app.repository.cooldownRemainingSeconds()) }
    val scope = rememberCoroutineScope()
    val activity = LocalContext.current as FragmentActivity
    val biometricReady = remember {
        app.prefs.quickUnlockEnabled && QuickUnlockManager.isEnrolled(activity) &&
            QuickUnlockManager.hardwareBackedBiometricsAvailable(activity)
    }
    var biometricFellBack by remember { mutableStateOf(false) }

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
            null -> biometricFellBack = !QuickUnlockManager.isEnrolled(activity) // key invalidated → passphrase once (§6.5)
            else -> {}
        }
    }

    // auto-fire biometric prompt on cold arrival (S13 → S6)
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

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(Icons.Outlined.Lock, null, modifier = Modifier.size(72.dp), tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(16.dp))
        Text(stringResource(R.string.scr_lock_title), style = MaterialTheme.typography.displaySmall)
        Spacer(Modifier.height(24.dp))

        if (damagedRestored) {
            Text(
                stringResource(R.string.msg_file_damaged),
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(8.dp))
        }
        if (biometricFellBack) {
            Text(
                stringResource(R.string.scr_lock_use_passphrase_once),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(8.dp))
        }

        if (!recoveryMode) {
            OutlinedTextField(
                value = passphrase,
                onValueChange = { passphrase = it; wrong = false },
                label = { Text(stringResource(R.string.scr_lock_hint)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = wrong,
                supportingText = {
                    if (wrong) Text(stringResource(R.string.scr_lock_wrong), color = MaterialTheme.colorScheme.error)
                },
                enabled = !busy && cooldown == 0,
            )
        } else {
            OutlinedTextField(
                value = recoveryInput,
                onValueChange = { recoveryInput = it.uppercase(); recoveryInvalid = false },
                label = { Text(stringResource(R.string.scr_lock_recovery_hint)) },
                modifier = Modifier.fillMaxWidth(),
                isError = recoveryInvalid,
                supportingText = {
                    if (recoveryInvalid) Text(stringResource(R.string.scr_lock_recovery_invalid), color = MaterialTheme.colorScheme.error)
                },
                enabled = !busy && cooldown == 0,
            )
        }

        Spacer(Modifier.height(16.dp))
        if (cooldown > 0) {
            Text(
                stringResource(R.string.scr_lock_cooldown, cooldown),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(Modifier.height(8.dp))
        }

        Button(
            enabled = !busy && cooldown == 0 && (if (recoveryMode) recoveryInput.isNotBlank() else passphrase.isNotEmpty()),
            onClick = {
                busy = true
                scope.launch {
                    val outcome = if (recoveryMode) {
                        app.repository.unlockWithRecoveryKey(recoveryInput)
                    } else {
                        val bytes = passphrase.toByteArray()
                        app.repository.unlockWithPassphrase(bytes).also { bytes.fill(0) }
                    }
                    busy = false
                    handleOutcome(outcome)
                }
            },
            modifier = Modifier.fillMaxWidth().height(52.dp),
        ) {
            if (busy) CircularProgressIndicator(modifier = Modifier.size(20.dp), color = MaterialTheme.colorScheme.onPrimary)
            else Text(stringResource(R.string.scr_lock_unlock))
        }

        if (biometricReady) {
            Spacer(Modifier.height(8.dp))
            IconButton(onClick = { scope.launch { tryBiometric() } }, enabled = cooldown == 0) {
                Icon(
                    Icons.Outlined.Fingerprint,
                    contentDescription = stringResource(R.string.scr_lock_biometric_title),
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }

        TextButton(onClick = { recoveryMode = !recoveryMode; wrong = false; recoveryInvalid = false }) {
            Text(
                if (recoveryMode) stringResource(R.string.scr_lock_hint)
                else stringResource(R.string.scr_lock_use_recovery),
            )
        }
    }
}
