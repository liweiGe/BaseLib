package com.example.commom_view.swipe_back;

/**
 * @author Vea
 * @version 1.0.2
 * @since 2019-01
 * 侧滑返回库,只需要在application 初始化就可以使用,方便,但是在刘海屏手机上如果底部有输入框的情况会有白底的问题
 * 框架链接:
 * https://github.com/xwc520/BusinessComponent/tree/master/view_module_swipeback
 */
public interface ISwipeBack {
    /**
     * 返回 true 可以侧滑
     *
     * @return 返回 true 可以侧滑
     */
    boolean isEnableGesture();

    void setCanSwipe(boolean canSwipe);
}
