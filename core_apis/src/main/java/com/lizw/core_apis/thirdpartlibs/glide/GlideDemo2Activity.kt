package com.lizw.core_apis.thirdpartlibs.glide

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.RequestOptions
import com.lizw.core_apis.R
import com.lizw.core_apis.thirdpartlibs.glide.transform.CircleCropTransform
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.GrayscaleTransformation

private const val TAG = "GlideDemo2Activity"

/**
 * 图片变换Demo
 */
class GlideDemo2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glide_demo2)
        val imageView = findViewById<View>(R.id.image_view) as ImageView
        Log.d(TAG, "imageView scaleType is " + imageView.scaleType)

        val url = "https://www.baidu.com/img/bd_logo1.png"

        fun testTransform() {
            Glide.with(this)
                .load(url)
                .transform(CircleCropTransform())
                // 为了测试加载网络图片的进度，将缓存关了
                .into(imageView)
        }

        fun singleTransform() {
            Glide.with(this).load(url)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
                .into(imageView)
        }

        /**
         * 使用三方库，进行多种变换
         */
        fun multiTransform() {
            val multi = MultiTransformation(
                BlurTransformation(25),
                GrayscaleTransformation()
            )
            Glide.with(this).load(url)
                .apply(RequestOptions.bitmapTransform(multi))
                .into(imageView)
        }
    }
}