package com.lizw.core_apis.android

import com.lizw.common.ext.startActivityDefaultIntent
import com.lizw.core_apis.BaseListOfBtnsV2Activity
import com.lizw.core_apis.android.broadcast_receiver.BroadcastDemoActivity

class AndroidDemoActivity : BaseListOfBtnsV2Activity() {
    override fun getItems(): Map<String, () -> Unit> {
        return mapOf(
            "广播" to { startActivityDefaultIntent(BroadcastDemoActivity::class.java) }
        )
    }
}