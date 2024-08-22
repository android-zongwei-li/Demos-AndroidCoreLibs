package com.lizw.ui_demos.opengl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lizw.ui_demos.R
import com.lizw.ui_demos.databinding.ActivityOpenGldemoBinding

class OpenGLDemoActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityOpenGldemoBinding.inflate(layoutInflater)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}