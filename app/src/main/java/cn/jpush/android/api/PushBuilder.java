package cn.jpush.android.api;

import android.content.Context;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;

import example.mysmallexample.R;

/**
 * 自定义JPush
 * 重写Builder，包名与api中CustomPushNotificationBuilder一致，继承BasicPushNotificationBuilder
 * 类似CustomPushNotificationBuilder
 */
public class PushBuilder extends BasicPushNotificationBuilder {

    public int layout;
    public int layoutIconId;
    public int layoutTitleId;
    public int layoutContentId;
    public int layoutTime;
    public int layoutIconDrawable;

    //    private static final String[] z;
    public PushBuilder(Context context) {
        super(context);
    }

    public PushBuilder(Context var1, int var2, int var4) {
        super(var1);
        this.layout = var2;
        this.layoutTitleId = var4;
    }

    public PushBuilder(Context var1, int var2, int var3, int var4, int var5, int layoutTime) {
        super(var1);
        this.layout = var2;
        this.layoutIconId = var3;
        this.layoutTitleId = var4;
        this.layoutContentId = var5;
        this.layoutTime = layoutTime;
    }

    @Override
    RemoteViews b(String s) {
        RemoteViews var2;
//        var2 = new RemoteViews(this.a.getPackageName(), R.layout.customer_notitfication_layout);
        (var2 = new RemoteViews(this.a.getPackageName(), this.layout)).setTextViewText(this.layoutTitleId, this.b);
        var2.setImageViewResource(this.layoutIconId, this.layoutIconDrawable);
        SimpleDateFormat sDateFormat = new SimpleDateFormat("HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String date = sDateFormat.format(curDate);
//        CalendarUtil.friendlyTime1(date,);
        var2.setTextViewText(this.layoutContentId, s);
        var2.setTextViewText(R.id.time, "" + date);
//        var2.setTextViewText(this.layoutTime, s);
        return var2;
    }

    @Override
    void a(String[] var1) {
        super.a(var1);
        this.layout = Integer.parseInt(var1[5]);
        this.layoutIconId = Integer.parseInt(var1[6]);
        this.layoutTitleId = Integer.parseInt(var1[7]);
        this.layoutContentId = Integer.parseInt(var1[8]);
        this.layoutIconDrawable = Integer.parseInt(var1[9]);
    }

}

