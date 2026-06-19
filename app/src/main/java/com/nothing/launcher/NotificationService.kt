package com.nothing.launcher

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.nothing.core.utils.BadgeCounter

class NotificationService : NotificationListenerService() {

    override fun onCreate() {
        super.onCreate()
        refresh()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        refresh()
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        refresh()
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
        refresh()
    }

    private fun refresh() {
        try {
            val grouped = activeNotifications
                .filter { !it.isOngoing }
                .groupingBy { it.packageName }
                .eachCount()
            BadgeCounter.update(grouped)
        } catch (_: SecurityException) {
            // ignore
        }
    }
}
