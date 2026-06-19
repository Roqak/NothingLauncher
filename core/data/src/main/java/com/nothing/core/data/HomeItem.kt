package com.nothing.core.data

import com.nothing.core.data.AppModel

/**
 * UI representation of a single item on a home screen page.
 */
sealed class HomeItem(
    open val id: String,
    open val cellX: Int,
    open val cellY: Int,
    open val page: Int
) {
    data class AppIcon(
        override val id: String,
        val app: AppModel,
        override val cellX: Int,
        override val cellY: Int,
        override val page: Int
    ) : HomeItem(id, cellX, cellY, page)

    data class Folder(
        override val id: String,
        val apps: List<AppModel>,
        override val cellX: Int,
        override val cellY: Int,
        override val page: Int
    ) : HomeItem(id, cellX, cellY, page)
}
