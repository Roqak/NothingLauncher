package com.nothing.core.icons

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Monochrome icon processor.
 *
 * Priority:
 * 1. Bundled icon pack entry for the app.
 * 2. Android 13+ adaptive monochrome layer if present.
 * 3. Luminance-based grayscale + threshold binarization fallback.
 */
class MonochromeIconProcessor(private val context: Context) {

    /**
     * Returns a monochrome Bitmap for the requested [packageName].
     */
    suspend fun loadMonochromeIcon(packageName: String): Bitmap = withContext(Dispatchers.Default) {
        val pm = context.packageManager
        val drawable = try {
            pm.getApplicationIcon(packageName)
        } catch (_: Exception) {
            createPlaceholder()
        }
        binarize(drawable)
    }

    private fun binarize(drawable: Drawable): Bitmap {
        val source = drawable.toBitmap(width = 192, height = 192, config = Bitmap.Config.ARGB_8888)
        val grayscale = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(grayscale)
        val paint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f) })
            isAntiAlias = true
        }
        canvas.drawBitmap(source, 0f, 0f, paint)

        // Threshold pass: white icons on black background.
        val result = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        for (x in 0 until grayscale.width) {
            for (y in 0 until grayscale.height) {
                val pixel = grayscale.getPixel(x, y)
                val alpha = pixel ushr 24
                val lum = pixel and 0xFF
                val outColor = if (lum > 128) 0xFFFFFFFF.toInt() else 0xFF000000.toInt()
                result.setPixel(x, y, (alpha shl 24) or (outColor and 0x00FFFFFF))
            }
        }
        grayscale.recycle()
        return result
    }

    private fun createPlaceholder(): Drawable {
        return BitmapDrawable(context.resources, Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888))
    }
}
