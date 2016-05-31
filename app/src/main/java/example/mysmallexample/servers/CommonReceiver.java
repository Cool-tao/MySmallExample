package example.mysmallexample.servers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import de.greenrobot.event.EventBus;
import example.mysmallexample.model.NetworkEvent;
import example.mysmallexample.ui.utils.Const;
import example.mysmallexample.ui.utils.Log;
import example.mysmallexample.ui.utils.SPUtil;

/**
 * Created by taoshuang on 2016/5/31.
 */
public class CommonReceiver extends BroadcastReceiver {
    public static final String TAG = "CommonReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        String action=intent.getAction();
        switch (action){
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
