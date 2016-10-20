package com.mysmallexample.ui.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.mysmallexample.ui.EnumDemo;
import com.mysmallexample.ui.EnumTypeDemo;
import com.mysmallexample.ui.LoadJosnTask;
import com.mysmallexample.ui.adapter.MainPagerAdapter;
import com.mysmallexample.ui.adapter.MyViewPager;
import com.mysmallexample.ui.fragment.BaseFragment;
import com.mysmallexample.ui.fragment.FragmentAboutMe;
import com.mysmallexample.ui.fragment.FragmentFind;
import com.mysmallexample.ui.fragment.FragmentRank;
import com.mysmallexample.ui.fragment.FragmentRecommend;
import com.mysmallexample.ui.listener.TestListener;
import com.mysmallexample.ui.utils.DeviceUtil;
import com.mysmallexample.ui.utils.Log;
import com.mysmallexample.ui.utils.SPUtil;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.PushBuilder;
import example.mysmallexample.R;

public class MainActivity extends BaseActivity implements TestListener {

    private MyViewPager viewPager;
    private MainPagerAdapter adapter;
    private TextView[] tv_menu;
    private BaseFragment[] fragments;
//    public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000; //屏蔽Home键，需要自己定义标志

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);//屏蔽Home键，关键代码
        /**
         * JPush获取Android6.0权限
         */
        JPushInterface.requestPermission(this);

        setContentView(R.layout.activity_main);
        changeStatusBar();
        viewPager = (MyViewPager) findViewById(R.id.main_pager);
        adapter = new MainPagerAdapter(getSupportFragmentManager());
        fragments = new BaseFragment[4];
        tv_menu = new TextView[4];

        tv_menu[0] = (TextView) findViewById(R.id.tv_tab_menu0);
        tv_menu[1] = (TextView) findViewById(R.id.tv_tab_menu1);
        tv_menu[2] = (TextView) findViewById(R.id.tv_tab_menu2);
        tv_menu[3] = (TextView) findViewById(R.id.tv_tab_menu3);

        for (int i = 0; i < tv_menu.length; i++) {
            final int cur = i;
            tv_menu[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    onClickIndex(cur);
                }
            });
        }

        fragments[0] = new FragmentRecommend();
        fragments[1] = new FragmentRank();
//        fragments[2] = new FragmentDiscover();
        fragments[2] = new FragmentFind();
        fragments[3] = new FragmentAboutMe();
        adapter.add(fragments[0]);
        adapter.add(fragments[1]);
        adapter.add(fragments[2]);
        adapter.add(fragments[3]);
        viewPager.setOffscreenPageLimit(adapter.getCount());
        viewPager.setAdapter(adapter);
        onClickIndex(0);
        setStyleCustom();
        //闹钟设置
//        SetAlarmUtils.setRepeatAlarm(this);
//        SetAlarmUtils.setAlarm(this);CommonReceiver

        //EnumDemo测试
        Log.i(TAG, "EnumDemo :" + EnumDemo.Enum_One.options
                + "\t" + EnumDemo.Enum_Second.options
                + "\t" + EnumDemo.Enum_Third.options
                + "\t" + EnumDemo.Enum_Fourth.options
                + "\t" + EnumDemo.Enum_Fifth.options
                + "\t" + EnumDemo.Defult.options);

        for (EnumTypeDemo enumTypeDemo : EnumTypeDemo.values()) {
            Log.e(TAG, "enumTypeDemo name:" + enumTypeDemo.name()
                    + "\tenumTypeDemo vlaus:" + enumTypeDemo.getType());
        }

        Properties prop = loadConfig("D:/workspace/MySmallExample/app/configs/signature.txt");
        String storeFile = prop.getProperty("storeFile");
        String storePassword = prop.getProperty("storePassword");
        String keyAlias = prop.getProperty("keyAlisa");
        String keyPassword = prop.getProperty("keyPassword");
        Log.i(TAG, "storeFile:" + storeFile + "storePassword:" + storePassword + "keyAlias:" + keyAlias + "keyPassword:" + keyPassword);


        new LoadJosnTask(this).execute("LoadJsonTask");


        boolean isTrue = false;
        String str = isTrue ? "1" : "2";

        int top = getSysBarHeight();
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        Log.i("MainActivity", "statusBarHeight  1=" + statusBarHeight + ":" + top);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5 * 10 * 1000);
                    Log.i("MainActivity", "LogUtils MainActivity：" + DeviceUtil.isApplicationBroughtToBackground(MainActivity.this));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        Collections.sort(installedApplications,new ApplicationInfo.
                DisplayNameComparator(packageManager));
        for (int i = 0; i < installedApplications.size(); i++) {
            ApplicationInfo applicationInfo = installedApplications.get(i);
            CharSequence charSequence = applicationInfo.loadLabel(packageManager);
            Log.i("MainActivity", "LogUtils MainActivity appName:" + charSequence.toString() + ", flags：" + applicationInfo.flags);
            if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
                //是系统

            } else {
                //不是系统
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
////            dialog();
//            return true;
//        } else
//        if (keyCode == KeyEvent.KEYCODE_MENU) {
//            Toast.makeText(MainActivity.this, "Menu", Toast.LENGTH_SHORT).show();
//            return false;
//        } else if (keyCode == KeyEvent.KEYCODE_HOME) {
//            //由于Home键为系统键，此处不能捕获，需要重写onAttachedToWindow()
//            Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        return super.onKeyDown(keyCode, event);
    }


