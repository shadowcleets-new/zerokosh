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
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.outlined.Contactless
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.QrCode2
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.material3.ToggleFloatingActionButtonDefaults
import androidx.compose.material3.animateFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.window.core.layout.WindowSizeClass
import androidx.compose.animation.togetherWith
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import androidx.navigation3.ui.NavDisplay
import org.zerokosh.app.MainActivity
import org.zerokosh.app.R
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.autofill.PendingSave
import org.zerokosh.app.autofill.loginRecordFor
import org.zerokosh.app.data.VaultState
import org.zerokosh.app.reminders.ReminderWorker
import org.zerokosh.app.ui.authenticator.AuthenticatorScreen
import org.zerokosh.app.ui.common.EmphasisSpan
import org.zerokosh.app.ui.common.GroupCard
import org.zerokosh.app.ui.common.Kicker
import org.zerokosh.app.ui.common.NoticeCard
import org.zerokosh.app.ui.common.NoticeTone
import org.zerokosh.app.ui.common.RevealAuth
import org.zerokosh.app.ui.common.VaultExtendedFab
import org.zerokosh.app.ui.common.VaultNavBar
import org.zerokosh.app.ui.common.VaultNavRail
import org.zerokosh.app.ui.common.VaultTab
import org.zerokosh.app.ui.gallery.TemplateGalleryScreen
import org.zerokosh.app.ui.health.VaultHealthScreen
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
import org.zerokosh.app.ui.record.TapCardSheet
import org.zerokosh.app.ui.settings.SettingsScreen
import org.zerokosh.app.ui.theme.VaultTheme
import org.zerokosh.app.ui.trash.TrashScreen
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
        VaultState.Unlocked -> {
            // A credential Android asked us to save while the vault was shut.
            // Finishing it here is what makes "save to Zerokosh" mean anything
            // when the app was locked at the time — which is most of the time.
            LaunchedEffect(Unit) {
                PendingSave.take()?.let { credential ->
                    app.repository.upsertRecord(loginRecordFor(app, credential))
                }
            }
            MainScaffold(app)
        }
        VaultState.Damaged -> DamagedScreen()
    }
}
// #endregion

// #region Onboarding flow (S1→S6)
@Composable
private fun OnboardingFlow(app: ZerokoshApp) {
    val backStack = rememberNavBackStack(WelcomeRoute)
    val onboarding = remember { OnboardingState() }
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        transitionSpec = { globalEnterTransition() togetherWith globalExitTransition() },
        popTransitionSpec = { globalPopEnterTransition() togetherWith globalPopExitTransition() },
        entryProvider = entryProvider {
            entry<WelcomeRoute> {
                WelcomeScreen(
                    app = app,
                    onGetStarted = { backStack.add(LanguageRoute) },
                    onLogin = { backStack.add(LanguageRoute) }, // NoVault: both paths proceed
                )
            }
            entry<LanguageRoute> {
                LanguageScreen(app, onBack = { backStack.removeLastOrNull() }) {
                    backStack.add(TrustRoute)
                }
            }
            entry<TrustRoute> {
                TrustScreen(onBack = { backStack.removeLastOrNull() }) {
                    backStack.add(CreateVaultRoute)
                }
            }
            entry<CreateVaultRoute> {
                CreatePassphraseScreen(app, onboarding, onBack = { backStack.removeLastOrNull() }) {
                    backStack.add(RecoveryKitRoute)
                }
            }
            entry<RecoveryKitRoute> {
                RecoveryKitScreen(app, onboarding) { backStack.add(QuickUnlockRoute) }
            }
            entry<QuickUnlockRoute> {
                val context = LocalContext.current
                QuickUnlockScreen(app, onboarding) {
                    app.prefs.onboardingDone = true
                    // The window flag is set once per activity, so flipping the
                    // pref is not enough — re-apply here or the vault stays
                    // screenshotable until the next cold start.
                    (context as? MainActivity)?.applyScreenPrivacy()
                    // repository is already Unlocked → root switch lands on Home (S7)
                }
            }
        },
    )
}
// #endregion

