package com.binlee.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/9/30
 */
public final class Reflects {

    private Reflects() {
        //no instance
    }

    public static Object getStaticField(String clazz, String field)
            throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        return getField(clazz, field, null);
    }

    public static Object getField(String clazz, String field, Object obj)
            throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> klass = Class.forName(clazz);
        // if (klass == null) {
        //     klass = Class.forName(clazz, false, Thread.currentThread().getContextClassLoader());
        //     System.out.println("load class second hit: " + klass);
        // }
        // if (klass == null) {
        //     klass = ClassLoader.getSystemClassLoader().loadClass(clazz);
        //     System.out.println("load class third hit: " + klass);
        // }
        final Field f = klass.getDeclaredField(field);
        f.setAccessible(true);
        // 静态方法不需要传入 obj 对象
        return f.get(obj);
    }

    public static Object invokeStatic(Class<?> clazz, String method, Class<?> paramTypes, Object... args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method m = clazz.getDeclaredMethod(method, paramTypes);
        m.setAccessible(true);
        return m.invoke(null, args);
    }

    public static Object invoke(Object obj, String method, Class<?>[] paramTypes, Object... args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = obj.getClass().getDeclaredMethod(method, paramTypes);
        m.setAccessible(true);
        return m.invoke(obj, args);
    }
}
