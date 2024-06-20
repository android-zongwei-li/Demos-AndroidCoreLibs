package com.lizw.designpattern.dynamic_proxy

import android.annotation.SuppressLint
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.text.SimpleDateFormat
import java.util.Date

/**
 * 不传入被代理对象的实现（接口代理）
 */
class TestProxy2 : InvocationHandler {
    // 在程序运行期间，拿到【代理类的对象】后，调用xxx方法，程序会调用到当前的 invoke() 方法，
    // 而【被代理对象】的方法的调用，将会在当前的 invoke() 中由我们调用。
    // 这样，就可以在调用【被代理对象】的目标方法的前、后，加上新增的逻辑，起到了代理的效果。
    @SuppressLint("SimpleDateFormat")
    override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any? {
        // 在这里可以添加前置逻辑
        // 打印起始时间
        println(SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date()))
        
        // 处理业务逻辑
        println("业务处理")
        val methodName = method.name
        println("methodName = $methodName")
        when (methodName) {
            "addNewData" -> {
                // 处理方法调用
            }
            
            "addNewData2" -> {
            }
        }
        
        // 在这里可以添加后置逻辑
        // 打印结束时间
        println(SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date()))
        println("end")
        return null
    }
}

fun main() {
    // 实例化 TestProxy2 类
    val invocationHandler = TestProxy2()
    
    // 通过 Proxy.newProxyInstance 方法返回一个代理类
    val proxy = Proxy.newProxyInstance(ITest::class.java.classLoader, arrayOf(ITest::class.java), invocationHandler) as ITest
    
    // 代理类调用目标方法（最终会执行 TestProxy2 中的invoke方法）
    proxy.addNewData()
    proxy.addNewData2("this is 2")
}