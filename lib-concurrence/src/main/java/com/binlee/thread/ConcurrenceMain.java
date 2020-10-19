package com.binlee.thread;

import com.binlee.thread.concurrent.DeadLock;
import com.binlee.thread.concurrent.SyncThread;
import com.binlee.thread.concurrent.WaitNotify;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/10/19
 */
public final class ConcurrenceMain {

    public static void run(String[] args) {
        WayToStartThread.run();
        WayToAbortThread.run();
        DaemonThread.run();
        SyncThread.run();
        DeadLock.run();
        WaitNotify.run();
    }
}
