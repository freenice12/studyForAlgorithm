package jpa.example.demo.algo;

import java.util.Objects;

/**
 * You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.
 * <p>
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 * <p>
 * Example:
 * <p>
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * Explanation: 342 + 465 = 807.
 *
 * solution:
 * public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
 *     ListNode dummyHead = new ListNode(0);
 *     ListNode p = l1, q = l2, curr = dummyHead;
 *     int carry = 0;
 *     while (p != null || q != null) {
 *         int x = (p != null) ? p.val : 0;
 *         int y = (q != null) ? q.val : 0;
 *         int sum = carry + x + y;
 *         carry = sum / 10;
 *         curr.next = new ListNode(sum % 10);
 *         curr = curr.next;
 *         if (p != null) p = p.next;
 *         if (q != null) q = q.next;
 *     }
 *     if (carry > 0) {
 *         curr.next = new ListNode(carry);
 *     }
 *     return dummyHead.next;
 * }
 *
 */
public class WRONGAddTwoNumbers {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    private static boolean hasCarry;

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int length = getMaxLength(l1, l2, 0);
        ListNode node = new ListNode(0);
        createAcc(node, length);
        addTwoNumber(l1, l2, false, node);
        if (hasCarry) {
            ListNode acc = node;
            while (acc != null) {
                ListNode pre = acc;
                acc = acc.next;
                if (acc == null) {
                    pre.next = new ListNode(1);
                }
            }
        }
        return node;
    }

    private static void createAcc(ListNode node, int length) {
        if (length < 2) {
            return;
        }
        node.next = newNode(0);
        createAcc(node.next, length - 1);
    }

    private static int getMaxLength(ListNode l1, ListNode l2, int depth) {
        if (l1 == null && l2 == null) {
            return depth;
        } else if (l1 == null) {
            return getMaxLength(null, l2.next, depth + 1);
        } else if (l2 == null) {
            return getMaxLength(l1.next, null, depth + 1);
        } else {
            return getMaxLength(l1.next, l2.next, depth + 1);
        }
    }

    private static ListNode addTwoNumber(ListNode l1, ListNode l2, boolean isCarry, ListNode acc) {
        if (acc == null) {
            hasCarry = isCarry;
            return acc;
        }
        acc.val = getVal(l1) + getVal(l2);
        if (isCarry) {
            acc.val += 1;
        }
        boolean b = acc.val > 9;
        if (b) {
            acc.val = acc.val % 10;
        }
        ListNode lNext = l1 == null ? null : l1.next;
        ListNode rNext = l2 == null ? null : l2.next;

        return addTwoNumber(lNext, rNext, b, acc.next);
    }

    private static int getVal(ListNode node) {
        if (Objects.nonNull(node))
            return node.val;
        return 0;
    }

    private static void printNode(ListNode result) {
        if (result == null) {
            System.out.println();
            return;
        }

        System.out.print(result.val + " ");
        printNode(result.next);
    }

    private static ListNode add(ListNode node, ListNode newNode) {
        node.next = newNode;
        return newNode;
    }

    private static ListNode newNode(int x) {
        return new ListNode(x);
    }

    public static void main(String[] args) {
        ListNode root1 = new ListNode(2);
        ListNode root11 = add(root1, newNode(4));
        ListNode root111 = add(root11, newNode(3));

        ListNode root2 = new ListNode(5);
        ListNode root22 = add(root2, newNode(6));
        ListNode root222 = add(root22, newNode(4));

        ListNode result1 = addTwoNumbers(root1, root2);
        ListNode result11 = addTwoNumbersNew(root1, root2);
        printNode(result1);
        printNode(result11);


        ListNode root3 = new ListNode(1);
        ListNode root4 = new ListNode(9);
        ListNode root44 = add(root4, newNode(9));

        ListNode result2 = addTwoNumbers(root3, root4);
        ListNode result22 = addTwoNumbersNew(root3, root4);
        printNode(result2);
        printNode(result22);
    }

    private static ListNode addTwoNumbersNew(ListNode l1, ListNode l2) {
        // find longer node to use accumulate
        ListNode acc = l1;
        if (isLeftNodeDepper(l1, l2)) {
            acc = l1;
        } else {
            acc = l2;
        }
        // acc will save values sum from the other node
        boolean needCarry = sumTo(acc, l1, l2, false);
        //   when need to carry(true / false)
        // return carry
        if (needCarry) {
            ListNode pre = acc;
            ListNode next = acc.next;
            while (next != null) {
                pre = next;
                next = next.next;
            }
            pre.next = new ListNode(1);
        }
        return acc;
    }

    private static boolean isLeftNodeDepper(ListNode root1, ListNode root2) {
        if (root1 != null && root2 != null) {
            return isLeftNodeDepper(root1.next, root2.next);
        }
        return root1 != null;
    }

    private static boolean sumTo(ListNode acc, ListNode root1, ListNode root2, boolean hasCarry) {
        if (acc == null) return hasCarry;
        ListNode left = null, right = null;

        if (root1 != null && root2 != null) {
            acc.val = root1.val + root2.val;
            left = root1.next;
            right = root2.next;
        } else if (root1 != null) {
            acc.val = root1.val;
            left = root1.next;
        } else {
            acc.val = root2.val;
            right = root2.next;
        }

        if (hasCarry) {
            acc.val += 1;
        }

        boolean isCarried = acc.val > 9;
        if (isCarried) {
            acc.val = acc.val % 10;
        }

        return sumTo(acc.next, left, right, isCarried);
    }


}
