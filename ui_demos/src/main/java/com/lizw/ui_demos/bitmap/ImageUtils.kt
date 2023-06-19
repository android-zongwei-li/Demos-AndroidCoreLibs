package com.lizw.ui_demos.bitmap

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Message
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

object ImageUtils {
    fun resizeBitmap(
            resources: Resources, id: Int,
            maxW: Int, maxH: Int, hasAlpha: Boolean,
    ): Bitmap {
        val options = BitmapFactory.Options()
        // 设置为true后，只解码出图片的参数，比如图片的格式、尺寸等，不会加载具体的图片
        options.inJustDecodeBounds = true
        // 解码后，会将图片的参数保存到 options 中，后续可通过 options 获取图片参数
        BitmapFactory.decodeResource(resources, id, options)
        
        // 图片类型
        val imageType = options.outMimeType
        
        // 获取图片的宽度和高度
        val originalWidth = options.outWidth
        val originalHeight = options.outHeight
        // 设置缩放系数（采样率），只能取2的n次方。比如采样率为2时，加载的图片宽高均为原始图片的 1/2，总的大小就是图片的 1/4，以此类推。
        options.inSampleSize = calculateInSampleSize(originalWidth, originalHeight, maxW, maxH)
        
        if (!hasAlpha) {
            options.inPreferredConfig = Bitmap.Config.RGB_565
        }
        
        // 设置为false后，就会加载 Bitmap 到内存中
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(resources, id, options)
    }
    
    fun loadBitmapFromNet(handler: Handler) {
        val t: Thread = object : Thread() {
            override fun run() {
                super.run()
                try {
                    val url = URL("https://img2.baidu.com/it/u=3442533269,4290406331&fm=253&fmt=auto&app=138&f=JPEG?w=750&h=500")
                    var connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                    println("    code :" + connection.responseCode)
                    val options = BitmapFactory.Options()
                    options.inJustDecodeBounds = true
                    val `is`: InputStream = connection.inputStream
                    BitmapFactory.decodeStream(`is`, null, options)
                    
                    val height = options.outHeight
                    val width = options.outWidth
                    // 获取图片的类型
                    val imageType = options.outMimeType
                    Log.i("imageTest", "$width    $height   $imageType")
                    
                    connection = url.openConnection() as HttpURLConnection
                    
                    options.inJustDecodeBounds = false
                    options.inSampleSize = 2
                    val bitmap = BitmapFactory.decodeStream(connection.inputStream, null, options)
                    val m = Message()
                    m.obj = bitmap
                    handler.sendMessage(m)
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        t.start()
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
    
    fun getBitmapInfo(bitmap: Bitmap): String {
        val size = bitmap.allocationByteCount
        return "BitmapInfo: w = ${bitmap.width} h = ${bitmap.height} , 内存大小 = ${size}B(${size / 1000000.0}M)"
    }
}