package com.binlee.util;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/9/25
 */
public abstract class NamedRunnable implements Runnable {

    private final String mPrefix;
    private final int mId;

    protected NamedRunnable(String prefix, int id) {
        mPrefix = prefix;
        mId = id;
    }

    public final int getId() {
        return mId;
    }

    @Override
    public String toString() {
        // Worker#10 run in Thread#8
        return mPrefix + "#" + mId;
    }
}
