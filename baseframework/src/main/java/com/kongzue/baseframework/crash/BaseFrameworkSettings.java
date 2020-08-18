package com.kongzue.baseframework.crash;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import java.io.File;
import static com.kongzue.baseframework.crash.DebugLogG.*;

/**
 * Author: @Kongzue
 * Github: https://github.com/kongzue/
 * Homepage: http://kongzue.com/
 * Mail: myzcxhh@live.cn
 * CreateTime: 2018/9/30 03:22
 * <p>
 * 崩溃监控部分代码借鉴 (@var_rain)https://www.jianshu.com/p/2ebf802f7e78
 */
public class BaseFrameworkSettings {

    private static OnBugReportListener onBugReportListener;


    private static boolean running = true;

    //设置开启崩溃监听
    public static void turnOnReadErrorInfoPermissions(Context context, OnBugReportListener listener) {
        onBugReportListener = listener;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    try {
                        Looper.loop();
                    } catch (Throwable e) {
                        File catchFile = catchException(context, e);
                        if (onBugReportListener != null) {
                            if (onBugReportListener.onCrash(new Exception(e), catchFile)) {
                                exitApp();
                            }
                        }
                    }
                }
            }
        });

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                new Thread() {
                    @Override
                    public void run() {
                        File catchFile = catchException(context, e);
                        if (onBugReportListener != null) {
                            Looper.prepare();
                            if (onBugReportListener.onCrash(new Exception(e), catchFile)) {
                                exitApp();
                            }
                            Looper.loop();
                        }
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ae) {
                            ae.printStackTrace();
                        }
                        super.run();
                    }
                }.start();
            }
        });
    }

    public static void exitApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
