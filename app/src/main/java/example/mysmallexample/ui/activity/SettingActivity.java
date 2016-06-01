package example.mysmallexample.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import cn.jpush.android.api.JPushInterface;
import example.mysmallexample.R;
import example.mysmallexample.customview.switchbutton.SwitchButton;
import example.mysmallexample.ui.utils.LocationUtils;
import example.mysmallexample.ui.utils.Log;

/**
 * Created by taoshuang on 2016/6/1.
 */
public class SettingActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    public static final String TAG = "SettingActivity";

    private View back;
    private SwitchButton notify_switcher;
    private TextView title;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 定位不成功
         */
        LocationUtils.getLocation(getApplicationContext());
        setContentView(R.layout.activity_setting);
        initViews();
        Log.i(TAG, "JPush is Stop=" + JPushInterface.isPushStopped(this));

    }

    /**
     * 初始化控件
     */
    private void initViews() {

        back = findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.setting));

        back.setOnClickListener(this);
        notify_switcher = (SwitchButton) findViewById(R.id.notify_switcher);
        if (spu.getOffTuiSong()) {
            notify_switcher.setChecked(true);
        } else {
            notify_switcher.setChecked(false);
        }
        notify_switcher.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.notify_switcher:
                pushSwitch(isChecked);
                break;
        }
    }

    private void pushSwitch(boolean isChecked) {
//		保存推送开关状态
        spu.setOffTuiSong(isChecked);
        if (JPushInterface.isPushStopped(this)) {
            JPushInterface.init(this);
            JPushInterface.resumePush(this);
        }
        if (!isChecked) {
            JPushInterface.stopPush(this);
        }
//        if (isChecked) {
//            JPushInterface.resumePush(this);
//        } else {
//            JPushInterface.stopPush(this);
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
