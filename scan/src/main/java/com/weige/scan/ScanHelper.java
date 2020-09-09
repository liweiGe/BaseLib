package com.weige.scan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.huawei.hms.hmsscankit.RemoteView;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.hmsscankit.WriterException;
import com.huawei.hms.ml.scan.HmsBuildBitmapOption;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;

import java.io.IOException;

/**
 * @Description: 扫一扫入口类
 * @Author: Liwei.Qiu
 * @Date: 2020/9/9 11:08
 * https://developer.huawei.com/consumer/cn/doc/development/HMS-Guides/scan-overview4
 */
public class ScanHelper {
    static final int REQUEST_CODE_SCAN_ONE = 0x111;

    /**
     * 启动默认的扫一扫
     *
     * @param activity
     */
    public static void openScan(Activity activity) {
        //“QRCODE_SCAN_TYPE”和“DATAMATRIX_SCAN_TYPE”表示只扫描QR和Data Matrix的码
        HmsScanAnalyzerOptions options = new HmsScanAnalyzerOptions.Creator()
                .setHmsScanTypes(HmsScan.QRCODE_SCAN_TYPE)
                .create();
        ScanUtil.startScan(activity, REQUEST_CODE_SCAN_ONE, options);
    }

    /**
     * 只限于调用openScan 这个方法调用,也就是说跟上面这个方法是搭配使用的
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public static void activityResult(int requestCode, int resultCode, Intent data, ScanResultCallBack callBack) {
        if (resultCode != Activity.RESULT_OK || data == null || callBack == null) {
            return;
        }
        if (requestCode == REQUEST_CODE_SCAN_ONE) {
            HmsScan obj = data.getParcelableExtra(ScanUtil.RESULT);
            if (obj != null) {
                //展示解码结果
                String originalValue = obj.getOriginalValue();
                if (!TextUtils.isEmpty(originalValue)) {
                    callBack.onSuccess(originalValue);
                } else {
                    callBack.onFail();
                }
            } else {
                callBack.onFail();
            }
        }
    }

    /**
     * 自定义扫描界面的时候调用,需要配合扫描框控件 com.huawei.hms.scankit.ViewfinderView(可自定义一些参数) 一起使用
     *
     * @param context
     * @param savedInstanceState
     * @param width
     * @return
     */
    public static RemoteView getScanView(@NonNull Context context, Bundle savedInstanceState, int width) {
        //2. Obtain the screen size.
        int mScreenWidth = getScreenWidth(context);
        int mScreenHeight = getScreenHeight(context);
        Rect rect = new Rect();
        rect.left = mScreenWidth / 2 - width / 2;
        rect.right = mScreenWidth / 2 + width / 2;
        rect.top = mScreenHeight / 2 - width / 2;
        rect.bottom = mScreenHeight / 2 + width / 2;

        RemoteView remoteView = new RemoteView.Builder().setContext((Activity) context)
                .setBoundingBox(rect) //设置扫描大小
                .setFormat(HmsScan.QRCODE_SCAN_TYPE) //设置只扫描二维码类型
                .build();
        // Load the customized view to the activity.
        remoteView.onCreate(savedInstanceState);
        /**************************  下面这部分得在使用处处理监听操作  ********************/
        //自动监听是否需要打开闪光灯
//        remoteView.setOnLightVisibleCallback(visible -> {
//            if (visible) {
//                flushBtn.setVisibility(View.VISIBLE);
//            }
//        });
//        flushBtn.setOnCheckedChangeListener((buttonView, isChecked) -> remoteView.switchLight());
//        getLifecycle().addObserver(new ObserverRemoteView(remoteView));
//        remoteView.setOnResultCallback(new OnResultCallback() {
//            @Override
//            public void onResult(HmsScan[] hmsScans) {
//
//            }
//        });
        return remoteView;
    }

    /**
     * 解析本地图片
     * 选择本地图片可以使用: remoteView.selectPictureFromLocalFile()
     * 选择图片成功之后可以调用:  remoteView.onActivityResult(requestCode, resultCode, data);
     *
     * @param context
     * @param path     图片本地路径
     * @param callBack
     */
    public static void decodeQRBitmap(Context context, String path, ScanResultCallBack callBack) {
        if (callBack == null) return;
        try {
            Uri uri = Uri.parse(path);
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            //“QRCODE_SCAN_TYPE ”和“ DATAMATRIX_SCAN_TYPE表示只扫描QR和Data Matrix的码
            HmsScanAnalyzerOptions options = new HmsScanAnalyzerOptions.Creator()
                    .setHmsScanTypes(HmsScan.QRCODE_SCAN_TYPE)
                    .setPhotoMode(true).create();
            HmsScan[] hmsScans = ScanUtil.decodeWithBitmap(context, bitmap, options);
            scanResultParse(hmsScans, callBack);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接解析bitmap
     *
     * @param context
     * @param date
     * @param callBack
     */
    public static void decodeQRBitmap(Context context, Bitmap date, ScanResultCallBack callBack) {
        if (callBack == null) return;
        //“QRCODE_SCAN_TYPE ”和“ DATAMATRIX_SCAN_TYPE表示只扫描QR和Data Matrix的码
        HmsScanAnalyzerOptions options = new HmsScanAnalyzerOptions.Creator()
                .setHmsScanTypes(HmsScan.QRCODE_SCAN_TYPE)
                .setPhotoMode(true).create();
        HmsScan[] hmsScans = ScanUtil.decodeWithBitmap(context, date, options);
        scanResultParse(hmsScans, callBack);
    }

    /**
     * 生成二维码图片(bitmap)
     *
     * @param content
     */
    public static Bitmap createQRCodeBitmap(String content, int width, int height) {
        Bitmap qrBitmap = null;
        try {
            HmsBuildBitmapOption options = new HmsBuildBitmapOption.Creator()
                    .create();
            //如果未设置HmsBuildBitmapOption对象，生成二维码参数options置null”
            qrBitmap = ScanUtil.buildBitmap(content, HmsScan.QRCODE_SCAN_TYPE, width, height, options);
        } catch (WriterException e) {
            Log.w("buildBitmap", e);
        }
        return qrBitmap;
    }

    /**
     * 这个可以在回调处理中调用
     * if (needVibrator){
     * Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
     * vibrator.vibrate(300);
     * }
     * <p>
     * 这个是最好是调用处理,如果不是解析跳转页面的话,可能是弹窗处理的情况
     * remoteView.pauseContinuouslyScan();
     *
     * @param hmsScans
     * @param callBack
     */
    private static void scanResultParse(HmsScan[] hmsScans, ScanResultCallBack callBack) {
        if (callBack == null) return;
        if (hmsScans != null && hmsScans.length > 0 && hmsScans[0] != null
                && !TextUtils.isEmpty(hmsScans[0].getOriginalValue())) {
            HmsScan hmsScan = hmsScans[0];
            if (hmsScan.getScanTypeForm() == HmsScan.URL_FORM
                    && hmsScan.getScanType() == HmsScan.QRCODE_SCAN_TYPE) {
                String originalValue = hmsScan.getOriginalValue();
                callBack.onSuccess(originalValue);
            } else {
                callBack.onFail();
            }
        } else {
            callBack.onFail();
        }
    }

    private static int getScreenWidth(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        if (null != context) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(metric);
        }
        return metric.widthPixels;
    }

    private static int getScreenHeight(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        if (null != context) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(metric);
        }
        return metric.heightPixels;
    }
}
