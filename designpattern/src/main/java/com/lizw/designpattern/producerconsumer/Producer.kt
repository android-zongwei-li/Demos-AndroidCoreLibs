package com.lizw.designpatterns.producerconsumer

import android.util.Log
import java.util.concurrent.BlockingQueue

/**
 * 生产者
 *
 * author:lizw
 * created on: 2022/6/26
 */
class Producer(private val blockingQueue: BlockingQueue<String>) : Runnable {

    override fun run() {
        for (index in 1..10) {
            Thread.sleep(1000L)
            val product = Thread.currentThread().name + " - $index"
            blockingQueue.put(product)
//            Log.i("producer", "生产 - $product")
            println("生产 - $product , ${blockingQueue.size}")
        }
    }
}