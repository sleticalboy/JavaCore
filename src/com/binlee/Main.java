package com.binlee;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    private int counter = 0;
    private final Object mLock;
    private final ReentrantLock mReentrantLock;
    private final Semaphore mSemaphore;

    public Main() {
        mLock = new Object();
        mReentrantLock = new ReentrantLock();
        mSemaphore = new Semaphore(10);
    }

    public static void main(String[] args) {
        // write your code here
        System.out.println("Main#main() run...");
        new Main().run();
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            new Thread("Thread#" + i) {
                @Override
                public void run() {
                    // loop(getName());
                    for (int j = 0; j < 100; j++) {
                        add_1(getName());
                        // add_2(getName());
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    }

    private void loop(String name) {
        try {
            mSemaphore.acquire();
            for (int j = 0; j < 100; j++) {
                if (counter < 999) {
                    counter++;
                    System.out.println(name + " -> counter: " + counter);
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mSemaphore.release();
        }
    }

    private void add_2(String who) {
        mReentrantLock.lock();
        try {
            if (counter < 999) {
                counter++;
                System.out.println(who + " -> counter: " + counter);
            }
        } finally {
            mReentrantLock.unlock();
        }
    }

    private /*synchronized*/ void add_1(String who) {
        // synchronized (mLock) {
        synchronized (this) {
            if (counter < 999) {
                counter++;
                System.out.println(who + " -> counter: " + counter);
            }
        }
    }
}
