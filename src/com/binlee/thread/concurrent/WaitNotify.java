package com.binlee.thread.concurrent;

import com.binlee.Logger;
import com.binlee.util.HandlerThread;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/9/26
 */
public final class WaitNotify {

    private static final Logger sLogger = Logger.get(WaitNotify.class);

    public static void main() {
        new WaitNotify().run();
    }

    private void run() {
        final HandlerThread thread = new HandlerThread("HandlerThread");
        thread.start();
        sLogger.log("run() looper: " + thread.getLooper());
    }
}
