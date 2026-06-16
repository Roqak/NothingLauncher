# Nothing OS Launcher

A pixel-accurate, monochrome Android home screen replacement that brings the Nothing OS aesthetic to any Android 8.0+ device.

[![Release](https://img.shields.io/badge/release-v1.0.0-blue)](https://github.com/Roqak/NothingLauncher/releases)

## Features

- **Nothing OS home screen** — 4×6 grid, monochrome icons, spring-physics animations.
- **App drawer** — swipe up, real-time search, recent apps, Nothing-style alphabet scroller.
- **Native widgets** — NDot clock, compact clock, date card, battery, weather, music, step counter, shortcuts.
- **Settings** — grid size, app labels, dock slots; persisted with DataStore.
- **Live wallpaper** — bundled "Dot Field" animated dot-matrix wallpaper service.
- **Gestures** — swipe up/down, double-tap to sleep (device admin), long-press for edit mode.
- **Icon system** — monochrome adaptive-icon layer + grayscale binarization fallback + icon pack API.

## Tech Stack

- Kotlin 2.0.20
- Jetpack Compose
- Hilt (DI)
- Room + DataStore
- LauncherApps / AppWidgetHost / WallpaperService

## Project Structure

- `:app` — Launcher entry point, manifest, DI graph
- `:core:theme` — Colors, typography, dimens, motion tokens
- `:core:icons` — Monochrome icon processor, icon pack API
- `:core:data` — App repository, Room DB, DataStore preferences
- `:core:utils` — Dispatchers, haptics
- `:launcher:home` — Home screen grid, pages, gestures
- `:launcher:drawer` — App drawer, search, alphabet scroller
- `:launcher:dock` — Bottom dock
- `:launcher:widgets` — Native widgets
- `:launcher:wallpaper` — Live wallpaper engine + picker UI
- `:launcher:settings` — Launcher settings UI

## Installation

1. Download the signed APK from the [Releases](https://github.com/Roqak/NothingLauncher/releases) page.
2. Sideload the APK:
   ```bash
   adb install NothingLauncher-v1.0.0.apk
   ```
3. Press Home and choose **Nothing Launcher** as the default.

> **Note:** Double-tap to sleep requires Device Admin permission; the launcher will guide you to enable it.

## Building Locally

```bash
./gradlew :app:assembleDebug
```

## CI / Release

A GitHub Actions workflow (`.github/workflows/build-and-release.yml`) is configured to build and sign the APK on every tag starting with `v`. It is currently paused because the associated GitHub account has a billing lock; once resolved, the workflow will automatically attach the signed APK to releases.

## License

This is an independent open-source project. Nothing OS and NDot are trademarks of Nothing Technology Ltd. No proprietary Nothing assets are included.

---

*Read the full Product Requirements Document in [`NOTHING_OS_LAUNCHER_PRD.md`](NOTHING_OS_LAUNCHER_PRD.md).*
