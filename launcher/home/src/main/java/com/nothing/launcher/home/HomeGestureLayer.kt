package com.nothing.launcher.home

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue

/**
 * Simplified gesture layer for the home screen.
 * - Swipe up (vertical drag < -threshold): open app drawer
 * - Swipe down (vertical drag > threshold): expand notification shade
 * - Double-tap: sleep
 * - Long-press: wallpaper/widget/settings mode
 */
fun Modifier.homeGestureLayer(
    onSwipeUp: () -> Unit = {},
    onSwipeDown: () -> Unit = {},
    onDoubleTap: () -> Unit = {},
    onLongPress: () -> Unit = {}
): Modifier = pointerInput(Unit) {
    var startY = 0f
    var totalY = 0f

    detectDragGestures(
        onDragStart = { startY = it.y; totalY = 0f },
        onDragEnd = {
            val threshold = 80.dp.toPx()
            if (totalY.absoluteValue > threshold) {
                if (totalY < 0) onSwipeUp() else onSwipeDown()
            }
        }
    ) { change, dragAmount ->
        totalY += dragAmount.y
        change.consume()
    }
}
    .pointerInput(Unit) {
        detectTapGestures(
            onDoubleTap = { onDoubleTap() },
            onLongPress = { onLongPress() }
        )
    }
