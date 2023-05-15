package com.lizw.core_apis.java.thread

import java.util.concurrent.Callable

/**
 * 带返回值的任务。
 *
 * author:lizw
 * created on: 2022/7/2
 */
class TaskWithResultCallable(private val id: Int) : Callable<String> {
    override fun call(): String {
        Thread.sleep(4000L)
        return "id = $id"
    }
}