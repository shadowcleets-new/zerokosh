/**
 * @file AuthenticatorScreen.kt
 * @description S11 (§5.1): all TOTP fields across the vault — live codes,
 *              countdown, tap-to-copy; "Add via QR" opens camera scanner
 *              for otpauth:// (the ONLY camera use permitted, §0.6/§5.8).
 *
 * [TABLE OF CONTENTS]
 * 1. TOTP ENTRY LIST
 * 2. LIVE CODE ROW
 * 3. QR SCANNER HOST
 */
package org.bharatvault.app.ui.authenticator

// #region Imports
import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.bharatvault.app.BharatVaultApp
import org.bharatvault.app.R
import org.bharatvault.app.ui.common.ClipboardHelper
import org.bharatvault.core.model.FieldType
import org.bharatvault.core.model.Record
import org.bharatvault.core.totp.Totp
import java.util.UUID
import java.util.concurrent.atomic.AtomicBoolean
// #endregion

// #region TOTP entry list
data class TotpEntry(
    val recordUuid: String,
    val recordTitle: String,
    val fieldId: String,
    val params: Totp.Params,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticatorScreen(app: BharatVaultApp) {
    val body by app.repository.body.collectAsState()
    val context = LocalContext.current
    var showScanner by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val entries = remember(body) {
        body?.records.orEmpty().flatMap { record ->
            record.fields.mapNotNull { (k, v) ->
                val field = app.catalog.templates.byId(record.template_id)?.fields?.firstOrNull { it.k == k }
                if (field?.type != FieldType.TOTP || v.isBlank()) return@mapNotNull null
                val params = Totp.parseOtpauthUri(v)
                    ?: Totp.Params(secretBase32 = v.trim(), label = record.title)
                TotpEntry(record.uuid, record.title, k, params)
            }
        }
    }

    if (showScanner) {
        QrScannerScreen(
            onCancel = { showScanner = false },
            onSecret = { secretOrUri ->
                showScanner = false
                scope.launch {
                    val params = Totp.parseOtpauthUri(secretOrUri)
                        ?: Totp.Params(secretBase32 = secretOrUri.trim())
                    val now = System.currentTimeMillis()
                    val title = listOf(params.issuer, params.label)
                        .filter { it.isNotBlank() }
                        .distinct()
                        .joinToString(" · ")
                        .ifBlank { "Authenticator" }
                    val record = Record(
                        uuid = UUID.randomUUID().toString(),
                        template_id = "login",
                        title = title,
                        fields = mapOf(
                            "totp" to (if (secretOrUri.startsWith("otpauth://", true)) secretOrUri else params.secretBase32),
                        ),
                        created_at = now,
                        modified_at = now,
                        device_id = app.prefs.deviceId,
                    )
                    app.repository.upsertRecord(record)
                    Toast.makeText(context, context.getString(R.string.msg_saved), Toast.LENGTH_SHORT).show()
                }
            },
        )
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.scr_home_tab_authenticator)) },
                actions = {
                    IconButton(onClick = { showScanner = true }) {
                        Icon(
                            Icons.Outlined.QrCodeScanner,
                            contentDescription = stringResource(R.string.scr_auth_add_qr),
                        )
                    }
                },
            )
        },
    ) { padding ->
        if (entries.isEmpty()) {
            Box(
                Modifier.fillMaxSize().padding(padding).padding(32.dp),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        stringResource(R.string.scr_auth_empty),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = { showScanner = true }) {
                        Text(stringResource(R.string.scr_auth_add_qr))
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(entries, key = { "${it.recordUuid}:${it.fieldId}" }) { entry ->
                    TotpLiveRow(entry)
                }
            }
        }
    }
}
// #endregion

