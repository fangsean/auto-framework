package com.auto.leetcode;

/**
 * Description:
 *
 * @author: fang.sheng
 * Version: 1.0
 * Date: 2019-09-13
 * @since JDK 1.8
 */
public class MergeTwoBinaryTrees {

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
     * t1 为基本节点对象
     *
     * @param t1
     * @param t2
     * @return
     */
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (null == t1) {
            return t2;
        }
        if (null == t2) {
            return t1;
        }
        t1.val = (t1.val + t2.val);
        t1.left = mergeTrees(t1.left, t2.left);
        t1.right = mergeTrees(t1.right, t2.right);
        return t1;
    }


}
    
    