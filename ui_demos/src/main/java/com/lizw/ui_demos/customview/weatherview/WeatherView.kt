package com.lizw.ui_demos.customview.weatherview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.lizw.common.ext.dp
import com.lizw.common.ext.sp
import com.lizw.ui_demos.R

/**
 * 参考：https://juejin.cn/post/7117174531910795272
 */
class WeatherView : View {
    
    companion object {
        private const val TAG = "WeatherView"
        
        private val TEMP = intArrayOf(
                21, 21, 23, 23, 23,
                22, 23, 23, 23, 22,
                21, 21, 22, 22, 23,
                23, 24, 24, 25, 25,
                25, 26, 25, 24
        )
        private val WEATHER = arrayOf(
                "晴", "晴", "多云", "多云", "多云", "雷阵雨", "小雨",
                "中雨", "中雨", "中雨", "中雨", "大雨", "大雪", "大雪",
                "大雪", "大雪", "大雪", "大雪", "大雪", "大雪", "雨夹雪",
                "雨夹雪", "雨夹雪", "雨夹雪"
        )
        private val ICON = intArrayOf(
                R.drawable.w0,
                -1,
                R.drawable.w1,
                -1,
                -1,
                R.drawable.w5,
                R.drawable.w7,
                R.drawable.w9,
                -1,
                -1,
                -1,
                R.drawable.w10,
                R.drawable.w15,
                -1,
                -1,
                -1,
                -1,
                -1,
                -1,
                -1,
                R.drawable.w19,
                -1,
                -1,
                -1
        )
    }
    
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    
    //数据集合
    private var data: ArrayList<Weather> = ArrayList()
    
    // 有天气icon的数据，天气icon不是-1的集合
    private var weatherWithIcon: ArrayList<Weather> = ArrayList()
    private var maxTemp: Int = 0 //最大温度
    private var minTemp: Int = 0 //最小温度
    
    private val itemCount = 24 //24小时
    private val itemWidth by lazy { 30.dp(context) } //每个Item的宽度
    private val marginLeftItem by lazy { 2.dp(context) } //左边预留宽度
    private val marginRightItem by lazy { 20.dp(context) } //右边预留宽度
    private val bottomTextHeight by lazy { 16.dp(context) } //底部文字高度
    private val mHeight by lazy { 140.dp(context) } //View的高度
    private val mWidth by lazy { marginLeftItem + marginRightItem + itemCount * itemWidth } //View的宽度
    
    private val tempLineTopY by lazy { (mHeight - bottomTextHeight) / 3 } //温度折线的上边Y坐标
    private val tempLineBottomY by lazy { (mHeight - bottomTextHeight) * 3 / 4 } //温度折线的下边Y坐标
    
    private val drawer: Drawer = Drawer()
    
    init {
        initData()
    }
    
    /**
     * 方便演示,自己造数据
     */
    private fun initData() {
        data.let { data ->
            maxTemp = TEMP.maxOrNull()!!
            minTemp = TEMP.minOrNull()!!
            for (i in 0 until itemCount) {
                val time = if (i < 10) {
                    "0$i:00"
                } else {
                    "$i:00"
                }
                val itemLeft = marginLeftItem + i * itemWidth
                val itemRight = itemLeft + itemWidth - 1
                val point = calculateTempPoint(itemLeft, itemRight, TEMP[i])
                val weather = Weather(ICON[i], TEMP[i], WEATHER[i], time, point)
                data.add(weather)
                
                if (data[i].icon != -1) {
                    weatherWithIcon.add(weather)
                }
            }
        }
    }
    
