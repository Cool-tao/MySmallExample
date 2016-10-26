package com.mysmallexample.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysmallexample.ui.adapter.MainPagerAdapter;

import example.mysmallexample.R;

/**
 * Created by taoshuang on 2016/10/19.
 */
public class FragmentPackage extends BaseFragment {
    public static final String TAG = "FragmentPackage";
    private View[] tv_menu;
    private ViewPager viewPager;
    private BaseFragment[] fragments;
    private MainPagerAdapter adapter;
    private int crrentIndex = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_package, null);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        adapter = new MainPagerAdapter(getActivity().getSupportFragmentManager());
        tv_menu = new View[2];
        tv_menu[0] = view.findViewById(R.id.menu_type1);
        tv_menu[1] = view.findViewById(R.id.menu_type2);
        fragments = new BaseFragment[2];
        fragments[0] = new FragmentPackage1Type();
        fragments[1] = new FragmentPackage2Type();
        adapter.add(fragments[0]);
        adapter.add(fragments[1]);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(crrentIndex);
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        //预加载数
        viewPager.setOffscreenPageLimit(adapter.getCount());
        for (int i = 0; i < tv_menu.length; i++) {
            tv_menu[i].setOnClickListener(new MyOnClickListener(i));
        }
        tv_menu[0].setSelected(true);
        return view;
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for (View textView : tv_menu) {
                textView.setSelected(false);
            }
            tv_menu[position].setSelected(true);
            crrentIndex = position;


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            viewPager.setCurrentItem(index);
        }
    }
}
