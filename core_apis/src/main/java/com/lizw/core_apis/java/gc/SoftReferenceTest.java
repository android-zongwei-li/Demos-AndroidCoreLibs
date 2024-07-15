package com.lizw.core_apis.java.gc;

import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.Set;

/**
 * NotesUrl：
 * 技术点：
 */
public class SoftReferenceTest {
    public static class SoftObject {
        // 1KB
        byte[] data = new byte[1024];
    }

    // 100M
    public static int CACHE_INITIAL_CAPACITY = 100 * 1024;
    // 静态集合保存软引用，会导致这些软引用对象本身无法被垃圾回收器回收
    public static Set<SoftReference<SoftObject>> cache = new HashSet<>(CACHE_INITIAL_CAPACITY);

    public static void main(String[] args) {
        for (int i = 0; i < CACHE_INITIAL_CAPACITY; i++) {
            SoftObject object = new SoftObject();
            cache.add(new SoftReference<>(object));
            if (i % 10000 == 0) {
                System.out.println("size of cache: " + cache.size());
            }
        }
        System.out.println("end");
    }
}