// #region Live code row
@Composable
private fun TotpLiveRow(entry: TotpEntry) {
    val context = LocalContext.current
    var now by remember { mutableLongStateOf(System.currentTimeMillis()) }
    LaunchedEffect(Unit) {
        while (true) {
            now = System.currentTimeMillis()
            delay(500)
        }
    }
    val code = remember(now, entry.params) { Totp.code(entry.params, now) }
    val remaining = Totp.secondsRemaining(entry.params, now)
    val progress = remaining.toFloat() / entry.params.periodSeconds
    val display = if (code.length == 6) "${code.substring(0, 3)} ${code.substring(3)}" else code

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                ClipboardHelper.copySensitive(context, code)
                Toast.makeText(context, context.getString(R.string.scr_detail_copied), Toast.LENGTH_SHORT).show()
            }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            Text(entry.recordTitle, style = MaterialTheme.typography.titleMedium)
            Text(
                display,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                ),
            )
        }
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(48.dp)) {
            CircularProgressIndicator(progress = { progress }, modifier = Modifier.size(40.dp), strokeWidth = 3.dp)
            Text("$remaining", style = MaterialTheme.typography.labelSmall)
        }
        IconButton(onClick = {
            ClipboardHelper.copySensitive(context, code)
            Toast.makeText(context, context.getString(R.string.scr_detail_copied), Toast.LENGTH_SHORT).show()
        }) {
            Icon(Icons.Outlined.ContentCopy, contentDescription = stringResource(R.string.scr_detail_copy))
        }
    }
}
// #endregion

// #region QR scanner host (§5.8 — CameraX + ZXing only)
@Composable
fun QrScannerScreen(onCancel: () -> Unit, onSecret: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val hasPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
        PackageManager.PERMISSION_GRANTED
    var granted by remember { mutableStateOf(hasPermission) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted = it }
    LaunchedEffect(Unit) {
        if (!granted) launcher.launch(Manifest.permission.CAMERA)
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text(stringResource(R.string.scr_auth_scan_title), style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        if (!granted) {
            Text(stringResource(R.string.scr_auth_camera_denied), style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(16.dp))
            Button(onClick = onCancel) { Text(stringResource(R.string.msg_cancel)) }
            return
        }
        val handled = remember { AtomicBoolean(false) }
        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx)
                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                cameraProviderFuture.addListener({
                    val provider = cameraProviderFuture.get()
                    val preview = Preview.Builder().build().also {
                        it.surfaceProvider = previewView.surfaceProvider
                    }
                    val analysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                    analysis.setAnalyzer(ContextCompat.getMainExecutor(ctx)) { imageProxy ->
                        if (handled.get()) {
                            imageProxy.close()
                            return@setAnalyzer
                        }
                        try {
                            val buffer = imageProxy.planes[0].buffer
                            val data = ByteArray(buffer.remaining())
                            buffer.get(data)
                            val source = PlanarYUVLuminanceSource(
                                data, imageProxy.width, imageProxy.height,
                                0, 0, imageProxy.width, imageProxy.height, false,
                            )
                            val bitmap = BinaryBitmap(HybridBinarizer(source))
                            val result = MultiFormatReader().decodeWithState(bitmap)
                            val text = result.text
                            if (text.startsWith("otpauth://", ignoreCase = true) ||
                                text.matches(Regex("^[A-Z2-7=]+$", RegexOption.IGNORE_CASE))
                            ) {
                                if (handled.compareAndSet(false, true)) onSecret(text)
                            }
                        } catch (_: Exception) {
                            // no QR in this frame
                        } finally {
                            imageProxy.close()
                        }
                    }
                    provider.unbindAll()
                    provider.bindToLifecycle(
                        lifecycleOwner,
                        CameraSelector.DEFAULT_BACK_CAMERA,
                        preview,
                        analysis,
                    )
                }, ContextCompat.getMainExecutor(ctx))
                previewView
            },
            modifier = Modifier.fillMaxWidth().weight(1f),
        )
        Spacer(Modifier.height(8.dp))
        Button(onClick = onCancel, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.msg_cancel))
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            runCatching {
                ProcessCameraProvider.getInstance(context).get().unbindAll()
            }
        }
    }
}
// #endregion
