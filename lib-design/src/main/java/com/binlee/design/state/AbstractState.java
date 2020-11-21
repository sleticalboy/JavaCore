package com.binlee.design.state;

import com.binlee.util.Logger;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/21
 */
public abstract class AbstractState {

    protected final Context context;
    protected final String name;
    protected final Logger logger;
    protected int score;

    public AbstractState(Context context, String name) {
        this.context = context;
        this.name = name;
        this.logger = Logger.get(getClass());
    }

    public void addScore(int score) {
        this.score += score;
        logger.i("addScore() before checking: " + context.getState().name
                + ", score: " + this.score + ", x: " + score);
        checkState();
        logger.i("addScore() after checking: " + context.getState().name);
    }

    protected abstract void checkState();
}
