package com.mysmallexample.ui.fragment;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mysmallexample.ui.adapter.PackageAdapter;
import com.mysmallexample.ui.utils.DataCleanManager;
import com.mysmallexample.ui.utils.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.mysmallexample.R;

/**
 * Created by taoshuang on 2016/10/19.
 */
public class FragmentPackage1Type extends BaseFragment implements View.OnClickListener {
    public static final String TAG = "FragmentPackageTypeFirst";
    private static final int TYPE_DIALOG = 0;
    private static final int TYPE_GET_APP = 1;

    private RecyclerView recyclerView;
    private PackageAdapter adapter;
    private List<Map> list;
    private Map<String, Object> map;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {

                case TYPE_GET_APP:
                    List<Map> listData = (List<Map>) msg.obj;
                    adapter = new PackageAdapter(getContext(), listData, FragmentPackage1Type.this);
                    recyclerView.setAdapter(adapter);
                    break;
                case TYPE_DIALOG:
                    try {
                        Bundle data = msg.getData();
                        String cache_Size = data.getString("cache_Size");
                        String data_Size = data.getString("data_Size");
                        String code_Size = data.getString("code_Size");
                        String total_Size = data.getString("total_Size");
                        String app_Label = data.getString("app_Label");

                        LayoutInflater infater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View dialog = infater.inflate(R.layout.dialog_app_size, null);
                        TextView tvcachesize = (TextView) dialog.findViewById(R.id.tvcachesize); //缓存大小
                        TextView tvdatasize = (TextView) dialog.findViewById(R.id.tvdatasize); //数据大小
                        TextView tvcodesize = (TextView) dialog.findViewById(R.id.tvcodesize); // 应用程序大小
                        TextView tvtotalsize = (TextView) dialog.findViewById(R.id.tvtotalsize); //总大小
                        //类型转换并赋值
                        tvcachesize.setText(cache_Size);
                        tvdatasize.setText(data_Size);
                        tvcodesize.setText(code_Size);
                        tvtotalsize.setText(total_Size);
                        //显示自定义对话框
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setView(dialog);
                        builder.setTitle(app_Label + "的大小信息为：");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.cancel();  // 取消显示对话框
                            }

                        });
                        builder.create().show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_package_type_first, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        list = new ArrayList<>();
        map = new HashMap<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                PackageManager pm = getContext().getPackageManager();
                Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
                // 调用系统排序 ， 根据name排序
                // 该排序很重要，否则只能显示系统应用，而不能列出第三方应用程序
                Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(pm));
                if (resolveInfos != null && resolveInfos.size() > 0) {
                    for (int i = 0; i < resolveInfos.size(); i++) {
                        ResolveInfo resolveInfo = resolveInfos.get(i);
                        String pkgName = resolveInfo.activityInfo.packageName;// 获得应用程序的包名
                        String activityName = resolveInfo.activityInfo.name;// 获得该应用程序的启动Activity的name
                        Drawable drawable = resolveInfo.loadIcon(pm);// 获得应用程序图标
                        String loadLabel = resolveInfo.loadLabel(pm).toString();// 获得应用程序的Label
                        map.put("appName" + i, loadLabel);
                        map.put("activityName" + i, activityName);
                        map.put("packageName" + i, pkgName);
                        map.put("drawable" + i, drawable);
                        list.add(map);
                    }
                }

                Message msg = new Message();
                msg.obj = list;
                msg.what = TYPE_GET_APP;
                handler.sendMessage(msg);
            }
        }).start();


        return view;
    }


    @Override
    public void onClick(View v) {
        try {
            if (v.getTag() != null && v.getTag() instanceof PackageAdapter.MyViewHolder && v.getId() == R.id.tv_start) {
                PackageAdapter.MyViewHolder myViewHolder = (PackageAdapter.MyViewHolder) v.getTag();
                String pkgName = myViewHolder.textView.getText().toString();
                String app_name = myViewHolder.app_name.getText().toString();
                String activityName = myViewHolder.activityName;
                Toast.makeText(getContext(), "启动：" + app_name, Toast.LENGTH_SHORT).show();
                Log.i("FragmentPackageTypeFirst", "LogUtils FragmentPackageTypeFirst  Name:" + pkgName + ", " + activityName);
                //第一种
                ComponentName componentName = new ComponentName(pkgName, activityName);
                Intent intent = new Intent();
                intent.setComponent(componentName);
                getActivity().startActivity(intent);

                //第二种
//                Intent launchIntentForPackage = getContext().getPackageManager().getLaunchIntentForPackage(pkgName);
//                if (launchIntentForPackage!=null){
//                    getContext().startActivity(launchIntentForPackage);
//                }
            } else if (v.getId() == PackageAdapter.itemViewId) {
                Toast.makeText(getContext(), "onClick", Toast.LENGTH_SHORT).show();
                PackageAdapter.MyViewHolder myViewHolder = (PackageAdapter.MyViewHolder) v.getTag();
                String pkgName = myViewHolder.textView.getText().toString();
                String app_name = myViewHolder.app_name.getText().toString();
                String activityName = myViewHolder.activityName;
                queryPacakgeSize(pkgName, app_name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void queryPacakgeSize(String pkgName, final String appName) throws Exception {

        if (pkgName != null) {
            //使用放射机制得到PackageManager类的隐藏函数getPackageSizeInfo
            PackageManager pm = getContext().getPackageManager();  //得到pm对象
            try {
                //通过反射机制获得该隐藏函数
                Method getPackageSizeInfo = pm.getClass().getMethod("getPackageSizeInfo", String.class, IPackageStatsObserver.class);
                //调用该函数，并且给其分配参数 ，待调用流程完成后会回调PkgSizeObserver类的函数
                getPackageSizeInfo.invoke(pm, pkgName, new IPackageStatsObserver.Stub() {
                    /***
                     * 回调函数，
                     *
                     * @param pStats    ,返回数据封装在PackageStats对象中
                     * @param succeeded 代表回调成功
                     */
                    @Override
                    public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
                            throws RemoteException {
                        // TODO Auto-generated method stub
                        long cache_Size = pStats.cacheSize;   //缓存大小
                        long data_Size = pStats.dataSize;     //数据大小
                        long code_Size = pStats.codeSize;     //应用程序大小
                        long total_Size = cache_Size + data_Size + code_Size;//总大小


                        Bundle bundle = new Bundle();
                        bundle.putString("cache_Size", DataCleanManager.getFormatSize(cache_Size));
                        bundle.putString("data_Size", DataCleanManager.getFormatSize(data_Size));
                        bundle.putString("code_Size", DataCleanManager.getFormatSize(code_Size));
                        bundle.putString("total_Size", DataCleanManager.getFormatSize(total_Size));
                        bundle.putString("app_Label", appName);
                        Message msg = new Message();
                        msg.setData(bundle);
                        msg.what = TYPE_DIALOG;
                        handler.sendMessage(msg);


                    }
                });
            } catch (Exception ex) {
                android.util.Log.i("MainActivity", "LogUtils MainActivity:ex.printStackTrace()");
                ex.printStackTrace();
            }
        }
    }
}
