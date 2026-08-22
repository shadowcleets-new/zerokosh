/**
 * @file ZerokoshNav.kt
 * @description Root navigation: state-driven top switch (NoVault → onboarding,
 *              Locked → S13, Unlocked → main tabs), per §5.1 flow
 *              S1→S2→S3→S4→S5→S6 first run, S13→S6 thereafter. The main
 *              scaffold carries the mockup's four-tab M3 bottom navigation
 *              (Vault · Codes · Templates · Settings) with a separate extended
 *              FAB floating above it.
 *
 * [TABLE OF CONTENTS]
 * 1. IMPORTS & DEPENDENCIES
 * 2. ROOT SWITCH
 * 3. ONBOARDING FLOW (S1–S6)
 * 4. MAIN SCAFFOLD (dock + FAB + inner routes)
 * 5. DAMAGED SCREEN
 */
@file:OptIn(androidx.compose.material3.ExperimentalMaterial3ExpressiveApi::class)

package org.zerokosh.app.ui

// #region Imports
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import org.zerokosh.app.reminders.ReminderWorker
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.QrCode2
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.animateFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.material3.ToggleFloatingActionButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.data.VaultState
import org.zerokosh.app.ui.authenticator.AuthenticatorScreen
import org.zerokosh.app.ui.common.EmphasisSpan
import org.zerokosh.app.ui.common.GroupCard
import org.zerokosh.app.ui.common.Kicker
import org.zerokosh.app.ui.common.NoticeCard
import org.zerokosh.app.ui.common.NoticeTone
import org.zerokosh.app.ui.common.RevealAuth
import org.zerokosh.app.ui.common.VaultExtendedFab
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.platform.LocalConfiguration
import org.zerokosh.app.ui.common.VaultNavRail
import org.zerokosh.app.ui.common.VaultNavBar
import org.zerokosh.app.ui.common.VaultTab
import org.zerokosh.app.ui.gallery.TemplateGalleryScreen
import org.zerokosh.app.ui.home.HomeScreen
import org.zerokosh.app.ui.lock.LockScreen
import org.zerokosh.app.ui.motion.LocalAnimatedVisibilityScope
import org.zerokosh.app.ui.motion.LocalSharedTransitionScope
import org.zerokosh.app.ui.motion.globalEnterTransition
import org.zerokosh.app.ui.motion.globalExitTransition
import org.zerokosh.app.ui.motion.globalPopEnterTransition
import org.zerokosh.app.ui.motion.globalPopExitTransition
import org.zerokosh.app.ui.onboarding.CreatePassphraseScreen
import org.zerokosh.app.ui.onboarding.LanguageScreen
import org.zerokosh.app.ui.onboarding.OnboardingState
import org.zerokosh.app.ui.onboarding.QuickUnlockScreen
import org.zerokosh.app.ui.onboarding.RecoveryKitScreen
import org.zerokosh.app.ui.onboarding.TrustScreen
import org.zerokosh.app.ui.onboarding.WelcomeScreen
import org.zerokosh.app.ui.record.RecordDetailScreen
import org.zerokosh.app.ui.record.RecordEditScreen
import org.zerokosh.app.ui.settings.SettingsScreen
import org.zerokosh.app.ui.theme.VaultTheme
// #endregion

// #region Root switch
@Composable
fun ZerokoshNav(app: ZerokoshApp) {
    val state by app.repository.state.collectAsState()
    when (state) {
        VaultState.NoVault -> OnboardingFlow(app)
        VaultState.Locked -> {
            RevealAuth.reset()
            LockScreen(app)
        }
        VaultState.Unlocked -> MainScaffold(app)
        VaultState.Damaged -> DamagedScreen()
    }
}
// #endregion

