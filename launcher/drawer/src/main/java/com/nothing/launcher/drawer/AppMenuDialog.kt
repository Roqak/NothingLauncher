package com.nothing.launcher.drawer

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
fun AppMenuDialog(
    app: AppModel,
    onDismiss: () -> Unit,
    onAddToHome: ((AppModel) -> Unit)? = null
) {
    val context = LocalContext.current
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
            TextButton(onClick = {
                context.openAppInfo(app)
                onDismiss()
            }) {
                Text("App info", color = MaterialTheme.colorScheme.onBackground)
            }
            TextButton(onClick = {
                context.uninstallApp(app)
                onDismiss()
            }) {
                Text("Uninstall", color = MaterialTheme.colorScheme.onBackground)
            }
            if (onAddToHome != null) {
                TextButton(onClick = {
                    onAddToHome(app)
                    onDismiss()
                }) {
                    Text("Add to home", color = MaterialTheme.colorScheme.onBackground)
                }
            }
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
