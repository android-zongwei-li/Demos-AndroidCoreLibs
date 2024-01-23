package com.lizw.ui_demos

import android.app.UiModeManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.lizw.common.interfaces.ITechKeywords
import com.lizw.ui_demos.databinding.ActivityLightNightBinding

class LightNightActivity : AppCompatActivity(), ITechKeywords {
    private val viewBinding by lazy { ActivityLightNightBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val uiModeManager = getSystemService(UI_MODE_SERVICE) as UiModeManager

        viewBinding.btnLight.setOnClickListener {
            // setNightMode 在应用内是不会生效的
//            uiModeManager.nightMode = UiModeManager.MODE_NIGHT_NO
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                uiModeManager.setApplicationNightMode(UiModeManager.MODE_NIGHT_NO)
//            }

            delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
        }

        viewBinding.btnNight.setOnClickListener {
//            uiModeManager.nightMode = UiModeManager.MODE_NIGHT_YES

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                uiModeManager.setApplicationNightMode(UiModeManager.MODE_NIGHT_YES)
//            }


            delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
        }
    }

    override fun getTechKeywords(): Array<String> {
        return arrayOf("白天、夜间模式切换")
    }
}