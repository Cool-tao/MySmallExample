package example.mysmallexample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class FragmentRank extends BaseFragment implements View.OnClickListener {

    private EditText editText;

    private View top_layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_rank, null);
        editText = (EditText) layout.findViewById(R.id.edit);

        top_layout=layout.findViewById(R.id.top_layout);
        top_layout.setOnClickListener(this);

        return layout;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.top_layout){
            Toast.makeText(getActivity(),"top_layout",Toast.LENGTH_SHORT).show();
        }
    }
}
