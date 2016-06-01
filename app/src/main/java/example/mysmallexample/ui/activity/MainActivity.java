package example.mysmallexample.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.PushBuilder;
import example.mysmallexample.R;
import example.mysmallexample.ui.adapter.MainPagerAdapter;
import example.mysmallexample.ui.adapter.MyViewPager;
import example.mysmallexample.ui.fragment.BaseFragment;
import example.mysmallexample.ui.fragment.FragmentAboutMe;
import example.mysmallexample.ui.fragment.FragmentDiscover;
import example.mysmallexample.ui.fragment.FragmentHome;
import example.mysmallexample.ui.fragment.FragmentRank;
import example.mysmallexample.ui.utils.Log;
import example.mysmallexample.ui.utils.SPUtil;

public class MainActivity extends BaseActivity {

    private MyViewPager viewPager;
    private MainPagerAdapter adapter;
    private TextView[] tv_menu;
    private BaseFragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        fragments[0] = new FragmentHome();
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


    @Override
    public void onBackPressed() {
        SPUtil.exit(this);
    }
}
