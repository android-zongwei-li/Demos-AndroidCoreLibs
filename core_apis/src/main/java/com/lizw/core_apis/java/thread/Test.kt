package com.lizw.core_apis.java.thread

/**
 *
 * author: zongwei.li created on: 2022/7/7
 */
class Test {
    @Volatile
    var flag = true
    
    /**
     * 如果flag不加Volatile，那么在其他线程改flag的值，spin()方法并不会结束。
     * 线程的可见性问题。
     */
    fun spin() {
        while (flag) {
//            flag = false
        }
    }
    
    fun referenceTest(person: Person) {
        person.name
        
        val p2 = Person("p2")
        // 在Java中person参数是可以赋值的，这涉及到一个问题：给person赋值后，外部并不会收到影响
        // person = p2?
    }
    
    class Person(val name: String)
}