package com.lizw.core_apis.android.broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 *
 */
class TestBroadcastReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "TestBroadcastReceiver"
        const val ACTION_TEST = "action.testBroadcastReceiver"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i(TAG, "onReceive: $context")
    }
}