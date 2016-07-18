package example.mysmallexample.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import example.mysmallexample.R;
import example.mysmallexample.ui.App;
import example.mysmallexample.ui.adapter.MyRecyclerViewAdapter;
import example.mysmallexample.ui.utils.Log;

public class FragmentRecommend extends BaseFragment implements View.OnClickListener {


    private View main_title_layout;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    private List<String> data;
    private MyRecyclerViewAdapter.CallBack callBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View layout = inflater.inflate(R.layout.main_layout, null);
        initViews(layout);
        data = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            main_title_layout.setPadding(0, App.px_20dp, 0, 0);
        }
        initData();
        if (data == null || data.size() == 0) {
            Log.i("FragmentHome1", "data=null");
        } else {
            Log.i("FragmentHome1", "data.size()=" + data.size());
        }
        layoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(layoutManager);
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(activity, this);
        mRecyclerView.setAdapter(myRecyclerViewAdapter);

        myRecyclerViewAdapter.appendData(data);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                myRecyclerViewAdapter.isShowLoading = true;
            }
        });

        callBack = new MyRecyclerViewAdapter.CallBack() {
            @Override
            public void loadMore() {
//                initData();
                Log.i("FragmentHome1", "loadMore");
//                myRecyclerViewAdapter.isShowLoading = false;
//                myRecyclerViewAdapter.appendData(data);
            }
        };
        myRecyclerViewAdapter.callBack = callBack;
        return layout;
    }

    private void initViews(View layout) {
        main_title_layout = layout.findViewById(R.id.main_title_layout);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerview);
    }

    private void initData() {
        for (int i = 0; i < 25; i++) {
            data.add("测试数据：" + (i + 1));
        }
    }

    @Override
    public void onClick(View v) {
        String str = (String) v.getTag();
        Toast.makeText(activity, "显示：" + str, Toast.LENGTH_SHORT).show();
    }
}
