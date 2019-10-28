package com.auto.algorithm.tree.binary;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.StringStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

/**
 * @author jsen.yin[jsen.yin@gmail.com]
 * 2019-03-17
 * @Description: <p>二叉查找树</p>
 */
public class Bintree<E extends Comparable<E>> implements BinaryTree<E> {

    protected TreeNode<E> root;
    protected int size;

    public Bintree() {
        size = 0;
    }

    //求出深度
    public int height() {
        return height(root);
    }

    public int height(TreeNode<E> root) {
        if (root == null) {
            return 0;
        }

        int leftHeight = height(root.left) + 1;
        int rightHeight = height(root.right) + 1;

        return leftHeight > rightHeight ? leftHeight : rightHeight;
    }

    //给B树插入一个元素。
    public boolean insert(E e) {
        if (root == null) {
            root = new TreeNode<>(e);
        } else {
            TreeNode<E> father = root;
            TreeNode<E> current = root;
            while (current != null) {
                if (e.compareTo(current.element) > 0) {
                    father = current;
                    current = current.right;
                } else if (e.compareTo(current.element) < 0) {
                    father = current;
                    current = current.left;
                } else {
                    return false;
                }
            }

            if (e.compareTo(father.element) > 0) {
                father.right = new TreeNode<>(e);
            } else {
                father.left = new TreeNode<>(e);
            }
        }
        size++;
        return true;
    }

