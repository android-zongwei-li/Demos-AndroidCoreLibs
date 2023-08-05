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
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.lizw.ui_demos.databinding.ActivityDrawableBinding

/**
 * 图形图像实现方式分为Java/kotlin代码或者xml实现
 * 简单的、代码容易实现的，并且是一次性的，也就是只在当前业务处使用，不需要复用的，建议使用Java/kotlin实现。
 * 复杂的图形，或者多处复用的，可以考虑用xml。
 * 因为创建xml是有成本的，apk会增大，并且随着项目迭代，可能会有越来越多的xml，难以清理和维护。
 */
class DrawableActivity : AppCompatActivity() {
    companion object {
        /**
         * 涉及到的技术点
         */
        val TECH_KEYWORDS =
            arrayOf("PaintDrawable",
                "ShapeDrawable", "OvalShape", "RectShape", "ArcShape",
                "TransitionDrawable", "ColorDrawable",
                "渐变Drawable", "环形渐变", "矩形渐变", "线性渐变", "GradientDrawable",
                "圆角图像", "RoundedBitmapDrawableFactory", "CircleImageView",
                "ImageView#setImageLevel",
                "右上角消息提醒"
            )
    }

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

        /**
         * 示例：绘制圆形图像
         */
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

        /**
         * 示例：ImageView.setImageLevel()
         * 适用场景：
         * 1、表示某种实时状态时，可以考虑使用，比如WiFi信号
         * 比如wifi信号，深色模式和浅色模式分别是两套图，同时每套图有对应多个信号的样式图，这种就挺适合
         */
        fun levelExample() {
            var mode = "light"
            viewBinding.ivLevel.setImageResource(R.drawable.wifi_light)
            viewBinding.btnIvLevel1.setOnClickListener {
                viewBinding.ivLevel.setImageLevel(1)
            }
            viewBinding.btnIvLevel2.setOnClickListener {
                viewBinding.ivLevel.setImageLevel(2)
            }
            viewBinding.btnChangeMode.setOnClickListener {
                if (mode == "light") {
                    mode = "night"
                    viewBinding.ivLevel.setImageResource(R.drawable.wifi_night)
                } else {
                    mode = "light"
                    viewBinding.ivLevel.setImageResource(R.drawable.wifi_light)
                }
            }
        }
        levelExample()
    }
}