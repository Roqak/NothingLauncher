package com.nothing.core.icons

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable

/**
 * Abstraction over installed icon packs.
 */
interface IconPack {
    val name: String
    val packageName: String?
    fun getIcon(packageName: String, activityName: String? = null): Drawable?
}

/**
 * Built-in Nothing-style monochrome pack placeholder.
 */
class BundledIconPack(context: Context) : IconPack {
    override val name = "Nothing Monochrome"
    override val packageName = null

    private val overrides = mapOf<String, Int>()

    override fun getIcon(packageName: String, activityName: String?): Drawable? {
        // TODO: load bundled vector drawables keyed by package name.
        return null
    }
}

/**
 * Discovers installed third-party icon packs that expose their components.
 */
class IconPackManager(private val context: Context) {

    private val pm = context.packageManager

    fun getInstalledPacks(): List<IconPack> {
        // TODO: query icon pack apps (e.g., those with a known metadata key) and parse their config.
        return listOf(BundledIconPack(context))
    }
}
