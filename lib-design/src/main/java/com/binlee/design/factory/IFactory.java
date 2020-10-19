package com.binlee.design.factory;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/10/13
 */
public interface IFactory<F> {

    /**
     * 简单工厂
     *
     * @param id 产品标识
     * @return id 对应的产品
     */
    F create(String id);
}
