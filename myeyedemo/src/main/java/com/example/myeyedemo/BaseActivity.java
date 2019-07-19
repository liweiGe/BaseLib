package com.example.myeyedemo;

import android.app.Activity;
import android.os.Message;
import android.widget.Toast;

import com.lib.FunSDK;
import com.lib.IFunSDKResult;
import com.lib.MsgContent;

public class BaseActivity extends Activity implements IFunSDKResult{

	int _msgId = -1;
	int GetId(){
		_msgId = FunSDK.GetId(_msgId, this);
		return _msgId;
	}

	@Override
	public int OnFunSDKResult(Message arg0, MsgContent arg1) {
		return 0;
	}
	
	public void ShowMsg(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
}
