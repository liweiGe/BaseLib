package com.kongzue.baseframework.crash;

import java.io.File;

/**
 * Author: @Kongzue
 * Github: https://github.com/kongzue/
 * Homepage: http://kongzue.com/
 * Mail: myzcxhh@live.cn
 * CreateTime: 2018/9/30 15:47
 */
public interface OnBugReportListener {
    boolean onCrash(Exception e,File crashLogFile);
}
