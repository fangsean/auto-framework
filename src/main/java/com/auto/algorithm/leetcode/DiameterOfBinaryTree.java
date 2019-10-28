package com.auto.algorithm.leetcode;

/**
 * Description:
 *
 * @author: fang.sheng
 * Version: 1.0
 * Date: 2019-09-15
 * @since JDK 1.8
 */
public class DiameterOfBinaryTree {

    /**
     * Definition for a binary tree node.
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static int diameterOfBinaryTree(TreeNode root) {
        depthOfBinaryTree(root);
        return max;
    }

    public static int max = 0;

    public static int depthOfBinaryTree(TreeNode root) {
        if (null == root) {
            return 0;
        }
        int left = depthOfBinaryTree(root.left);
        int right = depthOfBinaryTree(root.right);
        max = Math.max(left + right, max);
        System.out.println(left + " " + right + " " + max);
        return Math.max(left, right) + 1;
    }

    public static void main(String[] args) {
        /*
        TreeNode treeNode = new TreeNode(0);
        int i = diameterOfBinaryTree(treeNode);
        System.out.println(i);
        */

        String a = "aa_bb_cc";

        String substring = a.substring(0, a.lastIndexOf("_"));
        System.out.println(substring);


    }


}
    
    