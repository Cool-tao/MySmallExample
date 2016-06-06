package example.mysmallexample;

import android.app.ActivityManager;
import android.content.Context;
import android.test.AndroidTestCase;
import android.text.format.Formatter;

import java.util.List;

/**
 * Created by zhengnan on 2016/5/12.
 * 应用相关：如判断应用是否在运行
 */
public class TactService extends AndroidTestCase {
    public void testMain() {
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        //获取后台运行的应用列表，5.0后移除！
        am.getRunningTasks(100);
    }


    /**
     * @param context
     * @des 获取当前前台的包名
     */
    public static String getTopRunPackage(Context context) {
        try {
            ActivityManager am = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(5);
            return list.get(0).topActivity.getPackageName();
        } catch (Exception e) {
            if (BuildConfig.DEBUG)
                e.printStackTrace();
            return "";
        }
    }

    /**
     * 传入的包名是否在后台运行
     *
     * @param ctx
     * @return
     */
    public static boolean isPkgRunBack(Context ctx, String pname) {
        try {
            ActivityManager am = (ActivityManager) ctx
                    .getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).processName.equals(pname)) {
                    return true;
                }
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG)
                e.printStackTrace();
            return false;
        }
        return true;
    }
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return "";
    }
    /**
     * @param context
     * @des 获取当前前台的act的name (该name为全名，即填写在manifest中的内容)
     */
    public static String getTopAct(Context context) {
        try {
            ActivityManager am = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(5);
            return list.get(0).topActivity.getClassName().toString();
        } catch (Exception e) {
            if (BuildConfig.DEBUG)
                e.printStackTrace();
            return "";
        }
    }
    public static String getAvailMemory(Context ctx) {// 获取android当前可用内存大小

        ActivityManager am = (ActivityManager) ctx
                .getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        // mi.availMem; 当前系统的可用内存
        return Formatter.formatFileSize(ctx, mi.availMem);// 将获取的内存大小规格化
    }
}