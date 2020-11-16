package com.binlee.design.composite;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/16
 */
public class Screen extends AbstractGood {
    public Screen(String name) {
        super(name);
    }

    @Override
    public float calculate() {
        return 1499;
    }
}
