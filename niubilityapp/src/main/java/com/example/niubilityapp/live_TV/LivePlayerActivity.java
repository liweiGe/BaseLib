package com.example.niubilityapp.live_TV;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.example.niubilityapp.R;
import com.example.niubilityapp.leboTP.IUIUpdateListener;
import com.example.niubilityapp.leboTP.LelinkHelper;
import com.example.niubilityapp.leboTP.MessageDeatail;
import com.hpplay.sdk.source.api.LelinkPlayerInfo;
import com.hpplay.sdk.source.browse.api.ILelinkServiceManager;
import com.hpplay.sdk.source.browse.api.LelinkServiceInfo;
import com.kongzue.baseframework.toast.Toaster;
import com.kongzue.baseframework.util.ObjectUtils;

import java.util.List;

/**
 * 投屏文档:
 * http://cloud.hpplay.cn:9889/dev/#http://cdn.hpplay.com.cn/doc/sdk/source/android/_book/
 */

public class LivePlayerActivity extends AppCompatActivity {

    private IjkVideoView ijkVideoView;
    private LelinkHelper instance;
    private TextView tp_tv;
    private LelinkServiceInfo lelinkServiceInfo;
    private String url;
    private AlertDialog dialog1;
    private AlertDialog dialog2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_player);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Player");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ijkVideoView = findViewById(R.id.player);

        Intent intent = getIntent();
        if (intent != null) {
            StandardVideoController controller = new StandardVideoController(this);
            boolean isLive = intent.getBooleanExtra("isLive", false);
            if (isLive) {
                controller.setLive();
            }
            String title = intent.getStringExtra(IntentKeys.TITLE);
            controller.setTitle(title);
            //自己添加实验
//            ijkVideoView.setLooping(true);
            url = intent.getStringExtra("url");
            ijkVideoView.setUrl(url);
            ijkVideoView.setVideoController(controller);
            ijkVideoView.start();
        }
        if (ContextCompat.checkSelfPermission(getApplication(),
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_DENIED
                && ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_DENIED) {
            initLelinkHelper();
        } else {
            // 若没有授权，会弹出一个对话框（这个对话框是系统的，开发者不能自己定制），用户选择是否授权应用使用系统权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }

        tp_tv = findViewById(R.id.tp_tv);
        tp_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lelinkServiceInfo == null) return;
                instance.connect(lelinkServiceInfo);
                dialog2 = new AlertDialog.Builder(v.getContext())
                        .setMessage("正在连接tv")
                        .setCancelable(false)
                        .show();

            }
        });
    }

    private void initLelinkHelper() {
        instance = LelinkHelper.getInstance(this);
        instance.setUIUpdateListener(new IUIUpdateListener() {
            @Override
            public void onUpdate(int what, MessageDeatail deatail) {
                Log.i("aaa", "onUpdate: " + deatail.toString());
                if (what == 1) {
                    if (dialog1 != null && dialog1.isShowing()) {
                        dialog1.dismiss();
                    }
                    List<LelinkServiceInfo> infos = instance.getInfos();
                    if (ObjectUtils.isEmpty(infos)) return;
                    lelinkServiceInfo = infos.get(0);
                    tp_tv.setText(lelinkServiceInfo.getName());
                    instance.stopBrowse();
                } else if (what == 10 || what == 11 || what == 12) {
                    if (dialog2 != null && dialog2.isShowing()) {
                        dialog2.dismiss();
                    }
                } else if (what == 2 || what == 3) {
                    if (dialog1 != null && dialog1.isShowing()) {
                        dialog1.dismiss();
                    }
                }
                Toaster.build(getBaseContext()).show(deatail.text);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ijkVideoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ijkVideoView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ijkVideoView.release();
    }


    @Override
    public void onBackPressed() {
        if (!ijkVideoView.onBackPressed()) {
            super.onBackPressed();
        }
    }

    public void screenScaleDefault(View view) {
        ijkVideoView.setScreenScale(IjkVideoView.SCREEN_SCALE_DEFAULT);
    }

    public void screenScale169(View view) {
        ijkVideoView.setScreenScale(IjkVideoView.SCREEN_SCALE_16_9);
    }

    public void screenScale43(View view) {
        ijkVideoView.setScreenScale(IjkVideoView.SCREEN_SCALE_4_3);
    }

    public void search_devices(View view) {
        instance.browse(ILelinkServiceManager.TYPE_ALL);
        dialog1 = new AlertDialog.Builder(this)
                .setMessage("正在搜索")
                .show();
        dialog1.setOnDismissListener(dialog -> instance.stopBrowse());
    }

    public void stop_search(View view) {
        //链接成功
        instance.playNetMedia(url, LelinkPlayerInfo.TYPE_VIDEO);
    }
}
