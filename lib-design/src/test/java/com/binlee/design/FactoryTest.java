package com.binlee.design;

import com.binlee.design.factory.AbstractFactory.*;
import com.binlee.design.factory.FactoryMethod.*;
import com.binlee.design.factory.SimpleFactory;
import org.junit.Test;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/13
 */
public final class FactoryTest {

    // 测试时需要在 build.gradle 文件的 dependency 节点下添加 implementation(project(":lib-util"))

    @Test
    public void simpleFactory() {
        // SimpleFactory.exec();
        System.out.println("simple/static factory run...");
        System.out.println("new maria: " + SimpleFactory.newMaria());
        System.out.println("new jack: " + SimpleFactory.newJack());
        System.out.println("new tom: " + SimpleFactory.newTom());
    }

    @Test
    public void factoryMethod() {
        // FactoryMethod.exec();
        System.out.println("factory method run...");
        // 根据配置生成具体的工厂
        MyFactory factory = new TeacherFactory();
        Teacher teacher = (Teacher) factory.create();
        teacher.setName("John");
        teacher.sayHello();
        factory = new StudentFactory();
        final Student student = (Student) factory.create();
        student.setName("student Jordan");
        student.sayHello();
    }

    @Test
    public void absFactory() {
        // AbstractFactory.exec();
        System.out.println("abstract factory run...");
        ICompany company = new Alibaba(new StaffFactory(), new StuffFactory());
        for (int i = 0; i < 10; i++) {
            company.createStaff().duty();
            company.createStuff().usage();
        }

        company = new ByteDance(new StaffFactory(), new StuffFactory());
        for (int i = 0; i < 10; i++) {
            company.createStaff().duty();
            company.createStuff().usage();
        }
    }
}
