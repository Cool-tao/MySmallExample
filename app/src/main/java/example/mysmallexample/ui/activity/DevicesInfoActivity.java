package example.mysmallexample.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import example.mysmallexample.R;

/**
 * Created by taoshuang on 2016/6/8.
 */
public class DevicesInfoActivity extends BaseActivity implements View.OnClickListener {

    Button copy;
    String deviceInfo;
    TextView info;
    Button share;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        init();
    }

    private void copy() {
        try {
            //clipboard
            ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(null, this.deviceInfo));
            Toast.makeText(this, "copy success.", Toast.LENGTH_SHORT).show();
            return;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    private void init() {
        this.info = ((TextView) findViewById(R.id.info));
        this.copy = ((Button) findViewById(R.id.copy));
        this.share = ((Button) findViewById(R.id.share));
        this.copy.setOnClickListener(this);
        this.share.setOnClickListener(this);
        this.deviceInfo = getDeviceInfo();
        if (TextUtils.isEmpty(this.deviceInfo)) {
            this.deviceInfo = "Ops! \r\n Please contact me! ";
        }
        this.info.setText(this.deviceInfo);
    }

    private void share() {
        try {
            Intent localIntent = new Intent("android.intent.action.SEND");
            localIntent.setType("text/plain");
            localIntent.putExtra("android.intent.extra.TEXT", this.deviceInfo);
            startActivity(localIntent);
            return;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public String getDeviceInfo() {
        try {
            Context localContext = getApplicationContext();
            JSONObject localJSONObject = new JSONObject();
            Object localObject = ((TelephonyManager) localContext.getSystemService(TELEPHONY_SERVICE)).getDeviceId();
            String str1 = ((WifiManager) localContext.getSystemService(WIFI_SERVICE)).getConnectionInfo().getMacAddress();
            localJSONObject.put("mac", str1);
            if (TextUtils.isEmpty((CharSequence) localObject)) {
                localObject = str1;
            }
            if (TextUtils.isEmpty((CharSequence) localObject)) {
                localObject = Settings.Secure.getString(localContext.getContentResolver(), "android_id");
            }
            localJSONObject.put("device_id", localObject);
            String str2 = localJSONObject.toString();
            return str2;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return null;
    }

    public void onClick(View paramView) {
        switch (paramView.getId()) {
            case R.id.copy:
                copy();
                return;
            case R.id.share:
                share();
                break;
        }
    }


    protected void onStop() {
        super.onStop();
        finish();
    }
}
