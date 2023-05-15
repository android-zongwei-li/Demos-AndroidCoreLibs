package com.lizw.designpattern.factory

import android.util.Log
import com.lizw.designpattern.factory.Shape

/**
 *
 * author: zongwei.li created on: 2022/7/7
 */
class Square : Shape {
    override fun draw() {
        Log.i("factory pattern", "square")
    }
}