package com.auto.algorithm.tree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: <p>树节点对象</p>
 */
@Slf4j
@Data
public class TreeNode<T> {

    T value;

    TreeNode<T> parent;
    /**
     * 左孩子
     */
    TreeNode<T> leftChild;
    /**
     * 右孩子
     */
    TreeNode<T> rightChild;

    TreeNode() {
    }

    TreeNode(T value) {
        this.value = value;
    }

    public TreeNode(T value, TreeNode<T> left, TreeNode<T> right) {
        this.value = value;
        this.leftChild = left;
        this.rightChild = right;
        if (null != this.leftChild) {
            this.leftChild.parent = this;
        }
        if (null != this.rightChild) {
            this.rightChild.parent = this;
        }
    }

    public final T setValue(T newValue) {
        T oldValue = value;
        value = newValue;
        return oldValue;
    }

    public T getValue() {
        return value;
    }

    public int compareTo(T value) {
        if (this.value == value) {
            return 0;
        } else if (value != null) {
            if (value instanceof String) {
                String s = (String) this.value;
                String s1 = (String) value;
                return s.compareTo(s1);
            } else if (value instanceof Integer) {
                Integer s = (Integer) this.value;
                Integer s1 = (Integer) value;

                if (s == s1) {
                    return 0;
                } else {
                    return (s - s1) > 0 ? 1 : -1;
                }
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return ((this.leftChild == null) ? "" : this.leftChild.value + "←") + "[" + this.value + "]" + ((this.rightChild == null) ? "" : "→" + this.rightChild.value);
    }
}
