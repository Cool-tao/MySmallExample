package example.mysmallexample;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.io.File;
import java.util.Locale;

/**
 * Created by zhengnan on 2016/5/23.
 * 设备信息的获取
 */
public class DeviceInfoSumm {
    public void execute(Context ctx)throws Exception{
        PackageInfo pi = ctx.getPackageManager().getPackageInfo(
                ctx.getPackageName(), 0);
        TelephonyManager t  = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        WindowManager wm = (WindowManager) ctx
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        Log.e("test",
                        "imei = " + t.getDeviceId()+ "\n"
                        + "imsi = " + t.getSubscriberId()+ "\n"
                        + "androidId = " + Settings.Secure.getString(
                                ctx.getContentResolver(), Settings.Secure.ANDROID_ID) + "\n"
                        + ("运营商-NetworkOperatorName = " + t.getNetworkOperatorName() + "\n")
                        + ("SIM卡国别-SimCountryIso = " + t.getSimCountryIso() + "\n")
                        + ("SIM卡序列-SimSerialNumber = " + t.getSimSerialNumber() + "\n")
                        + ("SimOperator = " + t.getSimOperator() + "\n")
                        + ("网络运营商代号-NetworkOperator = " + t.getNetworkOperator() + "\n")
                        + "机型(model) = " + Build.MODEL
                        + "\n品牌(brand) =  " + Build.BRAND
                        + "\nmac地址 = " + getMacAddr(ctx)
                        + "\nlocale = " + Locale.getDefault().toString() + "   ||" + Locale.getDefault().getCountry() + "_" + Locale.getDefault().getLanguage()
                        + " ||" + ctx.getResources().getConfiguration().locale + "||"
                        + "\n 发行版本(release) = " + Build.VERSION.RELEASE
                        + "\n 设备序列(searial) = " + Build.SERIAL
                        + "\n api版本(sdkVersion) = " + Build.VERSION.SDK_INT + "  " + Build.VERSION.SDK //ex. 17
                        + "\n (指纹码)fingerprint = " + Build.FINGERPRINT//ex.samsung/GT-I9100G/GT-I9100G:4.1.2/JZO54K/I9100GXXLSR:user/release-keys
                        + "\n (设备全名)product = " + Build.PRODUCT   //ex. GT-I9100G
                        + "\n 启动加载器的版本号（bootLoader） = " + Build.BOOTLOADER //ex. unknown
                        + "\n 硬件（hardWare） = " + Build.HARDWARE //ex.  t1
                        + "\n 产品制造商(manufacturer) = " + Build.MANUFACTURER //ex. samsung
                        + "\n 工业设计名称(device) = " + Build.DEVICE //ex. GT-I9100G
                        + "\n 变量记录id(id) = " + Build.ID //ex. JDQ39E
                        + "\n 指令集类型1，2（cpu_api,cpu_api2） = " + Build.CPU_ABI + " , " + Build.CPU_ABI2 //ex. armeabi-v7a , armeabi
                        + "\n  screen :" +dm.widthPixels + "  ,  " + dm.heightPixels
                        + "\n root :" + checkRootMethod2()
                        + "\n usbEnable:" + (Settings.Secure.getInt(ctx.getContentResolver(), Settings.Secure.ADB_ENABLED, 0) > 0)
                        + "\n buildTime:" + Build.TIME
                        +"\n 包名："+ctx.getPackageName()
                        +"\n versionCode: "+pi.versionCode
                        +"\n versionName: "+pi.versionName
                        +"\n sd卡是否存在："+Environment.MEDIA_MOUNTED.equals(Environment
                                .getExternalStorageState())
                        +"\n 是否为rom应用："+((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0)
                        +"\n 是否有静默权限："+checkSilenceInstallPermission(ctx)

        );

    }

    private static boolean checkRootMethod2() {
        String[] paths = { "/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                "/system/bin/failsafe/su", "/data/local/su" };
        for (String path : paths) {
            if (new File(path).exists()) return true;
        }
        return false;
    }
    public static String getMacAddr(Context context) {
        String DEFAULT_MAC_ADDR = "";
        /** 增加获取mac 地址功能 */
        String retString = DEFAULT_MAC_ADDR;
        try {
            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);

            WifiInfo wifiInfo = wifiManager.getConnectionInfo();

            if (wifiInfo.getMacAddress() != null)
                retString = wifiInfo.getMacAddress();
            else {
                retString = DEFAULT_MAC_ADDR;
            }
        } catch (Exception e) {
            retString = DEFAULT_MAC_ADDR;
        }

        return retString;
    }



    /**
     * @param ctx
     * @return 是否有静默安装的权限
     */
    public static boolean checkSilenceInstallPermission(Context ctx) {
        PackageManager pm = ctx.getPackageManager();
        int result = pm.checkPermission(
                android.Manifest.permission.INSTALL_PACKAGES,
                ctx.getPackageName());
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }


}