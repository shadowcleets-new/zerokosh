/**
 * @file AuthenticatorScreen.kt
 * @description S11 (§5.1): all TOTP fields across the vault — live codes,
 *              countdown, tap-to-copy; "Add via QR" opens full CameraX scanner
 *              for otpauth:// & raw Base32 keys, image picker, manual entry, & torch.
 *              Crash-proof camera provider lifecycle and error handling.
 *
 * [TABLE OF CONTENTS]
 * 1. TOTP ENTRY LIST & SCREEN
 * 2. LIVE CODE ROW
 * 3. QR SCANNER & MANUAL ENTRY DIALOG
 */
@file:OptIn(androidx.compose.material3.ExperimentalMaterial3ExpressiveApi::class)

package org.zerokosh.app.ui.authenticator

// #region Imports
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FlashOff
import androidx.compose.material.icons.outlined.FlashOn
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.R
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import org.zerokosh.app.ui.common.BrandTile
import org.zerokosh.app.ui.common.EmphasisSpan
import org.zerokosh.app.ui.common.NoticeTone
import org.zerokosh.app.ui.common.SearchDock
import org.zerokosh.app.ui.common.StatusPill
import org.zerokosh.app.ui.theme.CornerCard
import org.zerokosh.app.ui.theme.JetBrainsMono
import org.zerokosh.app.ui.theme.TotpTextStyle
import org.zerokosh.app.ui.theme.VaultTheme
import org.zerokosh.app.ui.common.ClipboardHelper
import org.zerokosh.core.model.FieldType
import org.zerokosh.core.model.Record
import org.zerokosh.core.totp.Totp
import java.util.EnumMap
import java.util.UUID
import java.util.concurrent.atomic.AtomicBoolean
// #endregion

// #region TOTP entry list & Screen
data class TotpEntry(
    val recordUuid: String,
    val recordTitle: String,
    val fieldId: String,
    val params: Totp.Params,
)

/** "1 active code" / "7 active codes". */
@Composable
private fun activeCodeCount(count: Int): String = stringResource(
    if (count == 1) R.string.au_active_one else R.string.au_active_many,
    count,
)

