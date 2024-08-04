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
abstract class BaseListOfBtnsV2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vb = ActivityBaseListOfBtnsBinding.inflate(layoutInflater)
        setContentView(vb.root)

        getItems().onEach { item ->
            val btn = Button(this).apply {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100)
                text = item.key
                isAllCaps = false
                setOnClickListener {
                    item.value.invoke()
                }
            }
            vb.container.addView(btn)
        }
    }

    /**
     * @return 提供一个 Map 集合。Key 用来显示按钮的文本；Value 是按钮的点击事件。
     */
    abstract fun getItems(): Map<String, () -> Unit>

}