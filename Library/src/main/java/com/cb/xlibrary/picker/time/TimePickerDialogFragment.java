package com.cb.xlibrary.picker.time;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.cb.xlibrary.R;


/**
 * 时间选择器，弹出框
 * Created by ycuwq on 2018/1/6.
 */
public class TimePickerDialogFragment extends DialogFragment {

    protected HourAndMinutePicker hourAndMinutePicker;
    private int mSelectedHour = -1, mSelectedMinute = -1;
    private OnTimeChooseListener mOnDateChooseListener;
    private boolean mIsShowAnimation = true;
    protected Button mCancelButton, mDecideButton;
    private TextView titleTv;

    public void setOnDateChooseListener(OnTimeChooseListener onDateChooseListener) {
        mOnDateChooseListener = onDateChooseListener;
    }

    public void showAnimation(boolean show) {
        mIsShowAnimation = show;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_chose_time_cblibrary, container);

        hourAndMinutePicker = view.findViewById(R.id.time_picker_dialog);
        titleTv = view.findViewById(R.id.tv_title);
        mDecideButton = view.findViewById(R.id.btn_dialog_date_decide);

        hourAndMinutePicker.post(new Runnable() {
            @Override
            public void run() {
                setSelectedDate();
            }
        });

        mDecideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDateChooseListener != null) {
                    mOnDateChooseListener.onDateChoose(hourAndMinutePicker.getHour(), hourAndMinutePicker.getMinute());
                }
                dismiss();
            }
        });
        return view;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.DatePickerBottomDialog_cblibrary);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定

        dialog.setContentView(R.layout.dialog_chose_time_cblibrary);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消

        Window window = dialog.getWindow();
        if (window != null) {
            if (mIsShowAnimation) {
                window.getAttributes().windowAnimations = R.style.DatePickerDialogAnim_cblibrary;
            }
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.BOTTOM; // 紧贴底部
            lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
            lp.dimAmount = 0.35f;
            window.setAttributes(lp);
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }

        return dialog;
    }

    public void setTitle(String title) {
        titleTv.setText(title);
    }

    public void setSelectedDate(int hour, int minute) {
        mSelectedHour = hour;
        mSelectedMinute = minute;
//        setSelectedDate();
    }

    private void setSelectedDate() {
        if (hourAndMinutePicker != null) {
            hourAndMinutePicker.setTime(mSelectedHour, mSelectedMinute);
        }
    }

    public interface OnTimeChooseListener {
        void onDateChoose(int hour, int minute);
    }


}
