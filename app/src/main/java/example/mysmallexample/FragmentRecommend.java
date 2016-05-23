package example.mysmallexample;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentRecommend extends BaseFragment implements ViewSwitcher.ViewFactory, View.OnClickListener {

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
    private Button btn_count_down_timer;
    private Button btn_send;
    private MyTimer myTimer;


    private TimeTextView mTimeText;
    private TimeDownView timedownview;
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


        btn_count_down_timer = (Button) layout.findViewById(R.id.btn_count_down_timer);
        btn_count_down_timer.setOnClickListener(this);

        btn_send = (Button) layout.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
        mTimeText = (TimeTextView) layout.findViewById(R.id.temai_timeTextView);
        timedownview = (TimeDownView) layout.findViewById(R.id.timedownview);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
		int[] time = { date, hour, minute, second };
//        int[] time = { 0, 0, 0, 10};

        mTimeText.setTimes(time);
        if (!mTimeText.isRun()) {
            mTimeText.run();
        }

        timedownview.setTimes(time);
        if (!timedownview.isRun()) {
            timedownview.run();
        }
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

    private MyCount mc;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_count_down_timer:

                if (myTimer == null) {
                    // 第一参数是总的时间，第二个是间隔时间
                    myTimer = new MyTimer(1000 * 60 * 60, 1000);
                }

                myTimer.start();
                break;
            case R.id.btn_send:

                if (mc == null) {
                    mc = new MyCount(60000, 1000); // 第一参数是总的时间，第二个是间隔时间
                }
                mc.start();

                break;

        }
    }

    private class MyTask extends TimerTask {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            mHandler.sendMessage(message);

        }
    }

    private class MyTimer extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //执行倒计时操作
        @Override
        public void onTick(long millisUntilFinished) {
            btn_count_down_timer.setEnabled(false);
//            btn_count_down_timer.setText("" + millisUntilFinished / (1000 * 60) + "分钟" + millisUntilFinished / 1000 + "秒");
            btn_count_down_timer.setText("" + millisUntilFinished / 1000 + "秒");

        }

        //倒计时执行完成
        @Override
        public void onFinish() {

            btn_count_down_timer.setEnabled(true);
            btn_count_down_timer.setText("倒计时完成");

        }
    }

    /* 定义一个倒计时的内部类 */
    private class MyCount extends CountDownTimer {

        /**
         * @param millisInFuture    表示以毫秒为单位 倒计时的总数
         *                          <p/>
         *                          例如 millisInFuture=1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick 方法
         *                          <p/>
         *                          例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         */
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            btn_send.setEnabled(true);
            btn_send.setText("发送验证码");
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btn_send.setEnabled(false);
            btn_send.setText("(" + millisUntilFinished / 1000 + ")秒");
        }
    }

}
