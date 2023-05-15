package com.lizw.ui_demos.colorpicker

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt

class ImageViewWithBounds : View {
    private var drawer: Drawer
    
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        drawer = Drawer()
    }
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawer.drawCircle(canvas)
    }
    
    fun setColor(@ColorInt color: Int) {
        drawer.paint.color = color
        invalidate()
    }
    
    inner class Drawer {
        val paint by lazy {
            Paint().apply {
                color = Color.GREEN
            }
        }
        
        fun drawCircle(canvas: Canvas) {
            paint.style = Paint.Style.FILL
            canvas.drawCircle(width / 2f, width / 2f, width / 2f - 10, paint)
            
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 4f
            canvas.drawCircle(width / 2f, width / 2f, width / 2f - paint.strokeWidth, paint)
        }
    }
}