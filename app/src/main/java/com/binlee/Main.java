package com.binlee;

import com.binlee.annotation.Person;
import com.binlee.design.Design;
import com.binlee.thread.DaemonThread;
import com.binlee.thread.WayToAbortThread;
import com.binlee.thread.WayToStartThread;
import com.binlee.thread.concurrent.DeadLock;
import com.binlee.thread.concurrent.SyncThread;
import com.binlee.thread.concurrent.WaitNotify;
import com.binlee.util.Logger;

public final class Main {

    public Main() {
    }

    public static void main(String[] args) {
        // WayToStartThread.run();
        // WayToAbortThread.run();
        // DaemonThread.run();
        // SyncThread.run();
        // DeadLock.run();
        // WaitNotify.run();
        // Person.run();
        Design.run(args);
        // Logger.run(args);
    }
}