// #region Main scaffold
/**
 * Shortcuts on the Add FAB menu, in the order they appear bottom-up. Ids must
 * exist in templates.json; TemplateCatalogTest guards that they cannot be
 * renamed away underneath us.
 */
private val QuickAdd = listOf(
    Triple("login", R.string.tpl_login, Icons.Outlined.Password),
    Triple("card", R.string.tpl_card, Icons.Outlined.CreditCard),
    Triple("upi", R.string.tpl_upi, Icons.Outlined.QrCode2),
    Triple("bank_account", R.string.tpl_bank_account, Icons.Outlined.AccountBalance),
)

/**
 * The tap entry sits beside them rather than in the list: it opens a reader,
 * not a blank form, so it is a different kind of action.
 */
private const val TAP_CARD_ID = "__tap_card__"

/**
 * Which destination each tab shows. Route objects rather than paths, so a
 * renamed destination is a compile error here instead of a tab that silently
 * stops highlighting.
 */
private val TabRoutes: Map<VaultTab, Any> = mapOf(
    VaultTab.Vault to HomeRoute,
    VaultTab.Codes to CodesRoute,
    VaultTab.Templates to GalleryRoute,
    VaultTab.Settings to SettingsRoute,
)

/**
 * Date fields ReminderWorker auto-suggests a 30-day reminder for (§5.7).
 *
 * Read from the worker rather than repeated here. It was repeated here, and the
 * two lists had already drifted: maturity_date was added to this copy and not
 * to the worker's, so the app offered a reminder it would never raise.
 */
