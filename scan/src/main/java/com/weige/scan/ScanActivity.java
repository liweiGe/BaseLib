//package com.weige.scan;
//
//import android.content.Intent;
//import android.graphics.Rect;
//import android.os.Bundle;
//import android.os.Vibrator;
//import android.text.TextUtils;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.CheckBox;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.alibaba.android.arouter.facade.annotation.Route;
//import com.alibaba.android.arouter.launcher.ARouter;
//import com.geely.base.BaseActivity;
//import com.huawei.hms.hmsscankit.RemoteView;
//import com.huawei.hms.ml.scan.HmsScan;
//import com.movit.platform.framework.utils.ScreenUtils;
//
///**
// * https://developer.huawei.com/consumer/cn/forum/topicview?tid=0201282395801440275&fid=18
// * https://developer.huawei.com/consumer/cn/doc/development/HMS-Guides/scan-default4
// 这个只是做个参考,扫一扫的页面一般都是自定义的
// */
//
//public class ScanActivity extends AppCompatActivity {
//    private RemoteView remoteView;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        setContentView(R.layout.activity_capture);
//        findViewById(R.id.iv_back).setOnClickListener(view -> ScanActivity.this.finish());
//        findViewById(R.id.bt_gallery).setOnClickListener(view -> remoteView.selectPictureFromLocalFile());
//        findViewById(R.id.bt_my_info_code).setOnClickListener(view -> skipMyCode());
//        initRemoteView(savedInstanceState);
//    }
//
//    private void initRemoteView(Bundle savedInstanceState) {
//        //2. Obtain the screen size.
//        int mScreenWidth = ScreenUtils.getScreenWidth(this);
//        int mScreenHeight = ScreenUtils.getScreenHeight(this);
//        int scanFrameSize = ScreenUtils.dp2px(this, 260);
//        Rect rect = new Rect();
//        rect.left = mScreenWidth / 2 - scanFrameSize / 2;
//        rect.right = mScreenWidth / 2 + scanFrameSize / 2;
//        rect.top = mScreenHeight / 2 - scanFrameSize / 2;
//        rect.bottom = mScreenHeight / 2 + scanFrameSize / 2;
//
//        remoteView = new RemoteView.Builder().setContext(this)
//                .setBoundingBox(rect) //设置扫描大小
//                .setFormat(HmsScan.QRCODE_SCAN_TYPE) //设置只扫描二维码类型
//                .build();
//        // Load the customized view to the activity.
//        remoteView.onCreate(savedInstanceState);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        ViewGroup group = findViewById(R.id.layout_parent);
//        group.addView(remoteView, 0, params);
//        CheckBox flushBtn = findViewById(R.id.flush_btn);
//        //自动监听是否需要打开闪光灯
//        remoteView.setOnLightVisibleCallback(visible -> {
//            if (visible) {
//                flushBtn.setVisibility(View.VISIBLE);
//            }
//        });
//        flushBtn.setOnCheckedChangeListener((buttonView, isChecked) -> remoteView.switchLight());
//        remoteView.setOnResultCallback(this::resolveScanResult);
//        getLifecycle().addObserver(new ObserverRemoteView(remoteView));
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        remoteView.onActivityResult(requestCode, resultCode, data);
//    }
//
//    /**
//     * @param hmsScans
//     */
//    private void resolveScanResult(HmsScan[] hmsScans) {
//        if (hmsScans != null
//                && hmsScans.length > 0
//                && hmsScans[0] != null
//                && !TextUtils.isEmpty(hmsScans[0].getOriginalValue())) {
//            HmsScan hmsScan = hmsScans[0];
//            if (hmsScan.getScanTypeForm() == HmsScan.URL_FORM
//                    && hmsScan.getScanType() == HmsScan.QRCODE_SCAN_TYPE) {
//                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//                vibrator.vibrate(300);
//                remoteView.pauseContinuouslyScan();
//                String originalValue = hmsScan.getOriginalValue();
//            }
//        }
//    }
//
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && remoteView != null) {
//            remoteView.resumeContinuouslyScan();
//        }
//    }
//}
