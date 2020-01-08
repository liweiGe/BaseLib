package com.example.niubilityapp;

import android.util.Log;

import com.example.niubilityapp.http.HttpApi;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.kongzue.baseframework.BaseActivity;
import com.kongzue.baseframework.BaseApp;
import com.kongzue.baseframework.BaseFrameworkSettings;
import com.kongzue.baseframework.util.AppManager;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.interceptor.CacheInterceptorOffline;

public class MyApp extends BaseApp<MyApp> {
    @Override
    public void init() {
        Stetho.initializeWithDefaults(this);
        // TODO: 2019-07-05  https://blog.csdn.net/zhouy478319399/article/details/78550248 该框架的rxjava用法
        EasyHttp.init(this);//默认初始化
        EasyHttp.getInstance().setBaseUrl(HttpApi.baseUrl)
                .setRetryCount(1)//网络不好自动重试1次
                // 最后的true表示是否打印内部异常，一般打开方便调试错误
                .debug(HttpApi.TAG, true)
//              .setCacheMode(CacheMode.FIRSTREMOTE)

                .addNetworkInterceptor(new CacheInterceptorOffline(this))//设置网络拦截器
                .addNetworkInterceptor(new StethoInterceptor())
//                .setCacheMode(CacheMode.CACHEANDREMOTE) //先使用缓存，不管是否存在，仍然请求网络，会回调两次
                .setCertificates();
//        SmartSwipeBack.activityDoorBack(this, null);    //侧滑百叶窗样式关闭activity
//        Vitamio.isInitialized(getApplicationContext());
        BaseFrameworkSettings.DEBUGMODE = true;
        BaseFrameworkSettings.BETA_PLAN = true;

        AppManager.setOnActivityStatusChangeListener(new AppManager.OnActivityStatusChangeListener() {
            @Override
            public void onActivityCreate(BaseActivity activity) {

            }

            @Override
            public void onActivityDestroy(BaseActivity activity) {

            }

            @Override
            public void onAllActivityClose() {
                Log.e(">>>", "onAllActivityClose ");
            }
        });
    }
}
