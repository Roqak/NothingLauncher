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
