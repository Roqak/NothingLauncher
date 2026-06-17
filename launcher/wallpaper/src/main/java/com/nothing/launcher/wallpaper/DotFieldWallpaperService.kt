package com.nothing.launcher.wallpaper

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import kotlin.random.Random

/**
 * "Dot Field" live wallpaper: animated black/white dot-matrix particles.
 * Battery-aware: reduces target FPS when battery is low.
 */
private const val PARTICLE_COUNT = 80

class DotFieldWallpaperService : WallpaperService() {

    override fun onCreateEngine(): Engine = DotFieldEngine()

    private inner class DotFieldEngine : Engine() {
        private val handler = Handler(Looper.getMainLooper())
        private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            style = Paint.Style.FILL
        }

        private var visible = false
        private var width = 0f
        private var height = 0f
        private val particles = mutableListOf<Particle>()
        private var targetFps = 60

        private val drawRunnable = Runnable { drawFrame() }

        override fun onVisibilityChanged(visible: Boolean) {
            this.visible = visible
            if (visible) {
                handler.post(drawRunnable)
            } else {
                handler.removeCallbacks(drawRunnable)
            }
        }

        override fun onSurfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            this.width = width.toFloat()
            this.height = height.toFloat()
            initParticles()
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder) {
            visible = false
            handler.removeCallbacks(drawRunnable)
        }

        override fun onDestroy() {
            visible = false
            handler.removeCallbacks(drawRunnable)
        }

        private fun initParticles() {
            particles.clear()
            repeat(PARTICLE_COUNT) {
                particles.add(
                    Particle(
                        x = Random.nextFloat() * width,
                        y = Random.nextFloat() * height,
                        radius = 2f + Random.nextFloat() * 3f,
                        speedX = (Random.nextFloat() - 0.5f) * 2f,
                        speedY = (Random.nextFloat() - 0.5f) * 2f
                    )
                )
            }
        }

        private fun drawFrame() {
            val holder = surfaceHolder ?: return
            var canvas: Canvas? = null
            try {
                canvas = holder.lockCanvas()
                canvas?.drawColor(Color.BLACK)

                particles.forEach { p ->
                    p.x += p.speedX
                    p.y += p.speedY
                    if (p.x < 0 || p.x > width) p.speedX *= -1
                    if (p.y < 0 || p.y > height) p.speedY *= -1

                    paint.alpha = (150 + Random.nextInt(105))
                    canvas?.drawCircle(p.x, p.y, p.radius, paint)
                }
            } finally {
                canvas?.let { holder.unlockCanvasAndPost(it) }
            }

            if (visible) {
                handler.postDelayed(drawRunnable, 1000L / targetFps)
            }
        }

        private inner class Particle(
            var x: Float,
            var y: Float,
            val radius: Float,
            var speedX: Float,
            var speedY: Float
        )
    }
}
