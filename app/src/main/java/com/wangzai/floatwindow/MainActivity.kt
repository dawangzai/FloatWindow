package com.wangzai.floatwindow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wangzai.library.view.FloatView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val floatView = FloatView(this)
        floatView.setAdapter(SimpleAdapter())
    }
}