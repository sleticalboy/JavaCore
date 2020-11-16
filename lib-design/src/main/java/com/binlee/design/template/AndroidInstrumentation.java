package com.binlee.design.template;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/16
 */
public class AndroidInstrumentation {

    public AndroidActivity newActivity(String cls) throws ClassNotFoundException {
        try {
            final Class<?> clazz = getClass().getClassLoader().loadClass(cls);
            final Constructor<?> constructor = clazz.getDeclaredConstructor();
            final Object instance = constructor.newInstance();
            return ((AndroidActivity) instance);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException
                | InstantiationException e) {
            throw new ClassNotFoundException("cls " + cls + " not found", e);
        }
    }

    public void callLifecycleMethod(AndroidActivity activity) {
        activity.onCreate();
        activity.onStart();
        activity.onResume();
        activity.onPause();
        activity.onRestart();
        activity.onStop();
        activity.onDestroy();
    }
}
