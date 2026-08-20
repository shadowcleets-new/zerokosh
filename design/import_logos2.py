# -*- coding: utf-8 -*-
"""Second logo batch: fill the entries still rendering as text monograms."""
import io
import os
import re
import sys

from PIL import Image

SRC = r'C:\Users\acer\Dropbox\PC\Downloads\Logos\new logos'
ROOT = r'C:\Users\acer\bharatvault'
RES = os.path.join(ROOT, r'app\src\main\res')
DEST = os.path.join(RES, 'drawable')
GAL = os.path.join(ROOT, r'app\src\main\java\org\zerokosh\app\ui\gallery\TemplateGalleryScreen.kt')
MAX_EDGE = 512

MAP = {
    'Airtel-Money-Logo.png': 'airtelpaymentsbank',
    'BANDHAN BANK.png': 'bandhanbank',
    'CITY UNION BANK.png': 'cityunionbank',
    'CSB BANK.png': 'csbbank',
    'Hyderabad Metro Rail.webp': 'hyderabadmetro',
    'IDFC FIRST BANK.png': 'idfcfirstbank',
    'India_Post_Payments_Bank_logo.png': 'indiapostpaymentsbank',
    'Jammu-and-Kashmir-Bank-Logo.webp': 'jammukashmirbank',
    'Jio-payments-bank.webp': 'jiopaymentsbank',
    'KARNATAKA BANK.png': 'karnatakabank',
    'Karur Vysya Bank.webp': 'karurvysyabank',
    'MAHARASHTRA BANK.png': 'bankofmaharashtra',
    'MUTHOOT FINANCE.png': 'muthootfinance',
    'OneCard Logo.png': 'onecard',
    'RBL BANK.png': 'rblbank',
    'RazorpayX-logo.png': 'razorpayx',
    'SOUTH INDIAN BANK.png': 'southindianbank',
    'aditya birla capital.jfif': 'adityabirlacapital',
    'au small finance bank.png': 'ausmallfinancebank',
    'capital small finance bank.jfif': 'capitalsmallfinancebank',
    'cheq logo.png': 'cheq',
    'dcb bank.jfif': 'dcbbank',
    'dhanlaxmi bank.jfif': 'dhanlaxmibank',
    'equitas small finance bank.png': 'equitassmallfinancebank',
    'esaf small finance bank.jfif': 'esafsmallfinancebank',
    'fampay logo.jpg': 'famapp',
    'fino payments bank.jfif': 'finopaymentsbank',
    'indian-overseas-bank-logo.png': 'indianoverseasbank',
    'indmoney.png': 'indmoney',
    'irctc.webp': 'irctc',
    'jana small finance bank.jpg': 'janasmallfinancebank',
    'kotak securities.png': 'kotaksecurities',
    'niyo global.png': 'niyo',
    'nsdl payments bank.jfif': 'nsdlpaymentsbank',
    'open money logo.jfif': 'openmoney',
    'paytm-payments-bank-logo.png': 'paytmpaymentsbank',
    'shivalik small finance bank.png': 'shivaliksmallfinancebank',
    'slice logo.png': 'slice',
    'suryoday small finance bank.jfif': 'suryodaysmallfinancebank',
    'tamilnad mercantile bank.png': 'tamilnadmercantilebank',
    'tata capital.jfif': 'tatacapital',
    'uco-bank-logo.png': 'ucobank',
    'ujjivan small finance bank.jfif': 'ujjivansmallfinancebank',
    'uni_card.png': 'unicards',
    'unity small finance bank.png': 'unitysmallfinancebank',
    'utkarsh small finance bank.jfif': 'utkarshsmallfinancebank',
}
# 'Mastercard-logo.svg' handled separately — SVG becomes a vector drawable.
# 'federal bank.htm' is a Radware captcha page, not artwork. Skipped.

apply = '--apply' in sys.argv


def norm(s):
    return re.sub(r'[^a-z0-9_]', '', s.lower())


catalog = {norm(l) for l, _ in re.findall(
    r'GalleryItem\(\s*"([^"]+)"\s*,\s*"([^"]+)"', io.open(GAL, encoding='utf-8').read())}


def existing(stem):
    hits = []
    for d in os.listdir(RES):
        folder = os.path.join(RES, d)
        if d.startswith('drawable') and os.path.isdir(folder):
            hits += [os.path.join(folder, f) for f in os.listdir(folder)
                     if f.rsplit('.', 1)[0] == 'logo_' + stem]
    return hits


rows, bad, to_delete = [], [], []
for fname, stem in sorted(MAP.items(), key=lambda kv: kv[1]):
    if stem not in catalog:
        bad.append((fname, stem))
        continue
    src = os.path.join(SRC, fname)
    if not os.path.exists(src):
        rows.append((stem, fname, 'NO SOURCE', '', ''))
        continue
    old = existing(stem)
    dest = os.path.join(DEST, 'logo_%s.png' % stem)
    to_delete += [p for p in old if os.path.normcase(p) != os.path.normcase(dest)]

    im = Image.open(src)
    has_alpha = im.mode in ('RGBA', 'LA') or (im.mode == 'P' and 'transparency' in im.info)
    im = im.convert('RGBA' if has_alpha else 'RGB')
    w, h = im.size
    if max(w, h) > MAX_EDGE:
        sc = MAX_EDGE / float(max(w, h))
        im = im.resize((max(1, int(w * sc)), max(1, int(h * sc))), Image.LANCZOS)
    if apply:
        im.save(dest, 'PNG', optimize=True)
        out = '%d KB' % (os.path.getsize(dest) // 1024)
    else:
        out = '-'
    rows.append((stem, fname[:30], 'replace' if old else 'add',
                 '%dx%d->%dx%d' % (w, h, im.width, im.height), out))

print('%-27s %-31s %-8s %-20s %s' % ('RESOURCE', 'SOURCE', 'ACTION', 'SIZE', 'OUT'))
for r in rows:
    print('%-27s %-31s %-8s %-20s %s' % r)

if bad:
    print('\n!! target not found in the gallery catalog — would never display:')
    for f, s_ in bad:
        print('   %s -> logo_%s' % (f, s_))

if to_delete:
    print('\n--- stale files to remove ---')
    for p in to_delete:
        print('   ', p.replace(ROOT, ''))
    if apply:
        for p in to_delete:
            os.remove(p)

print('\n%d imported, %d rejected' % (len([r for r in rows if r[2] in ('add', 'replace')]), len(bad)))
if not apply:
    print('(dry run — re-run with --apply)')
