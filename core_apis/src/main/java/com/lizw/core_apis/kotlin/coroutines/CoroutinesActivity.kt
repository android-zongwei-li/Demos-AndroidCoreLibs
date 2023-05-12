package com.lizw.core_apis.kotlin.coroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.lizw.core_apis.R
import com.lizw.core_apis.databinding.ActivityCoroutinesBinding

/**
 * 学习协程的使用
 * 技术栈：viewBinding，navigation
 */
class CoroutinesActivity : AppCompatActivity() {
    
    private lateinit var appBarConfiguration: AppBarConfiguration
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val binding = ActivityCoroutinesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        
        val navController = findNavController(R.id.nav_host_fragment_content_coroutines)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
    
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_coroutines)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}