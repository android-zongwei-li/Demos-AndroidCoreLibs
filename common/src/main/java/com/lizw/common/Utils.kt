package com.lizw.common

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics

object Utils {
    class Ui {
        /**
         * dp 转 px
         */
        fun dp2px(dp: Float, context: Context): Float = dp * context.resources.displayMetrics.density
    
        /**
         * sp 转 px
         */
        fun sp2px(sp: Float, context: Context): Float = sp * context.resources.displayMetrics.scaledDensity
    
    }
    
    /**
     * 跟屏幕相关
     */
    class Screen {
        /**
         * 获取屏幕实际的高度（单位：像素点）
         */
        fun getScreenRealHeight(activity: Activity): Int {
            val dm = DisplayMetrics()
            activity.windowManager.defaultDisplay.getRealMetrics(dm)
            return dm.heightPixels
        }
    
        /**
         * 获取屏幕实际的宽度（单位：像素点）
         */
        fun getScreenRealWidth(activity: Activity): Int {
            val dm = DisplayMetrics()
            activity.windowManager.defaultDisplay.getRealMetrics(dm)
            return dm.widthPixels
        }
    
        /**
         * 获取屏幕可用的高度（单位：像素点），可用高/宽度受顶部或底部导航栏影响，会小于实际的屏幕宽高。
         */
        fun getScreenAvailableHeight(activity: Activity): Int {
            return activity.resources.displayMetrics.heightPixels
        }
    
        /**
         * 获取屏幕可用的宽度（单位：像素点），可用高/宽度受顶部或底部导航栏影响，会小于实际的屏幕宽高。
         */
        fun getScreenAvailableWidth(activity: Activity): Int {
            return activity.resources.displayMetrics.widthPixels
        }
    }
}