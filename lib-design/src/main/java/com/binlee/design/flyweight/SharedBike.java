package com.binlee.design.flyweight;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/16
 */
public final class SharedBike {

    private static final int MAX_SIZE = 10;
    private static final SharedBike[] POOL = new SharedBike[MAX_SIZE];
    private boolean inUse = false;

    public String qrCode;

    private SharedBike() {
    }

    public static SharedBike obtain() {
        synchronized (POOL) {
            for (int i = MAX_SIZE - 1; i >= 0; i--) {
                final SharedBike bike = POOL[i];
                if (bike != null && !bike.inUse) {
                    bike.inUse = true;
                    POOL[i] = null;
                    return bike;
                }
            }
            return new SharedBike();
        }
    }

    public void recycle() {
        synchronized (POOL) {
            for (int i = 0; i < MAX_SIZE; i++) {
                final SharedBike bike = POOL[i];
                if (bike == null) {
                    inUse = false;
                    qrCode = null;
                    POOL[i] = this;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "SharedBike{@" + hashCode() +
                ", inUse=" + inUse +
                ", qrCode='" + qrCode + '\'' +
                '}';
    }
}
