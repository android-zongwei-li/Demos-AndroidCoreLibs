package com.lizw.ui_demos.customview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import com.lizw.ui_demos.R
import com.lizw.ui_demos.databinding.ActivityPreviewBinding

/**
 * 各种自定义View的预览页面。
 */
class PreviewActivity : AppCompatActivity() {
    companion object {
        private const val PREVIEW_VIEW = "show_view"
        
        /**
         * 要新增支持的控件，在此属性中添加即可
         */
        private val viewList by lazy {
            val viewList = arrayListOf<CustomViewItemBean>()
            viewList.add(CustomViewItemBean(R.id.preview_view_basis, "基础控件",
                    "控件基础，Canvas使用", "paint, canvas"))
            viewList.add(CustomViewItemBean(R.id.preview_animWaveView, "波浪",
                    "", ""))
            viewList.add(CustomViewItemBean(R.id.preview_clip_region_view, "控件裁剪",
                    "", "canvas.clip"))
            viewList.add(CustomViewItemBean(R.id.preview_cv_circle_view, "圆形头像",
                    "", "canvas.clip"))
            viewList.add(CustomViewItemBean(R.id.preview_track_view_normal, "手指跟踪",
                    "跟随手指画线条", "手势"))
            
            viewList
        }
        
        /**
         * 此页面支持预览的控件
         */
        fun supportedPreviewViewList(): ArrayList<CustomViewItemBean> {
            return viewList
        }
        
        fun startSelf(context: Context, @IdRes id: Int) {
            val intent = Intent(context, PreviewActivity::class.java)
            intent.putExtra(PREVIEW_VIEW, id)
            context.startActivity(intent)
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 此页面支持展示的控件 id 命名规则：以 preview_ 开头
        val viewBinding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        
        val viewId = intent.getIntExtra(PREVIEW_VIEW, 0)
        show(viewId)
    }
    
    private fun show(id: Int) {
        findViewById<View>(id).visibility = View.VISIBLE
    }
}