package com.lizw.ui_demos.opengl

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.GLUtils
import android.opengl.Matrix
import android.util.Log
import com.lizw.ui_demos.R
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class ATextureRenderer(private val context: Context) : GLSurfaceView.Renderer {
    companion object {
        private const val TAG = "TextureRenderer"
        
        private val TEX_VERTICES = floatArrayOf(
                0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f
        )
        
        private val POS_VERTICES = floatArrayOf(
                -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f
        )
        
        private val POS_VERTICES2 = floatArrayOf(
                -0.05f, -0.05f, 0.05f, -0.05f, -0.05f, 0.05f, 0.05f, 0.05f
        )
    }
    
    private var textureId1: Int = 0
    private var textureId2: Int = 0
    
    private val aTextureGlProgram = ATextureGlProgram()
    private var mProgram = 0
    private var mTexSamplerHandle = 0
    private var mTexCoordHandle = 0
    private var mPosCoordHandle = 0
    private var mMVPMatrixHandle = 0
    
    // Setup coordinate buffers
    private var mTexVertices: FloatBuffer = MyGlUtil.floatBuffer(TEX_VERTICES)
    private var mPosVertices: FloatBuffer = MyGlUtil.floatBuffer(POS_VERTICES)
    
    private var mPosVertices2: FloatBuffer = MyGlUtil.floatBuffer(POS_VERTICES2)
    
    private var mViewWidth = 0
    private var mViewHeight = 0
    
    private var mTexWidth = 0
    private var mTexHeight = 0
    
    private var initialized = false
    
    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private val mProjectionMatrix = FloatArray(16)
    private val mViewMatrix = FloatArray(16)
    private val mMVPMatrix = FloatArray(16)
    
    // 旋转
    private val mRotationMatrix = FloatArray(16)
    private val translateMatrix = FloatArray(16)
    private val scaleMatrix = FloatArray(16)
    
    private fun init() {
        if (!initialized) {
            
            aTextureGlProgram.init()
            aTextureGlProgram.apply {
                mProgram = program
                // Bind attributes and uniforms
                mTexSamplerHandle = uTexSamplerHandle
                mTexCoordHandle = aTexCoordHandle
                mPosCoordHandle = aPosCoordHandle
                // 得到形状的变换矩阵的句柄
                mMVPMatrixHandle = uMVPMatrixHandle
            }
            
            initialized = true
        }
    }
    
    fun tearDown() {
        GLES20.glDeleteProgram(mProgram)
    }
    
    //调整AspectRatio 保证landscape和portrait的时候显示比例相同，图片不会被拉伸
    private fun computeOutputVertices() {
        val imgAspectRatio = mTexWidth / mTexHeight.toFloat()
        val viewAspectRatio = mViewWidth / mViewHeight.toFloat()
        val relativeAspectRatio = viewAspectRatio / imgAspectRatio
        val x0: Float
        val y0: Float
        val x1: Float
        val y1: Float
        if (relativeAspectRatio > 1.0f) {
            x0 = -1.0f / relativeAspectRatio
            y0 = -1.0f
            x1 = 1.0f / relativeAspectRatio
            y1 = 1.0f
        } else {
            x0 = -1.0f
            y0 = -relativeAspectRatio
            x1 = 1.0f
            y1 = relativeAspectRatio
        }
        val coords = floatArrayOf(x0, y0, x1, y0, x0, y1, x1, y1)
        mPosVertices.put(coords).position(0)
    }
    
    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        // GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.5f)
        
        textureId1 = loadTexture(R.drawable.a01)
        textureId2 = loadTexture(R.drawable.w1)
    }
    
    private fun loadTexture(resourceId: Int): Int {
        val textureIds = IntArray(1)
        GLES20.glGenTextures(1, textureIds, 0)
        val textureId = textureIds[0]
        
        val bitmap = BitmapFactory.decodeResource(context.resources, resourceId)
        
        mTexWidth = bitmap.width
        mTexHeight = bitmap.height
        
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId)
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)
        MyGlUtil.initTextureParams()
        
        bitmap.recycle()
        
        return textureId
    }
    
    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        mViewWidth = width
        mViewHeight = height
        computeOutputVertices()
        
        GLES20.glViewport(0, 0, width, height)
        
        val ratio = width.toFloat() / height
        // 这个投影矩阵被应用于对象坐标在onDrawFrame（）方法中
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f)
        
        // Set the camera position (View matrix)
        Matrix.setLookAtM(
                mViewMatrix, 0, 0f, 0f, -3f,
                0f, 0f, 0f,
                0f, 1.0f, 0.0f
        )
    }
    
    override fun onDrawFrame(gl: GL10) {
        init()
        Log.i(TAG, "onDrawFrame: onDrawFrame")
        
        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0)
        
        renderTexture(textureId1, mTexVertices, mPosVertices)
        renderTexture(textureId2, mTexVertices, mPosVertices2)
    }
    
    private fun renderTexture(texId: Int, texVertices: FloatBuffer, posVertices: FloatBuffer) {
        GLES20.glVertexAttribPointer(
                mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, texVertices
        )
        GLES20.glVertexAttribPointer(
                mPosCoordHandle, 2, GLES20.GL_FLOAT, false, 0, posVertices
        )
        
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId) //把已经处理好的Texture传到GL上面
        
        // 将投影和视图转换传递给着色器
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
    }
}
