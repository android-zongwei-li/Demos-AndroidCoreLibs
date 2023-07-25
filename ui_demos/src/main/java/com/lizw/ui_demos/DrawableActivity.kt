package com.lizw.ui_demos

import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.ArcShape
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    }
}