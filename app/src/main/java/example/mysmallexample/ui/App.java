package example.mysmallexample.ui;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by taoshuang on 2016/5/31.
 */
public class App extends Application {
    public static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);
    }
}
