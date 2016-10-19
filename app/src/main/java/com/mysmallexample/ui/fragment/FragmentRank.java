package com.mysmallexample.ui.fragment;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cool.makejarlib.utils.string.DateUtils;
import com.mysmallexample.model.ClassItem;
import com.mysmallexample.model.NetworkEvent;
import com.mysmallexample.ui.activity.DraftTestActivity;
import com.mysmallexample.ui.activity.ListViewItemActivity;
import com.mysmallexample.ui.activity.MyToastActivity;
import com.mysmallexample.ui.activity.OtherActivity;
import com.mysmallexample.ui.utils.Const;
import com.mysmallexample.ui.utils.GetUri;
import com.mysmallexample.ui.utils.Log;
import com.mysmallexample.ui.utils.SPUtil;
import com.mysmallexample.ui.utils.XmlParsePerson;

import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;
import example.mysmallexample.R;

@SuppressLint("ValidFragment")
public class FragmentRank extends BaseFragment implements View.OnClickListener {

    private EditText editText;
    private View top_layout;
    private TextView text;
    private View test_is_task_root_tv;
    private TextView test_jar_tv;
    private TextView test_get_market;
    private XmlParsePerson mPullPersonPaseService;
    private XmlResourceParser xmlParser;
    private View test_draft;
    private View test_taost;
    private View test_listview;
    private TextView test_net;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisible != isVisibleToUser) {
            if (isVisibleToUser) {
                checkMode();
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View layout = inflater.inflate(R.layout.fragment_rank, null);
        editText = (EditText) layout.findViewById(R.id.edit);
        top_layout = layout.findViewById(R.id.top_layout);
        top_layout.setOnClickListener(this);
        text = (TextView) layout.findViewById(R.id.text);
        text.setOnClickListener(this);
        test_is_task_root_tv = layout.findViewById(R.id.test_is_task_root_tv);
        test_is_task_root_tv.setOnClickListener(this);
        test_jar_tv = (TextView) layout.findViewById(R.id.test_jar_tv);
        test_jar_tv.setOnClickListener(this);
        test_get_market = (TextView) layout.findViewById(R.id.test_get_market);
        test_get_market.setOnClickListener(this);
        test_draft = layout.findViewById(R.id.test_draft);
        test_draft.setOnClickListener(this);
        test_taost = layout.findViewById(R.id.test_taost);
        test_taost.setOnClickListener(this);
        test_listview = layout.findViewById(R.id.test_listview);
        test_listview.setOnClickListener(this);
        test_net = (TextView) layout.findViewById(R.id.test_net);
        test_net.setOnClickListener(this);
        mPullPersonPaseService = new XmlParsePerson();
        // 获取本地xml
        xmlParser = this.getResources().getXml(R.xml.person);
        checkMode();
        return layout;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.text) {
            Locale locale = Locale.getDefault();
            String country = locale.getCountry();
            String language = locale.getLanguage();
            text.setText("country :" + country + "\r:\r language:" + language);
            Toast.makeText(v.getContext(), country + ":" + language, Toast.LENGTH_LONG).show();
        }
        if (v.getId() == R.id.test_is_task_root_tv) {

            Intent intent = new Intent();
            intent.setClass(getActivity(), OtherActivity.class);
            intent.putExtra("from", "Rank");
            startActivity(intent);

            setNotify(getActivity());
        }
        if (v.getId() == R.id.test_jar_tv) {
            long currentTime = DateUtils.getCurrentTime();
            test_jar_tv.setText("currentTime" + currentTime);
            try {
                List<ClassItem> persons = mPullPersonPaseService
                        .getPersons(xmlParser);

                for (ClassItem person : persons) {
                    ClassItem item = new ClassItem(person.getClassId(),
                            person.getClassName(), person.getPartId(),
                            person.getPartName(), person.getClassIcon());
                    Log.i("FragmentRank", "ClassItem:" + item.toString());

                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        if (v.getId() == R.id.test_get_market) {
            GetUri gu = new GetUri();
            Intent i = gu.getIntent(getActivity());
            boolean b = gu.judge(getActivity(), i);
            if (b == false) {
                startActivity(i);
            }
        }
        if (v.getId() == R.id.test_draft) {
            Intent intent = new Intent(getContext(), DraftTestActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.test_taost) {
            Intent intent = new Intent(getContext(), MyToastActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.test_listview) {
            Intent intent = new Intent(getContext(), ListViewItemActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.test_net) {
            Const.toggleMode();
            checkMode();
            if (Const.isSaveMode()) {
                Toast.makeText(getContext(), "" + getString(R.string.switch_normal), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "" + getString(R.string.switch_save_flow), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void checkMode() {
        if (Const.isSaveMode()) {
            test_net.setText(R.string.page_best_mode);
            Toast.makeText(getContext(), "" + getString(R.string.page_best_mode), Toast.LENGTH_SHORT).show();
        } else {
            test_net.setText(R.string.page_save_mode);
            Toast.makeText(getContext(), "" + getString(R.string.page_save_mode), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(NetworkEvent event) {
        if (isAdded()) {
            NetworkInfo networkInfo = SPUtil.getNetworkInfo(getActivity());
            if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                Const.setBestMode();
                checkMode();
            }
        }
    }

    private void setNotify(Context context) {

        Intent intent = new Intent();
        intent.setClass(getActivity(), OtherActivity.class);
        intent.putExtra("from", "Notify");
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        /**
         * 第四个参数  flags
         * FLAG_CANCEL_CURRENT：如果构建的PendingIntent已经存在，则取消前一个，重新构建一个。
         * FLAG_NO_CREATE：如果前一个PendingIntent已经不存在了，将不再构建它。
         * FLAG_ONE_SHOT：表明这里构建的PendingIntent只能使用一次。
         * FLAG_UPDATE_CURRENT：如果构建的PendingIntent已经存在，则替换它，常用。
         */
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        // RingtoneManager
        // 1.getRingtone()    //获取铃声
        // 2.getDefaultUri()    //获取某一铃声类型的默认铃声
        // 3.setActualDefaultRingtoneUri()  //为某一铃声类型设置默认铃声
        // 4.getActualDefaultRingtoneUri(); //获取默认铃声
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentText("notifyMessage")
                .setContentTitle("notifyTitle")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSound(defaultSoundUri)//设定一个铃声，用于在通知的时候响应。传递一个Uri的参数，格式为“file:///mnt/sdcard/Xxx.mp3”。
                //<!-- 振动器权限 -->
                //<uses-permission android:name="android.permission.VIBRATE"/>
                //.setVibrate(long[] pattern)//设定震动的模式，以一个long数组保存毫秒级间隔的震动。

                //<!-- 闪光灯权限 -->
                //<uses-permission android:name="android.permission.FLASHLIGHT"/>
                //.setLights(int argb, int onMs, int offMs)//设定前置LED灯的闪烁速率，持续毫秒数，停顿毫秒数。


                //.setDefaults(Notification.DEFAULT_ALL)
                // DEFAULT_ALL：铃声、闪光、震动均系统默认。
                // DEFAULT_SOUND：系统默认铃声。
                // DEFAULT_VIBRATE：系统默认震动。
                // DEFAULT_LIGHTS：系统默认闪光。
                ;

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1/* ID of notification */, builder.build());

    }

}
