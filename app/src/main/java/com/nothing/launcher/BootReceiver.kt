package com.nothing.launcher

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Receiver that listens for BOOT_COMPLETED so the launcher can perform any
 * post-reboot initialisation (e.g., schedule widget updates).
 */
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // TODO: schedule recurring jobs / refresh widgets
        }
    }
}
