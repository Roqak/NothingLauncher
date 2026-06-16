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
