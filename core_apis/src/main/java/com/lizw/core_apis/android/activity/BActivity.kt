package com.lizw.core_apis.android.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lizw.core_apis.R

class BActivity : BaseLifecycleTestActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bactivity)
    }
}