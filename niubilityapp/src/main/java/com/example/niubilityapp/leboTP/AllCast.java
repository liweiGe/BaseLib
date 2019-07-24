package com.example.niubilityapp.leboTP;

import android.app.Activity;
import android.content.Context;

import com.hpplay.sdk.source.api.IConnectListener;
import com.hpplay.sdk.source.api.ILelinkMirrorManager;
import com.hpplay.sdk.source.api.ILelinkPlayerListener;
import com.hpplay.sdk.source.api.IRelevantInfoListener;
import com.hpplay.sdk.source.api.InteractiveAdListener;
import com.hpplay.sdk.source.api.LelinkPlayer;
import com.hpplay.sdk.source.api.LelinkPlayerInfo;
import com.hpplay.sdk.source.bean.DanmakuBean;
import com.hpplay.sdk.source.bean.DanmakuPropertyBean;
import com.hpplay.sdk.source.bean.MediaAssetBean;
import com.hpplay.sdk.source.browse.api.AdInfo;
import com.hpplay.sdk.source.browse.api.IAPI;
import com.hpplay.sdk.source.browse.api.IBrowseListener;
import com.hpplay.sdk.source.browse.api.ILelinkServiceManager;
import com.hpplay.sdk.source.browse.api.IPinCodeListener;
import com.hpplay.sdk.source.browse.api.IQRCodeListener;
import com.hpplay.sdk.source.browse.api.LelinkServiceInfo;
import com.hpplay.sdk.source.browse.api.LelinkServiceManager;
import com.hpplay.sdk.source.browse.api.LelinkSetting;

import java.io.File;
import java.util.List;
import java.util.Random;

/**
 * Created by Zippo on 2018/5/16.
 * Date: 2018/5/16
 * Time: 14:33:06
 */
public class AllCast {

    private static final String TAG = "AllCast";

    public static final int MEDIA_TYPE_VIDEO = LelinkPlayerInfo.TYPE_VIDEO;
    public static final int MEDIA_TYPE_AUDIO = LelinkPlayerInfo.TYPE_AUDIO;
    public static final int MEDIA_TYPE_IMAGE = LelinkPlayerInfo.TYPE_IMAGE;
    public static final int RESOLUTION_HEIGHT = ILelinkMirrorManager.RESOLUTION_HIGH;
    public static final int RESOLUTION_MIDDLE = ILelinkMirrorManager.RESOLUTION_MID;
    public static final int RESOLUTION_AUTO = ILelinkMirrorManager.RESOLUTION_AUTO;
    public static final int BITRATE_HEIGHT = ILelinkMirrorManager.BITRATE_HIGH;
    public static final int BITRATE_MIDDLE = ILelinkMirrorManager.BITRATE_MID;
    public static final int BITRATE_LOW = ILelinkMirrorManager.BITRATE_LOW;

    private ILelinkServiceManager mLelinkServiceManager;
    private LelinkPlayer mLelinkPlayer;

    public AllCast(Context context, String appid, String appSecret) {
        initLelinkService(context, appid, appSecret);
    }

    public void setOnBrowseListener(IBrowseListener listener) {
        mLelinkServiceManager.setOnBrowseListener(listener);
    }

    public void setConnectListener(IConnectListener listener) {
        mLelinkPlayer.setConnectListener(listener);
    }

    public void setPlayerListener(ILelinkPlayerListener listener) {
        mLelinkPlayer.setPlayerListener(listener);
        mLelinkPlayer.setRelevantInfoListener(new IRelevantInfoListener() {

            @Override
            public void onSendRelevantInfoResult(int option, String result) {

            }

        });
    }

    private void initLelinkService(Context context, String appid, String appSecret) {
        LelinkSetting lelinkSetting = new LelinkSetting.LelinkSettingBuilder(appid, appSecret)
                .build();
        mLelinkServiceManager = LelinkServiceManager.getInstance(context);
        mLelinkServiceManager.setDebug(true);
        mLelinkServiceManager.setLelinkSetting(lelinkSetting);
        mLelinkServiceManager.setOption(IAPI.OPTION_5, false);
        initLelinkPlayer(context);
    }

    private void initLelinkPlayer(Context pContext) {
        mLelinkPlayer = new LelinkPlayer(pContext);
    }

