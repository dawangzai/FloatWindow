package com.wangzai.floatwindow

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.wangzai.library.FloatWindow
import com.wangzai.library.view.FloatView
import kotlinx.android.synthetic.main.activity_main.*

const val REQ_CODE_1000 = 1000

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                permissionCheck()
            } else {
                startActivity(Intent(this, TestActivity::class.java))
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE_1000) {
            if (Settings.canDrawOverlays(this)) {
                startActivity(Intent(this, TestActivity::class.java))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        FloatWindow.getInstance(this).removeView()
    }

    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                showFloat()
                return
            }
        }
        showFloat()
    }

    private fun showFloat() {
        val floatView = FloatView(this).apply {
            setAdapter(SimpleAdapter())
            setOnClickListener {
                startActivity(Intent(this@MainActivity, MainActivity::class.java))
            }
        }
        FloatWindow.getInstance(this).bindView(floatView)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun permissionCheck() {
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            intent.data = Uri.parse("package:$packageName")
            startActivityForResult(intent, REQ_CODE_1000)
        } else {
            startActivity(Intent(this, TestActivity::class.java))
        }
    }
}