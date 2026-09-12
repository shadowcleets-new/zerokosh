/**
 * @file VaultNavState.kt
 * @description The four tabs' back stacks, and the rules for moving between them.
 *
 * Under Navigation 2 this was emulated: one back stack, and every tab switch
 * carried `popUpTo(home) { saveState = true }` with `restoreState = true` to
 * make the controller stash and restore the parts belonging to each tab. It
 * worked, but the separation was a side effect of three flags rather than
 * something the code said.
 *
 * Here the stacks are simply four lists, and which one is showing is a
 * variable. That also answers the question the migration guide warns about —
 * a destination reachable from more than one tab, such as a record opened
 * from the vault list and again from Vault review under Settings. There is
 * nothing to reconcile: pushing adds to whichever stack is current, so the
 * same record can sit in two stacks and each tab keeps its own way back.
 */
package org.zerokosh.app.ui

// #region Imports
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import org.zerokosh.app.ui.common.VaultTab
// #endregion

/**
 * Which tab is showing, and what each tab has open.
 *
 * @property displayed the entries NavDisplay should draw. Away from the Vault
 *   tab this is the vault's stack with the current tab's on top, which is what
 *   keeps back always eventually arriving home rather than closing the app from
 *   wherever the user happened to be.
 */
class VaultNavState(
    private val stacks: Map<VaultTab, NavBackStack<NavKey>>,
    initialTab: VaultTab,
) {
    var tab: VaultTab by mutableStateOf(initialTab)
        private set

    /**
     * The Codes tab's Scan button, which lives in this scaffold's FAB while the
     * scanner it opens belongs to the screen below. Set here, consumed there.
     */
    var scanRequested: Boolean by mutableStateOf(false)

    private val current: NavBackStack<NavKey> get() = stacks.getValue(tab)

    val displayed: List<NavKey>
        get() = if (tab == VaultTab.Vault) {
            current.toList()
        } else {
            stacks.getValue(VaultTab.Vault).toList() + current.toList()
        }

    /** The destination on top, which is what decides the FAB and the title. */
    val top: NavKey get() = current.lastOrNull() ?: HomeRoute

    /**
     * Show [target].
     *
     * Re-selecting the tab you are already on empties that tab back to its root
     * rather than doing nothing, which is what a bottom bar is expected to do.
     */
    fun selectTab(target: VaultTab) {
        if (target == tab) {
            val stack = current
            while (stack.size > 1) stack.removeAt(stack.lastIndex)
            return
        }
        tab = target
    }

    /** Push a destination onto the tab the user is currently in. */
    fun push(key: NavKey) {
        current.add(key)
    }

    /**
     * Replace everything above the tab's root with [key].
     *
     * The template gallery and the editor are reached this way: opening one
     * from another should not stack them up, or backing out of an edit would
     * walk through every template screen visited on the way in.
     */
    fun replaceTop(key: NavKey) {
        val stack = current
        while (stack.size > 1) stack.removeAt(stack.lastIndex)
        stack.add(key)
    }

    /** Empty the current tab back to its root. */
    fun popToRoot() {
        val stack = current
        while (stack.size > 1) stack.removeAt(stack.lastIndex)
    }

    /**
     * Back. Returns false when there is nothing left to go back to and the
     * activity should handle it — which only happens at the vault root.
     */
    fun back(): Boolean {
        val stack = current
        if (stack.size > 1) {
            stack.removeAt(stack.lastIndex)
            return true
        }
        if (tab != VaultTab.Vault) {
            tab = VaultTab.Vault
            return true
        }
        return false
    }
}

/**
 * One saveable stack per tab, so each survives rotation and process death with
 * its own contents rather than sharing one controller's saved state.
 */
@Composable
fun rememberVaultNavState(): VaultNavState {
    val vault = rememberNavBackStack(HomeRoute)
    val codes = rememberNavBackStack(CodesRoute)
    val templates = rememberNavBackStack(GalleryRoute)
    val settings = rememberNavBackStack(SettingsRoute)
    var savedTab by rememberSaveable { mutableStateOf(VaultTab.Vault.name) }
    val state = remember {
        VaultNavState(
            stacks = mapOf(
                VaultTab.Vault to vault,
                VaultTab.Codes to codes,
                VaultTab.Templates to templates,
                VaultTab.Settings to settings,
            ),
            initialTab = runCatching { VaultTab.valueOf(savedTab) }.getOrDefault(VaultTab.Vault),
        )
    }
    savedTab = state.tab.name
    return state
}
