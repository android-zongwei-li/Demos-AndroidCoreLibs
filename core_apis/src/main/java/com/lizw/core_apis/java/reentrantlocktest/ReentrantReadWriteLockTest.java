package com.lizw.core_apis.java.reentrantlocktest;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Li Zongwei on 2021/3/12.
 **/
public class ReentrantReadWriteLockTest {
    private static ReentrantReadWriteLock mLock = new ReentrantReadWriteLock();
    // 缓存数据
    private static int sharedNum = 0;

    public static void main(String[] args) {
        Thread t1 = new Thread(new Reader(), "Reader  1");
        Thread t2 = new Thread(new Reader(), "Reader  2");
        Thread t3 = new Thread(new Writer(), "Writer  3");

        t1.start();
        t2.start();
        t3.start();
    }

    static class Reader implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    // 模拟每隔 0.5s 读一次数据
                    Thread.sleep(500L);
                    mLock.readLock().lock();
                    System.out.println(Thread.currentThread().getName() + "--> Num is " + sharedNum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    mLock.readLock().unlock();
                }
            }
        }
    }

    static class Writer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    // 模拟 每隔 1s 写一次数据
                    Thread.sleep(1000L);
                    mLock.writeLock().lock();
                    sharedNum = i;
                    System.out.println(Thread.currentThread().getName() + "--> 正在写入 " + sharedNum);
                } catch (Exception e) {
                } finally {
                    mLock.writeLock().unlock();
                }
            }
        }
    }
}
