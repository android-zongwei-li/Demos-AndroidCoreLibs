package com.lizw.core_apis.java.collection;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用 ConcurrentHashMap 实现 Key 计数
 */
public class Counter {
    @RequiresApi(api = Build.VERSION_CODES.N)
    private final ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void main(String[] args) throws Exception {
        Counter counter = new Counter();
        counter.increment("one");
        counter.increment("one");
        System.out.println("One count = " + counter.getCount("one"));
    }

    /**
     * 需要增加一个键的计数时，可以使用compute方法，这个方法会在键存在时增加计数，否则初始化计数为1。
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void increment(String key) {
        map.compute(key, (k, v) -> (v == null) ? 1 : v + 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int getCount(String key) throws Exception {
        Integer value = map.getOrDefault(key, 0);
        // 注意：由于 Value 值可能为null，所以Integer拆箱赋值给int可能产生 NullPointerException
        // 所以进行一下判空
        if (value == null) {
            throw new Exception("value is null");
        }
        return value;
    }
}