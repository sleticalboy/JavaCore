package com.binlee.design.state;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/21
 */
public class LowState extends AbstractState {

    public LowState(AbstractState state) {
        this(state.context);
        score = state.score;
    }

    public LowState(Context context) {
        super(context, "low state");
        score = 0;
    }

    @Override
    protected void checkState() {
        if (score >= 90) {
            context.setState(new HighState(this));
        } else if (score >= 60) {
            context.setState(new MiddleState(this));
        }
    }
}
