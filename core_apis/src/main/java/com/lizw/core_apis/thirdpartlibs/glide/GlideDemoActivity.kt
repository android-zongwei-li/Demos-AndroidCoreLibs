package com.lizw.core_apis.thirdpartlibs.glide

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.lizw.core_apis.databinding.ActivityGlideDemoBinding

class GlideDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityGlideDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.btnLoadImg.setOnClickListener {
            Glide.with(this)
                    .load("https://img2.baidu.com/it/u=2502839585,1143634321&fm=253&fmt=auto&app=138&f=PNG?w=940&h=500")
                    .into(binding.imageView)
        }
        
        // 测试 Glide 中的 DiskLruCache
        // DiskLruCache.open(File(cacheDir.path + "test_disk"), 1, 5, Int.MAX_VALUE.toLong())
    }
}