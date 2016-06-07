package example.mysmallexample.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.mysmallexample.R;
import example.mysmallexample.ui.utils.Log;

/**
 * Created by taoshuang on 2016/6/7.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter {
    public static final String TAG = "MyRecyclerViewAdapter";

    private Context context;
    private View.OnClickListener onClickListener;
    private LayoutInflater inflater;
    private List<String> list;

    public MyRecyclerViewAdapter() {

    }

    public MyRecyclerViewAdapter(Context context, View.OnClickListener onClickListener) {
        this.context = context;
        this.onClickListener = onClickListener;
        this.inflater = LayoutInflater.from(context);
        list = new ArrayList<>();
    }

    public void appendData(List<String> data) {
        if (data == null) {
            list.clear();
            return;
        }
        int index = getItemCount();
        list.addAll(data);
        notifyItemRangeChanged(index, data.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = inflater.inflate(R.layout.home_item, parent, false);
        viewHolder = new TextViewHolder(view);
        return viewHolder;
    }

    public class TextViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public int pos;

        public TextViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.home_item_text);
            itemView.setOnClickListener(onClickListener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        String str = list.get(position);
        TextViewHolder textViewHolder = (TextViewHolder) holder;
        textViewHolder.pos = position;
        if (TextUtils.isEmpty(str)) {
            textViewHolder.textView.setText("error");
            return;
        }
        textViewHolder.textView.setText("" + str);

    }

    @Override
    public int getItemCount() {
        int count = list.size();
//        Log.e(TAG, "count=" + count);
        return count;
    }
}
