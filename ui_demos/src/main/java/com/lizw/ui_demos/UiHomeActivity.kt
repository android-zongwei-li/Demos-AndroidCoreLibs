package com.lizw.ui_demos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lizw.ui_demos.animation.AnimationActivity
import com.lizw.ui_demos.customview.CustomViewActivity
import com.lizw.ui_demos.floatwindow.FloatWindowActivity
import com.lizw.ui_demos.gesture.GestureActivity
import com.lizw.ui_demos.recyclerview.RecyclerViewActivity

class UiHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ui_home)
        
        val dataMap = HashMap<Class<*>, String>()
        dataMap[AnimationActivity::class.java] = getString(R.string.animation_page)
        dataMap[GestureActivity::class.java] = getString(R.string.gesture_page)
        dataMap[FloatWindowActivity::class.java] = "悬浮窗"
        dataMap[CustomViewActivity::class.java] = "自定义View"
        dataMap[RecyclerViewActivity::class.java] = "RecyclerView"
        
        val rvMainUI = findViewById<RecyclerView>(R.id.rv_main_ui)
        rvMainUI.layoutManager = LinearLayoutManager(this)
        rvMainUI.adapter = MainUIAdapter(data = dataMap)
    }
}