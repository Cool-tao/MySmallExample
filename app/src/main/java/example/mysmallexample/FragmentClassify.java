package example.mysmallexample;

import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentClassify extends BaseFragment {


    private int px_170dp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_classify, null);
        TextView text_classify = (TextView) layout.findViewById(R.id.text_classify);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        px_170dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 175, dm);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Toast.makeText(getActivity(), "Classify", Toast.LENGTH_SHORT).show();
            text_classify.setPadding(0, px_170dp, 0, 0);
        }
        return layout;
    }

}
