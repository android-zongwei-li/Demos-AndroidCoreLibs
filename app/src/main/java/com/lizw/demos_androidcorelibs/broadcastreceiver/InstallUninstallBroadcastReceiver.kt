package com.lizw.demos_androidcorelibs.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log

/**
 * 应用（Apk）安装、卸载、覆盖安装监听
 */
class InstallUninstallBroadcastReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "InstallBroadcastReceive"
        
        fun register(context: Context) {
            val filter = IntentFilter()
            filter.addAction(Intent.ACTION_PACKAGE_ADDED)
            filter.addAction(Intent.ACTION_PACKAGE_REMOVED)
            filter.addAction(Intent.ACTION_PACKAGE_REPLACED)
            filter.addAction(Intent.ACTION_PACKAGE_DATA_CLEARED)
            filter.addDataScheme("package")
            val r = InstallUninstallBroadcastReceiver()
            context.registerReceiver(r, filter)
        }
    }
    
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action.toString()
        
        // intent.dataString 示例： package:com.cainiao.wireless
        val pkgNameWithPrefix = intent.dataString ?: return
        val pkgName = pkgNameWithPrefix.substring(8)
        Log.i(TAG, "action = $action")
        Log.i(TAG, pkgName)
        
        // com.meizu.notepaper
    }
}