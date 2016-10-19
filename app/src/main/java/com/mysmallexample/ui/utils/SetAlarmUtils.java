package com.mysmallexample.ui.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by taoshuang on 2016/6/15.
 */
public class SetAlarmUtils {
    public static final String TAG = "SetAlarmUtils";
    public static final String ACTION_SET_REPEAT_ALARM = "com.example.RepeatAlarm";
    public static final long SET_REPEAT_ALARM_TIME = 1 * 1 * 10 * 1000;
    public static final String ACTION_SET_ALARM = "com.example.Alarm";
    public static final long SET_ALARM_TIME = 1 * 1 * 35 * 1000;

    /**
     * 开启重复闹钟
     *
     * @param context
     */
    public static void setRepeatAlarm(Context context) {
        try {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(ACTION_SET_REPEAT_ALARM);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0x32, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager.cancel(pendingIntent);
            //当前时间10s后
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + SET_REPEAT_ALARM_TIME, SET_REPEAT_ALARM_TIME, pendingIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭重复闹钟
     *
     * @param context
     */
    public static void closeRepeatAlarm(Context context) {
        try {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(ACTION_SET_REPEAT_ALARM);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0x32, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager.cancel(pendingIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开启一次性闹钟
     *
     * @param context
     */
    public static void setAlarm(Context context) {
        try {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(ACTION_SET_ALARM);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0x00, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager.cancel(pendingIntent);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + SET_ALARM_TIME, pendingIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
