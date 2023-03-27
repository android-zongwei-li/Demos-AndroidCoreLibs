package com.lizw.designpattern.chain_of_responsibility.example1

/**
 * 责任链模式基类，构成要素
 * 1、有一个指向下一个节点的引用（变量）
 * 2、有一个方法用于分发事件，判断是当前节点处理，还是交给下一个节点处理（事件驱动/分发方法）
 * 3、当前节点有一个真正的处理事件的方法（事件处理方法）
 *
 * 这个责任链上的节点
 */
abstract class Handler {
    /**
     * 下一个处理者
     */
    protected var next: Handler? = null
    
    /**
     * 请求处理
     *
     * @param request 请求
     */
    abstract fun handleRequest(request: String)
}