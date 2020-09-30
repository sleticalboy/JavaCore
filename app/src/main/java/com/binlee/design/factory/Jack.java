package com.binlee.design.factory;

import com.binlee.annotation.Factory;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/9/30
 */
@Factory(name = "Jack", type = IPerson.class)
public final class Jack implements IPerson {

    @Override
    public String getName() {
        return "Jack";
    }

    @Override
    public void sayHello() {
        System.out.println("Hello, I'm " + getName());
    }
}