@Composable
fun AuthenticatorScreen(app: ZerokoshApp) {
    val c = VaultTheme.colors
    val body by app.repository.body.collectAsState()
    val context = LocalContext.current
    var showScanner by remember { mutableStateOf(false) }
    var showManualDialog by remember { mutableStateOf(false) }
    // BV-11: survive rotation.
    var query by rememberSaveable { mutableStateOf("") }
    var searching by rememberSaveable { mutableStateOf(false) }
    // BV-14: resolve the string in composition, not through LocalContext in a
    // callback — that form doesn't re-read on a locale change.
    val savedMessage = stringResource(R.string.msg_saved)
    val scope = rememberCoroutineScope()

    val entries = remember(body) {
        body?.records.orEmpty().flatMap { record ->
            record.fields.mapNotNull { (k, v) ->
                if (v.isBlank()) return@mapNotNull null
                val field = app.catalog.templates.byId(record.template_id)?.fields?.firstOrNull { it.k == k }
                val isTotp = field?.type == FieldType.TOTP || k.equals("totp", ignoreCase = true) || v.startsWith("otpauth://", ignoreCase = true)
                if (!isTotp) return@mapNotNull null
                val params = Totp.parseOtpauthUri(v)
                    ?: Totp.Params(secretBase32 = v.trim(), label = record.title)
                TotpEntry(record.uuid, record.title, k, params)
            }
        }
    }
    val shown = remember(entries, query) {
        if (query.isBlank()) entries
        else entries.filter {
            it.recordTitle.contains(query, true) ||
                it.params.label.contains(query, true) ||
                it.params.issuer.contains(query, true)
        }
    }

    // Read in composition: onSaveSecret is a plain lambda, not a composable.
    val fallbackName = stringResource(R.string.au_fallback_name)
    val onSaveSecret: (String, String?) -> Unit = { secretOrUri, customTitle ->
        val params = Totp.parseOtpauthUri(secretOrUri)
            ?: Totp.Params(secretBase32 = secretOrUri.trim())
        val now = System.currentTimeMillis()
        val title = customTitle?.takeIf { it.isNotBlank() }
            ?: listOf(params.issuer, params.label)
                .filter { it.isNotBlank() }
                .distinct()
                .joinToString(" · ")
                .ifBlank { fallbackName }

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
        scope.launch {
            app.repository.upsertRecord(record)
            Toast.makeText(context, savedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    if (showScanner) {
        QrScannerScreen(
            onCancel = { showScanner = false },
            onSecret = { secretOrUri ->
                showScanner = false
                onSaveSecret(secretOrUri, null)
            },
            onManualClick = {
                showScanner = false
                showManualDialog = true
            },
        )
        return
    }

    if (showManualDialog) {
        ManualTotpDialog(
            onDismiss = { showManualDialog = false },
            onSave = { secret, title ->
                showManualDialog = false
                onSaveSecret(secret, title)
            },
        )
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(c.paper)
            .statusBarsPadding()
            .imePadding(),
    ) {
        Box(Modifier.padding(horizontal = 16.dp).padding(top = 12.dp, bottom = 8.dp)) {
            if (searching) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(CircleShape)
                        .background(c.surface)
                        .border(1.dp, c.line, CircleShape)
                        .padding(start = 16.dp, end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Icon(
                        Icons.Outlined.Search,
                        contentDescription = null,
                        tint = c.ink(0.5f),
                        modifier = Modifier.size(20.dp),
                    )
                    BasicTextField(
                        value = query,
                        onValueChange = { query = it },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp, color = c.ink),
                        cursorBrush = SolidColor(c.primary),
                    )
                    Box(
                        Modifier.size(40.dp).clip(CircleShape).clickable {
                            searching = false; query = ""
                        },
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            Icons.Outlined.Close,
                            contentDescription = stringResource(R.string.hm_close_search),
                            tint = c.ink(0.55f),
                            modifier = Modifier.size(20.dp),
                        )
                    }
                }
            } else {
                SearchDock(
                    placeholder = stringResource(R.string.au_search_hint),
                    initials = "ZK",
                    onClick = { searching = true },
                )
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 132.dp),
        ) {
            item {
                Column(Modifier.padding(top = 12.dp)) {
                    Text(
                        buildAnnotatedString {
                            append(stringResource(R.string.au_rotating))
                            withStyle(EmphasisSpan) {
                                append(stringResource(R.string.au_rotating_emph))
                            }
                        },
                        style = MaterialTheme.typography.displaySmall,
                        color = c.ink,
                    )
                    Spacer(Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        StatusPill(
                            text = activeCodeCount(entries.size),
                            tone = NoticeTone.Positive,
                            showDot = true,
                        )
                        StatusPill(stringResource(R.string.au_tap_to_copy))
                    }
                    Spacer(Modifier.height(20.dp))
                }
            }

            if (shown.isEmpty()) {
                item { EmptyCodes(onScan = { showScanner = true }, hasQuery = query.isNotBlank()) }
            } else {
                itemsIndexed(shown, key = { _, it -> "${it.recordUuid}_${it.fieldId}" }) { i, entry ->
                    TotpCard(entry = entry, accented = i % 2 == 1)
                    Spacer(Modifier.height(12.dp))
                }
                item {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(c.card)
                            .border(1.dp, c.line, RoundedCornerShape(16.dp))
                            .clickable { showScanner = true }
                            .padding(vertical = 14.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            Icons.Outlined.QrCodeScanner,
                            contentDescription = null,
                            tint = c.primary,
                            modifier = Modifier.size(16.dp),
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            stringResource(R.string.au_add_another),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = c.ink,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyCodes(onScan: () -> Unit, hasQuery: Boolean) {
    val c = VaultTheme.colors
    Column(
        Modifier.fillMaxWidth().padding(vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(22.dp))
                .background(c.primary.copy(alpha = 0.10f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                Icons.Outlined.QrCodeScanner,
                contentDescription = null,
                tint = c.primary,
                modifier = Modifier.size(30.dp),
            )
        }
        Spacer(Modifier.height(20.dp))
        Text(
            stringResource(if (hasQuery) R.string.au_no_match else R.string.au_none_yet),
            style = MaterialTheme.typography.headlineSmall,
            color = c.ink,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            stringResource(R.string.au_empty_hint),
            style = MaterialTheme.typography.bodySmall,
            color = c.mute,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(max = 260.dp),
        )
        Spacer(Modifier.height(24.dp))
        Row(
            Modifier
                .height(44.dp)
                .clip(CircleShape)
                .background(c.ink)
                .clickable(onClick = onScan)
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                stringResource(R.string.au_scan_qr),
                style = MaterialTheme.typography.titleSmall,
                color = c.paper,
            )
        }
    }
}
// #endregion

// #region Live code card
/**
 * The mockup's TOTP card: brand tile, issuer, a 40dp countdown ring, and the
 * six digits sitting in a tinted well with a copy affordance. Cards alternate
 * between the burnt-orange and teal accents down the list.
 */
@Composable
private fun TotpCard(entry: TotpEntry, accented: Boolean) {
    val c = VaultTheme.colors
    val context = LocalContext.current
    var secondsLeft by remember { mutableIntStateOf(Totp.secondsRemaining(entry.params, System.currentTimeMillis())) }
    var code by remember { mutableStateOf(Totp.code(entry.params, System.currentTimeMillis())) }

    LaunchedEffect(entry.params) {
        while (true) {
            val now = System.currentTimeMillis()
            secondsLeft = Totp.secondsRemaining(entry.params, now)
            val freshCode = Totp.code(entry.params, now)
            if (freshCode != code) code = freshCode
            delay(1000)
        }
    }

    val tone = if (accented) c.accent else c.primary
    val urgent = secondsLeft <= 8
    val ringColor = if (urgent) c.primary else tone
    val period = entry.params.periodSeconds.coerceAtLeast(1)
    val fraction = secondsLeft.toFloat() / period.toFloat()
    val shape = RoundedCornerShape(CornerCard)
    val meta = listOf(entry.params.issuer, entry.params.label)
        .filter { it.isNotBlank() && it != entry.recordTitle }
        .distinct()
        .joinToString(" · ")

    Column(
        Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(c.card)
            .border(1.dp, c.ink(0.05f), shape)
            .clickable {
                if (code.isNotBlank()) {
                    ClipboardHelper.copySensitive(context, code)
                    Toast.makeText(context, context.getString(R.string.au_copied), Toast.LENGTH_SHORT).show()
                }
            }
            .padding(20.dp),
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
            BrandTile(code = entry.params.issuer.ifBlank { entry.recordTitle })
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    entry.recordTitle,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = c.ink,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (meta.isNotBlank()) {
                    Spacer(Modifier.height(2.dp))
                    Text(
                        meta,
                        fontSize = 11.sp,
                        color = c.mute,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            Spacer(Modifier.width(12.dp))
            // Was a hand-drawn Canvas with two drawArc calls. The Expressive
            // circular wavy indicator animates its own amplitude, so the
            // countdown reads as live rather than as a static swept arc.
            Box(Modifier.size(48.dp), contentAlignment = Alignment.Center) {
                // No size override: the wavy indicator draws its wave against
                // its own 48dp container, and squeezing it scallops the ring
                // into a blob that no longer reads as a countdown.
                CircularWavyProgressIndicator(
                    progress = { fraction },
                    color = ringColor,
                    trackColor = c.line,
                    // Full amplitude is tuned for large indicators; on a 48dp
                    // ring it reads as a squiggle rather than a countdown.
                    amplitude = { 0.45f },
                )
                Text(
                    "${secondsLeft}s",
                    fontFamily = JetBrainsMono,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ringColor,
                )
            }
        }

        Spacer(Modifier.height(16.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(tone.copy(alpha = 0.08f))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = if (code.length == 6) "${code.take(3)} ${code.drop(3)}" else code,
                style = TotpTextStyle,
                color = tone,
            )
            Box(
                Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(c.card)
                    .padding(horizontal = 10.dp, vertical = 4.dp),
            ) {
                Text(
                    stringResource(R.string.scr_detail_copy),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = c.accent,
                )
            }
        }
    }
}
// #endregion

// #region QR scanner host
@Composable
fun QrScannerScreen(
    onCancel: () -> Unit,
    onSecret: (String) -> Unit,
    onManualClick: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val hasPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
        PackageManager.PERMISSION_GRANTED
    var granted by remember { mutableStateOf(hasPermission) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted = it }

    var camera by remember { mutableStateOf<Camera?>(null) }
    var torchOn by remember { mutableStateOf(false) }

    val photoPickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()
                if (bitmap != null) {
                    val width = bitmap.width
                    val height = bitmap.height
                    val pixels = IntArray(width * height)
                    bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
                    val source = RGBLuminanceSource(width, height, pixels)
                    val binaryBitmap = BinaryBitmap(HybridBinarizer(source))

                    val reader = MultiFormatReader().apply {
                        val hints = EnumMap<DecodeHintType, Any>(DecodeHintType::class.java)
                        hints[DecodeHintType.POSSIBLE_FORMATS] = listOf(BarcodeFormat.QR_CODE)
                        setHints(hints)
                    }

                    val result = runCatching { reader.decodeWithState(binaryBitmap) }.getOrNull()
                        ?: runCatching { reader.decodeWithState(BinaryBitmap(HybridBinarizer(source.rotateCounterClockwise()))) }.getOrNull()

                    if (result != null && (result.text.startsWith("otpauth://", ignoreCase = true) || result.text.matches(Regex("^[A-Z2-7=]+$", RegexOption.IGNORE_CASE)))) {
                        onSecret(result.text)
                    } else {
                        Toast.makeText(context, context.getString(R.string.au_no_qr_found), Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, context.getString(R.string.au_image_failed), Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(Unit) {
        if (!granted) launcher.launch(Manifest.permission.CAMERA)
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        if (granted) {
            val handled = remember { AtomicBoolean(false) }
            val reader = remember {
                MultiFormatReader().apply {
                    val hints = EnumMap<DecodeHintType, Any>(DecodeHintType::class.java)
                    hints[DecodeHintType.POSSIBLE_FORMATS] = listOf(BarcodeFormat.QR_CODE)
                    setHints(hints)
                }
            }

            AndroidView(
                factory = { ctx ->
                    val previewView = PreviewView(ctx)
                    runCatching {
                        val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                        cameraProviderFuture.addListener({
                            runCatching {
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
                                        val w = imageProxy.width
                                        val h = imageProxy.height
                                        val source = PlanarYUVLuminanceSource(data, w, h, 0, 0, w, h, false)

                                        var result = runCatching { reader.decodeWithState(BinaryBitmap(HybridBinarizer(source))) }.getOrNull()
                                        if (result == null) {
                                            result = runCatching { reader.decodeWithState(BinaryBitmap(HybridBinarizer(source.rotateCounterClockwise()))) }.getOrNull()
                                        }

                                        if (result != null) {
                                            val text = result.text
                                            if (text.startsWith("otpauth://", ignoreCase = true) ||
                                                text.matches(Regex("^[A-Z2-7=]+$", RegexOption.IGNORE_CASE))
                                            ) {
                                                if (handled.compareAndSet(false, true)) {
                                                    onSecret(text)
                                                }
                                            }
                                        }
                                    } catch (_: Exception) {
                                    } finally {
                                        imageProxy.close()
                                    }
                                }

                                provider.unbindAll()
                                val selector = if (provider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA)) {
                                    CameraSelector.DEFAULT_BACK_CAMERA
                                } else if (provider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA)) {
                                    CameraSelector.DEFAULT_FRONT_CAMERA
                                } else null

                                if (selector != null && lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
                                    val cam = provider.bindToLifecycle(
                                        lifecycleOwner,
                                        selector,
                                        preview,
                                        analysis,
                                    )
                                    camera = cam
                                }
                            }
                        }, ContextCompat.getMainExecutor(ctx))
                    }
                    previewView
                },
                modifier = Modifier.fillMaxSize(),
            )

            // Scanning Overlay Reticle
            Box(
                modifier = Modifier
                    .size(260.dp)
                    .align(Alignment.Center)
                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(24.dp))
            )
        } else {
            // Permission fallback message
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.au_camera_needed),
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(16.dp))
                Button(onClick = { launcher.launch(Manifest.permission.CAMERA) }) {
                    Text(stringResource(R.string.au_grant))
                }
            }
        }

        // Top Control Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onCancel,
                modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape)
            ) {
                Icon(
                    Icons.Outlined.Close,
                    contentDescription = stringResource(R.string.au_close),
                    tint = Color.White,
                )
            }

            Text(
                text = stringResource(R.string.au_scan_title),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            IconButton(
                onClick = {
                    runCatching {
                        torchOn = !torchOn
                        camera?.cameraControl?.enableTorch(torchOn)
                    }
                },
                modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape)
            ) {
                Icon(
                    if (torchOn) Icons.Outlined.FlashOn else Icons.Outlined.FlashOff,
                    contentDescription = stringResource(R.string.au_flashlight),
                    tint = if (torchOn) Color.Yellow else Color.White
                )
            }
        }

        // Bottom Action Bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = { photoPickerLauncher.launch("image/*") },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(50)
                ) {
                    Icon(Icons.Outlined.PhotoLibrary, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.au_pick_image), color = MaterialTheme.colorScheme.onSurface)
                }

                Button(
                    onClick = onManualClick,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(50)
                ) {
                    Icon(Icons.Outlined.Edit, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.au_enter_key), color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            runCatching {
                val provider = ProcessCameraProvider.getInstance(context)
                if (provider.isDone) {
                    provider.get().unbindAll()
                }
            }
        }
    }
}
// #endregion

