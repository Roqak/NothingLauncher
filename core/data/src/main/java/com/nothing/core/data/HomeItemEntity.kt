package com.nothing.core.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a single item on the home screen or dock.
 */
@Entity(tableName = "home_items")
data class HomeItemEntity(
    @PrimaryKey val id: String,
    val itemType: String, // ICON, FOLDER, WIDGET
    val packageName: String?,
    val activityName: String?,
    val container: String, // HOME, DOCK, FOLDER_ID
    val page: Int,
    val cellX: Int,
    val cellY: Int,
    val spanX: Int = 1,
    val spanY: Int = 1,
    val title: String?,
    val iconBlob: ByteArray? = null
)