//    // 拦截/屏蔽系统Home键
//    public void onAttachedToWindow() {
//        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
//        super.onAttachedToWindow();
//    }


    @Override
    protected void onResume() {
        super.onResume();
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        Log.i("MainActivity", "statusBarHeight  2=" + statusBarHeight);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        Log.i("MainActivity", "statusBarHeight  2=" + statusBarHeight);
    }

    /**
     * 状态栏高度
     *
     * @return
     */
    public int getSysBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        int sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            Log.e("Jingle.du", "get status bar height fail");
            e1.printStackTrace();
        }
        return sbar;
    }

    public static Properties loadConfig(String file) {
        Properties properties = new Properties();
        try {
            FileInputStream s = new FileInputStream(file);
            properties.load(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

    public void onClickIndex(int index) {
        // TODO Auto-generated method stub
        viewPager.setCurrentItem(index, false);
        for (int i = 0; i < tv_menu.length; i++) {
            if (index == i) {
                tv_menu[i].setSelected(true);
            } else {
                tv_menu[i].setSelected(false);
            }
        }
    }

    /**
     * 可以设置在Application中，在JPush初始化之后
     */
    private void setStyleCustom() {
        //系统默认自定义
        //CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(MainActivity.this, R.layout.customer_notitfication_layout, R.id.icon, R.id.title, R.id.text);
        //重写自定义布局
        PushBuilder builder = new PushBuilder(MainActivity.this, R.layout.customer_notitfication_layout, R.id.icon, R.id.title, R.id.text, R.id.time);
        //PushBuilder builder = new PushBuilder(MainActivity.this,R.layout.notification_text,R.id.icon, R.id.title, R.id.text,R.id.time);
        builder.layoutIconDrawable = R.mipmap.ic_launcher;
        builder.developerArg0 = "developerArg2";
        //设置发送时TAG=2
        //JPushInterface.setPushNotificationBuilder(2, builder);
        //将默认设置为此样式，发送时不需要设置TAG
        JPushInterface.setDefaultPushNotificationBuilder(builder);
//        Toast.makeText(this, "Custom Builder - 2", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Custom Builder - 2");
    }

    /**
     * 自定义通知栏
     */

    private void localNotify() {
        RemoteViews contentViews = new RemoteViews(getPackageName(), R.layout.notification_text);
        //通过控件的Id设置属性
        //contentViews.setImageViewResource(R.id.imageNo, R.drawable.btm1);
        contentViews.setTextViewText(R.id.title, "自定义通知标题");
        contentViews.setTextViewText(R.id.text, "自定义通知内容自定义通知内容自定义通知内容自定义通知内容自定义通知内容自定义通知内容自定义通知内容自定义通知内容自定义通知内容自定义通知内容自定义通知内容自定义通知内容");
        contentViews.setTextViewText(R.id.time, "" + System.currentTimeMillis());
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        //点击跳转广播
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                MainActivity.this).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My notification")
                .setTicker("new message");
        mBuilder.setAutoCancel(true);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setContent(contentViews);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(10, mBuilder.build());
    }

    @Override
    public void onBackPressed() {
        SPUtil.exit(this);
    }

    @Override
    public void onTestListener(String message) {
        Log.e("MainActivity", "接口回调练习：" + message);
    }
}
