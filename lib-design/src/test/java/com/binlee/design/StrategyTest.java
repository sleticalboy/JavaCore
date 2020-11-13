package com.binlee.design;

import com.binlee.design.strategy.Strategies;
import org.junit.Test;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/13
 */
public final class StrategyTest {

    @Test
    public void strategy() {
        final boolean test = Strategies.sTest;
        Strategies.sTest = true;
        Strategies.exec();
        Strategies.sTest = test;
    }
}
