package com.lizw.common.ext

import android.app.Activity
import android.content.Intent

/**
 * 作用：Activity 功能拓展
 * 技术栈：
 */

fun Activity.startActivityDefaultIntent(cls: Class<*>) {
    startActivity(Intent(this, cls))
}