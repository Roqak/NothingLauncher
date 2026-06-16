package com.nothing.launcher.home

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nothing.core.theme.NothingDimens

/**
 * Holds the computed geometry for the home screen grid.
 *
 * @param screenWidthPx total screen width in pixels
 * @param screenHeightPx total screen height in pixels
 * @param columns number of grid columns
 * @param rows number of grid rows
 */
class GridGeometry(
    val screenWidthPx: Float,
    val screenHeightPx: Float,
    val columns: Int,
    val rows: Int
) {
    val horizontalPadding: Dp = NothingDimens.HorizontalPadding

    /** Cell width including padding between cells. */
    val cellSize: Dp
        get() = ((screenWidthPx - (horizontalPadding.value * 2)) / columns).dp

    /** Icon sits centered inside the cell, but the cell is the clickable/tappable area. */
    val iconSize: Dp = NothingDimens.IconNormal

    fun Density.cellWidthPx(): Float = cellSize.toPx()

    fun Density.iconOffsetPx(): Float = (cellSize.toPx() - iconSize.toPx()) / 2f
}

/**
 * Converts grid coordinates to layout positions in pixels.
 */
fun Density.gridPosition(
    geometry: GridGeometry,
    cellX: Int,
    cellY: Int
): Pair<Float, Float> {
    val cellWidth = geometry.cellSize.toPx()
    val iconSize = geometry.iconSize.toPx()
    val x = geometry.horizontalPadding.toPx() + cellX * cellWidth + (cellWidth - iconSize) / 2f
    val y = NothingDimens.TopPadding.toPx() + cellY * cellWidth + (cellWidth - iconSize) / 2f
    return x to y
}

/**
 * Snap a raw pixel position back to the nearest valid grid cell.
 */
fun Density.snapToCell(
    geometry: GridGeometry,
    x: Float,
    y: Float
): Pair<Int, Int> {
    val cellWidth = geometry.cellSize.toPx()
    val originX = geometry.horizontalPadding.toPx()
    val originY = NothingDimens.TopPadding.toPx()

    val col = ((x - originX + cellWidth / 2) / cellWidth).toInt().coerceIn(0, geometry.columns - 1)
    val row = ((y - originY + cellWidth / 2) / cellWidth).toInt().coerceIn(0, geometry.rows - 1)
    return col to row
}
