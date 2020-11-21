package com.binlee.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/9/25
 */
public final class Logger {

    private static final Map<String, Logger> LOGGERS = new ConcurrentHashMap<>();
    private static final String VERBOSE = "V/%s: %s\n";
    private static final String INFO = "\033[34mI/%s: %s\033[0m\n";
    private static final String DEBUG = "\033[36mD/%s: %s\033[0m\n";
    private static final String WARN = "\033[33mW/%s: %s\033[0m\n";
    private static final String ERROR = "\033[31mE/%s: %s\033[0m\n";

    private final String mTag;
    private boolean isDebuggable = true;

    private Logger(String tag) {
        mTag = tag;
    }

    public void setDebuggable(boolean debuggable) {
        isDebuggable = debuggable;
    }

    public void v(String msg) {
        printf(VERBOSE, mTag, msg);
    }

    public void i(String msg) {
        printf(INFO, mTag, msg);
    }

    public void d(String msg) {
        printf(DEBUG, mTag, msg);
    }

    public void w(String msg) {
        printf(WARN, mTag, msg);
    }

    public void e(String msg) {
        e(msg, null);
    }

    public void e(String msg, Throwable tr) {
        printf(ERROR, mTag, msg);
        if (tr == null) {
            return;
        }
        printf(ERROR, mTag, getStackTraceString(tr));
    }

    private void printf(String format, Object... args) {
        if (isDebuggable) {
            System.out.printf(format, args);
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

    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }
        final StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, false);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    public static void run(String[] args) {
        final Logger logger = get(Logger.class);
        logger.v("run() ...");
        logger.i("run() ...");
        logger.d("run() ...");
        logger.w("run() ...");
        logger.e("run() ...");

        System.out.println("\\033[(前缀),m(后缀),格式:\\033[XX;XX;XXm");
        System.out.println("------ 字体颜色30~37 ------");
        System.out.println("\033[30m" + "就是酱紫的");
        System.out.println("\033[31m" + "就是酱紫的");
        System.out.println("\033[32m" + "就是酱紫的");
        System.out.println("\033[37m" + "就是酱紫的");
        // System.out.println("------ 背景颜色40~47 -------");
        // System.out.println("\033[43m" + "就是酱紫的");
        // System.out.println("\033[44m" + "就是酱紫的"+"\033[m");
        // System.out.println("\033[45m" + "就是酱紫的");
        // System.out.println("\033[46m" + "就是酱紫的"+"\033[m");
        // System.out.println("--- 1:加粗,;:隔开,90~97字体颜色变亮 ---");
        // System.out.println("\033[1;90m" + "就是酱紫的");
        // System.out.println("\033[1;94m" + "就是酱紫的");
        // System.out.println("\033[1;95m" + "就是酱紫的");
        // System.out.println("\033[1;97m" + "就是酱紫的");
        // System.out.println("\033[1;93;45m" + "就是酱紫的"+"\033[m");
    }

}
