package com.nothing.launcher

import android.app.Application
import com.nothing.core.data.AppChangeReceiver
import com.nothing.core.data.AppRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject

@HiltAndroidApp
class NothingLauncherApp : Application() {

    @Inject
    lateinit var appRepository: AppRepository

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private lateinit var appChangeReceiver: AppChangeReceiver

    override fun onCreate() {
        super.onCreate()
        appChangeReceiver = AppChangeReceiver(appRepository, applicationScope)
        appChangeReceiver.register(this)
    }
}
