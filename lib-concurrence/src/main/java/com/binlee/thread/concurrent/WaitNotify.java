package com.binlee.thread.concurrent;

import com.binlee.util.Logger;
import com.binlee.util.HandlerThread;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/9/26
 */
public final class WaitNotify {

    private static final Logger sLogger = Logger.get(WaitNotify.class);

    public static void run() {
        new WaitNotify().run0();
    }

    private void run0() {
        final HandlerThread thread = new HandlerThread("HandlerThread");
        thread.start();
        sLogger.v("run() looper: " + thread.getLooper());
    }
}
