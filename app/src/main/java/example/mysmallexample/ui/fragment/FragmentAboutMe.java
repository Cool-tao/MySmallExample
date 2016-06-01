package example.mysmallexample.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import example.mysmallexample.R;
import example.mysmallexample.ui.activity.SettingActivity;

public class FragmentAboutMe extends BaseFragment implements View.OnClickListener {


    private View header_top;
    private LinearLayout settings_layout;
    private View my_page_feedback_layout;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.my_page, null);
        initViews(layout);
        return layout;
    }

    private void initViews(View layout) {
        header_top = layout.findViewById(R.id.header_top);
        header_top.setOnClickListener(this);
        settings_layout = (LinearLayout) layout.findViewById(R.id.settings_layout);
        settings_layout.setOnClickListener(this);
        my_page_feedback_layout = layout.findViewById(R.id.my_page_feedback_layout);
        my_page_feedback_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_top:
                Toast.makeText(activity, "登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings_layout:
                Toast.makeText(activity, "设置", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setClass(activity, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.my_page_feedback_layout:
                Toast.makeText(activity, "反馈", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
