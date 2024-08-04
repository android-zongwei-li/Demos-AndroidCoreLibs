package com.lizw.core_apis.android.broadcast_receiver

import android.content.Intent
import com.lizw.core_apis.BaseListOfBtnsV2Activity

class BroadcastDemoActivity : BaseListOfBtnsV2Activity() {
    override fun getItems(): Map<String, () -> Unit> {
        return mapOf(
            "发送广播" to { sendBroadcast(Intent(TestBroadcastReceiver.ACTION_TEST)) }
        )
    }
}