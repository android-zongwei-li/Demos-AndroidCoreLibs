package com.lizw.core_apis.android.bitmap

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log

object ImageUtils {
    fun resizeBitmap(
        resources: Resources, id: Int,
        maxW: Int, maxH: Int, hasAlpha: Boolean,
    ): Bitmap {
        val options = BitmapFactory.Options()
        // 只解码出相关的参数
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, id, options)
        val originalWidth = options.outWidth
        val originalHeight = options.outHeight
        // 设置缩放系数，只能取2的n次方
        options.inSampleSize = calculateInSampleSize(originalWidth, originalHeight, maxW, maxH)
        if (!hasAlpha) {
            options.inPreferredConfig = Bitmap.Config.RGB_565
        }
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(resources, id, options)
    }

    /**
     * 计算 inSampleSize：缩放系数，只能取2的n次方
     *
     * @param originalWidth  Bitmap初始宽度
     * @param originalHeight Bitmap初始高度
     * @param maxW           目标宽度
     * @param maxH           目标高度
     */
    private fun calculateInSampleSize(
        originalWidth: Int, originalHeight: Int,
        maxW: Int, maxH: Int,
    ): Int {
        var inSampleSize = 1
        if (originalWidth > maxW && originalHeight > maxH) {
            inSampleSize = 2
            while (originalWidth / inSampleSize > maxW && originalHeight / inSampleSize > maxH) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    fun printBitmapInfo(bitmap: Bitmap?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.e("bitmap", "x : " + bitmap!!.width + " y : " + bitmap.height +
                    " 内存大小 ： " + bitmap.allocationByteCount)
        }
    }
}