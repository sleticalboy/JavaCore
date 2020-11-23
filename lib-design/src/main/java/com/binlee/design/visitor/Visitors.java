package com.binlee.design.visitor;

import com.binlee.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/23
 */
public final class Visitors {

    private final Logger logger;
    private final List<IMaterial> materials = new ArrayList<>();

    private Visitors() {
        logger = Logger.get(getClass());
    }

    private void add(IMaterial material) {
        materials.add(material);
    }

    private String accept(IVisitor visitor) {
        StringBuilder buffer = new StringBuilder();
        for (final IMaterial material : materials) {
            buffer.append(material.accept(visitor)).append(", ");
        }
        return buffer.toString();
    }

    public static void exec() {
        final Visitors visitors = new Visitors();
        visitors.add(new Paper());
        visitors.add(new Cuprum());
        visitors.logger.i("mint product: " + visitors.accept(new MintCompany()));
        visitors.logger.i("art product: " + visitors.accept(new ArtCompany()));
    }
}
