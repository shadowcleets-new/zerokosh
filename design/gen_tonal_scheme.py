# -*- coding: utf-8 -*-
"""Generate a full Material 3 tonal colour scheme from the Zerokosh brand seeds.

Material's own seed-based generator lives in material-color-utilities, which the
locked §6.2 dependency list rules out, and material3's TonalPalette is internal.
So the ramps are generated here and emitted as Kotlin constants: no runtime
cost, no new dependency, and every value is visible in review.

An M3 "tone" is CIELAB L*, so a tonal ramp is the seed's hue and chroma held
constant while L* sweeps the tone stops. Chroma is reduced by binary search
where a tone would fall outside sRGB, which is what Material does too.

The Expressive part is the on-container rule taken from
expressiveLightColorScheme(): on*Container sits at tone 30 in light and tone 90
in dark, rather than the baseline's tone 10/10.
"""
import io
import math

# ---- sRGB <-> CIELAB ------------------------------------------------------
def _srgb_to_linear(c):
    return c / 12.92 if c <= 0.04045 else ((c + 0.055) / 1.055) ** 2.4


def _linear_to_srgb(c):
    return 12.92 * c if c <= 0.0031308 else 1.055 * (c ** (1 / 2.4)) - 0.055


D65 = (0.95047, 1.0, 1.08883)


def hex_to_lab(h):
    h = h.lstrip('#')
    r, g, b = [_srgb_to_linear(int(h[i:i + 2], 16) / 255.0) for i in (0, 2, 4)]
    x = r * 0.4124564 + g * 0.3575761 + b * 0.1804375
    y = r * 0.2126729 + g * 0.7151522 + b * 0.0721750
    z = r * 0.0193339 + g * 0.1191920 + b * 0.9503041
    def f(t):
        return t ** (1 / 3) if t > 216 / 24389 else (24389 / 27 * t + 16) / 116
    fx, fy, fz = f(x / D65[0]), f(y / D65[1]), f(z / D65[2])
    return (116 * fy - 16, 500 * (fx - fy), 200 * (fy - fz))


def lab_to_rgb(L, a, b):
    fy = (L + 16) / 116
    fx, fz = fy + a / 500, fy - b / 200
    def inv(t):
        return t ** 3 if t ** 3 > 216 / 24389 else (116 * t - 16) * 27 / 24389
    x, y, z = inv(fx) * D65[0], inv(fy) * D65[1], inv(fz) * D65[2]
    r = x * 3.2404542 + y * -1.5371385 + z * -0.4985314
    g = x * -0.9692660 + y * 1.8760108 + z * 0.0415560
    bl = x * 0.0556434 + y * -0.2040259 + z * 1.0572252
    return [_linear_to_srgb(v) for v in (r, g, bl)]


def in_gamut(rgb):
    return all(-0.0005 <= v <= 1.0005 for v in rgb)


def tone(seed_hex, t):
    """The seed's hue at tone t (=L*), chroma reduced only if out of gamut."""
    L0, a0, b0 = hex_to_lab(seed_hex)
    chroma = math.hypot(a0, b0)
    hue = math.atan2(b0, a0)
    lo, hi = 0.0, chroma
    best = None
    for _ in range(28):
        mid = (lo + hi) / 2
        rgb = lab_to_rgb(t, mid * math.cos(hue), mid * math.sin(hue))
        if in_gamut(rgb):
            best = rgb
            lo = mid
        else:
            hi = mid
    if best is None:
        best = lab_to_rgb(t, 0, 0)
    return '#%02X%02X%02X' % tuple(max(0, min(255, round(v * 255))) for v in best)


# ---- brand seeds ----------------------------------------------------------
PRIMARY = '#BB4717'    # burnt orange
SECONDARY = '#018D87'  # teal accent
ERROR = '#D40C1A'


def rotate_hue(seed_hex, degrees):
    L, a, b = hex_to_lab(seed_hex)
    c, h = math.hypot(a, b), math.atan2(b, a) + math.radians(degrees)
    rgb = lab_to_rgb(L, c * math.cos(h), c * math.sin(h))
    return '#%02X%02X%02X' % tuple(max(0, min(255, round(v * 255))) for v in rgb)


def desaturate(seed_hex, factor):
    L, a, b = hex_to_lab(seed_hex)
    rgb = lab_to_rgb(L, a * factor, b * factor)
    return '#%02X%02X%02X' % tuple(max(0, min(255, round(v * 255))) for v in rgb)


# M3 derives tertiary by rotating the primary hue; neutrals are the primary hue
# at very low chroma, so the greys stay in the brand's warm family.
TERTIARY = rotate_hue(PRIMARY, 60)
NEUTRAL = desaturate(PRIMARY, 0.055)
NEUTRAL_VARIANT = desaturate(PRIMARY, 0.13)

RAMPS = {'P': PRIMARY, 'S': SECONDARY, 'T': TERTIARY, 'E': ERROR,
         'N': NEUTRAL, 'NV': NEUTRAL_VARIANT}
STOPS = [0, 4, 6, 10, 12, 17, 20, 22, 24, 30, 40, 50, 60, 70, 80, 87, 90, 92, 94, 95, 96, 98, 99, 100]
T = {k: {s: tone(v, s) for s in STOPS} for k, v in RAMPS.items()}

