package com.kongzue.baseframework.base;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.SharedElementCallback;

import com.kongzue.baseframework.R;
import com.kongzue.baseframework.interfaces.DarkNavigationBarTheme;
import com.kongzue.baseframework.interfaces.DarkStatusBarTheme;
import com.kongzue.baseframework.interfaces.FullScreen;
import com.kongzue.baseframework.interfaces.GlobalLifeCircleListener;
import com.kongzue.baseframework.interfaces.Layout;
import com.kongzue.baseframework.interfaces.LifeCircleListener;
import com.kongzue.baseframework.interfaces.NavigationBarBackgroundColor;
import com.kongzue.baseframework.interfaces.SwipeBack;
import com.kongzue.baseframework.swipeback.util.SwipeBackActivityBase;
import com.kongzue.baseframework.swipeback.util.SwipeBackActivityHelper;
import com.kongzue.baseframework.swipeback.util.SwipeBackLayout;
import com.kongzue.baseframework.swipeback.util.SwipeBackUtil;
import com.kongzue.baseframework.toast.Toaster;
import com.kongzue.baseframework.util.AppManager;
import com.kongzue.baseframework.util.activityParam.JumpParameter;
import com.kongzue.baseframework.util.LanguageUtil;
import com.kongzue.baseframework.interfaces.OnJumpResponseListener;
import com.kongzue.baseframework.interfaces.OnPermissionResponseListener;
import com.kongzue.baseframework.util.activityParam.ParameterCache;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static com.kongzue.baseframework.BaseFrameworkSettings.DEBUGMODE;

/**
 * @Version: 6.5.6
 * @Author: Kongzue
 * @github: https://github.com/kongzue/BaseFrameworkSettings
 * @link: http://kongzue.com/
 * @describe: 自动化代码流水线作业，以及对原生安卓、MIUI、flyme的透明状态栏显示灰色图标文字的支持，同时提供一些小工具简化开发难度，详细说明文档：https://github.com/kongzue/BaseFramework
 */

public abstract class BaseActivity extends AppCompatActivity implements SwipeBackActivityBase {
    
    private LifeCircleListener lifeCircleListener;                          //快速管理生命周期
    private static GlobalLifeCircleListener globalLifeCircleListener;       //全局生命周期
    
    public boolean isActive = false;                                        //当前Activity是否处于前台
    public boolean isAlive = false;                                         //当前Activity是否处于存活状态
    
    public OnJumpResponseListener onResponseListener;                       //jump跳转回调
    private OnPermissionResponseListener onPermissionResponseListener;      //权限申请回调
    
    public BaseActivity me = this;
    
    private boolean isFullScreen = false;
    private boolean darkStatusBarThemeValue = false;
    private boolean darkNavigationBarThemeValue = false;
    private int navigationBarBackgroundColorValue = Color.BLACK;
    private int layoutResId = android.R.layout.list_content;
    
    private Bundle savedInstanceState;
    private SwipeBackActivityHelper mHelper;
    
    //不再推荐重写onCreate创建Activity，新版本推荐直接在Activity上注解：@Layout(你的layout资源id)
    @Deprecated
    protected void onCreate(Bundle savedInstanceState, int layoutResId) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;

        
        initAttributes();
        
        setContentView(layoutResId);
        if (isFullScreen) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
        } else {
            setTranslucentStatus(true);
        }
        
        isAlive = true;
        
        AppManager.getInstance().pushActivity(me);
        
        initViews();
        initDatas(getParameter());
        setEvents();
        
        if (lifeCircleListener != null) lifeCircleListener.onCreate();
        if (globalLifeCircleListener != null)
            globalLifeCircleListener.onCreate(me, me.getClass().getName());
    }
    
    @Override
    @Deprecated
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        this.savedInstanceState = savedInstanceState;
        
        isAlive = true;
        
        initAttributes();
        if (layoutResId == android.R.layout.list_content) {
            Log.e("警告！", "请在您的Activity的Class上注解：@Layout(你的layout资源id)");
            return;
        }
        
        setContentView(layoutResId);
        if (isFullScreen) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
