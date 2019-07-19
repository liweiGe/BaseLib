package com.kongzue.baseframework.interfaces;

public interface OnPermissionResponseListener {
    void onSuccess(String[] permissions);
    void onFail();
}
