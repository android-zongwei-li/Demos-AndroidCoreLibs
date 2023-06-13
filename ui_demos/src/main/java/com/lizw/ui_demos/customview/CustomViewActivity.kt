package com.lizw.ui_demos.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lizw.ui_demos.R
import com.lizw.ui_demos.databinding.ActivityCustomViewBinding

class CustomViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityCustomViewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        
        show(viewBinding.clipRegionView)
    }
    
    private fun show(view: View) {
        view.visibility = View.VISIBLE
    }
}