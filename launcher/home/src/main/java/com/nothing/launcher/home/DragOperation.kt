package com.nothing.launcher.home

import com.nothing.core.data.HomeItem
import androidx.compose.runtime.Stable

/**
 * Immutable snapshot of a drag operation.
 */
@Stable
data class DragOperation(
    val item: HomeItem,
    val startCellX: Int,
    val startCellY: Int,
    val currentCellX: Int,
    val currentCellY: Int,
    val currentPage: Int
)
