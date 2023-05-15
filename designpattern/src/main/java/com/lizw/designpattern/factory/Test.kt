package com.lizw.designpattern.factory

/**
 *
 * author: zongwei.li created on: 2022/7/7
 */
object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        val shapeFactory = ShapeFactory()
        shapeFactory.getShape(ShapeFactory.ShapeType.CIRCLE).draw()
        shapeFactory.getShape(ShapeFactory.ShapeType.SQUARE).draw()
        shapeFactory.getShape(ShapeFactory.ShapeType.RECTANGLE).draw()
    }
}