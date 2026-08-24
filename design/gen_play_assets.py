# -*- coding: utf-8 -*-
"""Render the Play Console store assets from the app's own launcher icon.

The padlock is redrawn from the vector in
app/src/main/res/drawable/ic_launcher_foreground.xml rather than traced by eye,
so the store icon and the icon on the user's home screen are the same mark. Its
path data works in a 108-unit viewport with the glyph in a group translated by
(27, 27); every coordinate below is quoted from it.

Output (not committed — regenerate on demand):
    play/icon-512.png                  512x512   Play app icon
    play/feature-graphic-1024x500.png  1024x500  Play feature graphic
"""
import os
from PIL import Image, ImageDraw, ImageFont

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.dirname(HERE)
OUT = os.path.join(ROOT, 'play')
os.makedirs(OUT, exist_ok=True)

# Straight from the app: ic_launcher_background, and the two fills in the vector.
GROUND = (0x18, 0x0F, 0x0D)
WHITE = (0xFF, 0xFF, 0xFF)
PRIMARY = (0xBB, 0x47, 0x17)
MUTE = (0xA2, 0x90, 0x7F)

SS = 4  # supersample; the shackle is an arc and aliases badly without it

GEORGIA = '/Windows/Fonts/georgia.ttf'
GEORGIA_B = '/Windows/Fonts/georgiab.ttf'
SEGOE = '/Windows/Fonts/segoeui.ttf'


def draw_lock(img, cx, cy, unit):
    """The launcher glyph, centred on (cx, cy). `unit` is one vector unit.

    Vector space: body x 6..48, y 24..50, r=4. Shackle centred (27,18), outer
    r=14, inner r=8. Keyhole circle (27,37) r=4 with a stem to y=44.
    """
    d = ImageDraw.Draw(img)
    # The glyph spans x 6..48 and y 4..50, so its centre is (27, 27).
    def X(x):
        return cx + (x - 27) * unit

    def Y(y):
        return cy + (y - 27) * unit

    # Shackle: an annulus between r=8 and r=14, upper half only, plus the two
    # legs that run down into the body.
    d.ellipse([X(27 - 14), Y(18 - 14), X(27 + 14), Y(18 + 14)], fill=WHITE)
    d.ellipse([X(27 - 8), Y(18 - 8), X(27 + 8), Y(18 + 8)], fill=GROUND)
    # Square off everything below the shackle's centre line; the body covers it.
    d.rectangle([X(6), Y(18), X(48), Y(52)], fill=GROUND)
    for x0 in (13, 27):
        pass
    # Legs, drawn after the cut so they survive it.
    d.rectangle([X(13), Y(18), X(19), Y(26)], fill=WHITE)
    d.rectangle([X(35), Y(18), X(41), Y(26)], fill=WHITE)

    # Body.
    d.rounded_rectangle([X(6), Y(24), X(48), Y(50)], radius=4 * unit, fill=WHITE)

    # Keyhole, in the brand orange.
    d.ellipse([X(27 - 4), Y(37 - 4), X(27 + 4), Y(37 + 4)], fill=PRIMARY)
    d.rounded_rectangle([X(25), Y(37), X(29), Y(44)], radius=2 * unit, fill=PRIMARY)


def app_icon(size=512):
    """Play's icon. Rendered edge to edge — Play applies its own rounding, and a
    transparent margin would show as a shrunken mark inside their mask."""
    img = Image.new('RGB', (size * SS, size * SS), GROUND)
    # The launcher crops the 108-unit viewport to its adaptive mask, so a user
    # only ever sees the middle ~72 units. Mapping 108 to the full square here
    # would render a noticeably smaller mark than the one on their home screen,
    # so map 72 instead and match what they actually see.
    unit = (size * SS) / 72.0
    draw_lock(img, size * SS / 2, size * SS / 2, unit)
    img = img.resize((size, size), Image.LANCZOS)
    path = os.path.join(OUT, 'icon-512.png')
    img.save(path, 'PNG')
    return path


def feature_graphic(w=1024, h=500):
    """Play's banner.

    Composed as one centred lockup rather than a left mark with text running to
    the right edge. Play crops this image to several aspect ratios depending on
    the surface, and anything near an edge is what gets cut; a centred group
    survives a symmetric crop. Text is measured and the layout is built from the
    measurement, so a wording change cannot silently run off the canvas.
    """
    W, H = w * SS, h * SS
    img = Image.new('RGB', (W, H), GROUND)

    # A soft warm bloom behind the mark, echoing the app's lock screen.
    bloom = Image.new('RGB', (W, H), GROUND)
    bd = ImageDraw.Draw(bloom)
    for i in range(30, 0, -1):
        r = int(190 * SS * i / 30.0)
        t = i / 30.0
        bd.ellipse(
            [W // 2 - r, int(H * 0.42) - r, W // 2 + r, int(H * 0.42) + r],
            fill=tuple(
                int(g + (p - g) * 0.20 * (1 - t)) for g, p in zip(GROUND, PRIMARY)
            ),
        )
    img = Image.blend(img, bloom, 0.9)
    d = ImageDraw.Draw(img)

    name = ImageFont.truetype(GEORGIA_B, int(86 * SS))
    tag = ImageFont.truetype(SEGOE, int(30 * SS))
    small = ImageFont.truetype(SEGOE, int(24 * SS))

    def width(text, font):
        box = d.textbbox((0, 0), text, font=font)
        return box[2] - box[0]

    wordmark = 'Zerokosh'
    tagline = 'Your financial life, sealed on your phone.'
    subline = 'No account.  No server.  No internet permission.'

    # Mark + wordmark on one baseline, the pair centred.
    unit = 2.5 * SS
    mark_w = 42 * unit          # the glyph spans x 6..48
    gap = 34 * SS
    name_w = width(wordmark, name)
    group = mark_w + gap + name_w
    left = (W - group) / 2
    mid = int(H * 0.42)

    draw_lock(img, left + mark_w / 2, mid, unit)
    d.text((left + mark_w + gap, mid), wordmark, font=name, fill=WHITE,
           anchor='lm')

    d.text((W / 2, int(H * 0.70)), tagline, font=tag, fill=(0xE8, 0xDC, 0xD0),
           anchor='mm')
    d.text((W / 2, int(H * 0.81)), subline, font=small, fill=MUTE, anchor='mm')

    # Anything wider than this is at risk when Play crops.
    safe = W - 120 * SS
    for text, font in ((tagline, tag), (subline, small)):
        assert width(text, font) <= safe, 'too wide for the safe area: %r' % text
    assert group <= safe, 'lockup too wide for the safe area'

    img = img.resize((w, h), Image.LANCZOS)
    path = os.path.join(OUT, 'feature-graphic-1024x500.png')
    img.save(path, 'PNG')
    return path


if __name__ == '__main__':
    for p in (app_icon(), feature_graphic()):
        print('%-46s %6.0f KB' % (os.path.relpath(p, ROOT), os.path.getsize(p) / 1024.0))
