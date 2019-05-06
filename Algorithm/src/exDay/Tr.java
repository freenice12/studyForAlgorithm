package jpa.example.demo.algo;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

public class Tr {
    static class NodeA {
        NodeA left;
        int value;
        NodeA right;

        public NodeA(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        NodeA root = new NodeA(1);
        root.left = new NodeA(2);
        root.right = new NodeA(3);
        root.left.left = new NodeA(4);
        root.left.right = new NodeA(5);
        root.right.left = new NodeA(6);
        root.right.right = new NodeA(7);

        dfsPre(root); // 1 2 4 5 3 6
        System.out.println();
        dfsIn(root); // 4 2 5 1 3 6
        System.out.println();
        dfsPost(root); // 4 5 2 6 3 1
        System.out.println();
        bfs(root); // 1 2 3 4 5 6
    }

    private static void dfsPre(NodeA root) {
        printNode(root);
        NodeA leftNode = root.left;
        if (null != leftNode) {
            dfsPre(leftNode);
        }
        NodeA rightNode = root.right;
        if (null != rightNode) {
            dfsPre(rightNode);
        }
    }

    private static void dfsIn(NodeA root) {
        NodeA leftNode = root.left;
        if (null != leftNode) {
            dfsIn(leftNode);
        }
        printNode(root);
        NodeA rightNode = root.right;
        if (null != rightNode) {
            dfsIn(rightNode);
        }
    }

    private static void dfsPost(NodeA root) {
        NodeA leftNode = root.left;
        if (null != leftNode) {
            dfsPost(leftNode);
        }
        NodeA rightNode = root.right;
        if (null != rightNode) {
            dfsPost(rightNode);
        }
        printNode(root);
    }

    private static void bfs(NodeA root) {
        Queue<NodeA> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            NodeA node = queue.poll();
            printNode(node);
            addNode(queue, node.left, node.right);
        }
    }

    private static void addNode(Queue<NodeA> queue, NodeA left, NodeA right) {
        if (null != left) queue.add(left);
        if (null != right) queue.add(right);
    }

    private static void printNode(NodeA node) {
        System.out.print(node.value + " ");
    }

}
