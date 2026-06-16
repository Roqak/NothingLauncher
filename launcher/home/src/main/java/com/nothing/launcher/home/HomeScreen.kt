package com.nothing.launcher.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nothing.core.theme.NothingDimens
import com.nothing.core.utils.performVirtualKeyHaptic
import com.nothing.launcher.dock.DockBar
import com.nothing.launcher.drawer.AppDrawerHost
import com.nothing.launcher.drawer.AppDrawer

private const val HOME_PAGES = 3

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(pageCount = { HOME_PAGES })
    val context = LocalContext.current
    var isDrawerOpen by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .homeGestureLayer(
                onSwipeUp = { isDrawerOpen = true },
                onDoubleTap = { context.sleepScreen() },
                onLongPress = { /* TODO: open wallpaper/widget/settings mode */ }
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            BoxWithConstraints(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = NothingDimens.HorizontalPadding,
                        end = NothingDimens.HorizontalPadding,
                        top = NothingDimens.TopPadding
                    )
            ) {
                val density = LocalDensity.current
                val geometry = GridGeometry(
                    screenWidthPx = with(density) { maxWidth.toPx() } + (NothingDimens.HorizontalPadding.value * 2),
                    screenHeightPx = with(density) { maxHeight.toPx() },
                    columns = state.columns,
                    rows = state.rows
                )

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    HomePage(
                        items = state.items.filter { it.page == page },
                        geometry = geometry,
                        onAppClick = { viewModel.onAppLaunched(it.app) },
                        onAppLongClick = { viewModel.startDrag(it) }
                    )
                }
            }
        }

        if (isDrawerOpen) {
            AppDrawer(
                modifier = Modifier.fillMaxSize()
            )
        }

        DockBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            onDrawerRequest = {
                isDrawerOpen = true
                context.performVirtualKeyHaptic()
            }
        )
    }
}
