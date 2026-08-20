# -*- coding: utf-8 -*-
"""Render the imported logos as they will appear, labelled with the catalog entry each serves."""
import io
import os
import re

from PIL import Image, ImageDraw, ImageFont

ROOT = r'C:\Users\acer\bharatvault'
RES = os.path.join(ROOT, r'app\src\main\res\drawable')
GAL = os.path.join(ROOT, r'app\src\main\java\org\zerokosh\app\ui\gallery\TemplateGalleryScreen.kt')
OUT = os.path.join(ROOT, r'design\imported-logos-2.png')

STEMS = ['adityabirlacapital','airtelpaymentsbank','ausmallfinancebank','bandhanbank',
         'bankofmaharashtra','capitalsmallfinancebank','cheq','cityunionbank','csbbank',
         'dcbbank','dhanlaxmibank','equitassmallfinancebank','esafsmallfinancebank','famapp',
         'finopaymentsbank','hyderabadmetro','idfcfirstbank','indianoverseasbank',
         'indiapostpaymentsbank','indmoney','irctc','jammukashmirbank','janasmallfinancebank',
         'jiopaymentsbank','karnatakabank','karurvysyabank','kotaksecurities','mastercard',
         'muthootfinance','niyo','nsdlpaymentsbank','onecard','openmoney','paytmpaymentsbank',
         'razorpayx','rblbank','shivaliksmallfinancebank','slice','southindianbank',
         'suryodaysmallfinancebank','tamilnadmercantilebank','tatacapital','ucobank',
         'ujjivansmallfinancebank','unicards','unitysmallfinancebank','utkarshsmallfinancebank']


def norm(s):
    return re.sub(r'[^a-z0-9_]', '', s.lower())


src = io.open(GAL, encoding='utf-8').read()
titles = {}
for logo, title in re.findall(r'GalleryItem\(\s*"([^"]+)"\s*,\s*"([^"]+)"', src):
    titles.setdefault(norm(logo), title)


def font(sz, bold=False):
    for n in (('arialbd.ttf' if bold else 'arial.ttf'), 'segoeui.ttf'):
        try:
            return ImageFont.truetype(n, sz)
        except Exception:
            pass
    return ImageFont.load_default()


COLS, CELL, TILE, PAD = 4, 300, 108, 40
rows = (len(STEMS) + COLS - 1) // COLS
W = PAD * 2 + COLS * CELL
H = PAD * 2 + 90 + rows * CELL

img = Image.new('RGB', (W, H), '#F3F4F6')
d = ImageDraw.Draw(img)
d.text((PAD, PAD), 'Second logo batch — 47 files', font=font(30, True), fill='#16181D')
d.text((PAD, PAD + 42), 'shown on the app\'s card tile, labelled with the catalog entry each one serves',
       font=font(15), fill='#6B7280')

for i, stem in enumerate(STEMS):
    cx = PAD + (i % COLS) * CELL
    cy = PAD + 90 + (i // COLS) * CELL
    path = os.path.join(RES, 'logo_%s.png' % stem)

    tile = Image.new('RGB', (TILE, TILE), '#FFFFFF')
    if os.path.exists(path):
        logo = Image.open(path).convert('RGBA')
        logo.thumbnail((TILE - 20, TILE - 20), Image.LANCZOS)
        tile.paste(logo, ((TILE - logo.width) // 2, (TILE - logo.height) // 2), logo)
    else:
        ImageDraw.Draw(tile).text((10, 46), 'MISSING', font=font(14, True), fill='#9A1C33')

    img.paste(tile, (cx, cy))
    d.rectangle([cx, cy, cx + TILE - 1, cy + TILE - 1], outline='#DDE0E5')

    tx = cx + TILE + 12
    d.text((tx, cy + 8), 'logo_' + stem, font=font(13, True), fill='#BB4717')
    title = titles.get(stem, '— no catalog entry —')
    words, line, lines = title.split(), '', []
    for w in words:
        t = (line + ' ' + w).strip()
        if d.textlength(t, font=font(13)) > CELL - TILE - 24:
            lines.append(line)
            line = w
        else:
            line = t
    lines.append(line)
    for j, ln in enumerate(lines[:4]):
        d.text((tx, cy + 30 + j * 18), ln, font=font(13), fill='#16181D')

img.save(OUT)
print('wrote', OUT, img.size)
