package com.binlee.design;

import com.binlee.annotation.Factory;

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
