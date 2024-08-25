package com.lizw.ui_demos.opengl

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet

/**
 */
class OneGlSurfaceView : GLSurfaceView {
    // private val mRenderer: OneGlRenderer = OneGlRenderer()
    
    constructor(context: Context) : this(context, null)
    
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2)
        // Set the Renderer for drawing on the GLSurfaceView
        
        val mRenderer = ATextureRenderer(context)
        
        setRenderer(mRenderer)
    }
}