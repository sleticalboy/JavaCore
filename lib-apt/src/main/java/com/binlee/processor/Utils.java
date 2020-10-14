package com.binlee.processor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
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

    private static Object sObj;
    public static String dumpObj(Object obj) {
        if (obj == sObj) {
            return null;
        }
        sObj = obj;
        String msg = "printObj() ----------- " + getCls(obj).getName() + "@" + obj.hashCode();
        if (obj instanceof Element) {
            msg += ", kind: " + ((Element) obj).getKind();
        }
        d(msg);
        if (obj instanceof TypeParameterElement) {
            // a formal type parameter of a generic class, interface, method, or constructor
            // 形式参数表
            dumpFormalParameter(((TypeParameterElement) obj));
        } else if (obj instanceof TypeElement) {
            // a class or interface
            // 类或接口
            dumpType(((TypeElement) obj));
        } else if (obj instanceof ExecutableElement) {
            // static_init, instance_init, constructor or method
            // 静态代码块/构造代码块/构造器/函数
            dumpExecutable(((ExecutableElement) obj));
        } else if (obj instanceof VariableElement) {
            // a field, constant, method or constructor parameter, local variable,
            // resource variable, or exception parameter
            // 表示字段，常量，方法或构造函数参数，局部变量，资源变量或异常参数
            dumpVariable(((VariableElement) obj));
        }
        return String.valueOf(obj);
    }

    private static void dumpFormalParameter(TypeParameterElement param) {
        //
    }

    private static void dumpVariable(VariableElement var) {
        //
    }

    private static void dumpType(TypeElement type) {
        final Element enclosingElement = type.getEnclosingElement();
        d("enclosingElement(包名): " + enclosingElement);
        // printObj(enclosingElement);

        final List<? extends Element> enclosedElements = type.getEnclosedElements();
        d("enclosingElements(方法): " + enclosedElements);
        // if (enclosedElements != null) {
        //     for (Element item : enclosedElements) {
        //         printObj(item);
        //     }
        // }

        final TypeMirror mirror = type.asType();
        d("asType(类名): " + mirror);
        // printObj(mirror);

        final Name simpleName = type.getSimpleName();
        d("simpleName(简单类名): " + simpleName);
        // printObj(simpleName);

        final List<? extends AnnotationMirror> mirrors = type.getAnnotationMirrors();
        d("annotationMirrors(注解信息): " + mirrors);
        // if (mirrors != null) {
        //     for (AnnotationMirror am : mirrors) {
        //         printObj(am);
        //     }
        // }
        d("toString(即类名): " + type);
    }

    private static void printPackage(PackageElement element) {
        //
    }

    private static void printConstructor(Element element) {
        //
    }

    private static void dumpExecutable(ExecutableElement ele) {
        d("simpleName(): " + ele.getSimpleName() + ", asType(): " + ele.asType());
        d("asType(): " + ele.asType());
        d("typeParams: " + ele.getTypeParameters());
        d("defaultValue(): " + ele.getDefaultValue());
        d("receiverValue(): " + ele.getReceiverType());
        d("returnType(): " + ele.getReturnType());
        d("thrownTypes(): " + ele.getThrownTypes());
        d("params(): " + ele.getParameters());
        d("isDefault(): " + ele.isDefault() + ", isVarArgs(): " + ele.isVarArgs());
    }

    public static Class<?> getCls(Object obj) {
        if (null == obj) {
            return null;
        }
        return obj.getClass();
    }

    // copy from com.binlee.util.Logger
    private static final String VERBOSE = "V/%s: %s\n";
    private static final String INFO = "\033[34mI/%s: %s\033[0m\n";
    private static final String DEBUG = "\033[36mD/%s: %s\033[0m\n";
    private static final String WARN = "\033[33mW/%s: %s\033[0m\n";
    private static final String ERROR = "\033[31mE/%s: %s\033[0m\n";

    private static final String TAG = "Utils";

    public static void v(String msg) {
        printf(VERBOSE, TAG, msg);
    }

    public static void i(String msg) {
        printf(INFO, TAG, msg);
    }

    public static void d(String msg) {
        printf(DEBUG, TAG, msg);
    }

    public static void w(String msg) {
        printf(WARN, TAG, msg);
    }

    public static void e(String msg) {
        e(msg, null);
    }

    public static void e(String msg, Throwable tr) {
        printf(ERROR, TAG, msg);
        if (tr == null) {
            return;
        }
        for (StackTraceElement ste : tr.getStackTrace()) {
            printf(ERROR, TAG, ste.toString());
        }
    }

    private static void printf(String format, Object... args) {
        System.out.printf(format, args);
    }
}
