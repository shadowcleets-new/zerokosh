/**
 * @file TapCardSheet.kt
 * @description The "hold your card to the phone" sheet. Opens reader mode while
 *              visible and releases it the moment it closes, so taps meant for
 *              the user's payment app are not swallowed.
 *
 *              States it has to handle, because a card read fails in ordinary
 *              ways: no NFC hardware, NFC switched off, a card that reads but
 *              carries no PAN, and a card yanked away mid-read.
 */
@file:OptIn(
    androidx.compose.material3.ExperimentalMaterial3Api::class,
    androidx.compose.material3.ExperimentalMaterial3ExpressiveApi::class,
)

package org.zerokosh.app.ui.record

// #region Imports
import android.nfc.Tag
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Contactless
import androidx.compose.material3.Icon
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.zerokosh.app.R
import org.zerokosh.app.nfc.CardNfcReader
import org.zerokosh.app.nfc.NfcReaderSession
import org.zerokosh.app.ui.common.PrimaryPillButton
import org.zerokosh.app.ui.theme.VaultTheme
import org.zerokosh.core.emv.EmvCard
// #endregion

private sealed interface TapState {
    data object Waiting : TapState
    data object Reading : TapState
    data class Failed(val message: String) : TapState
}

@Composable
fun TapCardSheet(onCard: (EmvCard) -> Unit, onDismiss: () -> Unit) {
    val activity = LocalActivity.current ?: return
    val scope = rememberCoroutineScope()
    val c = VaultTheme.colors
    val session = remember(activity) { NfcReaderSession(activity) }
    var state by remember { mutableStateOf<TapState>(TapState.Waiting) }

    val unavailable = when {
        !session.isAvailable -> stringResource(R.string.nfc_no_hardware)
        !session.isEnabled -> stringResource(R.string.nfc_disabled)
        else -> null
    }

    // Read in composition: DisposableEffect runs outside it.
    val readFailed = stringResource(R.string.nfc_read_failed)

    // Reader mode is bound to the sheet's lifetime, not the screen's.
    DisposableEffect(session, unavailable) {
        if (unavailable == null) {
            session.start { tag: Tag ->
                state = TapState.Reading
                scope.launch {
                    CardNfcReader.read(tag)
                        .onSuccess { card ->
                            session.stop()
                            onCard(card)
                        }
                        .onFailure { e ->
                            state = TapState.Failed(
                                e.message ?: readFailed,
                            )
                        }
                }
            }
        }
        onDispose { session.stop() }
    }

    val sheetState = rememberBottomSheetState(initialValue = SheetValue.Hidden)
    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 28.dp).padding(bottom = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Spacer(Modifier.height(8.dp))
            if (state is TapState.Reading) {
                LoadingIndicator(modifier = Modifier.size(56.dp), color = c.primary)
            } else {
                Icon(
                    Icons.Outlined.Contactless,
                    contentDescription = null,
                    tint = if (unavailable != null || state is TapState.Failed) c.mute else c.primary,
                    modifier = Modifier.size(56.dp),
                )
            }

            Text(
                text = when {
                    unavailable != null -> stringResource(R.string.nfc_cannot_read)
                    state is TapState.Reading -> stringResource(R.string.nfc_reading)
                    state is TapState.Failed -> stringResource(R.string.nfc_missed)
                    else -> stringResource(R.string.nfc_hold_card)
                },
                style = MaterialTheme.typography.titleMedium,
                color = c.ink,
                textAlign = TextAlign.Center,
            )

            Text(
                text = unavailable
                    ?: (state as? TapState.Failed)?.message
                    ?: stringResource(R.string.nfc_hint),
                style = MaterialTheme.typography.bodySmall,
                color = c.mute,
                textAlign = TextAlign.Center,
            )

            if (state is TapState.Failed) {
                Spacer(Modifier.height(4.dp))
                PrimaryPillButton(
                    label = stringResource(R.string.nfc_try_again),
                    onClick = { state = TapState.Waiting },
                    showArrow = false,
                )
            }
        }
    }
}
