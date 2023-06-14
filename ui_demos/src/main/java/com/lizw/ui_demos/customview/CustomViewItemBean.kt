package com.lizw.ui_demos.customview

import android.app.Activity
import androidx.annotation.IdRes

/**
 * 作用：
 * 技术栈：
 * @param id 制定View 的 id
 * @param targetClass 要跳转到的 Activity，有些控件不使用默认的 PreviewActivity 页面展示，可以制定一个 Activity 展示
 *        如果不为null，则跳转到 targetClass 页面，不考虑 id；否则跳转到 PreviewActivity
 * @param name 控件名称
 * @param info view 的介绍
 * @param techs 涉及的技术点
 */
data class CustomViewItemBean(
        @IdRes val id: Int,
        val name: String, val info: String = "", val techs: String = "",
        /**
         * 如果制定了，则调整到此页面。
         */
        val targetClass: Class<out Activity>? = null,
)
