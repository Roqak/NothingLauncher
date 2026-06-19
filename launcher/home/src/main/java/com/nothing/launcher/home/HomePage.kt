@file:OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)

package com.nothing.launcher.home

import com.nothing.core.data.HomeItem
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import com.nothing.core.icons.MonochromeAppIcon
import com.nothing.core.theme.NothingDimens
import kotlin.math.roundToInt

@Composable
fun HomePage(
    page: Int,
    items: List<HomeItem>,
    geometry: GridGeometry,
    onAppClick: (HomeItem.AppIcon) -> Unit = {},
    onAppLongClick: (HomeItem.AppIcon) -> Unit = {},
    onMoveItem: (HomeItem, Int, Int) -> Unit = { _, _, _ -> }
) {
    val density = LocalDensity.current
    val context = LocalContext.current
    var pageOffset by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { pageOffset = it.positionInRoot() }
    ) {
        items.forEach { item ->
            when (item) {
                is HomeItem.AppIcon -> {
                    val (baseX, baseY) = with(density) { gridPosition(geometry, item.cellX, item.cellY) }
                    var dragX by remember { mutableFloatStateOf(0f) }
                    var dragY by remember { mutableFloatStateOf(0f) }
                    var isDragging by remember { mutableStateOf(false) }

                    MonochromeAppIcon(
                        app = item.app,
                        size = NothingDimens.IconNormal,
                        modifier = Modifier
                            .size(NothingDimens.IconNormal)
                            .offset {
                                IntOffset(
                                    (baseX + dragX).roundToInt(),
                                    (baseY + dragY).roundToInt()
                                )
                            }
                            .pointerInput(item.id) {
                                detectDragGesturesAfterLongPress(
                                    onDragStart = {
                                        isDragging = true
                                        onAppLongClick(item)
                                    },
                                    onDrag = { change, dragAmount ->
                                        change.consume()
                                        dragX += dragAmount.x
                                        dragY += dragAmount.y
                                    },
                                    onDragEnd = {
                                        isDragging = false
                                        val iconSizePx = with(density) { NothingDimens.IconNormal.toPx() }
                                        val finalRootX = pageOffset.x + baseX + dragX + iconSizePx / 2f
                                        val finalRootY = pageOffset.y + baseY + dragY + iconSizePx / 2f
                                        val (targetCol, targetRow) = with(density) {
                                            snapToCell(
                                                geometry,
                                                finalRootX,
                                                finalRootY
                                            )
                                        }
                                        onMoveItem(item, targetCol, targetRow)
                                        dragX = 0f
                                        dragY = 0f
                                    },
                                    onDragCancel = {
                                        isDragging = false
                                        dragX = 0f
                                        dragY = 0f
                                    }
                                )
                            },
                        onClick = {
                            if (!isDragging) {
                                onAppClick(item)
                                context.launchApp(item.app)
                            }
                        },
                        onLongClick = {} // drag handles long press
                    )
                }
                is HomeItem.Folder -> { /* TODO */ }
            }
        }
    }
}
