package com.lizw.ui_demos.opengl

import android.opengl.GLES20

/**
 */
class AGLProgram {
    companion object {
        private const val vertexShader = "attribute vec4 vPosition;" +
                "uniform mat4 uMVPMatrix;" +
                "varying  vec4 vColor;" +
                "attribute vec4 aColor;" +
                "void main() {" +
                "  gl_Position = uMVPMatrix*vPosition;" +
                "  vColor=aColor;" +
                "}"
        
        private const val fragmentShader = "precision mediump float;" +
                "varying vec4 vColor;" +
                "void main() {" +
                "  gl_FragColor = vColor;" +
                "}"
    }
    
    private var program = MyGlUtil.createProgramFor(vertexShader, fragmentShader)
    
    // 获取顶点着色器的位置的句柄
    var aPositionHandle = GLES20.glGetAttribLocation(program, "vPosition")
    
    //获取片元着色器的vColor成员的句柄
    var aColorHandle = GLES20.glGetAttribLocation(program, "aColor")
    
    // Use to access and set the view transformation
    // 得到形状的变换矩阵的句柄
    var uMVPMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix")
    
    init {
        // 将程序添加到 OpenGL ES环境
        GLES20.glUseProgram(program)
        
        // 启用三角形顶点位置的句柄
        GLES20.glEnableVertexAttribArray(aPositionHandle)
        
        GLES20.glEnableVertexAttribArray(aColorHandle)
    }
}