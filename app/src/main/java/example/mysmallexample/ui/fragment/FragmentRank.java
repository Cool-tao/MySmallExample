package example.mysmallexample.ui.fragment;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

import java.util.Locale;

import example.mysmallexample.R;
import example.mysmallexample.ui.activity.OtherActivity;

@SuppressLint("ValidFragment")
public class FragmentRank extends BaseFragment implements View.OnClickListener {

    private EditText editText;
    private View top_layout;
    private TextView text;
    private View test_is_task_root_tv;

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
    }

    private void setNotify(Context context) {

        Intent intent = new Intent();
        intent.setClass(getActivity(), OtherActivity.class);
        intent.putExtra("from", "Notify");

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentText("notifyMessage")
                .setContentTitle("notifyTitle")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1/* ID of notification */, builder.build());

    }

}
