/**
 * @file BharatVaultNav.kt
 * @description Root navigation: state-driven top switch (NoVault → onboarding,
 *              Locked → S13, Unlocked → main tabs), per §5.1 flow
 *              S1→S2→S3→S4→S5→S6 first run, S13→S6 thereafter.
 *
 * [TABLE OF CONTENTS]
 * 1. ROOT SWITCH
 * 2. ONBOARDING FLOW (S1–S5)
 * 3. MAIN SCAFFOLD (tabs + inner routes)
 * 4. DAMAGED SCREEN
 */
package org.bharatvault.app.ui

// #region Imports
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.bharatvault.app.BharatVaultApp
import org.bharatvault.app.R
import org.bharatvault.app.data.VaultState
import org.bharatvault.app.ui.authenticator.AuthenticatorScreen
import org.bharatvault.app.ui.common.RevealAuth
import org.bharatvault.app.ui.gallery.TemplateGalleryScreen
import org.bharatvault.app.ui.home.HomeScreen
import org.bharatvault.app.ui.lock.LockScreen
import org.bharatvault.app.ui.onboarding.CreatePassphraseScreen
import org.bharatvault.app.ui.onboarding.LanguageScreen
import org.bharatvault.app.ui.onboarding.OnboardingState
import org.bharatvault.app.ui.onboarding.QuickUnlockScreen
import org.bharatvault.app.ui.onboarding.RecoveryKitScreen
import org.bharatvault.app.ui.onboarding.TrustScreen
import org.bharatvault.app.ui.record.RecordDetailScreen
import org.bharatvault.app.ui.record.RecordEditScreen
import org.bharatvault.app.ui.settings.SettingsScreen
// #endregion

// #region Root switch
@Composable
fun BharatVaultNav(app: BharatVaultApp) {
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

// #region Onboarding flow (S1→S5)
@Composable
private fun OnboardingFlow(app: BharatVaultApp) {
    val nav = rememberNavController()
    val onboarding = remember { OnboardingState() }
    NavHost(navController = nav, startDestination = "language") {
        composable("language") { LanguageScreen(app) { nav.navigate("trust") } }
        composable("trust") { TrustScreen { nav.navigate("create") } }
        composable("create") {
            CreatePassphraseScreen(app, onboarding) { nav.navigate("recovery") }
        }
        composable("recovery") {
            RecoveryKitScreen(app, onboarding) { nav.navigate("quickunlock") }
        }
        composable("quickunlock") {
            QuickUnlockScreen(app, onboarding) {
                app.prefs.onboardingDone = true
                // repository is already Unlocked → root switch lands on Home (S6)
            }
        }
    }
}
// #endregion

// #region Main scaffold (S6 tabs: Home · Authenticator · Settings — §10.3)
private data class Tab(val route: String, val labelRes: Int, val icon: @Composable () -> Unit)

@Composable
private fun MainScaffold(app: BharatVaultApp) {
    val nav: NavHostController = rememberNavController()
    val tabs = listOf(
        Tab("home", R.string.scr_home_tab_home) { Icon(Icons.Filled.Home, null) },
        Tab("authenticator", R.string.scr_home_tab_authenticator) { Icon(Icons.Outlined.Timer, null) },
        Tab("settings", R.string.scr_home_tab_settings) { Icon(Icons.Filled.Settings, null) },
    )
    val backStack by nav.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in tabs.map { it.route }) {
                NavigationBar {
                    tabs.forEach { tab ->
                        NavigationBarItem(
                            selected = currentRoute == tab.route,
                            onClick = {
                                nav.navigate(tab.route) {
                                    popUpTo("home") { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = tab.icon,
                            label = { Text(stringResource(tab.labelRes)) },
                        )
                    }
                }
            }
        },
    ) { padding ->
        NavHost(
            navController = nav,
            startDestination = "home",
            modifier = Modifier.padding(padding),
        ) {
            composable("home") {
                HomeScreen(
                    app = app,
                    onAdd = { nav.navigate("gallery") },
                    onOpen = { uuid -> nav.navigate("detail/$uuid") },
                )
            }
            composable("authenticator") {
                AuthenticatorScreen(app)
            }
            composable("settings") {
                SettingsScreen(app)
            }
            composable("gallery") {
                TemplateGalleryScreen(
                    app = app,
                    onPick = { templateId, presetName ->
                        nav.navigate("edit/$templateId?preset=${presetName ?: ""}") {
                            popUpTo("home")
                        }
                    },
                )
            }
            composable("detail/{uuid}") { entry ->
                val uuid = entry.arguments?.getString("uuid") ?: return@composable
                RecordDetailScreen(
                    app = app,
                    uuid = uuid,
                    onEdit = { nav.navigate("edit/byid?uuid=$uuid") },
                    onOpenRecord = { linked -> nav.navigate("detail/$linked") },
                    onClose = { nav.popBackStack() },
                )
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
// #endregion

// #region Damaged screen (§11.2 clean failure path)
@Composable
private fun DamagedScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            stringResource(R.string.msg_file_damaged),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.error,
        )
    }
}
// #endregion
