package com.mysmallexample.ui.fragment;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mysmallexample.ui.adapter.PackageAdapter;
import com.mysmallexample.ui.utils.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.mysmallexample.R;

/**
 * Created by taoshuang on 2016/10/19.
 */
public class FragmentPackageTypeFirst extends BaseFragment implements View.OnClickListener {
    public static final String TAG = "FragmentPackageTypeFirst";

    private RecyclerView recyclerView;
    private PackageAdapter adapter;
    private List<Map> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_package_type_first, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);


        list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        PackageManager pm = getContext().getPackageManager();
        List<PackageInfo> packageInfos = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (int i = 0; i < packageInfos.size(); i++) {
            PackageInfo packageInfo = packageInfos.get(i);
            String packageName = packageInfo.packageName;
            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            Log.i("FragmentPackageTypeFirst", "LogUtils FragmentPackageTypeFirst flags：" + applicationInfo.flags);
            /**
             * Value for {@link #flags}: if set, this application is installed in the
             * device's system image.
             */
            if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
                //系统程序

            } else {
                //不是系统程序

            }
            String appName = applicationInfo.loadLabel(pm).toString();
            Drawable drawable = applicationInfo.loadIcon(pm);
            map.put("appName" + i, appName);
            map.put("packageName" + i, packageName);
            map.put("versionCode" + i, versionCode);
            map.put("versionName" + i, versionName);
            map.put("drawable" + i, drawable);
            list.add(map);
        }
        adapter = new PackageAdapter(getContext(), list, this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onClick(View v) {
        try {
            if (v.getTag() != null && v.getTag() instanceof PackageAdapter.MyViewHolder) {
                PackageAdapter.MyViewHolder myViewHolder = (PackageAdapter.MyViewHolder) v.getTag();
                Toast.makeText(getContext(), myViewHolder.app_name.getText(), Toast.LENGTH_SHORT).show();
                Log.i("FragmentPackageTypeFirst", "LogUtils FragmentPackageTypeFirst  app_name:" + myViewHolder.app_name.getText());
            } else if (v.getId() == PackageAdapter.itemViewId) {
                Toast.makeText(getContext(), "onClick", Toast.LENGTH_SHORT).show();
                Integer position = (int) v.getTag();
                Log.i("FragmentPackageTypeFirst", "LogUtils FragmentPackageTypeFirst  position:" + position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
