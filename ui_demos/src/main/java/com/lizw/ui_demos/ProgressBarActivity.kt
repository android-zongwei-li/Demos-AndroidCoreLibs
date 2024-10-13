package com.lizw.ui_demos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lizw.ui_demos.databinding.ActivityProgressBarBinding

/**
 * 显示进度条控件
 */
class ProgressBarActivity : AppCompatActivity() {
    private val binding by lazy { ActivityProgressBarBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}