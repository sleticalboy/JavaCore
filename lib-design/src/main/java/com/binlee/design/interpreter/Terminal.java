package com.binlee.design.interpreter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/25
 */
public class Terminal implements IExpression {

    private final Set<String> data = new HashSet<>();

    public Terminal(String[] data) {
        Collections.addAll(this.data, data);
    }

    @Override
    public boolean interpret(String info) {
        return data.contains(info);
    }
}
