package com.binlee.design;

import com.binlee.design.proxy.Agent;
import com.binlee.design.proxy.IStar;
import org.junit.Test;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/15
 */
public final class ProxyTest {

    @Test
    public void proxyTest() {
        IStar agent = new Agent();
        agent.perform();
        agent.act();
    }
}
