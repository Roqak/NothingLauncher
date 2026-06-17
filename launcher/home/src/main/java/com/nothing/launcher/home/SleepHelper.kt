package com.nothing.launcher.home

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.os.PowerManager
import android.provider.Settings
import android.os.SystemClock

/**
 * Attempts to turn the screen off.
 * Prefers DevicePolicyManager lockNow if admin is granted, otherwise falls back to accessibility settings intent.
 */
fun Context.sleepScreen() {
    val dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as? DevicePolicyManager
    val admin = ComponentName(this, LauncherDeviceAdmin::class.java)
    if (dpm?.isAdminActive(admin) == true) {
        dpm.lockNow()
        return
    }

    // PowerManager.goToSleep requires DEVICE_POWER or a system signature.
    // Open device admin settings so the user can grant lock permission.
    val intent = android.content.Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
        putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, admin)
        flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK
    }
    if (packageManager.resolveActivity(intent, 0) != null) {
        startActivity(intent)
    } else {
        val fallback = android.content.Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).apply {
            flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(fallback)
    }
}
