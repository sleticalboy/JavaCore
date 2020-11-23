package com.binlee.design.visitor;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/23
 */
public final class Cuprum implements IMaterial {

    @Override
    public String accept(IVisitor visitor) {
        return visitor.create(this);
    }
}
