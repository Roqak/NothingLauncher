package com.nothing.core.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeItemDao {

    @Query("SELECT * FROM home_items WHERE container = :container ORDER BY page, cellY, cellX")
    fun observeItems(container: String): Flow<List<HomeItemEntity>>

    @Query("SELECT * FROM home_items WHERE container = :container AND page = :page ORDER BY cellY, cellX")
    fun observePageItems(container: String, page: Int): Flow<List<HomeItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<HomeItemEntity>)

    @Delete
    suspend fun delete(item: HomeItemEntity)
}
