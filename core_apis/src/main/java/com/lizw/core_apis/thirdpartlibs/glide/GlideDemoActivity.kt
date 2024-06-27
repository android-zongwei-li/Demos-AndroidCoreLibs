package com.lizw.core_apis.thirdpartlibs.glide

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.lizw.core_apis.databinding.ActivityGlideDemoBinding

class GlideDemoActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "GlideDemoActivity"
    }

    private val binding by lazy { ActivityGlideDemoBinding.inflate(layoutInflater) }

    val url = "http://cn.bing.com/az/hprichbg/rb/TOAD_ZH-CN7336795473_1920x1080.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        binding.btnLoadImg.setOnClickListener {
//            Glide.with(this)
//                .load("https://img2.baidu.com/it/u=2502839585,1143634321&fm=253&fmt=auto&app=138&f=PNG?w=940&h=500")
//                .into(binding.imageView)
//        }

        testListener()
    }

    private fun testCustomTarget() {
        Glide.with(this)
            .load(url)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    binding.imageView.setImageDrawable(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // Remove the Drawable provided in onResourceReady from any Views and ensure
                    // no references to it remain.
                }
            })

        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    binding.imageView.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }

    private fun testProgress() {
        ProgressInterceptor.addListener(url, object : ProgressListener {
            override fun onProgress(progress: Int) {
                Log.i(TAG, "loadImage progress = $progress")
            }
        })
        Glide.with(this)
            .load(url)
            // 为了测试加载网络图片的进度，将缓存关了
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(object : DrawableImageViewTarget(binding.imageView) {
                override fun onLoadStarted(placeholder: Drawable?) {
                    super.onLoadStarted(placeholder)
                    // 开始加载图片，可以在这加入开始加载的逻辑
                    // 比如显示进度条，或者打开进度弹窗
                    Log.i(TAG, "onLoadStarted")
                }

                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    super.onResourceReady(resource, transition)
                    Log.i(TAG, "onResourceReady")
                    // 图片加载完成后，可以在这里加入加载完成后的逻辑，比如更新进度条、关闭进度弹窗等
                    // 将监听移除
                    ProgressInterceptor.removeListener(url)
                }
            })
    }

    private fun testListener() {
        val url = "http://cn.bing.com/az/hprichbg/rb/TOAD_ZH-CN7336795473_1920x1080.jpg"
        Glide.with(this)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.i(TAG, "listener, onLoadFailed")
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.i(TAG, "listener, onResourceReady")
                    return false
                }
            })
            .into(binding.imageView)
    }
}