    //在B树中查找一个元素。
    public boolean search(E e) {
        TreeNode<E> current = root;
        while (current != null) {
            if (e.compareTo(current.element) > 0) {
                current = current.right;
            } else if (e.compareTo(current.element) < 0) {
                current = current.left;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override//查看这棵树是否为空
    public boolean isEmpty() {
        return size == 0;
    }

    //在B树中删除一个元素
    public boolean remove(E e) {
        TreeNode<E> father = root;
        TreeNode<E> current = root;

        while (current != null) {
            if (e.compareTo(current.element) > 0) {
                father = current;
                current = current.right;
            } else if (e.compareTo(current.element) < 0) {
                father = current;
                current = current.left;
            } else {
                break;
            }
        }
        if (current == null) {
            return false;
        }

        //当被删除点没有左支的情况
        if (current.left == null) {
            if (current == root) {
                root = root.right;
            } else if (e.compareTo(father.element) < 0) {
                father.left = current.right;
            } else {
                father.right = current.right;
            }
        } else {
            TreeNode<E> parentOfMostRight = current;
            TreeNode<E> mostRight = current.left;

            //找到最右节点
            while (mostRight.right != null) {
                parentOfMostRight = mostRight;
                mostRight = mostRight.right;
            }

            current.element = mostRight.element;

            if (parentOfMostRight.left == mostRight) {
                parentOfMostRight.left = mostRight.left;
            } else {
                parentOfMostRight.right = mostRight.left;
            }
        }
        size--;
        return true;
    }

    //显示搜索路径
    public ArrayList<E> path(E e) {
        ArrayList<E> arrayList = new ArrayList<E>();
        TreeNode<E> current = root;

        while (current != null) {
            arrayList.add(current.element);
            if (e.compareTo(current.element) > 0) {
                current = current.right;
            } else if (e.compareTo(current.element) < 0) {
                current = current.left;
            } else {
                break;
            }
        }

        return arrayList;
    }

    @Override//显示这棵树的大小
    public int getSize() {
        return size;
    }

    //先序遍历
    public void preorder() {
        preorder(root);
    }

    private void preorder(TreeNode<E> treeNode) {
        if (treeNode == null) return;

        System.out.println(treeNode.element);
        preorder(treeNode.left);
        preorder(treeNode.right);
    }

    //中序遍历
    public void inorder() {
        inorder(root);
    }

    private void inorder(TreeNode<E> treeNode) {
        if (treeNode == null) return;

        inorder(treeNode.left);
        System.out.println(treeNode.element);
        inorder(treeNode.right);
    }

    //后序遍历
    public void postorder() {
        postorder(root);
    }

    private void postorder(TreeNode<E> treeNode) {
        if (treeNode == null) return;

        postorder(treeNode.left);
        postorder(treeNode.right);
        System.out.println(treeNode.element);
    }

    protected static class TreeNode<E extends Comparable<E>> {
        E element;
        TreeNode<E> right;
        TreeNode<E> left;

        public TreeNode(E e) {
            element = e;
        }
    }

    //判断是否为完全二叉树
    public boolean isFullBinaryTree() {
        TreeNode<E> current = root;
        ArrayList arrayList = new ArrayList();
        isFullBinaryTree(root, 0, arrayList);

        for (int i = 0; i < arrayList.size() - 1; i++) {
            if (arrayList.get(i) != arrayList.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    private void isFullBinaryTree(TreeNode<E> current, int depth, ArrayList<Integer> arrayList) {
        if (current == null) {
            arrayList.add(depth);
            return;
        } else {
            isFullBinaryTree(current.left, depth + 1, arrayList);
            isFullBinaryTree(current.right, depth + 1, arrayList);
        }
    }

    //不使用递归的情况下进行中序遍历
    public void myInorder() {
        TreeNode<E> current;
        Stack<TreeNode<E>> stack = new Stack<>();
        current = root;

        while (true) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            if (stack.empty()) {
                break;
            }

            current = stack.pop();
            System.out.println(current.element);
            current = current.right;
        }
    }

    //不使用递归的情况下进行先序遍历
    public void myPreorder() {
        TreeNode<E> current = root;
        Stack<TreeNode<E>> stack = new Stack<>();

        while (true) {
            while (current != null) {
                if (current.right != null) {
                    stack.push(current.right);
                }
                System.out.println(current.element);
                current = current.left;
            }

            if (stack.empty()) {
                return;
            }

            current = stack.pop();
        }
    }

    //不使用递归的情况下进行后序遍历
    public void myPostorder() {
        TreeNode<E> current = root;
        Stack<TreeNode<E>> stack = new StringStack();

        while (true) {
            while (current != null) {
                if (current.left != null) {
                    stack.push(current);
                    current = current.left;
                } else if (current.right != null) {
                    stack.push(current);
                    current = current.right;
                } else {
                    System.out.println(current.element);
                    break;
                }
            }

            while (!stack.empty() && stack.peek().right == current) {
                current = stack.peek();
                System.out.println(stack.pop().element);
            }

            if (stack.empty()) {
                break;
            }
            current = stack.peek().right;
        }
    }

    @Override//清空这颗树
    public void clear() {
        size = 0;
        root = null;
    }

    //返回叶子结点的个数
    public int getNumberOfLeaves() {
        return getNumberOfLeaves(root);
    }

    private int getNumberOfLeaves(TreeNode<E> current) {
        if (current == null) {
            return 0;
        }

        int left = getNumberOfLeaves(current.left);
        int right = getNumberOfLeaves(current.right);

        if (left == 0 && right == 0) {
            return 1;
        } else {
            return left + right;
        }
    }

    //返回非叶子结点的个数
    public int getNumberofNonLeaves() {
        return getNumberofNonLeaves(root);
    }

    private int getNumberofNonLeaves(TreeNode<E> current) {
        if (current == null) {
            return 0;
        }

        if (current.left == null && current.right == null) {
            return 0;
        }

        int left = getNumberofNonLeaves(current.left);
        int right = getNumberofNonLeaves(current.right);

        return left + right + 1;
    }

    @Override//返回一个以中序遍历这颗二叉查找树的单项迭代器
    public Iterator<E> iterator() {
        return inorderIterator();
    }

    public Iterator<E> inorderIterator() {
        return new InorderIterator();
    }

    //单项迭代器对应的类
    class InorderIterator implements Iterator<E> {
        private ArrayList<E> arrayList = new ArrayList<E>();
        private int current = 0;

        public InorderIterator() {
            Bintree.this.inorder();
        }

        private void inorder() {
            inorder(root);
        }

        private void inorder(TreeNode<E> treeNode) {
            if (treeNode == null) {
                return;
            }

            inorder(treeNode.left);
            arrayList.add(treeNode.element);
            inorder(treeNode.right);
        }

        @Override
        public E next() {
            return arrayList.get(current++);
        }

        @Override
        public void remove() {
            Bintree.this.remove(arrayList.get(current));
            arrayList.remove(current);
        }

        @Override
        public boolean hasNext() {
            if (current < arrayList.size()) {
                return true;
            }
            return false;
        }

        public void clear() {
            root = null;
            size = 0;
        }
    }

    public static void main(String[] args) {
        Random random = new Random(2);

        BinaryTree<Integer> tree = new Bintree<>();

        int[] number = new int[10000000];

        for (int i = 0; i < number.length; i++) {
            number[i] = random.nextInt(number.length);
        }

        long time = System.currentTimeMillis();

        for (int i = 0; i < number.length; i++) {
            tree.insert(number[i]);
        }

        System.out.println(System.currentTimeMillis() - time);
    }
}
