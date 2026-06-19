package com.nothing.core.data

import android.content.Context
import com.nothing.core.data.AppRepository
import com.nothing.core.data.HomeItemDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appRepository: AppRepository,
    private val homeItemDao: HomeItemDao
) {
    /**
     * Persisted home screen items as domain objects, joined with live app data.
     */
    fun observeHomeItems(): Flow<List<HomeItem>> {
        return homeItemDao.observeItems("HOME").map { entities ->
            entities.mapNotNull { it.toHomeItem() }
        }
    }

    /**
     * Persisted dock icons as domain objects, joined with live app data.
     */
    fun observeDockItems(): Flow<List<HomeItem>> {
        return homeItemDao.observeItems("DOCK").map { entities ->
            entities.mapNotNull { it.toHomeItem() }
        }
    }

    suspend fun saveItems(items: List<HomeItem>) {
        val entities = items.map { it.toEntity(container = "HOME") }
        homeItemDao.insertAll(entities)
    }

    suspend fun saveDockItems(items: List<HomeItem>) {
        val entities = items.map { it.toEntity(container = "DOCK") }
        homeItemDao.insertAll(entities)
    }

    private suspend fun HomeItemEntity.toHomeItem(): HomeItem? {
        val app = appRepository.loadApps().find {
            it.packageName == packageName && it.activityName == activityName
        } ?: return null

        return HomeItem.AppIcon(
            id = id,
            app = app,
            cellX = cellX,
            cellY = cellY,
            page = page
        )
    }

    private fun HomeItem.toEntity(container: String): HomeItemEntity {
        return when (this) {
            is HomeItem.AppIcon -> HomeItemEntity(
                id = id,
                itemType = "ICON",
                packageName = app.packageName,
                activityName = app.activityName,
                container = container,
                page = page,
                cellX = cellX,
                cellY = cellY,
                title = app.label
            )
            is HomeItem.Folder -> HomeItemEntity(
                id = id,
                itemType = "FOLDER",
                packageName = null,
                activityName = null,
                container = container,
                page = page,
                cellX = cellX,
                cellY = cellY,
                title = "Folder"
            )
        }
    }
}
