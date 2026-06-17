package com.nothing.core.icons

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import com.nothing.core.data.AppModel
import com.nothing.core.icons.MonochromeIconProcessor
import com.nothing.core.theme.NothingColors
import com.nothing.core.theme.NothingDimens
import com.nothing.core.utils.performVirtualKeyHaptic

/**
 * Renders a single monochrome app icon on the home screen.
 */
@Composable
fun MonochromeAppIcon(
    app: AppModel,
    size: Dp = NothingDimens.IconNormal,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {}
) {
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(app.packageName) {
        bitmap = MonochromeIconProcessor(context).loadMonochromeIcon(app.packageName)
    }

    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(NothingDimens.IconCornerRadius))
            .background(NothingColors.Surface)
            .pointerInput(app.packageName) {
                detectTapGestures(
                    onLongPress = {
                        context.performVirtualKeyHaptic()
                        onLongClick()
                    },
                    onTap = { onClick() }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = app.label,
                modifier = Modifier.size(size)
            )
        }
    }
}

