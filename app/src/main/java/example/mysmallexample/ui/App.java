package example.mysmallexample.ui;

import android.app.Application;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import cn.jpush.android.api.JPushInterface;

/**
 * For developer startup JPush SDK
 * <p/>
 * 一般建议在自定义 Application 类里初始化。也可以在主 Activity 里。
 */
public class App extends Application {

    public static final String TAG = "App";

    private static App mInstance = null;
    public static volatile String imei = "555555555555555";
    public static volatile String android_id = "6666666666666666";
    public static volatile String uuid = "77777777-7777-7777-7777-777777777777";

    public  static int px_20dp;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        JPushInterface.setDebugMode(true);// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);// 初始化 JPush
        initPix();
    }

    public static App getInstance() {
        return mInstance;
    }

    private void initPix(){
        DisplayMetrics dm=getResources().getDisplayMetrics();
        px_20dp= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,20,dm);
    }

}
