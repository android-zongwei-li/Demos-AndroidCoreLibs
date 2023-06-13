package com.lizw.ui_demos.customview.basic

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
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
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context,
        attrs,
        defStyle)

    private val drawer = Drawer()
    private val canvasHelper = CanvasHelper()
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        // 5、绘制区域（Region）
        // 见：686620《Android自定义控件开发入门与实战》_启舰 第一章，1.3节
        
        drawer.drawCanvasBg(canvas)
        
        canvasHelper.testSaveRestore(canvas)
        // canvasHelper.testCanvas(canvas)
        // 如果在 testCanvas 后面执行了绘制背景，那么前面的内容会被背景覆盖
//        drawer.drawCanvasBg(canvas)
        
        when (5) {
            1 -> drawer.drawCircle(canvas)
            2 -> drawer.drawCanvasBg(canvas)
            3 -> drawer.drawLinePointRect(canvas)
            4 -> drawer.drawRect(canvas, drawer.clickX, drawer.clickY)
            5 -> drawer.drawPath(canvas)
            6 -> {}
            7 -> {}
            8 -> drawer.drawText(canvas)
            9 -> drawer.drawWave(canvas)
        }
    }
    
    override fun onTouchEvent(event: MotionEvent): Boolean {
        drawer.clickX = event.x
        drawer.clickY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                invalidate()
                return true
            }
            MotionEvent.ACTION_UP -> {
                drawer.clickY = -1f
                drawer.clickY = -1f
            }
        }
        return super.onTouchEvent(event)
    }
    
    private inner class Drawer {
        private val paint by lazy {
            val p = Paint()
            p.color = Color.BLACK
            p.set(Paint())
            p
        }
        
        private val paintCircle by lazy {
            Paint().apply {
                // 1、设置画笔的基本属性
                color = Color.RED
                // FILL_AND_STROKE 是 FILL 和 STROKE 的叠加
                style = Paint.Style.STROKE
                
                // 以画圆为例，stroke width 50会沿着圆边左右各25px。
                // 这个点在绘制的时候需要注意。可能会出现圆的一部分在屏幕外边，看例1。
                strokeWidth = 40f
            }
        }
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
        
        /**
         * 绘制画布背景
         */
        fun drawCanvasBg(canvas: Canvas) {
            // 2、画布背景设置，下面这三个方法可以用来填充画布颜色
            // canvas.drawRGB(0x00, 0xFF, 0x00)
            // canvas.drawARGB()
            // canvas.drawColor(0xFFFF00FF)
            canvas.drawColor(Color.GRAY)
        }
        
        /**
         * 画圆形。了解strokeWidth的作用
         */
        fun drawCircle(canvas: Canvas) {
            // 表示是否打开抗锯齿功能。抗锯齿是依赖算法的，一般在绘制不规则的图形时使用，比如圆形、文字等。
            // 在绘制棱角分明的图像时，比如一个矩形、一张位图，是不需要打开抗锯齿功能的。
            // 在打开抗锯齿功能的情况下，所绘图像可以产生平滑的边缘。
            paintCircle.isAntiAlias = true
            
            when (1) {
                1 -> {
                    // 例1，会有一部分“边”在屏幕外。
                    // 参数（圆心横坐标，圆心纵坐标，半径，画笔）
                    canvas.drawCircle(100F, 100F, 100F, paintCircle)
                    // 圆心平移后，就ok了。
                    // canvas.drawCircle(100F + paint1.strokeWidth / 2, 100F + paint1.strokeWidth / 2, 100F, paint1)
                }
                2 -> {
                    // 例1.1，理解 strokeWidth 的含义
                    // strokeWidth 的含义是在边的内外各有一般，比如是画了一个圆，设置strokeWidth为20f，那么圆边的里和外各有10f。
                    // 单位是px，只在 style 为 Paint.Style.FILL_AND_STROKE 或 Paint.Style.STROKE 时生效
                    paintCircle.style = Paint.Style.STROKE
                    paintCircle.strokeWidth = 40f
                    canvas.drawCircle(100F, 100F, 80F, paintCircle)
                }
            }
        }
        
        /**
         * 绘制直线/点/矩形
         */
        fun drawLinePointRect(canvas: Canvas) {
            // line的宽度、点的大小，与style的无关，与strokeWidth有关。
            
            // 画直线
            paint.strokeWidth = 10f
            canvas.drawLine(0F, 50F, 200F, 200F, paint)
            
            // 画多条直线，即画线段。
            // pts参数：点的集合。从下面的代码中可以看到，这里不是形成连接线，而是每两个点形成一条直线，
            // pts 的组织方式为｛x1,y1,x2,y2,x3,y3,…｝。
            // 下面有 4 个点，分别是(0,10)和(40,10)、(80,10)和(120,10)，两两连成一条直线。
            canvas.drawLines(floatArrayOf(0f, 10f, 40f, 10f, 80f, 10f, 120f, 10f), paint)
            // int offset：集合中跳过的数值个数。注意不是点的个数！一个点有两个数值。
            // int count：参与绘制的数值个数，指 pts 数组中数值的个数，而不是点的个数，一个点有两个数值。
            // 因此下面的点，实际参与绘制的有（40f,20f）和（80f,20f）
            canvas.drawLines(floatArrayOf(0f, 20f, 40f, 20f, 80f, 20f, 120f, 20f), 2, 6, paint)
            
            // 画点
            paint.color = Color.BLUE
            // x,y 是点的中心
            canvas.drawPoint(50f, 50f, paint)
            
            // 画多个点
            canvas.drawPoints(floatArrayOf(0f, 200f, 20f, 200f, 40f, 200f, 60f, 200f), paint)
            // 参数含义和 上面点 drawLines 一样
            canvas.drawPoints(floatArrayOf(0f, 200f, 20f, 220f, 40f, 220f, 60f, 200f), 2, 4, paint)
            
            // 画矩形
            // style会影响边的宽度。矩形/圆会收到style的影响
            paint.style = Paint.Style.STROKE
            paint.color = Color.YELLOW
            // 左上角，右下角坐标，RectF 与 Rect 的区别是，RectF 的值是float类型，Rect是 int 类型。
            canvas.drawRect(150f, 150f, 200f, 200f, paint)
            
            // 画圆角矩形
            paint.strokeWidth = 2f
            // float rx：生成圆角的椭圆的 X 轴半径。
            // float ry：生成圆角的椭圆的 Y 轴半径。
            // Android 在生成矩形的圆角时，其实利用的是椭圆。
            // shape标签 4 个角都可以设置生成圆角的椭圆，它生成圆角矩形的原理如图drawable-example_round_rect所示。
            // 与 shape 标签不同的是，drawRoundRect()函数不能针对每个角设置对应的椭圆，而只能统一设置 4 个角对应的椭圆。
            val rf1 = RectF(20f, 300f, 80f, 400f)
            canvas.drawRect(rf1, paint)
            canvas.drawRoundRect(rf1, 30f, 30f, paint)
            
            // 画椭圆
            // 椭圆是根据矩形生成的，以矩形的长为椭圆的 X 轴，以矩形的宽为椭圆的 Y 轴。
            val rf = RectF(10f, 400f, 200f, 500f)
            canvas.drawRect(rf, paint)
            canvas.drawOval(rf, paint)
            
            // 画弧
            // RectF oval：生成椭圆的矩形。
            // float startAngle：弧开始的角度，以 X 轴正方向为 0°。
            // float sweepAngle：弧持续的角度。
            // boolean useCenter：是否有弧的两边。
            // 为true时，表示带有两边，即弧的起始点/终点会与中心点连接；为false时，只有一条弧。
            val rectF = RectF(200f, 10f, 400f, 110f)
            canvas.drawRect(rectF, paint)
            canvas.drawArc(rectF, 0f, 90f, true, paint)
            canvas.drawArc(rectF, 120f, 30f, false, paint)
            paint.style = Paint.Style.FILL_AND_STROKE
            canvas.drawArc(rectF, 180f, 60f, false, paint)
            canvas.drawArc(rectF, 270f, 60f, true, paint)
        }
        
        private val exampleRectF = RectF(0f, 100f, 200f, 300f)
        private val exampleRectF1 = RectF(100f, 100f, 200f, 400f)
        var clickX = -1f
        var clickY = -1f
        
        /**
         * 绘制矩形，学习 Rect 的一些方法
         */
        fun drawRect(canvas: Canvas, x: Float, y: Float) {
            paint.style = Paint.Style.STROKE
            if (exampleRectF.contains(x, y)) {
                paint.color = Color.RED
                canvas.drawRect(exampleRectF, paint)
            } else {
                paint.color = Color.GREEN
                canvas.drawRect(exampleRectF, paint)
            }
            
            // canvas.drawRect(exampleRectF1, paint)
            // 矩形相交，exampleRectF 会被设置为相交的矩形
            val intersect = exampleRectF.intersect(exampleRectF1)
            Log.i("BasisView", "intersect $intersect")
            
            // 矩形合并，exampleRectF 会被设置为合并后的矩形
            // 合并是取最小左上角点和最大右下角点，然后构建一个新的矩形
            // exampleRectF.union(exampleRectF1)
            // exampleRectF.union(300f, 500f)
        }
        
        private val path = Path()
        
        /**
         * 绘制路径
         */
        fun drawPath(canvas: Canvas) {
            paint.style = Paint.Style.STROKE
            
            when (3) {
                1 -> {
                    // 4.1 直线路径
                    path.moveTo(10f, 10f)
                    path.lineTo(10f, 100f)
                    path.lineTo(200f, 100f)
                    // 首尾相连
                    path.close()
                    canvas.drawPath(path, paint)
                }
                2 -> {
                    // 4.2 弧线路径
                    path.moveTo(10f, 10f)
                    // 参数 boolean forceMoveTo 的含义是是否强制将弧的起始点作为绘制起始位置。
                    // forceMoveTo 为true时，会moveTo到弧的起始点，也就不会跟上面的（10,10）连在一起了。为false时，弧起始位置会与上一个moveTo位置连接
                    path.arcTo(RectF(100f, 10f, 200f, 100f), 0f, 359f, true)
                    canvas.drawPath(path, paint)
                }
                3 -> {
                    // 绘制路径，了解 Path.addXXX()系列方法
                    
                    path.moveTo(10f, 10f)
                    
                    // 使用addArc不会和前面的点连起来
                    // 路径一般都是连贯的，而 addXXX 系列函数可以让我们直接往 Path 中添加一些曲线，而不必考虑连贯性。
                    
                    // 绘制弧形
                    path.addArc(RectF(100f, 10f, 200f, 100f), 0f, 359f)
                    
                    // @param dir 有两个取值
                    // Path.Direction.CCW ：是 counter-clockwise 的缩写，指创建逆时针方向的矩形路径。
                    // Path.Direction.CW ：是 clockwise 的缩写，指创建顺时针方向的矩形路径。
                    path.addRect(RectF(10f, 10f, 100f, 100f), Path.Direction.CCW)
                    
                    // 再画一条顺时针路径，可以发现对大小没有影响
                    path.addRect(RectF(10f, 110f, 100f, 200f), Path.Direction.CW)
                    
                    // 路径方向的一个作用是与文字绘制有关
                    // 在给定路径上，绘制文字
                    val strTest = "Pathxxxxxxxxxxxxxx"
                    paint.color = Color.WHITE
                    paint.textSize = 20f
                    val pathCCW = Path()
                    pathCCW.addRect(RectF(10f, 210f, 100f, 300f), Path.Direction.CCW)
                    val pathCW = Path()
                    pathCW.addRect(RectF(10f, 310f, 100f, 400f), Path.Direction.CW)
                    canvas.drawTextOnPath(strTest, pathCCW, 0f, 0f, paint)
                    canvas.drawTextOnPath(strTest, pathCW, 0f, 0f, paint)
                    
                    // 绘制圆角矩形，统一圆角
                    path.addRoundRect(RectF(200f, 10f, 300f, 100f), 10f, 10f, Path.Direction.CW)
                    // 四个圆角分别定义
                    // float[] radii：必须传入 8 个数值，分 4 组，分别对应每个角所使用的椭圆的横轴半径和纵轴半径，值越大，圆角弧度越大
                    path.addRoundRect(RectF(200f, 110f, 300f, 200f),
                            floatArrayOf(10f, 10f, 10f, 10f, 30f, 30f, 30f, 30f), Path.Direction.CW)
                    
                    // 绘制圆形路径
                    path.addCircle(250f, 250f, 40f, Path.Direction.CW)
                    
                    // 绘制椭圆
                    path.addOval(RectF(200f, 300f, 300f, 350f), Path.Direction.CW)
                    
                    canvas.drawPath(path, paint)
                }
                4 -> {
                    // 路径填充模式
                    
                    // fillType 只在 paint.style 为 Paint.Style.FILL 或 Paint.Style.FILL_AND_STROKE 时生效
                    
                    // Path.FillType.WINDING 默认值，当两个图形相交时，取相交部分显示。
                    // Path.FillType.INVERSE_WINDING 取 path 的外部区域。
                    // Path.FillType.EVEN_ODD 取Path所在并不相交的区域。
                    // Path.FillType.INVERSE_EVEN_ODD 取 path 的外部和相交区域
                    
                    val pathWinding = Path().apply {
                        addRect(RectF(10f, 10f, 100f, 100f), Path.Direction.CW)
                        addCircle(100f, 100f, 50f, Path.Direction.CW)
                        fillType = Path.FillType.WINDING
                    }
                    val pathInverseWinding = Path().apply {
                        addRect(RectF(110f, 10f, 200f, 100f), Path.Direction.CW)
                        addCircle(200f, 100f, 50f, Path.Direction.CW)
                        fillType = Path.FillType.INVERSE_WINDING
                    }
                    val pathEvenOdd = Path().apply {
                        addRect(RectF(210f, 10f, 300f, 100f), Path.Direction.CW)
                        addCircle(300f, 100f, 50f, Path.Direction.CW)
                        fillType = Path.FillType.EVEN_ODD
                    }
                    val pathInverseEvenOdd = Path().apply {
                        addRect(RectF(110f, 300f, 200f, 400f), Path.Direction.CW)
                        addCircle(200f, 400f, 50f, Path.Direction.CW)
                        fillType = Path.FillType.INVERSE_EVEN_ODD
                    }
                    
                    paint.style = Paint.Style.FILL_AND_STROKE
                    paint.color = Color.RED
                    // 填充Path内部所有部分
                    canvas.drawPath(pathWinding, paint)
                    // 与上面的效果取反，填充path外部（Path外，View内）的部分
                    // canvas.drawPath(pathInverseWinding, paint)
                    
                    // 不填充相交与外部部分
                    canvas.drawPath(pathEvenOdd, paint)
                    // 与上面效果取反，填充相交部分与外部
                    // canvas.drawPath(pathInverseEvenOdd, paint)
                }
                5 -> {
                    // 重置路径，重置方法有两个
                    // path.reset()，函数类似于新建一个路径对象，它的所有数据空间都会被回收并重新分配，但不会清除 FillType。
                    // path.rewind()，函数会清除 FillType 及所有的直线、曲线、点的数据等，但是会保留数据结构。
                    // 这样可以实现快速重用，提高一定的性能。例如，重复绘制一类线段，它们的点的数量都相等，
                    // 那么使用 rewind()函数可以保留装载点数据的数据结构，效率会更高。一定要注意的是，只有在重复绘制相同的路径时，这些数据结构才是可以复用的。
                    
                    val path = Path()
                    path.fillType = Path.FillType.INVERSE_WINDING
                    
                    // reset 不能重置 fillType
                    // path.reset()
                    // path.rewind()
                    
                    path.addCircle(100f, 100f, 50f, Path.Direction.CW)
                    
                    path.rewind()
                    // path.reset()
                    
                    paint.style = Paint.Style.FILL_AND_STROKE
                    paint.color = Color.RED
                    canvas.drawPath(path, paint)
                }
            }
        }
        
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
    
    private inner class CanvasHelper {
        
        /**
         * 了解 translate(),rotate(),scale(),save()/restore() 的用法
         */
        fun testCanvas(canvas: Canvas) {
            val paint = Paint().apply {
                color = Color.RED
                strokeWidth = 30f
                this.textSize = 50f
            }
            val text = "Hello!"
            canvas.drawText(text, 0, text.length, 0f, 0f, paint)
            
            canvas.translate(100f, 100f)
            canvas.rotate(30f)
//            canvas.scale(1.0f, 1.0f)    // 1.0 是保持不变
            
            // 可以看到，画布平移、旋转、缩放后，不影响前面已经绘制好的内容。canvas后绘制的内容会盖到前面的内容之上
            paint.color = Color.RED
            canvas.drawText(text, 0, text.length, 0f, 0f, paint)
            
            // case2，画布裁剪
            canvas.clipRect(0f, 0f, 300f, 300f)
            canvas.drawColor(Color.GREEN)
            
            // case3，save 和 restore
            // canvas.save() ，会将当前的画布状态放到一个栈中，restore会从栈顶取出一个画布，
            // 这样在 save 后对canvas进行操作（平移、缩放，旋转等）后，(可以通过restore方法将画布恢复到之前的状态，表述不够准确)
            canvas.save()
            canvas.clipRect(0f, 0f, 200f, 200f)
            canvas.drawColor(Color.BLACK)
            
            // restore 以后，canvas 会再次用 save 之前的canvas绘图，但是在 save 和 restore 之前的操作，还是会被保留的，
            canvas.restore()
            // 下面3行，可以说明在restore以后，save-restore之前的操作还在屏幕上（一个黑色矩形）
            paint.color = Color.BLUE
            paint.style = Paint.Style.FILL
            canvas.drawRect(0f, 0f, 100f, 100f, paint)
            // 如果上面 3 行，换成下面一行，就容易发现操作的不再是save-restore之前裁剪的画布了，而是save之前的画布
            canvas.drawColor(Color.GREEN)
            
            // 综上，restore 的本质作用就是，后续的canvas操作，会在save之前的画布上进行，而非save-restore之前的画布
        }
        
        fun testSaveRestore(canvas: Canvas) {
            canvas.drawColor(Color.RED)
            // 保存的画布大小为全屏幕大小
            val c1 = canvas.save()
            canvas.clipRect(Rect(100, 100, 800, 800))
            canvas.drawColor(Color.GREEN)
            // 保存的画布大小为 Rect(100, 100, 800, 800)
            val c2 = canvas.save()
            canvas.clipRect(Rect(200, 200, 700, 700))
            canvas.drawColor(Color.BLUE)
            // 保存的画布大小为 Rect(200, 200, 700, 700)
            val c3 = canvas.save()
            canvas.clipRect(Rect(300, 300, 600, 600))
            canvas.drawColor(Color.BLACK)
            // 保存的画布大小为 Rect(300, 300, 600, 600)
            val c4 = canvas.save()
            canvas.clipRect(Rect(400, 400, 500, 500))
            canvas.drawColor(Color.WHITE)
            
            // 连续三次出栈，将最后一次画布状态作为当前画布，并填充为黄色
            // 相当于调用 3 次 restore
            canvas.restore()
            canvas.restore()
            canvas.restore()
            // canvas.restoreToCount(c2)
            canvas.drawColor(Color.YELLOW)
        }
    }
}