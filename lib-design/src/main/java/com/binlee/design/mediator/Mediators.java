package com.binlee.design.mediator;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/19
 */
public final class Mediators {

    public static void exec() {
        final DanKe danKe = new DanKe();

        Seller[] sellers = new Seller[10];
        Buyer[] buyers = new Buyer[4];
        for (int i = 0; i < sellers.length; i++) {
            sellers[i] = new Seller("Seller#" + i);
            danKe.register(sellers[i]);
            if (i % 3 == 0) {
                buyers[i / 3] = new Buyer("Buyer#" + (i / 3));
                danKe.register(buyers[i / 3]);
            }
        }
        for (final Seller seller : sellers) {
            seller.send("I have a nice apartment to seller.");
        }
        for (final Buyer buyer : buyers) {
            buyer.send("I want a big, huge, wonderful house.");
        }
    }
}
