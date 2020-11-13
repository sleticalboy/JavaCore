package com.binlee.thread.practice;

import com.binlee.util.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/13
 */
public final class ProducerConsumerV1 {

    private static final Logger LOGGER = Logger.get(ProducerConsumerV1.class);

    private static final int CAPACITY = 15;
    private static final long SLEEP_TIME = 500L;
    private final List<String> mQueue = new ArrayList<>();
    private static final AtomicInteger SEQ = new AtomicInteger();

    private ProducerConsumerV1() {
        //no instance
    }

    // public static void main(String[] args) {
    //     exec();
    // }

    public static void exec() {
        final ProducerConsumerV1 pc = new ProducerConsumerV1();
        for (int i = 0; i < 3; i++) {
            new Producer(i, pc.mQueue).start();
        }
        for (int i = 0; i < 3; i++) {
            new Consumer(i, pc.mQueue).start();
        }
    }

    static class Producer extends Thread {

        private final List<String> mQueue;
        private final int mIndex;

        public Producer(int index, List<String> queue) {
            mQueue = queue;
            mIndex = index;
        }

        @Override
        public void run() {
            LOGGER.w("Producer " + mIndex + " run...");
            try {
                while (!Thread.currentThread().isInterrupted() && SEQ.get() <= CAPACITY * 2) {
                    synchronized (mQueue) {
                        while (mQueue.size() >= CAPACITY) {
                            // 唤醒消费线程
                            mQueue.notifyAll();
                            // 生产线程等待
                            mQueue.wait();
                        }
                        String item = "product:" + SEQ.getAndIncrement();
                        mQueue.add(item);
                        // 唤醒消费线程
                        mQueue.notifyAll();
                        LOGGER.i("produce-> " + item);
                    }
                    Thread.sleep(SLEEP_TIME);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer extends Thread {

        private final List<String> mQueue;
        private final int mIndex;

        public Consumer(int index, List<String> queue) {
            mQueue = queue;
            mIndex = index;
        }

        @Override
        public void run() {
            LOGGER.w("Consumer " + mIndex + " run...");
            try {
                while (!Thread.currentThread().isInterrupted() && SEQ.get() <= CAPACITY * 2) {
                    String item;
                    synchronized (mQueue) {
                        while (mQueue.size() == 0) {
                            // 唤醒生产线程
                            mQueue.notifyAll();
                            // 消费线程等待
                            mQueue.wait();
                        }
                        item = mQueue.remove(0);
                        // 唤醒生产线程
                        mQueue.notifyAll();
                    }
                    LOGGER.i("consume-> " + item);
                    Thread.sleep(SLEEP_TIME);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
