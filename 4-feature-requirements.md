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
