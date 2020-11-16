package com.binlee.design.composite;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/16
 */
public class Keyboard extends AbstractGood {

    public Keyboard(String name) {
        super(name);
    }

    @Override
    public float calculate() {
        return 459;
    }
}
