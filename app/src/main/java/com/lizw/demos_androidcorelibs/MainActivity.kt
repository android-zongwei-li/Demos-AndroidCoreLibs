package com.lizw.demos_androidcorelibs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lizw.core_apis.CoreApisMainActivity
import com.lizw.demos_androidcorelibs.databinding.ActivityMainBinding
import com.lizw.ui_demos.UiHomeActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        
        viewBinding.btnUiDemos.setOnClickListener {
            startActivity(Intent(this, UiHomeActivity::class.java))
        }
        
        viewBinding.btnCoreApis.setOnClickListener {
            startActivity(Intent(this, CoreApisMainActivity::class.java))
        }
    }
}