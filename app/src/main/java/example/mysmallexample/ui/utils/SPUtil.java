package example.mysmallexample.ui.utils;

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
import android.view.animation.Animation;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import cn.jpush.android.api.JPushInterface;
import example.mysmallexample.BuildConfig;
import example.mysmallexample.ui.App;

/**
 * @author color
 *         程序配置
 */
public class SPUtil {

    public static Random random = new Random(System.currentTimeMillis());
    public static LinearLayout.LayoutParams NORMAL;
    public static LinearLayout.LayoutParams LARGE;
    public static Animation imageAnimation;
    private static SPUtil mSPutil;

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

    /**
     * 国家代码
     */
    public static final String COUNTRY_CODE = "country_code";
    /**
     * 城市
     */
    public static final String CITY = "city";
    /**
     * 街道
     */
    public static final String ADDRESS = "address";
    /**
     * 用户设备唯一标识，如00000000-54b3-e7c7-0000-000046bffd97
     */
    public static final String UUID = "uuid";
    /**
     * 语言代码，如en、zh
     */
    public static final String LAUGUAGE = "language";
    /**
     * 国家代码，如CN
     */
    public static final String COUNTRY = "country";
    /**
     * 屏幕分辨率，如720*1080
     */
    public static final String SCREEN_RESOLUTION = "screen_type";
    /**
     * 设备制造商名称，如Xiaomi
     */
    public static final String MANUFACTURE = "manufacture";
    /**
     * 设备型号，如MI 1S
     */
    public static final String MODEL = "model";
    /**
     * 系统版本号，如21（代表Android5.0）
     */
    public static final String OS_VERSION = "android_version";
    /**
     * 手机SIM卡运营商
     */
    public static final String SIM_OPERATOR = "operator";
    /**
     * 手机卡IMSI
     */
    public static final String IMSI = "imsi";
    /**
     * 手机卡IMEI
     */
    public static final String IMEI = "imei";
    /**
     * Android ID
     */
    public static final String ANDROID_ID = "android_id";
    /**
     * 本机是否安装了GooglePlay
     */
    public static final String HAS_GOOGLE_MARKET = "has_google_market";
    /**
     * 当前应用自身包名
     */
    public static final String PACKAGE_NAME_SELF = "packageNameSelf";
    /**
     * 版本号
     */
    public static final String VERSION_CODE = "ver_code";

    private static int codeId = 0;
}

