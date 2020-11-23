package com.binlee.design.visitor;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/23
 */
public final class MintCompany implements IVisitor {

    @Override
    public String create(Paper paper) {
        return "paper coin";
    }

    @Override
    public String create(Cuprum cuprum) {
        return "cuprum coin";
    }
}
