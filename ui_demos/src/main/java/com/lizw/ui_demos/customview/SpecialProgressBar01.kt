package com.lizw.ui_demos.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View

/**
 *
 * NotesUrl：
 * 技术点：
 */
class SpecialProgressBar01 : View {
    private val totalProgressPaint = Paint().apply {
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.FILL_AND_STROKE
        color = Color.GRAY
    }
    private val currentProgressPaint = Paint().apply {
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.FILL_AND_STROKE
    }
    private val innerCirclePaint = Paint().apply {
        color = Color.WHITE
    }

    // 当前进度
    var currentProgress = 3 / 6f
        set(value) {
            field = value
            invalidate()
        }

    // 进度条距离当前控件的上边距和下边距，用来控制进度条的高度
    var currentProgressBarPaddingTopAndBottom = 20f
        set(value) {
            field = value
            invalidate()
        }

    // 当前进度条，渐变颜色
    var gradientColors = intArrayOf(Color.GREEN, Color.YELLOW, Color.BLACK, Color.RED)
        set(value) {
            field = value
            invalidate()
        }

    // 当前进度条，渐变颜色位置
    var gradientPositions = floatArrayOf(0f, 0.33f, 0.66f, 1f)
        set(value) {
            field = value
            invalidate()
        }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context, attrs, defStyle
    )

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        // 总进度条
        canvas.drawRoundRect(
            0f,
            currentProgressBarPaddingTopAndBottom,
            width.toFloat(),
            height.toFloat() - currentProgressBarPaddingTopAndBottom,
            50f,
            50f,
            totalProgressPaint
        )

        // 当前进度条
        val currentProgressBarWidth = width * currentProgress
        currentProgressPaint.shader =
            LinearGradient(
                0f, height / 2f, currentProgressBarWidth, height / 2f,
                gradientColors,
                gradientPositions,
                Shader.TileMode.CLAMP
            )
        canvas.drawRoundRect(
            0f,
            currentProgressBarPaddingTopAndBottom,
            currentProgressBarWidth,
            height.toFloat() - currentProgressBarPaddingTopAndBottom,
            50f,
            50f,
            currentProgressPaint
        )

        // 当前位置圆圈
        if (currentProgress == 0f) {
            // 为了确保渐变色从矩形流畅的过渡到圆形，矩形和圆形使用同一个Paint，也即同一个shader
            canvas.drawCircle(50f, height / 2f, 50f, currentProgressPaint)

            canvas.drawCircle(50f, height / 2f, 25f, innerCirclePaint)
        } else {
            canvas.drawCircle(currentProgressBarWidth - 25f, height / 2f, 50f, currentProgressPaint)

            canvas.drawCircle(currentProgressBarWidth - 25f, height / 2f, 25f, innerCirclePaint)
        }
    }
}