package com.mysmallexample.servers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mysmallexample.model.NetworkEvent;
import com.mysmallexample.ui.utils.Const;
import com.mysmallexample.ui.utils.Log;
import com.mysmallexample.ui.utils.SPUtil;

import de.greenrobot.event.EventBus;

/**
 * 检查网络连接状态信息广播
 * Created by taoshuang on 2016/5/31.
 */
public class CommonReceiver extends BroadcastReceiver {
    public static final String TAG = "CommonReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        switch (action) {
            case ConnectivityManager.CONNECTIVITY_ACTION:
                checkNetwork(context);
                break;
        }
    }

    private void checkNetwork(Context context) {
        Log.e("test", "News: Event");
        NetworkInfo networkInfo = SPUtil.getNetworkInfo(context);
        EventBus.getDefault().post(new NetworkEvent());
        Const.checkNetwork(networkInfo);
        if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
            Intent intent = new Intent(context, CommonReceiver.class);
            context.startService(intent);
        }
    }

}
