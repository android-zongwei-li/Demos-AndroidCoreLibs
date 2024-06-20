package com.lizw.designpattern.dynamic_proxy

/**
 * 作用：
 * 技术栈：
 */
class TestImpl : ITest {
    override fun addNewData() {
        println("开始插入新数据")
    }
    
    override fun addNewData2(text: String) {
        println("开始插入新数据2 is $text")
    }
}