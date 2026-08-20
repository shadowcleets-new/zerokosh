# -*- coding: utf-8 -*-
"""Which gallery entries resolve to a bundled drawable, and which fall back to a monogram."""
import io
import os
import re

ROOT = r'C:\Users\acer\bharatvault'
GAL = os.path.join(ROOT, r'app\src\main\java\org\zerokosh\app\ui\gallery\TemplateGalleryScreen.kt')
RES = os.path.join(ROOT, r'app\src\main\res')


def norm(s):
    return re.sub(r'[^a-z0-9_]', '', s.lower())


# bundled drawables -> set of resource names (extension stripped)
bundled = {}
for dirpath, _, names in os.walk(RES):
    for n in names:
        if n.startswith('logo_'):
            stem = n.rsplit('.', 1)[0]
            bundled.setdefault(stem, []).append(os.path.join(dirpath, n))

src = io.open(GAL, encoding='utf-8').read()
entries = re.findall(r'GalleryItem\(\s*"([^"]+)"\s*,\s*"([^"]+)"', src)

missing, present, dupes = [], [], []
for logo, title in entries:
    key = 'logo_' + norm(logo)
    if key in bundled:
        present.append((logo, title, key))
        if len(bundled[key]) > 1:
            dupes.append((key, bundled[key]))
    else:
        missing.append((logo, title, key))

print('gallery entries: %d | resolve: %d | fall back to monogram: %d'
      % (len(entries), len(present), len(missing)))
print()
print('=== MISSING (renders as a text monogram) ===')
for logo, title, key in missing:
    print('  %-34s %-42s -> %s' % (logo, title, key))

if dupes:
    print()
    print('=== DUPLICATE RESOURCE NAMES ===')
    for key, paths in sorted(set((k, tuple(v)) for k, v in dupes)):
        print('  %s' % key)
        for p in paths:
            print('      %s' % p.replace(ROOT, ''))
