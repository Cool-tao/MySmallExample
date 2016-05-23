package example.mysmallexample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

@SuppressLint("ValidFragment")
public class FragmentRank extends BaseFragment implements View.OnClickListener {

    private EditText editText;

    private View top_layout;
    private TextView text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_rank, null);
        editText = (EditText) layout.findViewById(R.id.edit);

        top_layout=layout.findViewById(R.id.top_layout);
        top_layout.setOnClickListener(this);
        text= (TextView) layout.findViewById(R.id.text);

        return layout;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.top_layout){
//            Toast.makeText(getActivity(),"top_layout",Toast.LENGTH_SHORT).show();
            Locale locale = Locale.getDefault();
            String country = locale.getCountry();
            String language = locale.getLanguage();
            text.setText("country :" + country + "\r\n language:" + language);
            Toast.makeText(v.getContext(), country + ":" + language, Toast.LENGTH_LONG).show();

        }
    }
}
