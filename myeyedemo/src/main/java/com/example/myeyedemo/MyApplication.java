package com.example.myeyedemo;

import android.app.Application;
import android.view.WindowManager;

import com.basic.G;
import com.lib.FunSDK;
import com.lib.sdk.struct.SInitParam;

public class MyApplication extends Application {
	private WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
	public static int serverVersion = 2;// 服务器版本
	private static MyApplication application;
	public static MyApplication getInstance() {
		return application;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
		
		SInitParam param = new SInitParam();
		param.st_0_nAppType = SInitParam.LOGIN_TYPE_XM030;
		FunSDK.Init(0, G.ObjToBytes(param));
		FunSDK.InitNetSDK();
	}
}
