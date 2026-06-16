package com.nothing.launcher.home

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.os.PowerManager
import android.provider.Settings

/**
 * Attempts to turn the screen off.
 * Prefers DevicePolicyManager lockNow if admin is granted, otherwise tries PowerManager.goToSleep
 * (requires system permission) and falls back to accessibility settings intent.
 */
fun Context.sleepScreen() {
    val dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as? DevicePolicyManager
    val admin = ComponentName(this, LauncherDeviceAdmin::class.java)
    if (dpm?.isAdminActive(admin) == true) {
        dpm.lockNow()
        return
    }

    val powerManager = getSystemService(Context.POWER_SERVICE) as? PowerManager
    try {
        @Suppress("MissingPermission")
        powerManager?.goToSleep(System.currentTimeMillis())
    } catch (_: Exception) {
        // Fallback: ask user to enable accessibility or device admin.
        val intent = android.content.Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).apply {
            flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }
}
