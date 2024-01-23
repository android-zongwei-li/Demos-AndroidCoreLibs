package com.lizw.core_apis.android.activity

import android.os.Bundle
import com.lizw.core_apis.R

class MyDialogActivity : BaseLifecycleTestActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_dialog)
    }
}