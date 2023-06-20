package com.lizw.core_apis.android.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

open class BaseLifecycleTestActivity : AppCompatActivity() {
    companion object {
        private const val FEATURE_TAG = "Lifecycle_"
    }
    
    val TAG = FEATURE_TAG + javaClass.simpleName
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
    }
    
    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }
    
    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }
    
    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }
    
    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }
}