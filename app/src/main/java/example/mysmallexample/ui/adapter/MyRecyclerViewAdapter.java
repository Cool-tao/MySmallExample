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

    final static int Type_Loading = 0;
    final static int Type_Text = 1;

    public boolean isShowLoading = false;

    private Context context;
    private View.OnClickListener onClickListener;
    private LayoutInflater inflater;
    private List<String> list;

    public interface CallBack {
        void loadMore();
    }

    public CallBack callBack;

    public MyRecyclerViewAdapter() {

    }

    @Override
    public int getItemViewType(int position) {
        if (isShowLoading && position == getItemCount() - 1) {
            return Type_Loading;
        }
        return Type_Text;
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
        if (isShowLoading) {
            index = index - 1;
        }
        list.addAll(data);
        notifyItemRangeChanged(index, data.size());
//        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        int count = list.size();
        if (isShowLoading) {
            ++count;
        }
        return count;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view;
        switch (viewType) {
            case Type_Loading:
                view = inflater.inflate(
                        R.layout.list_load_more_layout, parent, false);
                viewHolder = new LoadingHolder(view);
                break;
            case Type_Text:
                view = inflater.inflate(R.layout.home_item, parent, false);
                viewHolder = new TextViewHolder(view);
                break;
        }
        return viewHolder;
    }

    public class LoadingHolder extends RecyclerView.ViewHolder {
        public LoadingHolder(View itemView) {
            super(itemView);
        }

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

        int type = getItemViewType(position);
        switch (type) {
            case Type_Loading:
                Log.i("MyRecyclerViewAdapter", "isShowLoading" + isShowLoading);
                if (isShowLoading && callBack != null) {
                    Log.i("MyRecyclerViewAdapter", "loadMore");
                    callBack.loadMore();
                }
                break;

            case Type_Text:
                TextViewHolder textViewHolder = (TextViewHolder) holder;
                String str = list.get(position);
                holder.itemView.setTag(str);
                textViewHolder.pos = position;
                if (TextUtils.isEmpty(str)) {
                    textViewHolder.textView.setText("error");
                    return;
                }
                textViewHolder.textView.setText("" + str);
                break;
        }


    }


}
