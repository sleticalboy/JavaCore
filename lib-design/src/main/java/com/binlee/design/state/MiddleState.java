package com.binlee.design.state;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/21
 */
public class MiddleState extends AbstractState {

    public MiddleState(AbstractState state) {
        super(state.context, "middle state");
        score = state.score;
    }

    @Override
    protected void checkState() {
        if (score >= 90) {
            context.setState(new HighState(this));
        } else if (score < 60) {
            context.setState(new LowState(this));
        }
    }
}
