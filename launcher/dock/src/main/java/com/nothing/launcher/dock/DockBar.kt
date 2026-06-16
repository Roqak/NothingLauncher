package com.nothing.launcher.dock

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun DockBar(
    modifier: Modifier = Modifier,
    onDrawerRequest: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp)
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .pointerInput(Unit) {
                // Swipe-up on dock opens the app drawer.
                detectTapGestures(
                    onTap = {},
                    onLongPress = {}
                )
            },
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // TODO: render dock icons from persisted state
        repeat(4) {
            DockIconSlot()
        }
    }
}
