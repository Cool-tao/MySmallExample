package example.mysmallexample.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;
import example.mysmallexample.R;
import example.mysmallexample.model.NetworkEvent;
import example.mysmallexample.ui.fragment.NetworkDialogFragment;
import example.mysmallexample.ui.fragment.NetworkMobileDialogFragment;
import example.mysmallexample.ui.utils.Const;
import example.mysmallexample.ui.utils.Log;
import example.mysmallexample.ui.utils.SPUtil;
import example.mysmallexample.ui.utils.SystemBarTintManager;

/**
 * Created by taoshuang on 2016/5/24.
 */
public class BaseActivity extends FragmentActivity {
    public static final String TAG = "BaseActivity";
    protected boolean isResume = false;
    protected FragmentManager fm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getSupportFragmentManager();
        EventBus.getDefault().register(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onDestroy() {
        isResume = false;
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        isResume = false;
        super.onPause();
        JPushInterface.onPause(this);
    }


    @Override
    protected void onStart() {
        isResume = true;
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        isResume = false;
    }

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        JPushInterface.onResume(this);
        checkNetwork();
    }


    public void onEventMainThread(NetworkEvent event) {
        Log.e(TAG, "onEventMainThread:");
        checkNetwork();
    }


    //检查网络电话
    protected static int networkStatus = 9;

    public void checkNetwork() {
        NetworkInfo networkInfo = null;
        networkInfo = SPUtil.getNetworkInfo(getApplicationContext());

        Log.e(TAG, "networkInfo:" + networkInfo);

        if (networkInfo == null) {
            if (networkStatus < Const.NETWORK_OFFLINE) {
                Log.e(TAG, "isResume:" + isResume);
                if (!isResume) {
                    return;
                }
                networkStatus = Const.NETWORK_OFFLINE;
                if (!NetworkDialogFragment.shown) {
                    NetworkDialogFragment fragment = (NetworkDialogFragment) Fragment.instantiate(this, NetworkDialogFragment.class.getName());
                    fragment.show(fm, NetworkDialogFragment.TAG);
                }
            }
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            if (networkStatus != Const.NETWORK_WIFI) {
                if (!isResume) {
                    return;
                }
                networkStatus = Const.NETWORK_WIFI;
                NetworkDialogFragment.dismissIfShow();
                NetworkMobileDialogFragment.dismissIfShow();
                if (Const.isSaveMode()) {
//                    TUtils.toast(getString(R.string.switch_wifi));
                    Toast.makeText(this, "" + getString(R.string.switch_wifi), Toast.LENGTH_SHORT).show();
                    Log.i(TAG, getString(R.string.switch_wifi));
                }
                Const.setBestMode();
            }
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            if (networkStatus != Const.NETWORK_MOBILE) {
                if (!isResume) {
                    return;
                }
                NetworkDialogFragment.dismissIfShow();
                networkStatus = Const.NETWORK_MOBILE;
                if (!Const.isSaveMode() && !NetworkMobileDialogFragment.shown) {
                    NetworkMobileDialogFragment fragment = (NetworkMobileDialogFragment) Fragment.instantiate(this, NetworkMobileDialogFragment.class.getName());
                    fragment.show(fm, NetworkMobileDialogFragment.TAG);
                }
            }
        }

    }



    public void changeStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.system_bar);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    // 获取点击事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                HideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // 判定是否需要隐藏输入法
    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                    && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    // 隐藏软键盘
    private void HideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
