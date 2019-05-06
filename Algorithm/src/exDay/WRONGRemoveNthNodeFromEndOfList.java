package jpa.example.demo.algo;

import java.util.*;

/**
 * Given a linked list, remove the n-th node from the end of list and return its head.
 *
 * Example:
 *
 * Given linked list: 1->2->3->4->5, and n = 2.
 *
 * After removing the second node from the end, the linked list becomes 1->2->3->5.
 * Note:
 *
 * Given n will always be valid.
 *
 * Follow up:
 *
 * Could you do this in one pass?
 */
public class WRONGRemoveNthNodeFromEndOfList {

    static class Solution {
        public ListNode removeNthFromEnd(ListNode head, int n) {
            ListNode dummy = new ListNode(0);
            dummy.next = head;
            ListNode first = dummy;
            ListNode second = dummy;
            for (int i = 1; i <= n + 1; i++) {
                first = first.next;
            }
            while (first != null) {
                first = first.next;
                second = second.next;
            }
            second.next = second.next.next;
            return dummy.next;
        }

        private void removeNode(ListNode node, ListNode pre, int n) {
            if (node.next == null) {
                pre.next = node;
            }


        }
//        public ListNode removeNthFromEnd(ListNode head, int n) {
//            Queue<ListNode> subList = new LinkedList<>();
//            ListNode start = head;
//            while (start != null) {
//                if (subList.size() < n + 1) {
//                    // 넣고
//                    subList.add(start);
//                } else {
//                    // 빼고 넣고
//                    subList.poll();
//                    subList.add(start);
//                }
//                start = start.next;
//            }
//
//            ListNode target = subList.poll();
//            ListNode removed = subList.poll();
//            if (removed == null) return null;
//            ListNode next = subList.poll();
//            target.next = next;
//            return head;
//        }
//    }
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
//        head.next = new ListNode(2);
//        head.next.next = new ListNode(3);
//        head.next.next.next = new ListNode(4);
//        head.next.next.next.next = new ListNode(5);
        Solution solution = new Solution();
//        solution.removeNthFromEnd(head, 2);
        solution.removeNthFromEnd(head, 1);
        ListNode start = head;
        while (start != null) {
            System.out.print(start.val + " ");
            start = start.next;
        }
        System.out.println();
    }

    static class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
  }

}