// #region Onboarding flow (S1→S6)
@Composable
private fun OnboardingFlow(app: ZerokoshApp) {
    val nav = rememberNavController()
    val onboarding = remember { OnboardingState() }
    NavHost(
        navController = nav,
        startDestination = "welcome",
        enterTransition = { globalEnterTransition() },
        exitTransition = { globalExitTransition() },
        popEnterTransition = { globalPopEnterTransition() },
        popExitTransition = { globalPopExitTransition() },
    ) {
        composable("welcome") {
            WelcomeScreen(
                app = app,
                onGetStarted = { nav.navigate("language") },
                onLogin = { nav.navigate("language") }, // NoVault: both paths proceed
            )
        }
        composable("language") {
            LanguageScreen(app, onBack = { nav.popBackStack() }) { nav.navigate("trust") }
        }
        composable("trust") {
            TrustScreen(onBack = { nav.popBackStack() }) { nav.navigate("create") }
        }
        composable("create") {
            CreatePassphraseScreen(app, onboarding, onBack = { nav.popBackStack() }) {
                nav.navigate("recovery")
            }
        }
        composable("recovery") {
            RecoveryKitScreen(app, onboarding) { nav.navigate("quickunlock") }
        }
        composable("quickunlock") {
            QuickUnlockScreen(app, onboarding) {
                app.prefs.onboardingDone = true
                // repository is already Unlocked → root switch lands on Home (S7)
            }
        }
    }
}
// #endregion

// #region Main scaffold
/**
 * Shortcuts on the Add FAB menu, in the order they appear bottom-up. Ids must
 * exist in templates.json; TemplateCatalogTest guards that they cannot be
 * renamed away underneath us.
 */
private val QuickAdd = listOf(
    Triple("login", "Login", Icons.Outlined.Password),
    Triple("card", "Card", Icons.Outlined.CreditCard),
    Triple("upi", "UPI", Icons.Outlined.QrCode2),
    Triple("bank_account", "Bank account", Icons.Outlined.AccountBalance),
)

private val TabRoutes = mapOf(
    VaultTab.Vault to "home",
    VaultTab.Codes to "authenticator",
    VaultTab.Templates to "gallery",
    VaultTab.Settings to "settings",
)

/** Date fields ReminderWorker auto-suggests a 30-day reminder for (§5.7). */
private val ReminderDateFields =
    listOf("expiry", "premium_due_date", "membership_renewal", "renewal_date")

