package com.lizw.core_apis.android.bitmap

import android.os.Bundle
import com.lizw.core_apis.R
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.lizw.core_apis.android.bitmap.ImageUtils
import android.os.Build
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class BitmapActivity : AppCompatActivity() {
    var imageView: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bitmap)
        imageView = findViewById(R.id.iv_big_view)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        // 初始图片
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.big_image)
        ImageUtils.printBitmapInfo(bitmap)

        // 压缩后
        val bitmap1 = ImageUtils.resizeBitmap(resources,
            R.mipmap.big_image,
            imageView!!.width,
            imageView!!.height,
            false)
        ImageUtils.printBitmapInfo(bitmap1)
        imageView!!.setImageBitmap(bitmap1)
    }
}