package com.binlee.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/9/25
 */
public final class Logger {

    private static final Map<String, Logger> LOGGERS = new ConcurrentHashMap<>();
    private final String mTag;

    private Logger(String tag) {
        mTag = tag;
    }

    public void log(String msg) {
        System.out.println(mTag + ": " + msg);
    }

    public void err(String msg, Throwable tr) {
        System.err.println(mTag + ": " + msg);
        final StackTraceElement[] trace = tr != null ? tr.getStackTrace() : Thread.currentThread().getStackTrace();
        for (StackTraceElement ste : trace) {
            System.err.println(ste.toString());
        }
    }

    public static Logger get(Class<?> clazz) {
        synchronized (LOGGERS) {
            Logger logger = LOGGERS.get(clazz.getName());
            if (logger == null) {
                logger = new Logger(clazz.getSimpleName());
                LOGGERS.put(clazz.getName(), logger);
            }
            return logger;
        }
    }
}
