package com.binlee.thread.concurrent;

import com.binlee.Logger;
import com.binlee.thread.NamedRunnable;

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

    private SyncThread() {
    }

    public static void main() {
        final SyncThread sync = new SyncThread();
        sLogger.log("main() start sValue: " + sync.mValue);
        final Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(sync.getRunnable(i), "Thread#" + i);
            threads[i].start();
        }
        // 等待所有线程完成, 保证主线程最后一行代码被执行
        for (Thread t : threads) {
            try {
                // 阻塞调用 join() 的线程（此处即 main thread）直到线程 t 执行完毕
                // 其底层是用 Object#wait(long millis) 和 Object#notifyAll() 方法实现的
                // notifyAll() 是在 /hotspot/src/share/vm/runtime/thread.cpp 中实现:
                // 当线程退出时调用 ensure_join() -> lock.notify_all(thread), 由 JVM 自动实现唤醒
                t.join();
                // Object#wait() 方法, wait 的是调用 wait() 方法的线程
            } catch (InterruptedException e) {
                sLogger.err(t.getName() + " join() error.", e);
            }
        }
        sLogger.log("main() end sValue: " + sync.mValue);
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
                calcSync_4(toString());
            }
        };
    }

    private void calcSync_4(String desc) {
        mReentrantLock.tryLock();
        try {
            for (; mValue < 10000; mValue++) {
                sLogger.log(desc + " run() mValue: " + mValue);
            }
        } finally {
            mReentrantLock.unlock();
        }
    }

    private void calcSync_3(String desc) {
        synchronized (mLock) {
            for (; mValue < 10000; mValue++) {
                sLogger.log(desc + " run() mValue: " + mValue);
            }
        }
    }

    private synchronized void calcSync_2(String desc) {
        // 非静态同步方法的锁对象是当前方法调用者的实例对象，即 this
        // 静态同步方法的锁对象是当前类在 JVM 中 Class<?> 的实例对象，即 this
        for (; mValue < 10000; mValue++) {
            sLogger.log(desc + " run() mValue: " + mValue);
        }
    }

    private void calcSync_1(String desc) {
        synchronized (this) {
            for (; mValue < 10000; mValue++) {
                sLogger.log(desc + " run() mValue: " + mValue);
            }
        }
    }

    private void calc(String desc) {
        for (; mValue < 10000; mValue++) {
            sLogger.log(desc + " run() mValue: " + mValue);
        }
    }
}
