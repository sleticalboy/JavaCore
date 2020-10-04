package com.binlee.processor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/10/4
 */
public final class Utils {

    public static void log(ProcessingEnvironment env, Object obj) {
        env.getMessager().printMessage(Diagnostic.Kind.NOTE, extract(obj));
    }

    public static void logE(ProcessingEnvironment env, Object obj) {
        env.getMessager().printMessage(Diagnostic.Kind.ERROR, extract(obj));
    }

    private static String extract(Object obj) {
        return obj instanceof String ? ((String) obj) : obj == null ? "{null}" : obj.toString();
    }
}
