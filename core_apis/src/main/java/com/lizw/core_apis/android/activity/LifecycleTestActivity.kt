package com.lizw.core_apis.android.activity

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.lizw.common.ext.startActivityDefaultIntent
import com.lizw.core_apis.databinding.ActivityLifecycleTestBinding

class LifecycleTestActivity : BaseLifecycleTestActivity() {
    private val viewBinding by lazy { ActivityLifecycleTestBinding.inflate(layoutInflater) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        
        viewBinding.btnStartActivityA.setOnClickListener {
            startActivityDefaultIntent(AActivity::class.java)
        }
        
        viewBinding.btnStartActivityB.setOnClickListener {
            startActivityDefaultIntent(BActivity::class.java)
        }
        
        viewBinding.btnDialog.setOnClickListener {
            AlertDialog.Builder(this).setMessage("This is a Dialog").show()
        }
        
        viewBinding.btnDialogActivity.setOnClickListener {
            startActivityDefaultIntent(MyDialogActivity::class.java)
        }
    }
}