package com.example.commom_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.concurrent.TimeUnit;

public class TimerView extends AppCompatTextView implements  View.OnClickListener {


    private CountDownTimer mCountDownTimer;
    private OnCountDownListener mOnCountDownListener;

    private String mNormalText;
    private String mCountDownText;

    //倒计时期间是否允许点击
    private boolean mClickable = true;
    //是否把时间格式化成时分秒
    private boolean mShowFormatTime = true;

    public TimerView(Context context) {
        this(context, null);
    }

    public TimerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void onDestroy() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onDestroy();
    }

    /**
     * 非倒计时状态文本
     *
     * @param normalText 文本
     */
    public TimerView setNormalText(String normalText) {
        mNormalText = normalText;
        setText(normalText);
        return this;
    }

    /**
     * 设置倒计时文本内容
     *
     * @param front  倒计时文本前部分
     * @param latter 倒计时文本后部分
     */
    public TimerView setCountDownText(String front, String latter) {
        mCountDownText = front + "%1$s" + latter;
        return this;
    }



    /**
     * 默认按秒倒计时
     *
     * @param second 多少秒
     */
    public void startCountDown(long second) {
        startCountDown(second, TimeUnit.SECONDS);
    }

    public void startCountDown(long time, final TimeUnit timeUnit) {
        count(time,  timeUnit);
    }

    /**
     * 计时方案
     *
     * @param time        计时时长
     * @param timeUnit    时间单位

     */
    private void count(final long time, final TimeUnit timeUnit) {
        this.setOnClickListener(this);
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        setEnabled(mClickable);
        final long millisInFuture = timeUnit.toMillis(time) ;
        long interval = TimeUnit.MILLISECONDS.convert(1, timeUnit);


        if (TextUtils.isEmpty(mCountDownText)) {
            mCountDownText = getText().toString();
        }
        mCountDownTimer = new CountDownTimer(millisInFuture, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                long l = timeUnit.convert(millisUntilFinished, TimeUnit.MILLISECONDS);
                String showTime;
                if (mShowFormatTime) {
                    showTime = generateTime(millisUntilFinished);
                } else {
                    showTime = String.valueOf(l);
                }
                setText(String.format(mCountDownText, showTime));
            }

            @Override
            public void onFinish() {
                setEnabled(true);
                mCountDownTimer = null;
                setText(mNormalText);
                if (mOnCountDownListener != null) {
                    mOnCountDownListener.onFinish();
                }
            }
        };
        mCountDownTimer.start();
    }

    public TimerView setOnCountDownListener(OnCountDownListener listener) {
        mOnCountDownListener = listener;
        return this;
    }


    public interface OnCountDownListener {
        void onClick();
        void onFinish();
    }


    @Override
    public void onClick(View v) {
        if (mCountDownTimer != null && !mClickable) return;
        if (mOnCountDownListener != null) {
            mOnCountDownListener.onClick();
        }
    }


    /**
     * 将毫秒转时分秒
     */
    @SuppressLint("DefaultLocale")
    public static String generateTime(long time) {
        String format;
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        if (hours > 0) {
            format = String.format("%02d时%02d分%02d秒", hours, minutes, seconds);
        } else if (minutes > 0) {
            format = String.format("%02d分%02d秒", minutes, seconds);
        } else {
            format = String.format("%2d", seconds);
        }
        return format;
    }
}
