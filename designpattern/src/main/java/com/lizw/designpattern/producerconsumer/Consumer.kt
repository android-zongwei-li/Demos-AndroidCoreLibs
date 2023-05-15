package com.lizw.designpatterns.producerconsumer

import android.util.Log
import java.util.concurrent.BlockingQueue

/**
 * 消费者
 *
 * author:lizw
 * created on: 2022/6/26
 */
class Consumer(private val blockingQueue: BlockingQueue<String>) : Runnable {

    override fun run() {
        for (index in 1..10) {
            Thread.sleep(1000L)
            println("消费 - ${blockingQueue.size}")

            val product = blockingQueue.take()
//            Log.i("consumer","消费 - $product")
            println("消费 - $product , ${blockingQueue.size}")
        }
    }

}