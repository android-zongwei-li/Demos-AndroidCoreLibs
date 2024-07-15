package com.lizw.core_apis.java.gc;

import java.lang.ref.SoftReference;

/**
 * 软引用常规使用
 */
public class SoftReferenceNormal {
    static class SoftObject {
        byte[] data = new byte[120 * 1024 * 1024];
    }

    public static void main(String[] args) {
        // cache data
        SoftReference<SoftObject> cacheRef = new SoftReference<>(new SoftObject());
        System.out.println("before the first GC, softReference: " + cacheRef.get());
        // gc
        System.gc();
        System.out.println("after the first GC, softReference: " + cacheRef.get());
        // 创建一个120M的强引用
        SoftObject newSo = new SoftObject();
        System.out.println("then allocated 120M object, softReference: " + cacheRef.get());
        printMemory();
    }

    /**
     * 打印出当前JVM剩余空间和总的空间大小
     */
    public static void printMemory() {
        System.out.print("free is " + Runtime.getRuntime().freeMemory() / 1024 / 1024 + " M");
        System.out.println("total is " + Runtime.getRuntime().totalMemory() / 1024 / 1024 + " M");
    }
}