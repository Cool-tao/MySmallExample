package example.mysmallexample;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.test.AndroidTestCase;

/**
 * Created by zhengnan on 2016/5/24.
 * 主要管理和联网相关的内容。一般需要关联权限 android.permission.ACCESS_NETWORK_STATE
 * 主要靠NetworkInfo来实现
 */
public class TConnectivityService extends AndroidTestCase {
    public void testMain() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    //获取是否可用
    public boolean netIsAva(ConnectivityManager cm){
        return cm.getActiveNetworkInfo().isAvailable();
    }

    //网络类型
    public void netTYpe(ConnectivityManager cm){
        NetworkInfo.State state = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if(NetworkInfo.State.CONNECTED==state){
            //GPRS
        }
        state = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if(NetworkInfo.State.CONNECTED==state){
            //wifi
        }
    }
}
