package com.lizw.core_apis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.lizw.core_apis.databinding.ActivityMainBinding
import com.lizw.core_apis.kotlin.coroutines.CoroutinesActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.btnStartActivityCoroutines.setOnClickListener {
            startActivity(Intent(this, CoroutinesActivity::class.java))
        }
    }
}