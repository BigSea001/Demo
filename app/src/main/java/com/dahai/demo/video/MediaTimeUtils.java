package com.dahai.demo.video;

import java.util.Locale;

final class MediaTimeUtils {
    private static final String TIME_FORMAT_LONG = "%1$d:%2$02d:%3$02d";
    private static final String TIME_FORMAT_SHORT = "%1$d:%2$02d";

    private MediaTimeUtils() {}

    static String getPlaybackTime(long timeMillis) {
        final int timeSeconds = (int) (timeMillis / 1000);
        final int seconds = timeSeconds % 60;
        final int minutes = (timeSeconds / 60) % 60;
        final int hours = timeSeconds / 3600;

        if (hours > 0) {
            return String.format(Locale.getDefault(), TIME_FORMAT_LONG, hours, minutes, seconds);
        } else {
            return String.format(Locale.getDefault(), TIME_FORMAT_SHORT, minutes, seconds);
        }
    }
}
