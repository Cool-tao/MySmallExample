package example.mysmallexample.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import example.mysmallexample.R;
import example.mysmallexample.ui.fragment.FragmentTest;

/**
 * Created by taoshuang on 2016/6/8.
 */
public class TestActivity extends BaseActivity {

    private View root_layout;
    private FragmentTest fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        root_layout=findViewById(R.id.root_layout);

        Bundle args = new Bundle();
        fragment = new FragmentTest();
        args.putString("id", "id");
        fragment.setArguments(args);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.root_layout, fragment);
        ft.commit();

    }
}

