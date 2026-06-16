package com.nothing.core.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * Listens for package add/remove/change events and refreshes the app list.
 */
class AppChangeReceiver(
    private val repository: AppRepository,
    private val externalScope: CoroutineScope
) : BroadcastReceiver() {

    private val internalScope = CoroutineScope(SupervisorJob() + externalScope.coroutineContext)

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action in watchedActions) {
            internalScope.launch {
                repository.updateApps(repository.loadApps())
            }
        }
    }

    fun register(context: Context) {
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_PACKAGE_ADDED)
            addAction(Intent.ACTION_PACKAGE_REMOVED)
            addAction(Intent.ACTION_PACKAGE_CHANGED)
            addAction(Intent.ACTION_PACKAGE_REPLACED)
            addDataScheme("package")
        }
        context.registerReceiver(this, filter)
    }

    fun unregister(context: Context) {
        context.unregisterReceiver(this)
        internalScope.cancel()
    }

    companion object {
        private val watchedActions = setOf(
            Intent.ACTION_PACKAGE_ADDED,
            Intent.ACTION_PACKAGE_REMOVED,
            Intent.ACTION_PACKAGE_CHANGED,
            Intent.ACTION_PACKAGE_REPLACED
        )
    }
}
