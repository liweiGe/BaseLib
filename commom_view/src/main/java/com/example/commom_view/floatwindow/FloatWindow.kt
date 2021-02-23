package com.example.commom_view.floatwindow

import android.content.Context
import android.view.WindowManager

/**
 * @Description: java类作用描述
 * @Author: Liwei.Qiu
 * @Date: 2021/2/23 10:51
 * 使用方式: https://github.com/dawangzai/FloatWindow
 */
class FloatWindow(context: Context) {
    companion object {
        @Volatile
        private var instance: FloatWindow? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: FloatWindow(context.applicationContext).also { instance = it }
        }
    }

    private var windowManager: WindowManager =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var floatView: FloatView? = null

    fun bindView(view: FloatView) {
        this.floatView = view
        floatView?.addWindow(windowManager)
    }

    fun removeView() {
        floatView?.remove()
        floatView = null
    }
}