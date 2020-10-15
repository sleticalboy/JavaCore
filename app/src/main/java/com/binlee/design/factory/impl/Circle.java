package com.binlee.design.factory.impl;

import com.binlee.annotation.Factory;
import com.binlee.design.factory.IShape;

import java.io.IOException;
import java.util.Map;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/10/4
 */
@Factory(type = IShape.class, name = "Circle")
public final class Circle implements IShape {

    public Circle() {
        //
    }

    public Circle(int size) {
        //
    }

    @Override
    public String getShape() {
        return "Circle";
    }

    @Override
    public void draw() {
        System.out.println("draw() ->" + getShape());
    }

    public Object foo(String foo, Map<String, String> owners, Object... args) throws IOException {
        return null;
    }
}
