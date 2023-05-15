package com.lizw.core_apis.java.thread

import android.util.Log

/**
 *
 *
 * author:lizw
 * created on: 2022/7/3
 */
class SleeperThread(name: String, private val sleepTime: Long) : Thread(name) {

    override fun run() {
        try {
            sleep(sleepTime)
        } catch (e: InterruptedException) {
            Log.i("joinTest", "$name is interrupted, isInterrupted=$isInterrupted")
        }
        Log.i("joinTest", "$name is finished")
    }
}