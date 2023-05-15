package com.lizw.core_apis.java.thread

import android.util.Log

/**
 * 《Java 编程思想》并发demo。
 *
 * author:lizw
 * created on: 2022/7/1
 */
class LiftOffRunnable(private var countDown: Int = 10) : Runnable {
    companion object {
        private var taskCount = 0
    }

    /**
     * Q1：taskCount++操作是非原子性的，所以在不同线程创建LiftOffRunnable，可能会导致id重复
     */
    private val id = taskCount++

    private fun status(): String {
        val status = if (countDown > 0) countDown else "LiftOff!"
        return "#id($id)($status)"
    }

    override fun run() {
        val name = Thread.currentThread().name
        while (countDown-- > 0) {
            timeConsuming()
            Log.i(name, status())
            Thread.yield()
        }
    }

    /**
     * 模拟耗时任务
     */
    private fun timeConsuming() {
        var sum = 0
        for (i in 1..100000) {
            sum += i
        }
    }
}