package com.lizw.core_apis.android.activity

import android.os.Bundle
import com.lizw.common.ext.startActivityDefaultIntent
import com.lizw.core_apis.databinding.ActivityLifecycleTestBinding

class LifecycleTestActivity : BaseLifecycleTestActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityLifecycleTestBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        
        viewBinding.btnStartActivityA.setOnClickListener {
            startActivityDefaultIntent(AActivity::class.java)
        }
    
        viewBinding.btnStartActivityB.setOnClickListener {
            startActivityDefaultIntent(BActivity::class.java)
        }
    }
}