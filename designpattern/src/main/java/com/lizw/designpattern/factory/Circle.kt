package com.lizw.designpattern.factory

import android.util.Log

/**
 *
 * author: zongwei.li created on: 2022/7/7
 */
class Circle : Shape {
    override fun draw() {
        Log.i("factory pattern", "circle")
    }
}