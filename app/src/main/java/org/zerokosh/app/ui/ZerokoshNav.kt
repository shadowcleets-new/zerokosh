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
package org.zerokosh.app.ui

// #region Imports
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
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
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (activeTab != null) {
                VaultNavBar(active = activeTab, onSelect = { tab ->
                    nav.navigate(TabRoutes.getValue(tab)) {
                        popUpTo("home") { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
            }
        },
        floatingActionButton = {
            // The mockup floats an extended FAB above the dock on the two list
            // screens; Templates and Settings carry their own actions.
            when (activeTab) {
                VaultTab.Vault -> VaultExtendedFab("Add", openGallery)
                VaultTab.Codes -> VaultExtendedFab(
                    label = "Scan",
                    onClick = openGallery,
                    icon = Icons.Outlined.QrCodeScanner,
                )
                else -> Unit
            }
        },
    ) { padding ->
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
                                onOpen = { uuid -> nav.navigate("detail/$uuid") },
                                onAdd = openGallery,
                            )
                        }
                    }
                    composable("authenticator") { AuthenticatorScreen(app) }
                    composable("settings") { SettingsScreen(app) }
                    composable("gallery") {
                        TemplateGalleryScreen(
                            app = app,
                            onPick = { templateId, presetName ->
                                nav.navigate("edit/$templateId?preset=${presetName ?: ""}") {
                                    popUpTo("home")
                                }
                            },
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
                    composable("edit/{templateId}?uuid={uuid}&preset={preset}") { entry ->
                        val templateId = entry.arguments?.getString("templateId") ?: return@composable
                        val uuid = entry.arguments?.getString("uuid")?.ifEmpty { null }
                        val preset = entry.arguments?.getString("preset")?.ifEmpty { null }
                        RecordEditScreen(
                            app = app,
                            templateIdArg = templateId,
                            editUuid = uuid,
                            presetName = preset,
                            onDone = { nav.popBackStack() },
                        )
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
