package com.binlee.design.decorator;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/16
 */
public class Morrigan implements IMorrigan {

    private String image = "morrigan self";

    public Morrigan() {
        L.i("《恶魔战士》中的莫利亚·安斯兰");
    }

    @Override
    public void display() {
        L.i("display() " + image);
    }

    public void setImage(String image) {
        this.image = image;
    }
}
