package com.lizw.core_apis.java.synchronized_test;

/**
 * Created by Li Zongwei on 2021/3/12.
 **/
public class SynchronizedMethods {
    private Object lock = new Object();

    public static void main(String[] args) {
        final SynchronizedMethods s1 = new SynchronizedMethods();
        final SynchronizedMethods s2 = new SynchronizedMethods();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                s1.printLog();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                s2.printLog();
            }
        });

        t1.start();
        t2.start();
    }

    public void printLog() {
        synchronized (lock) {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()+"is print " + i);
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
