package com.binlee.design.flyweight;

import com.binlee.util.Logger;
import com.binlee.util.NamedRunnable;

import java.util.UUID;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/16
 */
public final class Flyweights {

    private static final Logger L = Logger.get(Flyweights.class);

    public static void exec() {
        final Thread[] threads = new Thread[3];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Task("flyweight", i));
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }

    private static class Task extends NamedRunnable {

        protected Task(String prefix, int id) {
            super(prefix, id);
        }

        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                final SharedBike bike = SharedBike.obtain();
                bike.qrCode = UUID.randomUUID().toString();
                L.i(toString() + " " + bike);
                bike.recycle();
                try {
                    Thread.sleep(50L);
                } catch (InterruptedException e) {
                    L.e(toString(), e);
                }
            }
        }
    }
}
