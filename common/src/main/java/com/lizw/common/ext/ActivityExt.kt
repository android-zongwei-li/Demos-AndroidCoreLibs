package com.lizw.common.ext

import android.app.Activity
import android.content.Intent
import android.view.ViewGroup
import android.widget.Button

/**
 * 作用：Activity 功能拓展
 * 技术栈：
 */

fun Activity.startActivityDefaultIntent(cls: Class<*>) {
    startActivity(Intent(this, cls))
}

/**
 * 添加一个测试按钮。
 * 写demo的时候，经常会需要一个测试按钮，总是去xml中添加的话，有点麻烦，
 * 通过这个方法可以更方便的添加测试按钮
 */
fun Activity.addTestButton(rootView: ViewGroup, btnText: String = "test", onClick: () -> Unit) {
    val btn = Button(this).apply {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100)
        text = btnText
        isAllCaps = false
        setOnClickListener {
            onClick.invoke()
        }
    }
    rootView.addView(btn)
}