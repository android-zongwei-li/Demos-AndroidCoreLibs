package com.lizw.ui_demos

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.TransitionDrawable
import android.graphics.drawable.shapes.ArcShape
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.lizw.ui_demos.databinding.ActivityDrawableBinding

class DrawableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding = ActivityDrawableBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val paintDrawable = PaintDrawable(Color.YELLOW)
        paintDrawable.setCornerRadius(30f)
        viewBinding.tvDrawablePaint.setBackgroundDrawable(paintDrawable)

        val ovalDrawable = ShapeDrawable(OvalShape())
        ovalDrawable.paint.color = Color.RED
        ovalDrawable.paint.style = Paint.Style.FILL
        viewBinding.tvDrawableOval.setBackgroundDrawable(ovalDrawable)

        val rectDrawable = ShapeDrawable(RectShape())
        rectDrawable.paint.color = Color.GREEN
        rectDrawable.paint.style = Paint.Style.FILL
        viewBinding.tvDrawableRect.setBackgroundDrawable(rectDrawable)

        val arcDrawable = ShapeDrawable(ArcShape(0f, 90f))
        arcDrawable.paint.color = Color.BLUE
        arcDrawable.paint.style = Paint.Style.FILL
        viewBinding.tvDrawableArc.setBackgroundDrawable(arcDrawable)

        val drawableArr = arrayOf(ColorDrawable(Color.TRANSPARENT), ColorDrawable(Color.RED))
        val transitionDrawable = TransitionDrawable(drawableArr)
        // 交叉淡入样式
        transitionDrawable.isCrossFadeEnabled = true
        transitionDrawable.startTransition(5000)
        viewBinding.ivTransitionDrawable.setImageDrawable(transitionDrawable)

        fun roundPicExample() {
            // 原始图
            viewBinding.ivRoundPic1.setImageResource(R.mipmap.p10)

            // 圆角图
            val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources,
                BitmapFactory.decodeResource(resources, R.mipmap.p10))
            roundedBitmapDrawable.paint.isAntiAlias = true
            // cornerRadius 设置为 180 也可以变成圆角
            roundedBitmapDrawable.cornerRadius = 30f
            viewBinding.ivRoundPic2.setImageDrawable(roundedBitmapDrawable)

            // 圆形图像
            val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.p10)
            val roundedBitmapDrawable2 = RoundedBitmapDrawableFactory.create(resources, bitmap)
            roundedBitmapDrawable2.paint.isAntiAlias = true
            roundedBitmapDrawable2.cornerRadius = Math.max(bitmap.width, bitmap.height).toFloat()
            viewBinding.ivRoundPic3.setImageDrawable(roundedBitmapDrawable2)
        }
        roundPicExample()
    }
}