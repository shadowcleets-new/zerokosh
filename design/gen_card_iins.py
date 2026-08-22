# -*- coding: utf-8 -*-
"""Build the bundled IIN lookup from bin-list-data, India only.

Source : https://github.com/venelinkochev/bin-list-data  (CC-BY-4.0)
Output : app/src/main/assets/card_iins.txt   — bundled, read at runtime
         design/bin-unmapped-issuers.csv     — NOT bundled; the triage pile

Nothing is discarded. A row with no issuer still carries Type and Category, so
it still resolves debit/credit and the card variant even when the bank cannot
be named — which is the HDFC case, where the chip exposed no kind either.

Format (v1), chosen so the parse is a split rather than a JSON walk:

    v1
    B<TAB>bank<TAB>bank<TAB>...        index table, -1 means unknown
    V<TAB>variant<TAB>variant<TAB>...  index table, -1 means unknown
    <bin> <bankIdx> <kind> <variantIdx>
    ...

kind is D | C | P | -   (debit, credit, prepaid, unknown)
"""
import csv
import io
import os
import re
import json
import collections

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.dirname(HERE)
SRC = os.path.join(
    r'C:\Users\acer\AppData\Local\Temp\claude\C--Users-acer'
    r'\64721c7d-5432-4629-bbcf-601459342fe9\scratchpad', 'bin-list-data.csv')
OUT = os.path.join(ROOT, 'app', 'src', 'main', 'assets', 'card_iins.txt')
REVIEW = os.path.join(HERE, 'bin-unmapped-issuers.csv')
PICKERS = os.path.join(ROOT, 'app', 'src', 'main', 'assets', 'pickers.json')

banks_picker = json.load(io.open(PICKERS, encoding='utf-8'))['pickers']['banks']

# Issuer strings that are not banks. They are co-brand partners or product
# names sitting in the Issuer column, and bundling them verbatim would label a
# card's bank as "Yatra Online". They keep their type and variant; they just
# never claim a bank.
NOT_A_BANK = re.compile(
    r'^(RUPAY|VISA|MASTERCARD|MAESTRO|JCB)\b'
    r'|TRAVEL|ONLINE|AIRLINES|AIRWAYS|RETAIL|MOBILITY|TECHNOL|SOLUTIONS'
    r'|PAYMENT SERVICES PVT|FINTECH|WALLET',
    re.I,
)

# Hand-checked aliases where the dataset's name will never fuzzy-match the
# picker. Kept small and explicit rather than clever.
ALIASES = {
    'SBI CARDS AND PAYMENT SERVICES': 'State Bank of India',
    'STATE BANK OF INDIA': 'State Bank of India',
    'SBI': 'State Bank of India',
    'HDFC': 'HDFC Bank',
    'ICICI': 'ICICI Bank',
    'IDBI': 'IDBI Bank',
    'AXIS': 'Axis Bank',
    'KOTAK MAHINDRA': 'Kotak Mahindra Bank',
    'YES': 'Yes Bank',
}


def squash(s):
    s = re.sub(r'[.,]', ' ', s.upper())
    s = re.sub(r'\b(LTD|LIMITED|THE|PVT|PRIVATE|CO|CORP|CORPORATION)\b', ' ', s)
    return re.sub(r'\s+', ' ', s).strip()


picker_squashed = {squash(b): b for b in banks_picker}


def map_bank(issuer):
    """A picker bank, or None. None is a fine answer — better than a wrong one."""
    raw = (issuer or '').strip()
    if not raw or NOT_A_BANK.search(raw):
        return None
    key = squash(raw)
    if key in picker_squashed:
        return picker_squashed[key]
    for alias, bank in ALIASES.items():
        if key.startswith(alias):
            return bank
    # containment, longest picker name first so "Bank of India" cannot swallow
    # "Central Bank of India"
    for pk in sorted(picker_squashed, key=len, reverse=True):
        if len(pk) >= 6 and (pk in key or key in pk):
            return picker_squashed[pk]
    return None


def map_kind(row):
    cat = (row.get('Category') or '').upper()
    if 'PREPAID' in cat:
        return 'P'
    t = (row.get('Type') or '').strip().upper()
    return {'DEBIT': 'D', 'CREDIT': 'C'}.get(t, '-')


def map_variant(row):
    """Category, minus the prepaid prefix that the kind already carries."""
    cat = re.sub(r'\bPREPAID\b', '', (row.get('Category') or '')).strip()
    cat = re.sub(r'\s+', ' ', cat).title()
    return cat or None


rows = [r for r in csv.DictReader(io.open(SRC, encoding='utf-8', errors='replace'))
        if (r.get('CountryName') or '').strip().lower() == 'india']

banks, variants = [], []
entries = {}
unmapped = collections.Counter()

for r in rows:
    bin_ = (r.get('BIN') or '').strip()
    if len(bin_) != 6 or not bin_.isdigit():
        continue
    bank = map_bank(r.get('Issuer'))
    if bank is None and (r.get('Issuer') or '').strip():
        unmapped[(r.get('Issuer') or '').strip()] += 1
    variant = map_variant(r)

    if bank and bank not in banks:
        banks.append(bank)
    if variant and variant not in variants:
        variants.append(variant)

    entry = (
        banks.index(bank) if bank else -1,
        map_kind(r),
        variants.index(variant) if variant else -1,
    )
    # first writer wins; the file is ordered and duplicates agree in practice
    entries.setdefault(bin_, entry)

lines = ['v1', 'B\t' + '\t'.join(banks), 'V\t' + '\t'.join(variants)]
for bin_ in sorted(entries):
    b, k, v = entries[bin_]
    lines.append('%s %d %s %d' % (bin_, b, k, v))

io.open(OUT, 'w', encoding='utf-8', newline='\n').write('\n'.join(lines) + '\n')

with io.open(REVIEW, 'w', encoding='utf-8', newline='') as fh:
    w = csv.writer(fh)
    w.writerow(['Issuer', 'BinCount', 'Note'])
    for issuer, n in unmapped.most_common():
        note = 'not-a-bank filter' if NOT_A_BANK.search(issuer) else 'no picker match'
        w.writerow([issuer, n, note])

named = sum(1 for e in entries.values() if e[0] >= 0)
kinded = sum(1 for e in entries.values() if e[1] != '-')
varied = sum(1 for e in entries.values() if e[2] >= 0)
print('india BINs bundled : %d' % len(entries))
print('  resolve a bank   : %d  (%.0f%%)' % (named, 100.0 * named / len(entries)))
print('  resolve a kind   : %d  (%.0f%%)' % (kinded, 100.0 * kinded / len(entries)))
print('  resolve a variant: %d  (%.0f%%)' % (varied, 100.0 * varied / len(entries)))
print('  distinct banks   : %d   variants: %d' % (len(banks), len(variants)))
print('asset  : %s  (%.0f KB)' % (OUT, os.path.getsize(OUT) / 1024.0))
print('triage : %s  (%d distinct unmapped issuers)' % (REVIEW, len(unmapped)))
