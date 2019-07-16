package com.kongzue.baseframework.util;

/**
 * 字符串判空,集合判空等处理
 */
public class OtherHelper {
    //网络传输文本判空规则
    public static boolean isNull(String s) {
        if (s == null || s.trim().isEmpty() || s.equals("null") || s.equals("(null)")) {
            return true;
        }
        return false;
    }
}
