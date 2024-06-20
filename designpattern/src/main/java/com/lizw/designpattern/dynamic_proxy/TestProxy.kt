package com.lizw.designpattern.dynamic_proxy

import android.annotation.SuppressLint
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.text.SimpleDateFormat
import java.util.Date

/**
 * 有被代理对象的动态代理（类代理）
 *
 * @param target 被代理的对象
 */
class TestProxy(private val target: Any) : InvocationHandler {
    // 在程序运行期间，拿到【代理类的对象】后，调用xxx方法，程序会调用到当前的 invoke() 方法，
    // 而【被代理对象】的方法的调用，将会在当前的 invoke() 中由我们调用。
    // 这样，就可以在调用【被代理对象】的目标方法的前、后，加上新增的逻辑，起到了代理的效果。
    @SuppressLint("SimpleDateFormat")
    override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any? {
        // println("invoke : proxy = $proxy , method = ${method.name}")
        
        // 在这里可以添加前置逻辑
        // 5.打印起始时间
        println(SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date()))
        
        // 6.调用目标方法
        // 由于一个接口中可能含有多个方法，所以需要做方法匹配：按照方法名称
        val methodName = method.name
        println("methodName = $methodName")
        when (methodName) {
            "addNewData" -> method.invoke(target)
            "addNewData2" -> {
                // 由于 Kotlin 的特性，调用了方法 fun addNewData2(text: String) 时，参数一定不为null
                method.invoke(target, args!![0])
            }
        }
        
        // 在这里可以添加后置逻辑
        // 7.打印结束时间
        println(SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date()))
        println("end")
        return null
    }
}

fun main() {
    // 1.实例化被代理类
    val iTest = TestImpl()
    
    // 2.实例化 TestProxy 类，并将代理类对象传入
    val invocationHandler = TestProxy(iTest)
    
    // 3.通过 Proxy.newProxyInstance 方法返回一个代理类
    val proxy = Proxy.newProxyInstance(iTest::class.java.classLoader, iTest::class.java.interfaces, invocationHandler) as ITest
    
    // 4.代理类调用目标方法（最终会执行 TestProxy 中的invoke方法）
    proxy.addNewData()
    proxy.addNewData2("this is 2")
}