package com.lizw.ui_demos.floatwindow

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.lizw.ui_demos.R

/**
 * 权限要求：
 * <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
 */
class FloatWindowActivity : AppCompatActivity() {
    private lateinit var wm: WindowManager
    
    private lateinit var btnCreateWindow: Button
    private lateinit var btnRemoveWindow: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_float_window)
        
        wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                initView()
            } else {
                startActivityForResult(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION), 1)
            }
        } else {
            initView()
        }
    }
    
    lateinit var lp: WindowManager.LayoutParams
    
    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        btnCreateWindow = findViewById(R.id.btn_add_view)
        btnRemoveWindow = findViewById(R.id.btn_remove_view)
        
        val imageView = ImageView(this)
        btnCreateWindow.setOnClickListener {
            // notes ：type字段可以设置是否响应触摸等，flag可以控制浮窗对事件对响应类型
            // flag https://www.csdn.net/tags/NtDakgwsMjMwMzctYmxvZwO0O0OO0O0O.html
            // FLAG_NOT_TOUCH_MODAL FLAG_NOT_FOCUSABLE ：悬浮窗内的事件悬浮窗响应，悬浮窗之外的事件由浮窗外响应
            lp = WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    2099,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSPARENT)
            lp.apply {
                type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                } else {
                    WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;// 系统提示window
                }
                
                gravity = Gravity.TOP or Gravity.LEFT
                x = 0
                y = 300
            }
            
            imageView.setBackgroundResource(R.mipmap.ic_launcher)
            imageView.setOnTouchListener { v, event ->
                val rawX = event.rawX
                when (event.action) {
                    MotionEvent.ACTION_MOVE -> {
                        lp.x = rawX.toInt()
                        lp.y = event.rawY.toInt()
                        wm.updateViewLayout(imageView, lp)
                    }
                    else -> {}
                }
                false
            }
         
            // todo 不能重复添加，会crash
            wm.addView(imageView, lp)
        }
        
        btnRemoveWindow.setOnClickListener {
            wm.removeView(imageView)
        }
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            initView()
        }
    }
}