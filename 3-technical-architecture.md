# 3. Technical Architecture

## 3.1 Technology Stack

| Layer | Technology | Language | Notes |
|-------|-------------|----------|-------|
| UI | Jetpack Compose | Kotlin 2.x | Compose Animation for animations |
| Launcher API | android.app.Activity | Kotlin | Implements LauncherActivity |
| Widget Host | AppWidgetHost | Kotlin | Supports third-party widgets |
| Database | Room | Kotlin | Home screen layout persistence |
| DI | Hilt | Kotlin | Dependency injection |
| Build System | Gradle 8.x | Kotlin | Multi-module Android project |
| Min SDK / Target SDK | 26 / 36 | N/A | Android 8.0+ to 16 |

## 3.2 Module Structure
The project is structured as a multi-module Android project:

- `:app` – Entry point, Activity, DI graph
- `:launcher:home` – Home screen composables, grid engine, drag-drop
- `:launcher:drawer` – App drawer, search, alphabetical scroller
- `:launcher:dock` – Bottom dock bar, gesture bar integration
- `:launcher:widgets` – Native widgets (clock, weather, date, music, steps)
- `:launcher:wallpaper` – Wallpaper picker, live wallpaper engine
- `:launcher:settings` – Launcher settings UI
- `:core:theme` – Design tokens, typography
- `:core:icons` – Icon pack engine
- `:core:data` – Room DB, DataStore preferences
- `:core:utils` – Extension functions, coroutine dispatchers