// #region Manual Key Entry Dialog
@Composable
private fun ManualTotpDialog(
    onDismiss: () -> Unit,
    onSave: (secret: String, title: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var secret by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val dialogFallbackName = stringResource(R.string.au_fallback_name)
    val invalidSecretMessage = stringResource(R.string.au_invalid_secret)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.au_add_secret), fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(stringResource(R.string.au_account_name)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = secret,
                    onValueChange = {
                        secret = it.uppercase()
                        error = null
                    },
                    label = { Text(stringResource(R.string.au_secret_label)) },
                    singleLine = true,
                    isError = error != null,
                    supportingText = {
                        if (error != null) {
                            Text(error!!, color = MaterialTheme.colorScheme.error)
                        } else {
                            Text(
                                stringResource(R.string.au_secret_example),
                                style = MaterialTheme.typography.labelSmall,
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                enabled = secret.isNotBlank(),
                onClick = {
                    val cleanSecret = secret.replace(" ", "").uppercase()
                    if (cleanSecret.matches(Regex("^[A-Z2-7=]+$"))) {
                        onSave(cleanSecret, title.ifBlank { dialogFallbackName })
                    } else {
                        error = invalidSecretMessage
                    }
                }
            ) {
                Text(stringResource(R.string.au_save_key))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.msg_cancel))
            }
        }
    )
}
// #endregion
