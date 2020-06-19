package com.kongzue.baseframework.best;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

class BaseViewModel extends AndroidViewModel {
    public BaseViewModel(@NonNull Application application) {
        super(application);
        stateLiveData = new StateLiveData<>();
    }

    public StateLiveData<Object> stateLiveData;

    public MutableLiveData<Object> getStateLiveData() {
        return stateLiveData;
    }


    /**
     * 请求数据，所有网络操作请使用本方法
     *
     * @param observable
     * @param dataCall
     * @return
     */
    public Disposable request(Observable observable, final DataCall dataCall) {
        //处理所有异常
        Disposable subscribe = observable.subscribeOn(Schedulers.io())//将请求调度到子线程上
                .observeOn(AndroidSchedulers.mainThread())//观察响应结果，把响应结果调度到主线程中处理
                .onErrorReturn((Function<Throwable, Throwable>) throwable -> throwable)
                .subscribe(getConsumer(dataCall), new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        dataCall.fail(ApiException.handleException(e));
                    }
                });
        return subscribe;
    }


    /**
     * @param
     * @return
     * @author: yintao
     * @date: 2020/4/20 11:56 PM
     * @method
     * @description 根据返回值{@link #getResponseType()}灵活改变Consumer或者自己直接重写都可以
     */
    protected <T> Consumer getConsumer(DataCall<T> dataCall) {
        return (Consumer<BDResult<T>>) result -> {
            if (result.getCode() == 0) {
                dataCall.success(result.getData());
            } else {
                dataCall.fail(new ApiException(String.valueOf(result.getCode()), result.getMsg()));
            }
        };

//        if (getResponseType()==RESPONSE_TYPE_SDK_BD) {//如果整个项目中只有一个百度的接口，那么不建议修改基类Presenter，请重写getConsumer方法就可以。
//            return new Consumer<BDResult>() {
//                @Override
//                public void accept(BDResult result) throws Exception {
//                    if (result.getCode()==0) {
//                        dataCall.success(result.getData());
//                    }else{
//                        dataCall.fail(new ApiException(String.valueOf(result.getCode()),result.getMsg()));
//                    }
//                }
//            };
//        }else{
//            return new Consumer<Result>() {
//                @Override
//                public void accept(Result result) throws Exception {
//                    if (result.getStatus().equals("0000")) {
//                        dataCall.success(result.getResult());
//                    }else{
//                        dataCall.fail(new ApiException(result.getStatus(),result.getMessage()));
//                    }
//                }
//            };
//        }
    }
}
