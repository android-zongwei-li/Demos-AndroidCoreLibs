package com.lizw.demos_androidcorelibs

import android.content.Intent
import android.os.Bundle
import com.lizw.core_apis.BaseListOfBtnsV2Activity
import com.lizw.core_apis.CoreApisMainActivity
import com.lizw.demos_androidcorelibs.aidl.AidlClientActivity
import com.lizw.demos_androidcorelibs.broadcastreceiver.InstallUninstallBroadcastReceiver
import com.lizw.ui_demos.ImmersiveStatusBarActivity
import com.lizw.ui_demos.ProgressBarActivity
import com.lizw.ui_demos.UiHomeActivity

/**
 * 是否在debug
 */
const val debug = true

/**
 * 在debug模式下，指定要debug的页面，用于快速启动
 */
val debug_activity = ProgressBarActivity::class.java

class MainActivity : BaseListOfBtnsV2Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (debug) {
            startActivity(Intent(this, debug_activity))
        }
    }

    override fun getItems(): Map<String, () -> Unit> {
        return mapOf(
            "UI示例" to { startActivity(Intent(this, UiHomeActivity::class.java)) },
            "核心接口" to { startActivity(Intent(this, CoreApisMainActivity::class.java)) },
            "AIDL" to { startActivity(Intent(this, AidlClientActivity::class.java)) },
            "注册Apk安装、卸载" to { InstallUninstallBroadcastReceiver.register(this) },
        )
    }
}