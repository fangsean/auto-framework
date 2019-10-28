package com.auto.algorithm.leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Description:
 *
 * @author: fang.sheng
 * Version: 1.0
 * Date: 2019-09-14
 * @since JDK 1.8
 */
public class MaximumDepthOfBinaryTree {

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

    public int maxDepthd(TreeNode root) {
        if (null == root) {
            return 0;
        }
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return Math.max(left, right) + 1;
    }


    public int maxDepth(TreeNode root) {
        if (null == root) {
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        Queue<Integer> value = new LinkedList<>();
        queue.add(root);
        value.add(0);
        int depth = 0, temp = 0;
        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            Integer poll = value.poll();
            temp = poll;
            depth = Math.max(temp, depth);
            if (null != current.left) {
                queue.add(current.left);
                value.add(temp + 1);
            }
            if (null != current.right) {
                queue.add(current.right);
                value.add(temp + 1);
            }
        }
        return depth;
    }


}
    
    