package com.lizw.core_apis.java.thread

import android.util.Log
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.Future

/**
 *
 *
 * author:lizw
 * created on: 2022/7/2
 */
object CallableManager {

    fun runATask() {
        val executorService = Executors.newCachedThreadPool()
        val future = executorService.submit(TaskWithResultCallable(100))
        Log.i("result", future.get())
    }

    fun runMoreTaskWithResult() {
        runMoreTaskByExecutorWithResult()
    }

    private fun runMoreTaskByExecutorWithResult() {
        val results = arrayListOf<Future<String>>()
        val executorService = Executors.newCachedThreadPool()
        for (i in 1..5) {
            results.add(executorService.submit(TaskWithResultCallable(i)))
        }
        results.forEach {
            try {
                Log.i("result", it.get())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            } finally {
                executorService.shutdown()
            }
        }
    }
}