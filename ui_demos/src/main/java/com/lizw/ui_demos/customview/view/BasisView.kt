package com.lizw.ui_demos.customview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * 用于练习 Paint，canvas 的基本用法。
 * 涉及到
 * 1、RectF、Rect，Paint.Style的三种用法；
 * 2、绘制点、线、圆、矩形等，绘制路径（Path，直线路径、弧线路径），绘制canvas背景，绘制区域（Region）；
 * 3、理解canvas，包括canvas前后是叠加状态、以canvas的左上角为坐标原点（0,0）；
 *    canvas的平移、缩放、旋转，剪切（clip），以及save和restore等。
 *
 */
class BasisView : View {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)
    
    private val drawer = Drawer()
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 1、设置画笔的基本属性
        // paint.color = Color.RED
        // FILL_AND_STROKE 是 FILL 和 STROKE 的叠加
        // paint.style = Paint.Style.STROKE
        // 以画圆为例，stroke width 50会沿着圆边左右各25px。
        // 这个点在绘制的时候需要注意。可能会出现圆的一部分在屏幕外边，看例1。
        // paint.strokeWidth = 2f
        
        // 例1，会有一部分“边”在屏幕外。
//        canvas.drawCircle(100F, 100F, 100F, paint)
        // 圆心平移后，就ok了。
//        canvas.drawCircle(100F + paint.strokeWidth / 2, 100F + paint.strokeWidth / 2, 100F, paint)
        
        // 2、画布背景设置，下面这三个方法可以用来填充画布颜色
//        canvas.drawRGB(0x00, 0xFF, 0x00)
//        canvas.drawARGB()
        // 直接指定会不生效。用Color取值后有用。
//        canvas.drawColor(0xFF000000)  // 不生效
//         canvas.drawColor(Color.CYAN)
        
        // 3、绘制图形
//        paint.color = Color.GREEN
//        // 点的大小，line的宽度，与style的设置无关，style会影响circle的边宽度。
//        canvas.drawLine(0F, 50F, 200F, 200F, paint)
//        paint.color = Color.BLUE
//        // x,y是点的中心
//        canvas.drawPoint(50f, 50f, paint)
//        paint.color = Color.YELLOW
//        // 左上角，右下角坐标，RectF 与 Rect 的区别是，RectF的值是float类型，Rect是 int 类型。
//        canvas.drawRect(150f, 150f, 200f, 200f, paint)
        
        // 4、绘制路径
        // 4.1 直线路径
//        path.moveTo(10f, 10f)
//        path.lineTo(10f, 100f)
//        path.lineTo(200f, 100f)
//        path.close()   // 首尾相连
//        canvas.drawPath(path, paint)
        // 4.2 弧线路径
//        path.moveTo(10f, 10f)
        // forceMoveTo 为true时，会moveTo到弧的起始点，也就不会跟上面的（10,10）连在一起了。
        // 除了这种方式，使用addArc也不会和前面的点连起来。否则会默认连接起来。
//        path.arcTo(RectF(100f, 10f, 200f, 100f), 0f, 359f, true)
//        path.addArc(RectF(100f, 10f, 200f, 100f), 0f, 359f)
//        canvas.drawPath(path, paint)
        
        // 5、绘制区域（Region）
        // 见：686620《Android自定义控件开发入门与实战》_启舰 第一章，1.3节
        
        // 6、画布
        // paint.color = Color.YELLOW
        // val text = "Hello!"
        // canvas.drawText(text, 0, text.length, 0f, 100f, paint)
        //
        // canvas.save()
        // 6.1 画布平移、旋转、缩放后，不影响前面已经绘制好的内容。canvas前后绘制的内容，会叠加到一起。
        // paint.color = Color.RED
        // canvas.translate(100f, 100f)
//        canvas.rotate(30f)
//         canvas.scale(1.0f, 1.0f)    // 1.0 是保持不变
//         canvas.drawText(text, 0, text.length, 0f, 0f, paint)
        
        // 6.2 画布裁剪
        // canvas.clipRect(50f, 50f, 100f, 100f)
        // canvas.drawColor(Color.RED)
        
        // 6.3 save 和 restore
        // save 会将当前的画布状态放到一个栈中，restore会从栈顶取出一个画布，
        // 这样在 save 后对canvas进行操作（平移、缩放，旋转等）后，可以通过restore方法将画布恢复到之前的状态
        // canvas.restore()
        // paint.color = Color.BLUE
        // paint.style = Paint.Style.FILL
        // canvas.drawRect(0f, 0f, 100f, 100f, paint)
        
        
        // drawer.drawText(canvas)
        
        drawer.drawWave(canvas)
    }
    
    inner class Drawer {
        private val defaultPaint by lazy {
            Paint().apply {
                color = Color.RED
            }
        }
        private val textPaint by lazy {
            Paint().apply {
                color = Color.GREEN
                textSize = 110f // 单位 px
                // 对齐方式，默认左对齐
                // textAlign = Paint.Align.CENTER
            }
        }
        
        private val path = Path()
        
        fun drawText(canvas: Canvas) {
            val baseLineX = 0f
            val baseLineY = 200f
            // 基线
            canvas.drawLine(baseLineX, baseLineY, 1000f, baseLineY, defaultPaint)
            
            canvas.drawText("Dad's blog", baseLineX, baseLineY, textPaint)
            
            // 绘制四线格
            textPaint.fontMetrics.apply {
                val ascentY = baseLineY + ascent
                val descentY = baseLineY + descent
                val topY = baseLineY + top
                val bottomY = baseLineY + bottom
                canvas.drawLine(baseLineX, ascentY, 1000f, ascentY, textPaint)
                canvas.drawLine(baseLineX, descentY, 1000f, descentY, textPaint)
                canvas.drawLine(baseLineX, topY, 1000f, topY, textPaint)
                
                textPaint.color = Color.BLACK
                canvas.drawLine(baseLineX, bottomY, 1000f, bottomY, textPaint)
            }
        }
        
        fun drawWave(canvas: Canvas) {
            path.moveTo(100f, 200f)
            path.quadTo(200f, 0f, 300f, 200f)
            path.quadTo(400f, 400f, 500f, 200f)
            canvas.drawPath(path, defaultPaint)
        }
    }
}