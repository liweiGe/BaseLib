package com.example.niubilityapp;

import android.app.Application;

import com.example.niubilityapp.http.HttpApi;
import com.zhouyou.http.EasyHttp;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
  // TODO: 2019-07-05  https://blog.csdn.net/zhouy478319399/article/details/78550248 该框架的rxjava用法
        EasyHttp.init(this);//默认初始化
        EasyHttp.getInstance().setBaseUrl(HttpApi.baseUrl)
                .setRetryCount(1)//网络不好自动重试1次
                // 最后的true表示是否打印内部异常，一般打开方便调试错误
                .debug(HttpApi.TAG, true)
//                .setCacheMode(CacheMode.CACHEANDREMOTE) //先使用缓存，不管是否存在，仍然请求网络，会回调两次
                .setCertificates();
    }
}
