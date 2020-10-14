package com.binlee.processor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/10/4
 */
public final class Utils {

    static boolean sDebuggable = false;

    public static void log(ProcessingEnvironment env, Object obj) {
        if (sDebuggable) {
            env.getMessager().printMessage(Diagnostic.Kind.NOTE, extract(obj));
        }
    }

    public static void logE(ProcessingEnvironment env, Object obj) {
        if (sDebuggable) {
            env.getMessager().printMessage(Diagnostic.Kind.ERROR, extract(obj));
        }
    }

    private static String extract(Object obj) {
        if (obj instanceof Throwable) {
            final StringBuilder buffer = new StringBuilder();
            buffer.append(((Throwable) obj).getMessage()).append('\n');
            for (StackTraceElement ste : ((Throwable) obj).getStackTrace()) {
                buffer.append(ste.toString()).append('\n');
            }
            return buffer.toString();
        }
        return obj instanceof String ? ((String) obj) : obj == null ? "{null}" : obj.toString();
    }
}
