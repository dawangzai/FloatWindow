package com.wangzai.library

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.wangzai.library.view.FloatView

class FloatWindow(context: Context) {
    private val windowManager: WindowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var layoutParams: WindowManager.LayoutParams = WindowManager.LayoutParams()

    init {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        }

        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        layoutParams.gravity = Gravity.START or Gravity.TOP
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
    }

    fun bindView(view: FloatView) {
//        view.windowManager = windowManager
//        view.layoutParams = layoutParams
//        windowManager.addView(view, layoutParams)
    }
}