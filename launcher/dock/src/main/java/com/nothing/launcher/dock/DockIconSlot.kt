package com.nothing.launcher.dock

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nothing.core.theme.NothingColors
import com.nothing.core.theme.NothingDimens

@Composable
fun DockIconSlot(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(NothingDimens.IconNormal)
            .background(
                color = NothingColors.Surface,
                shape = RoundedCornerShape(NothingDimens.IconCornerRadius)
            )
    )
}
