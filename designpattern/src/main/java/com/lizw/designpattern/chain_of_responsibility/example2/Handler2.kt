package com.lizw.designpattern.chain_of_responsibility.example2

/**
 * 责任链上的节点基类
 *
 * @param next 下一个处理者
 */
abstract class Handler2(val next: Handler2? = null) {
    
    /**
     * 负责请求的分发，用于传递请求。判断是当前节点处理，还是传递给下一个节点
     */
    fun dispatch(request: Request) {
        if (handleBySelf(request)) {
            println("${this.javaClass.simpleName} will handle request")
            handle(request)
        } else {
            println("${this.javaClass.simpleName} is not processed, will dispatch to next")
            if (next == null) {
                println("no next handler")
            } else {
                next.dispatch(request)
            }
        }
    }
    
    /**
     * 是否当前对象处理请求。子类重写此方法用于添加自己的判断逻辑
     *
     * @param request 请求
     * @return true 表示当前对象处理，会回调[handle]方法
     *         false 表示当前对象不处理。父类会自动将事件派发到下一节点
     */
    abstract fun handleBySelf(request: Request): Boolean
    
    /**
     * 处理请求。
     * 如果子类[handleBySelf]方法返回true，此方法会执行。否则不执行。
     *
     * @param request 请求
     */
    abstract fun handle(request: Request)
}