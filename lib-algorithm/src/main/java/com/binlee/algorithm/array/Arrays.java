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
        if (original == null || original.length <= 1) return 0;
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
        if (arr == null || arr.length == 0) return -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == val) return i;
        }
        return -1;
    }

    public static void sort(int[] original) {
        // java.util.Arrays.sort(original);
        if (original == null || original.length <= 1) return;
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

    // int[] arr = {1, 2, 3, 4, 5, 7, 9, 10}
    public static int binSearch(int[] arr, int target) {
        if (arr == null || arr.length == 0) return -1;
        int left = 0; // 左边界
        int right = arr.length - 1; // 右边界
        int index; // 中间
        while (left <= right) {
            index = (left + right) / 2;
            if (target > arr[index]) {
                // -> right
                left = index + 1;
            } else if (target < arr[index]) {
                // <- left
                right = index - 1;
            } else {
                // hit
                return index;
            }
        }
        return -1;
    }

    public static class SimpleBinarySearchTest {

        public static void main(String[] args) {
            int[] array = new int[]{1, 2, 3, 4, 5, 6};
            test(binarySearch(array, 3), 2);
            test(binarySearch(array, 2), 1);
            test(binarySearch(array, 1), 0);
            test(binarySearch(array, 4), 3);
            test(binarySearch(array, 5), 4);
            test(binarySearch(array, 6), 5);
            test(binarySearch(array, -1), -1);
            test(binarySearch(array, 7), -1);
            array = new int[]{1, 2, 3, 4, 5};
            test(binarySearch(array, 3), 2);
            test(binarySearch(array, 2), 1);
            test(binarySearch(array, 1), 0);
            test(binarySearch(array, 4), 3);
            test(binarySearch(array, 5), 4);
            test(binarySearch(array, -1), -1);
            test(binarySearch(array, 7), -1);
            array = new int[]{1, 3, 5, 7, 9};
            test(binarySearch(array, 0), -1);
            test(binarySearch(array, 1), 0);
            test(binarySearch(array, 2), -1);
            test(binarySearch(array, 3), 1);
            test(binarySearch(array, 4), -1);
            test(binarySearch(array, 5), 2);
            test(binarySearch(array, 6), -1);
            test(binarySearch(array, 7), 3);
            test(binarySearch(array, 8), -1);
            test(binarySearch(array, 9), 4);
            test(binarySearch(array, 10), -1);
            array = new int[]{1};
            test(binarySearch(array, 1), 0);
            test(binarySearch(array, 2), -1);
            test(binarySearch(array, -1), -1);
            array = new int[]{};
            test(binarySearch(array, 2), -1);
            test(binarySearch(array, -1), -1);
        }

        public static int binarySearch(int[] array, int targetValue) {
            // 1. 数字存储
            // 2. 设置左边界的leftIndex和右边界的rightIndex
            // 3. 计算中间的middleIndex
            // 4. 目标值 targetValue
            // 5. 如果leftIndex == middleIndex, 或者rightIndex == middleIndex,
            // 表示middleIndex已经处于是两个数字中的一个, 没有其他值了, 如果相等, 表示存在,
            // 如果不等, 如果不存在, 返回-1
            // 6. 如果array[middleIndex]的值, 大于targetValue, 表示目标值在左边, 所以 rightIndex = middleIndex
            // 7. 如果array[middleIndex]的值, 小于targetValue, 表示目标值在右边, 所以leftIndex = middleIndex
            // 8. else, array[middleIndex] 等于 targetValue, 表示目标值为当前值, 所以middleIndex 为
            // 最终结果, 返回即可
            if (array == null || array.length == 0) return -1;
            int leftIndex = 0;
            int rightIndex = array.length - 1;
            while (true) {
                int middleIndex = (leftIndex + rightIndex) / 2;
                //处理特殊case, 已经剩下了两个数, 或者一个数时的case
                if (middleIndex == leftIndex || middleIndex == rightIndex) {
                    if (array[leftIndex] == targetValue) {
                        return leftIndex;
                    } else if (array[rightIndex] == targetValue) {
                        return rightIndex;
                    }
                    return -1;
                }
                if (array[middleIndex] > targetValue) {
                    //在左边
                    rightIndex = middleIndex;
                } else if (array[middleIndex] == targetValue) {
                    //已经找到了
                    return middleIndex;
                } else {
                    // 在右边
                    leftIndex = middleIndex;
                }
            }
        }

        private static void test(int currentValue, int targetValue) {
            if (currentValue != targetValue) {
                throw new RuntimeException("currentValue:" + currentValue + ", targetValue:" + targetValue);
            }
        }
    }
}
