package example.mysmallexample.ui.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.io.FileInputStream;
import java.util.Properties;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.PushBuilder;
import example.mysmallexample.R;
import example.mysmallexample.ui.EnumDemo;
import example.mysmallexample.ui.EnumTypeDemo;
import example.mysmallexample.ui.adapter.MainPagerAdapter;
import example.mysmallexample.ui.adapter.MyViewPager;
import example.mysmallexample.ui.fragment.BaseFragment;
import example.mysmallexample.ui.fragment.FragmentAboutMe;
import example.mysmallexample.ui.fragment.FragmentDiscover;
import example.mysmallexample.ui.fragment.FragmentHome1;
import example.mysmallexample.ui.fragment.FragmentRank;
import example.mysmallexample.ui.listener.TestListener;
import example.mysmallexample.ui.utils.Log;
import example.mysmallexample.ui.utils.SPUtil;
import example.mysmallexample.ui.utils.SetAlarmUtils;

public class MainActivity extends BaseActivity implements TestListener {

    private MyViewPager viewPager;
    private MainPagerAdapter adapter;
    private TextView[] tv_menu;
    private BaseFragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        fragments[0] = new FragmentHome1();
        fragments[1] = new FragmentRank();
        fragments[2] = new FragmentDiscover();
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
        SetAlarmUtils.setRepeatAlarm(this);
        SetAlarmUtils.setAlarm(this);

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

//        storeFile file(properties.getProperty("storeFile"))
//        storePassword properties.getProperty("storePassword")
//        keyAlias properties.getProperty("keyAlias")
//        keyPassword properties.getProperty("keyPassword")

        Properties prop = loadConfig("D:/workspace/MySmallExample/app/configs/signature.txt");
        String storeFile = prop.getProperty("storeFile");
        String storePassword = prop.getProperty("storePassword");
        String keyAlias = prop.getProperty("keyAlisa");
        String keyPassword = prop.getProperty("keyPassword");
        Log.i(TAG, "storeFile:" + storeFile + "storePassword:" + storePassword + "keyAlias:" + keyAlias + "keyPassword:" + keyPassword);


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
