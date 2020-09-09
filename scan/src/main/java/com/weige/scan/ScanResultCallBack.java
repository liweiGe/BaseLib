package com.weige.scan;

/**
 * @Description: java类作用描述
 * @Author: Liwei.Qiu
 * @Date: 2020/9/9 11:17
 */
public interface ScanResultCallBack {
    void onSuccess(String result);

    void onFail();
}
