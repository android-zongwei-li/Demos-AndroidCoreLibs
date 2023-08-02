package com.lizw.common.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.MessageDigest

object Utils {
    class Toast {
        companion object {
            fun showShort(context: Context, msg: String) {
                android.widget.Toast.makeText(context, msg, android.widget.Toast.LENGTH_SHORT).show()
            }
            
            fun showLong(context: Context, msg: String) {
                android.widget.Toast.makeText(context, msg, android.widget.Toast.LENGTH_LONG).show()
            }
        }
    }
    
    class Ui {
        companion object {
            fun dp2px(context: Context, dpValue: Float): Float {
                return TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        dpValue,
                        context.resources.displayMetrics
                )
            }
        }
        
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
    
    class Md5 {
        companion object {
            /**
             * 字符串生成md5
             * @param content 输入的值
             * @return 输出md5加密后的值
             */
            fun getMd5(content: String): String {
                val md5 = MessageDigest.getInstance("MD5")
                val rowMd5Str = BigInteger(
                        1,
                        md5.digest(content.toByteArray(Charset.defaultCharset()))
                ).toString(16)
                // BigInteger会把开头的0省略掉，需补全至32位
                return fillMD5(rowMd5Str)
            }
            
            private fun fillMD5(md5: String): String {
                return if (md5.length == 32) md5 else fillMD5("0$md5")
            }
        }
    }
}