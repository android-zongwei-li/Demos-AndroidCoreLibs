package com.lizw.ui_demos.opengl

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer

/**
 * 作用：
 * 技术栈：
 */
class GlSquare {
    companion object {
        // number of coordinates per vertex in this array
        const val COORDS_PER_VERTEX = 3
        var squareCoords = floatArrayOf(
                -0.5f, 0.5f, 0.0f,  // top left
                -0.5f, -0.5f, 0.0f,  // bottom left
                0.5f, -0.5f, 0.0f,  // bottom right
                0.5f, 0.5f, 0.0f) // top right
    }
    
    private val vertexBuffer: FloatBuffer
    private val drawListBuffer: ShortBuffer
    private val drawOrder = shortArrayOf(0, 1, 2, 0, 2, 3) // order to draw vertices
    
    init {
        // 初始化ByteBuffer，长度为arr数组的长度*4，因为一个float占4个字节
        val bb = ByteBuffer.allocateDirect(squareCoords.size * 4)
        bb.order(ByteOrder.nativeOrder())
        vertexBuffer = bb.asFloatBuffer()
        vertexBuffer.put(squareCoords)
        vertexBuffer.position(0)
        
        // 初始化ByteBuffer，长度为arr数组的长度*2，因为一个short占2个字节
        val dlb = ByteBuffer.allocateDirect(drawOrder.size * 2)
        dlb.order(ByteOrder.nativeOrder())
        drawListBuffer = dlb.asShortBuffer()
        drawListBuffer.put(drawOrder)
        drawListBuffer.position(0)
    }
}