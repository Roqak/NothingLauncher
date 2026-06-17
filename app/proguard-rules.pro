# Keep source names readable; still shrink unused code
-dontobfuscate

# Hilt / Dagger generated classes
-keepclassmembers class * {
    @dagger.hilt.android.EarlyEntryPoint <methods>;
}
-keep class dagger.hilt.** { *; }
-keep class * extends dagger.hilt.internal.GeneratedComponentManager { *; }

# Room entities/dao
-keep class com.nothing.core.data.** { *; }
-keepclassmembers class com.nothing.core.data.** { *; }

# Keep Application subclass and entry points used by Hilt
-keep class com.nothing.launcher.NothingLauncherApp { *; }
-keep class com.nothing.launcher.MainActivity { *; }
-keep class * extends android.app.Application { *; }
-keep @dagger.hilt.android.HiltAndroidApp class * { *; }
-keep @dagger.hilt.android.AndroidEntryPoint class * { *; }

# Keep device admin and broadcast receivers
-keep class * extends android.app.admin.DeviceAdminReceiver { *; }
-keep class * extends android.content.BroadcastReceiver { *; }
-keep class * extends android.service.wallpaper.WallpaperService { *; }

# Compose / Kotlin metadata used by KSP/Dagger
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes EnclosingMethod
-keep class kotlin.Metadata { *; }
