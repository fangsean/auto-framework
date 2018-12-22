package com.auto.algorithm.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @Description: <p>二叉树</p>
 */
public class BinaryTreeNode<T> {

    /**
     * 根
     */
    private TreeNode<T> root;

    public TreeNode<T> getRoot() {
        return root;
    }

    public BinaryTreeNode() {
    }

    /**
     * 新增
     *
     * @param value
     */
    public void insertTreeNode(T value) {

        TreeNode<T> tTreeNode = new TreeNode<>(value);
        if (this.root == null) {
            this.root = tTreeNode;
        } else {
            TreeNode<T> currentNode = this.root;
            TreeNode<T> parentNode;
            while (true) {
                parentNode = currentNode;
                if (tTreeNode.compareTo(currentNode.getValue()) == 1) {
                    currentNode = currentNode.rightChild;
                    if (currentNode == null) {
                        parentNode.rightChild = tTreeNode;
                        return;
                    }
                } else {
                    currentNode = currentNode.leftChild;
                    if (currentNode == null) {
                        parentNode.leftChild = tTreeNode;
                        return;
                    }
                }
            }
        }
    }


    /**
     * 查询遍历
     *
     * @param key
     * @return
     */
    public TreeNode findTreeNode(T key) {

        TreeNode currentNode = this.root;

        while (null != currentNode && currentNode.getValue() != key) {
            if (currentNode.compareTo(key) == 1) {
                currentNode = currentNode.leftChild;
            } else {
                currentNode = currentNode.rightChild;
            }
        }
        return currentNode;
    }


    /**
     * 前序遍历
     *
     * @param node
     */
    private void preorderTreeNode(TreeNode<T> node) {

        if (null == node) {
            return;
        }
        System.out.print(node.value + " ");
        preorderTreeNode(node.leftChild);
        preorderTreeNode(node.rightChild);

    }

    /**
     * 前序遍历
     * 非递归
     */
    public void preorderTreeNodeFalse(TreeNode<T> node) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        while (node != null || !stack.empty()) {
            while (node != null) {
                System.out.print(node.value + " ");
                stack.push(node);
                node = node.leftChild;
            }
            if (!stack.empty()) {
                node = stack.pop();
                node = node.rightChild;
            }
        }
    }


    /**
     * 中序遍历
     *
     * @param node
     */
    public void inorderTreeNode(TreeNode<T> node) {
        if (null == node) {
            return;
        }

        inorderTreeNode(node.leftChild);
        System.out.print(node.value + " ");
        inorderTreeNode(node.rightChild);
    }

    /**
     * 中序遍历
     * 非递归
     */
    public void inorderTreeNodeFalse(TreeNode<T> node) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        while (node != null || !stack.empty()) {
            while (node != null) {
                stack.push(node);
                node = node.leftChild;
            }
            if (!stack.empty()) {
                node = stack.pop();
                System.out.print(node.value + " ");
                node = node.rightChild;
            }
        }
    }


    /**
     * 后序遍历
     *
     * @param node
     */
    public void afterorderTreeNode(TreeNode<T> node) {
        if (null == node) {
            return;
        }
        afterorderTreeNode(node.leftChild);
        afterorderTreeNode(node.rightChild);
        System.out.print(node.value + " ");

    }

    /**
     * 后序遍历
     * 非递归
     */
    public void afterorderTreeNodeFalse(TreeNode<T> node) {
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        int i = 1;
        while (node != null || !stack1.empty()) {
            while (node != null) {
                stack1.push(node);
                stack2.push(0);
                node = node.leftChild;
            }

            while (!stack1.empty() && stack2.peek() == i) {
                stack2.pop();
                System.out.print(stack1.pop().value + " ");
            }

            if (!stack1.empty()) {
                stack2.pop();
                stack2.push(1);
                node = stack1.peek();
                node = node.rightChild;
            }
        }
    }


    /**
     * 层序遍历
     *
     * @param node
     */
    public void levelOrderTreeNode(TreeNode<T> node) {
        if (node == null) {
            return;
        }

        int depth = depth(node);

        for (int i = 1; i <= depth; i++) {
            levelOrder(node, i);
        }
    }

    /**
     * 层序遍历
     * 非递归
     */
    public void levelOrderTreeNodeFalse(TreeNode<T> node) {
        if (node == null) {
            return;
        }

        TreeNode<T> binaryNode;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(node);

        while (queue.size() != 0) {
            binaryNode = queue.poll();

            System.out.print(binaryNode.value + " ");

            if (binaryNode.leftChild != null) {
                queue.offer(binaryNode.leftChild);
            }
            if (binaryNode.rightChild != null) {
                queue.offer(binaryNode.rightChild);
            }
        }
    }

    private void levelOrder(TreeNode<T> node, int level) {
        if (null == node || level < 1) {
            return;
        }

        if (level == 1) {
            System.out.print(node.value + " ");
            return;
        }

        // 左子树
        levelOrder(node.leftChild, level - 1);

        // 右子树
        levelOrder(node.rightChild, level - 1);
    }


    public int depth(TreeNode<T> node) {
        if (null == node) {
            return 0;
        }

        int l = depth(node.leftChild);
        int r = depth(node.rightChild);
        if (l > r) {
            return l + 1;
        } else {
            return r + 1;
        }
    }

    int maxDeath(TreeNode node) {
        if (null == node) {
            return 0;
        }
        int left = maxDeath(node.leftChild);
        int right = maxDeath(node.rightChild);
        return Math.max(left, right) + 1;
    }

    public static void main(String[] args) {

        BinaryTreeNode binTree = new BinaryTreeNode();

        binTree.insertTreeNode(4);
        binTree.insertTreeNode(2);
        binTree.insertTreeNode(6);
        binTree.insertTreeNode(1);
        binTree.insertTreeNode(3);
        binTree.insertTreeNode(5);
        binTree.insertTreeNode(7);
        binTree.insertTreeNode(8);
        binTree.insertTreeNode(9);
        binTree.insertTreeNode(10);

        System.out.println("\uE15E前序遍历:");
        binTree.preorderTreeNodeFalse(binTree.getRoot());
        System.out.println("\r\n\uE15E中序遍历:");
        binTree.inorderTreeNodeFalse(binTree.getRoot());
        System.out.println("\r\n\uE15E后序遍历:");
        binTree.afterorderTreeNodeFalse(binTree.getRoot());
        System.out.println("\r\n\uE15E层序遍历:");
        binTree.levelOrderTreeNodeFalse(binTree.getRoot());

        System.out.println();
        TreeNode treeNode = binTree.findTreeNode(10);
        System.out.println(treeNode.toString());

        /***
         前序遍历:
         4 2 1 3 6 5 7 8 9 10
         中序遍历:
         1 2 3 4 5 6 7 8 9 10
         后序遍历:
         1 3 2 5 10 9 8 7 6 4
         层序遍历:
         4  2  6  1  3  5  7  8  9  10
         2←4→6
         */
    }


}




