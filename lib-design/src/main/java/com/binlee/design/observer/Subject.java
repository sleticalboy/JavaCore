package com.binlee.design.observer;

import java.util.Observable;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/18
 */
public final class Subject extends Observable {

    public Subject() {
        setChanged();
    }

    @Override
    public synchronized void setChanged() {
        super.setChanged();
    }
}
