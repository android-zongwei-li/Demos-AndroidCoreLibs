package com.lizw.ui_demos.customview.basic

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * 蜘蛛图
 * from：《Android自定义View入门与实战补充内容》1.2.7
 */
class SpiderView : View {
    
    private val drawer = Drawer()
    
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
    }
    
    private fun init() {
    }
    
    /**
     * 在控件大小发生变化时，都会通过 onSizeChanged()函数通知我们当前控件的大小。
     * 所以，我们只需要重写 onSizeChanged()函数，即可得知当前控件的最新大小。
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        drawer.radius = Math.min(w, h) / 2 * 0.9f
        drawer.centerX = w / 2
        drawer.centerY = h / 2
        invalidate()
        super.onSizeChanged(w, h, oldw, oldh)
    }
    
    private inner class Drawer {
        private val radarPaint by lazy {
            Paint().apply {
                style = Paint.Style.STROKE
                color = Color.GREEN
            }
        }
        private val valuePaint by lazy {
            Paint().apply {
                style = Paint.Style.FILL
                color = Color.BLUE
            }
        }
        
        // 网格最大半径
        var radius = 0f
        
        // 中心 X
        var centerX = 0
        
        // 中心 Y
        var centerY = 0
        
        fun drawSpider() {
        
        }
    }
}