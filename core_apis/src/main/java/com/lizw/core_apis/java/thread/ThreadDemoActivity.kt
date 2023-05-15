package com.lizw.core_apis.java.thread

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.lizw.core_apis.R

class ThreadDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread_demo)

        findViewById<Button>(R.id.btn_test).setOnClickListener {
//            TestUtils.testPriority()
            TestUtils.testJoin()
        }

        findViewById<Button>(R.id.btn_task_one).setOnClickListener {
//            LiftOffManager.runATask()

            CallableManager.runATask()
        }

        findViewById<Button>(R.id.btn_task_more).setOnClickListener {
//            LiftOffManager.runMoreTask()

            CallableManager.runMoreTaskWithResult()
        }

        findViewById<Button>(R.id.btn_interrupted).setOnClickListener {
            TestUtils.interruptedS1()
        }
    }
}