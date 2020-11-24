package com.binlee.design.memento;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/24
 */
public final class GirlStack {

    private final Girl[] girls;
    private int top = -1;

    public GirlStack() {
        girls = new Girl[5];
    }

    public boolean push(Girl girl) {
        if (top >= 4) {
            return false;
        }
        girls[++top] = girl;
        return true;
    }

    public Girl pop() {
        if (top <= 0) {
            return girls[0];
        }
        return girls[top--];
    }
}
