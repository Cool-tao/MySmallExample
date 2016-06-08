package example.mysmallexample.ui.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import example.mysmallexample.R;
/**
 * Created by taoshuang on 2016/6/8.
 */
public class TestActivity extends BaseActivity {

    private LinearLayout layout;
    private ListView listView;
    private PopupWindow popupWindow;
    private String title[] = { "1", "2", "3", "4", "5" };
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_test);
        imageView=(ImageView) findViewById(R.id.imageview_above_more);
        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopupWindow(imageView);
            }
        });
    }
    public void showPopupWindow(View parent) {
        //加载布局
        layout = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.dialog, null);
        //找到布局的控件
        listView = (ListView) layout.findViewById(R.id.lv_dialog);
        //设置适配器
        listView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.text, R.id.tv_text, title));
        // 实例化popupWindow
        popupWindow = new PopupWindow(layout, 300,500);
        //控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        //设置popupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null,""));
        WindowManager manager=(WindowManager) getSystemService(Context.WINDOW_SERVICE);
        @SuppressWarnings("deprecation")
        //获取xoff
                int xpos=manager.getDefaultDisplay().getWidth()/2-popupWindow.getWidth()/2;
        //xoff,yoff基于anchor的左下角进行偏移。
        popupWindow.showAsDropDown(parent,xpos, 0);
        //监听
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                //关闭popupWindow
                popupWindow.dismiss();
                popupWindow = null;
            }
        });
    }
}

