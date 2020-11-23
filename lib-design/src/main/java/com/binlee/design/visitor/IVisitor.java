package com.binlee.design.visitor;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/23
 */
public interface IVisitor {

    String create(Paper paper);

    String create(Cuprum cuprum);
}
