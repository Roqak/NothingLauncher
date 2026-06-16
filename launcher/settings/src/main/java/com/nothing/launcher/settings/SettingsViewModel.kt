package com.nothing.launcher.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nothing.core.data.LauncherPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferences: LauncherPreferences
) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> = combine(
        preferences.gridColumns,
        preferences.gridRows,
        preferences.showLabels,
        preferences.dockSlots
    ) { cols, rows, labels, dock ->
        SettingsUiState(
            gridColumns = cols,
            gridRows = rows,
            showLabels = labels,
            dockSlots = dock
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsUiState()
    )

    fun setGrid(columns: Int, rows: Int) {
        viewModelScope.launch { preferences.setGrid(columns, rows) }
    }

    fun setShowLabels(show: Boolean) {
        viewModelScope.launch { preferences.setShowLabels(show) }
    }

    fun setDockSlots(slots: Int) {
        viewModelScope.launch { preferences.setDockSlots(slots) }
    }
}

data class SettingsUiState(
    val gridColumns: Int = 4,
    val gridRows: Int = 6,
    val showLabels: Boolean = false,
    val dockSlots: Int = 4
)
