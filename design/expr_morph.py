# -*- coding: utf-8 -*-
"""Morph on the hero blobs, and ShapeDefaults for the shape scale."""
import io

UI = 'app/src/main/java/org/zerokosh/app/ui/common/VaultUi.kt'
THEME = 'app/src/main/java/org/zerokosh/app/ui/theme/Theme.kt'


def rd(p):
    return io.open(p, encoding='utf-8').read()


def wr(p, s):
    io.open(p, 'w', encoding='utf-8', newline='\n').write(s)


def sub(s, old, new, path=''):
    assert old in s, 'ANCHOR MISSING %s:\n%r' % (path, old[:170])
    return s.replace(old, new, 1)


# ---- VaultBlobs: morphing shapes -----------------------------------------
s = rd(UI)
start = s.index('object VaultBlobs {')
end = s.index('\n}\n', start) + 3
new = '''/**
 * A [Shape] that interpolates between two MaterialShapes. Morph gives the path
 * at a progress value; the scale-and-centre step is the same one
 * RoundedPolygon.toShape() performs, so a morphing blob sits exactly where a
 * static one did.
 */
private class MorphShape(private val morph: Morph, private val progress: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val path = morph.toPath(progress)
        path.transform(Matrix().apply { scale(x = size.width, y = size.height) })
        path.translate(size.center - path.getBounds().center)
        return Outline.Generic(path)
    }
}

/**
 * Two MaterialShapes, breathed between. The blobs are decorative, so this is
 * exactly the kind of motion that has to stop when the user has asked the system
 * to reduce it — at animator scale 0 the shape is pinned at rest instead.
 */
@Composable
private fun breathingBlob(
    from: RoundedPolygon,
    to: RoundedPolygon,
    durationMillis: Int,
): Shape {
    val morph = remember(from, to) { Morph(from, to) }
    val context = LocalContext.current
    val animated = remember(context) {
        Settings.Global.getFloat(
            context.contentResolver,
            Settings.Global.ANIMATOR_DURATION_SCALE,
            1f,
        ) > 0f
    }
    if (!animated) return remember(morph) { MorphShape(morph, 0f) }

    val transition = rememberInfiniteTransition(label = "blob")
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "blobProgress",
    )
    // A new Shape per progress value is what makes clip() re-read the outline.
    return remember(morph, progress) { MorphShape(morph, progress) }
}

/**
 * The hero blobs. Each is a morph between two MaterialShapes rather than a
 * single static one, on deliberately mismatched periods so the composition never
 * repeats a pose.
 */
object VaultBlobs {
    /** Large soft organic mass — the hero's primary blob. */
    val Primary: Shape
        @Composable get() = breathingBlob(MaterialShapes.Puffy, MaterialShapes.Cookie9Sided, 9000)

    /** Petalled counterweight — the accent satellite. */
    val Accent: Shape
        @Composable get() = breathingBlob(MaterialShapes.Clover4Leaf, MaterialShapes.Flower, 11000)

    /** Small dark pebble anchoring the composition. */
    val Ink: Shape
        @Composable get() = breathingBlob(MaterialShapes.Cookie9Sided, MaterialShapes.Pill, 13000)
}
'''
s = s[:start] + new + s[end:]

for imp in [
    'import android.provider.Settings\n',
    'import androidx.compose.animation.core.LinearEasing\n',
    'import androidx.compose.animation.core.RepeatMode\n',
    'import androidx.compose.animation.core.animateFloat\n',
    'import androidx.compose.animation.core.infiniteRepeatable\n',
    'import androidx.compose.animation.core.rememberInfiniteTransition\n',
    'import androidx.compose.animation.core.tween\n',
    'import androidx.compose.ui.geometry.Size\n',
    'import androidx.compose.ui.geometry.center\n',
    'import androidx.compose.ui.graphics.Matrix\n',
    'import androidx.compose.ui.graphics.Outline\n',
    'import androidx.compose.ui.platform.LocalContext\n',
    'import androidx.compose.ui.unit.Density\n',
    'import androidx.compose.ui.unit.LayoutDirection\n',
    'import androidx.graphics.shapes.Morph\n',
    'import androidx.graphics.shapes.RoundedPolygon\n',
    'import androidx.compose.material3.toPath\n',
]:
    if imp not in s:
        s = s.replace('import androidx.compose.material3.Icon\n',
                      imp + 'import androidx.compose.material3.Icon\n', 1)
wr(UI, s)
print('VaultUi.kt: VaultBlobs morph between two MaterialShapes')

# ---- ShapeDefaults for the shape scale -----------------------------------
s = rd(THEME)
s = sub(s, '''private val BharatShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),   // rounded-2xl
    large = RoundedCornerShape(24.dp),    // rounded-3xl
    extraLarge = RoundedCornerShape(26.dp), // the mockup's grouped list card
)''',
        '''// The mockup's own corner values, expressed through the Expressive shape scale
// rather than five loose literals. ShapeDefaults supplies the two sizes M3
// added for Expressive — LargeIncreased and ExtraLargeIncreased — which the
// hand-written Shapes() could not name at all, so components asking for them
// were silently falling back.
private val BharatShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),          // rounded-2xl
    large = RoundedCornerShape(24.dp),           // rounded-3xl
    largeIncreased = ShapeDefaults.LargeIncreased,
    extraLarge = RoundedCornerShape(26.dp),      // the mockup's grouped list card
    extraLargeIncreased = ShapeDefaults.ExtraLargeIncreased,
    extraExtraLarge = ShapeDefaults.ExtraExtraLarge,
)''', THEME)
if 'import androidx.compose.material3.ShapeDefaults\n' not in s:
    s = s.replace('import androidx.compose.material3.Shapes\n',
                  'import androidx.compose.material3.ShapeDefaults\n'
                  'import androidx.compose.material3.Shapes\n', 1)
wr(THEME, s)
print('Theme.kt: shape scale completed via ShapeDefaults')
