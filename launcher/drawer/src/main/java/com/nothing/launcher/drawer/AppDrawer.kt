package com.nothing.launcher.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nothing.core.data.AppModel
import com.nothing.core.theme.NothingColors
import com.nothing.core.theme.NothingDimens
import com.nothing.launcher.home.MonochromeAppIcon

@Composable
fun AppDrawer(
    modifier: Modifier = Modifier,
    viewModel: DrawerViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var activeLetter by remember { mutableStateOf<Char?>(null) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(NothingColors.Scrim)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.92f)
                .align(Alignment.BottomCenter)
                .clip(
                    RoundedCornerShape(
                        topStart = NothingDimens.WidgetCornerRadius,
                        topEnd = NothingDimens.WidgetCornerRadius
                    )
                )
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = NothingDimens.HorizontalPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(NothingDimens.SheetHandleWidth)
                        .height(NothingDimens.SheetHandleHeight)
                        .clip(RoundedCornerShape(2.dp))
                        .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f))
                )
            }

            SearchBar(
                query = state.query,
                onQueryChange = viewModel::onQueryChange
            )

            if (state.recentApps.isNotEmpty() && state.query.isBlank()) {
                RecentAppsRow(
                    apps = state.recentApps,
                    onAppClick = {
                        viewModel.onAppLaunched(it)
                        context.launchApp(it)
                    }
                )
            }

            Row(modifier = Modifier.weight(1f)) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(state.allApps, key = { it.packageName + it.activityName }) { app ->
                        AppDrawerRow(
                            app = app,
                            onClick = {
                                viewModel.onAppLaunched(app)
                                context.launchApp(app)
                            }
                        )
                    }
                }

                AlphabetScroller(
                    activeLetter = activeLetter,
                    onLetterSelected = { activeLetter = it }
                )
            }
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    BasicTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(NothingColors.Surface)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        ),
        singleLine = true,
        decorationBox = { innerTextField ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onBackgroundMuted,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Box(modifier = Modifier.weight(1f)) {
                    if (query.isEmpty()) {
                        Text(
                            text = "Search apps",
                            color = MaterialTheme.colorScheme.onBackgroundMuted,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}

@Composable
private fun RecentAppsRow(
    apps: List<AppModel>,
    onAppClick: (AppModel) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = "Recent",
            style = MaterialTheme.typography.labelSection,
            color = MaterialTheme.colorScheme.onBackgroundMuted,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row {
            apps.forEach { app ->
                MonochromeAppIcon(
                    app = app,
                    size = 56.dp,
                    modifier = Modifier.padding(end = 16.dp),
                    onClick = { onAppClick(app) }
                )
            }
        }
    }
}

@Composable
private fun AppDrawerRow(
    app: AppModel,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        MonochromeAppIcon(
            app = app,
            size = 48.dp,
            onClick = onClick
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = app.label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
