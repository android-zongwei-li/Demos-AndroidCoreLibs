package com.lizw.common.ext

import android.content.Context

/**
 * 基本数据类型拓展
 */

/**
 * dp 转 px
 */
fun dp2px(dp: Float, context: Context): Float = dp * context.resources.displayMetrics.density

fun Float.dp(context: Context) = dp2px(this, context)

fun Int.dp(context: Context) = dp2px(this.toFloat(), context).toInt()

/**
 * sp 转 px
 */
fun sp2px(sp: Float, context: Context): Float = sp * context.resources.displayMetrics.scaledDensity

fun Float.sp(context: Context) = sp2px(this, context)

fun Int.sp(context: Context) = sp2px(this.toFloat(), context).toInt()
