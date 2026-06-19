package com.nothing.launcher.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

private val GRID_OPTIONS = listOf(
    4 to 5, 4 to 6, 5 to 5, 5 to 6, 6 to 6
)

private fun Context.openWallpaperChooser() {
    val intent = Intent(Intent.ACTION_SET_WALLPAPER)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }
}

private fun Context.openLiveWallpaper(serviceName: String) {
    val intent = Intent("android.service.wallpaper.WallpaperService").apply {
        setPackage(packageName)
        putExtra("android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT", android.content.ComponentName(packageName, serviceName))
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    val chooser = Intent.createChooser(intent, "Set wallpaper")
    chooser.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(chooser)
}

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel(),
    onBack: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Launcher Settings",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 32.dp, bottom = 24.dp)
        )

        SectionHeader("Home Screen")
        GridSelector(
            selected = state.gridColumns to state.gridRows,
            onSelect = { viewModel.setGrid(it.first, it.second) }
        )

        ToggleRow(
            label = "Show app labels",
            checked = state.showLabels,
            onCheckedChange = viewModel::setShowLabels
        )

        SectionHeader("Dock")
        ToggleRow(
            label = "Dock slots",
            checked = state.dockSlots == 5,
            onCheckedChange = { viewModel.setDockSlots(if (it) 5 else 4) }
        )

        SectionHeader("Wallpaper")
        val context = LocalContext.current
        ActionRow(
            label = "Pick live wallpaper",
            onClick = { context.openWallpaperChooser() }
        )
        ActionRow(
            label = "Set Dot Field wallpaper",
            onClick = { context.openLiveWallpaper("com.nothing.launcher.wallpaper.DotFieldWallpaperService") }
        )

        SectionHeader("About")
        Text(
            text = "Nothing Launcher v1.0.0",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(vertical = 12.dp)
        )
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title.uppercase(),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
    )
}

@Composable
private fun GridSelector(
    selected: Pair<Int, Int>,
    onSelect: (Pair<Int, Int>) -> Unit
) {
    Column {
        GRID_OPTIONS.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelect(option) }
                    .padding(vertical = 12.dp)
            ) {
                Text(
                    text = "${option.first} × ${option.second}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )
                if (option == selected) {
                    Text(
                        text = "•",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
        }
    }
}

@Composable
private fun ActionRow(
    label: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "›",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
}

@Composable
private fun ToggleRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
}
