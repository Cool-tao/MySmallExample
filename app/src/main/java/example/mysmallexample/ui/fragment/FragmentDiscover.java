package example.mysmallexample.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import example.mysmallexample.R;
import example.mysmallexample.ui.adapter.MainPagerAdapter;

@SuppressLint("ShowToast")
public class FragmentDiscover extends BaseFragment {
    public FragmentDiscover() {
    }

    private int linesize = 4;
    private BaseFragment[] fragments;
    /**
     * 自动滑动ViewPager
     */
//    private AutoScrollViewPager viewPager;
    private ViewPager viewPager;
    private MainPagerAdapter adapter;
    /**
     * ViewPager的当前选中页
     */
    private int currentIndex = 0;
    private TextView[] tv_menu;
    /**
     * Tab的那个引导线
     */
    private View mTabLineIv;
    /**
     * 屏幕的宽度
     */
    private int screenWidth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_classify, null);
        initLayout(layout);
        return layout;
    }

    private void initLayout(View layout) {
        // TODO Auto-generated method stub
        viewPager = (ViewPager) layout.findViewById(R.id.rank_pager);
        adapter = new MainPagerAdapter(getActivity()
                .getSupportFragmentManager());
        fragments = new BaseFragment[4];
        mTabLineIv = (View) layout.findViewById(R.id.cursor);
        fragments[0] = new FragmentRankList();
        fragments[1] = new FragmentRankList();
        fragments[2] = new FragmentRankList();
        fragments[3] = new FragmentRankList();
        adapter.add(fragments[0]);
        adapter.add(fragments[1]);
        adapter.add(fragments[2]);
        adapter.add(fragments[3]);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentIndex);
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        viewPager.setOffscreenPageLimit(adapter.getCount());
//        viewPager.startAutoScroll();
        initTabLineWidth();
        InitTextView(layout);
        viewPager.setCurrentItem(0);
        tv_menu[0].setSelected(true);
    }

    private void InitTextView(View layout) {
        tv_menu = new TextView[4];
        tv_menu[0] = (TextView) layout.findViewById(R.id.tv_week);
        tv_menu[1] = (TextView) layout.findViewById(R.id.tv_month);
        tv_menu[2] = (TextView) layout.findViewById(R.id.tv_single);
        tv_menu[3] = (TextView) layout.findViewById(R.id.tv_online);
        for (int i = 0; i < tv_menu.length; i++) {
            tv_menu[i].setOnClickListener(new MyOnClickListener(i));
        }
    }

    /**
     * 头标点击监听 3
     */
    private class MyOnClickListener implements OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        public void onClick(View v) {
            viewPager.setCurrentItem(index);
        }

    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        public void onPageScrollStateChanged(int arg0) {

        }

        public void onPageScrolled(int position, float offset, int offsetPixels) {
            /**
             * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来 设置mTabLineIv的左边距
             * 滑动场景： 记3个页面, 从左到右分别为0,1,2 0->1; 1->2; 2->1; 1->0
             */
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
                    .getLayoutParams();
            if (currentIndex == position)// 0->1
            {
                lp.leftMargin = (int) (offset * (screenWidth * 1.0 / linesize) + currentIndex
                        * (screenWidth / linesize));

            } else {
                lp.leftMargin = (int) (-(1 - offset)
                        * (screenWidth * 1.0 / linesize) + currentIndex
                        * (screenWidth / linesize));

            }
            mTabLineIv.setLayoutParams(lp);
        }

        public void onPageSelected(int position) {
            resetTextView();
            tv_menu[position].setSelected(true);
            currentIndex = position;
        }
    }

    /**
     * 设置滑动条的宽度为屏幕的1/4(根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getActivity().getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
                .getLayoutParams();
        lp.width = screenWidth / linesize;
        mTabLineIv.setLayoutParams(lp);
    }

    /**
     * 重置导航
     */
    private void resetTextView() {
        for (TextView textView : tv_menu) {
            textView.setSelected(false);
        }
    }
}