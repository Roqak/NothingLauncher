package com.nothing.core.data.di

import android.content.Context
import androidx.room.Room
import com.nothing.core.data.LauncherDatabase
import com.nothing.core.data.LauncherPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LauncherDatabase {
        return Room.databaseBuilder(
            context,
            LauncherDatabase::class.java,
            "nothing_launcher.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideHomeItemDao(database: LauncherDatabase) = database.homeItemDao()

    @Provides
    @Singleton
    fun provideLauncherPreferences(@ApplicationContext context: Context): LauncherPreferences {
        return LauncherPreferences(context)
    }

    @Provides
    @Singleton
    fun provideAppRepository(@ApplicationContext context: Context): AppRepository {
        return AppRepository(context)
    }
}
