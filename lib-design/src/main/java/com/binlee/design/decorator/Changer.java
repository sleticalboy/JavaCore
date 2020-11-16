package com.binlee.design.decorator;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/16
 */
public abstract class Changer implements IMorrigan {

    protected final IMorrigan original;

    public Changer(IMorrigan original) {
        this.original = original;
    }

    @Override
    public final void display() {
        change();
        original.display();
    }

    public abstract void change();
}
