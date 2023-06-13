package com.lizw.ui_demos.customview.basic

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.lizw.ui_demos.R

/**
 * 作用：圆形头像，通过 canvas 裁剪实现。
 * 技术栈：
 * 《Android自定义View入门与实战补充内容》1.5.3
 */
class CustomCircleView : View {
    private lateinit var bitmap: Bitmap
    private val paint = Paint()
    private val path = Path()
    
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int)
            : super(context, attrs, defStyle) {
        init()
    }
    
    private fun init() {
        // bitmap = BitmapFactory.decodeResource(resources, R.mipmap.p1)
        bitmap = BitmapFactory.decodeResource(resources, R.mipmap.p10)
        path.addCircle(bitmap.width / 2f, bitmap.height / 2f, bitmap.width / 2f, Path.Direction.CCW)
        
        // 在使用 clip 系列函数时，要禁用硬件加速功能。如果不禁用硬件加速功能，则将不会产生任何效果。
        setLayerType(LAYER_TYPE_SOFTWARE,null)
    }
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.clipPath(path)
        canvas.drawColor(Color.RED)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
    }
}