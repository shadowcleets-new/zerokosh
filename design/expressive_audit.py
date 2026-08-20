# -*- coding: utf-8 -*-
"""Which Material 3 Expressive APIs exist in the version we ship, and which we use."""
import io
import os
import re

M3 = (r'C:\Users\acer\AppData\Local\Temp\claude\C--Users-acer'
      r'\64721c7d-5432-4629-bbcf-601459342fe9\scratchpad\m3src\commonMain\androidx\compose\material3')
APP = r'C:\Users\acer\bharatvault\app\src\main\java'

# Every public declaration preceded (within 6 lines) by the Expressive opt-in,
# plus file-level opt-ins.
DECL = re.compile(r'^(?:@\w+(?:\([^)]*\))?\s*\n)*\s*(?:fun|val|object|class|interface)\s+([A-Za-z][A-Za-z0-9]*)', re.M)

available = {}
for fn in sorted(os.listdir(M3)):
    if not fn.endswith('.kt'):
        continue
    src = io.open(os.path.join(M3, fn), encoding='utf-8', errors='replace').read()
    if 'ExperimentalMaterial3ExpressiveApi' not in src:
        continue
    file_level = bool(re.search(r'^@file:.*ExperimentalMaterial3ExpressiveApi', src, re.M))
    lines = src.split('\n')
    for i, ln in enumerate(lines):
        m = re.match(r'^(?:public )?(?:fun|val|object) ([A-Z][A-Za-z0-9]*)', ln)
        if not m:
            continue
        window = '\n'.join(lines[max(0, i - 8):i])
        if file_level or 'ExperimentalMaterial3ExpressiveApi' in window:
            available.setdefault(m.group(1), fn)

# also sweep subpackages (carousel lives in one)
for sub in ('carousel',):
    d = os.path.join(M3, sub)
    if os.path.isdir(d):
        for fn in os.listdir(d):
            if not fn.endswith('.kt'):
                continue
            src = io.open(os.path.join(d, fn), encoding='utf-8', errors='replace').read()
            for m in re.finditer(r'^(?:public )?fun ([A-Z][A-Za-z0-9]*)', src, re.M):
                available.setdefault(m.group(1), sub + '/' + fn)

# what the app actually references
app_src = []
for root, _, files in os.walk(APP):
    for f in files:
        if f.endswith('.kt'):
            app_src.append(io.open(os.path.join(root, f), encoding='utf-8', errors='replace').read())
blob = '\n'.join(app_src)

used, unused = [], []
for name, fn in sorted(available.items()):
    if re.search(r'\b%s\b' % re.escape(name), blob):
        used.append((name, fn))
    else:
        unused.append((name, fn))

print('Expressive-gated public API in material3 1.5.0-alpha25: %d' % len(available))
print('  referenced by the app : %d' % len(used))
print('  not referenced        : %d' % len(unused))
print()
print('=== USED ===')
for n, f in used:
    print('  %-34s %s' % (n, f))
print()
print('=== NOT USED ===')
for n, f in unused:
    print('  %-34s %s' % (n, f))
