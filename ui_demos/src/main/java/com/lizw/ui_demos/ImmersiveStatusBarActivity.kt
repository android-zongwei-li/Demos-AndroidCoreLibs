package com.lizw.ui_demos

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.lizw.ui_demos.databinding.ActivityImmersiveStatusBarBinding

/**
 * 沉浸式状态栏
 * 即：状态栏设置为透明，然后将内容延伸到状态栏下方
 */
class ImmersiveStatusBarActivity : AppCompatActivity() {
    private val binding by lazy { ActivityImmersiveStatusBarBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        } else {
            // ??
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            // WindowInsetsCompat.Type.displayCutout() 考虑了横屏下的刘海屏显示效果，可以横屏的话要设置
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 修改状态栏的背景色
//        window.statusBarColor = ContextCompat.getColor(this, R.color.colorAccent)

        foreground()

//        hideSystemBars()
    }

    // 隐藏系统栏，包括状态栏和导航栏
    private fun hideSystemBars() {
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    private fun foreground() {
        // 控制状态栏内容（例如时间、电池图标、通知图标）的外观：
        val windowInsetsControllerSta = WindowCompat.getInsetsController(window, window.decorView)
        // 状态栏-黑色外观
//        windowInsetsControllerSta.isAppearanceLightStatusBars = true
        // 状态栏-白色外观
        windowInsetsControllerSta.isAppearanceLightStatusBars = false

        // 控制导航栏内容（例如返回、主页、最近应用按钮）的外观：
        val windowInsetsControllerNav = WindowCompat.getInsetsController(window, window.decorView)
        // 导航栏-黑色外观
//        windowInsetsControllerNav.isAppearanceLightNavigationBars = true
        // 导航栏-白色外观
        windowInsetsControllerNav.isAppearanceLightNavigationBars = false
    }
}