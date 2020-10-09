package com.binlee.design.singleton;

import com.binlee.annotation.Singleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/10/9
 */
@Singleton
public final class ServiceManager {

    private final Map<String, Class<?>> mServices = new ConcurrentHashMap<>();

    public void addService(String key, Class<?> service) {
        synchronized (mServices) {
            mServices.putIfAbsent(key, service);
        }
    }

    public Class<?> getService(String key) {
        synchronized (mServices) {
            return mServices.get(key);
        }
    }
}
