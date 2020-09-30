package com.binlee.processor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/9/30
 */
public final class FactoryItem {

    // parent interface
    final public String parent;
    // children implementation
    final public Set<Impl> children = new HashSet<>();

    public FactoryItem(String parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FactoryItem that = (FactoryItem) o;
        return Objects.equals(parent, that.parent) && Objects.equals(children, that.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, children);
    }

    @Override
    public String toString() {
        return "FactoryItem{" +
                "parent='" + parent + '\'' +
                ", children=" + children +
                '}';
    }

    public static Impl impl(String name, String clazz) {
        return new Impl(name, clazz);
    }

    public static final class Impl {

        // impl simple name
        public final String name;
        // impl full name
        public final String clazz;

        private Impl(String name, String clazz) {
            this.name = name;
            this.clazz = clazz;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Impl impl = (Impl) o;
            return Objects.equals(name, impl.name) &&
                    Objects.equals(clazz, impl.clazz);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, clazz);
        }

        @Override
        public String toString() {
            return "Impl{" +
                    "name='" + name + '\'' +
                    ", clazz='" + clazz + '\'' +
                    '}';
        }
    }
}
