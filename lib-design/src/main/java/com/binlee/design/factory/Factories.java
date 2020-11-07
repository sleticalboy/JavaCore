package com.binlee.design.factory;

import com.binlee.design.factory.impl.Jack;
import com.binlee.design.factory.impl.Maria;
import com.binlee.design.factory.interfaces.IPerson;
import com.binlee.util.Logger;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/10/13
 */
public class Factories {

    private static final Logger sLogger = Logger.get(Factories.class);

    // 任何可以产生对象的方法或类，都可以称之为工厂
    // 简单工厂：如果要创建的产品不多，只要一个工厂类就可以完成，这种模式叫“简单工厂模式”。
    // 在简单工厂模式中创建实例的方法通常为静态（static）方法，因此简单工厂模式又叫作静态工厂方法模式。
    // 简单工厂模式每增加一个产品就要增加一个具体产品类和一个对应的具体工厂类，这增加了系统的复杂度，违背了“开闭原则”。
    //    可扩展性差

    // 工厂方法
    // 抽象工厂

    public static void main(String[] args) {
        exec();
    }

    public static void exec() {
        sLogger.d("simple factory run...");
        final SimpleFactory factory = new SimpleFactory();
        sLogger.d("new maria: " + factory.newMaria());
        sLogger.d("new jack: " + factory.newJack());
        sLogger.d("new tom: " + factory.newTom());

        final PersonFactory personFactory = PersonFactory.newInstance();
        final IPerson maria = personFactory.create("Maria");
        sLogger.d("exec() maria: " + maria);
        sLogger.d("static factory run...");
    }

    public static class SimpleFactory {

        public static IPerson newMaria() {
            return new Maria();
        }

        public static IPerson newJack() {
            return new Jack();
        }

        public static IPerson newTom() {
            return new Maria();
        }

        public IPerson create(String id) {
            if (id == null) {
                throw new IllegalArgumentException("Unexpected id: null");
            }
            if (id.equals("Maria")) {
                return new com.binlee.design.factory.impl.Maria();
            }
            if (id.equals("Jack")) {
                return new com.binlee.design.factory.impl.Jack();
            }
            if (id.equals("Tom")) {
                return new com.binlee.design.factory.impl.Tom();
            }
            throw new IllegalArgumentException("Unknown id: " + id);
        }
    }

    public static class StaticFactory {
    }

    public static class FactoryMethod {
    }

    public static class AbstractFactory {
    }
}
