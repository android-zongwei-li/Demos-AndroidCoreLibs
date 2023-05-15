package com.lizw.ui_demos.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lizw.ui_demos.R
import com.lizw.ui_demos.recyclerview.adapter.MultiTypeAdapter

class MultiTypeActivity : AppCompatActivity() {
    private lateinit var rvMultiType: RecyclerView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_type)
        
        rvMultiType = findViewById<RecyclerView?>(R.id.rv_multi_type).apply {
            layoutManager = LinearLayoutManager(this@MultiTypeActivity)
            adapter = MultiTypeAdapter(Model.loadMultiData().multiTypeData)
        }
    }
}