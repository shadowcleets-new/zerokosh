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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.activity.compose.LocalActivity
import kotlinx.coroutines.launch
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
        !session.isAvailable -> "This phone has no NFC, so it cannot read a card."
        !session.isEnabled -> "NFC is switched off. Turn it on in Settings, then try again."
        else -> null
    }

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
                                e.message ?: "That card could not be read. Try again.",
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
                    unavailable != null -> "Cannot read cards"
                    state is TapState.Reading -> "Reading…"
                    state is TapState.Failed -> "Did not catch that"
                    else -> "Hold your card to the phone"
                },
                style = MaterialTheme.typography.titleMedium,
                color = c.ink,
                textAlign = TextAlign.Center,
            )

            Text(
                text = unavailable
                    ?: (state as? TapState.Failed)?.message
                    ?: "Rest the card flat against the back of the phone until it reads. " +
                    "This picks up the card number, expiry and name — the CVV is not on the chip, " +
                    "so you will still type that yourself.",
                style = MaterialTheme.typography.bodySmall,
                color = c.mute,
                textAlign = TextAlign.Center,
            )

            if (state is TapState.Failed) {
                Spacer(Modifier.height(4.dp))
                PrimaryPillButton(
                    label = "Try again",
                    onClick = { state = TapState.Waiting },
                    showArrow = false,
                )
            }
        }
    }
}
