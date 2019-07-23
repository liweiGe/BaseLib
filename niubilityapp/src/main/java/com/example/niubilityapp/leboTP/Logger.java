package com.example.niubilityapp.leboTP;

import android.util.Log;

/**
 * Created by Zippo on 2018/5/26.
 * Date: 2018/5/26
 * Time: 11:37:49
 */
public class Logger {

    private static final String TAG = "hpplay-demo";
    private static final String TAG_TEST = "hpplay-test";

    public static void v(String tag, String msg) {
        String message = formatMessage(tag, msg);
        Log.v(TAG, message);
    }

    public static void d(String tag, String msg) {
        String message = formatMessage(tag, msg);
        Log.d(TAG, message);
    }

    public static void i(String tag, String msg) {
        String message = formatMessage(tag, msg);
        Log.i(TAG, message);
    }


    public static void w(String tag, String msg) {
        String message = formatMessage(tag, msg);
        Log.w(TAG, message);
    }

    public static void w(String tag, String msg, Throwable tr) {
        String message = formatMessage(tag, msg);
        Log.w(TAG, message, tr);
    }

    public static void w(String tag, Throwable tr) {
        String message = formatMessage(tag, null);
        Log.w(TAG, message, tr);
    }

    public static void e(String tag, String msg) {
        String message = formatMessage(tag, msg);
        Log.e(TAG, message);
    }

    public static void e(String tag, String msg, Throwable tr) {
        String message = formatMessage(tag, msg);
        Log.e(TAG, message, tr);
    }

    public static void test(String tag, String msg) {
        String message = formatMessage(tag, msg);
        Log.i(TAG_TEST, message);
    }

    private static String formatMessage(String tag, String msg) {
        if (tag == null) {
            tag = "";
        }
        if (msg == null) {
            msg = "";
        }
        String ret = tag + ":" + msg;
        ret = "[" + Thread.currentThread().getName() + "]:" + ret;
        return ret;
    }
}
