package com.binlee.thread.concurrent;

import com.binlee.util.Logger;

import java.util.concurrent.TimeUnit;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/23
 */
public final class Volatiles {

    private static final Logger LOGGER = Logger.get(Volatiles.class);
    private static /*volatile*/ boolean sRun = true;

    // volatile: 可见性
    // synchronized: 原子性 & 可见性

    private static void run() {
        LOGGER.i("run() start");
        while (sRun) {
            LOGGER.i("run().....");
        }
        LOGGER.i("run() end");
    }

    public static void exec() {
        new Thread(Volatiles::run, "sub thread").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sRun = false;
    }
}
