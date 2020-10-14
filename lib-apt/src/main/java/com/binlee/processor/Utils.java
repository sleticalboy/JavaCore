package com.binlee.processor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.util.List;

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
        // if (obj instanceof Throwable) {
        //     final StringBuilder buffer = new StringBuilder();
        //     buffer.append(((Throwable) obj).getMessage()).append('\n');
        //     for (StackTraceElement ste : ((Throwable) obj).getStackTrace()) {
        //         buffer.append(ste.toString()).append('\n');
        //     }
        //     return buffer.toString();
        // }
        return obj instanceof String ? ((String) obj) : obj == null ? "{null}" : obj.toString();
    }

    public static String printObj(Object obj) {
        if (obj instanceof Element) {
            final Element element = (Element) obj;
            switch (element.getKind()) {
                case PACKAGE:
                case CLASS:
                case CONSTRUCTOR:
                    return String.valueOf(((Element) obj).asType());
            }
            System.out.println("\nUtils#toString() " + obj.getClass() + ", Kind: " + element.getKind());

            final Element enclosingElement = element.getEnclosingElement();
            System.out.println("enclosingElement(包名): " + enclosingElement + ", " + getCls(enclosingElement));
            printObj(enclosingElement);

            final List<? extends Element> enclosedElements = element.getEnclosedElements();
            System.out.println("enclosingElements(方法): " + enclosedElements);
            if (enclosedElements != null) {
                for (Element item : enclosedElements) {
                    printObj(item);
                }
            }

            final TypeMirror mirror = element.asType();
            System.out.println("asType(类名): " + mirror);
            printObj(mirror);

            final Name simpleName = element.getSimpleName();
            System.out.println("simpleName(简单类名): " + simpleName);
            printObj(simpleName);

            final List<? extends AnnotationMirror> mirrors = element.getAnnotationMirrors();
            System.out.println("annotationMirrors(注解信息): " + mirrors);
            if (mirrors != null) {
                for (AnnotationMirror am : mirrors) {
                    printObj(am);
                }
            }

            System.out.println("toString(即类名): " + obj + "\n");
        }
        return String.valueOf(obj);
    }

    public static Class<?> getCls(Object obj) {
        if (null == obj) {
            return null;
        }
        return obj.getClass();
    }
}
