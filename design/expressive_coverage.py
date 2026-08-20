# -*- coding: utf-8 -*-
"""Coverage of the Material 3 Expressive surface, as published in the
compose-material3 1.4/1.5 release notes, against this app."""
import io
import os
import re

APP = r'C:\Users\acer\bharatvault\app\src\main\java'
M3 = (r'C:\Users\acer\AppData\Local\Temp\claude\C--Users-acer'
      r'\64721c7d-5432-4629-bbcf-601459342fe9\scratchpad\m3src\commonMain\androidx\compose\material3')

# group -> [(api, note)]
SURFACE = [
    ('Theme & foundations', [
        ('MaterialExpressiveTheme', 'the Expressive theme entry point'),
        ('MotionScheme', 'spring-based motion scheme'),
        ('expressiveLightColorScheme', 'prebuilt expressive colour scheme'),
        ('MaterialShapes', '35-shape library'),
        ('Morph', 'animated shape morphing between two MaterialShapes'),
        ('ShapeDefaults', 'expressive corner defaults'),
    ]),
    ('Navigation', [
        ('ShortNavigationBar', 'compact bottom bar'),
        ('WideNavigationRail', 'side rail for wide windows'),
        ('NavigationSuiteScaffold', 'adaptive nav — separate artifact'),
    ]),
    ('App bars & toolbars', [
        ('MediumFlexibleTopAppBar', 'flexible medium top bar'),
        ('LargeFlexibleTopAppBar', 'flexible large top bar'),
        ('TwoRowsTopAppBar', 'two-row top bar'),
        ('FlexibleBottomAppBar', 'flexible bottom bar'),
        ('AppBarRow', 'overflow-aware app bar actions'),
        ('AppBarColumn', 'vertical overflow-aware actions'),
        ('HorizontalFloatingToolbar', 'floating toolbar'),
        ('VerticalFloatingToolbar', 'vertical floating toolbar'),
    ]),
    ('Buttons', [
        ('ToggleButton', 'shape-morphing toggle'),
        ('ButtonGroup', 'connected group that squishes on press'),
        ('SplitButton', 'action + menu affordance'),
        ('ElevatedToggleButton', 'toggle variant'),
        ('FilledTonalToggleButton', 'toggle variant'),
        ('OutlinedToggleButton', 'toggle variant'),
        ('ButtonDefaults.MediumContainerHeight', 'expressive button size scale'),
    ]),
    ('Progress & loading', [
        ('LinearWavyProgressIndicator', 'wavy determinate bar'),
        ('CircularWavyProgressIndicator', 'wavy determinate ring'),
        ('LoadingIndicator', 'morphing-shape busy indicator'),
        ('ContainedLoadingIndicator', 'loading indicator on a container'),
    ]),
    ('FAB', [
        ('FloatingActionButtonMenu', 'radial FAB menu'),
        ('ToggleFloatingActionButton', 'FAB that morphs open/closed'),
        ('animateFloatingActionButton', 'scroll-aware FAB show/hide'),
    ]),
    ('Carousel', [
        ('HorizontalMultiBrowseCarousel', 'multi-size browsing carousel'),
        ('HorizontalUncontainedCarousel', 'uncontained carousel'),
        ('HorizontalCenteredHeroCarousel', 'centred hero carousel'),
    ]),
    ('Search', [
        ('SearchBarState', 'expandable search bar state'),
        ('TopSearchBar', 'search bar in the app bar slot'),
        ('ExpandedFullScreenSearchBar', 'full-screen expanded search'),
        ('ExpandedDockedSearchBar', 'docked expanded search'),
    ]),
    ('Inputs', [
        ('TextFieldLabelPosition', 'Inside / Cutout label positions'),
        ('tonalColors', 'expressive tonal text-field colours'),
        ('roundedShape', 'expressive rounded text-field shape'),
        ('ScrollField', 'scrollable number field'),
        ('TimePicker', 'expressive scroll time picker'),
    ]),
    ('Other components', [
        ('FilterChip', 'chip (shape-morph overload available)'),
        ('DropdownMenuItem', 'expressive menu item overloads'),
        ('rememberBottomSheetState', 'unified sheet state'),
        ('Scrim', 'standalone scrim for modals'),
        ('RippleThemeConfiguration', 'inset focus rings'),
        ('VerticalSlider', 'vertical slider'),
        ('PullToRefreshBox', 'expressive pull to refresh'),
    ]),
]

blob = []
for root, _, files in os.walk(APP):
    for f in files:
        if f.endswith('.kt'):
            blob.append(io.open(os.path.join(root, f), encoding='utf-8', errors='replace').read())
blob = '\n'.join(blob)

m3_files = set(os.listdir(M3)) if os.path.isdir(M3) else set()
m3_blob = ''
for fn in m3_files:
    p = os.path.join(M3, fn)
    if os.path.isfile(p):
        m3_blob += io.open(p, encoding='utf-8', errors='replace').read()
for sub in ('carousel',):
    d = os.path.join(M3, sub)
    if os.path.isdir(d):
        for fn in os.listdir(d):
            m3_blob += io.open(os.path.join(d, fn), encoding='utf-8', errors='replace').read()

done = total = 0
for group, items in SURFACE:
    print('\n## %s' % group)
    for api, note in items:
        key = api.split('.')[-1]
        used = bool(re.search(r'\b%s\b' % re.escape(key), blob))
        avail = bool(re.search(r'\b%s\b' % re.escape(key), m3_blob))
        total += 1
        done += used
        mark = 'USED   ' if used else ('open   ' if avail else 'N/A    ')
        print('  [%s] %-32s %s' % (mark.strip()[:4], api, note))

print('\n%d of %d surfaced APIs referenced by the app' % (done, total))
