package com.lizw.ui_demos.opengl

import android.opengl.GLES20

/**
 * 一个支持绘制图片的 Gl 程序
 */
class ATextureGlProgram {

    companion object {
        private const val VERTEX_SHADER =
            "attribute vec4 a_position;\n" +
                    "attribute vec2 a_texcoord;\n" +
                    "varying vec2 v_texcoord;\n" +
                    "uniform mat4 uMVPMatrix;\n" +
                    "void main() {\n" +
                    "  gl_Position = uMVPMatrix * a_position;\n" +
                    "  v_texcoord = a_texcoord;\n" +
                    "}\n"
        
        private const val FRAGMENT_SHADER =
            "precision mediump float;\n" +
                    "uniform sampler2D tex_sampler;\n" +
                    "varying vec2 v_texcoord;\n" +
                    "void main() {\n" +
                    "  gl_FragColor = texture2D(tex_sampler, v_texcoord);\n" +
                    "}\n"
    }
    
    var program = 0
    
    var uTexSamplerHandle = 0
    var aTexCoordHandle = 0
    var aPosCoordHandle = 0
    var uMVPMatrixHandle = 0
    
    init {
        // MyGlUtil.initTexParams()
        // GLES20.glUseProgram(program)
    }
    
    fun init() {
        program = MyGlUtil.createProgramFor(VERTEX_SHADER, FRAGMENT_SHADER)
        uTexSamplerHandle = GLES20.glGetUniformLocation(program, "tex_sampler")
        aTexCoordHandle = GLES20.glGetAttribLocation(program, "a_texcoord")
        aPosCoordHandle = GLES20.glGetAttribLocation(program, "a_position")
        uMVPMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix")
        
        GLES20.glEnableVertexAttribArray(aTexCoordHandle)
        
        GLES20.glEnableVertexAttribArray(aPosCoordHandle)
        
        GLES20.glUseProgram(program)
        
    }
    
    
}