    /**
     * 计算每个温度的坐标
     */
    private fun calculateTempPoint(left: Int, right: Int, temp: Int): Point {
        // 折线最高点，对应最高温度，Y 值最小
        val minHeight = tempLineTopY.toDouble()
        // 折线最低点，对应最低温度，Y 值最大
        val maxHeight = tempLineBottomY.toDouble()
        // 计算温度的Y坐标，基于minHeight 和 maxHeight 都可以
        // val tempY =
        //     maxHeight - (temp - minTemp) * 1.0 / (maxTemp - minTemp) * (maxHeight - minHeight)
        // 基于 minHeight 计算 tempY
        val tempY = minHeight + (maxTemp - temp) * 1.0 / (maxTemp - minTemp) * (maxHeight - minHeight)
        return Point((left + right) / 2, tempY.toInt())
    }
    
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(mWidth, mHeight)
    }
    
    private var maxScrollOffset: Int = 0 // 滚动条最长滚动距离
    private var scrollOffset: Int = 0 // 滚动条偏移量
    private var currentItemIndex: Int = 0 // 当前item位置
    fun setScrollOffset(offset: Int, maxScrollOffset: Int, scrollWidth: Int) {
        this.maxScrollOffset = maxScrollOffset
        // this.scrollWidth = scrollWidth
        scrollOffset = offset
        currentItemIndex = calculateItemIndex()
        invalidate()
    }
    
    private val maxScrollableOffset = (itemCount - 4) * itemWidth
    
    // scrollPercent == scrollOffset / maxScrollOffset == currentScrollX / maxScrollableOffset
    private fun getScrollBarX(): Int {
        val scrollPercent = scrollOffset / maxScrollOffset.toDouble()
        val currentScrollX = maxScrollableOffset * scrollPercent
        return currentScrollX.toInt() + marginLeftItem
    }
    
    private fun calculateItemIndex(): Int {
        val x = getScrollBarX()
        var sum = marginLeftItem - itemWidth
        for (i in 0 until itemCount) {
            sum += itemWidth
            if (x < sum) return i
        }
        return itemCount - 1
    }
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawer.drawLine(canvas)
        drawer.drawText(canvas)
        drawer.drawRectangle(canvas)
        drawer.drawIcon(canvas)
        drawer.drawTempBar(canvas)
    }
    
    /**
     * 通过内部类可以将相同类型的方法，都封装到一起，有利于简化代码
     */
    inner class Drawer {
        private val linePaint: Paint by lazy {
            Paint().apply {
                color = Color.WHITE
                pathEffect = CornerPathEffect(180f)
                isAntiAlias = true
                strokeCap = Paint.Cap.ROUND
                strokeJoin = Paint.Join.ROUND
                style = Paint.Style.STROKE
                strokeWidth = 10f
            }
        }
        private val textPaint: TextPaint by lazy {
            TextPaint().apply {
                color = Color.WHITE
                isAntiAlias = true
                textAlign = Paint.Align.CENTER
                textSize = 12f.sp(context)
            }
        }
        private val rectPaint: Paint by lazy {
            Paint().apply {
                color = Color.parseColor("#1AFFFFFF")
                // color = Color.RED
                isAntiAlias = true
                style = Paint.Style.FILL
                strokeCap = Paint.Cap.ROUND
                strokeJoin = Paint.Join.ROUND
                strokeWidth = 3f
            }
        }
        private val indicatorLinePaint: Paint by lazy {
            Paint().apply {
                color = Color.WHITE
                isAntiAlias = true
                strokeCap = Paint.Cap.ROUND
                style = Paint.Style.STROKE
                strokeWidth = 2f
            }
        }
        
        /**
         * 记录下温度条中线，通过这条线，来改变画矩形的画笔颜色
         */
        private var tempBarCenterLineX = 0f
        
        fun drawLine(canvas: Canvas) {
            data.let { data ->
                val path = Path()
                data.forEach { weather ->
                    val point: Point = weather.tempPoint
                    val i = data.indexOf(weather)
                    if (i == 0) {
                        path.moveTo(point.x.toFloat(), point.y.toFloat())
                    } else {
                        path.lineTo(point.x.toFloat(), point.y.toFloat())
                    }
                }
                canvas.drawPath(path, linePaint)
            }
        }
        
        fun drawText(canvas: Canvas) {
            data.forEach { weather ->
                if (data.indexOf(weather) % 2 == 0) {
                    val left = marginLeftItem + data.indexOf(weather) * itemWidth
                    val right = left + itemWidth - 1
                    val bottom = mHeight - bottomTextHeight
                    val targetRect = Rect(left, bottom, right, bottom + bottomTextHeight)
                    val fontMetrics = textPaint.fontMetricsInt
                    val baseline =
                        (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2
                    canvas.drawText(weather.time, (left + (itemWidth - 1) / 2).toFloat(),
                            baseline.toFloat(), textPaint)
                }
            }
        }
        
        fun drawRectangle(canvas: Canvas) {
            val pathBG = Path()
            val point0 = data[0].tempPoint
            pathBG.moveTo(point0.x.toFloat(), point0.y.toFloat())
            
            var lastWeatherIconPoint: Point? = null
            data.forEach { weather ->
                val point: Point = weather.tempPoint
                val i = data.indexOf(weather)
                
                if (i != 0) {
                    // 要开始闭合（画矩形）的x坐标
                    val closeX: Float = if (weather.icon != -1) {
                        point.x.toFloat() - 1f.dp(context)
                    } else {
                        point.x.toFloat()
                    }
                    pathBG.lineTo(closeX, point.y.toFloat())
                    
                    // 不同矩形显示不同的颜色
                    if (lastWeatherIconPoint?.x!!.toFloat() < tempBarCenterLineX
                        && tempBarCenterLineX < point.x.toFloat()
                    ) {
                        rectPaint.color = Color.RED
                    } else {
                        rectPaint.color = Color.parseColor("#1AFFFFFF")
                    }
                    
                    val weatherIconChanged = weather.icon != -1
                    // 最后一个天气可能是 -1 ，也需要画线
                    val isEndPoint = i == data.lastIndex
                    if (weatherIconChanged || isEndPoint) {
                        
                        val height = mHeight - bottomTextHeight - 4f.dp(context) - point.y
                        pathBG.rLineTo(0f, height)
                        pathBG.rLineTo(lastWeatherIconPoint!!.x - point.x + 1f.dp(context), 0f)
                        canvas.drawPath(pathBG, rectPaint)
                        pathBG.reset()
                        // 移到新的点开始画
                        pathBG.moveTo(point.x.toFloat(), point.y.toFloat())
                    }
                }
                
                // 记录上一个天气开始时的point坐标，用于后续画矩形
                if (weather.icon != -1) {
                    lastWeatherIconPoint = weather.tempPoint
                }
            }
        }
        
        fun drawIcon(canvas: Canvas) {
            weatherWithIcon.forEach { weather ->
                val i = weatherWithIcon.indexOf(weather)
                val currentWeather = weatherWithIcon[i].tempPoint.x
                val nextWeatherIcon = if (i == weatherWithIcon.lastIndex) {
                    data[data.lastIndex].tempPoint.x
                } else {
                    weatherWithIcon[i + 1].tempPoint.x
                }
                val left = (currentWeather + nextWeatherIcon) / 2 - 10.dp(context)
                
                // 移动过程中，改变icon位置，只要改变left即可
                // if (tempBarCenterLineX > currentWeather && tempBarCenterLineX < nextWeatherIcon - 20.dp(context)) {
                //     left += getScrollBarX()
                // }
                
                val right = left + 20.dp(context)
                
                val drawable = ContextCompat.getDrawable(context, weather.icon)
                drawable?.let { da ->
                    da.bounds = Rect(
                            left,
                            tempLineBottomY + 5.dp(context),
                            right,
                            tempLineBottomY + 25.dp(context)
                    )
                    da.draw(canvas)
                }
            }
        }
        
        fun drawTempBar(canvas: Canvas) {
            Log.i(TAG, "$currentItemIndex")
            val weather: Weather = data[currentItemIndex]
            
            val targetRect = Rect().apply {
                left = getScrollBarX()
                top = weather.tempPoint.y - 35.dp(context)
                right = left + 80.dp(context)
                bottom = top + 25.dp(context)
                
                drawTempBar(this, weather, canvas)
            }
        }
        
        /**
         * 在给定的位置区间内，绘制天气信息
         */
        private fun drawTempBar(location: Rect, weather: Weather, canvas: Canvas) {
            val drawable = ContextCompat.getDrawable(context, R.drawable.bg_indicator_text)
            drawable?.apply {
                drawable.bounds = location
                drawable.draw(canvas)
            }
            
            val fontMetrics = textPaint.fontMetricsInt
            val baseline =
                (location.bottom + location.top - fontMetrics.bottom - fontMetrics.top) / 2
            textPaint.textAlign = Paint.Align.CENTER
            textPaint.textSize = 10f.sp(context)
            canvas.drawText(
                    "${weather.time} ${weather.weather} ${weather.temperature}°",
                    location.centerX().toFloat(),
                    baseline.toFloat(),
                    textPaint
            )
            
            val height = mHeight - bottomTextHeight - 4f.dp(context)
            tempBarCenterLineX = location.centerX().toFloat()
            canvas.drawLine(
                    tempBarCenterLineX,
                    location.bottom + 4f.dp(context),
                    location.centerX().toFloat(),
                    height,
                    indicatorLinePaint
            )
        }
    }
}