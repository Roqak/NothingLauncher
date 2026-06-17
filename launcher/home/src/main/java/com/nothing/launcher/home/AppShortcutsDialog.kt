package com.nothing.launcher.home

import android.content.pm.LauncherApps
import android.os.Build
import android.os.Process
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.nothing.core.data.AppModel

@Composable
fun AppShortcutsDialog(
    app: AppModel,
    onDismiss: () -> Unit,
    onRemoveFromHome: () -> Unit
) {
    val context = LocalContext.current
    val shortcuts = context.getAppShortcuts(app)

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = app.label,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            shortcuts.forEach { shortcut ->
                TextButton(onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                        val launcherApps = context.getSystemService(android.content.Context.LAUNCHER_APPS_SERVICE) as? LauncherApps
                        launcherApps?.startShortcut(app.packageName, shortcut.id, null, null, Process.myUserHandle())
                    }
                    onDismiss()
                }) {
                    Text(shortcut.label, color = MaterialTheme.colorScheme.onBackground)
                }
            }
            TextButton(onClick = {
                onRemoveFromHome()
                onDismiss()
            }) {
                Text("Remove from home", color = MaterialTheme.colorScheme.onBackground)
            }
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
