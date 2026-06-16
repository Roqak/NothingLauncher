package com.nothing.launcher.drawer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nothing.core.data.AppModel
import com.nothing.core.data.AppRepository
import com.nothing.core.data.LauncherPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawerViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val preferences: LauncherPreferences
) : ViewModel() {

    private val query = MutableStateFlow("")

    val uiState: StateFlow<DrawerUiState> = combine(
        appRepository.observeApps(),
        query,
        preferences.drawerSortOrder
    ) { apps, q, sort ->
        val filtered = if (q.isBlank()) apps else apps.filter {
            it.label.contains(q, ignoreCase = true)
        }
        val sorted = when (sort.uppercase()) {
            "ZA" -> filtered.sortedByDescending { it.label.lowercase() }
            "MOST_USED" -> filtered // TODO: sort by usage stats
            else -> filtered.sortedBy { it.label.lowercase() }
        }

        DrawerUiState(
            allApps = sorted,
            query = q,
            recentApps = apps.takeLast(4) // placeholder until usage stats exist
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DrawerUiState()
    )

    fun onQueryChange(newQuery: String) {
        query.value = newQuery
    }

    fun clearQuery() {
        query.value = ""
    }

    fun onAppLaunched(app: AppModel) {
        // TODO: persist usage
    }
}

data class DrawerUiState(
    val allApps: List<AppModel> = emptyList(),
    val recentApps: List<AppModel> = emptyList(),
    val query: String = ""
)
