package com.auto.algorithm.tree.binary;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author jsen.yin[jsen.yin@gmail.com]
 * 2019-03-17
 * @Description: <p></p>
 */
public class AVL<E extends Comparable<E>> extends Bintree<E> {

    public AVL() {
        super();
    }

    public boolean insert(E e) {
        TreeNode<E> parent = (TreeNode<E>) root;
        TreeNode<E> current = (TreeNode<E>) root;
        ArrayList<TreeNode<E>> path = new ArrayList<>();

        while (current != null) {
            path.add(current);
            if (e.compareTo(current.element) > 0) {
                parent = current;
                current = (TreeNode<E>) current.right;
            } else if (e.compareTo(current.element) < 0) {
                parent = current;
                current = (TreeNode<E>) current.left;
            } else {
                return false;
            }
        }

        if (parent == current) {
            root = new TreeNode<>(e);
        } else {
            if (e.compareTo(parent.element) > 0) {
                parent.right = new TreeNode<>(e);
                //若parent节点没有左子树，则搜索路径上的所有节点高度小于它的子高度的都加1,整理AVL树
                if (parent.left == null) {
                    path.get(path.size() - 1).altitude++;
                    for (int i = path.size() - 2; i >= 0; i--) {
                        if (path.get(i).altitude <= path.get(i + 1).altitude) {
                            path.get(i).altitude++;
                        }
                    }
                    amendment(path);
                }
            } else {
                parent.left = new TreeNode<>(e);

                if (parent.right == null) {
                    path.get(path.size() - 1).altitude++;
                    for (int i = path.size() - 2; i >= 0; i--) {
                        if (path.get(i).altitude <= path.get(i + 1).altitude) {
                            path.get(i).altitude++;
                        }
                    }
                    amendment(path);
                }
            }
        }

        size++;
        return true;
    }

    /**
     * 计算一个节点左树和右树间的深度差，
     *
     * @param treeNode
     * @return depth
     * 若左边比右边深则返回正数，平衡则0，
     * 若右边比左边深则返回负数。
     */
    private int altitudeDifferent(TreeNode<E> treeNode) {
        int depthDifferent;
        int rightDepth;
        int leftDepth;

        if (treeNode.left == null) {
            leftDepth = 0;
        } else {
            leftDepth = ((TreeNode<E>) treeNode.left).altitude;
        }

        if (treeNode.right == null) {
            rightDepth = 0;
        } else {
            rightDepth = ((TreeNode<E>) treeNode.right).altitude;
        }

        depthDifferent = leftDepth - rightDepth;
        return depthDifferent;
    }

    //进行自平衡操作
    private void amendment(ArrayList<TreeNode<E>> path) {

        //对路径进行分析，并进对其进行旋转调节
        for (int i = path.size() - 1; i >= 0; i--) {
            TreeNode<E> current = path.get(i);
            int depthDifferent = altitudeDifferent(current);
            if (depthDifferent <= 1 && depthDifferent >= -1) {
                continue;
            }

            if (depthDifferent > 1 && altitudeDifferent((TreeNode<E>) current.left) > 0) {
                leftRotate(current);
            } else if (depthDifferent < -1 && altitudeDifferent((TreeNode<E>) current.right) < 0) {
                rightRotate(current);
            } else if (depthDifferent < -1 && altitudeDifferent((TreeNode<E>) current.right) > 0) {
                doubleRightRotate(current);
            } else if (depthDifferent > 1 && altitudeDifferent((TreeNode<E>) current.left) < 0) {
                doubleLeftRotate(current);
            }
            for (int j = i - 1; j >= 0; j--) {
                //用getAltitude方法进行错误处理
                if (path.get(j).altitude - 1 > getAltitude((TreeNode<E>) path.get(j).left)
                        && path.get(j).altitude - 1 > getAltitude((TreeNode<E>) path.get(j).right)) {
                    path.get(j).altitude--;
                }
            }
        }
    }

    //查询对象的altitude，若为null则返回0
    private int getAltitude(TreeNode<E> treeNode) {
        if (treeNode == null) {
            return 0;
        }
        return treeNode.altitude;
    }

    //双旋转，左旋转
    private void doubleLeftRotate(TreeNode<E> current) {
        //转换深度
        current.altitude = current.altitude - 2;
        ((TreeNode<E>) current.left.right).altitude++;
        ((TreeNode<E>) current.left).altitude--;

        TreeNode<E> temp1 = new TreeNode<E>(current.left.element, ((TreeNode<E>) current.left).altitude);
        TreeNode<E> temp2 = new TreeNode<E>(current.element, current.altitude);
        temp1.left = current.left.left;
        temp1.right = current.left.right.left;
        temp2.left = current.left.right.right;
        temp2.right = current.right;
        current.element = current.left.right.element;
        current.altitude = ((TreeNode<E>) current.left.right).altitude;
        current.left = temp1;
        current.right = temp2;
    }