@Composable
private fun MainScaffold(app: ZerokoshApp) {
    val nav: NavHostController = rememberNavController()
    val backStack by nav.currentBackStackEntryAsState()

    // BV-02: the vault is only readable while it is open, so this is the one
    // moment reminders can be evaluated. Ask for POST_NOTIFICATIONS only if the
    // vault actually holds something with a date worth reminding about.
    val context = LocalContext.current
    val body by app.repository.body.collectAsState()
    val notificationPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { /* the reminder check runs either way; a denial just silences it */ }
    LaunchedEffect(body === null) {
        ReminderWorker.runNow(context)
        val hasDatedRecord = body?.records.orEmpty().any { record ->
            record.reminders.isNotEmpty() ||
                ReminderDateFields.any { record.fields[it]?.isNotBlank() == true }
        }
        if (hasDatedRecord && !app.prefs.notificationAsked &&
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            app.prefs.notificationAsked = true
            notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    val currentRoute = backStack?.destination?.route
    val activeTab = TabRoutes.entries.firstOrNull { it.value == currentRoute }?.key
    val openGallery = {
        nav.navigate("gallery") {
            popUpTo("home") { saveState = true }
            launchSingleTop = true
        }
    }
    // The one place the edit route is built. Encoded because "Punjab & Sind
    // Bank" and "L&T Finance" would otherwise split the query string and arrive
    // truncated to "Punjab ".
    val openEdit = { templateId: String, presetName: String?, brand: String? ->
        val p = android.net.Uri.encode(presetName ?: "")
        val b = android.net.Uri.encode(brand ?: "")
        nav.navigate("edit/$templateId?preset=$p&brand=$b") { popUpTo("home") }
    }
    val openTemplate = { templateId: String -> openEdit(templateId, null, null) }
    // The Add FAB used to be a one-way trip to the 20-item template gallery.
    // The Expressive FAB menu puts the four templates that cover most additions
    // one tap away and keeps the gallery as the escape hatch.
    var fabMenuExpanded by rememberSaveable { mutableStateOf(false) }
    var fabVisible by remember { mutableStateOf(true) }
    BackHandler(fabMenuExpanded) { fabMenuExpanded = false }
    // Leaving the Vault tab must not strand an open menu offscreen.
    LaunchedEffect(activeTab) { if (activeTab != VaultTab.Vault) fabMenuExpanded = false }

    val selectTab = { tab: VaultTab ->
        nav.navigate(TabRoutes.getValue(tab)) {
            popUpTo("home") { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }
    // M3's medium breakpoint: at 600dp and up a bottom bar wastes the vertical
    // space that is already scarce in landscape, so the destinations move to a
    // side rail instead.
    val wideWindow = LocalConfiguration.current.screenWidthDp >= 600

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (activeTab != null && !wideWindow) {
                VaultNavBar(active = activeTab, onSelect = selectTab)
            }
        },
        floatingActionButton = {
            // The mockup floats an extended FAB above the dock on the two list
            // screens; Templates and Settings carry their own actions.
            when (activeTab) {
                VaultTab.Vault -> FloatingActionButtonMenu(
                    expanded = fabMenuExpanded,
                    button = {
                        ToggleFloatingActionButton(
                            checked = fabMenuExpanded,
                            onCheckedChange = { fabMenuExpanded = it },
                            // Never hide the button while its own menu is open.
                            modifier = Modifier.animateFloatingActionButton(
                                visible = fabVisible || fabMenuExpanded,
                                alignment = Alignment.BottomEnd,
                            ),
                            containerColor = ToggleFloatingActionButtonDefaults.containerColor(
                                initialColor = VaultTheme.colors.primary,
                                finalColor = VaultTheme.colors.ink,
                            ),
                        ) {
                            val icon by remember {
                                derivedStateOf {
                                    if (checkedProgress > 0.5f) Icons.Filled.Close else Icons.Filled.Add
                                }
                            }
                            Icon(
                                painter = rememberVectorPainter(icon),
                                contentDescription = if (fabMenuExpanded) "Close menu" else "Add",
                                // animateIcon tints through a ColorFilter, which
                                // beats Icon(tint=) -- the colour has to go here
                                // or the glyph keeps the M3 default and vanishes
                                // against our overridden container.
                                modifier = with(ToggleFloatingActionButtonDefaults) {
                                    Modifier.animateIcon(
                                        checkedProgress = { checkedProgress },
                                        color = ToggleFloatingActionButtonDefaults.iconColor(
                                            initialColor = VaultTheme.colors.paper,
                                            finalColor = VaultTheme.colors.paper,
                                        ),
                                    )
                                },
                            )
                        }
                    },
                ) {
                    QuickAdd.forEach { (templateId, label, icon) ->
                        FloatingActionButtonMenuItem(
                            onClick = {
                                fabMenuExpanded = false
                                openTemplate(templateId)
                            },
                            icon = { Icon(icon, contentDescription = null) },
                            text = { Text(label) },
                        )
                    }
                    FloatingActionButtonMenuItem(
                        onClick = {
                            fabMenuExpanded = false
                            openGallery()
                        },
                        icon = { Icon(Icons.Outlined.GridView, contentDescription = null) },
                        text = { Text("All templates") },
                    )
                }
                VaultTab.Codes -> VaultExtendedFab(
                    label = "Scan",
                    onClick = openGallery,
                    icon = Icons.Outlined.QrCodeScanner,
                )
                else -> Unit
            }
        },
    ) { padding ->
        Row(Modifier.fillMaxSize()) {
            if (activeTab != null && wideWindow) {
                VaultNavRail(active = activeTab, onSelect = selectTab)
            }
            SharedTransitionLayout {
                CompositionLocalProvider(LocalSharedTransitionScope provides this@SharedTransitionLayout) {
                    NavHost(
                        navController = nav,
                        startDestination = "home",
                        modifier = Modifier.padding(bottom = padding.calculateBottomPadding()),
                        enterTransition = { globalEnterTransition() },
                        exitTransition = { globalExitTransition() },
                        popEnterTransition = { globalPopEnterTransition() },
                        popExitTransition = { globalPopExitTransition() },
                    ) {
                        composable("home") {
                            CompositionLocalProvider(LocalAnimatedVisibilityScope provides this@composable) {
                                HomeScreen(
                                    app = app,
                                    onScrollHideFab = { hide -> fabVisible = !hide },
                                    onOpen = { uuid -> nav.navigate("detail/$uuid") },
                                    onAdd = openGallery,
                                    onQuickAdd = openEdit,
                                )
                            }
                        }
                        composable("authenticator") { AuthenticatorScreen(app) }
                        composable("settings") { SettingsScreen(app) }
                        composable("gallery") {
                            TemplateGalleryScreen(
                                app = app,
                                onPick = openEdit,
                                onClose = {
                                    nav.navigate("home") {
                                        popUpTo("home") { inclusive = true }
                                        launchSingleTop = true
                                    }
                                },
                            )
                        }
                        composable("detail/{uuid}") { entry ->
                            val uuid = entry.arguments?.getString("uuid") ?: return@composable
                            CompositionLocalProvider(LocalAnimatedVisibilityScope provides this@composable) {
                                RecordDetailScreen(
                                    app = app,
                                    uuid = uuid,
                                    onEdit = { nav.navigate("edit/byid?uuid=$uuid") },
                                    onOpenRecord = { linked -> nav.navigate("detail/$linked") },
                                    onClose = { nav.popBackStack() },
                                )
                            }
                        }
                        composable("edit/{templateId}?uuid={uuid}&preset={preset}&brand={brand}") { entry ->
                            val templateId = entry.arguments?.getString("templateId") ?: return@composable
                            val uuid = entry.arguments?.getString("uuid")?.ifEmpty { null }
                            // Uri.decode whether or not navigation already did:
                            // these names carry no literal %, so decoding a
                            // plain string is a no-op.
                            val preset = entry.arguments?.getString("preset")
                                ?.ifEmpty { null }?.let(android.net.Uri::decode)
                            val brand = entry.arguments?.getString("brand")
                                ?.ifEmpty { null }?.let(android.net.Uri::decode)
                            RecordEditScreen(
                                app = app,
                                templateIdArg = templateId,
                                editUuid = uuid,
                                presetName = preset,
                                brandName = brand,
                                onDone = { nav.popBackStack() },
                            )
                        }
                    }
                }
            }
        }
    }
}
// #endregion

// #region Damaged screen (§11.2 clean failure path)
@Composable
private fun DamagedScreen() {
    val c = VaultTheme.colors
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(c.paper)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 28.dp, vertical = 24.dp),
    ) {
        Kicker("Damaged state")
        Spacer(Modifier.height(12.dp))
        Text(
            buildAnnotatedString {
                append("Something in the ")
                withStyle(EmphasisSpan.copy(color = c.ink)) { append("vault file") }
                append(" is off.")
            },
            style = MaterialTheme.typography.displayMedium,
            color = c.ink,
        )

        Spacer(Modifier.height(24.dp))
        NoticeCard(
            title = "Integrity check failed",
            body = "The .kosh file's Poly1305 authentication tag doesn't match. This can " +
                "happen after an interrupted sync or a bad flash.",
            icon = Icons.Outlined.WarningAmber,
            tone = NoticeTone.Warn,
        )

        Spacer(Modifier.height(24.dp))
        GroupCard {
            Column(Modifier.padding(16.dp)) {
                Text(
                    "What to do next",
                    style = MaterialTheme.typography.titleSmall,
                    color = c.ink,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "Zerokosh keeps a rolling backup beside the vault file. Restore it " +
                        "from your sync folder, or reopen the vault with your Recovery Kit " +
                        "on another device.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = c.ink(0.65f),
                )
            }
        }
    }
}
// #endregion
