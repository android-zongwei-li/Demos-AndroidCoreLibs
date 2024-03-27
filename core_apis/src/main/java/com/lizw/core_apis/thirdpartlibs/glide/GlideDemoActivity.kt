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
            Glide.with(this).load("").into(binding.imageView)
        }
    }
}