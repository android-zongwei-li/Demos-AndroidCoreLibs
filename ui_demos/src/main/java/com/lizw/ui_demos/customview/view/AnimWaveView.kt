package com.lizw.ui_demos.customview.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

class AnimWaveView : View {
    
    private val mPaint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
    }
    private val mPath = Path()
    private val mItemWaveLength = 300f
    private var dx = 0
    
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        startAnim()
    }
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPath.reset()
        val originY = 300f
        val halfWaveLen = mItemWaveLength / 2
        mPath.moveTo(-mItemWaveLength + dx, originY)
        var i = -mItemWaveLength
        while (i <= width + mItemWaveLength) {
            mPath.rQuadTo(halfWaveLen / 2, -100f, halfWaveLen, 0f)
            mPath.rQuadTo(halfWaveLen / 2, 100f, halfWaveLen, 0f)
            i += mItemWaveLength
        }
        mPath.lineTo(width.toFloat(), height.toFloat())
        mPath.lineTo(0f, height.toFloat())
        mPath.close()
        canvas.drawPath(mPath, mPaint)
    }
    
    private fun startAnim() {
        // Thread {
        //     while (true) {
        //         for (i in 0..mItemWaveLength.toInt()) {
        //             dx = i
        //             Thread.sleep(10L)
        //             postInvalidate()
        //         }
        //     }
        // }.start()
        
        val animator = ValueAnimator.ofFloat(0f, mItemWaveLength)
        animator.duration = 3000L
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener {
            dx = (it.animatedValue as Float).toInt()
            postInvalidate()
        }
        animator.start()
    }
}