    //双旋转,右旋转
    private void doubleRightRotate(TreeNode<E> current) {
        //转换深度
        current.altitude = current.altitude - 2;
        ((TreeNode<E>) current.right.left).altitude++;
        ((TreeNode<E>) current.right).altitude--;

        TreeNode<E> temp1 = new TreeNode<E>(current.element, current.altitude);
        TreeNode<E> temp2 = new TreeNode<E>(current.right.element, ((TreeNode<E>) current.right).altitude);
        temp1.left = current.left;
        temp1.right = current.right.left.left;
        temp2.left = current.right.left.right;
        temp2.right = current.right.right;
        current.element = current.right.left.element;
        current.altitude = ((TreeNode<E>) current.right.left).altitude;
        current.left = temp1;
        current.right = temp2;
    }

    //右旋转
    private void rightRotate(TreeNode<E> current) {
        //转换深度
        current.altitude = current.altitude - 2;

        TreeNode<E> temp = new TreeNode<>(current.element, current.altitude);
        temp.left = current.left;
        temp.right = current.right.left;
        current.element = current.right.element;
        current.altitude = ((TreeNode<E>) current.right).altitude;
        current.left = temp;
        current.right = current.right.right;
    }

    //左旋转
    private void leftRotate(TreeNode<E> current) {
        //先转换好深度
        current.altitude = current.altitude - 2;

        TreeNode<E> temp = new TreeNode<E>(current.element, current.altitude);
        temp.right = current.right;
        temp.left = current.left.right;
        current.element = current.left.element;
        current.altitude = ((TreeNode<E>) current.left).altitude;
        current.right = temp;
        current.left = current.left.left;
    }

    @Override
    public boolean remove(E e) {
        ArrayList<TreeNode<E>> path = new ArrayList<>();
        TreeNode<E> current = (TreeNode<E>) root;
        while (current != null) {
            path.add(current);
            if (e.compareTo(current.element) > 0) {
                current = (TreeNode<E>) current.right;
            } else if (e.compareTo(current.element) < 0) {
                current = (TreeNode<E>) current.left;
            } else {
                break;
            }
        }
        if (current == null) {
            return false;
        }

        //先处理没有左支的情况
        if (current.left == null) {
            if (path.size() == 1) {
                root = root.right;
                size--;
                return true;
            } else {
                TreeNode<E> parent = path.get(path.size() - 2);
                if (current.element.compareTo(parent.element) > 0) {
                    parent.right = current.right;
                } else {
                    parent.left = current.right;
                }
            }
        } else {
            //先找出最右点
            TreeNode<E> mostRight = (TreeNode<E>) current.left;
            path.add(mostRight);

            while (mostRight.right != null) {
                mostRight = (TreeNode<E>) mostRight.right;
                path.add(mostRight);
            }
            current.element = mostRight.element;
            TreeNode<E> parentOfMostRight = path.get(path.size() - 2);
            if (mostRight.element.compareTo(parentOfMostRight.element) > 0) {
                parentOfMostRight.right = mostRight.left;
            } else {
                parentOfMostRight.left = mostRight.left;
            }
        }

        for (int i = path.size() - 2; i >= 0; i--) {
            int maxSonAltitude = getAltitude((TreeNode<E>) path.get(i).left);
            if (getAltitude((TreeNode<E>) path.get(i).right) > maxSonAltitude) {
                maxSonAltitude = getAltitude((TreeNode<E>) path.get(i).right);
            }

            path.get(i).altitude = maxSonAltitude + 1;
        }

        path.remove(path.size() - 1);
        amendment(path);

        size--;
        return true;
    }

    protected static class TreeNode<E extends Comparable<E>> extends Bintree.TreeNode<E> {
        int altitude;

        public TreeNode(E element) {
            this(element, 1);
        }

        public TreeNode(E element, int altitude) {
            super(element);
            this.altitude = altitude;
        }
    }


    public static void main(String[] args) {
        HashSet<Integer> set = new HashSet<>();
        AVL<Integer> avl = new AVL<>();

        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 100; j++) {
                int number = (int) (Math.random() * 30);
                if (set.add(number) != avl.insert(number)) {
                    System.out.println("Wrong");
                }
            }
            for (int j = 0; j < 10000; j++) {
                int number = (int) (Math.random() * 300);
                if (set.remove(number) != avl.remove(number)) {
                    System.out.println("Wrong");
                }
            }
        }
    }
}

