package com.binlee.design.iterator;

import java.util.Iterator;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/23
 */
public final class Iterators {

    public static void exec() {
        final IntArray array = IntArray.of(3, 5, 6, 7, 8);
        final Iterator<Integer> it = array.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    static final class IntArray {

        int[] data;

        private IntArray(int capacity) {
            data = new int[capacity];
        }

        public static IntArray of(int... args) {
            if (args == null || args.length == 0) {
                return new IntArray(0);
            }
            final IntArray array = new IntArray(args.length);
            for (int i = 0; i < args.length; i++) {
                array.set(i, args[i]);
            }
            return array;
        }

        private void set(int index, int element) {
            data[index] = element;
        }

        Iterator<Integer> iterator() {
            return new IntIterator();
        }

        class IntIterator implements Iterator<Integer> {

            int index;

            @Override
            public boolean hasNext() {
                return index < data.length;
            }

            @Override
            public Integer next() {
                final int ret = data[index];
                index++;
                return ret;
            }
        }
    }
}
