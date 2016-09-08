package example.mysmallexample.ui.fragment;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.media.RingtoneManager;
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

import java.util.List;
import java.util.Locale;

import example.mysmallexample.R;
import example.mysmallexample.model.ClassItem;
import example.mysmallexample.ui.activity.DraftTestActivity;
import example.mysmallexample.ui.activity.MyToastActivity;
import example.mysmallexample.ui.activity.OtherActivity;
import example.mysmallexample.ui.utils.GetUri;
import example.mysmallexample.ui.utils.Log;
import example.mysmallexample.ui.utils.XmlParsePerson;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        mPullPersonPaseService = new XmlParsePerson();
        // 获取本地xml
        xmlParser = this.getResources().getXml(R.xml.person);

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
