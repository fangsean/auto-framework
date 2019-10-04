package com.auto.leetcode;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Description:
 *
 * @author: fang.sheng
 * Version: 1.0
 * Date: 2019-09-13
 * @since JDK 1.8
 */
public class InvertBinaryTree {

    /**
     * Definition for a binary tree node.
     */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * dfs（深度优先搜索）
     *
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {
        if (null == root) {
            return null;
        }
        TreeNode right = invertTree(root.right);
        TreeNode left = invertTree(root.left);
        root.left = right;
        root.right = left;
        return root;
    }

    /**
     * bfs（广度优先搜索）
     *
     * @param root
     * @return
     */
    public TreeNode invertTreeQueue(TreeNode root) {
        if (null == root) {
            return null;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            TreeNode left = current.left;
            current.left = current.right;
            current.right = left;
            if (null != current.left) {
                queue.add(current.left);
            }
            if (null != current.right) {
                queue.add(current.right);
            }
        }
        return root;
    }

    /**
     * bfs（广度优先搜索）
     *
     * @param root
     * @return
     */
    public TreeNode invertTreeStack(TreeNode root) {
        if (null == root) {
            return null;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode current = stack.lastElement();
            TreeNode left = current.left;
            current.left = current.right;
            current.right = left;
            if (null != current.left) {
                stack.push(current.left);
            }
            if (null != current.right) {
                stack.add(current.right);
            }
        }
        return root;
    }




}
    
    