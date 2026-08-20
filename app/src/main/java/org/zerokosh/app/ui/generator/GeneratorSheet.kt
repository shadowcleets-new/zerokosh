package org.zerokosh.app.ui.generator

// #region Imports
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.R
import org.zerokosh.app.ui.record.DropdownSelector
import org.zerokosh.app.ui.theme.SecretTextStyle
import org.zerokosh.core.generator.PasswordGenerator
// #endregion

/**
 * §5.8 generator: length 8–64 (default 16), class toggles, PIN mode (4/6),
 * bank preset dropdown from bank_rules.json. Randomness = libsodium only.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneratorSheet(app: ZerokoshApp, onUse: (String) -> Unit, onDismiss: () -> Unit) {
    var length by remember { mutableFloatStateOf(16f) }
    var upper by remember { mutableStateOf(true) }
    var lower by remember { mutableStateOf(true) }
    var digits by remember { mutableStateOf(true) }
    var symbols by remember { mutableStateOf(true) }
    var pinMode by remember { mutableStateOf(false) }
    var pinLength by remember { mutableStateOf(6) }
    var bankPreset by remember { mutableStateOf<String?>(null) }

    fun generate(): String {
        val crypto = app.repository.crypto
        val rule = app.catalog.bankRules.rules.firstOrNull { it.bank == bankPreset }
        return when {
            pinMode -> PasswordGenerator.generatePin(pinLength, crypto)
            rule != null -> PasswordGenerator.generateForRule(rule, length.toInt(), crypto)
            else -> PasswordGenerator.generate(
                length = length.toInt(), upper = upper, lower = lower,
                digits = digits, symbols = symbols, crypto = crypto,
            )
        }
    }

    var preview by remember { mutableStateOf(generate()) }

    // rememberBottomSheetState is the unified replacement for the deprecated
    // rememberModalBottomSheetState / rememberStandardBottomSheetState pair.
    val sheetState = rememberBottomSheetState(initialValue = SheetValue.Hidden)
    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        Column(modifier = Modifier.padding(24.dp)) {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                Text(
                    preview,
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    style = SecretTextStyle,
                    textAlign = TextAlign.Center,
                )
            }
            Spacer(Modifier.height(16.dp))

            Row {
                FilterChip(selected = !pinMode, onClick = { pinMode = false; preview = generate() }, label = { Text("Aa1@") })
                Spacer(Modifier.padding(4.dp))
                FilterChip(selected = pinMode, onClick = { pinMode = true; preview = generate() }, label = { Text("PIN") })
            }

            if (pinMode) {
                Row(modifier = Modifier.padding(vertical = 8.dp)) {
                    FilterChip(selected = pinLength == 4, onClick = { pinLength = 4; preview = generate() }, label = { Text("4") })
                    Spacer(Modifier.padding(4.dp))
                    FilterChip(selected = pinLength == 6, onClick = { pinLength = 6; preview = generate() }, label = { Text("6") })
                }
            } else {
                Text("${length.toInt()}", style = MaterialTheme.typography.labelMedium)
                Slider(
                    value = length,
                    onValueChange = { length = it },
                    onValueChangeFinished = { preview = generate() },
                    valueRange = 8f..64f,
                )
                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    FilterChip(selected = upper, onClick = { upper = !upper; preview = generate() }, label = { Text("ABC") })
                    Spacer(Modifier.padding(2.dp))
                    FilterChip(selected = lower, onClick = { lower = !lower; preview = generate() }, label = { Text("abc") })
                    Spacer(Modifier.padding(2.dp))
                    FilterChip(selected = digits, onClick = { digits = !digits; preview = generate() }, label = { Text("123") })
                    Spacer(Modifier.padding(2.dp))
                    FilterChip(selected = symbols, onClick = { symbols = !symbols; preview = generate() }, label = { Text("@#$") })
                }
                Spacer(Modifier.height(8.dp))
                DropdownSelector(
                    options = listOf("—") + app.catalog.bankRules.rules.map { it.bank },
                    selected = bankPreset ?: "—",
                    onSelect = { picked ->
                        bankPreset = picked.takeIf { it != "—" }
                        preview = generate()
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Spacer(Modifier.height(16.dp))
            Row {
                OutlinedButton(
                    onClick = { preview = generate() },
                    modifier = Modifier.weight(1f).height(48.dp),
                ) { Text(stringResource(R.string.scr_edit_generate)) }
                Spacer(Modifier.padding(6.dp))
                Button(
                    onClick = { onUse(preview) },
                    modifier = Modifier.weight(1f).height(48.dp),
                ) { Text(stringResource(R.string.msg_ok)) }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}
