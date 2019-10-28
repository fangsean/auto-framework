package com.auto.algorithm.tree.binary;

import java.util.Stack;

/**
 * @author jsen.yin[jsen.yin@gmail.com]
 * 2019-03-17
 * @Description: <p>分裂树</p>
 */
public class SplayTree <E extends Comparable<E>> extends Bintree<E> {
    public SplayTree(){
        super();
    }

    @Override
    public boolean search(E e) {
        Stack<TreeNode<E>> path=new Stack<>();

        TreeNode<E> current=root;

        while (current!=null){
            if(e.compareTo(current.element)>0){
                path.add(current);
                current=current.right;
            }else if(e.compareTo(current.element)<0){
                path.add(current);
                current=current.left;
            }else {
                break;
            }
        }

        if (current==null){
            return false;
        }else if(path.size()==0){
            return true;
        }

        while (path.peek()!=root){
            TreeNode<E> parent=path.pop();
            singleRotation(current,parent,path.peek());
        }

        if(root.left==current){
            root.left=current.right;
            current.right=root;
            root=current;
        }else {
            root.right=current.left;
            current.left=root;
            root=current;
        }

        return true;
    }

    //单旋转操作,非根旋转
    private void singleRotation(TreeNode<E> current,TreeNode<E> parent,TreeNode<E> grandParent){
        if(grandParent.left==parent){
            grandParent.left=current;
        }else {
            grandParent.right=current;
        }

        if(parent.left==current){
            parent.left=current.right;
            current.right=parent;
        }else{
            parent.right=current.left;
            current.left=parent;
        }
    }
}