package com.cxz.skinsample.app;

import android.app.Application;

import com.cxz.skinsample.skin.SkinEngine;

/**
 * @author chenxz
 * @date 2019/3/9
 * @desc
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化换肤引擎
        SkinEngine.getInstance().init(this);
    }
}
