package com.lizw.core_apis.java.thread

import java.util.concurrent.Executors

/**
 *
 *
 * author:lizw
 * created on: 2022/7/3
 */
object TestUtils {

    fun testPriority() {
        val executor = Executors.newCachedThreadPool()
        for (i in 1..5) {
            executor.execute(SimplePrioritiesRunnable(Thread.MIN_PRIORITY))
        }
        executor.execute(SimplePrioritiesRunnable(Thread.MAX_PRIORITY))
        executor.shutdown()
    }

    private lateinit var s1: Thread
    fun testJoin() {
        s1 = SleeperThread("S1", 2000)
        s1.start()
        val s2 = SleeperThread("S2", 2000)
        s2.start()
        JoinerThread("J1", s1).start()
        JoinerThread("J2", s2).start()
    }

    fun interruptedS1() {
        s1.interrupt()
    }
}