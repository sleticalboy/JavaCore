package com.binlee.thread.practice;

import com.binlee.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/13
 */
public final class ProducerConsumerV1 {

    private static final Logger LOGGER = Logger.get(ProducerConsumerV1.class);

    private ProducerConsumerV1() {
        //no instance
    }

    public static void main(String[] args) {
        exec();
    }

    public static void exec() {
        final List<String> queue = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            new Producer(i, queue, 5).start();
            new Consumer(i, queue).start();
        }
    }

    static class Producer extends Thread {
        private final int index;
        private final List<String> queue;
        private final int max;
        private int seq;

        public Producer(int index, List<String> queue, int max) {
            this.index = index;
            this.queue = queue;
            this.max = max;
        }

        @Override
        public void run() {
            LOGGER.w("p-" + index + " run...");
            try {
                while (isAlive() && !isInterrupted()) {
                    synchronized (queue) {
                        while (queue.size() >= max) {
                            LOGGER.e("p-" + index + " wait for consumer...");
                            queue.wait();
                        }
                        String item = "product:" + seq++;
                        LOGGER.i("produce-> " + item);
                        queue.add(item);
                        queue.notifyAll();
                        Thread.sleep(500L);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer extends Thread {

        private final List<String> queue;
        private final int index;

        public Consumer(int index, List<String> queue) {
            this.index = index;
            this.queue = queue;
        }

        @Override
        public void run() {
            LOGGER.w("c-" + index + " run...");
            try {
                while (isAlive() && !isInterrupted()) {
                    synchronized (queue) {
                        while (queue.size() == 0) {
                            LOGGER.e("c-" + index + " wait for producer...");
                            queue.wait();
                        }
                        String item = queue.remove(0);
                        LOGGER.i("consume-> " + item);
                        queue.notifyAll();
                        Thread.sleep(500L);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
