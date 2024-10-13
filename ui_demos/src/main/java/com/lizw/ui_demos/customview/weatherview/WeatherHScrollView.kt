package com.lizw.ui_demos.customview.weatherview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.HorizontalScrollView
import com.lizw.common.ext.getScreenWidth

class WeatherHScrollView : HorizontalScrollView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    
    private var weatherView: WeatherView? = null
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 滑块当前偏移
        val offset = computeHorizontalScrollOffset()
        // 滑块最大偏移量 = 水平滑动最大范围 - 屏幕宽度。这个求的实际上就是隐藏的那部分的宽度
        val maxOffset = computeHorizontalScrollRange() - getScreenWidth(context)
        weatherView?.setScrollOffset(offset, maxOffset, width)
    }
    
    fun setWeatherView(twentyFourHourView: WeatherView) {
        this.weatherView = twentyFourHourView
    }
}