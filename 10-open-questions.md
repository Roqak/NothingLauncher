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
