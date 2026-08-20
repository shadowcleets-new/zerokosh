# -*- coding: utf-8 -*-
"""Mastercard shipped as SVG, which Android's drawable loader cannot read, and no
SVG rasteriser is installed. The mark is two circles plus their intersection, so
it is rebuilt from the source file's own geometry rather than guessed.

Circle centres and radius are taken from the supplied SVG's viewBox and path
data: r=309, centres (309,309) and (690.2,309).
"""
import os

from PIL import Image, ImageDraw, ImageChops

OUT = r'C:\Users\acer\bharatvault\app\src\main\res\drawable\logo_mastercard.png'

R = 309.0
C1 = (309.0, 309.0)     # red
C2 = (690.2, 309.0)     # yellow
W, H = 999.2, 618.0     # symbol only; the SVG's 776 height allows for the wordmark

RED, YELLOW, OVERLAP = (235, 0, 27), (247, 158, 27), (255, 95, 0)

SS = 4                  # supersample, then downscale for clean edges
TARGET_W = 512


def disc(size, centre, r):
    m = Image.new('L', size, 0)
    d = ImageDraw.Draw(m)
    cx, cy = centre
    d.ellipse([(cx - r) * SS, (cy - r) * SS, (cx + r) * SS, (cy + r) * SS], fill=255)
    return m


size = (int(W * SS), int(H * SS))
m1, m2 = disc(size, C1, R), disc(size, C2, R)
both = ImageChops.multiply(m1, m2)          # the lens where they overlap

img = Image.new('RGBA', size, (0, 0, 0, 0))
img.paste(Image.new('RGBA', size, RED + (255,)), (0, 0), m1)
img.paste(Image.new('RGBA', size, YELLOW + (255,)), (0, 0), m2)
img.paste(Image.new('RGBA', size, OVERLAP + (255,)), (0, 0), both)

scale = TARGET_W / float(size[0])
img = img.resize((TARGET_W, max(1, int(size[1] * scale))), Image.LANCZOS)
img.save(OUT, 'PNG', optimize=True)
print('wrote %s  %dx%d  %d KB' % (OUT, img.width, img.height, os.path.getsize(OUT) // 1024))
