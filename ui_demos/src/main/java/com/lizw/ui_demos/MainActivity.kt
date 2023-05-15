package com.lizw.ui_demos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.lizw.ui_demos.customview.CustomViewActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn_start_custom_view_activity).setOnClickListener {
            startActivity(Intent(this, CustomViewActivity::class.java))
        }
    }
}