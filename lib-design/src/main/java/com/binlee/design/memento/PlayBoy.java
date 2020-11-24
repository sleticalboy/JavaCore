package com.binlee.design.memento;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/24
 */
public class PlayBoy {

    private String lover;

    public void setLover(String lover) {
        this.lover = lover;
    }

    public String getLover() {
        return lover;
    }

    public Girl createMemento() {
        return new Girl(lover);
    }

    public void restoreMemento(Girl girl) {
        setLover(girl.getName());
    }
}
