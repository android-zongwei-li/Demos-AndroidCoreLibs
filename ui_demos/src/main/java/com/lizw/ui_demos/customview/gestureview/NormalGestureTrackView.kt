package com.lizw.ui_demos.customview.gestureview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.lizw.ui_demos.customview.PaintFactory

class NormalGestureTrackView : View {
    
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)
    
    private val path = Path()
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(event.x, event.y)
                /**
                 * 返回 true 表示当前已经消费了下按动作，之后的ACTION_MOVE ACTION_UP 动作也会继续传递到当前控件中
                 * 如果在ACTION_DOWN时返回 false ，那么后续的 ACTION_MOVE ACTION_UP 动作就不会再传递到这个控件中。
                 */
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(event.x, event.y)
                postInvalidate()
            }
        }
        return super.onTouchEvent(event)
    }
    
    private val paint = PaintFactory.newDefaultPaint()
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.WHITE)
        canvas.drawPath(path, paint)
    }
}