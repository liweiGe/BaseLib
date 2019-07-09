package com.example.niubilityapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    public void httpTest(View view) {
//http://musicapi.leanapp.cn/
        EasyHttp.get("http://musicapi.leanapp.cn/banner?type=1")

                .execute(new SimpleCallBack<Object>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(Object o) {

                    }
                });
    }

    public void goMain(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
