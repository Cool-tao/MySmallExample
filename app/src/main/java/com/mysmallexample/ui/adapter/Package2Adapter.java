package com.mysmallexample.ui.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import example.mysmallexample.R;


/**
 * Created by taoshuang on 2016/10/19.
 */
public class Package2Adapter extends RecyclerView.Adapter {
    public static final String TAG = "PackageAdapter";
    private Context context;
    private List list;

    private static final int TYPE_SYSTEM = 1;
    private static final int TYPE_NOT_SYSTEM = 2;
    private static final int TYPE_EXTERNAL_STORAGE = 3;


    public Package2Adapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getItemViewType(int position) {
        ApplicationInfo applicationInfo = (ApplicationInfo) list.get(position);
        if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
            return TYPE_SYSTEM;
        } else if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
            return TYPE_NOT_SYSTEM;
        } else {
            return 0;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewype) {

        RecyclerView.ViewHolder viewHolder = null;
        View view;

        switch (viewype) {
            case TYPE_SYSTEM:
                view = LayoutInflater.from(context).inflate(R.layout.item_package2_layout, parent, false);
                viewHolder = new vHpkg2Type(view);
                break;
            case TYPE_NOT_SYSTEM:
                view = LayoutInflater.from(context).inflate(R.layout.item_package3_layout, parent, false);
                viewHolder = new vHpkg3Type(view);

                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        PackageManager pm = context.getPackageManager();
        ApplicationInfo applicationInfo = (ApplicationInfo) list.get(position);
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_SYSTEM:
                vHpkg2Type vhPkg2 = (vHpkg2Type) holder;
                vhPkg2.app_name.setText("" + applicationInfo.loadLabel(pm));
                vhPkg2.image_icon.setBackgroundDrawable(applicationInfo.loadIcon(pm));
                vhPkg2.pkg_name.setText("" + applicationInfo.packageName);
                vhPkg2.app_type.setText("系统应用");
                break;

            case TYPE_NOT_SYSTEM:
                vHpkg3Type vhPkg3 = (vHpkg3Type) holder;
                vhPkg3.app_name.setText("" + applicationInfo.loadLabel(pm));
                vhPkg3.image_icon.setBackgroundDrawable(applicationInfo.loadIcon(pm));
                vhPkg3.pkg_name.setText("" + applicationInfo.packageName);
                vhPkg3.app_type.setText("第三方应用");
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }

    class vHpkg2Type extends RecyclerView.ViewHolder {

        public TextView pkg_name;
        public ImageView image_icon;
        public TextView app_name;
        public TextView app_type;

        public vHpkg2Type(View itemView) {
            super(itemView);
            pkg_name = (TextView) itemView.findViewById(R.id.pkg_name);
            image_icon = (ImageView) itemView.findViewById(R.id.image_icon);
            app_name = (TextView) itemView.findViewById(R.id.app_name);
            app_type = (TextView) itemView.findViewById(R.id.app_type);

        }
    }

    class vHpkg3Type extends RecyclerView.ViewHolder {

        public TextView pkg_name;
        public ImageView image_icon;
        public TextView app_name;
        public TextView app_type;

        public vHpkg3Type(View itemView) {
            super(itemView);
            pkg_name = (TextView) itemView.findViewById(R.id.pkg_name);
            image_icon = (ImageView) itemView.findViewById(R.id.image_icon);
            app_name = (TextView) itemView.findViewById(R.id.app_name);
            app_type = (TextView) itemView.findViewById(R.id.app_type);

        }
    }
}
