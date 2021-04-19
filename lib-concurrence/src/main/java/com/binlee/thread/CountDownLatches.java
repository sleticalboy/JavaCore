package com.binlee.thread;

import com.binlee.util.Logger;
import com.binlee.util.NamedRunnable;
import com.binlee.util.Threads;

import java.util.concurrent.CountDownLatch;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2021/4/19
 */
public class CountDownLatches {

    private static final Logger sLogger = Logger.get(CountDownLatches.class);

    public static void exec() {
        try {
            new CountDownLatches().run();
        } catch (InterruptedException e) {
            sLogger.e("exec() error ", e);
        }
    }

    private void run() throws InterruptedException {
        sLogger.d("main thread run");
        int count = 2;
        CountDownLatch latch = new CountDownLatch(count);
        new Thread(new NamedRunnable("sub thread", 0) {
            @Override
            public void run() {
                Threads.sleep(1000L);
                sLogger.d(this + " done " + System.currentTimeMillis());

                latch.countDown();
            }
        }).start();
        new Thread(new NamedRunnable("sub thread", 1) {
            @Override
            public void run() {
                Threads.sleep(1000L);
                sLogger.d(this + " done " + System.currentTimeMillis());
                latch.countDown();
            }
        }).start();
        sLogger.d("main thread waits for sub threads exec over");
        latch.await();
        sLogger.d("main thread over");
    }

    private void doWork(CountDownLatch latch, int id) {
        new Thread(new NamedRunnable("sub thread", id) {
            @Override
            public void run() {
                Threads.sleep(500L * id);
                sLogger.d(this + " done " + System.currentTimeMillis());
                latch.countDown();
            }
        }).start();
    }
}
