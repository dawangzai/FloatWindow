package com.wangzai.floatwindow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.wangzai.library.FloatWindow
import com.wangzai.library.view.FloatView

class SimpleAdapter : FloatView.FloatViewAdapter {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.layout_test, container)
    }

    override fun onBindView(itemView: View) {
        itemView.findViewById<ImageView>(R.id.iv_close).setOnClickListener {
            FloatWindow.getInstance(itemView.context).removeView()
        }
    }
}