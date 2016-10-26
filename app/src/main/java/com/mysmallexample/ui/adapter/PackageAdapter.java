package com.mysmallexample.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import example.mysmallexample.R;


/**
 * Created by taoshuang on 2016/10/19.
 */
public class PackageAdapter extends RecyclerView.Adapter {
    public static final String TAG = "PackageAdapter";
    private Context context;
    private List list;
    private View.OnClickListener onClickListener;
    private View.OnLongClickListener longClickListener;


    public PackageAdapter(Context context, List list, View.OnClickListener onClickListener) {
        this.context = context;
        this.list = list;
        this.onClickListener = onClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewype) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_package_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(onClickListener);
        return myViewHolder;
    }

    public static final int itemViewId = 0000;


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            Map map = (Map) list.get(position);
            map.put("position" + position, position);
            String appName = (String) map.get("appName" + position);
            String activityName = (String) map.get("activityName" + position);
            String packageName = (String) map.get("packageName" + position);
            Drawable drawable = (Drawable) map.get("drawable" + position);
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.activityName=activityName;
            myViewHolder.textView.setText(packageName);
            myViewHolder.image_icon.setBackgroundDrawable(drawable);
            myViewHolder.app_name.setText(appName);
            //某个控件
            myViewHolder.tv_start.setTag(myViewHolder);
            myViewHolder.tv_start.setOnClickListener(onClickListener);
            //点击一个itemView
            myViewHolder.itemView.setTag(myViewHolder);
            myViewHolder.itemView.setId(itemViewId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ImageView image_icon;
        public TextView app_name;
        public TextView tv_start;
        public String activityName;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_tv_name);
            image_icon = (ImageView) itemView.findViewById(R.id.image_icon);
            app_name = (TextView) itemView.findViewById(R.id.app_name);
            tv_start = (TextView) itemView.findViewById(R.id.tv_start);
        }
    }
}
