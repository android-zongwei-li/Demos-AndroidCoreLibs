package com.lizw.core_apis.thirdpartlibs.leakcanary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.lizw.core_apis.R
import leakcanary.LeakCanary

class LeakCanaryDemoActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "LeakCanaryDemoActivity"

//        var imageView: ImageView? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leak_canary_demo)
        val imageView: ImageView = findViewById(R.id.imageView)

        Thread(Runnable {
            while (true) {
                Log.i(TAG, "running1")
                Thread.sleep(1000L)
            }
        }).start()

        Thread {
            while (true) {
                Log.i(TAG, "running")
                Thread.sleep(1000L)
            }
        }.start()

    }
}