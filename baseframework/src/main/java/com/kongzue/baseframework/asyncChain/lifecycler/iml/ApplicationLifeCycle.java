package com.kongzue.baseframework.asyncChain.lifecycler.iml;


import com.kongzue.baseframework.asyncChain.lifecycler.base.AsyncChainLifeCycle;
import com.kongzue.baseframework.asyncChain.lifecycler.base.AsyncChainLifeCycleListener;

import java.util.ArrayList;
import java.util.List;

/**
 * app运行期间都存在的生命周期实现
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/7/24
 **/
public class ApplicationLifeCycle implements AsyncChainLifeCycle {
    private List<AsyncChainLifeCycleListener> mLifeCycleListenerList = new ArrayList<>();

    @Override
    public void addLifeCycleListener(AsyncChainLifeCycleListener listener) {
        mLifeCycleListenerList.remove(listener);
        mLifeCycleListenerList.add(listener);
    }

    @Override
    public List<AsyncChainLifeCycleListener> getLifeCycleListeners() {
        return mLifeCycleListenerList;
    }
    @Override
    public void removeLifeCycleListener(AsyncChainLifeCycleListener listener) {
        mLifeCycleListenerList.remove(listener);
    }
}
