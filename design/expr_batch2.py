# -*- coding: utf-8 -*-
"""Expressive batch 2: scroll-aware FAB."""
import io

HOME = 'app/src/main/java/org/zerokosh/app/ui/home/HomeScreen.kt'
NAV = 'app/src/main/java/org/zerokosh/app/ui/ZerokoshNav.kt'


def rd(p):
    return io.open(p, encoding='utf-8').read()


def wr(p, s):
    io.open(p, 'w', encoding='utf-8', newline='\n').write(s)


def sub(s, old, new, path=''):
    assert old in s, 'ANCHOR MISSING %s:\n%r' % (path, old[:170])
    return s.replace(old, new, 1)


# ---- HomeScreen: hoist the list state and report scroll direction ---------
s = rd(HOME)
s = sub(s, '''fun HomeScreen(
    app: ZerokoshApp,''',
        '''fun HomeScreen(
    app: ZerokoshApp,
    onScrollHideFab: (Boolean) -> Unit = {},''', HOME)

s = sub(s, '''        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),''',
        '''        // The Add FAB sits over this list. lastScrolledForward is Compose's own
        // direction flag, so no manual offset bookkeeping is needed; the
        // canScrollBackward guard stops the FAB hiding on an overscroll bounce
        // while already at the top.
        val listState = rememberLazyListState()
        val hideFab by remember {
            derivedStateOf { listState.lastScrolledForward && listState.canScrollBackward }
        }
        LaunchedEffect(hideFab) { onScrollHideFab(hideFab) }

        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f).fillMaxWidth(),''', HOME)

for imp in ['import androidx.compose.foundation.lazy.rememberLazyListState\n',
            'import androidx.compose.runtime.derivedStateOf\n',
            'import androidx.compose.runtime.LaunchedEffect\n']:
    if imp not in s:
        s = s.replace('import androidx.compose.foundation.lazy.LazyColumn\n',
                      'import androidx.compose.foundation.lazy.LazyColumn\n' + imp, 1)
wr(HOME, s)
print('HomeScreen.kt: list state hoisted, scroll direction reported')

# ---- ZerokoshNav: drive the FAB from it -----------------------------------
s = rd(NAV)
s = sub(s, '''    var fabMenuExpanded by rememberSaveable { mutableStateOf(false) }''',
        '''    var fabMenuExpanded by rememberSaveable { mutableStateOf(false) }
    var fabVisible by remember { mutableStateOf(true) }''', NAV)

s = sub(s, '''                        ToggleFloatingActionButton(
                            checked = fabMenuExpanded,
                            onCheckedChange = { fabMenuExpanded = it },''',
        '''                        ToggleFloatingActionButton(
                            checked = fabMenuExpanded,
                            onCheckedChange = { fabMenuExpanded = it },
                            // Never hide the button while its own menu is open.
                            modifier = Modifier.animateFloatingActionButton(
                                visible = fabVisible || fabMenuExpanded,
                                alignment = Alignment.BottomEnd,
                            ),''', NAV)

s = sub(s, '''                                HomeScreen(
                                    app = app,''',
        '''                                HomeScreen(
                                    app = app,
                                    onScrollHideFab = { hide -> fabVisible = !hide },''', NAV)

for imp in ['import androidx.compose.material3.animateFloatingActionButton\n',
            'import androidx.compose.ui.Alignment\n']:
    if imp not in s:
        s = s.replace('import androidx.compose.material3.Icon\n',
                      imp + 'import androidx.compose.material3.Icon\n', 1)
wr(NAV, s)
print('ZerokoshNav.kt: FAB hides on scroll down, returns on scroll up')
