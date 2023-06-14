package com.lizw.ui_demos.customview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lizw.ui_demos.customview.weatherview.WeatherActivity
import com.lizw.ui_demos.databinding.ActivityCustomViewBinding
import com.lizw.ui_demos.loading.LoadingActivity

/**
 * 此页面提供一系列自定义控件。
 * 介绍控件实现原理——涉及哪些技术点。
 */
class CustomViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityCustomViewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        
        viewBinding.rvCustomView.apply {
            layoutManager = LinearLayoutManager(this@CustomViewActivity)
            
            val showViews = PreviewActivity.supportedPreviewViewList()
            showViews.apply {
                // 添加额外的自定义View（不需要跳转到PreviewActivity预览的View）
                add(CustomViewItemBean(0, "天气", "天气控件", "自定义View", WeatherActivity::class.java))
                add(CustomViewItemBean(0, "加载loading", "加载动画", "动画", LoadingActivity::class.java))
            }
            
            adapter = CustomViewRVAdapter(showViews)
        }
    }
}