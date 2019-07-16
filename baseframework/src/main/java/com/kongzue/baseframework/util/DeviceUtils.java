package com.kongzue.baseframework.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

import androidx.annotation.ColorRes;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.kongzue.baseframework.BaseFrameworkSettings;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import static com.kongzue.baseframework.BaseFrameworkSettings.DEBUGMODE;
import static com.kongzue.baseframework.BaseFrameworkSettings.setNavigationBarHeightZero;

public class DeviceUtils {
    //使用默认浏览器打开链接
    public static boolean openUrl(Context context, String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            if (DEBUGMODE) e.printStackTrace();
            return false;
        }
    }

    //打开指定App
    public static boolean openApp(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        if (isInstallApp(context, packageName)) {
            try {
                Intent intent = packageManager.getLaunchIntentForPackage(packageName);
                context.startActivity(intent);
                return true;
            } catch (Exception e) {
                if (DEBUGMODE) e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    //检测App是否已安装
    public static boolean isInstallApp(Context context, String packageName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    /**
     * 检测所有的权限是否都已授权
     *
     * @param permissions
     * @return
     */
    public boolean checkPermissions(Context context,String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    //单权限检查
    public boolean checkPermissions(Context context,String permission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (ContextCompat.checkSelfPermission(context, permission) !=
                PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     */
    private List<String> getDeniedPermissions(Context context,String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) !=
                    PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {
                needRequestPermissionList.add(permission);
            }
        }
        return needRequestPermissionList;
    }
    /**
     * 请求权限
     * <p>
     * 警告：此处除了用户拒绝外，唯一可能出现无法获取权限或失败的情况是在AndroidManifest.xml中未声明权限信息
     * Android6.0+即便需要动态请求权限（重点）但不代表着不需要在AndroidManifest.xml中进行声明。
     *
     * @param permissions                  请求的权限
     * @param onPermissionResponseListener 回调监听器
     */
    private int REQUEST_CODE_PERMISSION = 0x00099;
    public void requestPermission(Context context,String[] permissions, OnPermissionResponseListener onPermissionResponseListener) {
        if (checkPermissions(context,permissions)) {
            if (onPermissionResponseListener != null)
                onPermissionResponseListener.onSuccess(permissions);
        } else {
            List<String> needPermissions = getDeniedPermissions(context,permissions);
            ActivityCompat.requestPermissions((Activity) context, needPermissions.toArray(new String[needPermissions.size()]), REQUEST_CODE_PERMISSION);
        }
    }

    //获取IMEI (请预先在 AndroidManifest.xml 中声明：<uses-permission android:name="android.permission.READ_PHONE_STATE"/>)
    @SuppressLint({"WrongConstant", "MissingPermission"})
    public String getIMEI(Context context) {
        String result = null;
        try {
            if (checkPermissions(context,new String[]{"android.permission.READ_PHONE_STATE"})) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    if (Build.VERSION.SDK_INT >= 26) {
                        try {
                            Method method = telephonyManager.getClass().getMethod("getImei", new Class[0]);
                            method.setAccessible(true);
                            result = (String) method.invoke(telephonyManager, new Object[0]);
                        } catch (Exception e) {
                        }
                        if (OtherHelper.isNull(result)) {
                            result = telephonyManager.getDeviceId();
                        }
                    } else {
                        result = telephonyManager.getDeviceId();
                    }
                }
            } else {
                requestPermission(context,new String[]{"android.permission.READ_PHONE_STATE"}, new OnPermissionResponseListener() {
                    @Override
                    public void onSuccess(String[] permissions) {
                        getIMEI(context);
                    }

                    @Override
                    public void onFail() {
                        if (BaseFrameworkSettings.DEBUGMODE)
                            Log.e(">>>", "getIMEI(): 失败，用户拒绝授权READ_PHONE_STATE");
                    }
                });
            }
        } catch (Exception e) {
            if (BaseFrameworkSettings.DEBUGMODE) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public String getAndroidId(Context context) {
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidID;
    }

    //获取Mac地址 (请预先在 AndroidManifest.xml 中声明：<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>)
    public String getMacAddress() {
        String macAddress = null;
        StringBuffer buf = new StringBuffer();
        NetworkInterface networkInterface = null;
        try {
            networkInterface = NetworkInterface.getByName("eth1");
            if (networkInterface == null) {
                networkInterface = NetworkInterface.getByName("wlan0");
            }
            if (networkInterface == null) {
                return "02:00:00:00:00:02";
            }
            byte[] addr = networkInterface.getHardwareAddress();
            for (byte b : addr) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            macAddress = buf.toString();
        } catch (SocketException e) {
            e.printStackTrace();
            return "02:00:00:00:00:02";
        }
        return macAddress;
    }

    //启动当前应用设置页面
    public static void startAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    //获取状态栏的高度
    public static int getStatusBarHeight(Context context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //获取屏幕宽度
    public static int getDisplayWidth(Context context) {
        if (context instanceof Activity) {
            Display disp = ((Activity) context).getWindowManager().getDefaultDisplay();
            Point outP = new Point();
            disp.getSize(outP);
            return outP.x;
        }
        return 0;
    }

    //获取屏幕可用部分高度（屏幕高度-状态栏高度-屏幕底栏高度）
    public static int getDisplayHeight(Context context) {
        if (context instanceof Activity) {
            Display disp = ((Activity) context).getWindowManager().getDefaultDisplay();
            Point outP = new Point();
            disp.getSize(outP);
            return outP.y;
        }
        return 0;
    }

    //获取底栏高度
    public static int getNavbarHeight(Context context) {
        if (setNavigationBarHeightZero) return 0;
        int resourceId = 0;
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            return context.getResources().getDimensionPixelSize(resourceId);
        } else
            return 0;
    }

    //获取真实的屏幕高度，注意判断非0
    public static int getRootHeight(Context context) {
        int diaplayHeight = 0;
        if (context instanceof Activity) {
            Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
            Point point = new Point();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealSize(point);
                diaplayHeight = point.y;
            } else {
                DisplayMetrics dm = new DisplayMetrics();
                ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
                diaplayHeight = dm.heightPixels; //得到高度```
            }
        }

        return diaplayHeight;
    }

    //用于进行dip和px转换
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //用于进行px和dip转换
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    //支持最低SDK的getColor方法
    public static int getColorS(Context context, @ColorRes int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getResources().getColor(id, context.getTheme());
        } else {
            return context.getResources().getColor(id);
        }
    }
}
