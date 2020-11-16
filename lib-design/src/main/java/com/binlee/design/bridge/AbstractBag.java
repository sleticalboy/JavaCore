package com.binlee.design.bridge;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/16
 */
public abstract class AbstractBag {

    private IColor color;

    public void setColor(IColor color) {
        this.color = color;
    }

    public IColor getColor() {
        return color;
    }

    public abstract String getName();
}
