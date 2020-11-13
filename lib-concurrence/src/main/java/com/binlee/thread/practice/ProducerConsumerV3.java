package com.binlee.thread.practice;

import com.binlee.util.Logger;
import jdk.nashorn.internal.ir.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/13
 */
public final class ProducerConsumerV3 {

    private static final Logger LOGGER = Logger.get(ProducerConsumerV3.class);

    private static final int CAPACITY = 15;
    private static final long SLEEP_TIME = 500L;
    private final BlockingQueue<String> mQueue = new ArrayBlockingQueue<>(CAPACITY);
    private static final AtomicInteger SEQ = new AtomicInteger();

    private ProducerConsumerV3() {
        //no instance
    }

    // public static void main(String[] args) {
    //     exec();
    // }

    public static void exec() {
        final ProducerConsumerV3 pc = new ProducerConsumerV3();
        for (int i = 0; i < 3; i++) {
            new Producer(i, pc.mQueue).start();
        }
        for (int i = 0; i < 3; i++) {
            new Consumer(i, pc.mQueue).start();
        }
    }

    static class Producer extends Thread {

        private final BlockingQueue<String> mQueue;
        private final int mIndex;

        public Producer(int index, BlockingQueue<String> queue) {
            mQueue = queue;
            mIndex = index;
        }

        @Override
        public void run() {
            LOGGER.w("Producer " + mIndex + " run...");
            try {
                while (!Thread.currentThread().isInterrupted() && SEQ.get() <= CAPACITY * 2) {
                    String item = "product:" + SEQ.getAndIncrement();
                    mQueue.put(item);
                    LOGGER.i("produce-> " + item);
                    Thread.sleep(SLEEP_TIME);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer extends Thread {

        private final BlockingQueue<String> mQueue;
        private final int mIndex;

        public Consumer(int index, BlockingQueue<String> queue) {
            mQueue = queue;
            mIndex = index;
        }

        @Override
        public void run() {
            LOGGER.w("Consumer " + mIndex + " run...");
            try {
                while (!Thread.currentThread().isInterrupted() && SEQ.get() <= CAPACITY * 2) {
                    String item = mQueue.poll();
                    LOGGER.i("consume-> " + item);
                    Thread.sleep(SLEEP_TIME);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
