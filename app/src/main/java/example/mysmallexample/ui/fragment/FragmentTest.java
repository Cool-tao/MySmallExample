package example.mysmallexample.ui.fragment;

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

import example.mysmallexample.R;
import example.mysmallexample.customview.TimeDownView;
import example.mysmallexample.customview.TimeTextView;
import example.mysmallexample.ui.dialog.CustomDialogFragment;
import example.mysmallexample.ui.utils.Log;

public class FragmentTest extends BaseFragment implements ViewSwitcher.ViewFactory, View.OnClickListener {

    private TextSwitcher textSwitcher;
    String[] resources = {"标题轮播  1", "标题轮播  2", "标题轮播  3", "标题轮播  4", "标题轮播  5"};
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
    /**
     * 倒计时
     */
    private Button btn_count_down_timer;
    private MyTimer myTimer;
    private Button btn_send;
    /**
     * 自定义倒计时view
     */
    private TimeTextView mTimeText;
    private TimeDownView timedownview;
    private String argsId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        //获取当前时间
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
//		int[] time = { date, hour, minute, second };
        int[] time = {0, 1, 35, 57};
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        if (null == args) {
            return;
        }
        argsId = args.getString("id");
        Log.i("FragmentTest", "argsId" + argsId);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_count_down_timer:

                if (myTimer == null) {
                    // 第一参数是总的时间，第二个是间隔时间
                    myTimer = new MyTimer(1000 * 10, 1000);
                }
                myTimer.start();
                break;
            case R.id.btn_send:

                CustomDialogFragment fragment = new CustomDialogFragment();
                fragment.show(getFragmentManager(), CustomDialogFragment.TAG);
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

}
