# -*- coding: utf-8 -*-
"""Expressive batch 1: inset focus rings, and the two partial adoptions."""
import io

THEME = 'app/src/main/java/org/zerokosh/app/ui/theme/Theme.kt'
UI = 'app/src/main/java/org/zerokosh/app/ui/common/VaultUi.kt'


def rd(p):
    return io.open(p, encoding='utf-8').read()


def wr(p, s):
    io.open(p, 'w', encoding='utf-8', newline='\n').write(s)


def sub(s, old, new, path=''):
    assert old in s, 'ANCHOR MISSING %s:\n%r' % (path, old[:170])
    return s.replace(old, new, 1)


# ---- 1. inset focus rings -------------------------------------------------
s = rd(THEME)
s = sub(s, '''        MaterialExpressiveTheme(
            colorScheme = if (darkTheme) DarkColors else LightColors,''',
        '''        // Expressive replaces the opacity wash that used to mark keyboard focus
        // with a drawn inset ring. The wash was nearly invisible against our
        // tinted surfaces, so this is an accessibility fix, not a restyle.
        CompositionLocalProvider(
            LocalRippleThemeConfiguration provides
                RippleDefaults.InsetFocusRingRippleThemeConfiguration,
        ) {
        MaterialExpressiveTheme(
            colorScheme = if (darkTheme) DarkColors else LightColors,''', THEME)
s = sub(s, '''            typography = BharatTypography,
            content = content,
        )
    }
}''',
        '''            typography = BharatTypography,
            content = content,
        )
        }
    }
}''', THEME)
for imp in ['import androidx.compose.material3.LocalRippleThemeConfiguration\n',
            'import androidx.compose.material3.RippleDefaults\n',
            'import androidx.compose.runtime.CompositionLocalProvider\n']:
    if imp not in s:
        s = s.replace('import androidx.compose.material3.MaterialTheme\n',
                      imp + 'import androidx.compose.material3.MaterialTheme\n', 1)
wr(THEME, s)
print('Theme.kt: inset focus rings')

# ---- 2. FilterChip -> the shapes overload ---------------------------------
s = rd(UI)
s = sub(s, '''    val c = VaultTheme.colors
    // Expressive chips morph their corner geometry on selection rather than only
    // swapping fill, so the state change is legible without relying on colour.
    val corner by animateDpAsState(
        targetValue = if (selected) 12.dp else 18.dp,
        animationSpec = MaterialTheme.motionScheme.fastSpatialSpec(),
        label = "chipCorner",
    )
    FilterChip(
        selected = selected,
        onClick = onClick,''',
        '''    val c = VaultTheme.colors
    // The corner morph used to be a hand-rolled animateDpAsState. Expressive
    // ships it as a first-class overload, which also gives a pressed shape the
    // hand-rolled version never had.
    FilterChip(
        selected = selected,
        onClick = onClick,
        shapes = FilterChipDefaults.shapes(
            shape = RoundedCornerShape(18.dp),
            selectedShape = RoundedCornerShape(12.dp),
            pressedShape = RoundedCornerShape(8.dp),
        ),''', UI)
s = sub(s, '''        shape = RoundedCornerShape(corner),
        leadingIcon = if (selected) {''',
        '''        leadingIcon = if (selected) {''', UI)
wr(UI, s)
print('VaultUi.kt: FilterChip shapes overload replaces the hand-rolled morph')
