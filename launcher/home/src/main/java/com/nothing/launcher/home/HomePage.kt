package com.nothing.launcher.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import com.nothing.core.theme.NothingDimens
import kotlin.math.roundToInt

@Composable
fun HomePage(
    items: List<HomeItem>,
    geometry: GridGeometry,
    onAppClick: (HomeItem.AppIcon) -> Unit = {},
    onAppLongClick: (HomeItem.AppIcon) -> Unit = {}
) {
    val density = LocalDensity.current
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        items.forEach { item ->
            when (item) {
                is HomeItem.AppIcon -> {
                    val (x, y) = with(density) { gridPosition(geometry, item.cellX, item.cellY) }
                    MonochromeAppIcon(
                        app = item.app,
                        size = NothingDimens.IconNormal,
                        modifier = Modifier
                            .size(NothingDimens.IconNormal)
                            .offset { IntOffset(x.roundToInt(), y.roundToInt()) },
                        onClick = {
                            onAppClick(item)
                            context.launchApp(item.app)
                        },
                        onLongClick = { onAppLongClick(item) }
                    )
                }
                is HomeItem.Folder -> { /* TODO */ }
            }
        }
    }
}
