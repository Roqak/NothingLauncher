package com.nothing.launcher.dock

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nothing.core.data.AppModel
import com.nothing.core.icons.MonochromeAppIcon
import com.nothing.core.theme.NothingDimens

private const val DOCK_SLOTS = 5

@Composable
fun DockBar(
    modifier: Modifier = Modifier,
    viewModel: DockViewModel = viewModel(),
    onDrawerRequest: () -> Unit = {},
    onAppClick: ((AppModel) -> Unit)? = null,
    onAppLongClick: ((AppModel) -> Unit)? = null
) {
    val items by viewModel.dockItems.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp)
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(DOCK_SLOTS) { slot ->
            val item = items.find { it.cellX == slot }
            if (item != null) {
                MonochromeAppIcon(
                    app = item.app,
                    size = NothingDimens.IconNormal,
                    onClick = {
                        onAppClick?.invoke(item.app) ?: context.launchApp(item.app)
                    },
                    onLongClick = { onAppLongClick?.invoke(item.app) }
                )
            } else {
                DockIconSlot()
            }
        }
    }
}
