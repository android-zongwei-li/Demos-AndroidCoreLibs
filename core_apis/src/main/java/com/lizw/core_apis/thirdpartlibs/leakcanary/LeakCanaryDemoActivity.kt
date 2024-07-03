package com.lizw.core_apis.thirdpartlibs.leakcanary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.lizw.core_apis.R

class LeakCanaryDemoActivity : AppCompatActivity() {
    companion object {
        var imageView: ImageView? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leak_canary_demo)
        imageView = findViewById(R.id.imageView)
    }
}