package com.binlee.design.decorator;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/16
 */
public class Girl extends Changer {

    public Girl(IMorrigan original) {
        super(original);
    }

    @Override
    public void change() {
        ((Morrigan) original).setImage("a girl");
    }
}
