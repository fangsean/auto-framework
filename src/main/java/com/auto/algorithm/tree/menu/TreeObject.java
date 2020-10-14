package com.auto.algorithm.tree.menu;

import lombok.Data;

import java.util.List;

/**
 * 树型结构数据原型
 *
 * @param <T>
 */
@Data
public abstract class TreeObject<T> {

    abstract void setChildren(List<T> children);

    abstract boolean isShow();

    abstract void setHide(boolean isHide);

}
