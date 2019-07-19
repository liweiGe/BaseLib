package com.example.niubilityapp;

import android.app.Application;

import com.billy.android.swipe.SmartSwipeBack;
import com.example.niubilityapp.http.HttpApi;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.interceptor.CacheInterceptorOffline;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
  // TODO: 2019-07-05  https://blog.csdn.net/zhouy478319399/article/details/78550248 该框架的rxjava用法
        EasyHttp.init(this);//默认初始化
        EasyHttp.getInstance().setBaseUrl(HttpApi.baseUrl)
                .setRetryCount(1)//网络不好自动重试1次
                // 最后的true表示是否打印内部异常，一般打开方便调试错误
                .debug(HttpApi.TAG, true)
                .addNetworkInterceptor(new CacheInterceptorOffline(this))//设置网络拦截器
                .addNetworkInterceptor(new StethoInterceptor())
//                .setCacheMode(CacheMode.CACHEANDREMOTE) //先使用缓存，不管是否存在，仍然请求网络，会回调两次
                .setCertificates();
        SmartSwipeBack.activityDoorBack(this, null);    //侧滑百叶窗样式关闭activity
    }
}
