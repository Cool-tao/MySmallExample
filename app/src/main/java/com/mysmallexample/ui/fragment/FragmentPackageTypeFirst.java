package com.mysmallexample.ui.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysmallexample.ui.adapter.PackageAdapter;

import java.util.ArrayList;
import java.util.List;

import example.mysmallexample.R;

/**
 * Created by taoshuang on 2016/10/19.
 */
public class FragmentPackageTypeFirst extends BaseFragment {
    public static final String TAG = "FragmentPackageTypeFirst";

    private RecyclerView recyclerView;
    private PackageAdapter adapter;
    private List list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_package_type_first, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        list=new ArrayList<>();
        PackageManager pm = getContext().getPackageManager();
        List<PackageInfo> packageInfos = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (int i = 0; i < packageInfos.size(); i++) {
            PackageInfo packageInfo = packageInfos.get(i);
            String packageName = packageInfo.packageName;
            list.add(packageName);
        }
        adapter = new PackageAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
