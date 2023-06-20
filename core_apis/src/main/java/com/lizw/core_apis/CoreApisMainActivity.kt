package com.lizw.core_apis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lizw.common.ext.startActivityDefaultIntent
import com.lizw.core_apis.android.activity.LifecycleTestActivity
import com.lizw.core_apis.android.contentprovider.ContentProviderActivity
import com.lizw.core_apis.databinding.ActivityCoreApisMainBinding
import com.lizw.core_apis.java.thread.ThreadDemoActivity
import com.lizw.core_apis.kotlin.coroutines.CoroutinesActivity
import com.lizw.core_apis.thirdpartlibs.retrofit.RetrofitActivity

class CoreApisMainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCoreApisMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.btnStartActivityCoroutines.setOnClickListener {
            startActivityDefaultIntent(CoroutinesActivity::class.java)
        }
        
        binding.btnStartRetrofitActivity.setOnClickListener {
            startActivityDefaultIntent(RetrofitActivity::class.java)
        }
        
        binding.btnStartActivityThread.setOnClickListener {
            startActivityDefaultIntent(ThreadDemoActivity::class.java)
        }
        
        binding.btnStartContentProvider.setOnClickListener {
            startActivityDefaultIntent(ContentProviderActivity::class.java)
        }
        
        binding.btnStartActivityLifecycle.setOnClickListener {
            startActivityDefaultIntent(LifecycleTestActivity::class.java)
        }
    }
}