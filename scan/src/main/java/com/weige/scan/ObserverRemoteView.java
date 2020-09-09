package com.weige.scan;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.huawei.hms.hmsscankit.RemoteView;

/**
 * @Description: 有生命周期监听的remoteView包装类
 * @Author: Liwei.Qiu
 * @Date: 2020/9/2 11:29
 */
public class ObserverRemoteView implements DefaultLifecycleObserver {
    RemoteView mRemoteView;

    public ObserverRemoteView(RemoteView remoteView) {
        mRemoteView = remoteView;
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        mRemoteView.onStart();
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        mRemoteView.onResume();
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        mRemoteView.onPause();
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        mRemoteView.onStop();
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        mRemoteView.onDestroy();
        mRemoteView = null;
    }
}
