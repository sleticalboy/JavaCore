package com.binlee.design.mediator;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/19
 */
public interface IMedium {

    void register(ICustomer customer);

    void dispatch(String from, String info);

}
