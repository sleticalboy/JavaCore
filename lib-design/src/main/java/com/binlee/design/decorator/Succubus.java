package com.binlee.design.decorator;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/16
 */
public class Succubus extends Changer {

    public Succubus(IMorrigan original) {
        super(original);
    }

    public void change() {
        ((Morrigan) original).setImage("a succubus");
    }
}
