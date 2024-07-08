package com.lizw.core_apis

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.lizw.core_apis.databinding.ActivityBaseListOfBtnsBinding
import kotlinx.coroutines.launch

/**
 * 一个用来 debug，方便功能测试的类
 * 注：子类只需要实现两个抽象方法即可，不需要添加 UI 了
 *
 * 一个通用的具有无限个按钮的Activity，Btn数量和Btn显示文本由子类配置，点击事件交给子类处理
 */
abstract class BaseListOfBtnsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vb = ActivityBaseListOfBtnsBinding.inflate(layoutInflater)
        setContentView(vb.root)

        getItems().onEach { itemName ->
            val btn = Button(this).apply {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100)
                text = itemName
                isAllCaps = false
                setOnClickListener {
                    lifecycleScope.launch {
                        onItemClick(itemName)
                    }
                }
            }
            vb.container.addView(btn)
        }
    }

    /**
     * return 提供每个按钮的显示文本
     */
    abstract fun getItems(): List<String>

    /**
     * 在这个方法中处理 item 的点击事件
     * @param itemName 被点击的 item
     */
    abstract fun onItemClick(itemName: String)
}