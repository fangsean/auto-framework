package com.auto.algorithm.tree.binary;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author jsen.yin[jsen.yin@gmail.com]
 * 2019-03-17
 * @Description: <p>线索二叉树</p>
 */
public class ThreadedTree <E extends Comparable<E>> implements BinaryTree<E>{
    private TreeNode<E> root;
    private int size=0;

    @Override
    public boolean insert(E e) {
        if(root==null){
            root=new TreeNode<E>(e);
            size++;
            return true;
        }

        TreeNode<E> current=root;

        //进行查找
        while (true){
            if(current.isRightNode&&e.compareTo(current.element)>0){
                current=current.right;
            }else if(current.isLeftNode&&e.compareTo(current.element)<0){
                current=current.left;
            }else if(e.compareTo(current.element)==0){
                return false;
            }else {
                break;
            }
        }

        TreeNode<E> temp=new TreeNode<E>(e);

        if(e.compareTo(current.element)>0){
            current.isRightNode=true;
            temp.right=current.right;
            current.right=temp;
            temp.left=current;
        }else {
            current.isLeftNode=true;
            temp.left=current.left;
            current.left=temp;
            temp.right=current;
        }

        size++;
        return true;
    }

    @Override
    public boolean search(E e) {
        TreeNode<E> current=root;

        if(current==null){
            return false;
        }

        while (true){
            if(current.isRightNode&&e.compareTo(current.element)>0){
                current=current.right;
            }else if(current.isLeftNode&&e.compareTo(current.element)<0) {
                current=current.left;
            }else if(e.compareTo(current.element)==0) {
                return true;
            }else {
                return false;
            }
        }
    }

    @Override
    public boolean remove(E e) {
        TreeNode<E> parent=root;
        TreeNode<E> current=root;

        //排除root为空的情况
        if(current==null){
            return false;
        }

        while (true){
            if(current.isRightNode&&e.compareTo(current.element)>0){
                parent=current;
                current=current.right;
            }else if(current.isLeftNode&&e.compareTo(current.element)<0) {
                parent=current;
                current=current.left;
            }else if(e.compareTo(current.element)==0) {
                break;
            }else {
                return false;
            }
        }

        //当左子树为空的情况
        if(!current.isLeftNode){
            if(current==root){
                if(root.isRightNode){
                    root.isRightNode=current.isRightNode;
                    root=root.right;
                }else {
                    clear();
                }
            }else if(current==parent.right) {
                parent.isRightNode=current.isRightNode;
                parent.right=current.right;
            }else if(!current.isRightNode){
                parent.isLeftNode=current.isLeftNode;
                parent.left=current.left;
            }else {
                parent.left=current.right;
                TreeNode<E> temp=current.right;
                while (temp.isLeftNode){
                    temp=temp.left;
                }
                temp.left=current.left;
            }
            size--;
            return true;
        }

        TreeNode<E> mostOfParent=current.left;
        TreeNode<E> mostOfLeft=current.left;

        while (mostOfLeft.isRightNode){
            mostOfParent=mostOfLeft;
            mostOfLeft=mostOfParent.right;
        }

        current.element=mostOfLeft.element;

        if(mostOfParent==mostOfLeft){
            current.left=mostOfLeft.left;
            current.isLeftNode=mostOfLeft.isLeftNode;
        }else {
            mostOfParent.right=mostOfLeft.left;
            mostOfParent.isRightNode=mostOfLeft.isLeftNode;
        }

        size--;
        return true;
    }

    @Override
    public void clear(){
        root=null;
        size=0;
    }

    @Override
    public ArrayList<E> path(E e) {
        ArrayList<E> path=new ArrayList<E>();

        if(root==null){
            return path;
        }

        TreeNode<E> current=root;

        while (true){
            path.add(current.element);
            if(current.isLeftNode&&e.compareTo(current.element)<0){
                current=current.left;
            }else if(current.isRightNode&&e.compareTo(current.element)>0){
                current=current.right;
            }else{
                break;
            }
        }

        return path;
    }

    @Override
    public boolean isEmpty() {
        return root==null;
    }

    @Override
    public void inorder() {
        TreeNode<E> current=root;

        if(root==null){
            return;
        }

        while (current.isLeftNode){
            current=current.left;
        }

        while (current!=null){
            System.out.println(current.element);
            if(current.isRightNode){
                current=current.right;
                while (current.isLeftNode){
                    current=current.left;
                }
            }else {
                current=current.right;
            }
        }
    }

    @Override
    public void postorder() {
        postorder(root);
    }
    private void postorder(TreeNode<E> current){
        if(current.isLeftNode){
            postorder(current.left);
        }
        if(current.isRightNode){
            postorder(current.right);
        }
        System.out.println(current.element);
    }

    @Override
    public void preorder() {
        boolean isReally=true;
        TreeNode<E> current=root;

        while (current!=null){
            if(isReally){
                System.out.println(current.element);
                if(current.isLeftNode){
                    current=current.left;
                    continue;
                }
            }

            if(current.isRightNode){
                isReally=true;
            }else {
                isReally=false;
            }
            current=current.right;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new InorderIterator();
    }

    @Override
    public int getSize() {
        return size;
    }

    public static class TreeNode<E extends Comparable<E>>{
        private E element;
        private TreeNode<E> left;
        private TreeNode<E> right;
        private boolean isLeftNode;
        private boolean isRightNode;

        public TreeNode(E element){
            this.element=element;
            isLeftNode=false;
            isRightNode=false;
        }
    }

    class InorderIterator implements Iterator<E>{
        private TreeNode<E> child;
        private TreeNode<E> current;

        public InorderIterator(){
            current=root;
            if(root==null){
                return;
            }

            while (current.isLeftNode){
                current=current.left;
            }
        }

        @Override
        public boolean hasNext() {
            return current!=null;
        }

        @Override
        public E next() {
            E e=current.element;
            child=current;
            if(current.isRightNode){
                current=current.right;
                while (current.isLeftNode){
                    current=current.left;
                }
            }else {
                current=current.right;
            }

            return e;
        }

        @Override
        public void remove() {
            ThreadedTree.this.remove(current.left.element);
        }

        public void clear(){
            root=null;
            size=0;
        }
    }

    public static void main(String[] args) {
        ThreadedTree<Integer> tree=new ThreadedTree<>();
        tree.insert(0);
        tree.insert(3);
        tree.insert(4);
        tree.insert(6);
        tree.insert(2);
        tree.insert(9);
        tree.insert(7);
        tree.insert(8);

        Iterator<Integer> iterator=tree.iterator();
//        for(int i=0;i<6;i++){
//            iterator.next();
//        }
//        iterator.remove();

        tree.inorder();
    }
}
