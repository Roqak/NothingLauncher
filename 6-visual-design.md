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
