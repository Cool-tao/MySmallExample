package com.cool.makejarlib.utils.notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.cool.makejarlib.R;

public class NotificationUtils {

    /**
     * 发送不会重复的通知
     *
     * @param context
     * @param title       标题
     * @param message     消息
     * @param SmallIconId 图标
     * @param activity    要启动的类
     * @param extras      传递的参数
     */
    @SuppressLint("NewApi")
    public static void sendNotification(Context context, String title,
                                        String message, int SmallIconId, Class<?> activity, Bundle extras) {

        Intent mIntent = new Intent(context, activity);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (extras != null) {
            mIntent.putExtras(extras);
        }


        int requestCode = (int) System.currentTimeMillis();

        PendingIntent mContentIntent = PendingIntent.getActivity(context,
                requestCode, mIntent, 0);

        Notification mNotification = new Notification.Builder(context)
                .setContentTitle(title).setSmallIcon(SmallIconId)
                .setContentIntent(mContentIntent).setContentText(message)
                .build();
        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotification.defaults = Notification.DEFAULT_ALL;

        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(requestCode, mNotification);
    }

    /**
     * 自定义通知栏
     */

    private void CustomNotify(Context context, int layoutId, String title, String content, Class compassClass, int iconId) {
        RemoteViews contentViews = new RemoteViews(context.getPackageName(), layoutId);
        //通过控件的Id设置属性
        //contentViews.setImageViewResource(R.id.imageNo, R.drawable.btm1);
        contentViews.setTextViewText(R.id.title, title);
        contentViews.setTextViewText(R.id.text, content);
        contentViews.setTextViewText(R.id.time, "" + System.currentTimeMillis());
        Intent intent = new Intent(context, compassClass);
        int requestCode = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        //点击跳转广播
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(iconId)
                .setContentTitle(title)
                .setTicker(content);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setContent(contentViews);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(requestCode, mBuilder.build());
    }

    /**
     * 清除所有通知
     *
     * @param context
     */
    public static void cancelNotification(Context context) {

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

}
