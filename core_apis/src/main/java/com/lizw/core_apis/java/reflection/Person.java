package com.lizw.core_apis.java.reflection;

/**
 *
 */
public class Person {
    private static final String TAG = "TAG_PERSON";

    private String name;

    public Person() {
        System.out.println("无参构造函数，创建了一个Person");
    }

    public Person(String str) {
        System.out.println("调用了有参构造函数，参数 = " + str);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        System.out.println("invoked setName");
    }

    public void print() {
        System.out.println("调用了print无参方法");
        System.out.println(this);
    }

    public static void testStaticMethod() {
        System.out.println("static method");
    }
}
