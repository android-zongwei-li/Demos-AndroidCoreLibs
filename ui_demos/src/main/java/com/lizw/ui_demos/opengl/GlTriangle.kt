package com.lizw.ui_demos.opengl

import android.opengl.GLES20
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
        val vertexCount: Int = triangleCoords.size / COORDS_PER_VERTEX
        
        // Set color with red, green, blue and alpha (opacity) values
        // var color = floatArrayOf(255f, 0f, 0f, 1.0f)
        var color = floatArrayOf(
                1.0f, 0f, 0f, 1.0f,
                0f, 1.0f, 0f, 1.0f,
                0f, 0f, 1.0f, 1.0f
        )
    }
    
    private val vertexBuffer: FloatBuffer = MyGlUtil.floatBuffer(triangleCoords)
    private val colorBuffer: FloatBuffer = MyGlUtil.floatBuffer(color)
    
    private val aGlProgram = AGLProgram()

    fun draw(mvpMatrix: FloatArray) {
        aGlProgram.apply {
            // 设置三角形坐标数据
            GLES20.glVertexAttribPointer(aPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false,
                    COORDS_PER_VERTEX * 4, vertexBuffer)
            
            //设置绘制三角形的颜色
            GLES20.glVertexAttribPointer(aColorHandle, 4, GLES20.GL_FLOAT, false, 0,
                    colorBuffer)
            
            // 将投影和视图转换传递给着色器
            GLES20.glUniformMatrix4fv(uMVPMatrixHandle, 1, false, mvpMatrix, 0)
        }
        
        // 绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)
    }
}