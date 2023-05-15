package com.lizw.ui_demos.customview

import android.graphics.Color
import android.graphics.Paint

class PaintFactory {
    companion object {
        fun newDefaultPaint(): Paint {
            return Paint().apply {
                color = Color.BLACK
                style = Paint.Style.STROKE
                strokeWidth = 2f
            }
        }
    }
}