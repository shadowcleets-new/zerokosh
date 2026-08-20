# -*- coding: utf-8 -*-
"""Find @Composable declarations that nothing anywhere references.

Deliberately conservative. A composable counts as dead only when the ONLY
occurrence of its name in the whole source tree is its own declaration. Kotlin
call sites take several shapes — Name(...), Name { ... } with a trailing lambda
and no parens, ::Name as a reference — so matching on "Name(" alone reports
live code as dead. That bias is the wrong way round for something that can
fail a build, so this counts bare name occurrences instead: it can miss a dead
composable, but it will not condemn a live one.
"""
import io
import os
import re
import sys

ROOT = sys.argv[1] if len(sys.argv) > 1 else r'C:\Users\acer\bharatvault\app\src'

DECL = re.compile(
    r'@Composable[^\n]*\n(?:\s*@[\w.]+(?:\([^)]*\))?\s*\n)*'
    r'\s*(?:internal\s+|private\s+|public\s+)?fun\s+([A-Z][A-Za-z0-9_]*)\s*\(')

files = {}
for base, _, names in os.walk(ROOT):
    for n in names:
        if n.endswith('.kt'):
            p = os.path.join(base, n)
            files[p] = io.open(p, encoding='utf-8', errors='replace').read()

decls = {}
for p, src in files.items():
    for m in DECL.finditer(src):
        decls.setdefault(m.group(1), []).append(p)

dead = []
for name, sites in sorted(decls.items()):
    total = 0
    for src in files.values():
        total += len(re.findall(r'(?<![A-Za-z0-9_])%s(?![A-Za-z0-9_])' % re.escape(name), src))
    # one occurrence per declaration site is the declaration itself
    if total <= len(sites):
        home = sites[0]
        vis = 'private' if re.search(
            r'private\s+fun\s+%s\s*\(' % re.escape(name), files[home]) else 'public'
        dead.append((name, os.path.relpath(home, ROOT), vis))

if dead:
    print('%-30s %-56s %s' % ('COMPOSABLE', 'FILE', 'VISIBILITY'))
    for row in dead:
        print('%-30s %-56s %s' % row)
    print()
print('%d composable(s) declared and never referenced' % len(dead))
sys.exit(1 if dead else 0)
