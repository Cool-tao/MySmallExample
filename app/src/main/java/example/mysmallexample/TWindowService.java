package example.mysmallexample;

import android.app.Dialog;
import android.content.Context;
import android.test.AndroidTestCase;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by zhengnan on 2016/5/12.
 * 主要用来管理窗口的一些状态、属性、view增加、删除、更新、窗口顺序、消息收集和处理
 * Android中真正展示给用户的是window和view，activity所起的作用主要是处理一些逻辑问题，比如生命周期管理及建立窗口
 * // act.getWindowManager(); 是针对于act的，getSystem方式获取的是全局的。
 */
public class TWindowService extends AndroidTestCase {
    public void testMain() {
        WindowManager wm =(WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);

        //WindowManager.LayoutParams.
    }
    //获取屏幕相关信息 ,宽，高，密度等
    public void getDisplay(WindowManager wm){
        Display dp = wm.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        dp.getMetrics(dm);
        int width = dm.widthPixels;  // 屏幕宽度（像素）
        int height = dm.heightPixels;  // 屏幕高度（像素）
        float density = dm.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）

    }

    //在当前屏幕上添加新的view ， 如悬浮。
    public void addView(WindowManager wm) {
        View aView = new Button(mContext);
        //该view遵循的属性，如：view的 位置，大小，透明度，类型等等。
        WindowManager.LayoutParams p = new WindowManager.LayoutParams();

        wm.addView(aView,p);
        wm.removeView(aView);
    }

    //改变窗口透明度。
    public void else4changeAlpha(){
        Dialog dg = new Dialog(mContext);
        Window window = dg.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.5f;
        window.setAttributes(lp);
    }


}
