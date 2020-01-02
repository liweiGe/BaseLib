package com.kongzue.baseframework.asyncChain;

import androidx.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;

/**
 * 一部链式调用的入口类
 *
 * @author :  luoming    luomingbear@163.com
 * @date :  2019/7/22
 **/
public class AsyncChainLink {
    /**
     * 是否已经开始了
     */
    private boolean isStart = false;
    /**
     * 异步操作的列表
     */
    private LinkedList<AsyncChainRunnableWrapper> mRunnableList = new LinkedList<>();
    /**
     * 异步操作错误回调接口
     */
    private AsyncChainErrorCallback mErrorCallback;


    private AsyncChainLink() {

    }

    private static final AsyncChainLink ourInstance = new AsyncChainLink();

    public static AsyncChainLink getInstance() {
        return ourInstance;
    }


    /**
     * 在原始线程执行一个异步操作
     *
     * @param runnable
     */
    public AsyncChainLink with(AsyncChainRunnable runnable) {
        AsyncChainRunnableWrapper wrapper = new AsyncChainRunnableWrapper(
                runnable, AsyncChainRunnableWrapper.ORIGINAL);
        mRunnableList.add(wrapper);
        return this;
    }

    /**
     * 在工作线程，添加一个异步执行的操作
     */
    public AsyncChainLink withWork(AsyncChainRunnable runnable) {
        AsyncChainRunnableWrapper wrapper = new AsyncChainRunnableWrapper(
                runnable, AsyncChainRunnableWrapper.WORK);
        mRunnableList.add(wrapper);
        return this;
    }

    /**
     * 在主线程ui(UI线程)，添加一个异步执行的操作
     */
    public AsyncChainLink withMain(AsyncChainRunnable runnable) {
        AsyncChainRunnableWrapper wrapper = new AsyncChainRunnableWrapper(
                runnable, AsyncChainRunnableWrapper.MAIN);
        mRunnableList.add(wrapper);
        return this;
    }

//    /**
//     * 延迟一定时长执行操作，仅支持在工作线程做延时操作
//     *
//     * @param ms 延迟的时间单位毫秒
//     */
//    public AsyncChainLink delay(long ms) {
//        AsyncChainRunnableWrapper wrapper = new AsyncChainRunnableWrapper(
//                new AsyncChainRunnable.DefaultAsyncChainRunnable(), AsyncChainRunnableWrapper.WORK, ms);
//        mRunnableList.add(wrapper);
//        return this;
//    }

    /**
     * 在报错的线程处理错误
     *
     * @param errorCallback 执行错误的时候的回调
     */
    public AsyncChainLink error(@NonNull AsyncChainErrorCallback errorCallback) {
        mErrorCallback = errorCallback;
        mErrorCallback.setThread(AsyncChainRunnableWrapper.ORIGINAL);
        return this;
    }

    /**
     * 在工作线程处理错误
     *
     * @param errorCallback 执行错误的时候的回调
     */
    public AsyncChainLink errorWork(@NonNull AsyncChainErrorCallback errorCallback) {
        mErrorCallback = errorCallback;
        mErrorCallback.setThread(AsyncChainRunnableWrapper.WORK);
        return this;
    }

    /**
     * 在主线程处理错误
     *
     * @param errorCallback 执行错误的时候的回调
     */
    public AsyncChainLink errorMain(@NonNull AsyncChainErrorCallback errorCallback) {
        mErrorCallback = errorCallback;
        mErrorCallback.setThread(AsyncChainRunnableWrapper.MAIN);
        return this;
    }

    /**
     * 执行串行任务
     * 在任务执行完毕后调用onComp
     */
    public void go() {
        start(null);
    }

    /**
     * 并发执行多个任务
     */
    public void goAll(AsyncChainResultCallBack callBack) {
        startAll(callBack);
    }

    /**
     * 获取异步操作列表
     *
     * @return {@link List<AsyncChainRunnableWrapper>}的列表
     */
    protected List<AsyncChainRunnableWrapper> getRunnableList() {
        return mRunnableList;
    }

    /**
     * 获取异步操作错误时的回调接口
     *
     * @return
     */
    protected AsyncChainErrorCallback getErrorCallback() {
        return mErrorCallback;
    }

    /**
     * 执行下一个操作
     *
     * @param task
     * @param result 传给下个任务的参数
     */
    protected void next(AsyncChainTask task, Object result) {
        if (mRunnableList == null || mRunnableList.size() == 0) {
            return;
        }
        AsyncChainRunnableWrapper wrapper = mRunnableList.getFirst();
        if (wrapper.runnable.runId.equals(task.getRunId())) {
            isStart = false;
            mRunnableList.remove(wrapper);
            start(result);
        }
    }

    /**
     * 开始一个异步操作
     */
    private void start(Object result) {
        if (isStart) {
            return;
        }
        isStart = true;
        if (mRunnableList != null && mRunnableList.size() > 0) {
            AsyncChainManager.getInstance().run(mRunnableList.getFirst(), result);
        }
    }

    private AsyncChainResultCallBack mResultCallBack;

    public AsyncChainResultCallBack getResultCallBack() {
        return mResultCallBack;
    }

    private void startAll(AsyncChainResultCallBack callBack) {
        mResultCallBack = callBack;
        if (mRunnableList != null && mRunnableList.size() > 0) {
            for (AsyncChainRunnableWrapper runnableWrapper : mRunnableList) {
                AsyncChainManager.getInstance().run(runnableWrapper, null);
            }
        }
    }

    /**
     * 页面销毁,或者想手动结束所有任务的时候调用
     */
    public void onDestroy() {
        if (mRunnableList != null) {
            mRunnableList.clear();
        }
    }
}
