package example.mysmallexample.servers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import example.mysmallexample.ui.utils.Log;
import example.mysmallexample.ui.utils.SetAlarmUtils;

/**
 * Created by taoshuang on 2016/6/15.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    public static final String TAG = "MyBroadcastReceiver";
    public static final String ACTION_SET_REPEAT_ALARM = "com.example.RepeatAlarm";
    public static final String ACTION_SET_ALARM = "com.example.Alarm";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case ACTION_SET_REPEAT_ALARM:
                Log.i(TAG, "重复闹钟");
                Toast.makeText(context, "重复闹钟 10s", Toast.LENGTH_SHORT).show();
                break;
            case ACTION_SET_ALARM:
                Log.i(TAG, "单次闹钟");
                Toast.makeText(context, "单次闹钟 35s", Toast.LENGTH_SHORT).show();
                SetAlarmUtils.closeRepeatAlarm(context);
                break;
        }
    }
}
