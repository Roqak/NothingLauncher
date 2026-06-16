# NOTHING OS LAUNCHER

Product Requirements Document
A complete Nothing OS experience on any Android device
Version
Status
Date
1.0.0
Draft – Ready for Engineering Review
June 2025

---

## 1. Executive Summary
This document defines the complete product requirements for the Nothing OS Launcher – a fully featured Android home screen replacement that brings the iconic Nothing OS aesthetic and experience to any Android device running Android 8.0 (Oreo) or above.

Nothing OS is celebrated for its monochrome, dot-matrix-inspired design language, radical minimalism, and a focus on intentional UI. This launcher does not attempt to be a superficial clone – it is a faithful, production-quality implementation of every observable Nothing OS software component, from the home screen grid to the app drawer, widget system, quick-settings panel aesthetics, and system font integration.

**What we are building**: A standalone Android launcher APK (no root required, no system modifications) that replaces the default home screen and delivers a 100% Nothing OS software look-and-feel. It will be distributed as a sideloadable APK and optionally published to the Google Play Store.

### 1.1 Goals
- Deliver a pixel-accurate reproduction of the Nothing OS home screen, app drawer, and launcher widgets.
- Use monochrome iconography (black & white adaptive icons with outline/dot-matrix aesthetics).
- Implement the NDot / dot-matrix clock and font system throughout.
- Support live wallpapers and static wallpapers matching Nothing's default selection.
- Achieve performance parity with stock Android (< 16ms frame budget, no jank).
- Require zero root access and no system-level modifications.
- Target Android 8.0+ (API 26+), optimized for Android 12–16.

### 1.2 Non-Goals
- Glyph Interface control (hardware-level LED strips – not replicable without Nothing hardware).
- Nothing OS lock screen (requires system-level integration).
- Nothing's notification shade / quick settings panel (restricted by Android).
- Always-On Display customization.
- Replicating Nothing OS settings menus.
# 2. Product Overview

## 2.1 Design Philosophy
Nothing OS is built on four pillars that must be reflected at every layer of this launcher:

