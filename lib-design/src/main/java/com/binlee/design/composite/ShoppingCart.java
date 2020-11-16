package com.binlee.design.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/16
 */
public final class ShoppingCart implements IComponent {

    private final List<AbstractGood> goods = new ArrayList<>();

    public void addGood(AbstractGood good) {
        goods.add(good);
    }

    public void removeGood(AbstractGood good) {
        goods.remove(good);
    }

    public List<AbstractGood> getGoods() {
        return goods;
    }

    @Override
    public float calculate() {
        float sum = 0.0F;
        for (AbstractGood good : goods) {
            sum += good.calculate();
        }
        return sum;
    }
}
