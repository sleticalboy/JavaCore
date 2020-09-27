package com.binlee.util;

import java.util.*;
import java.util.concurrent.locks.*;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/9/27
 */
public final class TaskQueue<T> {

    private final ReentrantLock mLock = new ReentrantLock();
    // 注意： condition 对象必须从 lock.newCondition() 返回, 这样才能或得绑定了 lock 的 condition 实例
    // Condition 可以代替 Object#wait() 和 Object#notify()/notifyAll()
    private final Condition mCondition = mLock.newCondition();
    private final Queue<T> mQueue;

    // 只允许一个线程写入（写入时其他线程不能写入和不能读取）
    // 没有写入时，允许多个线程同时读取
    // 以上特性决定了：此锁适用于读多写少的场景
    private final ReadWriteLock mRWLock = new ReentrantReadWriteLock();
    private final List<T> mData;

    // 悲观锁：认为读取的过程中拒绝写入，写入操作必须在读取操作之后
    // 乐观锁：认为读取的过程中大概率不会有写入操作（效率高于悲观锁）
    private final StampedLock mStampedLock = new StampedLock();
    private double mX, mY;

    public TaskQueue() {
        mQueue = new LinkedList<>();
        mData = new ArrayList<>();
    }

    // 从坐标原点移动一个点，x、y 坐标分别移动 deltaX、deltaY
    private void move(double deltaX, double deltaY) {
        // 获取写锁
        final long stamp = mStampedLock.writeLock();
        try {
            mX += deltaX;
            mY += deltaY;
        } finally {
            // 释放写锁
            mStampedLock.unlockWrite(stamp);
        }
    }

    // 计算点(mX, mY)到坐标原点 O(0, 0) 的距离
    private double getLengthFormO() {
        // 获取乐观锁（只是返回一个版本号）
        long stamp = mStampedLock.tryOptimisticRead();
        // 假设执行到此处时，读取的的 x 是 100
        double x = mX;
        // 有可能在执行到此处时，x 被其他线程修改为 200
        double y = mY;
        // 判断获取乐观锁后是否被其他线程写入
        if (!mStampedLock.validate(stamp)) {
            // 获取悲观锁
            stamp = mStampedLock.readLock();
            try {
                x = mX;
                y = mY;
            } finally {
                // 释放悲观锁
                mStampedLock.unlockRead(stamp);
            }
        }
        return Math.sqrt(x * x + y * y);
    }

    public void setTask(int index, T task) {
        // 同一时刻，只允许一个线程执行写入操作
        // 必须等写操作完成后，才可以执行读操作（悲观锁）
        mRWLock.writeLock().lock();
        try {
            mData.set(index, task);
        } finally {
            mRWLock.writeLock().unlock();
        }
    }

    public List<T> getData() {
        // 同一时刻，允许多个线程执行读取操作
        mRWLock.readLock().lock();
        try {
            return Collections.unmodifiableList(mData);
        } finally {
            mRWLock.readLock().unlock();
        }
    }

    public void addTask(T task) {
        // addTask_2(task);
        addTask_0(task);
    }

    public T getTask() {
        // return getTask_2();
        return getTask_0();
    }

    public void addTask_2(T task) {
        // 任何时刻，只允许一个 Thread 写入
        mLock.lock();
        try {
            mQueue.add(task);
            // 添加完任务，唤醒所有等待的线程
            mCondition.signalAll();
        } finally {
            mLock.unlock();
        }
    }

    public T getTask_2() {
        // 任何时刻，只允许一个 Thread 读取，有些保护“过头”了
        mLock.lock();
        try {
            while (mQueue.isEmpty()) {
                try {
                    // 当前任务队列为空，阻塞
                    mCondition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException("TaskQueue#getTask() error(" + e.getCause() + ")");
                }
            }
            return mQueue.remove();
        } finally {
            mLock.unlock();
        }
    }

    public synchronized void addTask_0(T task) {
        mQueue.add(task);
        // 添加完任务，唤醒所有等待的线程
        notifyAll();
    }

    public synchronized T getTask_0() {
        while (mQueue.isEmpty()) {
            try {
                // 当前任务队列为空，阻塞
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException("TaskQueue#getTask() error(" + e.getCause() + ")");
            }
        }
        return mQueue.remove();
    }
}
