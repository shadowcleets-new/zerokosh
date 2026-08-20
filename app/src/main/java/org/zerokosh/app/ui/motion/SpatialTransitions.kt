package org.zerokosh.app.ui.motion

import android.os.Build
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset

object HardwareProfiler {
    val isFlagshipTier: Boolean by lazy {
        val cores = Runtime.getRuntime().availableProcessors()
        val hardware = Build.HARDWARE.lowercase()

        // Pass 1: Strict Hardware/SoC profiling (Snapdragon 8 Gen 2+, Tensor G3+, Apple A16+)
        val flagshipSoCs = listOf("sm8550", "sm8650", "tensor g3", "exynos 2400")
        if (flagshipSoCs.any { hardware.contains(it) }) return@lazy true

        // Pass 2: Brute force capability check
        cores >= 8
    }
}

object SpatialPhysics {
    val fluidSpring: SpringSpec<Float> = spring(
        dampingRatio = 0.75f,
        stiffness = Spring.StiffnessMediumLow
    )

    val offsetSpring: SpringSpec<IntOffset> = spring(
        dampingRatio = 0.75f,
        stiffness = Spring.StiffnessMediumLow
    )
}

@OptIn(ExperimentalAnimationApi::class)
fun globalEnterTransition(): EnterTransition {
    return if (HardwareProfiler.isFlagshipTier) {
        slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = SpatialPhysics.offsetSpring
        ) + fadeIn(animationSpec = tween(200))
    } else {
        fadeIn(animationSpec = tween(150))
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun globalExitTransition(): ExitTransition {
    return if (HardwareProfiler.isFlagshipTier) {
        slideOutHorizontally(
            targetOffsetX = { -it / 10 },
            animationSpec = SpatialPhysics.offsetSpring
        ) + scaleOut(
            targetScale = 0.96f,
            animationSpec = SpatialPhysics.fluidSpring
        ) + fadeOut(animationSpec = tween(200))
    } else {
        fadeOut(animationSpec = tween(150))
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun globalPopEnterTransition(): EnterTransition {
    return if (HardwareProfiler.isFlagshipTier) {
        slideInHorizontally(
            initialOffsetX = { -it / 10 },
            animationSpec = SpatialPhysics.offsetSpring
        ) + scaleIn(
            initialScale = 0.96f,
            animationSpec = SpatialPhysics.fluidSpring
        ) + fadeIn(animationSpec = tween(200))
    } else {
        fadeIn(animationSpec = tween(150))
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun globalPopExitTransition(): ExitTransition {
    return if (HardwareProfiler.isFlagshipTier) {
        slideOutHorizontally(
            targetOffsetX = { it },
            animationSpec = SpatialPhysics.offsetSpring
        ) + fadeOut(animationSpec = tween(200))
    } else {
        fadeOut(animationSpec = tween(150))
    }
}
