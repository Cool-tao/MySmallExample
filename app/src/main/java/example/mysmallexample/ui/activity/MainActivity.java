package example.mysmallexample.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import example.mysmallexample.R;
import example.mysmallexample.ui.adapter.MainPagerAdapter;
import example.mysmallexample.ui.adapter.MyViewPager;
import example.mysmallexample.ui.fragment.BaseFragment;
import example.mysmallexample.ui.fragment.FragmentClassify;
import example.mysmallexample.ui.fragment.FragmentMe;
import example.mysmallexample.ui.fragment.FragmentRank;
import example.mysmallexample.ui.fragment.FragmentHome;

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


}
