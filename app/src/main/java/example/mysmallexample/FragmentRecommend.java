package example.mysmallexample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.Timer;
import java.util.TimerTask;

public class FragmentRecommend extends BaseFragment implements ViewSwitcher.ViewFactory {

    String[] resources = {"标题", "身是菩提树", "心如明镜台", "时时勤拂拭", "勿使惹尘埃"};
    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    id = next(); // 更新Id值
                    updateText(); // 更新TextSwitcherd显示内容;
                    break;
            }
        }
    };
    int id = 0; // resources 数组的Id;

    private TextSwitcher textSwitcher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        View layout = inflater.inflate(R.layout.fragment_recomment, null);
        textSwitcher = (TextSwitcher) layout.findViewById(R.id.textSwitcher);
        textSwitcher.setFactory(this);
        textSwitcher.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.notice_scroll_top_to_down_current_text));
        textSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.notice_scroll_top_to_down_next_text));
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTask(), 1, 3000);// 每3秒更新
        return layout;
    }

    private int next() {

        int flag = id + 1;
        if (flag > resources.length - 1) {
            flag = flag - resources.length;
        }
        return flag;
    }

    private void updateText() {
        textSwitcher.setText(resources[id]);
    }

    @Override
    public View makeView() {
        // TODO Auto-generated method stub
        TextView tv = new TextView(getActivity());
        tv.setText(resources[id]);
        return tv;
    }

    private class MyTask extends TimerTask {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            mHandler.sendMessage(message);

        }
    }
}
