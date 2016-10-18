package example.mysmallexample.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.InputStream;

import example.mysmallexample.R;
import example.mysmallexample.ui.activity.DevicesInfoActivity;
import example.mysmallexample.ui.activity.SettingActivity;
import example.mysmallexample.ui.activity.SpecialEfficacyActivity;
import example.mysmallexample.ui.activity.TestActivity;
import example.mysmallexample.ui.utils.Log;

public class FragmentAboutMe extends BaseFragment implements View.OnClickListener {


    private View header_top;
    private LinearLayout settings_layout;
    private View my_page_feedback_layout;
    private View my_page_special_efficacy_layout;
    private View my_page_activity_layout;
    private View my_friend_update_layout;

    private ImageView imageView;

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
        getAssetsImage();
        return layout;
    }

    private void initViews(View layout) {
        header_top = layout.findViewById(R.id.header_top);
        header_top.setOnClickListener(this);
        settings_layout = (LinearLayout) layout.findViewById(R.id.settings_layout);
        settings_layout.setOnClickListener(this);
        my_page_feedback_layout = layout.findViewById(R.id.my_page_feedback_layout);
        my_page_feedback_layout.setOnClickListener(this);
        my_page_special_efficacy_layout = layout.findViewById(R.id.my_page_special_efficacy_layout);
        my_page_special_efficacy_layout.setOnClickListener(this);
        my_page_activity_layout = layout.findViewById(R.id.my_page_activity_layout);
        my_page_activity_layout.setOnClickListener(this);
        my_friend_update_layout = layout.findViewById(R.id.my_friend_update_layout);
        my_friend_update_layout.setOnClickListener(this);
        imageView = (ImageView) layout.findViewById(R.id.user_avatar_iv);
    }

    private void getAssetsImage() {

        try {

            AssetManager assetManager = getContext().getAssets();
            InputStream is = assetManager.open("sample.txt");
            int size = is.available();
            Log.i("FragmentAboutMe", "LogUtils FragmentAboutMe：size=" + size);
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convert the buffer into a string.
            String text = new String(buffer, "UTF-8");
            Log.i("FragmentAboutMe", "LogUtils FragmentAboutMe：" + text);

        } catch (Exception e) {
            e.printStackTrace();
        }


        //测试一:获取asset下图片资源
        try {
            AssetManager assetManager = getContext().getAssets();
            InputStream is = assetManager.open("setting.png");
            //以下注释掉的代码不靠谱.若采用,会有异常
            //InputStream is = assetManager.open("file:///android_asset/Fresh_01.jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            if (bitmap != null) {
                Log.i("FragmentAboutMe", "LogUtils FragmentAboutMe：" + " width=" + bitmap.getWidth() + " ,height=" + bitmap.getHeight());
            } else {
                System.out.println("bitmap == null");
            }
        } catch (Exception e) {
            System.out.println("异常信息:" + e.toString());
        }

        System.out.println("======================");

        //测试二:获取asset下某个文件夹中的图片资源
        try {
            AssetManager assetManager = getContext().getAssets();
            InputStream is = assetManager.open("image/day.png");
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            if (bitmap != null) {
                Log.i("FragmentAboutMe", "LogUtils FragmentAboutMe：" + " width=" + bitmap.getWidth() + " ,height=" + bitmap.getHeight());
            } else {
                System.out.println("bitmap == null");
            }
        } catch (Exception e) {
            System.out.println("异常信息:" + e.toString());
        }

        System.out.println("======================");

        // 测试三:遍历asset下某个文件夹中的所有图片资源
        try {
            InputStream is;
            Bitmap bitmap;
            String dirPath = "image";
            String photoName;
            AssetManager assetManager = getContext().getAssets();
            //使用list()方法获取某文件夹下所有文件的名字
            String[] photos = assetManager.list(dirPath);
            for (int i = 0; i < photos.length; i++) {
                photoName = photos[i];
                //利用dirPath+"/"+photoName组拼某文件完整路径
                is = assetManager.open(dirPath + "/" + photoName);
                bitmap = BitmapFactory.decodeStream(is);
                Log.i("FragmentAboutMe", "LogUtils FragmentAboutMe：i=" + i + " ,width=" + bitmap.getWidth() + " ,height=" + bitmap.getHeight());
            }
        } catch (Exception e) {
            System.out.println("异常信息:" + e.toString());
        }

    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.header_top:
                Toast.makeText(activity, "登 录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings_layout:
                Toast.makeText(activity, "设 置", Toast.LENGTH_SHORT).show();

                intent.setClass(activity, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.my_page_special_efficacy_layout:
                Toast.makeText(activity, "特 效", Toast.LENGTH_SHORT).show();
                intent.setClass(activity, SpecialEfficacyActivity.class);
                startActivity(intent);
                break;
            case R.id.my_page_activity_layout:
                Toast.makeText(activity, "活 动", Toast.LENGTH_SHORT).show();
                intent.setClass(activity, DevicesInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.my_friend_update_layout:
                Toast.makeText(activity, "好 友 动 态", Toast.LENGTH_SHORT).show();
                intent.setClass(activity, TestActivity.class);
                startActivity(intent);
                break;
            case R.id.my_page_feedback_layout:
                Toast.makeText(activity, "反 馈", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
