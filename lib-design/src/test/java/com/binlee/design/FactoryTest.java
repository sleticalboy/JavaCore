package com.binlee.design;

import com.binlee.design.factory.AbstractFactory;
import com.binlee.design.factory.FactoryMethod;
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
        SimpleFactory.exec();
    }

    @Test
    public void factoryMethod() {
        FactoryMethod.exec();
    }

    @Test
    public void absFactory() {
        AbstractFactory.exec();
    }
}
