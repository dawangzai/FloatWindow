package com.wangzai.library

import android.content.Context
import android.view.WindowManager
import com.wangzai.library.view.FloatView

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