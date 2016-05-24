package example.mysmallexample.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import example.mysmallexample.R;
import example.mysmallexample.ui.utils.Log;

/**
 * Created by taoshuang on 2016/5/24.
 */
public class OtherActivity extends BaseActivity {
    public static final String TAG = "OtherActivity";

    private TextView is_task_root_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        Intent intent = getIntent();
        String from = intent.getStringExtra("from");
        is_task_root_tv = (TextView) findViewById(R.id.is_task_root_tv);
        Log.i(TAG, "isTaskRoot" + isTaskRoot());
        is_task_root_tv.setText("from:" + from + "\risTaskRoot:" + isTaskRoot());

    }
}
