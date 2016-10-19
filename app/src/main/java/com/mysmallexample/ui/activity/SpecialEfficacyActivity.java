package com.mysmallexample.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.mysmallexample.ui.fragment.FragmentTest;

import example.mysmallexample.R;

/**
 * Created by taoshuang on 2016/6/8.
 */
public class SpecialEfficacyActivity extends BaseActivity {
    private FragmentTest fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_efficacy);
        Bundle args = new Bundle();
        fragment = new FragmentTest();
        args.putString("id", "id");
        fragment.setArguments(args);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.root_layout, fragment);
        ft.commit();

    }


}