private val ReminderDateFields = ReminderWorker.AUTO_SUGGEST_FIELDS

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun MainScaffold(app: ZerokoshApp) {
    val navState = rememberVaultNavState()
    // horizontalPartitionSpacerSize = 0 so the two panes meet without a gutter
    // the rest of this app's surfaces do not have.
    val adaptiveInfo = currentWindowAdaptiveInfo()
    val paneDirective = remember(adaptiveInfo) {
        calculatePaneScaffoldDirective(adaptiveInfo).copy(horizontalPartitionSpacerSize = 0.dp)
    }
    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>(directive = paneDirective)

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

    // The tab is state now, not something inferred from whatever route happens
    // to be on top.
    val activeTab = navState.tab.takeIf { navState.top == TabRoutes.getValue(navState.tab) }
    val openGallery = { navState.selectTab(VaultTab.Templates) }
    // No encoding here any more. The string route had to Uri.encode these,
    // because "Punjab & Sind Bank" and "L&T Finance" split the query string on
    // the ampersand and arrived truncated to "Punjab "; serialization handles it.
    val openEdit = { templateId: String, presetName: String?, brand: String? ->
        navState.replaceTop(EditRoute(templateId = templateId, preset = presetName, brand = brand))
    }
    val openTemplate = { templateId: String -> openEdit(templateId, null, null) }
    // The Add FAB used to be a one-way trip to the 20-item template gallery.
    // The Expressive FAB menu puts the four templates that cover most additions
    // one tap away and keeps the gallery as the escape hatch.
    var fabMenuExpanded by rememberSaveable { mutableStateOf(false) }
    // Not rememberSaveable: a half-finished read is not worth restoring, and the
    // sheet holds a live NFC reader session.
    var showTapCard by remember { mutableStateOf(false) }
    val onCardTapped = { card: org.zerokosh.core.emv.EmvCard ->
        // Handed over in memory, never as a route argument — see PendingCard.
        org.zerokosh.app.nfc.PendingCard.offer(card)
        showTapCard = false
        openEdit("card", null, null)
    }
    var fabVisible by remember { mutableStateOf(true) }
    BackHandler(fabMenuExpanded) { fabMenuExpanded = false }
    // Leaving the Vault tab must not strand an open menu offscreen.
    LaunchedEffect(activeTab) { if (activeTab != VaultTab.Vault) fabMenuExpanded = false }

    val selectTab = { tab: VaultTab -> navState.selectTab(tab) }
    // M3's medium breakpoint: at 600dp and up a bottom bar wastes the vertical
    // space that is already scarce in landscape, so the destinations move to a
    // side rail instead.
    //
    // currentWindowAdaptiveInfo() rather than LocalConfiguration.screenWidthDp.
    //
    // Not because the old check was giving wrong answers: measured in a freeform
    // window on this device, Configuration already reported the window (220dp)
    // and not the display (923dp), and both routes chose the bottom bar. The
    // reasons are narrower and worth stating honestly. It is the supported API
    // for size classes, so it is not exposed to screenWidthDp's shifting
    // semantics — that value excluded system insets until API 35 and includes
    // them after, which moves a device sitting near the 600dp line across it.
    // And it carries posture, which Configuration has no way to report, so a
    // folding device is answerable here when the two-pane layout arrives.
    val widthClass = currentWindowAdaptiveInfo().windowSizeClass
    val wideWindow = widthClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

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
                                contentDescription = stringResource(
                                    if (fabMenuExpanded) R.string.nav_close_menu else R.string.scr_home_add,
                                ),
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
                    FloatingActionButtonMenuItem(
                        onClick = {
                            fabMenuExpanded = false
                            showTapCard = true
                        },
                        icon = { Icon(Icons.Outlined.Contactless, contentDescription = null) },
                        text = { Text(stringResource(R.string.nav_tap_card)) },
                    )
                    QuickAdd.forEach { (templateId, labelRes, icon) ->
                        FloatingActionButtonMenuItem(
                            onClick = {
                                fabMenuExpanded = false
                                openTemplate(templateId)
                            },
                            icon = { Icon(icon, contentDescription = null) },
                            text = { Text(stringResource(labelRes)) },
                        )
                    }
                    FloatingActionButtonMenuItem(
                        onClick = {
                            fabMenuExpanded = false
                            openGallery()
                        },
                        icon = { Icon(Icons.Outlined.GridView, contentDescription = null) },
                        text = { Text(stringResource(R.string.nav_all_templates)) },
                    )
                }
                VaultTab.Codes -> VaultExtendedFab(
                    label = stringResource(R.string.nav_scan),
                    // Said Scan, opened the Templates tab.
                    onClick = { navState.scanRequested = true },
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
                    NavDisplay(
                        backStack = navState.displayed,
                        onBack = { navState.back() },
                        // Where there is room, a record opens beside the list it
                        // came from rather than on top of it. Below that width
                        // the strategy simply does not apply and behaviour is
                        // exactly as before.
                        sceneStrategies = listOf(listDetailStrategy),
                        modifier = Modifier.padding(bottom = padding.calculateBottomPadding()),
                        transitionSpec = {
                            globalEnterTransition() togetherWith globalExitTransition()
                        },
                        popTransitionSpec = {
                            globalPopEnterTransition() togetherWith globalPopExitTransition()
                        },
                        entryProvider = entryProvider {
                            entry<HomeRoute>(
                                metadata = ListDetailSceneStrategy.listPane(
                                    detailPlaceholder = { DetailPlaceholder() },
                                ),
                            ) {
                                // Shared elements come from Nav3's own animation
                                // scope now. The screens are untouched: they read
                                // the app's own CompositionLocal, which is what
                                // this provides.
                                CompositionLocalProvider(
                                    LocalAnimatedVisibilityScope provides LocalNavAnimatedContentScope.current,
                                ) {
                                    HomeScreen(
                                        app = app,
                                        onScrollHideFab = { hide -> fabVisible = !hide },
                                        onOpen = { uuid -> navState.push(DetailRoute(uuid)) },
                                        onAdd = openGallery,
                                        onQuickAdd = openEdit,
                                        // Settings owns the "new recovery key"
                                        // flow, which already asks for the
                                        // passphrase and shows a fresh kit.
                                        onSaveKit = { navState.selectTab(VaultTab.Settings) },
                                    )
                                }
                            }
                            entry<CodesRoute> {
                                AuthenticatorScreen(
                                    app,
                                    scanRequested = navState.scanRequested,
                                    onScanRequestHandled = { navState.scanRequested = false },
                                )
                            }
                            entry<SettingsRoute> {
                                SettingsScreen(
                                    app = app,
                                    onOpenTrash = { navState.push(TrashRoute) },
                                    onOpenHealth = { navState.push(HealthRoute) },
                                )
                            }
                            entry<TrashRoute> { TrashScreen(app, onBack = { navState.back() }) }
                            entry<HealthRoute> {
                                VaultHealthScreen(
                                    app = app,
                                    onBack = { navState.back() },
                                    onOpen = { uuid -> navState.push(DetailRoute(uuid)) },
                                )
                            }
                            entry<GalleryRoute> {
                                TemplateGalleryScreen(
                                    app = app,
                                    onPick = openEdit,
                                    onClose = { navState.selectTab(VaultTab.Vault) },
                                )
                            }
                            entry<DetailRoute>(
                                metadata = ListDetailSceneStrategy.detailPane(),
                            ) { route ->
                                CompositionLocalProvider(
                                    LocalAnimatedVisibilityScope provides LocalNavAnimatedContentScope.current,
                                ) {
                                    RecordDetailScreen(
                                        app = app,
                                        uuid = route.uuid,
                                        onEdit = { navState.push(EditRoute.forRecord(route.uuid)) },
                                        onOpenRecord = { linked -> navState.push(DetailRoute(linked)) },
                                        onClose = { navState.back() },
                                    )
                                }
                            }
                            entry<EditRoute> { route ->
                                // No decoding. The arguments arrive as they were
                                // sent, ampersands and all.
                                RecordEditScreen(
                                    app = app,
                                    templateIdArg = route.templateId,
                                    editUuid = route.uuid,
                                    presetName = route.preset,
                                    brandName = route.brand,
                                    onDone = { navState.back() },
                                )
                            }
                        },
                    )
                }
            }
            if (showTapCard) {
                TapCardSheet(onCard = onCardTapped, onDismiss = { showTapCard = false })
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
        Kicker(stringResource(R.string.nav_damaged_kicker))
        Spacer(Modifier.height(12.dp))
        Text(
            buildAnnotatedString {
                append(stringResource(R.string.nav_damaged_lead))
                withStyle(EmphasisSpan.copy(color = c.ink)) {
                    append(stringResource(R.string.nav_damaged_emph))
                }
                append(stringResource(R.string.nav_damaged_tail))
            },
            style = MaterialTheme.typography.displayMedium,
            color = c.ink,
        )

        Spacer(Modifier.height(24.dp))
        NoticeCard(
            title = stringResource(R.string.nav_integrity_title),
            body = stringResource(R.string.nav_integrity_body),
            icon = Icons.Outlined.WarningAmber,
            tone = NoticeTone.Warn,
        )

        Spacer(Modifier.height(24.dp))
        GroupCard {
            Column(Modifier.padding(16.dp)) {
                Text(
                    stringResource(R.string.nav_what_next),
                    style = MaterialTheme.typography.titleSmall,
                    color = c.ink,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    stringResource(R.string.nav_what_next_body),
                    style = MaterialTheme.typography.bodyMedium,
                    color = c.ink(0.65f),
                )
            }
        }
    }
}
// #endregion

// #region List-detail placeholder
/**
 * What fills the detail pane before a record is chosen.
 *
 * Only ever seen on a wide window, where the pane exists but nothing has been
 * opened into it yet. Quiet on purpose: it sits beside a list the user is
 * reading, so it should not compete with it.
 */
@Composable
private fun DetailPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VaultTheme.colors.paper),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.hm_detail_placeholder),
            style = MaterialTheme.typography.bodyMedium,
            color = VaultTheme.colors.mute,
        )
    }
}
// #endregion
