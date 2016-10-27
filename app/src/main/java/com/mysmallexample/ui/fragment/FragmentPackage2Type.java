package com.mysmallexample.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysmallexample.ui.adapter.Package2Adapter;

import java.util.Collections;
import java.util.List;

import example.mysmallexample.R;

/**
 * Created by taoshuang on 2016/10/19.
 */
public class FragmentPackage2Type extends BaseFragment {
    public static final String TAG = "FragmentPackageTypeFirst";

    private RecyclerView recyclerView;
    private Package2Adapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<ApplicationInfo> applicationInfos = (List<ApplicationInfo>) msg.obj;
            adapter = new Package2Adapter(getContext(), applicationInfos);
            recyclerView.setAdapter(adapter);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_package_type_second, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        new Thread(new Runnable() {
            @Override
            public void run() {
                PackageManager packageManager = getContext().getPackageManager();
                //所有应用
                List<PackageInfo> installedPackages = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
                if (installedPackages != null && installedPackages.size() > 0) {
                    for (int i = 0; i < installedPackages.size(); i++) {
                        PackageInfo packageInfo = installedPackages.get(i);
                        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                        if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
                            //系统应用

                        } else if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                            //第三方应用

                        } else if ((applicationInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
                            //sd上应用
                        }

                    }
                }
                //ApplicationInfo
                List<ApplicationInfo> applicationInfos = packageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
                Message msg = new Message();
                msg.obj = applicationInfos;
                handler.sendMessage(msg);

            }
        }).start();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
//        Package2Adapter adapter = new Package2Adapter(getContext(), applicationInfos);
//        recyclerView.setAdapter(adapter);

        return view;
    }

    private void getResolveInfoInfo(Context context) {
        //获取PackageManager对象
        PackageManager pm = context.getPackageManager();
        //设置<intent-filter>标签内需要满足的条件
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_DEFAULT);

        //通过queryIntentActivites获取ResolveInfo对象
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        // 调用系统排序，根据name排序
        // 该排序很重要，否则只能显示系统应用，不能显示第三方应用
        // 其实我测试发现有没有其实是一样的，就是输出的顺序是乱的
        Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(pm));
        for (ResolveInfo resolveInfo : resolveInfos) {
            String appName = resolveInfo.loadLabel(pm).toString();// 获取应用名称
            String packageName = resolveInfo.activityInfo.packageName;// 包名
            String className = resolveInfo.activityInfo.name;// 入口类名
            System.out.println("程序名：" + appName + " 包名:" + packageName
                    + " 入口类名：" + className);
        }
    }


    private List<ResolveInfo> getShareApps(Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND, null);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("text/Pain");
        PackageManager pManager = context.getPackageManager();
        List<ResolveInfo> mApps = pManager.queryIntentActivities(intent, 0);
        return mApps;
    }


}
