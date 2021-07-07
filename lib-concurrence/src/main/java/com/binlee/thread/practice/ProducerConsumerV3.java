package com.binlee.thread.practice;

import com.binlee.util.Logger;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/13
 */
public final class ProducerConsumerV3 {

    private static final Logger LOGGER = Logger.get(ProducerConsumerV3.class);

    private static final int CAPACITY = 15;
    private static final long SLEEP_TIME = 500L;
    private final ArrayBlockingQueue<String> mQueue = new ArrayBlockingQueue<>(CAPACITY);

    private ProducerConsumerV3() {
        //no instance
    }

    public static void main(String[] args) {
        exec();
    }

    public static void exec() {
        final ProducerConsumerV3 pc = new ProducerConsumerV3();
        for (int i = 0; i < 1; i++) {
            new Producer(i, pc.mQueue).start();
            new Consumer(i, pc.mQueue).start();
        }
    }

    static class Producer extends Thread {

        private final ArrayBlockingQueue<String> mQueue;
        private final int mIndex;
        private int mSeq = 0;

        public Producer(int index, ArrayBlockingQueue<String> queue) {
            mQueue = queue;
            mIndex = index;
        }

        @Override
        public void run() {
            LOGGER.w("Producer " + mIndex + " run...");
            try {
                while (!isInterrupted()) {
                    String item = "product:" + mSeq++;
                    LOGGER.i("produce-> " + item);
                    mQueue.put(item);
                    Thread.sleep(SLEEP_TIME);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer extends Thread {

        private final ArrayBlockingQueue<String> mQueue;
        private final int mIndex;

        public Consumer(int index, ArrayBlockingQueue<String> queue) {
            mQueue = queue;
            mIndex = index;
        }

        @Override
        public void run() {
            LOGGER.w("Consumer " + mIndex + " run...");
            while (!isInterrupted()) {
                try {
                    String item = mQueue.take();
                    LOGGER.i("consume-> " + item);
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
