package com.binlee.thread.concurrent;

import com.binlee.util.Logger;
import com.binlee.util.NamedRunnable;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/9/25
 */
public final class SyncThread {

    private static final Logger sLogger = Logger.get(SyncThread.class);
    private int mValue;
    private final Object mLock = new Object();
    private final ReentrantLock mReentrantLock = new ReentrantLock();
    private final Mutex mMutex = Mutex.create();

    private SyncThread() {
    }

    public static void run() {
        final SyncThread sync = new SyncThread();
        sLogger.v("main() start sValue: " + sync.mValue);
        final Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(sync.getRunnable(i), "Thread#" + i);
            threads[i].start();
        }
        // 等待所有线程完成, 保证主线程最后一行代码被执行
        for (Thread t : threads) {
            try {
                // 阻塞调用 join() 的线程（此处即 main com.binlee.thread）直到线程 t 执行完毕
                // 其底层是用 Object#wait(long millis) 和 Object#notifyAll() 方法实现的
                // notifyAll() 是在 /hotspot/src/share/vm/runtime/com.binlee.thread.cpp 中实现:
                // 当线程退出时调用 ensure_join() -> lock.notify_all(com.binlee.thread), 由 JVM 自动实现唤醒
                t.join();
                // Object#wait() 方法, 调用 wait() 方法的线程进行等待
                // 调用 wait 和 notify/notifyAll 方法需要获取到锁
            } catch (InterruptedException e) {
                sLogger.e(t.getName() + " join() error.", e);
            }
        }
        // 所有的子线程执行完毕之后，才会执行主线程最后一行代码
        sLogger.v("main() end sValue: " + sync.mValue);
    }

    private Runnable getRunnable(final int index) {
        return new NamedRunnable("Counter", index) {
            @Override
            public void run() {
                // 线程不安全，输出的结果是无序的且最终 mValue 不一定是 10000
                // calc(toString());
                // 线程安全，输出的结果是有序的且最终 mValue 一定是 10000
                // calcSync_1(toString());
                // calcSync_2(toString());
                // calcSync_3(toString());
                // calcSync_4(toString());
                calcSync_5(toString());
            }
        };
    }

    private void calcSync_5(String desc) {
        // tryLock() 必须在返回 true 时才可执行需同步的代码，否则会报 IllegalMonitorStateException
        if (!mMutex.tryLock()) {
            // 重试
            mMutex.lock();
        }
        try {
            for (; mValue < 10000; mValue++) {
                sLogger.v(desc + " run() mValue: " + mValue);
            }
        } finally {
            mMutex.unlock();
        }
    }

    private void calcSync_4(String desc) {
        // tryLock() 必须在返回 true 时才可执行需同步的代码，否则会报 IllegalMonitorStateException
        if (!mReentrantLock.tryLock()) {
            // 重试
            mReentrantLock.lock();
        }
        try {
            for (; mValue < 10000; mValue++) {
                sLogger.v(desc + " run() mValue: " + mValue);
            }
        } finally {
            mReentrantLock.unlock();
        }
    }

    private void calcSync_3(String desc) {
        synchronized (mLock) {
            for (; mValue < 10000; mValue++) {
                sLogger.v(desc + " run() mValue: " + mValue);
            }
        }
    }

    private synchronized void calcSync_2(String desc) {
        // 非静态同步方法的锁对象是当前方法调用者的实例对象，即 this
        // 静态同步方法的锁对象是当前类在 JVM 中 Class<?> 的实例对象，即 this
        for (; mValue < 10000; mValue++) {
            sLogger.v(desc + " run() mValue: " + mValue);
        }
    }

    private void calcSync_1(String desc) {
        synchronized (this) {
            for (; mValue < 10000; mValue++) {
                sLogger.v(desc + " run() mValue: " + mValue);
            }
        }
    }

    private void calc(String desc) {
        for (; mValue < 10000; mValue++) {
            sLogger.v(desc + " run() mValue: " + mValue);
        }
    }
}
