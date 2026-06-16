package com.nothing.launcher.wallpaper

import android.content.ComponentName
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/**
 * Preview thumbnail of the bundled "Dot Field" live wallpaper.
 */
@Composable
fun DotFieldThumbnail(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val bitmap = rememberDotFieldThumbnail()

    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = "Dot Field wallpaper",
        modifier = modifier.size(120.dp)
    )
}

@Composable
private fun rememberDotFieldThumbnail(): Bitmap {
    val size = 256
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE }

    canvas.drawColor(Color.BLACK)
    repeat(40) {
        val x = kotlin.random.Random.nextInt(size).toFloat()
        val y = kotlin.random.Random.nextInt(size).toFloat()
        val r = 2f + kotlin.random.Random.nextFloat() * 3f
        canvas.drawCircle(x, y, r, paint)
    }
    return bitmap
}
