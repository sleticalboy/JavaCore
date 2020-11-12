package com.binlee.design;

import com.binlee.design.factory.Factories;
import com.binlee.design.prototype.Prototypes;
import com.binlee.design.singleton.ServiceManager;
import com.binlee.design.singleton.Singles;
import com.binlee.design.strategy.Strategies;
import com.binlee.util.Logger;

import java.lang.reflect.Method;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/9/30
 * see http://c.biancheng.net/design_pattern/
 */
public final class DesignMain {

    private static final Logger sLogger = Logger.get(DesignMain.class);

    // 23 种设计模式
    // 按照目的来划分
    //    创建型模式
    //    结构型模式
    //    行为型模式
    // 按照作用来划分
    //    类模式
    //    对象模式

    public static void run(String... args) {
        foo();
        // 单例模式
        Singles.exec();
        // 策略模式
        Strategies.exec();
        // 工厂模式
        Factories.exec();
        // 原型模式
        Prototypes.exec();
    }

    private static void foo() {
        sLogger.d("foo().....");
        // PersonFactory.create("Tom").sayHello();
        // PersonFactory.create("Jack").sayHello();
        try {
            final Method method = ServiceManager.class.getDeclaredMethod("getInstance");
            sLogger.d("reflect method: " + method);
            // FIXME: 2020/10/10 静态内部类未定义异常？？
            final ServiceManager manager = ((ServiceManager) method.invoke(null));
            sLogger.d("reflect manager: " + manager);
            // manager.addService("hello", Design.class);
            // System.out.println("hello -> " + manager.getService("hello"));
        } catch (Exception e) {
            sLogger.e("run() error: " + e, e);
        }
    }
}
