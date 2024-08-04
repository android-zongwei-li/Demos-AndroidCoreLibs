package com.lizw.core_apis.java.reentrantlocktest;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    ReentrantLock mLock = new ReentrantLock();

    public static void main(String[] args) {
        final ReentrantLockTest r1 = new ReentrantLockTest();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                r1.printLog();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                r1.printLog();
            }
        });

        t1.start();
        t2.start();
    }

    public void printLog() {
        try {
            mLock.lock();
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " is printing " + i);
            }
        } catch (Exception e) {
        } finally {
            mLock.unlock();
        }
    }
}