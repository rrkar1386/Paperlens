# PaperLens V3 — real-time architecture prototype

V3 moves from the V2 screenshot/overlay concept toward a low-latency renderer architecture.

## Improvements
- Event-driven analysis rather than constant full-rate processing.
- 90 ms debounce/throttle to reduce work during scrolling.
- Adaptive-profile smoothing (hysteresis/exponential smoothing) to prevent visible flicker.
- Paper profile is represented as warmth, brightness, contrast and saturation.
- No network permission.

## Important Android limitation
A normal third-party Android app cannot arbitrarily replace the system compositor's pixels.
Accessibility screenshot/overlay techniques have platform restrictions and can introduce latency.
This V3 build therefore does not pretend to provide a production-grade system-wide pixel filter.
The next implementation step is to connect the analyzer to the supported screenshot pipeline on
a target Android device, benchmark it, and select the lowest-latency rendering path available.

## Build
Open the project in Android Studio and build the `app` module.


## Cloud APK build with GitHub Actions

1. Create a new GitHub repository.
2. Upload the contents of this folder to the repository (including `.github/workflows/build-apk.yml`).
3. Open the repository's **Actions** tab.
4. Select **Build PaperLens APK**.
5. Choose **Run workflow**.
6. When the workflow finishes, open the run and download the **PaperLens-V3-debug** artifact.
7. Extract the APK and install it on your Android phone.

This produces a debug APK for testing, not a Play Store release.
