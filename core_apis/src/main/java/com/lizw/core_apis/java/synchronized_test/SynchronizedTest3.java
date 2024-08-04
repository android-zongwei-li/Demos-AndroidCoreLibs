package com.lizw.core_apis.java.synchronized_test;

/**
 * Created by Li Zongwei on 2020/9/28.
 **/
public class SynchronizedTest3 implements Runnable {
    public static int count = 0;

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            count++;
        }
    }
}
