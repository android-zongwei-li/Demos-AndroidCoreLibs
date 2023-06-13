package com.lizw.ui_demos

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lizw.ui_demos.floatwindow.FloatWindowActivity
import com.lizw.ui_demos.animation.AnimationActivity
import com.lizw.ui_demos.colorpicker.ColorChoiceAdapter
import com.lizw.ui_demos.customview.CustomViewActivity
import com.lizw.ui_demos.customview.PreviewActivity
import com.lizw.ui_demos.customview.weatherview.WeatherActivity
import com.lizw.ui_demos.gesture.GestureActivity
import com.lizw.ui_demos.loading.LoadingActivity
import com.lizw.ui_demos.recyclerview.RecyclerViewActivity

class UiHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ui_home)
        
        val dataMap = HashMap<Class<*>, String>()
        dataMap[AnimationActivity::class.java] = getString(R.string.animation_page)
        dataMap[PreviewActivity::class.java] = "custom view"
        dataMap[GestureActivity::class.java] = getString(R.string.gesture_page)
        dataMap[FloatWindowActivity::class.java] = "悬浮窗"
        dataMap[WeatherActivity::class.java] = "天气"
        dataMap[LoadingActivity::class.java] = "loading"
        dataMap[CustomViewActivity::class.java] = "自定义View"
        dataMap[RecyclerViewActivity::class.java] = "RecyclerView"
        
        val rvMainUI = findViewById<RecyclerView>(R.id.rv_main_ui)
        rvMainUI.layoutManager = LinearLayoutManager(this)
        rvMainUI.adapter = MainUIAdapter(data = dataMap)
        
        findViewById<Button>(R.id.btn_quick_test).setOnClickListener {
            AlertDialog.Builder(this).apply {
                val view = View.inflate(this@UiHomeActivity, R.layout.dialog_view_1, null)
                val llContainer = view.findViewById<LinearLayoutCompat>(R.id.ll_container)
                llContainer.layoutMode = ViewGroup.LAYOUT_MODE_OPTICAL_BOUNDS
                
                val rvColorMultiChoice = llContainer.findViewById<RecyclerView>(R.id.rv_color_multi_choice)
                val colorArray = context.resources.getIntArray(R.array.defaultColorArray)
                rvColorMultiChoice.adapter = ColorChoiceAdapter(colorArray)
                rvColorMultiChoice.layoutManager = GridLayoutManager(this@UiHomeActivity, colorArray.size, RecyclerView.VERTICAL, false)
                setView(llContainer)
                
                llContainer.findViewById<Button>(R.id.btn_positive).setOnClickListener {
                
                }
            }.show()
        }
    }
}