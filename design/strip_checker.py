# -*- coding: utf-8 -*-
"""Some sources arrived with the transparency checkerboard baked in as pixels —
someone screenshotted a preview instead of saving the asset. On the app's card
tile that shows as a grey grid, and it is worst in dark mode where a light
chequered slab sits behind the mark.

A naive colour flood-fill does NOT work here: the checker alternates between two
greys (e.g. 234 and 252) and any tolerance tight enough to spare the artwork is
too tight to cross between them. So the checker tones are identified first from
the border ring, a binary "is a checker tone" mask is built, and the fill runs on
that mask instead — it crosses the alternation freely while never leaving it.

Aborts on any image where it would clear most of the frame, rather than
returning a hollowed-out logo.
"""
import os
from collections import Counter

from PIL import Image, ImageDraw

RES = r'C:\Users\acer\bharatvault\app\src\main\res\drawable'
TARGETS = ['airtelpaymentsbank', 'esafsmallfinancebank', 'irctc', 'ucobank',
           'utkarshsmallfinancebank']
TONE_TOL = 10       # how close a pixel must be to a checker tone
MAX_CLEAR = 0.80    # refuse to clear more of the frame than this


def border_tones(rgb):
    """The two dominant bright near-greys around the edge."""
    w, h = rgb.size
    ring = []
    for x in range(w):
        ring += [rgb.getpixel((x, 0)), rgb.getpixel((x, h - 1))]
    for y in range(h):
        ring += [rgb.getpixel((0, y)), rgb.getpixel((w - 1, y))]
    greys = [p for p in ring if abs(p[0] - p[1]) < 8 and abs(p[1] - p[2]) < 8 and p[0] > 185]
    if not greys:
        return []
    common = [c for c, _ in Counter(greys).most_common(6)]
    tones = []
    for c in common:
        if all(abs(c[0] - t[0]) > TONE_TOL for t in tones):
            tones.append(c)
    return tones[:3]


def strip(stem):
    path = os.path.join(RES, 'logo_%s.png' % stem)
    src = Image.open(path)
    rgb = src.convert('RGB')
    w, h = rgb.size
    tones = border_tones(rgb)
    if len(tones) < 2:
        print('%-26s no checker tone pair found — left alone' % stem)
        return

    # binary mask: 255 where the pixel is one of the checker tones
    mask = Image.new('L', (w, h), 0)
    pm, pr = mask.load(), rgb.load()
    for y in range(h):
        for x in range(w):
            r, g, b = pr[x, y]
            if any(abs(r - t[0]) <= TONE_TOL and abs(g - t[1]) <= TONE_TOL
                   and abs(b - t[2]) <= TONE_TOL for t in tones):
                pm[x, y] = 255

    # fill the mask from the border; thresh=0 keeps it inside the mask exactly
    for s in ([(x, 0) for x in range(0, w, 2)] + [(x, h - 1) for x in range(0, w, 2)] +
              [(0, y) for y in range(0, h, 2)] + [(w - 1, y) for y in range(0, h, 2)]):
        if pm[s] == 255:
            ImageDraw.floodfill(mask, s, 128, thresh=0)

    cleared = sum(1 for y in range(h) for x in range(w) if pm[x, y] == 128)
    pct = cleared / float(w * h)
    if pct > MAX_CLEAR:
        print('%-26s would clear %.0f%% — refused, source needs replacing' % (stem, pct * 100))
        return

    out = src.convert('RGBA')
    po = out.load()
    for y in range(h):
        for x in range(w):
            if pm[x, y] == 128:
                r, g, b, _ = po[x, y]
                po[x, y] = (r, g, b, 0)
    out.save(path, 'PNG', optimize=True)
    print('%-26s tones=%-28s cleared %4.1f%%  %d KB'
          % (stem, ' '.join('%d' % t[0] for t in tones), pct * 100,
             os.path.getsize(path) // 1024))


for t in TARGETS:
    strip(t)
