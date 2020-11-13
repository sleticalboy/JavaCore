package com.binlee.thread.practice;

import com.binlee.util.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/13
 * <p>
 * 生产线程会一直生产产品，直到临界点才唤醒消费线程
 */
public final class ProducerConsumerV2 {

    private static final Logger LOGGER = Logger.get(ProducerConsumerV2.class);

    private static final int CAPACITY = 15;
    private static final long SLEEP_TIME = 500L;
    private static final AtomicInteger SEQ = new AtomicInteger();

    private final List<String> mQueue = new ArrayList<>();
    private final ReentrantLock mLock = new ReentrantLock();
    private final Condition mNotFull = mLock.newCondition();
    private final Condition mNotEmpty = mLock.newCondition();
    // queue's real size
    private int mSize;

    private ProducerConsumerV2() {
        //no instance
    }

    // public static void main(String[] args) {
    //     exec();
    // }

    public static void exec() {
        final ProducerConsumerV2 pc = new ProducerConsumerV2();
        for (int i = 0; i < 3; i++) {
            new Producer(i, pc).start();
        }
        for (int i = 0; i < 3; i++) {
            new Consumer(i, pc).start();
        }
    }

    static class Producer extends Thread {

        private final ProducerConsumerV2 mPc;
        private final int mIndex;

        public Producer(int index, ProducerConsumerV2 pc) {
            mPc = pc;
            mIndex = index;
        }

        @Override
        public void run() {
            LOGGER.w("Producer " + mIndex + " run...");
            try {
                while (!Thread.currentThread().isInterrupted() && SEQ.get() <= CAPACITY * 2) {
                    mPc.mLock.lock();
                    try {
                        while (mPc.mSize == CAPACITY) {
                            // 生产线程等待
                            mPc.mNotFull.await();
                        }
                        String item = "product:" + SEQ.getAndIncrement();
                        mPc.mQueue.add(item);
                        mPc.mSize++;
                        // 唤醒消费线程
                        mPc.mNotEmpty.signalAll();
                        LOGGER.i("produce-> " + item);
                        Thread.sleep(SLEEP_TIME);
                    } finally {
                        mPc.mLock.unlock();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer extends Thread {

        private final ProducerConsumerV2 mPc;
        private final int mIndex;

        public Consumer(int index, ProducerConsumerV2 pc) {
            mPc = pc;
            mIndex = index;
        }

        @Override
        public void run() {
            LOGGER.w("Consumer " + mIndex + " run...");
            try {
                while (!Thread.currentThread().isInterrupted() && SEQ.get() <= CAPACITY * 2) {
                    mPc.mLock.lock();
                    try {
                        while (mPc.mSize == 0) {
                            // 消费线程等待
                            mPc.mNotEmpty.await();
                        }
                        String item = mPc.mQueue.remove(0);
                        mPc.mSize--;
                        // 唤醒生产线程
                        mPc.mNotFull.signalAll();
                        LOGGER.i("consume-> " + item);
                        Thread.sleep(SLEEP_TIME);
                    } finally {
                        mPc.mLock.unlock();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
