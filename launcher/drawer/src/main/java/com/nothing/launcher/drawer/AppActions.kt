package com.nothing.launcher.drawer

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.nothing.core.data.AppModel

fun Context.openAppInfo(app: AppModel) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.parse("package:${app.packageName}")
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    startActivity(intent)
}

fun Context.uninstallApp(app: AppModel) {
    val intent = Intent(Intent.ACTION_DELETE).apply {
        data = Uri.parse("package:${app.packageName}")
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    startActivity(intent)
}
