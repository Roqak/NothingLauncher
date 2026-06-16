package com.nothing.core.data

import android.content.pm.ApplicationInfo
import android.graphics.drawable.Drawable

/**
 * Domain model for an installed app visible to the launcher.
 */
data class AppModel(
    val packageName: String,
    val activityName: String,
    val label: String,
    val icon: Drawable,
    val isSystemApp: Boolean,
    val userId: Int
)
