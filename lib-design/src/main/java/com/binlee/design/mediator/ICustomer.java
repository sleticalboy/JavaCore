package com.binlee.design.mediator;

import com.binlee.util.Logger;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/19
 */
public interface ICustomer {

    String getName();

    void setMedium(IMedium medium);

    void send(String info);

    void receive(String from, String info);
}
