package com.lizw.ui_demos

import com.lizw.ui_demos.webview.WebViewActivity

/**
 * 配置要debug的页面
 *
 * NotesUrl：
 * 技术点：
 */
object Configs {
    /**
     * 是否在debug
     */
    val debug = true

    /**
     * 在debug模式下，指定要debug的页面，用于快速启动
     */
    val debug_activity = WebViewActivity::class.java
}