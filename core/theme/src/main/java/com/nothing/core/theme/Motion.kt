package com.nothing.core.theme

/** Animation specs matching Nothing OS physics-based motion. */
object NothingMotion {
    const val AppOpenMillis = 280
    const val AppCloseMillis = 240
    const val DrawerOpenMillis = 320
    const val DrawerCloseMillis = 260
    const val PageSwipeMillis = 300
    const val IconDragLiftMillis = 120
    const val IconDropMillis = 180
    const val ContextMenuOpenMillis = 200
    const val FolderOpenMillis = 250

    // Spring physics constants used with androidx.compose.animation.core.SpringSpec
    const val StiffnessMedium = 400f
    const val StiffnessLow = 300f
    const val DampingDefault = 0.8f
}
