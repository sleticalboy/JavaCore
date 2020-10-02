package com.binlee.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/9/30
 */
public final class Reflects {

    private static final Map<String, Class<?>> CLASS_MAP = new WeakHashMap<>();

    private Reflects() {
        //no instance
    }

    public static Object getStaticField(Class<?> clazz, String field) throws ReflectException {
        return getField(clazz, field, null);
    }

    public static Object getStaticField(String clazz, String field) throws ReflectException {
        return getField(clazz, field, null);
    }

    public static Object getField(String clazz, String field, Object obj) throws ReflectException {
        try {
            return getField(load(clazz), field, obj);
        } catch (ClassNotFoundException e) {
            throw new ReflectException(e);
        }
    }

    public static Object getField(Class<?> clazz, String field, Object obj) throws ReflectException {
        try {
            final Field f = clazz.getDeclaredField(field);
            f.setAccessible(true);
            // 静态方法不需要传入 obj 对象
            return f.get(obj);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new ReflectException(e);
        }
    }

    public static Object invokeStatic(String clazz, String method, Class<?>[] paramTypes,
                                      Object... args) throws ReflectException {
        try {
            return invokeStatic(load(clazz), method, paramTypes, args);
        } catch (ClassNotFoundException e) {
            throw new ReflectException(e);
        }
    }

    public static Object invokeStatic(Class<?> clazz, String method, Class<?>[] paramTypes,
                                      Object... args) throws ReflectException {
        try {
            final Method m = clazz.getDeclaredMethod(method, paramTypes);
            m.setAccessible(true);
            return m.invoke(null, args);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new ReflectException(e);
        }
    }

    public static Object invoke(Object obj, String method, Class<?>[] paramTypes, Object... args)
            throws ReflectException {
        try {
            Method m = obj.getClass().getDeclaredMethod(method, paramTypes);
            m.setAccessible(true);
            return m.invoke(obj, args);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new ReflectException(e);
        }
    }

    private static Class<?> load(String clazz) throws ClassNotFoundException {
        Class<?> klass = CLASS_MAP.get(clazz);
        if (klass != null) {
            return klass;
        }
        try {
            klass = Class.forName(clazz);
            CLASS_MAP.put(clazz, klass);
            return klass;
        } catch (ClassNotFoundException ignored) {
        }
        try {
            klass = Class.forName(clazz, false, Thread.currentThread().getContextClassLoader());
            CLASS_MAP.put(clazz, klass);
            return klass;
        } catch (ClassNotFoundException ignored) {
        }
        try {
            klass = ClassLoader.getSystemClassLoader().loadClass(clazz);
            CLASS_MAP.put(clazz, klass);
            return klass;
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("", e);
        }
    }
}
