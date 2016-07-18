package example.mysmallexample.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import example.mysmallexample.R;
import example.mysmallexample.customview.ViewPagerIndicator;

@SuppressLint("ValidFragment")
public class FragmentFind extends BaseFragment implements View.OnClickListener {
    private List<Fragment> mTabContents = new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;
    private ViewPager mViewPager;
    //private List<String> mDatas = Arrays.asList("短信1", "短信2", "短信3", "短信4",
    //"短信5", "短信6", "短信7", "短信8", "短信9");
    private List<String> mDatas = Arrays.asList("首页", "排行", "推荐", "发现");

    private ViewPagerIndicator mIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.vp_indicator, null);
        initView(layout);
        initDatas();
        //设置Tab上的标题
        mIndicator.setTabItemTitles(mDatas);
        mViewPager.setAdapter(mAdapter);
        //设置关联的ViewPager
        mIndicator.setViewPager(mViewPager, 0);
        return layout;
    }

    private void initDatas() {

        for (String data : mDatas) {
            VpSimpleFragment fragment = VpSimpleFragment.newInstance(data);
            mTabContents.add(fragment);
        }

        mAdapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTabContents.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mTabContents.get(position);
            }
        };
    }

    private void initView(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.id_vp);
        mIndicator = (ViewPagerIndicator) view.findViewById(R.id.id_indicator);
    }


    @Override
    public void onClick(View v) {
    }


}
