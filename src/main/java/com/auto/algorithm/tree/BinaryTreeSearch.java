package com.auto.algorithm.tree;

import java.util.*;

/**
 * @author jsen.yin[jsen.yin@gmail.com]
 * 2019-03-12
 * @Description: <p></p>
 */
public class BinaryTreeSearch {

    /**
     * 深度遍历
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (null == root) {
            return 0;
        }

        int left = maxDepth(root.leftChild);
        int right = maxDepth(root.rightChild);

        return Math.max(left, right) + 1;
    }

    /**
     * 深度优先
     * @param res
     * @param root
     * @param level
     */
    public void levelOrderDfs_(List<Integer> res, TreeNode<Integer> root, int level) {
        if (null == root) {
            return;
        }
        res.add(root.value);
        levelOrderDfs_(res, root.leftChild, level + 1);
        levelOrderDfs_(res, root.rightChild, level + 1);
    }

    public List<Integer> levelOrderDfs(TreeNode<Integer> root) {
        if (null == root) {
            return null;
        }
        List<Integer> res = new ArrayList<>();
        levelOrderDfs_(res, root, 0);
        return res;
    }


    /**
     * 广度优先
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrderBfs(TreeNode<Integer> root) {
        List<List<Integer>> res = new ArrayList<>();
        if (null == root) {
            return res;
        }

        Queue<TreeNode<Integer>> queue = new LinkedList<>();

        queue.add(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> currlevel = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                TreeNode<Integer> currNode = queue.poll();
                currlevel.add(currNode.value);
                if (null != currNode.leftChild) {
                    queue.add(currNode.leftChild);
                }
                if (null != currNode.rightChild) {
                    queue.add(currNode.rightChild);
                }
            }
            res.add(currlevel);
        }
        return res;
    }


    public static void main(String[] args) {

/*
        TreeNode treeNode1 = new TreeNode(1);
        TreeNode treeNode2 = new TreeNode(2, treeNode1, null);
        TreeNode treeNode3 = new TreeNode(3, treeNode2, null);
        TreeNode treeNode4 = new TreeNode(4, null, treeNode3);
        TreeNode treeNode5 = new TreeNode(5, null, treeNode4);
        TreeNode treeNode6 = new TreeNode(6, null, treeNode5);
        TreeNode treeNode7 = new TreeNode(7, treeNode6, null);
        TreeNode treeNode8 = new TreeNode(8, treeNode7, null);
        TreeNode treeNode = new TreeNode(0, treeNode8, null);
*/

        TreeNode treeNode1 = new TreeNode(1);
        TreeNode treeNode2 = new TreeNode(2, treeNode1, null);
        TreeNode treeNode3 = new TreeNode(3, null, treeNode2);
        TreeNode treeNode4 = new TreeNode(4, null, null);
        TreeNode treeNode5 = new TreeNode(5, treeNode3, treeNode4);
        TreeNode treeNode6 = new TreeNode(6, treeNode5, null);
        TreeNode treeNode8 = new TreeNode(8, null, null);
        TreeNode treeNode7 = new TreeNode(7, treeNode8, treeNode6);
        TreeNode treeNode = new TreeNode(0, treeNode7, null);

        BinaryTreeSearch binaryTreeSearch = new BinaryTreeSearch();
        int i = binaryTreeSearch.maxDepth(treeNode);

        System.out.println(i);

//        List list = binaryTreeSearch.levelOrderBfs(treeNode);
        List list = binaryTreeSearch.levelOrderDfs(treeNode);
        System.out.println(Arrays.toString(list.toArray()));


    }


}
