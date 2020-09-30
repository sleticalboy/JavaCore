package com.binlee.thread.concurrent;

import com.binlee.util.Logger;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/9/25
 */
public final class DeadLock {

    private static final Logger sLogger = Logger.get(DeadLock.class);
    private final Object mLockA = new Object();
    private final Object mLockB = new Object();

    private DeadLock() {
    }

    public static void run() {
        // 死锁产生的必要条件：
        // 互斥：一个资源每次只能被一个线程使用（独木桥每次只能通过一人）
        // 不可剥夺：线程已经获得资源，在未使用完之前不能强行剥夺（ben 正在过桥，其他人不能强制让 ben 返回）
        // 请求与保持：一个线程因请求资源而阻塞时，对已获得的资源不释放（ben 和 tom 同时过桥且两人互不谦让）
        // 循环等待：两个及以上的线程形成一种首尾相接的循环等待资源局面（tom 不返回则 ben 不能过桥；反之则 tom 无法过桥）

        // 解决死锁问题：只要打破上面 4 个条件之一即可
        // 按照一定的顺序获取锁
        // 获取锁时，添加尝试超时机制
        // 思死锁检测
        // 避免嵌套锁

        // synchronized 是可重入锁
        new DeadLock().run0();
    }

    private void run0() {
        new Thread("Thread-1") {
            @Override
            public void run() {
                sLogger.v(getName() + " trying acquire lock A");
                synchronized (mLockA) {
                    sLogger.v(getName() + " holding acquire lock A");
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                        sLogger.e(getName() + " sleep(500) error.", e);
                    }
                    sLogger.v(getName() + " trying acquire lock B");
                    synchronized (mLockB) {
                        sLogger.v(getName() + " holding acquire lock B");
                        sLogger.v(getName() + " released acquire lock B");
                    }
                    sLogger.v(getName() + " released acquire lock A");
                }
            }
        }.start();

        // new Thread("Thread-2") {
        //     @Override
        //     public void run() {
        //         sLogger.log(getName() + " trying acquire lock B");
        //         synchronized (mLockB) {
        //             sLogger.log(getName() + " holding acquire lock B");
        //             try {
        //                 Thread.sleep(500L);
        //             } catch (InterruptedException e) {
        //                 sLogger.err(getName() + " sleep(500) error.", e);
        //             }
        //             sLogger.log(getName() + " trying acquire lock A");
        //             synchronized (mLockA) {
        //                 sLogger.log(getName() + " holding acquire lock A");
        //                 sLogger.log(getName() + " released acquire lock A");
        //             }
        //             sLogger.log(getName() + " released acquire lock B");
        //         }
        //     }
        // }.start();

        // 按顺序获取锁
        new Thread("Thread-3") {
            @Override
            public void run() {
                sLogger.v(getName() + " trying acquire lock A");
                synchronized (mLockA) {
                    sLogger.v(getName() + " holding acquire lock A");
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                        sLogger.e(getName() + " sleep(500) error.", e);
                    }
                    sLogger.v(getName() + " trying acquire lock B");
                    synchronized (mLockB) {
                        sLogger.v(getName() + " holding acquire lock B");
                        sLogger.v(getName() + " released acquire lock B");
                    }
                    sLogger.v(getName() + " released acquire lock A");
                }
            }
        }.start();
    }
}
