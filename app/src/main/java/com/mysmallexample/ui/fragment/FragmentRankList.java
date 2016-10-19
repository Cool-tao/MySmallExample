package com.mysmallexample.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import example.mysmallexample.R;

@SuppressLint("ValidFragment")
public class FragmentRankList extends BaseFragment {


    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_rank_list, null);
        imageView = (ImageView) layout.findViewById(R.id.progress);
        startAnim(imageView);
        return layout;
    }

    /**
     * 开始进度条动画
     *
     * @param progress
     */
    private void startAnim(ImageView progress) {
        RotateAnimation anim = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        anim.setDuration(2000);
        anim.setRepeatCount(60);
        progress.startAnimation(anim);

    }

}
