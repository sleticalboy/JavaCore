package com.binlee.processor.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/10/15
 */
public final class Reflects {

    private static final String TAG = "Reflects";

    private Reflects() {
        //no instance
    }

    private static void reflectDetails(Object obj) {
        if (obj == null) {
            return;
        }
        try {
            reflectFields(obj);
            reflectMethods(obj);
        } catch (Throwable e) {
            Log.e(TAG, "reflectDetails() error", e);
        }
    }

    private static void reflectMethods(Object obj) throws InvocationTargetException, IllegalAccessException {
        final Method[] methods = obj.getClass().getDeclaredMethods();
        for (Method method : methods) {
            Log.d(TAG, method.getName() + ": " + method.toGenericString()
                    + "\ninvoke: " + method.invoke(obj));
        }
    }

    private static void reflectFields(Object obj) throws IllegalAccessException {
        final Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            final Object value = field.get(obj);
            if (value == null) {
                Log.d(TAG, field.getName() + ": null");
                continue;
            }
            if (value.getClass().isArray()) {
                final String name = value.getClass().getSimpleName();
                final int length = Array.getLength(value);
                for (int i = 0; i < length; i++) {
                    Log.d(TAG, name + "[" + i + "]: " + Array.get(value, i));
                }
            } else if (value.getClass().isAssignableFrom(List.class)) {
                Log.d(TAG, field.getName() + ": " + value + ", " + value.getClass());
            }
            else {
                Log.d(TAG, field.getName() + ": " + value + ", " + value.getClass());
            }
        }
    }
}