    public List<LelinkServiceInfo> getConnectInfos() {
        return mLelinkPlayer.getConnectLelinkServiceInfos();
    }

    public void addQRServiceInfo(String qrCode, IQRCodeListener listener) {
        mLelinkServiceManager.addQRServiceInfo(qrCode, listener);
    }

    public void addPinCodeServiceInfo(String pinCode) {
        mLelinkServiceManager.addPinCodeServiceInfo(pinCode, new IPinCodeListener() {

            @Override
            public void onParceResult(int resultCode, LelinkServiceInfo info) {

                if (resultCode == IPinCodeListener.PARCE_SUCCESS) {
                    mLelinkPlayer.connect(info);
                }
            }

        });
    }

    public void onBrowseListGone() {
        mLelinkServiceManager.onBrowseListGone();
    }

    public void onPushButtonClick() {
        mLelinkServiceManager.onPushButtonClick();
    }

    public void browse(int type) {
        mLelinkServiceManager.browse(type);
    }

    public void stopBrowse() {
        mLelinkServiceManager.stopBrowse();
    }

    public void connect(LelinkServiceInfo info) {
        mLelinkPlayer.connect(info);
    }

    public void disConnect(LelinkServiceInfo info) {
        if(info != null){
            mLelinkPlayer.disConnect(info);
        }
    }

    public void deleteRemoteServiceInfo(LelinkServiceInfo serviceInfo) {
        mLelinkServiceManager.deleteRemoteServiceInfo(serviceInfo);
    }

    public boolean canPlayLocalVideo(LelinkServiceInfo serviceInfo) {
        if (mLelinkPlayer != null) {
            return mLelinkPlayer.canPlayLocalVideo(serviceInfo);
        }
        return false;
    }

    public boolean canPlayLocalPhoto(LelinkServiceInfo serviceInfo) {
        if (mLelinkPlayer != null) {
            return mLelinkPlayer.canPlayLocalPhoto(serviceInfo);
        }
        return false;
    }

    public boolean canPlayLocalAudio(LelinkServiceInfo serviceInfo) {
        if (mLelinkPlayer != null) {
            return mLelinkPlayer.canPlayLocalAudio(serviceInfo);
        }
        return false;
    }

    public boolean canPlayOnlineAudio(LelinkServiceInfo serviceInfo) {
        if (mLelinkPlayer != null) {
            return mLelinkPlayer.canPlayOnlieAudio(serviceInfo);
        }
        return false;
    }

    public boolean canPlayOnlinePhoto(LelinkServiceInfo serviceInfo) {
        if (mLelinkPlayer != null) {
            return mLelinkPlayer.canPlayOnliePhoto(serviceInfo);
        }
        return false;
    }

    public boolean canPlayOnlineVideo(LelinkServiceInfo serviceInfo) {
        if (mLelinkPlayer != null) {
            return mLelinkPlayer.canPlayOnlieVideo(serviceInfo);
        }
        return false;
    }

    public boolean canPlayScreen(LelinkServiceInfo serviceInfo) {
        if (mLelinkPlayer != null) {
            return mLelinkPlayer.canPlayScreen(serviceInfo);
        }
        return false;
    }

    public void playLocalMedia(String url, int type, String screenCode) {
        LelinkPlayerInfo lelinkPlayerInfo = new LelinkPlayerInfo();
        lelinkPlayerInfo.setType(type);
        lelinkPlayerInfo.setLocalPath(url);
        lelinkPlayerInfo.setOption(IAPI.OPTION_6, screenCode);
        // lelinkPlayerInfo.setStartPosition(8);
        mLelinkPlayer.setDataSource(lelinkPlayerInfo);
        mLelinkPlayer.start();
    }

    public void playNetMedia(String url, int type, String screenCode) {
        LelinkPlayerInfo lelinkPlayerInfo = new LelinkPlayerInfo();
        lelinkPlayerInfo.setType(type);
        lelinkPlayerInfo.setUrl(url);
        lelinkPlayerInfo.setOption(IAPI.OPTION_6, screenCode);
        // lelinkPlayerInfo.setStartPosition(15);
        mLelinkPlayer.setDataSource(lelinkPlayerInfo);
        mLelinkPlayer.start();
    }

