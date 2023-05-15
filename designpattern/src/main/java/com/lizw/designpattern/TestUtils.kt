package com.lizw.designpatterns

import com.lizw.designpattern.factory.ShapeFactory
import com.lizw.designpatterns.producerconsumer.Consumer
import com.lizw.designpatterns.producerconsumer.Producer
import java.util.concurrent.ArrayBlockingQueue

/**
 * 用于
 *
 * author:lizw
 * created on: 2022/6/26
 */
object TestUtils {

    @JvmStatic
    fun main(args: Array<String>) {
        testProducerConsumer()
    }

    fun testProducerConsumer() {
        val blockingQueue = ArrayBlockingQueue<String>(100)
        Thread(Producer(blockingQueue)).start()
        Thread(Consumer(blockingQueue)).start()
        Thread(Producer(blockingQueue)).start()
        Thread(Consumer(blockingQueue)).start()
        Thread(Producer(blockingQueue)).start()
        Thread(Consumer(blockingQueue)).start()
    }

    fun testFactory() {
        val shapeFactory = ShapeFactory()
        shapeFactory.getShape(ShapeFactory.ShapeType.CIRCLE).draw()
        shapeFactory.getShape(ShapeFactory.ShapeType.SQUARE).draw()
        shapeFactory.getShape(ShapeFactory.ShapeType.RECTANGLE).draw()
    }
}