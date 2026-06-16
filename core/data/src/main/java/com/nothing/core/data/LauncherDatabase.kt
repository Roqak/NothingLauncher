package com.nothing.core.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [HomeItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LauncherDatabase : RoomDatabase() {
    abstract fun homeItemDao(): HomeItemDao
}
