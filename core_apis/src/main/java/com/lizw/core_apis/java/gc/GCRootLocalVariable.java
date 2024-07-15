package com.lizw.core_apis.java.gc;

/**
 * 验证虚拟机栈（栈帧中的局部变量）中引用的对象作为 GC Root
 * 可以。
 *
 * 输出：
 * 开始时
 * free is 175 M
 * total is 200 M
 * 第一次GC完成
 * free is 112 M
 * total is 200 M
 * 第二次GC完成
 * free is 196 M
 * total is 200 M
 */
public class GCRootLocalVariable {
    private int _10MB = 10 * 1024 * 1024;
    private byte[] memory = new byte[8 * _10MB];

    public static void main(String[] args) {
        System.out.println("开始时");
        printMemory();
        method();
        System.gc();
        System.out.println("第二次GC完成");
        printMemory();
    }

    private static void method() {
        GCRootLocalVariable g = new GCRootLocalVariable();
        System.gc();
        System.out.println("第一次GC完成");
        printMemory();
    }

    /**
     * 打印出当前 JVM 剩余空间和总的空间大小
     */
    private static void printMemory() {
        System.out.println("free is " + Runtime.getRuntime().freeMemory() / 1024 / 1024 + " M");
        System.out.println("total is " + Runtime.getRuntime().totalMemory() / 1024 / 1024 + " M");
    }


}