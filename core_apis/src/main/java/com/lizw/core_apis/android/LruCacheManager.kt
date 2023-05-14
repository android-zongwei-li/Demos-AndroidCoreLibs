package com.lizw.core_apis.android

import android.app.ActivityManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import android.util.LruCache

/**
 *
 * author: zongwei.li created on: 2023/5/14
 */
class LruCacheManager(context: Context) {
    companion object {
        private const val TAG = "LruCacheManager"
    }

    var mMemoryCache: LruCache<String, Bitmap>? = null

    init {
        initMemoryCache(context)
    }

    private fun initMemoryCache(context: Context) {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        // 设置LruCache缓存的大小，一般为当前进程可用容量的1/8。
        val availMenInBytes = am.memoryClass * 1024 * 1024 / 8
        mMemoryCache = object : LruCache<String, Bitmap>(availMenInBytes) {
            override fun sizeOf(key: String?, bitmap: Bitmap?): Int {
                return getBitmapSize(bitmap)
            }
        }
    }

    // 根据android版本来计算bitmap的实际占用内存
    private fun getBitmapSize(bitmap: Bitmap?): Int {
        if (bitmap == null) {
            return 0
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //API 19
            return bitmap.allocationByteCount
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            //API 12
            return bitmap.byteCount
        }
        // 在低版本中用一行的字节x高度
        return bitmap.rowBytes * bitmap.height
    }

    fun getBitmap(key: String?): Bitmap? {
        return mMemoryCache!![key]
    }

    fun putCache(key: String?, bitmap: Bitmap?) {
        if (getBitmap(key) == null) {
            if (key != null && bitmap != null) {
                try {
                    mMemoryCache!!.put(key, bitmap)
                } catch (e: Exception) {
                    Log.e(TAG, e.toString())
                }
            }
        }
    }
}