package com.lizw.ui_demos.gesture

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lizw.ui_demos.R
import kotlin.math.abs

class GestureActivity : AppCompatActivity() {

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gesture)

        val tvTest = findViewById<TextView>(R.id.tv_test)
        tvTest.setOnTouchListener(MyOnTouchListener(this))

        tvTest.isClickable = true
        tvTest.isLongClickable = true
        tvTest.isFocusable = true
    }

    class MyOnTouchListener(context: Context) : View.OnTouchListener {
        // way1
        private var gestureDetector: GestureDetector = GestureDetector(context, MyGestureListener())

        // way2 这种可以根据需求，选择重写想要的方法。上面的方式，每个接口中的方法都要自己实现
//        private var gestureDetector: GestureDetector = GestureDetector(MySimpleGestureListener())

        init {
            gestureDetector.setOnDoubleTapListener(MyOnDoubleTapListener())
        }

        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(v: View?, event: MotionEvent): Boolean {
            return gestureDetector.onTouchEvent(event)
        }

        class MyGestureListener : GestureDetector.OnGestureListener {
            companion object {
                private const val TAG = "GestureActivity"

                private const val FLING_MIN_DISTANCE = 100
                private const val FLING_MIN_VELOCITY = 200
            }

            // 按下屏幕触发
            // Q：返回值的含义
            override fun onDown(e: MotionEvent): Boolean {
                Log.i(TAG, "onDown,e = $e")
                return false
            }

            // 按下一定时间，并且没有松开或拖动时，触发。在 onDown 和 onLongPress之间触发
            override fun onShowPress(e: MotionEvent) {
                Log.i(TAG, "onShowPress,e = $e")
            }

            // 单击触发，单击后立刻抬起触发。如果除了down以外还有其他操作，就不会触发了
            // 快速点击一次的触发顺序：onDown onSingleTapUp onSingleTapConfirmed
            // 慢速点击一次的触发顺序：onDown onShowPress onSingleTapUp onSingleTapConfirmed
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                Log.i(TAG, "onSingleTapUp,e = $e")
                return false
            }

            // 拖动View，或者滑动屏幕。会多次触发次方法，在MOVE事件发生时会触发。
            // 滑动后松开的触发顺序：onDown onScroll onScroll onScroll ... onFling
            override fun onScroll(
                e1: MotionEvent?,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                Log.i(
                    TAG,
                    "onScroll,e1 = $e1 , e2 = $e2 , distanceX = $distanceX , distanceY = $distanceY"
                )
                return false
            }

            // 长按一定时间触发
            override fun onLongPress(e: MotionEvent) {
                Log.i(TAG, "onLongPress,e = $e")
            }

            // 滑屏，按下并快速移动后松开，由一个 ACTION_DOWN 多个ACTION_MOVE ACTION_UP触发
            // 一定要快速滑动才会触发，慢慢的拖动是不会触发 onFling 的
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                if (e1 == null) {
                    return false
                }
                Log.i(
                    TAG,
                    "onFling,e1 = $e1 , \n e2 = $e2 , velocityX = $velocityX , velocityY = $velocityY"
                )
                val fingerLeft = e1.x - e2.x > FLING_MIN_DISTANCE
                // 手指右滑
                val fingerRight = e2.x - e1.x > FLING_MIN_DISTANCE
                if (fingerLeft && abs(velocityX) > FLING_MIN_VELOCITY) {
                    Log.i(TAG, "左滑")
                } else if (fingerRight && abs(velocityX) > FLING_MIN_VELOCITY) {
                    Log.i(TAG, "右滑")
                }
                return true
            }
        }

        class MyOnDoubleTapListener : GestureDetector.OnDoubleTapListener {
            companion object {
                private const val TAG = "GestureActivity"
            }

            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                Log.i(TAG, "onSingleTapConfirmed,e = $e")
                return true
            }

            override fun onDoubleTap(e: MotionEvent): Boolean {
                Log.i(TAG, "onDoubleTap,e = $e")
                return true
            }

            override fun onDoubleTapEvent(e: MotionEvent): Boolean {
                Log.i(TAG, "onDoubleTapEvent,e = $e")
                return true
            }
        }

        // 提供了 OnGestureListener OnDoubleTapListener 接口的默认实现，可以按需重写
        class MySimpleGestureListener : GestureDetector.SimpleOnGestureListener() {
            override fun onContextClick(e: MotionEvent): Boolean {
                return super.onContextClick(e)
            }

            override fun onDoubleTap(e: MotionEvent): Boolean {
                return super.onDoubleTap(e)
            }
        }
    }
}