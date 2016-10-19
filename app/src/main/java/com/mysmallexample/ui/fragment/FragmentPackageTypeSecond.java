package com.mysmallexample.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.mysmallexample.R;

/**
 * Created by taoshuang on 2016/10/19.
 */
public class FragmentPackageTypeSecond extends BaseFragment {
    public static final String TAG = "FragmentPackageTypeFirst";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_package_type_second,null);

        return view;
    }
}
