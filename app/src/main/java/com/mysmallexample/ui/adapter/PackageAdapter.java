package com.mysmallexample.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import example.mysmallexample.R;


/**
 * Created by taoshuang on 2016/10/19.
 */
public class PackageAdapter extends RecyclerView.Adapter {
    public static final String TAG = "PackageAdapter";
    private Context context;
    private List list;


    public PackageAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewype) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_package_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        list.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;

    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_tv_name);
        }
    }
}
