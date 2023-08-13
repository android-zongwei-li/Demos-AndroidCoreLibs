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
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.lizw.common.interfaces.ITechKeywords
import com.lizw.ui_demos.R
import com.lizw.ui_demos.databinding.ActivityFloatWindowBinding

/**
 * 权限要求：
 * <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
 */
class FloatWindowActivity : AppCompatActivity(), ITechKeywords {
    private val viewBinding by lazy { ActivityFloatWindowBinding.inflate(layoutInflater) }

    private lateinit var wm: WindowManager

    private val launcherForWindowPermission =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            initView()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                initView()
            } else {
                launcherForWindowPermission.launch(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION))
            }
        } else {
            initView()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        val imageView = ImageView(this)
        viewBinding.btnAddView.setOnClickListener {
            // notes ：type字段可以设置是否响应触摸等，flag可以控制浮窗对事件对响应类型
            // flag https://www.csdn.net/tags/NtDakgwsMjMwMzctYmxvZwO0O0OO0O0O.html
            // FLAG_NOT_TOUCH_MODAL FLAG_NOT_FOCUSABLE ：悬浮窗内的事件悬浮窗响应，悬浮窗之外的事件由浮窗外响应
            val lp = WindowManager.LayoutParams(
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

        viewBinding.btnRemoveView.setOnClickListener {
            wm.removeView(imageView)
        }
    }

    override fun getTechKeywords(): Array<String> {
        return arrayOf("悬浮窗")
    }
}