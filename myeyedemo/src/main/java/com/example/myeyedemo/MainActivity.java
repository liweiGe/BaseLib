package com.example.myeyedemo;


import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.basic.G;
import com.lib.EUIMSG;
import com.lib.FunSDK;
import com.lib.MsgContent;
import com.lib.sdk.struct.SDK_ChannelNameConfigAll;
import com.video.opengl.GLSurfaceView20;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends BaseActivity implements OnClickListener, OnItemSelectedListener {
    private EditText serAdd;//
    private EditText serPort;//
    private EditText user;//
    private EditText psd;//
    private Button getList;//
    private RadioButton main;//
    private RadioButton extra;//
    private Button start;//
    private Button stop;//
    private int realhandle;
    SurfaceView showWnd;
    Spinner _spChannel;
    public int _hPlayer;
    String _devSN = "";
    boolean _bLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        init();
    }

    @Override
    protected void onDestroy() {
        if (_hPlayer != 0) {
            FunSDK.MediaStop(_hPlayer);
            _hPlayer = 0;
        }
        super.onDestroy();
    }

    public void init() {
        serAdd = (EditText) findViewById(R.id.serAdd);
        serAdd.setText("10.10.16.66");
        serPort = (EditText) findViewById(R.id.serPort);
        serPort.setText("9000");
        user = (EditText) findViewById(R.id.user);
        user.setText("1");
        psd = (EditText) findViewById(R.id.psd);
        psd.setText("123456");
        getList = (Button) findViewById(R.id.getList);
        main = (RadioButton) findViewById(R.id.rbMain);
        main.setChecked(true);
        extra = (RadioButton) findViewById(R.id.rbExtra);
        start = (Button) findViewById(R.id.startPlay);
        stop = (Button) findViewById(R.id.stopPlay);
        _spChannel = (Spinner) findViewById(R.id.spChannelList);
        getList.setOnClickListener(this);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);

        Spinner sp = (Spinner) findViewById(R.id.spDevList);
        if (sp != null) {
            sp.setOnItemSelectedListener(this);
        }

        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        showWnd = new GLSurfaceView20(this);
        LinearLayout ll = (LinearLayout) this.findViewById(R.id.layoutPlayWnd);
        LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, width * 576 / 704);
        ll.addView(showWnd, lp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void Logout() {
        if (_hPlayer != 0) {
            FunSDK.MediaStop(_hPlayer);
            _hPlayer = 0;
        }
        _bLogin = false;
        this.findViewById(R.id.layoutLoginInfo).setVisibility(View.VISIBLE);
        this.findViewById(R.id.layoutMedia).setVisibility(View.GONE);
        ((Button) this.findViewById(R.id.getList)).setText("LogIn");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getList: { // 获取设备列表
                if (_bLogin) {
                    Logout();
                } else {
                    String serverIP = serAdd.getText().toString();
                    String serverPort = serPort.getText().toString();
                    String userName = user.getText().toString();
                    String pwd = psd.getText().toString();
                    if (serverIP.length() == 0 || serverPort.length() == 0 || userName.length() == 0 || pwd.length() == 0) {
                        break;
                    }
                    FunSDK.SysInitNet(serverIP, Integer.parseInt(serverPort));
                    FunSDK.SysGetDevList(GetId(), userName, pwd, 0);
                }
            }
            break;
            case R.id.startPlay: { // 播放实时视频
                if (_devSN.length() <= 0) {
                    break;
                }
                if (_hPlayer != 0) {
                    FunSDK.MediaStop(_hPlayer);
                    _hPlayer = 0;
                }
                int nChannel = _spChannel.getSelectedItemPosition();
                if (nChannel < 0) {
                    nChannel = 0;
                }
                _hPlayer = FunSDK.MediaRealPlay(this.GetId(), _devSN, nChannel, main.isChecked() ? 0 : 1, showWnd, 0);
            }
            break;
            case R.id.stopPlay:
                if (_hPlayer != 0) {
                    FunSDK.MediaStop(_hPlayer);
                    _hPlayer = 0;
                }
                break;
            default:
                break;
        }
    }

    public int OnFunSDKResult(Message msg, MsgContent ex) {
        switch (msg.what) {
            case EUIMSG.SYS_GET_DEV_INFO_BY_USER:
                if (msg.arg1 > 0) {
                    _bLogin = true;
                    this.findViewById(R.id.layoutLoginInfo).setVisibility(View.GONE);
                    this.findViewById(R.id.layoutMedia).setVisibility(View.VISIBLE);
                    ((Button) this.findViewById(R.id.getList)).setText("Logout");
                    DataCenter.Instance().UpdateData(ex.pData);
                    this.InitDevList();
                } else {
                    this.ShowMsg("SYS_GET_DEV_INFO_BY_USER Error:" + msg.arg1);
                }
                break;
            case EUIMSG.SYS_GET_DEV_STATE: {
                MyDeviceInfo devInfo = DataCenter.Instance().GetDBDeviceInfo(ex.str);
                if (devInfo != null) {
                    devInfo.nState = msg.arg1;
                }
            }
            break;
            case EUIMSG.ON_MEDIA_REPLAY:
                if (msg.arg1 > 0) {
                    this.ShowMsg("ON_MEDIA_REPLAY Successfull!");
                } else {
                    this.ShowMsg("ON_MEDIA_REPLAY Error:" + msg.arg1);
                }
                break;
            case EUIMSG.DEV_GET_CHN_NAME: {
                if (msg.arg1 <= 0) {
                    this.ShowMsg("DEV_GET_CHN_NAME Error!");
                    break;
                }
                SDK_ChannelNameConfigAll Channel = DataCenter.Instance()
                        .ChannelNameConfigAll(ex.pData);
                Channel.nChnCount = msg.arg1;

                Spinner spinner_c = _spChannel;
                ArrayList<String> allcountries = new ArrayList<String>();
                for (int i = 0; i < Channel.nChnCount; i++) {
                    String name = G.ToString(Channel.st_channelTitle[i]);
                    allcountries.add(name);
                }
                ArrayAdapter aspnCountries = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allcountries);
                aspnCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_c.setAdapter(aspnCountries);
                break;
            }
            default:
                break;
        }
        return 0;
    }

    void InitDevList() {
        Spinner spinner_c = (Spinner) findViewById(R.id.spDevList);
        ArrayList allcountries = new ArrayList<String>();
        List<MyDeviceInfo> devs = DataCenter.Instance().GetDevList();
        String devStates = "";
        for (int i = 0; i < devs.size(); i++) {
            String mac = devs.get(i).GetDevId();
            String name = devs.get(i).GetDevName();
            if (mac.equals(name)) {
                allcountries.add(name);
            } else {
                allcountries.add(name + "(" + mac + ")");
            }
            devStates += mac + ";";
        }
        FunSDK.SysGetDevState(GetId(), devStates, 0);
        ArrayAdapter aspnCountries = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allcountries);
        aspnCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_c.setAdapter(aspnCountries);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View v, int position, long id) {
        List<MyDeviceInfo> devs = DataCenter.Instance().GetDevList();
        if (position >= 0 && position < devs.size()) {
            _devSN = devs.get(position).GetDevId();
            FunSDK.DevGetChnName(this.GetId(), _devSN, "", "", 0);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }
}
