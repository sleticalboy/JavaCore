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

    public static void dumpObj(Object obj) {
        if (obj instanceof Element) {
            // Element family
            dumpElementFamily(((Element) obj));
        } else if (obj instanceof TypeMirror) {
            // TypeMirror family
            dumpTypeMirrorFamily(((TypeMirror) obj));
        }
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
        } else if (obj instanceof PackageElement) {
            // a package program element
            // 表示包
            dumpPackage(((PackageElement) obj));
        }
    }

    private static void dumpTypeMirrorFamily(TypeMirror obj) {
        if (obj instanceof WildcardType) {
            //
            dumpWildcard(((WildcardType) obj));
        } else if (obj instanceof PrimitiveType) {
            //
            dumpPrimitive(((PrimitiveType) obj));
        } else if (obj instanceof NoType) {
            // A pseudo-type used where no actual type is appropriate.
            // 在没有实际类型的情况下使用的伪类型(void package, the superclass of Object)
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
        } else if (obj instanceof ReferenceType) {
            // 表示引用类型： 包括类和接口，数组，类型变量，空类型
            if (obj instanceof TypeVariable) {
                //
                dumpVariable0(((TypeVariable) obj));
            } else if (obj instanceof NullType) {
                //
                dumpNull(((NullType) obj));
            } else if (obj instanceof ArrayType) {
                //
                dumpArray(((ArrayType) obj));
            } else if (obj instanceof DeclaredType) {
                // 类或是接口
                if (obj instanceof ErrorType) {
                    //
                    dumpError(((ErrorType) obj));
                } else {
                    Log.i(TAG, "dumpDeclared() -> " + getObjInfo(obj) + " " + obj);
                }
            }
        }
    }

    private static void dumpVariable0(TypeVariable obj) {
        Log.d(TAG, "dumpVariable0() -> " + getObjInfo(obj));
    }

    private static void dumpError(ErrorType obj) {
        Log.d(TAG, "dumpError() -> " + getObjInfo(obj));
    }

    private static void dumpNull(NullType obj) {
        Log.d(TAG, "dumpNull() -> " + getObjInfo(obj));
    }

    private static void dumpArray(ArrayType obj) {
        Log.d(TAG, "dumpArray() -> " + getObjInfo(obj));
    }

    private static void dumpWildcard(WildcardType obj) {
        Log.d(TAG, "dumpWildcard() -> " + getObjInfo(obj));
    }

    private static void dumpPrimitive(PrimitiveType obj) {
        Log.d(TAG, "dumpPrimitive() -> " + getObjInfo(obj));
    }

    private static void dumpNo(NoType obj) {
        Log.i(TAG, "dumpNo() -> " + getObjInfo(obj));
    }

    private static void dumpExecutable0(ExecutableType obj) {
        Log.i(TAG, "dumpExecutable0() -> " + getObjInfo(obj));
    }

    private static void dumpIntersection(IntersectionType obj) {
        Log.d(TAG, "dumpIntersection() -> " + getObjInfo(obj));
    }

    private static void dumpUnion(UnionType obj) {
        Log.d(TAG, "dumpNinon() -> " + getObjInfo(obj));
    }

    private static void dumpFormalParameter(TypeParameterElement obj) {
        Log.i(TAG, "dumpFormalParameter() -> " + getObjInfo(obj));
    }

    private static void dumpVariable(VariableElement obj) {
        Log.i(TAG, "dumpVariable() -> " + getObjInfo(obj));
    }

    private static void dumpType(TypeElement obj) {
        Log.i(TAG, "dumpType() -> " + getObjInfo(obj));
        // 向上获取为 package（当前元素的内嵌类型为 top-level 时）
        // 或外部类（当前元素的内嵌类型为 member 时）
        final Element enclosingElement = obj.getEnclosingElement();
        Log.d(TAG, "enclosingElement(包名): " + enclosingElement);

        // 向下获取为构造器、内部类、方法, 不包括构造代码块和静态代码块
        final List<? extends Element> enclosedElements = obj.getEnclosedElements();
        Log.d(TAG, "enclosingElements(方法): " + enclosedElements);

        // 获取当前元素所定义的类型
        final TypeMirror mirror = obj.asType();
        Log.d(TAG, "asType(类名): " + mirror);

        Log.d(TAG, "simpleName(简单类名): " + obj.getSimpleName());

        // 获取注解信息
        final List<? extends AnnotationMirror> mirrors = obj.getAnnotationMirrors();
        Log.d(TAG, "annotationMirrors(注解信息): " + mirrors);
        // 获取接口信息
        Log.d(TAG, "interfaces: " + obj.getInterfaces());
        // 获取父类信息
        Log.d(TAG, "super: " + obj.getSuperclass());
        // 获取当前元素的嵌套类型
        // top level class: 独立的类，类名与文件名一致
        // member class: 成员类：非静态内部类与静态内部类
        // local class: 方法或代码块中的类
        Log.d(TAG, "nesting kind: " + obj.getNestingKind());
        Log.d(TAG, "toString(即类名): " + obj);

        dumpElementFamily(enclosingElement);

        // if (enclosedElements != null) {
        //     for (Element item : enclosedElements) {
        //         dumpElementFamily(item);
        //     }
        // }

        dumpTypeMirrorFamily(mirror);
    }

    private static void dumpPackage(PackageElement pkg) {
        Log.i(TAG, "dumpPackage() -> " + getObjInfo(pkg));
        // 获取包全名、简单名
        Log.d(TAG, "pkg: " + pkg.getQualifiedName() + ", simple: " + pkg.getSimpleName());
        // 向外获取封闭的元素: 如果当前元素的 package 会返回 null
        Log.d(TAG, "enclosing element: " + pkg.getEnclosingElement());
        // 向内获取封闭的元素：当前包下的类，不包括子包中的类和内部类
        Log.d(TAG, "enclosed elements: " + pkg.getEnclosedElements());
        // 当前 package 是否未命名
        Log.d(TAG, "is unnamed: " + pkg.isUnnamed());
        // 获取当前元素所定义的类型
        Log.d(TAG, "asType: " + pkg.asType());

        dumpTypeMirrorFamily(pkg.asType());
    }

    private static void dumpConstructor(Element element) {
        Log.d(TAG, "dumpConstructor() -> " + getObjInfo(element));
    }

    private static void dumpExecutable(ExecutableElement ele) {
        Log.i(TAG, "dumpExecutable() -> " + getObjInfo(ele));
        Log.d(TAG, "simpleName(方法名): " + ele.getSimpleName());
        final TypeMirror mirror = ele.asType();
        Log.d(TAG, "asType(参数及返回值类型): " + mirror);
        Log.d(TAG, "typeParams(形参类型): " + ele.getTypeParameters());
        Log.d(TAG, "defaultValue(): " + ele.getDefaultValue());
        Log.d(TAG, "receiverValue(): " + ele.getReceiverType());
        Log.d(TAG, "returnType(): " + ele.getReturnType());
        Log.d(TAG, "thrownTypes(): " + ele.getThrownTypes());
        Log.d(TAG, "params(): " + ele.getParameters());
        Log.d(TAG, "isDefault(): " + ele.isDefault() + ", isVarArgs(): " + ele.isVarArgs());
    }

    public static String getObjInfo(Object obj) {
        if (null == obj) {
            return null;
        }
        String msg = "";
        if (obj instanceof Element) {
            msg += "kind: " + ((Element) obj).getKind();
        } else if (obj instanceof TypeMirror) {
            msg += "kind: " + ((TypeMirror) obj).getKind();
        }
        msg += " " + getSimple(obj.getClass());
        final Class<?>[] interfaces = obj.getClass().getInterfaces();
        if (interfaces.length >= 1) {
            msg += ", super: " + getSimple(interfaces[0]);
        } else {
            msg += ", super: " + getSimple(obj.getClass().getSuperclass());
        }
        return msg;
    }

    private static String getSimple(Class<?> cls) {
        if (cls == null) {
            return "";
        }
        final String name = cls.getName();
        return name.substring(name.lastIndexOf('.') + 1);
    }
}
