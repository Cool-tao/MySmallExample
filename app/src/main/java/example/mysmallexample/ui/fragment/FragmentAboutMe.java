package example.mysmallexample.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.mysmallexample.R;

public class FragmentAboutMe extends BaseFragment implements View.OnClickListener {


    private View action_layout;
    private Activity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.my_page, null);
//        action_layout = layout.findViewById(R.id.action_layout);
//        action_layout.setOnClickListener(this);
        return layout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.action_layout:
//                Toast.makeText(activity, "action_layout", Toast.LENGTH_SHORT).show();
//                break;
        }

    }
}
