package com.nothing.launcher.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nothing.core.data.AppModel
import com.nothing.core.data.AppRepository
import com.nothing.core.data.LauncherPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val homeRepository: HomeRepository,
    private val preferences: LauncherPreferences
) : ViewModel() {

    private val dragState = MutableStateFlow<DragState>(DragState.Idle)

    val uiState: StateFlow<HomeUiState> = combine(
        homeRepository.observeHomeItems(),
        preferences.gridColumns,
        preferences.gridRows,
        dragState
    ) { items, cols, rows, drag ->
        val effectiveItems = items.ifEmpty { fakeHomeItems(appRepository.loadApps(), cols, rows) }
        HomeUiState(
            apps = appRepository.loadApps(),
            columns = cols,
            rows = rows,
            dragState = drag,
            items = effectiveItems
        )
    }.flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState()
        )

    fun onAppLaunched(app: AppModel) {
        // TODO: increment usage stats for "Most used" drawer sort
    }

    fun startDrag(item: HomeItem) {
        dragState.value = DragState.Dragging(
            item = item,
            startCellX = item.cellX,
            startCellY = item.cellY
        )
    }

    fun updateDragPosition(page: Int, cellX: Int, cellY: Int) {
        dragState.value = (dragState.value as? DragState.Dragging)?.copy(
            currentPage = page,
            currentCellX = cellX,
            currentCellY = cellY
        ) ?: dragState.value
    }

    fun endDrag() {
        dragState.value = DragState.Idle
    }


    fun addAppToHome(app: AppModel) {
        viewModelScope.launch {
            val cols = preferences.gridColumns.stateIn(viewModelScope, SharingStarted.Eagerly, 4).value
            val rows = preferences.gridRows.stateIn(viewModelScope, SharingStarted.Eagerly, 6).value
            val items = homeRepository.observeHomeItems().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList()).value.toMutableList()
            val firstEmpty = (0 until rows).firstNotNullOfOrNull { y ->
                (0 until cols).firstNotNullOfOrNull { x ->
                    if (items.none { it.page == 0 && it.cellX == x && it.cellY == y }) x to y else null
                }
            }
            val (x, y) = firstEmpty ?: (cols - 1 to rows - 1)
            items.add(
                HomeItem.AppIcon(
                    id = "${app.packageName}/${app.activityName}",
                    app = app,
                    cellX = x,
                    cellY = y,
                    page = 0
                )
            )
            persistLayout(items)
        }
    }
    fun persistLayout(items: List<HomeItem>) {
        viewModelScope.launch {
            homeRepository.saveItems(items)
        }
    }

    private fun fakeHomeItems(apps: List<AppModel>, cols: Int, rows: Int): List<HomeItem> {
        val count = minOf(apps.size, cols * rows * 2)
        return apps.take(count).mapIndexed { index, app ->
            HomeItem.AppIcon(
                id = "${app.packageName}/${app.activityName}",
                app = app,
                cellX = index % cols,
                cellY = (index / cols) % rows,
                page = index / (cols * rows)
            )
        }
    }
}

data class HomeUiState(
    val apps: List<AppModel> = emptyList(),
    val columns: Int = 4,
    val rows: Int = 6,
    val dragState: DragState = DragState.Idle,
    val items: List<HomeItem> = emptyList()
)

sealed class DragState {
    object Idle : DragState()
    data class Dragging(
        val item: HomeItem,
        val startCellX: Int,
        val startCellY: Int,
        val currentCellX: Int = startCellX,
        val currentCellY: Int = startCellY,
        val currentPage: Int = 0
    ) : DragState()
}