    public void playNetMedia(String url, int type) {
        LelinkPlayerInfo lelinkPlayerInfo = new LelinkPlayerInfo();
        lelinkPlayerInfo.setType(type);
        lelinkPlayerInfo.setUrl(url);
//        lelinkPlayerInfo.setOption(IAPI.OPTION_6, screenCode);
        // lelinkPlayerInfo.setStartPosition(15);
        mLelinkPlayer.setDataSource(lelinkPlayerInfo);
        mLelinkPlayer.start();
    }

    public void playNetMediaWithAsset(String url, int type) {
        LelinkPlayerInfo lelinkPlayerInfo = new LelinkPlayerInfo();
        lelinkPlayerInfo.setType(type);
        lelinkPlayerInfo.setUrl(url);
        MediaAssetBean mediaAssetBean = new MediaAssetBean();
        mediaAssetBean.setActor("qiuju");
        mediaAssetBean.setDirector("zhangyimou");
        mediaAssetBean.setId("xxxxx");
        mediaAssetBean.setName("qiujudaguansi");
        lelinkPlayerInfo.setMediaAsset(mediaAssetBean);
        mLelinkPlayer.setDataSource(lelinkPlayerInfo);
        mLelinkPlayer.start();
    }

    public void playNetMediaWithHeader(String url, int type) {
        LelinkPlayerInfo lelinkPlayerInfo = new LelinkPlayerInfo();
        lelinkPlayerInfo.setType(type);
        lelinkPlayerInfo.setHeader("{\"header\":\"header data\"}");
        lelinkPlayerInfo.setAesKey("314e949aa0d73ee01079ae7035425a79");
        lelinkPlayerInfo.setAesIv("19835d6eea03aa2c5f80916ce9413c81");
        lelinkPlayerInfo.setUrl("http://58.68.252.136/vod1/cctv1/01.m3u8?hpplay=1");
        lelinkPlayerInfo.setLoopMode(LelinkPlayerInfo.LOOP_MODE_SINGLE);
        // lelinkPlayerInfo.setStartPosition(15);
        mLelinkPlayer.setDataSource(lelinkPlayerInfo);
        mLelinkPlayer.start();
    }

    public void sendRelevantInfo(String appid, boolean isSdk) {
        mLelinkPlayer.sendRelevantInfo(ILelinkPlayerListener.RELEVANCE_DATA, "{\"data\":\"pass through\"}", appid, isSdk);
    }

    public void sendLeboRelevantInfo(String appid, boolean isSdk) {
        mLelinkPlayer.sendRelevantInfo(ILelinkPlayerListener.RELEVANCE_LEBO_DATA, "{\"data\":\"pass through\"}", appid, isSdk);
    }

    public void sendRelevantErrorInfo() {
        mLelinkPlayer.sendRelevantInfo(ILelinkPlayerListener.RELEVANCE_ERROR);
    }

    private String[] danmuText = {"弹幕测试数据1", "弹幕测试数据22", "弹幕测试数据333", "弹幕测试数据4444", "弹幕测试数据55555", "弹幕测试数据666666", "弹幕测试数据7777777"};

    public void sendDanmaku() {
        DanmakuBean danmakuBean = new DanmakuBean();
        danmakuBean.setContent(danmuText[new Random().nextInt(danmuText.length)]);

//        danmakuBean.setDisplayTime(1000); //对应视频的播放进度
        danmakuBean.setImmShow(true);//是否马上显示
        danmakuBean.setFontSize(new Random().nextInt(20) + 10);
        mLelinkPlayer.sendDanmaku(danmakuBean);
    }

    public void sendDanmakuProperty(DanmakuPropertyBean danmakuBean) {
        mLelinkPlayer.sendDanmakuProperty(danmakuBean);
    }

    public void startWithLoopMode(String url, boolean isLocalFile) {
        LelinkPlayerInfo playerInfo = new LelinkPlayerInfo();
        if (isLocalFile) {
            playerInfo.setLocalPath(url);
        } else {
            playerInfo.setUrl(url);
        }
        playerInfo.setType(MEDIA_TYPE_VIDEO);
        playerInfo.setLoopMode(LelinkPlayerInfo.LOOP_MODE_SINGLE);
        mLelinkPlayer.setDataSource(playerInfo);
        mLelinkPlayer.start();
    }

