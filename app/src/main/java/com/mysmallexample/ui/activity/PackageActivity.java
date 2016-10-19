package com.mysmallexample.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.mysmallexample.ui.fragment.FragmentPackage;

import example.mysmallexample.R;

/**
 * Created by taoshuang on 2016/10/19.
 */
public class PackageActivity extends BaseActivity {
    public static final String TAG = "PackageActivity";

    private FragmentPackage fragmentPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);
        fragmentPackage = new FragmentPackage();

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.root_layout, fragmentPackage);
        transaction.commit();


    }
}
