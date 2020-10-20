package com.binlee.algorithm.array;

import java.util.*;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/10/20
 */
public final class Arrays {

    private Arrays() {
        //no instance
    }

    /**
     * 移除数组中的重复元素
     *
     * @param original 原数组
     * @return 未重复元素的个数，不需要考虑原数组中剩余位置的元素
     */
    public static int removeDuplicated(int[] original) {
        if (original == null || original.length <= 1) {
            return 0;
        }
        // 方式1:
        final int[] arr = new int[original.length];
        arr[0] = original[0];
        int j = 0;
        for (int i = 1; i < original.length; i++) {
            if (indexOf(arr, original[i]) < 0) {
                arr[++j] = original[i];
            }
        }
        if (j != 0) {
            System.arraycopy(arr, 0, original, 0, arr.length);
            return j + 1;
        }
        return 0;
    }

    public static int indexOf(int[] arr, int val) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == val) {
                return i;
            }
        }
        return -1;
    }

    public static boolean sort(int[] original) {
        // java.util.Arrays.sort(original);
        if (original == null || original.length <= 1) {
            return false;
        }
        int temp;
        // 0, 0, 2, 3, 2, 9, 5, 9, 4, 0
        for (int i = 0; i < original.length - 1; i++) {
            for (int j = i + 1; j < original.length; j++) {
                if (original[i] >= original[j]) {
                    temp = original[i];
                    original[i] = original[j];
                    original[j] = temp;
                }
            }
        }
        return true;
    }

    public static void foo(int[] original) {
        // 方式2:
        int k = 0;
        final Map<Integer, Integer> map = new LinkedHashMap<>();
        for (int element : original) {
            if (!map.containsValue(element)) {
                k++;
                map.put(k, element);
            }
        }
        System.out.println("way 2 -> size: " + map.size() + " " + map.values());
        // 方式3:
        final List<Integer> list = new ArrayList<>(original.length);
        for (int element : original) {
            if (!list.contains(element)) {
                list.add(element);
            }
        }
        System.out.println("way 3 -> size: " + list.size() + " " + list);
        // 方式4:
        final Set<Integer> set = new LinkedHashSet<>();
        for (int element : original) {
            set.add(element);
        }
        System.out.println("way 4 -> size: " + set.size() + " " + set);
    }
}
