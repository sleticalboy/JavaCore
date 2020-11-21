package com.binlee.design.state;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/21
 */
public class HighState extends AbstractState {

    public HighState(AbstractState state) {
        super(state.context, "high state");
        score = state.score;
    }

    @Override
    protected void checkState() {
        if (score < 60) {
            context.setState(new LowState(this));
        } else if (score < 90) {
            context.setState(new MiddleState(this));
        }
    }
}
