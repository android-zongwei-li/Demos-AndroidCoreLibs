package com.lizw.core_apis.android.activity

import android.os.Bundle
import com.lizw.common.ext.startActivityDefaultIntent
import com.lizw.core_apis.databinding.ActivityAactivityBinding

class AActivity : BaseLifecycleTestActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityAactivityBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        
        viewBinding.btnFinish.setOnClickListener {
            finish()
        }
        
        viewBinding.btnStartActivityB.setOnClickListener {
            startActivityDefaultIntent(BActivity::class.java)
        }
    }
}