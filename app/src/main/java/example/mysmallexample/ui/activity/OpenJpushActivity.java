package example.mysmallexample.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import example.mysmallexample.R;

public class OpenJpushActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_page);
//        TextView tv = new TextView(this);
//        tv.setText("用户自定义打开的Activity");
//        Intent intent = getIntent();
//        if (null != intent) {
//	        Bundle bundle = getIntent().getExtras();
//	        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
//	        String content = bundle.getString(JPushInterface.EXTRA_ALERT);
//	        tv.setText("Title : " + title + "  " + "Content : " + content);
//        }
//        addContentView(tv, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

}
