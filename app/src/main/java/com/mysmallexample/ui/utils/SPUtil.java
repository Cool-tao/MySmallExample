package com.mysmallexample.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.mysmallexample.ui.App;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.jpush.android.api.JPushInterface;
import example.mysmallexample.BuildConfig;
import example.mysmallexample.R;

/**
 * @author color
 *         程序配置
 */
public class SPUtil {

    private static SPUtil mSPutil;
    private ACache msp;

    private SPUtil() {
        msp = ACache.get(App.getInstance().getApplicationContext(), "ch");
    }

    public static synchronized SPUtil getInstance() {
        if (mSPutil == null) {
            mSPutil = new SPUtil();
        }
        return mSPutil;
    }

    /**
     * 判断是不是模拟器
     */
    public static boolean isEmulator() {
        if (BuildConfig.DEBUG) {
            return false;
        }
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
     * 跳转到谷歌商店
     *
     * @param context
     * @return
     */
    public static Intent getIntent(Context context) {
        final String appPackageName = context.getPackageName();
        Intent notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)); // 点击该通知后要跳转的Activity
        if (Utils.isAppInstall(context, "com.android.vending")) {
            notificationIntent.setPackage(Utils.GOOGLE_PLAY_PACKAGE_NAME);
        } else {
            notificationIntent = null;
        }
        return notificationIntent;
    }


    private static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String format(Calendar calendar) {
        return FORMAT.format(calendar.getTime());
    }


    public static BitmapDrawable getBitmapDrawable(Resources resources, int rid) {
        long start = System.currentTimeMillis();
        Bitmap bitmap = BitmapFactory.decodeResource(resources, rid);
        Log.e("test", "News: " + (System.currentTimeMillis() - start));
        if (bitmap != null && !bitmap.isRecycled()) {
            return new BitmapDrawable(resources, bitmap);
        } else {
            return null;
        }
    }


    public static void saveFile(File target, String str) {
        String filePath = target.getAbsolutePath();
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(str.getBytes());
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //保存和获取全局配置

    /**
     * 保存和获取String类型
     */
    public static void setGlobal(String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(App.getInstance()).edit().putString(key, value).commit();
    }

    public static String getGlobal(String key, String value) {
        return PreferenceManager.getDefaultSharedPreferences(App.getInstance()).getString(key, value);
    }

    /**
     * 保存和获取long类型
     */
    public static void setGlobal(String key, long value) {
        PreferenceManager.getDefaultSharedPreferences(App.getInstance()).edit().putLong(key, value).commit();
    }

    public static long getGlobal(String key, long value) {
        return PreferenceManager.getDefaultSharedPreferences(App.getInstance()).getLong(key, value);
    }

    /**
     * 保存和获取boolean类型
     */
    public static void setGlobal(String key, boolean value) {
        PreferenceManager.getDefaultSharedPreferences(App.getInstance()).edit().putBoolean(key, value).commit();
    }

    public static boolean getGlobal(String key, boolean value) {
        return PreferenceManager.getDefaultSharedPreferences(App.getInstance()).getBoolean(key, value);
    }

    // 打印所有的 intent extra 数据
    public static String printBundle(Bundle bundle) {
        try {
            StringBuilder sb = new StringBuilder();
            for (String key : bundle.keySet()) {
                if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                    sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
                } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                    sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
                } else {
                    try {
                        sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
                    } catch (Exception e) {
                    }
                }
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取网络连接信息
     */
    public static final NetworkInfo getNetworkInfo(Context context) {
        try {
            int ansPermission = context
                    .checkCallingOrSelfPermission(android.Manifest.permission.ACCESS_NETWORK_STATE);
            int internetPermission = context
                    .checkCallingOrSelfPermission(android.Manifest.permission.INTERNET);
            if (ansPermission == PackageManager.PERMISSION_GRANTED
                    && internetPermission == PackageManager.PERMISSION_GRANTED) {
                if (context != null) {
                    ConnectivityManager cm = (ConnectivityManager) context
                            .getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                    return networkInfo;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static long beforeTime = 0;

    public static void exit(Activity activity) {
        VibUtil.Vibrate(activity, 60);
        long currentTime = System.currentTimeMillis();
        if (currentTime - beforeTime < 2500) {
            activity.finish();
        } else {
            beforeTime = currentTime;
            Toast.makeText(activity, "" + activity.getString(R.string.toast_press_again_to_exit), Toast.LENGTH_SHORT).show();
        }
    }


    public boolean getOffTuiSong() {
        boolean start = msp.getAsBoolean("off_ts", true);
        return start;
    }

    public void setOffTuiSong(boolean flag) {
        msp.put("off_ts", flag);
    }
}

