package com.lizw.designpattern

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.lizw.designpatterns.TestUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    
        findViewById<Button>(R.id.btn_test_producer_consumer).setOnClickListener {
            TestUtils.testProducerConsumer()
        }
    
        findViewById<Button>(R.id.btn_factory).setOnClickListener {
            TestUtils.testFactory()
        }
    }
}