package com.binlee.design;

import com.binlee.design.adapter.Adapter;
import com.binlee.design.bridge.Bridges;
import com.binlee.design.builder.Builders;
import com.binlee.design.chain.Chains;
import com.binlee.design.command.Commands;
import com.binlee.design.composite.Composites;
import com.binlee.design.decorator.Decorators;
import com.binlee.design.facade.Facades;
import com.binlee.design.factory.AbstractFactory;
import com.binlee.design.factory.FactoryMethod;
import com.binlee.design.factory.SimpleFactory;
import com.binlee.design.flyweight.Flyweights;
import com.binlee.design.iterator.Iterators;
import com.binlee.design.mediator.Mediators;
import com.binlee.design.observer.Observers;
import com.binlee.design.prototype.Prototypes;
import com.binlee.design.proxy.Agent;
import com.binlee.design.proxy.IStar;
import com.binlee.design.singleton.Singles;
import com.binlee.design.state.States;
import com.binlee.design.strategy.Strategies;
import com.binlee.design.template.Templates;
import org.junit.Test;

import java.util.Observable;
import java.util.Observer;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/16
 */
public final class DesignPatternTest {

    // 测试时需要在 build.gradle 文件的 dependency 节点下添加 implementation(project(":lib-util"))

    @Test
    public void iterator() {
        Iterators.exec();
    }

    @Test
    public void state() {
        States.exec();
    }

    @Test
    public void chain() {
        Chains.exec();
    }

    @Test
    public void mediator() {
        Mediators.exec();
    }

    @Test
    public void observer() {
        Observers.exec();
    }

    @Test
    public void command() {
        Commands.exec();
    }

    @Test
    public void template() {
        Templates.exec();
    }

    @Test
    public void composite() {
        Composites.exec();
    }

    @Test
    public void flyweight() {
        Flyweights.exec();
    }

    @Test
    public void facade() {
        Facades.exec();
    }

    @Test
    public void decorator() {
        Decorators.exec();
    }

    @Test
    public void bridge() {
        Bridges.exec();
    }

    @Test
    public void adapter() {
        Adapter.exec();
    }

    @Test
    public void builder() {
        Builders.exec();
    }

    @Test
    public void proxy() {
        IStar agent = new Agent();
        agent.perform();
        agent.act();
    }

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

    @Test
    public void prototype() {
        Prototypes.exec();
    }

    @Test
    public void strategy() {
        final boolean test = Strategies.sTest;
        Strategies.sTest = true;
        Strategies.exec();
        Strategies.sTest = test;
    }

    @Test
    public void singleton() {
        Singles.exec();
    }
}
