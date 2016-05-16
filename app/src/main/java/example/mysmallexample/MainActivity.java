package example.mysmallexample;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

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

        fragments[0] = new FragmentRecommend();
        fragments[1] = new FragmentRank();
        fragments[2] = new FragmentClassify();
        fragments[3] = new FragmentMe();
        adapter.add(fragments[0]);
        adapter.add(fragments[1]);
        adapter.add(fragments[2]);
        adapter.add(fragments[3]);
        viewPager.setOffscreenPageLimit(adapter.getCount());
        viewPager.setAdapter(adapter);
        onClickIndex(0);
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

    protected void changeStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);

//        TypedValue typedValue = new TypedValue();
//        getTheme().resolveAttribute(R.attr.title_bar_color, typedValue, true);
//        int color = typedValue.data;
//        tintManager.setStatusBarTintColor(color);
        tintManager.setStatusBarTintResource(R.color.system_bar);
    }


    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