# ---- role maps (M3 spec; on*Container uses the Expressive tone) ------------
LIGHT = [
    ('primary', 'P', 40), ('onPrimary', 'P', 100),
    ('primaryContainer', 'P', 90), ('onPrimaryContainer', 'P', 30),
    ('inversePrimary', 'P', 80),
    ('secondary', 'S', 40), ('onSecondary', 'S', 100),
    ('secondaryContainer', 'S', 90), ('onSecondaryContainer', 'S', 30),
    ('tertiary', 'T', 40), ('onTertiary', 'T', 100),
    ('tertiaryContainer', 'T', 90), ('onTertiaryContainer', 'T', 30),
    ('error', 'E', 40), ('onError', 'E', 100),
    ('errorContainer', 'E', 90), ('onErrorContainer', 'E', 30),
    ('background', 'N', 99), ('onBackground', 'N', 10),
    ('surface', 'N', 99), ('onSurface', 'N', 10),
    ('surfaceVariant', 'NV', 90), ('onSurfaceVariant', 'NV', 30),
    ('surfaceTint', 'P', 40),
    ('inverseSurface', 'N', 20), ('inverseOnSurface', 'N', 95),
    ('outline', 'NV', 50), ('outlineVariant', 'NV', 80),
    ('scrim', 'N', 0),
    ('surfaceBright', 'N', 98), ('surfaceDim', 'N', 87),
    ('surfaceContainerLowest', 'N', 100), ('surfaceContainerLow', 'N', 96),
    ('surfaceContainer', 'N', 94), ('surfaceContainerHigh', 'N', 92),
    ('surfaceContainerHighest', 'N', 90),
]
DARK = [
    ('primary', 'P', 80), ('onPrimary', 'P', 20),
    ('primaryContainer', 'P', 30), ('onPrimaryContainer', 'P', 90),
    ('inversePrimary', 'P', 40),
    ('secondary', 'S', 80), ('onSecondary', 'S', 20),
    ('secondaryContainer', 'S', 30), ('onSecondaryContainer', 'S', 90),
    ('tertiary', 'T', 80), ('onTertiary', 'T', 20),
    ('tertiaryContainer', 'T', 30), ('onTertiaryContainer', 'T', 90),
    ('error', 'E', 80), ('onError', 'E', 20),
    ('errorContainer', 'E', 30), ('onErrorContainer', 'E', 90),
    ('background', 'N', 10), ('onBackground', 'N', 90),
    ('surface', 'N', 10), ('onSurface', 'N', 90),
    ('surfaceVariant', 'NV', 30), ('onSurfaceVariant', 'NV', 80),
    ('surfaceTint', 'P', 80),
    ('inverseSurface', 'N', 90), ('inverseOnSurface', 'N', 20),
    ('outline', 'NV', 60), ('outlineVariant', 'NV', 30),
    ('scrim', 'N', 0),
    ('surfaceBright', 'N', 24), ('surfaceDim', 'N', 6),
    ('surfaceContainerLowest', 'N', 4), ('surfaceContainerLow', 'N', 10),
    ('surfaceContainer', 'N', 12), ('surfaceContainerHigh', 'N', 17),
    ('surfaceContainerHighest', 'N', 22),
]


# The brand hexes are the identity anchors, and VaultColors already uses them
# literally. Letting the scheme land on tone 40 (#A93806) instead would put two
# slightly different oranges on the same screen — M3 components one shade, ours
# another. So the anchors are pinned and everything else is derived around them.
PIN_LIGHT = {'primary': PRIMARY, 'secondary': SECONDARY, 'error': ERROR}
PIN_DARK = {'primary': '#F0834E', 'secondary': '#1DBCB5', 'error': '#FF6367'}


def emit(name, roles, fn, pins):
    out = ['private val %s = %s(' % (name, fn)]
    for role, ramp, t in roles:
        if role in pins:
            out.append('    %s = Color(0xFF%s),  // brand anchor'
                       % (role, pins[role].lstrip('#')))
            continue
        out.append('    %s = Color(0xFF%s),  // %s%d' % (role, T[ramp][t].lstrip('#'), ramp, t))
    out.append(')')
    return '\n'.join(out)


header = '''// GENERATED by design/gen_tonal_scheme.py — do not hand-edit.
//
// A complete Material 3 tonal scheme derived from the brand seeds, rather than
// the ~24 hand-picked roles this replaces. Every role is now a tone of one of
// six ramps, so no role can be left undefined and silently fall back to the M3
// baseline purple — which is exactly the bug that produced a pink FAB.
//
//   primary          %s   the burnt orange
//   secondary        %s   the teal accent
//   tertiary         %s   primary hue rotated 60 degrees, per the M3 spec
//   error            %s
//   neutral          %s   primary hue at 5.5%% chroma, so greys stay warm
//   neutral variant  %s   primary hue at 13%% chroma
//
// on*Container sits at tone 30 in light and tone 90 in dark. That is the one
// thing expressiveLightColorScheme() actually changes over the baseline, applied
// here to our own ramps instead of Google's purple.
''' % (PRIMARY, SECONDARY, TERTIARY, ERROR, NEUTRAL, NEUTRAL_VARIANT)

body = header + '\n' + emit('LightColors', LIGHT, 'lightColorScheme', PIN_LIGHT) + '\n\n' + \
    emit('DarkColors', DARK, 'darkColorScheme', PIN_DARK) + '\n'

io.open(r'C:\Users\acer\bharatvault\design\_scheme.kt.txt', 'w',
        encoding='utf-8', newline='\n').write(body)
print(body[:1400])
print('...')
print('wrote design/_scheme.kt.txt')
