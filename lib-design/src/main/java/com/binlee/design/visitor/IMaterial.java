package com.binlee.design.visitor;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/23
 */
public interface IMaterial {

    String accept(IVisitor visitor);
}
