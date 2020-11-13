package com.binlee.design.factory;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/10/13
 */
public final class Factories {

    // 任何可以产生对象的方法或类，都可以称之为工厂
    // 简单工厂：如果要创建的产品不多，只要一个工厂类就可以完成，这种模式叫“简单工厂模式”。
    // 在简单工厂模式中创建实例的方法通常为静态（static）方法，因此简单工厂模式又叫作静态工厂方法模式。
    // 简单工厂模式每增加一个产品就要增加一个具体产品类和一个对应的具体工厂类，这增加了系统的复杂度，违背了“开闭原则”。
    //    可扩展性差

    // 工厂方法：是对简单工厂的进一步抽象化, 其好处是可以使系统在不修改原来代码的情况下引进新的产品，即满足开闭原则。
    // 抽象工厂：一种为访问类提供一个创建一组相关或相互依赖对象的接口，且访问类无须指定所要产品的具体类就能得到同族的不同等级的产品的模式结构。
    // 抽象工厂模式是工厂方法模式的升级版本，工厂方法模式只生产一个等级的产品，而抽象工厂模式可生产多个等级的产品。

    public static void exec() {
        SimpleFactory.exec();
        FactoryMethod.exec();
        AbstractFactory.exec();
    }
}
