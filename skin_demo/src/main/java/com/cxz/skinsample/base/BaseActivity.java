package com.cxz.skinsample.base;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cxz.skinsample.skin.SkinEngine;
import com.cxz.skinsample.skin.SkinFactory;

import java.io.File;

/**
 * @author chenxz
 * @date 2019/3/9
 * @desc
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();

    protected static String[] skins = new String[]{"skin.apk", "skin2.apk"};

    protected static String mCurrentSkin = null;

    private SkinFactory mSkinFactory;

    private boolean ifAllowChangeSkin = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // TODO: 关键点1：hook（劫持）系统创建view的过程
        if (ifAllowChangeSkin) {
            mSkinFactory = new SkinFactory();
            mSkinFactory.setDelegate(getDelegate());
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            Log.e(TAG, "layoutInflaterTag" + layoutInflater.toString());
            layoutInflater.setFactory2(mSkinFactory);
        }
        super.onCreate(savedInstanceState);
    }

    /**
     * 创建完成但是还不可以交互
     */
    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 等控件创建完成并且可交互之后，再换肤
     */
    @Override
    protected void onResume() {
        super.onResume();

        Log.e(TAG, "changeTag：" + ((null == mCurrentSkin) ? "currentSkin是空" : mCurrentSkin));

        if (null != mCurrentSkin)
            changeSkin(mCurrentSkin); // 换肤操作必须在setContentView之后
    }

    /**
     * 做一个切换方法
     *
     * @return
     */
    protected String getPath() {
        String path;
        if (null == mCurrentSkin) {
            path = skins[0];
        } else if (skins[0].equals(mCurrentSkin)) {
            path = skins[1];
        } else if (skins[1].equals(mCurrentSkin)) {
            path = skins[0];
        } else {
            return "unknown skin";
        }
        return path;
    }

    /**
     * 换肤方法
     *
     * @param path
     */
    protected void changeSkin(String path) {
        if (ifAllowChangeSkin) {
            File skinFile = new File(Environment.getExternalStorageDirectory(), path);
            SkinEngine.getInstance().load(skinFile.getAbsolutePath());
            mSkinFactory.changeSkin();
            mCurrentSkin = path;
        }
    }

}
