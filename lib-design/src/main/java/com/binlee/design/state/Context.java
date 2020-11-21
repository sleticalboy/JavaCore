package com.binlee.design.state;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/21
 */
public final class Context {

    private AbstractState state;

    public Context() {
        state = new LowState(this);
    }

    public void setState(AbstractState state) {
        this.state = state;
    }

    public AbstractState getState() {
        return state;
    }

    public void addScore(int score) {
        state.addScore(score);
    }
}
