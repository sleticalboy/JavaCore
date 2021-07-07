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

    private static final int CAPACITY = 5;
    private static final long SLEEP_TIME = 500L;

    private final List<String> mQueue = new ArrayList<>();
    private final ReentrantLock mLock = new ReentrantLock();
    private final Condition mNotFull = mLock.newCondition();
    private final Condition mNotEmpty = mLock.newCondition();

    private ProducerConsumerV2() {
        //no instance
    }

    public static void main(String[] args) {
        exec();
    }

    public static void exec() {
        final ProducerConsumerV2 pc = new ProducerConsumerV2();
        for (int i = 0; i < 3; i++) {
            new Producer(i, pc).start();
            new Consumer(i, pc).start();
        }
    }

    static class Producer extends Thread {

        private final ProducerConsumerV2 mPc;
        private final int mIndex;
        private int seq;

        public Producer(int index, ProducerConsumerV2 pc) {
            mPc = pc;
            mIndex = index;
        }

        @Override
        public void run() {
            LOGGER.w("Producer " + mIndex + " run...");
            try {
                while (isAlive() && !isInterrupted()) {
                    mPc.mLock.lock();
                    try {
                        while (mPc.mQueue.size() == CAPACITY) {
                            // 生产线程等待
                            LOGGER.w("Producer " + mIndex + " wait for consumer...");
                            mPc.mNotFull.await();
                        }
                        String item = "product:" + seq++;
                        LOGGER.i("produce-> " + item);
                        mPc.mQueue.add(item);
                        // 唤醒消费线程
                        mPc.mNotEmpty.signalAll();
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
                while (isAlive() && !isInterrupted()) {
                    mPc.mLock.lock();
                    try {
                        while (mPc.mQueue.size() == 0) {
                            // 消费线程等待
                            LOGGER.w("Consumer " + mIndex + " wait for producer...");
                            mPc.mNotEmpty.await();
                        }
                        String item = mPc.mQueue.remove(0);
                        LOGGER.i("consume-> " + item);
                        // 唤醒生产线程
                        mPc.mNotFull.signalAll();
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
