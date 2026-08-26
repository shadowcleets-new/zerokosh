# -*- coding: utf-8 -*-
"""Convert raw device captures into Play-compliant phone screenshots.

The Pixel 9 is 1080x2424 — a 2.24:1 panel. Play wants 9:16 with the long side
no more than twice the short, so a raw capture is rejected.

Scaled to fit and padded, rather than cropped: cropping 504px off a 2424px
capture means losing either the status bar or the navigation bar, and both are
part of what makes a screenshot read as a real phone. The pad is the app's own
background colour, so the result looks framed rather than letterboxed.

Usage:  python design/make_play_screenshots.py
Input:  play/screenshots/*.png   (raw captures, edited in place -> *-play.png)
"""
import glob
import io
import os
import sys

from PIL import Image

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.dirname(HERE)
SHOTS = os.path.join(ROOT, 'play', 'screenshots')

TARGET_W, TARGET_H = 1080, 1920          # exactly 9:16
DARK = (0x12, 0x0C, 0x09)                # the app's dark paper
LIGHT = (0xFD, 0xF8, 0xF3)               # the app's light paper


def background_of(img):
    """Pad with whichever of the app's two grounds the capture actually uses."""
    corner = img.convert('RGB').getpixel((4, img.height // 2))
    return DARK if sum(corner) < 3 * 128 else LIGHT


def convert(path):
    img = Image.open(path).convert('RGB')
    scale = TARGET_H / img.height
    w = max(1, int(round(img.width * scale)))
    if w > TARGET_W:                      # a wider panel would need the other axis
        scale = TARGET_W / img.width
        w = TARGET_W
    h = max(1, int(round(img.height * scale)))
    resized = img.resize((w, h), Image.LANCZOS)

    canvas = Image.new('RGB', (TARGET_W, TARGET_H), background_of(img))
    canvas.paste(resized, ((TARGET_W - w) // 2, (TARGET_H - h) // 2))

    out = path[:-4] + '-play.png'
    canvas.save(out, 'PNG')
    return out


def main():
    raw = sorted(p for p in glob.glob(os.path.join(SHOTS, '*.png'))
                 if not p.endswith('-play.png'))
    if not raw:
        print('no captures in %s' % SHOTS)
        return 1
    for p in raw:
        out = convert(p)
        im = Image.open(out)
        ok = (im.width, im.height) == (TARGET_W, TARGET_H)
        print('%-34s -> %-34s %dx%d  %s' % (
            os.path.basename(p), os.path.basename(out), im.width, im.height,
            'ok' if ok else 'WRONG SIZE'))
        if not ok:
            return 1
    return 0


if __name__ == '__main__':
    sys.exit(main())
