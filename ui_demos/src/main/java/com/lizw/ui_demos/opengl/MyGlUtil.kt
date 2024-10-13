package com.lizw.ui_demos.opengl

import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer
import java.nio.ShortBuffer

object MyGlUtil {
    
    fun loadShader(type: Int, shaderCode: String): Int {
        // 创造顶点着色器类型(GLES20.GL_VERTEX_SHADER)
        // 或者是片段着色器类型 (GLES20.GL_FRAGMENT_SHADER)
        val shader = GLES20.glCreateShader(type)
        // 添加上面编写的着色器代码并编译它
        GLES20.glShaderSource(shader, shaderCode)
        GLES20.glCompileShader(shader)
        return shader
    }
    
    fun createProgramFor(vertexShader: String, fragmentShader: String): Int {
        // 创建空的OpenGL ES程序
        val program = GLES20.glCreateProgram()
        // 添加顶点着色器到程序中
        GLES20.glAttachShader(program, loadShader(GLES20.GL_VERTEX_SHADER, vertexShader))
        // 添加片段着色器到程序中
        GLES20.glAttachShader(program, loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader))
        // 创建OpenGL ES程序可执行文件
        GLES20.glLinkProgram(program)
        return program
    }
    
    fun intBuffer(arr: IntArray): IntBuffer {
        val mBuffer: IntBuffer
        // 初始化ByteBuffer，长度为arr数组的长度*4，因为一个int占4个字节
        val qbb = ByteBuffer.allocateDirect(arr.size * 4)
        // 数组排列用nativeOrder
        qbb.order(ByteOrder.nativeOrder())
        mBuffer = qbb.asIntBuffer()
        mBuffer.put(arr)
        mBuffer.position(0)
        return mBuffer
    }
    
    fun floatBuffer(arr: FloatArray): FloatBuffer {
        val mBuffer: FloatBuffer
        // 初始化ByteBuffer，长度为arr数组的长度*4，因为一个 float 占4个字节
        val qbb = ByteBuffer.allocateDirect(arr.size * 4)
        // 数组排列用nativeOrder
        qbb.order(ByteOrder.nativeOrder())
        // 从ByteBuffer创建一个浮点缓冲区
        mBuffer = qbb.asFloatBuffer()
        // 将坐标添加到FloatBuffer
        mBuffer.put(arr)
        // 设置缓冲区来读取第一个坐标
        mBuffer.position(0)
        return mBuffer
    }
    
    fun shortBuffer(arr: ShortArray): ShortBuffer {
        val mBuffer: ShortBuffer
        // 初始化ByteBuffer，长度为arr数组的长度*2，因为一个short占2个字节
        val dlb = ByteBuffer.allocateDirect( // (# of coordinate values * 2 bytes per short)
                arr.size * 2)
        dlb.order(ByteOrder.nativeOrder())
        mBuffer = dlb.asShortBuffer()
        mBuffer.put(arr)
        mBuffer.position(0)
        return mBuffer
    }
    
    
    
    fun initTextureParams() {
        GLES20.glTexParameteri(
                GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR
        )
        GLES20.glTexParameteri(
                GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR
        )
        GLES20.glTexParameteri(
                GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE
        )
        GLES20.glTexParameteri(
                GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE
        )
    }
}