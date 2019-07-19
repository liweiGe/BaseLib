package com.kongzue.baseframework.util;

import java.util.Collection;

/**
 * 字符串判空,集合判空等处理
 */
public class NullHelper {
    //网络传输文本判空规则
    public static boolean isNull(String s) {
        return s == null || s.trim().isEmpty() || s.equals("null") || s.equals("(null)");
    }

    /**
     * 判断集合是否非空
     * @param list
     * @return
     */
    public static boolean listIsEmpty(Collection list) {
        return list == null || list.isEmpty();
    }
}
