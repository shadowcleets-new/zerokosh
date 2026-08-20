package org.zerokosh.app.ui.record

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Password Generator Screen using Material 3 Expressive design.
 * Features:
 * - Display Medium bold typography for the password.
 * - Asymmetrical rounded corners for sliders.
 * - Dynamic color logic based on password strength (Error to Brand Primary).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordGeneratorScreen(
    onPasswordAccepted: (String) -> Unit,
    onCancel: () -> Unit
) {
    var length by remember { mutableStateOf(16f) }
    var useUppercase by remember { mutableStateOf(true) }
    var useNumbers by remember { mutableStateOf(true) }
    var useSymbols by remember { mutableStateOf(true) }
    
    // State holding the generated password
    var generatedPassword by remember { mutableStateOf("") }
    
    // Entropy/Strength calculation logic (simplified for UI demonstration)
    val score = calculateStrengthScore(length.toInt(), useUppercase, useNumbers, useSymbols)
    
    // Dynamic color shifting from Error (Weak) -> Warning (Medium) -> Primary (Strong)
    val containerColor by animateColorAsState(
        targetValue = when (score) {
            in 0..40 -> MaterialTheme.colorScheme.errorContainer
            in 41..70 -> Color(0xFFFFD54F) // Warning-ish Amber
            else -> MaterialTheme.colorScheme.primaryContainer
        },
        animationSpec = spring(),
        label = "container_color"
    )
    
    val contentColor by animateColorAsState(
        targetValue = when (score) {
            in 0..40 -> MaterialTheme.colorScheme.onErrorContainer
            in 41..70 -> Color(0xFF3E2723)
            else -> MaterialTheme.colorScheme.onPrimaryContainer
        },
        animationSpec = spring(),
        label = "content_color"
    )
    
    // Generate immediately when params change
    LaunchedEffect(length, useUppercase, useNumbers, useSymbols) {
        generatedPassword = generateRandomPassword(length.toInt(), useUppercase, useNumbers, useSymbols)
    }

    Scaffold(
        topBar = {
            // The flexible bar carries a subtitle slot, which lets the live
            // character count sit under the title instead of competing with the
            // generated string below it.
            MediumFlexibleTopAppBar(
                title = { Text("Generate Password") },
                subtitle = { Text("${length.toInt()} characters") },
                navigationIcon = {
                    TextButton(onClick = onCancel) { Text("Cancel") }
                },
                actions = {
                    TextButton(onClick = { onPasswordAccepted(generatedPassword) }) {
                        Text("Use", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Expressive Password Display Container
            Card(
                colors = CardDefaults.cardColors(containerColor = containerColor, contentColor = contentColor),
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 16.dp, bottomEnd = 40.dp, bottomStart = 16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = generatedPassword,
                            style = MaterialTheme.typography.displayMedium,
                            fontFamily = FontFamily.Monospace,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Spacer(Modifier.height(16.dp))
                        IconButton(onClick = { generatedPassword = generateRandomPassword(length.toInt(), useUppercase, useNumbers, useSymbols) }) {
                            Icon(Icons.Filled.Refresh, contentDescription = "Regenerate")
                        }
                    }
                }
            }

            // Interactive Controls with Asymmetrical Styling
            Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                // Length Slider
                Column {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Length", style = MaterialTheme.typography.titleMedium)
                        Text("${length.toInt()}", style = MaterialTheme.typography.titleMedium, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                    }
                    Slider(
                        value = length,
                        onValueChange = { length = it },
                        valueRange = 8f..32f,
                        steps = 24,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(
                                MaterialTheme.colorScheme.surfaceVariant,
                                RoundedCornerShape(topStart = 24.dp, bottomEnd = 24.dp, topEnd = 4.dp, bottomStart = 4.dp)
                            )
                            .padding(horizontal = 16.dp)
                    )
                }

                // Toggles
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Uppercase (A-Z)", style = MaterialTheme.typography.bodyLarge)
                    Switch(checked = useUppercase, onCheckedChange = { useUppercase = it })
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Numbers (0-9)", style = MaterialTheme.typography.bodyLarge)
                    Switch(checked = useNumbers, onCheckedChange = { useNumbers = it })
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Symbols (!@#)", style = MaterialTheme.typography.bodyLarge)
                    Switch(checked = useSymbols, onCheckedChange = { useSymbols = it })
                }
            }
        }
    }
}

// Helper methods for password generation
private fun calculateStrengthScore(length: Int, upper: Boolean, num: Boolean, sym: Boolean): Int {
    var pool = 26
    if (upper) pool += 26
    if (num) pool += 10
    if (sym) pool += 15 // approx
    
    val entropy = length * (Math.log(pool.toDouble()) / Math.log(2.0))
    return when {
        entropy < 40 -> 30
        entropy < 60 -> 60
        else -> 100
    }
}

private fun generateRandomPassword(length: Int, upper: Boolean, num: Boolean, sym: Boolean): String {
    val lowerChars = "abcdefghijklmnopqrstuvwxyz"
    val upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val numChars = "0123456789"
    val symChars = "!@#\$%^&*()-_=+[]{}|;:,.<>?"
    
    var pool = lowerChars
    if (upper) pool += upperChars
    if (num) pool += numChars
    if (sym) pool += symChars
    
    if (pool.isEmpty()) return ""
    
    return (1..length)
        .map { pool.random() }
        .joinToString("")
}
