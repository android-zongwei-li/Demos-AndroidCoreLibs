package com.lizw.ui_demos.loading

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.lizw.ui_demos.R

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        
        val dot = findViewById<ImageView>(R.id.iv_dot1)
        
        fun loopAnim() {
            val animator = ObjectAnimator.ofFloat(dot, "translationY", 100f, -100f)
            animator.duration = 1500
            animator.interpolator = LinearInterpolator()
            animator.doOnEnd {
                val animator2 = ObjectAnimator.ofFloat(dot, "translationY", -100f, 100f)
                animator2.duration = 1500
                animator2.interpolator = LinearInterpolator()
                animator2.doOnEnd {
                    loopAnim()
                }
                animator2.start()
            }
            animator.start()
        }
        loopAnim()
    }
}