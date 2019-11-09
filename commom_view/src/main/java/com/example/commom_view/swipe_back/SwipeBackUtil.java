
package com.example.commom_view.swipe_back;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Method;

/**
 * @version 0.0.9
 * @since 2019-1
 */
final class SwipeBackUtil {


    /**
     * 获取底部导航栏高度
     */
    public static int getNavigationBarHeight(Activity activity) {
        int navigationBarHeight = 0;
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier(isPortrait(activity) ? "navigation_bar_height" : "navigation_bar_height_landscape", "dimen", "android");
        if (resourceId > 0 && checkDeviceHasNavigationBar(activity) && isNavigationBarVisible(activity)) {
            navigationBarHeight = resources.getDimensionPixelSize(resourceId);
        }
        String manufacturer = Build.MANUFACTURER;
        //加这个判断是为了怕印象面太广,针对三星 s8+ 9.0 的搞一下就好了,怕出问题
        if ("samsung".equalsIgnoreCase(manufacturer) && Build.VERSION.SDK_INT == Build.VERSION_CODES.P) {
            return getSanxingBottomBarHeight(activity);
        }
        return navigationBarHeight;
    }

    /**
     * 是否为竖屏
     */
    public static boolean isPortrait(Activity activity) {
        return activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 手机具有底部导航栏时，底部导航栏是否可见
     */
    private static boolean isNavigationBarVisible(Activity activity) {
//        View decorView = activity.getWindow().getDecorView();
//        return (decorView.getSystemUiVisibility() & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) != 2;

        boolean show = false;
        Display display = activity.getWindow().getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getRealSize(point);
        View decorView = activity.getWindow().getDecorView();
        Configuration conf = activity.getResources().getConfiguration();
        if (Configuration.ORIENTATION_LANDSCAPE == conf.orientation) {
            View contentView = decorView.findViewById(android.R.id.content);
            if (contentView != null) {
                show = (point.x != contentView.getWidth());
            }
        } else {
            Rect rect = new Rect();
            decorView.getWindowVisibleDisplayFrame(rect);
            show = (rect.bottom != point.y);
        }
        return show;
    }

    /**
     * 检测是否具有底部导航栏
     */
    private static boolean checkDeviceHasNavigationBar(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        display.getRealMetrics(realDisplayMetrics);
        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;
        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
    }

    /**
     * 狗叼三星出的啥子全面屏手势,操蛋的,不能用常规的方法获取导航栏高度
     * @param activity
     * @return
     */
    private static int getSanxingBottomBarHeight(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        display.getRealMetrics(realDisplayMetrics);
        int realHeight = realDisplayMetrics.heightPixels;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        int displayHeight = displayMetrics.heightPixels;
        return realHeight - displayHeight;
    }


    /**
     * 获取屏幕高度，包括底部导航栏
     */
    static int getRealScreenHeight(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    /**
     * 获取屏幕宽度，不包括右侧导航栏
     */
    static int getRealScreenWidth(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * 关闭activity中打开的键盘
     *
     * @param activity
     */
    static void closeKeyboard(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    static void d(String message) {
//        if (BuildConfig.DEBUG) {
//            Log.d("SwipeBackX", message);
//        }
    }

    /**
     * 转为不透明
     *
     * @param activity activity
     */
    static void convertActivityFromTranslucent(Activity activity) {
        try {
            TypedArray typedArray = activity.getTheme().obtainStyledAttributes(new int[]{
                    android.R.attr.windowIsTranslucent
            });
            boolean translucent = typedArray.getBoolean(0, false);
            if (translucent) {
                Method method = Activity.class.getDeclaredMethod("convertFromTranslucent");
                method.setAccessible(true);
                method.invoke(activity);
            }
            typedArray.recycle();
        } catch (Throwable t) {
            // pass
        }
    }
}
