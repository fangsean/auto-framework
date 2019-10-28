package com.auto.algorithm.tree.b;

/**
 * @author jsen.yin[jsen.yin@gmail.com]
 * 2019-03-17
 * @Description: <p></p>
 */
public class Finder {


    public static void main(String[] args) {
        double[] arr = {4, 3, 5, 8, 7, 9};
        BsTree root = new BsTree(6);
        for (double i : arr) {
            root.insert(root, i);
        }

        root.insert(root, 4.1);

        perRootStack(root);
//        BsTree bsTree = findRecurrence(root, 7);

        BsTree bsTree = findStack(root, 6);
        System.out.println(bsTree.data);

    }

    public static BsTree findRecurrence(BsTree bsTree, double value) {
        BsTree temp = bsTree;
        if (temp.data == value) {
            System.out.println("最终：" + temp.data);
            return bsTree;
        } else if (value < temp.data) {
            System.out.println("路径：" + temp.data);
            temp = bsTree.left;
            return findRecurrence(temp, value);
        } else if (value > temp.data) {
            System.out.println("路径：" + temp.data);
            temp = bsTree.right;
            return findRecurrence(temp, value);
        }

        return null;
    }

    public static BsTree findStack(BsTree bsTree, double value) {
        BsTree currentTree = bsTree;
        while (true) {
            if (value == currentTree.data) {
                System.out.println("finally: " + value);
                return currentTree;
            } else if (value < currentTree.data) {
                currentTree = currentTree.left;
            } else if (value > currentTree.data) {
                currentTree = currentTree.right;
            } else {
                return null;
            }

        }

    }

    public static void preRootRecurrence(BsTree root) {
        if (root != null) {
            System.out.println(root.data);
            preRootRecurrence(root.left);
            preRootRecurrence(root.right);
        }
    }

    public static void perRootStack(BsTree root) {
        int index = 0;
        BsTree[] stack = new BsTree[20];
        stack[0] = root;
        index = index + 1;
        BsTree temp;
        while (index > 0) {
            index = index - 1;
            temp = stack[index];
            System.out.println(temp.data);

            if (temp.right != null) {
                stack[index] = temp.right;
                index = index + 1;
            }
            if (temp.left != null) {
                stack[index] = temp.left;
                index = index + 1;
            }
            /*try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }

    public BsTree minData(BsTree bsTree) {
        BsTree currentTree = bsTree;
        while (true) {
            if (currentTree.left == null) {
                System.out.println(currentTree.data);
                return currentTree;
            } else {
                currentTree = currentTree.left;
            }
        }
    }

}
