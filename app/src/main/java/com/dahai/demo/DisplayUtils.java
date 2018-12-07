package com.dahai.demo;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * 作者： 大海
 * 时间： 2018/11/29
 * 描述：
 */
public class DisplayUtils {
    public static int dp2px(Context context, float f) {
        return (int) (TypedValue.applyDimension(1, f, getDM()) + 0.5f);
    }

    public static int sp2px(Context context, float f) {
        return (int) (TypedValue.applyDimension(2, f, getDM()) + 0.5f);
    }

    public static int px2dp(Context context, float f) {
        return (int) ((f / getDM().density) + 0.5f);
    }

    public static int getWidthDp(Context context) {
        DisplayMetrics dm = getDM();
        return (int) (((float) dm.widthPixels) / dm.density);
    }

    public static int getHeightDp(Context context) {
        DisplayMetrics dm = getDM();
        return (int) (((float) dm.heightPixels) / dm.density);
    }

    public static int getWidthPixels(Context context) {
        return getDM().widthPixels;
    }

    public static int getHeightPixels(Context context) {
        return getDM().heightPixels;
    }

    public static int getDpi(Context context) {
        return getDM().densityDpi;
    }

    public static float getAnyScaleHeightPixels(Context context, float f) {
        return ((float) getDM().heightPixels) / f;
    }

    private static DisplayMetrics getDM() {
        return MyApp.context.getResources().getDisplayMetrics();
    }
}
