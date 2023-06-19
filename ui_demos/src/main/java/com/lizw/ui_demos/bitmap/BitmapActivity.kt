package com.lizw.ui_demos.bitmap

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.lizw.ui_demos.R
import com.lizw.ui_demos.databinding.ActivityBitmapBinding

// todo 写一个面板显示当前图片的信息。其他页面也类似，把需要从logcat查看的数据显示到应用中
class BitmapActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "BitmapActivity"
    }
    
    private lateinit var binding: ActivityBitmapBinding
    
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBitmapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        resources.displayMetrics.apply {
            Log.i(TAG, "density = " + this.density.toString())
            Log.i(TAG, "densityDpi = " + this.densityDpi.toString())
        }
        
        // 初始图片
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.big_image)
        binding.tvBitmapInfo.text = ImageUtils.getBitmapInfo(bitmap).apply {
            Log.i(TAG, this)
        }
        // binding.ivBigView.setImageBitmap(bitmap)
        
        binding.btnResize.setOnClickListener {
            // 压缩后
            val bitmap1 = ImageUtils.resizeBitmap(resources, R.mipmap.big_image1,
                    binding.ivBigView.width, binding.ivBigView.height, false)
            binding.tvBitmapInfo.text = "${binding.tvBitmapInfo.text} \n${ImageUtils.getBitmapInfo(bitmap1)}"
            binding.ivBigView.setImageBitmap(bitmap1)
        }
        
        val handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                val bitmap2 = msg.obj as Bitmap
                binding.tvBitmapInfo.text = ImageUtils.getBitmapInfo(bitmap2)
                binding.ivBigView.setImageBitmap(bitmap2)
            }
        }
        binding.btnLoadNetBitmap.setOnClickListener {
            ImageUtils.loadBitmapFromNet(handler)
        }
    }
}