package com.lizw.ui_demos.customview

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lizw.ui_demos.R

/**
 * 各种自定义View的预览页面。
 */
class PreviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)
        
        // id 从 activity_preview 中获取
        // show(R.id.view_basis, "BasisView")
        // show(R.id.track_view_normal, "track_view_normal")
        // show(R.id.view_finger, FingerView::class.java)
        
        show(R.id.animWaveView, "")
    }
    
    /**
     * 指定要预览的控件
     *
     * @param id 要展示的控件 id
     * @param title 标题，一般为控件的名称（ClassName）
     */
    private fun show(id: Int, title: String) {
        findViewById<View>(id).visibility = View.VISIBLE
        findViewById<TextView>(R.id.tv_view_title).text = title
    }
    
    /**
     * 指定要预览的控件
     *
     * @param id 要展示的控件 id
     * @param className 标题，一般为控件的名称（ClassName）
     */
    private fun show(id: Int, className: Class<*>) {
        // findViewById<FingerView>(id).apply {
        //     visibility = View.VISIBLE
        //     smoothScrollTo(-400, 0)
        // }
        // findViewById<TextView>(R.id.tv_view_title).text = className.simpleName
    }
}