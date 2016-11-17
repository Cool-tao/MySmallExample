package com.mysmallexample.ui.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 其实android SDK给我们提供了android.provider.Settings类,该类提供android系统各个页面的跳转常量
 */
public class AppManagerUtils {
    public static final String TAG = "AppManagerUtils";

    /**
     * 直接跳转卸载界面
     *
     * @param context
     */
    public void unInstallApp(Context context) {
        Uri packageUri = Uri.parse("package:" + context.getPackageName());
        Intent intent = new Intent(Intent.ACTION_DELETE, packageUri);
        context.startActivity(intent);
    }

    /**
     * 跳转到应用详细设置界面
     *
     * @param context
     */
    public void skipSetting(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        // dat=package:com.itheima.mobileguard
        intent.setData(Uri.parse("package:" + "com.ss.android.article.news"));
        if (intent == null) {
            return;
        }
        context.startActivity(intent);
    }


}
