package example.mysmallexample.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import example.mysmallexample.R;
import example.mysmallexample.ui.utils.Log;


/**
 * 用户反馈，带标签
 */
public class CustomDialogFragment extends DialogFragment implements View.OnClickListener {

    public static final String TAG = CustomDialogFragment.class.getSimpleName();
    private static boolean sending = false;
    private static boolean success = false;

    public CustomDialogFragment() {
        success = false;
    }

    public static boolean shown = false;

    @Override
    public void show(FragmentManager manager, String tag) {
        sending = false;
        shown = true;
        super.show(manager, tag);
    }

    @Override
    public void onResume() {
        super.onResume();
        shown = true;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        shown = false;
        super.onDismiss(dialog);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(false);
        createContentView(dialog);
        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void configDialogSize(Dialog dialog) {
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);

        DisplayMetrics screenMetrics = new DisplayMetrics();
        dialogWindow.getWindowManager().getDefaultDisplay()
                .getMetrics(screenMetrics);
        // 竖屏状态下以宽度为基准
        lp.width = (int) (screenMetrics.widthPixels * 6f / 7f);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
    }

    View dialogView;
    TextView title;
    TextView desc;

    ImageView gif_subscription_view;
    private void createContentView(Dialog dialog) {
        try {
//            dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment, (ViewGroup) dialog.getWindow().getDecorView(), false);
            dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.gif_dialog_fragment, (ViewGroup) dialog.getWindow().getDecorView(), false);
            configDialogSize(dialog);
            // 设置内容
            //title = (TextView) dialogView.findViewById(R.id.title);
            //desc = (TextView) dialogView.findViewById(R.id.desc);
            //dialogView.findViewById(R.id.btnOK).setOnClickListener(this);
            //dialogView.findViewById(R.id.btnCancel).setOnClickListener(this);

            gif_subscription_view= (ImageView) dialogView.findViewById(R.id.gif_subscription_view);
//            gif_subscription_view.setBackgroundResource(R.drawable.subscription_lead);
            gif_subscription_view.setImageResource(R.drawable.subscription_lead);
            dialogView.findViewById(R.id.textContent_view).setOnClickListener(this);

            dialog.setContentView(dialogView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
//                case R.id.btnOK:
//                    Log.i("DialogFragment", "btnOK");
//                    dismiss();
//                    break;
//                case R.id.btnCancel:
//                    Log.i("DialogFragment", "btnCancel");
//                    dismiss();
//                    break;
                case R.id.textContent_view:
                    Log.i("DialogFragment", "gif");
                    dismiss();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
