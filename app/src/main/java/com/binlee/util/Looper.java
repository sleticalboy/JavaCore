package com.binlee.util;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/9/26
 */
public final class Looper {

    private static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();
    private static final Logger sLogger = Logger.get(Looper.class);
    private final Thread mThread;

    private Looper() {
        mThread = Thread.currentThread();
    }

    public static void prepare() {
        if (myLooper() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new Looper());
    }

    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    public static void loop() {
        sLogger.log("loop() running...");
    }

    public Thread getThread() {
        return mThread;
    }

    @Override
    public String toString() {
        return "Looper (" + mThread.getName() + ", tid " + mThread.getId() + ") {"
                + Integer.toHexString(System.identityHashCode(this)) + "}";
    }
}
