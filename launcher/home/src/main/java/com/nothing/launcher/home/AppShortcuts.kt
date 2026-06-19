package com.nothing.launcher.home

import android.content.Context
import android.content.pm.LauncherApps
import android.os.Build
import android.os.Process
import android.os.UserHandle
import com.nothing.core.data.AppModel
import com.nothing.core.data.HomeItem

fun Context.getAppShortcuts(app: AppModel): List<ShortcutInfoWrapper> {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) return emptyList()
    val launcherApps = getSystemService(Context.LAUNCHER_APPS_SERVICE) as? LauncherApps ?: return emptyList()
    if (!launcherApps.hasShortcutHostPermission()) return emptyList()

    val query = LauncherApps.ShortcutQuery().apply {
        setQueryFlags(LauncherApps.ShortcutQuery.FLAG_MATCH_DYNAMIC or LauncherApps.ShortcutQuery.FLAG_MATCH_MANIFEST or LauncherApps.ShortcutQuery.FLAG_MATCH_PINNED)
        setPackage(app.packageName)
    }
    val userHandle = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Process.myUserHandle()
    } else {
        android.os.Process.myUserHandle()
    }
    return try {
        launcherApps.getShortcuts(query, userHandle)
            ?.map { ShortcutInfoWrapper(it.id, it.shortLabel?.toString() ?: it.longLabel?.toString() ?: "") }
            ?: emptyList()
    } catch (_: SecurityException) {
        emptyList()
    }
}

data class ShortcutInfoWrapper(val id: String, val label: String)
