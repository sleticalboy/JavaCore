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

    // 面向对象三大特征
    // 封装
    // 继承
    // 多态
    // 面向对象六大原则
    // 职责单一原则：一个类只有一个发生变化的原因
    // 开闭原则：对扩展开放，对修改关闭
    // 里氏替换原则：基类可以出现的地方，子类必定可以出现
    // 依赖倒置原则：高层不应该依赖低层，应该依赖于抽象；抽象不应该依赖于实现，实现应依赖于抽象
    // 接口隔离原则
    // 迪米特法则（最少知识原则）

    // 23 种设计模式
    // 按照目的来划分
    //    创建型模式
    //    结构型模式
    //    行为型模式
    // 按照作用来划分
    //    类模式
    //    对象模式
    ////////////////////////////////////////////////////
    // 创建型模式分为以下 5 种：
    // 1. 单例（Singleton）模式：某个类只能生成一个实例，该类提供了一个全局访问点供外部获取该实例，其拓展是有限多例模式。
    // 2. 原型（Prototype）模式：将一个对象作为原型，通过对其进行复制而克隆出多个和原型类似的新实例。
    // 3. 工厂方法（FactoryMethod）模式：定义一个用于创建产品的接口，由子类决定生产什么产品。
    // 4. 抽象工厂（AbstractFactory）模式：提供一个创建产品族的接口，其每个子类可以生产一系列相关的产品。
    // 5. 建造者（Builder）模式：将一个复杂对象分解成多个相对简单的部分，然后根据不同需要分别创建它们，最后构建成该复杂对象。
    ////////////////////////////////////////////////////
    // 结构型模式分为以下 7 种：
    // 1. 代理（Proxy）模式：为某对象提供一种代理以控制对该对象的访问。即客户端通过代理间接地访问该对象，从而限制、增强或修改该对象的一些特性。
    // 2. 适配器（Adapter）模式：将一个类的接口转换成客户希望的另外一个接口，使得原本由于接口不兼容而不能一起工作的那些类能一起工作。
    // 3. 桥接（Bridge）模式：将抽象与实现分离，使它们可以独立变化。它是用组合关系代替继承关系来实现的，从而降低了抽象和实现这两个可变维度的耦合度。
    // 4. 装饰（Decorator）模式：动态地给对象增加一些职责，即增加其额外的功能。
    // 5. 外观（Facade）模式：为多个复杂的子系统提供一个一致的接口，使这些子系统更加容易被访问。
    // 6. 享元（Flyweight）模式：运用共享技术来有效地支持大量细粒度对象的复用。
    // 7. 组合（Composite）模式：将对象组合成树状层次结构，使用户对单个对象和组合对象具有一致的访问性。
    ////////////////////////////////////////////////////
    // 行为型模式是 GoF 设计模式中最为庞大的一类，它包含以下 11 种模式
    // 1. 模板方法（Template Method）模式：定义一个操作中的算法骨架，将算法的一些步骤延迟到子类中，使得子类在可以不改变该算法结构的情况下重定义该算法的某些特定步骤。
    // 2. 策略（Strategy）模式：定义了一系列算法，并将每个算法封装起来，使它们可以相互替换，且算法的改变不会影响使用算法的客户。
    // 3. 命令（Command）模式：将一个请求封装为一个对象，使发出请求的责任和执行请求的责任分割开。
    // 4. 职责链（Chain of Responsibility）模式：把请求从链中的一个对象传到下一个对象，直到请求被响应为止。通过这种方式去除对象之间的耦合。
    // 5. 状态（State）模式：允许一个对象在其内部状态发生改变时改变其行为能力。
    // 6. 观察者（Observer）模式：多个对象间存在一对多关系，当一个对象发生改变时，把这种改变通知给其他多个对象，从而影响其他对象的行为。
    // 7. 中介者（Mediator）模式：定义一个中介对象来简化原有对象之间的交互关系，降低系统中对象间的耦合度，使原有对象之间不必相互了解。
    // 8. 迭代器（Iterator）模式：提供一种方法来顺序访问聚合对象中的一系列数据，而不暴露聚合对象的内部表示。
    // 9. 访问者（Visitor）模式：在不改变集合元素的前提下，为一个集合中的每个元素提供多种访问方式，即每个元素有多个访问者对象访问。
    // 10. 备忘录（Memento）模式：在不破坏封装性的前提下，获取并保存一个对象的内部状态，以便以后恢复它。
    // 11. 解释器（Interpreter）模式：提供如何定义语言的文法，以及对语言句子的解释方法，即解释器。

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
