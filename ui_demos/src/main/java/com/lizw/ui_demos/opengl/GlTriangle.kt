package com.lizw.ui_demos.opengl

import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 */
class GlTriangle {
    companion object {
        // number of coordinates per vertex in this array
        const val COORDS_PER_VERTEX = 3
        var triangleCoords = floatArrayOf( // in counterclockwise order:
                0.0f, 0.5f, 0.0f,  // top
                -0.5f, -0.5f, 0.0f,  // bottom left
                0.5f, -0.5f, 0.0f // bottom right
        )
        
        // private const val vertexShaderCode = "attribute vec4 vPosition;" +
        //         "void main() {" +
        //         "  gl_Position = vPosition;" +
        //         "}"
        //
        
        // private const val vertexShaderCode =  // This matrix member variable provides a hook to manipulate
        //     // the coordinates of the objects that use this vertex shader
        //     "uniform mat4 uMVPMatrix;" +
        //             "attribute vec4 vPosition;" +
        //             "void main() {" +  // the matrix must be included as a modifier of gl_Position
        //             // Note that the uMVPMatrix factor *must be first* in order
        //             // for the matrix multiplication product to be correct.
        //             "  gl_Position = uMVPMatrix * vPosition;" +
        //             "}"
        //
        // private const val fragmentShaderCode = "precision mediump float;" +
        //         "uniform vec4 vColor;" +
        //         "void main() {" +
        //         "  gl_FragColor = vColor;" +
        //         "}"
        
        private const val vertexShaderCode = "attribute vec4 vPosition;" +
                "uniform mat4 uMVPMatrix;" +
                "varying  vec4 vColor;" +
                "attribute vec4 aColor;" +
                "void main() {" +
                "  gl_Position = uMVPMatrix*vPosition;" +
                "  vColor=aColor;" +
                "}"
        
        private const val fragmentShaderCode = "precision mediump float;" +
                "varying vec4 vColor;" +
                "void main() {" +
                "  gl_FragColor = vColor;" +
                "}"
    }
    
    private val vertexBuffer: FloatBuffer
    
    private val colorBuffer: FloatBuffer
    
    // Set color with red, green, blue and alpha (opacity) values
    // var color = floatArrayOf(255f, 0f, 0f, 1.0f)
    var color = floatArrayOf(
            1.0f, 0f, 0f, 1.0f,
            0f, 1.0f, 0f, 1.0f,
            0f, 0f, 1.0f, 1.0f
    )
    
    private var mProgram = 0
    
    init {
        // 初始化ByteBuffer，长度为arr数组的长度*4，因为一个float占4个字节
        val bb = ByteBuffer.allocateDirect(triangleCoords.size * 4)
        // 数组排列用nativeOrder
        bb.order(ByteOrder.nativeOrder())
        // 从ByteBuffer创建一个浮点缓冲区
        vertexBuffer = bb.asFloatBuffer()
        // 将坐标添加到FloatBuffer
        vertexBuffer.put(triangleCoords)
        // 设置缓冲区来读取第一个坐标
        vertexBuffer.position(0)
        
        val dd = ByteBuffer.allocateDirect(
                color.size * 4)
        dd.order(ByteOrder.nativeOrder())
        colorBuffer = dd.asFloatBuffer()
        colorBuffer.put(color)
        colorBuffer.position(0)
        
        //数据转换
        val vertexShader: Int = MyGlUtil.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader: Int = MyGlUtil.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)
        // 创建空的OpenGL ES程序
        mProgram = GLES20.glCreateProgram()
        // 添加顶点着色器到程序中
        GLES20.glAttachShader(mProgram, vertexShader)
        // 添加片段着色器到程序中
        GLES20.glAttachShader(mProgram, fragmentShader)
        // 创建OpenGL ES程序可执行文件
        GLES20.glLinkProgram(mProgram)
    }
    
    private var mPositionHandle = 0
    private var mColorHandle = 0
    
    // Use to access and set the view transformation
    private var mMVPMatrixHandle = 0
    
    private val vertexCount: Int = triangleCoords.size / COORDS_PER_VERTEX
    private val vertexStride = COORDS_PER_VERTEX * 4 // 4 bytes per vertex
    
    fun draw() {
        // 将程序添加到 OpenGL ES环境
        GLES20.glUseProgram(mProgram)
        
        // 获取顶点着色器的位置的句柄
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition")
        
        // 启用三角形顶点位置的句柄
        GLES20.glEnableVertexAttribArray(mPositionHandle)
        
        //准备三角形坐标数据
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer)
        
        // 获取片段着色器的颜色的句柄
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor")
        
        
        // 设置绘制三角形的颜色
        GLES20.glUniform4fv(mColorHandle, 1, color, 0)
        
        // 绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)
        
        // 禁用顶点数组
        GLES20.glDisableVertexAttribArray(mPositionHandle)
    }
    
    fun draw(mvpMatrix: FloatArray) {
        
        // 将程序添加到 OpenGL ES环境
        GLES20.glUseProgram(mProgram)
        
        // 获取顶点着色器的位置的句柄
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition")
        
        // 启用三角形顶点位置的句柄
        GLES20.glEnableVertexAttribArray(mPositionHandle)
        
        //准备三角形坐标数据
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer)
        
        
        // 得到形状的变换矩阵的句柄
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        
        // 将投影和视图转换传递给着色器
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        
        
        
        //
        // // 获取片段着色器的颜色的句柄
        // mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor")
        //
        // // 设置绘制三角形的颜色
        // GLES20.glUniform4fv(mColorHandle, 1, color, 0)
        
        //获取片元着色器的vColor成员的句柄
        mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");
        //设置绘制三角形的颜色
        GLES20.glEnableVertexAttribArray(mColorHandle);
        GLES20.glVertexAttribPointer(mColorHandle,4,
                GLES20.GL_FLOAT,false,
                0,colorBuffer);
        
        
        
        // 绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)
        
        // 禁用顶点数组
        GLES20.glDisableVertexAttribArray(mPositionHandle)
    }
}