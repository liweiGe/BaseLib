package com.kongzue.baseframework.asyncChain.lifecycler.iml;


import android.app.Fragment;

import com.kongzue.baseframework.asyncChain.lifecycler.base.AsyncChainLifeCycle;
import com.kongzue.baseframework.asyncChain.lifecycler.base.AsyncChainLifeCycleListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用Fragment实现的生命周期实现，寄主页面不存在的时候生命周期就结束了
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/7/24
 **/

public class FragmentLifeCycle extends Fragment implements AsyncChainLifeCycle {
    private List<AsyncChainLifeCycleListener> mLifeCycleListenerList = new ArrayList<>();

    @Override
    public void addLifeCycleListener(AsyncChainLifeCycleListener listener) {
        mLifeCycleListenerList.remove(listener);
        mLifeCycleListenerList.add(listener);
    }

    @Override
    public void removeLifeCycleListener(AsyncChainLifeCycleListener listener) {
        mLifeCycleListenerList.remove(listener);
    }

    @Override
    public List<AsyncChainLifeCycleListener> getLifeCycleListeners() {
        return mLifeCycleListenerList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (AsyncChainLifeCycleListener lifeCycleListener : mLifeCycleListenerList) {
            lifeCycleListener.onDestroy(this);
        }
        mLifeCycleListenerList.clear();
    }
}
