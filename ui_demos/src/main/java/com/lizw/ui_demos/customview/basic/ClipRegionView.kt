package com.lizw.ui_demos.customview.basic

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.lizw.ui_demos.R

/**
 * 作用：
 * 技术栈：
 * https://blog.csdn.net/u010853130/article/details/85076887
 * clipRegion已经过时，并被移除了，不能直接调用，用 Path 替代
 */
class ClipRegionView : View {
    /**
     * 每个裁剪区域的高度，可以通过调整此值，来改变裁剪效果
     */
    private var CLIP_HEIGHT = 30f
    
    private var clipWidth = 0f
    
    private var bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.mipmap.big_image)
    private val paint = Paint()
    private val path = Path()
    
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int)
            : super(context, attrs, defStyle) {
        // 在使用 clip 系列函数时，要禁用硬件加速功能。如果不禁用硬件加速功能，则将不会产生任何效果。
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        
        // 裁剪 2 个区域
        // CLIP_HEIGHT = bitmap.height / 2f
    }
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        // width 如果取 view.width，那么如果bitmap宽度 < View，效果会很奇怪，可以注释掉下面一行对比效果
        val width = bitmap.width
        
        var i = 0
        while (i * CLIP_HEIGHT <= height) {
            if (i % 2 == 0) {
                path.addRect(0f, i * CLIP_HEIGHT, clipWidth, (i + 1) * CLIP_HEIGHT, Path.Direction.CCW)
            } else {
                path.addRect(width - clipWidth, i * CLIP_HEIGHT, width.toFloat(), (i + 1) * CLIP_HEIGHT, Path.Direction.CCW)
            }
            i++
        }
        
        canvas.clipPath(path)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        
        if (clipWidth > width) {
            return
        }
        
        clipWidth += 5
        
        invalidate()
    }
}