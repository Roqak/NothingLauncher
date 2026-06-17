package com.nothing.launcher.home

import android.content.Context
import android.content.Intent
import com.nothing.core.data.AppModel

fun Context.launchApp(app: AppModel) {
    val intent = packageManager.getLaunchIntentForPackage(app.packageName)
        ?.apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED }
    if (intent != null) startActivity(intent)
}
