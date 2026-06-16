package com.nothing.core.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "nothing_launcher_prefs")

/**
 * User-facing launcher preferences persisted with DataStore.
 */
class LauncherPreferences(private val context: Context) {

    val gridColumns: Flow<Int> = context.dataStore.data.map { it[GRID_COLUMNS] ?: 4 }
    val gridRows: Flow<Int> = context.dataStore.data.map { it[GRID_ROWS] ?: 6 }
    val showLabels: Flow<Boolean> = context.dataStore.data.map { it[SHOW_LABELS] ?: false }
    val dockSlots: Flow<Int> = context.dataStore.data.map { it[DOCK_SLOTS] ?: 4 }
    val drawerSortOrder: Flow<String> = context.dataStore.data.map { it[DRAWER_SORT] ?: "AZ" }

    suspend fun setGrid(columns: Int, rows: Int) {
        context.dataStore.edit {
            it[GRID_COLUMNS] = columns
            it[GRID_ROWS] = rows
        }
    }

    suspend fun setShowLabels(show: Boolean) {
        context.dataStore.edit { it[SHOW_LABELS] = show }
    }

    suspend fun setDockSlots(slots: Int) {
        context.dataStore.edit { it[DOCK_SLOTS] = slots.coerceIn(4, 5) }
    }

    suspend fun setDrawerSortOrder(order: String) {
        context.dataStore.edit { it[DRAWER_SORT] = order }
    }

    companion object {
        private val GRID_COLUMNS = intPreferencesKey("grid_columns")
        private val GRID_ROWS = intPreferencesKey("grid_rows")
        private val SHOW_LABELS = booleanPreferencesKey("show_labels")
        private val DOCK_SLOTS = intPreferencesKey("dock_slots")
        private val DRAWER_SORT = stringPreferencesKey("drawer_sort")
    }
}
