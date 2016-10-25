package com.mysmallexample.ui.fragment;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysmallexample.ui.adapter.Package2Adapter;

import java.util.List;

import example.mysmallexample.R;

/**
 * Created by taoshuang on 2016/10/19.
 */
public class FragmentPackageTypeSecond extends BaseFragment {
    public static final String TAG = "FragmentPackageTypeFirst";

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_package_type_second, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        PackageManager packageManager = getContext().getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        if (installedPackages != null && installedPackages.size() > 0) {
            for (int i = 0; i < installedPackages.size(); i++) {
                PackageInfo packageInfo = installedPackages.get(i);
                ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
                    //系统应用
                    Log.i("FragmentPackageTypeSecond", "LogUtils FragmentPackageTypeSecond:" + applicationInfo.loadLabel(packageManager));

                } else if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                    //非系统应用

                } else if ((applicationInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
                    //sd上应用
                }

            }
        }
        List<ApplicationInfo> applicationInfos = packageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        Package2Adapter adapter = new Package2Adapter(getContext(), applicationInfos);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
