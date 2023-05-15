package com.lizw.core_apis.java.thread

import android.util.Log

/**
 *
 *
 * author:lizw
 * created on: 2022/7/3
 */
class JoinerThread(name: String, private val thread: Thread) : Thread(name) {

    override fun run() {
        try {
            thread.join()
        } catch (e: InterruptedException) {
            Log.i("joinTest", "$name is interrupted, isInterrupted=$isInterrupted")
        }
        Log.i("joinTest", "$name is finished")
    }
}