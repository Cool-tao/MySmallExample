package com.mysmallexample.ui.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

import java.util.List;


public class DeviceUtil {
    /**
     * 判断是不是虚拟设备
     * Utility methods related to physical devies and emulators.
     */
    public static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }

    /**
     * dp转换为px
     *
     * @param context
     * @param dp
     * @return
     */
    public static int Dp2Px(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    /**
     * px转换为dp
     *
     * @param context
     * @param px
     * @return
     */
    public static int Px2Dp(Context context, float px) {
        final float density = context.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5f);
    }

    /**
     * 判断当前应用程序处于前台还是后台
     *
     * @param context
     * @return boolean
     */
    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;

    }
}
