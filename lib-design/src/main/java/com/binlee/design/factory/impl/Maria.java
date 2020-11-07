package com.binlee.design.factory.impl;

import com.binlee.annotation.Factory;
import com.binlee.design.factory.interfaces.IPerson;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/9/30
 */
@Factory(name = "Maria", type = IPerson.class)
public final class Maria implements IPerson {

    @Override
    public String getName() {
        return "Maria";
    }

    @Override
    public void sayHello() {
        System.out.println("Hello, I'm " + getName());
    }
}
