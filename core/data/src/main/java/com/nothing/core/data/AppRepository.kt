package com.nothing.core.data

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.LauncherApps
import android.os.Build
import android.os.UserHandle
import android.os.UserManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val launcherApps: LauncherApps =
        context.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps

    private val userManager: UserManager =
        context.getSystemService(Context.USER_SERVICE) as UserManager

    private val appsFlow = MutableStateFlow<List<AppModel>>(emptyList())

    /**
     * Enumerates all launchable activities across all user profiles.
     */
    suspend fun loadApps(): List<AppModel> = withContext(Dispatchers.IO) {
        val apps = mutableListOf<AppModel>()

        val profiles = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            userManager.userProfiles
        } else {
            listOf(android.os.Process.myUserHandle())
        }

        for (profile in profiles) {
            val activities = launcherApps.getActivityList(null, profile)
            for (info in activities) {
                val appInfo = info.applicationInfo
                val isSystem = (appInfo?.flags?.and(ApplicationInfo.FLAG_SYSTEM)) != 0
                apps.add(
                    AppModel(
                        packageName = info.componentName.packageName,
                        activityName = info.componentName.className,
                        label = info.label.toString(),
                        icon = info.getBadgedIcon(0),
                        isSystemApp = isSystem,
                        userId = profile.hashCode()
                    )
                )
            }
        }

        apps.sortedBy { it.label.lowercase() }
    }

    /**
     * Cold Flow that emits the current app list every time it is collected.
     */
    fun observeApps(): Flow<List<AppModel>> = flow {
        emit(loadApps())
    }.flowOn(Dispatchers.IO)

    /**
     * In-memory hot Flow updated by the launcher package-change receiver.
     */
    fun apps(): Flow<List<AppModel>> = appsFlow

    internal fun updateApps(apps: List<AppModel>) {
        appsFlow.value = apps
    }
}
