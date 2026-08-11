package com.paperlens.v3;

import android.accessibilityservice.AccessibilityService;
import android.os.Handler;
import android.os.Looper;
import android.view.accessibility.AccessibilityEvent;

import java.util.concurrent.atomic.AtomicBoolean;

public class PaperLensService extends AccessibilityService {

    private final Handler handler =
            new Handler(Looper.getMainLooper());

    private final AtomicBoolean analyzing =
            new AtomicBoolean(false);

    private PaperProfile lastProfile =
            new PaperProfile(0.55f, 0.82f, 0.78f, 0.65f);

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        int type = event.getEventType();

        if (type == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
                || type == AccessibilityEvent.TYPE_WINDOWS_CHANGED
                || type == AccessibilityEvent.TYPE_VIEW_SCROLLED) {

            scheduleAnalysis();
        }
    }

    private void scheduleAnalysis() {

        if (analyzing.getAndSet(true)) {
            return;
        }

        handler.postDelayed(() -> {

            analyzeScreen();

            analyzing.set(false);

        }, 90);
    }

    private void analyzeScreen() {

        /*
         * V3 architecture:
         *
         * 1. Capture/sampling
         * 2. Analyze luminance
         * 3. Analyze saturation
         * 4. Analyze contrast
         * 5. Calculate paper profile
         * 6. Smooth the transition
         *
         * The actual screenshot pipeline will be connected
         * after the basic APK build is verified.
         */

        lastProfile = PaperOptimizer.update(
                lastProfile,
                0.80f,
                0.18f,
                0.72f,
                0.58f
        );
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        scheduleAnalysis();
    }

    @Override
    public void onInterrupt() {
    }

    static class PaperProfile {

        float warmth;
        float brightness;
        float contrast;
        float saturation;

        PaperProfile(
                float warmth,
                float brightness,
                float contrast,
                float saturation) {

            this.warmth = warmth;
            this.brightness = brightness;
            this.contrast = contrast;
            this.saturation = saturation;
        }
    }

    static class PaperOptimizer {

        static PaperProfile update(
                PaperProfile profile,
                float targetWarmth,
                float targetSaturation,
                float targetBrightness,
                float targetContrast) {

            float smoothing = 0.18f;

            return new PaperProfile(

                    profile.warmth
                            + (targetWarmth - profile.warmth)
                            * smoothing,

                    profile.brightness
                            + (targetBrightness - profile.brightness)
                            * smoothing,

                    profile.contrast
                            + (targetContrast - profile.contrast)
                            * smoothing,

                    profile.saturation
                            + (targetSaturation - profile.saturation)
                            * smoothing
            );
        }
    }
  }
