package com.binlee.design.mediator;

import com.binlee.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/19
 */
public final class DanKe implements IMedium {

    private final Logger logger;
    private final List<ICustomer> customers;

    public DanKe() {
        customers = new ArrayList<>();
        logger = Logger.get(getClass());
        logger.setDebuggable(false);
    }

    @Override
    public void register(ICustomer customer) {
        logger.i("register() customer: " + customer.getName());
        if (!customers.contains(customer)) {
            customer.setMedium(this);
            customers.add(customer);
        }
    }

    @Override
    public void dispatch(String from, String info) {
        logger.i("dispatch() from: " + from + ", info: " + info);
        for (final ICustomer customer : customers) {
            if (!from.equals(customer.getName())) {
                customer.receive(from, info);
            }
        }
    }
}
