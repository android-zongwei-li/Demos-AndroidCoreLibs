package com.lizw.designpattern.factory

/**
 *
 * author: zongwei.li created on: 2022/7/7
 */
class ShapeFactory {
    enum class ShapeType {
        CIRCLE,
        RECTANGLE,
        SQUARE
    }

    fun getShape(shapeType: ShapeType): Shape {
        return when (shapeType) {
            ShapeType.CIRCLE -> {
                Circle()
            }
            ShapeType.RECTANGLE -> {
                Rectangle()
            }
            ShapeType.SQUARE -> {
                Square()
            }
        }
    }
}