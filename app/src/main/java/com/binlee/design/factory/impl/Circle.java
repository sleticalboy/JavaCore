package com.binlee.design.factory.impl;

import com.binlee.annotation.Factory;
import com.binlee.design.factory.IShape;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/10/4
 */
@Factory(type = IShape.class, name = "Circle")
public final class Circle implements IShape {

    @Override
    public String getShape() {
        return "Circle";
    }

    @Override
    public void draw() {
        System.out.println("draw() ->" + getShape());
    }
}
