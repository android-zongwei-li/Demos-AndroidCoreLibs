package com.lizw.core_apis.java.collection;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ConcurrentHashMap 的使用示例
 */
public class ConcurrentHashMapTest {
    public static void main(String[] args) throws Exception {
        ConcurrentHashMapTest.testBasic();

    }

    static void testBasic() {
        ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<>();

        String key = "firstKey";
        String value = "firstValue";
        cache.putIfAbsent(key, value);

        // 添加键值对
        cache.put("one", 1);
        cache.put("two", 2);
        cache.put("three", 3);
        // 使用 putIfAbsent 不会再次插入
        cache.putIfAbsent("three", 30);
        // 不支持插入 null 值
//        cache.put("null", null);

        // 遍历ConcurrentHashMap
        // 这个遍历操作是线程安全的，即使在遍历过程中有其他线程修改ConcurrentHashMap，
        // 也不会抛出ConcurrentModificationException
        for (String k : cache.keySet()) {
            System.out.println("k = " + k + " , " + cache.get(k));
        }
    }

    static void testNull(){
        ConcurrentHashMap<String, Integer> map1 = new ConcurrentHashMap<>();
        // 报错：map1.get("x")为null，类型是Integer，此时拆箱赋值给int，会报错
        int x = map1.get("x");
    }
}


