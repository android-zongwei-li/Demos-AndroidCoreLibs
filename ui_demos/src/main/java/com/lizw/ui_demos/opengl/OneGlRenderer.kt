package com.lizw.ui_demos.opengl

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.SystemClock
import javax.microedition.khronos.opengles.GL10

/**
 */
class OneGlRenderer : GLSurfaceView.Renderer {
    private var mTriangle: GlTriangle? = null
    private var mSquare: GlSquare? = null
    
    // Model + View + Projection 视图
    private val mMVPMatrix = FloatArray(16)
    // 投影视图
    private val mProjectionMatrix = FloatArray(16)
    // 相机视图
    private val mViewMatrix = FloatArray(16)
    private val mRotationMatrix = FloatArray(16)
    
    override fun onSurfaceCreated(gl: GL10?, config: javax.microedition.khronos.egl.EGLConfig?) {
        // 设置背景颜色
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        
        // 初始化triangle
        mTriangle = GlTriangle()
        
        // 初始化 square
        mSquare = GlSquare()
    }
    
    override fun onSurfaceChanged(unused: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        
        val ratio = width.toFloat() / height
        
        // 这个投影矩阵被应用于对象坐标在onDrawFrame（）方法中
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f)
        
        // 定义一个相机视图
        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0f, 0f, -3f,
                0f, 0f, 0f, 0f, 1.0f, 0.0f);
    }
    
    override fun onDrawFrame(unused: GL10?) {
        // 重新绘制背景颜色，不重新绘制的话之前的图形还会存在，导致图形重叠
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        
        // 计算投影和相机变换
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0)
        
        genRotationMatrix()
        // 将旋转矩阵与投影和相机视图组合在一起
        Matrix.multiplyMM(mMVPMatrix, 0, mMVPMatrix, 0, mRotationMatrix, 0)
        
        mTriangle!!.draw(mMVPMatrix)
    }
    
    // 创建一个旋转矩阵
    private fun genRotationMatrix(): FloatArray {
        val time = SystemClock.uptimeMillis() % 4000L
        val angle = 0.090f * time.toInt()
        Matrix.setRotateM(mRotationMatrix, 0, angle, 0f, 0f, -1.0f)
        return mRotationMatrix
    }
}