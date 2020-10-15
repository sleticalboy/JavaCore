package com.binlee.processor.util;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.tools.Diagnostic;
import java.util.List;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/10/4
 */
public final class Utils {

    private static final String TAG = "Utils";

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
        if (obj instanceof Element) {
            // Element family
            dumpElementFamily(((Element) obj));
        } else if (obj instanceof TypeMirror) {
            // TypeMirror family
            dumpTypeMirrorFamily(((TypeMirror) obj));
        }
        return String.valueOf(obj);
    }

    private static void dumpElementFamily(Element obj) {
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
    }

    private static void dumpTypeMirrorFamily(TypeMirror obj) {
        if (obj instanceof TypeVariable) {
            //
            dumpVariable0(((TypeVariable) obj));
        } else if (obj instanceof ErrorType) {
            //
            dumpError(((ErrorType) obj));
        } else if (obj instanceof NullType) {
            //
            dumpNull(((NullType) obj));
        } else if (obj instanceof ArrayType) {
            //
            dumpArray(((ArrayType) obj));
        } else if (obj instanceof WildcardType) {
            //
            dumpWildcard(((WildcardType) obj));
        } else if (obj instanceof PrimitiveType) {
            //
            dumpPrimitive(((PrimitiveType) obj));
        } else if (obj instanceof NoType) {
            //
            dumpNo(((NoType) obj));
        } else if (obj instanceof ExecutableType) {
            //
            dumpExecutable0(((ExecutableType) obj));
        } else if (obj instanceof IntersectionType) {
            //
            dumpIntersection(((IntersectionType) obj));
        } else if (obj instanceof UnionType) {
            //
            dumpUnion(((UnionType) obj));
        }
    }

    private static void dumpVariable0(TypeVariable obj) {
        Log.d(TAG, "dumpVariable0() ------> " + getCls(obj).getName() + "@" + obj.hashCode() + ", kind: " + obj.getKind());
    }

    private static void dumpError(ErrorType obj) {
        Log.d(TAG, "dumpError() ------> " + getCls(obj).getName() + "@" + obj.hashCode() + ", kind: " + obj.getKind());
    }

    private static void dumpNull(NullType obj) {
        Log.d(TAG, "dumpNull() ------> " + getCls(obj).getName() + "@" + obj.hashCode() + ", kind: " + obj.getKind());
    }

    private static void dumpArray(ArrayType obj) {
        Log.d(TAG, "dumpArray() ------> " + getCls(obj).getName() + "@" + obj.hashCode() + ", kind: " + obj.getKind());
    }

    private static void dumpWildcard(WildcardType obj) {
        Log.d(TAG, "dumpWildcard() ------> " + getCls(obj).getName() + "@" + obj.hashCode() + ", kind: " + obj.getKind());
    }

    private static void dumpPrimitive(PrimitiveType obj) {
        Log.d(TAG, "dumpPrimitive() ------> " + getCls(obj).getName() + "@" + obj.hashCode() + ", kind: " + obj.getKind());
    }

    private static void dumpNo(NoType obj) {
        Log.d(TAG, "dumpNo() ------> " + getCls(obj).getName() + "@" + obj.hashCode() + ", kind: " + obj.getKind());
    }

    private static void dumpExecutable0(ExecutableType obj) {
        Log.d(TAG, "dumpExecutable0() ------> " + getCls(obj).getName() + "@" + obj.hashCode() + ", kind: " + obj.getKind());
    }

    private static void dumpIntersection(IntersectionType obj) {
        Log.d(TAG, "dumpIntersection() ------> " + getCls(obj).getName() + "@" + obj.hashCode() + ", kind: " + obj.getKind());
    }

    private static void dumpUnion(UnionType obj) {
        Log.d(TAG, "dumpNinon() ------> " + getCls(obj).getName() + "@" + obj.hashCode() + ", kind: " + obj.getKind());
    }

    private static void dumpFormalParameter(TypeParameterElement param) {
        //
    }

    private static void dumpVariable(VariableElement var) {
        //
    }

    private static void dumpType(TypeElement type) {
        Log.d(TAG, "dumpType() ------> " + getCls(type).getName() + "@" + type.hashCode() + ", kind: " + type.getKind());
        final Element enclosingElement = type.getEnclosingElement();
        Log.d(TAG, "enclosingElement(包名): " + enclosingElement);
        dumpObj(enclosingElement);
        // reflectDetails(enclosingElement);

        final List<? extends Element> enclosedElements = type.getEnclosedElements();
        Log.d(TAG, "enclosingElements(方法): " + enclosedElements);
        if (enclosedElements != null) {
            for (Element item : enclosedElements) {
                dumpObj(item);
                // reflectDetails(item);
            }
        }

        final TypeMirror mirror = type.asType();
        Log.d(TAG, "asType(类名): " + mirror);
        // printObj(mirror);

        final Name simpleName = type.getSimpleName();
        Log.d(TAG, "simpleName(简单类名): " + simpleName);
        // printObj(simpleName);

        final List<? extends AnnotationMirror> mirrors = type.getAnnotationMirrors();
        Log.d(TAG, "annotationMirrors(注解信息): " + mirrors);
        // if (mirrors != null) {
        //     for (AnnotationMirror am : mirrors) {
        //         printObj(am);
        //     }
        // }
        Log.d(TAG, "toString(即类名): " + type);
    }

    private static void dumpPackage(PackageElement pkg) {
        Log.d(TAG, "dumpPackage() ------> " + getCls(pkg).getName() + "@" + pkg.hashCode() + ", kind: " + pkg.getKind());
    }

    private static void dumpConstructor(Element element) {
        Log.d(TAG, "dumpPackage() ------> " + getCls(element).getName() + "@" + element.hashCode() + ", kind: " + element.getKind());
    }

    private static void dumpExecutable(ExecutableElement ele) {
        Log.d(TAG, "dumpExecutable() ------> " + getCls(ele).getName() + "@" + ele.hashCode() + ", kind: " + ele.getKind());
        Log.d(TAG, "simpleName(方法名): " + ele.getSimpleName());
        final TypeMirror mirror = ele.asType();
        Log.d(TAG, "asType(参数及返回值类型): " + mirror);
        dumpObj(mirror);
        Log.d(TAG, "typeParams(形参类型): " + ele.getTypeParameters());
        Log.d(TAG, "defaultValue(): " + ele.getDefaultValue());
        Log.d(TAG, "receiverValue(): " + ele.getReceiverType());
        Log.d(TAG, "returnType(): " + ele.getReturnType());
        Log.d(TAG, "thrownTypes(): " + ele.getThrownTypes());
        Log.d(TAG, "params(): " + ele.getParameters());
        Log.d(TAG, "isDefault(): " + ele.isDefault() + ", isVarArgs(): " + ele.isVarArgs());
    }

    public static Class<?> getCls(Object obj) {
        if (null == obj) {
            return null;
        }
        return obj.getClass();
    }
}