//        else {
//            setTranslucentStatus(true);
//        }
        AppManager.getInstance().pushActivity(me);
        
        initViews();
        initDatas(getParameter());
        setEvents();
        
        if (lifeCircleListener != null) lifeCircleListener.onCreate();
        if (globalLifeCircleListener != null)
            globalLifeCircleListener.onCreate(me, me.getClass().getName());
    }
    
    public void setLifeCircleListener(LifeCircleListener lifeCircleListener) {
        this.lifeCircleListener = lifeCircleListener;
    }
    
    //加载注解设置
    private void initAttributes() {
        try {
            FullScreen fullScreen = getClass().getAnnotation(FullScreen.class);
            SwipeBack swipeBack = getClass().getAnnotation(SwipeBack.class);
            Layout layout = getClass().getAnnotation(Layout.class);
            DarkNavigationBarTheme darkNavigationBarTheme = getClass().getAnnotation(DarkNavigationBarTheme.class);
            DarkStatusBarTheme darkStatusBarTheme = getClass().getAnnotation(DarkStatusBarTheme.class);
            NavigationBarBackgroundColor navigationBarBackgroundColor = getClass().getAnnotation(NavigationBarBackgroundColor.class);
            if (fullScreen != null) {
                isFullScreen = fullScreen.value();
                if (isFullScreen) requestWindowFeature(Window.FEATURE_NO_TITLE);
            }
            mHelper.onActivityCreate();
            if (swipeBack != null) {
                setSwipeBackEnable(swipeBack.value());
            } else {
                setSwipeBackEnable(false);
            }
            if (layout != null) {
                if (layout.value() != -1) layoutResId = layout.value();
            }
            if (darkStatusBarTheme != null) darkStatusBarThemeValue = darkStatusBarTheme.value();
            if (darkNavigationBarTheme != null)
                darkNavigationBarThemeValue = darkNavigationBarTheme.value();
            if (navigationBarBackgroundColor != null) {
                if (navigationBarBackgroundColor.a() != -1 && navigationBarBackgroundColor.r() != -1 && navigationBarBackgroundColor.g() != -1 && navigationBarBackgroundColor.b() != -1) {
                    navigationBarBackgroundColorValue = Color.argb(navigationBarBackgroundColor.a(), navigationBarBackgroundColor.r(), navigationBarBackgroundColor.g(), navigationBarBackgroundColor.b());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void finish() {
        AppManager.getInstance().killActivity(me);
    }
    
    public void finishActivity() {
        super.finish();
    }
    
    //可被重写的接口
    public abstract void initViews();
    
    public abstract void initDatas(JumpParameter paramer);
    
    public abstract void setEvents();
    
    public void setDarkStatusBarTheme(boolean value) {
        darkStatusBarThemeValue = value;
        setTranslucentStatus(true);
    }
    
    public void setDarkNavigationBarTheme(boolean value) {
        darkNavigationBarThemeValue = value;
        setTranslucentStatus(true);
    }
    
    public void setNavigationBarBackgroundColor(@ColorInt int color) {
        navigationBarBackgroundColorValue = color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(navigationBarBackgroundColorValue);
        }
    }
    
    public void setNavigationBarBackgroundColor(int a, int r, int g, int b) {
        navigationBarBackgroundColorValue = Color.argb(a, r, g, b);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(navigationBarBackgroundColorValue);
        }
    }
    
    //状态栏主题
    protected void setTranslucentStatus(boolean on) {
        if (isMIUI()) setStatusBarDarkModeInMIUI(darkStatusBarThemeValue, this);
        if (isFlyme()) setStatusBarDarkIconInFlyme(getWindow(), darkStatusBarThemeValue);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                                      | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            
            if (darkStatusBarThemeValue) {
                if (darkNavigationBarThemeValue) {
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                                                        | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                                                                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                                                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                                                        | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                    );
                } else {
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                                                        | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                                                                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                                                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    );
                }
            } else {
                if (darkNavigationBarThemeValue) {
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                                                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                                                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                                                        | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                    );
                } else {
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                                                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                                                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    );
                }
            }
            
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams winParams = window.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            window.setAttributes(winParams);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setNavigationBarColor(navigationBarBackgroundColorValue);
        }
    }
    
    private void setStatusBarDarkModeInMIUI(boolean darkmode, Activity activity) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private boolean setStatusBarDarkIconInFlyme(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {
                Log.e("MeiZu", "setStatusBarDarkIcon: failed");
            }
        }
        return result;
    }
    
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    
    //MIUI判断
    public static boolean isMIUI() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } catch (final IOException e) {
            return false;
        }
    }
    
    //Flyme判断
    public static boolean isFlyme() {
        try {
            final Method method = Build.class.getMethod("hasSmartBar");
            
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }

    public void log(String s) {
        Log.e("base", "log: "+s);
    }

    public static class BuildProperties {
        
        private final Properties properties;
        
        private BuildProperties() throws IOException {
            properties = new Properties();
            properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
        }
        
        public boolean containsKey(final Object key) {
            return properties.containsKey(key);
        }
        
        public boolean containsValue(final Object value) {
            return properties.containsValue(value);
        }
        
        public Set<Map.Entry<Object, Object>> entrySet() {
            return properties.entrySet();
        }
        
        public String getProperty(final String name) {
            return properties.getProperty(name);
        }
        
        public String getProperty(final String name, final String defaultValue) {
            return properties.getProperty(name, defaultValue);
        }
        
        public boolean isEmpty() {
            return properties.isEmpty();
        }
        
        public Enumeration<Object> keys() {
            return properties.keys();
        }
        
        public Set<Object> keySet() {
            return properties.keySet();
        }
        
        public int size() {
            return properties.size();
        }
        
        public Collection<Object> values() {
            return properties.values();
        }
        
        public static BuildProperties newInstance() throws IOException {
            return new BuildProperties();
        }
    }
    
    protected final static String NULL = "";
    private Toast toast;
    
    public void runOnMain(Runnable runnable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isAlive) {
                    runnable.run();
                }
            }
        });
    }
    
    public void runOnMainDelayed(Runnable runnable, long time) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnMain(runnable);
            }
        }, time);
    }
    
    public void runDelayed(Runnable runnable, long time) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }, time);
    }
    
    //简易吐司
    public void toast(final Object obj) {
        try {
            runOnMain(new Runnable() {
                @Override
                public void run() {
                    if (toast == null)
                        toast = Toast.makeText(BaseActivity.this, NULL, Toast.LENGTH_SHORT);
                    toast.setText(obj.toString());
                    toast.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void toastS(final Object obj) {
        Toaster.build(me).show(obj.toString());
    }

    //软键盘打开与收起
    public void showIME(boolean show, EditText editText) {
        if (editText == null) {
            return;
        }
        if (show) {
            editText.requestFocus();
            editText.setFocusableInTouchMode(true);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
        } else {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }
    
    //兼容用
    @Deprecated
    public void setIMMStatus(boolean show, EditText editText) {
        showIME(show, editText);
    }

    
    //位移动画
    public ObjectAnimator moveAnimation(Object obj, String perference, float aimValue, long time, long delay) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(obj, perference, aimValue);
        objectAnimator.setDuration(time);
        objectAnimator.setStartDelay(delay);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            objectAnimator.setAutoCancel(true);
        }
        objectAnimator.start();
        return objectAnimator;
    }
    
    public ObjectAnimator moveAnimation(Object obj, String perference, float aimValue, long time) {
        return moveAnimation(obj, perference, aimValue, time, 0);
    }
    
    public ObjectAnimator moveAnimation(Object obj, String perference, float aimValue) {
        return moveAnimation(obj, perference, aimValue, 300, 0);
    }
    
    //复制文本到剪贴板
    public boolean copy(String s) {
        if (isNull(s)) {
            return false;
        }
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData mClipData = ClipData.newPlainText("Label", s);
        cm.setPrimaryClip(mClipData);
        return true;
    }
    
    //网络传输文本判空规则
    public static boolean isNull(String s) {
        if (s == null || s.trim().isEmpty() || s.equals("null") || s.equals("(null)")) {
            return true;
        }
        return false;
    }
    
    //更好用的跳转方式
    public boolean jump(Class<?> cls) {
        try {
            startActivity(new Intent(me, cls));
        } catch (Exception e) {
            if (DEBUGMODE) e.printStackTrace();
            return false;
        }
        return true;
    }
    
    //可以传任何类型参数的跳转方式
    public boolean jump(Class<?> cls, JumpParameter jumpParameter) {
        try {
            if (jumpParameter != null)
                ParameterCache.getInstance().set(cls.getName(), jumpParameter);
            startActivity(new Intent(me, cls));
        } catch (Exception e) {
            if (DEBUGMODE) e.printStackTrace();
            return false;
        }
        return true;
    }
    
    //带返回值的跳转
    public boolean jump(Class<?> cls, OnJumpResponseListener onResponseListener) {
        return jump(cls, null, onResponseListener);
    }
    
    //带参数和返回值跳转
    public boolean jump(Class<?> cls, JumpParameter jumpParameter, OnJumpResponseListener onResponseListener) {
        try {
            startActivity(new Intent(me, cls));
            ParameterCache.getInstance().cleanResponse(me.getClass().getName());
            if (jumpParameter == null) jumpParameter = new JumpParameter();
            ParameterCache.getInstance().set(cls.getName(), jumpParameter
                    .put("needResponse", true)
                    .put("responseClassName", me.getClass().getName())
            );
            this.onResponseListener = onResponseListener;
        } catch (Exception e) {
            if (DEBUGMODE) e.printStackTrace();
            return false;
        }
        return true;
    }
    
    //可使用共享元素的跳转方式
    public boolean jump(Class<?> cls, View transitionView) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                me.setExitSharedElementCallback(new SharedElementCallback() {
                    @Override
                    public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                        super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
                        for (View view : sharedElements) {
                            view.setVisibility(View.VISIBLE);
                        }
                    }
                });
                startActivity(new Intent(me, cls), ActivityOptions.makeSceneTransitionAnimation(me, transitionView, transitionView.getTransitionName()).toBundle());
            } else {
                startActivity(new Intent(me, cls));
            }
        } catch (Exception e) {
            if (DEBUGMODE) e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean jump(Class<?> cls, View... transitionViews) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                me.setExitSharedElementCallback(new SharedElementCallback() {
                    @Override
                    public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                        super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
                        for (View view : sharedElements) {
                            view.setVisibility(View.VISIBLE);
                        }
                    }
                });
                
                Pair<View, String>[] pairs = new Pair[transitionViews.length];
                int i = 0;
                for (View tv : transitionViews) {
                    Pair<View, String> pair = new Pair<>(tv, tv.getTransitionName());
                    pairs[i] = pair;
                    i++;
                }
                startActivity(new Intent(me, cls), ActivityOptions.makeSceneTransitionAnimation(me, pairs).toBundle());
            } else {
                startActivity(new Intent(me, cls));
            }
        } catch (Exception e) {
            if (DEBUGMODE) e.printStackTrace();
            return false;
        }
        return true;
    }
    
    //可使用共享元素的带参数跳转方式
    public boolean jump(Class<?> cls, JumpParameter jumpParameter, View transitionView) {
        try {
            if (jumpParameter != null)
                ParameterCache.getInstance().set(cls.getName(), jumpParameter);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                me.setExitSharedElementCallback(new SharedElementCallback() {
                    @Override
                    public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                        super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
                        for (View view : sharedElements) {
                            view.setVisibility(View.VISIBLE);
                        }
                    }
                });
                startActivity(new Intent(me, cls), ActivityOptions.makeSceneTransitionAnimation(me, transitionView, transitionView.getTransitionName()).toBundle());
            } else {
                startActivity(new Intent(me, cls));
            }
        } catch (Exception e) {
            if (DEBUGMODE) e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean jump(Class<?> cls, JumpParameter jumpParameter, View... transitionViews) {
        try {
            if (jumpParameter != null)
                ParameterCache.getInstance().set(cls.getName(), jumpParameter);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                me.setExitSharedElementCallback(new SharedElementCallback() {
                    @Override
                    public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                        super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
                        for (View view : sharedElements) {
                            view.setVisibility(View.VISIBLE);
                        }
                    }
                });
                
                Pair<View, String>[] pairs = new Pair[transitionViews.length];
                int i = 0;
                for (View tv : transitionViews) {
                    Pair<View, String> pair = new Pair<>(tv, tv.getTransitionName());
                    pairs[i] = pair;
                    i++;
                }
                
                startActivity(new Intent(me, cls), ActivityOptions.makeSceneTransitionAnimation(me, pairs).toBundle());
            } else {
                startActivity(new Intent(me, cls));
            }
        } catch (Exception e) {
            if (DEBUGMODE) e.printStackTrace();
            return false;
        }
        return true;
    }
    
    //可使用共享元素的带返回值的跳转
    public boolean jump(Class<?> cls, OnJumpResponseListener onResponseListener, View transitionView) {
        return jump(cls, null, onResponseListener, transitionView);
    }
    
    //可使用共享元素的带参数和返回值跳转
    public boolean jump(Class<?> cls, JumpParameter jumpParameter, OnJumpResponseListener onResponseListener, View transitionView) {
        try {
            ParameterCache.getInstance().cleanResponse(me.getClass().getName());
            if (jumpParameter == null) jumpParameter = new JumpParameter();
            ParameterCache.getInstance().set(cls.getName(), jumpParameter
                    .put("needResponse", true)
                    .put("responseClassName", me.getClass().getName())
            );
            this.onResponseListener = onResponseListener;
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                me.setExitSharedElementCallback(new SharedElementCallback() {
                    @Override
                    public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                        super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
                        for (View view : sharedElements) {
                            view.setVisibility(View.VISIBLE);
                        }
                    }
                });
                startActivity(new Intent(me, cls), ActivityOptions.makeSceneTransitionAnimation(me, transitionView, transitionView.getTransitionName()).toBundle());
            } else {
                startActivity(new Intent(me, cls));
                
            }
        } catch (Exception e) {
            if (DEBUGMODE) e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public void jumpAnim(int enterAnim, int exitAnim) {
        int version = Integer.valueOf(android.os.Build.VERSION.SDK);
        if (version > 5) {
            overridePendingTransition(enterAnim, exitAnim);
        }
    }
    
    //目标Activity：设定要返回的数据
    public void setResponse(JumpParameter jumpParameter) {
        ParameterCache.getInstance().setResponse((String) getParameter().get("responseClassName"), jumpParameter);
    }
    
    //目标Activity：设定要返回的数据，写法2
    public void returnParameter(JumpParameter parameter) {
        setResponse(parameter);
    }
    
    //获取跳转参数
    public JumpParameter getParameter() {
        JumpParameter jumpParameter = ParameterCache.getInstance().get(me.getClass().getName());
        if (jumpParameter == null) jumpParameter = new JumpParameter();
        return jumpParameter;
    }
    
    @Override
    protected void onResume() {
        isActive = true;
        if (onResponseListener != null) {
            onResponseListener.OnResponse(ParameterCache.getInstance().getResponse(me.getClass().getName()));
            onResponseListener = null;
        }
        super.onResume();
        if (lifeCircleListener != null) lifeCircleListener.onResume();
        if (globalLifeCircleListener != null)
            globalLifeCircleListener.onResume(me, me.getClass().getName());
    }
    
    @Override
    protected void onPause() {
        if (Toaster.isSupportToast) {
            Toaster.cancel();
        }
        isActive = false;
        if (lifeCircleListener != null) lifeCircleListener.onPause();
        if (globalLifeCircleListener != null)
            globalLifeCircleListener.onPause(me, me.getClass().getName());
        super.onPause();
    }
    
    @Override
    protected void onDestroy() {
        isAlive = false;

        if (getParameter() != null) getParameter().cleanAll();
        AppManager.getInstance().deleteActivity(me);
        if (lifeCircleListener != null) lifeCircleListener.onDestroy();
        if (globalLifeCircleListener != null)
            globalLifeCircleListener.onDestroy(me, me.getClass().getName());
        super.onDestroy();
    }

    
    public static GlobalLifeCircleListener getGlobalLifeCircleListener() {
        return globalLifeCircleListener;
    }
    
    public static void setGlobalLifeCircleListener(GlobalLifeCircleListener globalLifeCircleListener) {
        BaseActivity.globalLifeCircleListener = globalLifeCircleListener;
    }
    
    public static boolean DEBUGMODE() {
        return DEBUGMODE;
    }
    

    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }

    
    public void restartMe() {
        finish();
        jump(me.getClass());
        jumpAnim(R.anim.fade, R.anim.hold);
    }
    
    //以下不用管系列————
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }
    
    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }
    
    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }
    
    @Override
    public void scrollToFinishActivity() {
        SwipeBackUtil.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
    
    @Override
    protected void attachBaseContext(Context c) {
        super.attachBaseContext(LanguageUtil.wrap(c));
    }

    
    public View getRootView() {
        return getWindow().getDecorView().findViewById(android.R.id.content);
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (globalLifeCircleListener != null)
            globalLifeCircleListener.WindowFocus(me, me.getClass().getName(), hasFocus);
    }
}