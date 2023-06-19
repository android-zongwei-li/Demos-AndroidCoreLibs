package com.lizw.core_apis

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lizw.core_apis.android.contentprovider.ContentProviderActivity
import com.lizw.core_apis.databinding.ActivityMainBinding
import com.lizw.core_apis.java.thread.ThreadDemoActivity
import com.lizw.core_apis.kotlin.coroutines.CoroutinesActivity
import com.lizw.core_apis.thirdpartlibs.retrofit.RetrofitActivity

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
  
        binding.btnStartRetrofitActivity.setOnClickListener {
            startActivity(Intent(this, RetrofitActivity::class.java))
        }

        binding.btnStartActivityThread.setOnClickListener {
            startActivity(Intent(this, ThreadDemoActivity::class.java))
        }

        binding.btnStartContentProvider.setOnClickListener {
            startActivity(Intent(this, ContentProviderActivity::class.java))
        }
    }

}