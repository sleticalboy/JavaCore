package com.binlee.design.observer;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/18
 */
public final class Observers {

    public static void exec() {
        final Subject subject = new Subject();
        subject.addObserver(new Observer());
        subject.addObserver(new Observer());
        subject.notifyObservers();
        subject.setChanged();
        subject.notifyObservers("The changed data");
    }
}
