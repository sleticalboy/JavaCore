package com.binlee.algorithm.array;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2021/7/7
 */
public class Lists {

    private Lists() {
        //no instance
    }

    // 判断链表是否有环
    public static <T> boolean hasCycle(ListNode<T> head) {
        if (head == null || head.next == null) return false;
        ListNode<T> slow = head, fast = head;
        // 快慢指针
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }

    // 反转链表
    public static <T> ListNode<T> reverse(ListNode<T> head) {
        if (head == null || head.next == null) return head;
        ListNode<T> newHead = null, removed;
        do {
            // 摘掉头
            removed = head;
            // 重置头
            head = removed.next;

            // 链接
            removed.next = newHead;
            // 重置新头
            newHead = removed;
        } while (head != null);

        return newHead;
    }
}
