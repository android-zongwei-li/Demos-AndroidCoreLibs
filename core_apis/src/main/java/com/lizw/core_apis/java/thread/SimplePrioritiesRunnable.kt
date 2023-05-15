package com.lizw.core_apis.java.thread

import android.util.Log

/**
 *
 *
 * author:lizw
 * created on: 2022/7/3
 */
class SimplePrioritiesRunnable(private val priority: Int) : Runnable {
    private var countDown = 5

    override fun run() {
        Thread.currentThread().priority = priority

        // 模拟耗时
        var d = 0.0
        while (true) {
            for (i in 1..100000) {
                d += (Math.PI + Math.E) / i
                // 多次让步，看竞争结果。
                // 有yield()的情况下，高优先级的基本不会最后结束
                // 没有的时候，测了不到5次，就出现高优先级最后结束
                if (i % 1000 == 0) {
                    Thread.yield()
                }
            }
            Log.i("info", this.toString())
            if (--countDown == 0) {
                break
            }
        }
        Log.i("info", this.toString() + "finished")
    }

    override fun toString(): String {
        return "${Thread.currentThread()} #p${Thread.currentThread().priority}: $countDown"
    }
}