    public void startNetVideoWith3rdMonitor(String netVideoUrl) {
        LelinkPlayerInfo playerInfo = new LelinkPlayerInfo();
        playerInfo.setUrl(netVideoUrl);
        playerInfo.setType(MEDIA_TYPE_VIDEO);
        playerInfo.putMonitor(LelinkPlayerInfo.MONITOR_START, "http://aa.qiyi.com/report?u=_UID_&h=_HID_&m=_MAC_&t=_TIME_&model=_MODEL_&a=_APPID_&p=_POS_&i=_IP_");
        playerInfo.putMonitor(LelinkPlayerInfo.MONITOR_STOP, "http://aa.qiyi.com/report?u=_UID_&h=_HID_&m=_MAC_&t=_TIME_&model=_MODEL_&a=_APPID_&p=_POS_&i=_IP_");
        playerInfo.putMonitor(LelinkPlayerInfo.MONITOR_RESUME, "http://aa.qiyi.com/report?u=_UID_&h=_HID_&m=_MAC_&t=_TIME_&model=_MODEL_&a=_APPID_&p=_POS_&i=_IP_");
        playerInfo.putMonitor(LelinkPlayerInfo.MONITOR_PAUSE, "http://aa.qiyi.com/report?u=_UID_&h=_HID_&m=_MAC_&t=_TIME_&model=_MODEL_&a=_APPID_&p=_POS_&i=_IP_");
        mLelinkPlayer.setDataSource(playerInfo);
        mLelinkPlayer.start();
    }

    public void resume() {
        mLelinkPlayer.resume();
    }

    public void pause() {
        mLelinkPlayer.pause();
    }

    public void stop() {
        mLelinkPlayer.stop();
    }

    public void seekTo(int position) {
        mLelinkPlayer.seekTo(position);
    }

    public void setVolume(int percent) {
        mLelinkPlayer.setVolume(percent);
    }

    public void voulumeUp() {
        mLelinkPlayer.addVolume();
    }

    public void voulumeDown() {
        mLelinkPlayer.subVolume();
    }

    public void startMirror(Activity pActivity, LelinkServiceInfo lelinkServiceInfo,
                            int resolutionLevel, int bitrateLevel, boolean isAudioEnnable, String screenCode) {
        if (mLelinkPlayer != null) {
            LelinkPlayerInfo lelinkPlayerInfo = new LelinkPlayerInfo();
            lelinkPlayerInfo.setType(LelinkPlayerInfo.TYPE_MIRROR);
            lelinkPlayerInfo.setActivity(pActivity);
            lelinkPlayerInfo.setOption(IAPI.OPTION_6, screenCode);
            lelinkPlayerInfo.setLelinkServiceInfo(lelinkServiceInfo);
            lelinkPlayerInfo.setMirrorAudioEnable(isAudioEnnable);
            lelinkPlayerInfo.setResolutionLevel(resolutionLevel);
            lelinkPlayerInfo.setBitRateLevel(bitrateLevel);
            mLelinkPlayer.setDataSource(lelinkPlayerInfo);
            mLelinkPlayer.start();
        }
    }

    public void stopMirror() {
        if (mLelinkPlayer != null) {
            mLelinkPlayer.stop();
        }
    }

    public void setInteractiveAdListener(InteractiveAdListener listener) {
        mLelinkPlayer.setInteractiveAdListener(listener);
    }

    public void onInteractiveAdShow(AdInfo adInfo, int status) {
        mLelinkPlayer.onAdShow(adInfo, status);
    }

    public void onInteractiveAdClosed(AdInfo adInfo, int duration, int status) {
        mLelinkPlayer.onAdClosed(adInfo, duration, status);
    }

    public void release() {
        mLelinkPlayer.release();
    }

    public void setOption(int opt, Object... values) {
        mLelinkPlayer.setOption(opt, values);
    }


    public void startScreenShot() {
        LelinkPlayerInfo playerInfo = new LelinkPlayerInfo();
        playerInfo.setOption(IAPI.OPTION_19, "/sdcard" + File.separator + "screenshot.jpg");
        mLelinkPlayer.setDataSource(playerInfo);
        mLelinkPlayer.start();

    }


}
