package com.binlee.design.composite;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/16
 */
public abstract class AbstractGood implements IComponent {

    private final String name;

    public AbstractGood(String name) {
        this.name = name;
    }
}
