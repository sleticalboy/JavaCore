package com.binlee.design;

import com.binlee.design.singleton.ServiceManager;
import com.binlee.util.Logger;

import java.lang.reflect.Method;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/9/30
 */
public final class Design {

    private static final Logger sLogger = Logger.get(Design.class);

    public static void run(String... args) {
        System.out.println("Design.run().....");
        // PersonFactory.create("Tom").sayHello();
        // PersonFactory.create("Jack").sayHello();
        try {
            final Method method = ServiceManager.class.getDeclaredMethod("getInstance");
            System.out.println("reflect method: " + method);
            // FIXME: 2020/10/10 静态内部类未定义异常？？
            final ServiceManager manager = ((ServiceManager) method.invoke(null));
            System.out.println("reflect manager: " + manager);
            // manager.addService("hello", Design.class);
            // System.out.println("hello -> " + manager.getService("hello"));
        } catch (Exception e) {
            sLogger.e("run() error: " + e, e);
        }
    }
}