| Pillar | Implementation Mandate |
|--------|-----------------------|
| Monochrome | Black (#0D0D0D) and White (#FFFFFF) as the only UI colors. No gradients, no color accents, no tinted elements anywhere in launcher chrome. |
| Dot-Matrix | NDot-55 and NDot-45 typefaces for the clock widget, status displays, and all numeric readouts. Custom dot-grid rendering for large type elements. |
| Minimalism | No labels beneath dock icons by default, reduced visual noise, no rounded colored blobs, generous whitespace, grid-aligned everything. |
| Intentionality | Every tap, swipe, and long-press is deliberate. Animations are physics-based but subtle. No distracting motion, no particle effects. |

## 2.2 Target Users
- Nothing Phone owners who want the same experience on an updated or secondary device.
- Android enthusiasts who admire the Nothing OS aesthetic.
- Users who prefer minimal, distraction-free launchers over feature-bloated alternatives.
- CMF Phone users who want a more complete Nothing experience than CMF OS provides.

## 2.3 Platform Requirements

| Requirement | Value |
|-------------|-------|
| Minimum Android Version | Android 8.0 (API 26) |
| Target Android Version | Android 16 (API 36) |
| Architecture | arm64-v8a, armeabi-v7a, x86_64 |
| Required Permissions | SET_WALLPAPER, QUERY_ALL_PACKAGES, RECEIVE_BOOT_COMPLETED, VIBRATE |
| Optional Permissions | ACCESS_FINE_LOCATION (weather widget), READ_CONTACTS (dialer suggestion), ACTIVITY_RECOGNITION (step widget) |
| Root Required | No |
| System App Required | No |
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
# 4. Feature Requirements

## 4.1 Home Screen

### 4.1.1 Grid System

| Property | Specification |
|----------|---------------|
| Default Grid | 4 columns × 6 rows |
| User-Selectable Grids | 4×5, 4×6, 5×5, 5×6, 6×6 |
| Cell Size | (screen_width – 48dp) / columns |
| Horizontal Padding | 24dp on each side |
| Vertical Padding | Top: 8dp, Bottom: 8dp |
| Icon Size | 60dp (normal) / 80dp (Max Icon mode) |
| Label Display | Off by default. Optional single-line labels in white, 11sp, Inter Regular. |
| Multiple Pages | Horizontal scroll, up to 7 pages. Page indicator dots below grid, above dock. |

### 4.1.2 Icon System

- All icons rendered in monochrome using Android 13+ monochrome adaptive icon layer when available.
- Fallback for apps without monochrome layers: apply luminance-based grayscale + threshold binarization to produce black/white icons.
- Icons are square with a corner radius of 16dp (system default from Android 12+ icon shape).
- No colored badge dots. Notification counts shown as minimal white overlays.
- The launcher ships a bundled icon pack with 500+ hand-crafted monochrome icons for the most common apps. These override generated icons where available.
- Icon pack format: compatible with standard APHRODITE / ARCUS icon pack format so third-party packs work natively.
- Max Icon Mode: selected icons scale to 80dp with bolder strokes; enabled per-icon via long-press context menu.

### 4.1.3 Long-Press Context Menu

- Background: solid black (#0D0D0D), corner radius 20dp top only.
- App icon + name at top of sheet in white.
- Menu items: Edit, App Info, Uninstall, Remove from Home Screen, Add to Dock, Toggle Max Icon.
- Haptic feedback on menu open (VIRTUAL_KEY effect).
- Dismiss: swipe down or tap outside sheet.

### 4.1.4 Drag & Drop

- Long-press + hold for 350ms initiates drag mode.
- Drag targets highlight with a subtle white border glow (1dp border, 60% opacity) to indicate valid drop zones.
- Other icons slide out of the way with spring physics (stiffness: MEDIUM, damping: 0.8).
- Dropping onto another icon creates a folder automatically.
- Folder icon shows a 2×2 preview grid of the top 4 apps inside, monochrome.
- Folder name is editable inline, shown above the icon grid in the folder open state.
# 5. Launcher Settings

Settings are accessed via long-press on home screen \u2192 'Launcher Settings'. The settings UI itself matches Nothing OS settings aesthetics: black background, white text, no icons on list items, NDot font for section headers.

## 5.1 Home Screen Settings
- Grid size (4×5, 4×6, 5×5, 5×6, 6×6)
- Show/hide app labels
- Icon size (Normal / Large / Max)
- Wallpaper parallax (on/off)
- Double-tap to sleep (on/off)
- Swipe down gesture action (notification shade / search)

## 5.2 Dock Settings
- Dock slots (4 or 5)
- Show dock labels (on/off)
- Dock divider line (on/off)

## 5.3 App Drawer Settings
- Sort order (A\u2190Z / Z\u2190A / Most used)
- Show recently used row (on/off)
- Search suggestions (on/off)
- Drawer background opacity (0\u2190100%)

## 5.4 Icon Pack Settings
- Select active icon pack (built-in monochrome / installed APHRODITE packs)
- Force monochrome on all icons (on/off \u2013 uses adaptive icon monochrome layer)
- Reset icon for specific app to default

## 5.5 About
- Launcher version
- Link to GitHub repository
- Credits and open-source licenses
# 6. Visual Design Specification

## 6.1 Color System

| Token | Value | Usage |
|-------|-------|-------|
| color.background | #0D0D0D | Drawer background, context menus, widget background, settings screen |
| color.surface | #1A1A1A | Elevated surfaces (e.g., folder open overlay) |
| color.on_background | #FFFFFF | Primary text, icon fills, widget text (on dark) |
| color.on_background_muted | #6B6B6B | Secondary text, metadata, disabled states |
| color.divider | #2E2E2E | List dividers, widget borders |
| color.scrim | #000000 @ 85% | Drawer background scrim, folder overlay scrim |
| color.inverse_background | #FFFFFF | Background when wallpaper is light (auto-detected) |
| color.inverse_on_bg | #0D0D0D | Text on light wallpaper |

## 6.2 Typography

| Style | Font / Size / Weight | Usage |
|-------|----------------------|-------|
| clock.large | NDot-55 / 88sp / Regular | Main home screen clock widget |
| clock.compact | NDot-45 / 48sp / Regular | Small clock widget, date card widget |
| body.large | Inter / 16sp / Regular | Drawer search placeholder, settings body |
| body.medium | Inter / 14sp / Regular | App labels (when shown), widget secondary text |
| body.small | Inter / 12sp / Regular | Metadata, notification badges |
| label.section | NDot-45 / 13sp / Regular | Settings section headers, widget section labels |
| context.menu.item | Inter / 15sp / Medium | Context menu item labels |

**Font Assets**: NDot-55.ttf and NDot-45.ttf must be bundled as custom font assets in the APK under res/font/. These are Nothing's proprietary typefaces. For open-source distribution, a legally free dot-matrix approximation (such as 'Press Start 2P' or a commissioned custom font) will be used unless licensing is secured.

## 6.3 Iconography
- Icon style: outlined, monochrome, 24dp × 24dp, 1.5dp stroke weight.
- No filled icons anywhere in launcher chrome (only outlined).
- All system icons (search, settings, add widget, etc.) must be custom-drawn in Nothing's exact outline style.
- Navigation icons (back, home, recents) are not controlled by the launcher (system jurisdiction).
# 7. Performance Requirements

## 7.1 Key Metrics

| Metric | Target |
|--------|--------|
| Home screen first render | < 500ms from cold start on mid-range device (Snapdragon 720G class) |
| App drawer open animation | Zero dropped frames (60fps sustained through full animation) |
| App launch animation | < 2 dropped frames across 10 repeated launches |
| Memory footprint (idle) | < 120MB RAM when home screen is active and idle |
| Battery drain (idle) | < 1% per hour on idle with clock widget active (measured over 4hrs) |
| App list load time | < 200ms to populate full app drawer from cold (< 50 apps); < 400ms for 200+ apps |
| Search response time | < 50ms from keystroke to filtered results (local search, no network) |
| Frame budget | < 16ms per frame (60fps) on all animations. Target < 8ms (120fps) on 120Hz-capable devices. |

## 7.2 Testing Matrix

Performance regression testing must be run on:
- Low-end: Snapdragon 460 / 3GB RAM device
- Mid-range: Snapdragon 720G / 6GB RAM device
- Flagship: Snapdragon 8 Gen 2 or above / 8GB RAM device
# 8. Development Phases & Milestones

## 8.1 Phase 1: Foundation

- **Duration**: 2 weeks
- **Deliverables**: Project setup, module structure, CI/CD, design token system, NDot font integration

## 8.2 Phase 2: Core Launcher

- **Duration**: 4 weeks
- **Deliverables**: Working home screen grid, icon rendering, drag-drop, page scroll, dock

## 8.3 Phase 3: App Drawer

- **Duration**: 2 weeks
- **Deliverables**: Drawer open/close, app list, search, alphabetical scroller

## 8.4 Phase 4: Widgets

- **Duration**: 3 weeks
- **Deliverables**: All 8 native widgets, AppWidgetHost for third-party, widget picker UI

## 8.5 Phase 5: Icon System

- **Duration**: 2 weeks
- **Deliverables**: Monochrome processing pipeline, bundled icon pack (500+ icons), icon pack API

## 8.6 Phase 6: Wallpaper & Gestures

- **Duration**: 2 weeks
- **Deliverables**: Bundled wallpapers, live wallpaper engine, all gesture handlers

## 8.7 Phase 7: Settings

- **Duration**: 1 week
- **Deliverables**: Complete settings UI, DataStore persistence, per-user preferences

## 8.8 Phase 8: Polish & Performance

- **Duration**: 2 weeks
- **Deliverables**: Animation tuning, perf benchmarking, haptics, a11y, edge cases

## 8.9 Phase 9: Beta & Release

- **Duration**: 2 weeks
- **Deliverables**: Internal beta, bug triage, Play Store prep, signed APK distribution

**Total estimated timeline**: ~20 weeks (5 months) for a solo developer. 10–12 weeks with a 2-person team.
# 9. Testing Requirements

## 9.1 Unit Tests
- Icon monochrome processing algorithm (color \u2192 greyscale \u2192 binary)
- App drawer search filter logic
- Home screen grid layout calculations (cell sizing, column math)
- DataStore serialization/deserialization for all settings
- Widget data providers (clock tick, battery level, step count)

## 9.2 Integration Tests
- LauncherApps API integration: app install/uninstall/update reflection in drawer
- AppWidgetHost: widget add/remove/resize lifecycle
- Room DB: layout persistence across process death and relaunch

## 9.3 UI Tests (Espresso / Compose Testing)
- Home screen \u2192 long press \u2192 context menu \u2192 Add to Home (end-to-end)
- Drag icon to different page (cross-page drag)
- Drawer: open \u2192 search \u2192 clear \u2192 close
- Settings: change grid size \u2192 verify home screen re-renders
- Widget: add clock widget \u2192 verify it ticks

## 9.4 Performance Tests
- Macrobenchmark: home screen cold start
- Macrobenchmark: drawer open animation frame timing
- Memory profiling: idle heap after 30 minutes
- Battery historian: drain rate over 4-hour idle session

## 9.5 Device Matrix
- Android 8.0 (API 26): functional smoke test only
- Android 10 (API 29): full regression
- Android 12 (API 31): full regression + monochrome icon validation
- Android 13 (API 33): full regression
- Android 14 (API 34): full regression + predictive back gesture
- Android 16 (API 36): full regression
# 10. Open Questions & Risks

## 10.1 High-Risk Items

- **NDot Font Licensing**: Cannot ship without license. Fallback: commission a free dot-matrix font or use 'Share Tech Mono' as a placeholder.
- **Nothing Icon Pack Copyright**: Hand-crafted icons must be original works. Cannot copy or extract Nothing's actual icon assets.

## 10.2 Medium-Risk Items

- **Double-tap to sleep**: No public API for screen-off without root. Accessibility service workaround available but may not work on all OEMs.
- **Android 13 monochrome icons**: Only ~40% of apps ship monochrome adaptive layers as of 2025. Fallback processing needed for remainder.

## 10.3 Low-Risk Items

- **Play Store policies**: Launcher apps are generally permitted. Avoid using 'Nothing' trademark in app name/description to prevent removal.
- **MIUI / Samsung One UI compat.**: Some OEM Android forks restrict third-party launchers or break LauncherApps API. Needs manual testing on Samsung and Xiaomi.
# 11. Appendix

## 11.1 Key Android APIs Used
- `android.app.LauncherApps` – enumerate, listen for installed app changes
- `android.appwidget.AppWidgetHost` – host third-party app widgets
- `android.appwidget.AppWidgetManager` – query available widget providers
- `android.app.WallpaperManager` – set wallpaper, listen for changes
- `android.media.session.MediaSessionManager` – Now Playing widget
- `android.hardware.Sensor` (`TYPE_STEP_COUNTER`) – Step widget
- `android.content.pm.ShortcutManager` – app long-press shortcuts
- `androidx.datastore.preferences` – settings persistence
- `androidx.room` – home screen layout persistence
- `androidx.benchmark.macro` – Macrobenchmark performance tests

## 11.2 Reference Devices
- **Nothing Phone (2a)** – primary reference device for UI matching
- **Nothing Phone (2)** – secondary reference
- **Google Pixel 8** – baseline Android verification
- **Samsung Galaxy A54** – OEM compatibility testing

## 11.3 Inspiration & References
- **Nothing OS 4.0 hands-on coverage** – 91mobiles.com, androidauthority.com
- **Nothing Launcher APK** (`com.nothing.launcher`) – visual reference only
- **Nothing Design Language** – nothing.tech/design
- **Material Design 3** – material.io (used where Nothing OS deviates from stock)
