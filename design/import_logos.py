# -*- coding: utf-8 -*-
"""Import the supplied brand logos into res/drawable, replacing wrong ones.

Android drawable can't load .jfif, and these are display at ~44dp, so everything
is normalised to PNG at <=512px on the long edge.
"""
import os
import shutil
import sys

from PIL import Image

SRC = r'C:\Users\acer\Dropbox\PC\Downloads\Logos'
ROOT = r'C:\Users\acer\bharatvault'
RES = os.path.join(ROOT, r'app\src\main\res')
DEST = os.path.join(RES, 'drawable')
MAX_EDGE = 512

# supplied file -> drawable resource stem (verified against the gallery catalog)
MAP = {
    'DigiLocker.webp': 'digilocker',
    'Google Favicon.webp': 'gmail',            # catalog entry is "Google Account (Ecosystem)"
    'Maha Mumbai Metro.png': 'mahamumbaimetro',
    'aadhaar.webp': 'aadhaar',
    'bajaj finserv.png': 'bajajfinance',       # catalog title "Bajaj Finserv / Loan"
    'best electricity.jpg': 'bestelectricity',
    'dmrc metro.jfif': 'dmrc',
    'epfo.png': 'epfo',
    'fi-money logo.png': 'fimoney',
    'google pay.jfif': 'googlepay',
    'jupiter money.png': 'jupitermoney',
    'make my trip.jpg': 'makemytrip',
    'mmrc.png': 'mumbaimetro3',                # catalog: "Mumbai Metro Aqua Line 3 (MMRC)"
    'mumbai monorail.png': 'mumbaimonorail',   # no catalog entry yet
    'mumbai=metro-one.png': 'mumbaimetroone',
    'myjio.png': 'myjio',
    'nagpur-metro.png': 'nagpurmetro',
    'navi-mumbai-metro.png': 'navimumbaimetro',
    'pan-card.webp': 'pan',
    'paytm.webp': 'paytm',
    'pnb - punjab national bank.png': 'punjabnationalbank',
    'pune-metro.png': 'punemetro',
    'railone.png': 'railone',
    'rupay.png': 'rupay',
    'thane-metro.png': 'thanemetro',           # no catalog entry yet
    'visa card.png': 'visa',
}

apply = '--apply' in sys.argv


def existing(stem):
    """Every file in any res/drawable* folder that claims this resource name."""
    hits = []
    for d in os.listdir(RES):
        if not d.startswith('drawable'):
            continue
        folder = os.path.join(RES, d)
        if not os.path.isdir(folder):
            continue
        for f in os.listdir(folder):
            if f.rsplit('.', 1)[0] == 'logo_' + stem:
                hits.append(os.path.join(folder, f))
    return hits


rows, to_delete = [], []
for fname, stem in sorted(MAP.items(), key=lambda kv: kv[1]):
    src = os.path.join(SRC, fname)
    if not os.path.exists(src):
        rows.append((stem, fname, 'SOURCE MISSING', '', ''))
        continue

    old = existing(stem)
    action = 'replace' if old else 'add'
    # anything not the exact destination filename has to go, or the build sees
    # two files claiming one resource name
    dest = os.path.join(DEST, 'logo_%s.png' % stem)
    to_delete += [p for p in old if os.path.normcase(p) != os.path.normcase(dest)]

    im = Image.open(src)
    im = im.convert('RGBA' if (im.mode in ('RGBA', 'LA', 'P') and 'transparency' in im.info)
                    or im.mode in ('RGBA', 'LA') else 'RGB')
    w, h = im.size
    if max(w, h) > MAX_EDGE:
        s = MAX_EDGE / float(max(w, h))
        im = im.resize((max(1, int(w * s)), max(1, int(h * s))), Image.LANCZOS)

    if apply:
        im.save(dest, 'PNG', optimize=True)
        newkb = os.path.getsize(dest) // 1024
    else:
        newkb = '-'
    rows.append((stem, fname, action,
                 '%dx%d -> %dx%d' % (w, h, im.width, im.height),
                 '%s KB' % newkb))

print('%-22s %-32s %-8s %-22s %s' % ('RESOURCE', 'SOURCE', 'ACTION', 'SIZE', 'OUT'))
for r in rows:
    print('%-22s %-32s %-8s %-22s %s' % r)

print()
print('--- files that must be removed (stale name collisions) ---')
for p in to_delete:
    print('   ', p.replace(ROOT, ''))
if apply:
    for p in to_delete:
        os.remove(p)
    print('   removed %d' % len(to_delete))
else:
    print('   (dry run — re-run with --apply)')
