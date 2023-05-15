package com.lizw.javaapidemos.thread

import com.lizw.core_apis.java.thread.LiftOffRunnable
import java.util.concurrent.Executors

/**
 * 用来提供跟 LiftOffRunnable 有关的方法。
 * 比如创建thread
 *
 * author:lizw
 * created on: 2022/7/1
 */
object LiftOffManager {

    fun test() {

    }

    fun runATask() {
        val liftOffThread = Thread(LiftOffRunnable(), "LiftOff")
        liftOffThread.start()
    }

    fun runMoreTask() {
//        runMoreTaskByDefault()
        runMoreTaskByExecutor()
    }

    private fun runMoreTaskByDefault() {
        for (i in 1..5) {
            Thread(LiftOffRunnable()).start()
        }
    }

    /**
     * 注1：对shutdown()的调用，可以防止新任务提交给这个Executor（即executorService），
     * 并且当前线程（调用runMoreTaskByExecutor()的线程）将继续运行executorService.shutdown()
     * 调用前提交的任务。
     *
     * 注2：
     * CachedThreadPool：为每个任务都创建一个线程
     * FixedThreadPool：使用有限的线程来执行所提交的任务
     */
    private fun runMoreTaskByExecutor() {
//        val executorService = Executors.newCachedThreadPool()
//        val executorService = Executors.newFixedThreadPool(3)
        val executorService = Executors.newSingleThreadExecutor()
        for (i in 1..5) {
            executorService.execute(LiftOffRunnable())
        }
        executorService.shutdown()

        // shutdown()以后再执行任务会抛出 RejectedExecutionException 异常
//        executorService.execute(LiftOffRunnable())
    }

}