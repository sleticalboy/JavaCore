package com.binlee;

import com.binlee.thread.DaemonThread;
import com.binlee.thread.WayToAbortThread;
import com.binlee.thread.WayToStartThread;
import com.binlee.thread.concurrent.DeadLock;
import com.binlee.thread.concurrent.SyncThread;
import com.binlee.thread.concurrent.WaitNotify;

public class Main {

    public Main() {
    }

    public static void main(String[] args) {
        // WayToStartThread.main();
        // WayToAbortThread.main();
        // DaemonThread.main();
        // SyncThread.main();
        // DeadLock.main();
        WaitNotify.main();
    }
}
