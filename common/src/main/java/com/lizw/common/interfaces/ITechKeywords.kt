package com.lizw.common.interfaces

/**
 * 通过实现这个，说明这个类用到了哪些技术点，涉及到了哪些东西，方便后面复用
 * 技术栈：
 * author: zongwei.li created on: 2023/8/12
 */
interface ITechKeywords {
    /**
     * 返回用到的技术关键词集合
     */
    fun getTechKeywords(): Array<String>
}