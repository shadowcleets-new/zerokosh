# -*- coding: utf-8 -*-
"""Fail if the Play listing copy exceeds Google's field limits.

Cheaper to find here than in the Console, where an over-long description is
rejected only after you have pasted it in and pressed save.
"""
import io
import os
import re
import sys

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
LIMITS = {'App name': 30, 'Short description': 80, 'Full description': 4000}

text = io.open(os.path.join(ROOT, 'play', 'listing.md'), encoding='utf-8').read()
blocks = dict(
    re.findall(r'^## ([^(\n]+?)\s*\(\d+ max\)\s*\n+```\n(.*?)\n```',
               text, re.M | re.S)
)

failed = False
for field, limit in LIMITS.items():
    body = blocks.get(field)
    if body is None:
        print('MISSING  %s' % field)
        failed = True
        continue
    n = len(body.strip())
    ok = n <= limit
    failed |= not ok
    print('%-18s %4d / %4d  %s' % (field, n, limit, 'ok' if ok else 'TOO LONG'))

sys.exit(1 if failed else 0)
