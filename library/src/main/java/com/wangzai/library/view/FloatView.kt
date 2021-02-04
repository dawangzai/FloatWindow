package com.wangzai.library.view

import android.content.Context
import android.util.DisplayMetrics
import android.view.*
import android.widget.FrameLayout
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import kotlin.math.max
import kotlin.math.min

class FloatView(context: Context) : FrameLayout(context) {

    private var lastX: Float = 0f
    private var lastY: Float = 0f
    private var minOffsetX = 0
    private var minOffsetY = 0
    private var maxOffsetX = 0
    private var maxOffsetY = 0
    private var offsetX: Int = minOffsetX
    private var offsetY: Int = minOffsetY
    private var windowManager: WindowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var layoutParams: WindowManager.LayoutParams = WindowManager.LayoutParams()
    private val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    private val gestureListener = GestureListener()
    private val gestureDetector = GestureDetectorCompat(context, gestureListener)
    private val scroller = OverScroller(context)
    private val flingRunnable = FlingRunnable()

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

    fun setAdapter(adapter: FloatViewAdapter) {
        adapter.onBindView(adapter.onCreateView(LayoutInflater.from(context), this))
        windowManager.addView(this, layoutParams)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.rawX
                lastY = event.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                val currentX = event.rawX
                val currentY = event.rawY
                val dx = (currentX - lastX).toInt()
                val dy = (currentY - lastY).toInt()
                fixValue(dx, dy)
                move()
                lastX = currentX
                lastY = currentY
            }
        }
        return gestureDetector.onTouchEvent(event)
    }

    private fun move() {
        layoutParams.x = offsetX
        layoutParams.y = offsetY
        windowManager.updateViewLayout(this, layoutParams)
    }

    private fun fixValue(dx: Int, dy: Int) {
        val currentOffsetX = max(offsetX + dx, 0)
        val currentOffsetY = max(offsetY + dy, 0)
        maxOffsetX = if (maxOffsetX > 0) maxOffsetX else displayMetrics.widthPixels - width
        maxOffsetY = if (maxOffsetY > 0) maxOffsetY else displayMetrics.heightPixels - height
        offsetX = min(currentOffsetX, maxOffsetX)
        offsetY = min(currentOffsetY, maxOffsetY)
    }

    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            maxOffsetX = if (maxOffsetX > 0) maxOffsetX else displayMetrics.widthPixels - width
            maxOffsetY = if (maxOffsetY > 0) maxOffsetY else displayMetrics.heightPixels - height
            scroller.fling(
                offsetX,
                offsetY,
                velocityX.toInt(),
                velocityY.toInt(),
                minOffsetX,
                maxOffsetX,
                minOffsetY,
                maxOffsetY
            )
            postOnAnimation(flingRunnable)
            return true
        }
    }

    inner class FlingRunnable : Runnable {
        override fun run() {
            if (scroller.computeScrollOffset()) {
                offsetX = scroller.currX
                offsetY = scroller.currY
                move()
                postOnAnimation(this)
            }
        }

    }

    abstract class FloatViewAdapter {

        abstract fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View

        abstract fun onBindView(itemView: View)

    }
}