package com.auto.algorithm.leetcode;

/**
 * Description:
 *
 * @author: fang.sheng
 * Version: 1.0
 * Date: 2019-09-08
 * @since JDK 1.8
 */
public class TwoAdd {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int carry = 0;
        ListNode head = new ListNode(0);
        ListNode node = head;
        while (null != l1 || null != l2) {
            int sum = (null == l1 ? 0 : l1.val) + (null == l2 ? 0 : l2.val) + carry;
            carry = sum / 10;
            node.next = new ListNode(sum % 10);
            node = node.next;
            l1 = null != l1 ? l1.next : null;
            l2 = null != l2 ? l2.next : null;
        }
        if (carry > 0) {
            node.next = new ListNode(carry);
            node = node.next;
        }
        return head.next;
    }


    public static void main(String[] args) {
        /*
        输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
        输出：7 -> 0 -> 8
        原因：342 + 465 = 807
        */
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(8);
        node1.next = node2;

        ListNode node11 = new ListNode(0);

        ListNode next = addTwoNumbers(node1, node11);
        while (null != next) {
            System.out.println(next.val);
            next = next.next;
        }
    }


}
    
    