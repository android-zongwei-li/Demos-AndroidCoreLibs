package com.lizw.core_apis.java.reentrantlocktest;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Li Zongwei on 2021/3/12.
 **/
public class FairLockTest implements Runnable {
    // 模拟共享资源
    private int sharedNumber = 0;

    private static ReentrantLock mLock = new ReentrantLock();

    @Override
    public void run() {
        while (sharedNumber < 20) {
            mLock.lock();
            try {
                sharedNumber++;
                System.out.println(Thread.currentThread().getName() + "获得锁，sharedNumber = " + sharedNumber);
            } catch (Exception e) {
            } finally {
                mLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        FairLockTest ft = new FairLockTest();
        Thread t1 = new Thread(ft);
        Thread t2 = new Thread(ft);
        Thread t3 = new Thread(ft);

        t1.start();
        t2.start();
        t3.start();
    }
}
