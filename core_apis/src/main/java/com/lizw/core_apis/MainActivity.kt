package com.lizw.core_apis

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.lizw.core_apis.android.LruCacheManager
import com.lizw.core_apis.databinding.ActivityMainBinding
import com.lizw.core_apis.kotlin.coroutines.CoroutinesActivity

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStartActivityCoroutines.setOnClickListener {
            startActivity(Intent(this, CoroutinesActivity::class.java))
        }

        binding.btnLoadBitmap.setOnClickListener {
            val startTime = System.currentTimeMillis()
            val bitmap = ImageLoader.loadDefaultBitmap(this)
            binding.ivPic.setImageBitmap(bitmap)
            val spentTime = System.currentTimeMillis() - startTime
            Log.i(TAG, "spentTime = $spentTime ms")
        }
    }

    private class ImageLoader {
        companion object {
            private const val TAG = "ImageLoader"

            var lruCacheManager: LruCacheManager? = null

            fun loadDefaultBitmap(context: Context): Bitmap? {
                var result: Bitmap?
                if (lruCacheManager == null) {
                    lruCacheManager = LruCacheManager(context)
                }

                result = lruCacheManager?.getBitmap("defaultBitmap")
                Log.i(TAG, "loaded from LruCache, result = $result")

                if (result == null) {
                    // 下面这行加载不到bitmap图片，why？
//                    result = BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher)
                    result =
                        ResourcesCompat.getDrawable(context.resources, R.mipmap.ic_launcher, null)
                            ?.toBitmap()
                    Log.i(TAG, "load from file, result = $result")
                }

                return result.apply {
                    lruCacheManager?.putCache("defaultBitmap", result)
                }
            }
        }
    }
}