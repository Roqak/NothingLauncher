package com.nothing.launcher.dock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nothing.core.data.AppModel
import com.nothing.core.data.HomeItem
import com.nothing.core.data.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DockViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    val dockItems: StateFlow<List<HomeItem.AppIcon>> = homeRepository.observeDockItems()
        .map { items -> items.filterIsInstance<HomeItem.AppIcon>() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addApp(app: AppModel, slot: Int) {
        viewModelScope.launch {
            val current = homeRepository.observeDockItems().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList()).value
            val trimmed = current.filter { it.cellX != slot }.toMutableList()
            trimmed.add(
                HomeItem.AppIcon(
                    id = "dock_${app.packageName}/${app.activityName}",
                    app = app,
                    cellX = slot,
                    cellY = 0,
                    page = 0
                )
            )
            homeRepository.saveDockItems(trimmed)
        }
    }

    fun removeItem(item: HomeItem) {
        viewModelScope.launch {
            val current = homeRepository.observeDockItems().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList()).value
            homeRepository.saveDockItems(current.filter { it.id != item.id })
        }
    }
}
