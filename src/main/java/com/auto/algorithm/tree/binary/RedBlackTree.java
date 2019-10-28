package com.auto.algorithm.tree.binary;

import java.util.Stack;

/**
 * @author jsen.yin[jsen.yin@gmail.com]
 * 2019-03-17
 * @Description: <p>红黑树</p>
 */
public class RedBlackTree<E extends Comparable<E>> extends Bintree<E> {

    @Override//红黑树的插入操作
    public boolean insert(E e) {
        TreeNode<E> parent = (TreeNode<E>) root;
        TreeNode<E> current = (TreeNode<E>) root;

        Stack<TreeNode<E>> path = new Stack<>();

        while (current != null) {
            path.push(current);
            if (e.compareTo(current.element) > 0) {
                parent = current;
                current = (TreeNode) current.right;
            } else if (e.compareTo(current.element) < 0) {
                parent = current;
                current = (TreeNode) current.left;
            } else {
                return false;
            }
        }

        if (parent == current) {
            root = new TreeNode<>(e, TreeNode.BLACK);
            size++;
            return true;
        }

        if (e.compareTo(parent.element) > 0) {
            parent.right = new TreeNode<>(e, TreeNode.RED);
            path.push((TreeNode<E>) parent.right);
        } else {
            parent.left = new TreeNode<>(e, TreeNode.RED);
            path.push((TreeNode<E>) parent.left);
        }

        size++;

        insertAmendment(path);

        return true;
    }

    @Override//红黑树的删除操作
    public boolean remove(E e) {
        Stack<TreeNode<E>> path = new Stack<>();
        TreeNode<E> current = (TreeNode<E>) root;

        while (current != null) {
            if (e.compareTo(current.element) > 0) {
                path.push(current);
                current = (TreeNode<E>) current.right;
            } else if (e.compareTo(current.element) < 0) {
                path.push(current);
                current = (TreeNode<E>) current.left;
            } else {
                break;
            }
        }

        if (current == null) {
            return false;
        }

        //当其左子树为null时
        if (current.left == null) {
            //根据不同情况对current结点进行删除，并把其右子节点挂到其入口处
            if (path.empty()) {
                //currnet==root
                root = root.right;
            } else if (e.compareTo(path.peek().element) > 0) {
                path.peek().right = current.right;
            } else {
                path.peek().left = current.right;
            }

            //根据红黑树的性质，若current.left为空，则右边子树要么为空，要么为一个红结点
            if (current.colour == TreeNode.BLACK) {
                if (current.right != null) {
                    ((TreeNode) current.right).colour = TreeNode.BLACK;
                } else {
                    path.add(null);
                    removeAmendment(path);
                }
            }
            size--;
            return true;
        }

        //当其左子树不为null时
        path.push(current);
        TreeNode<E> mostOfLeft = (TreeNode<E>) current.left;

        while (mostOfLeft.right != null) {
            path.push(mostOfLeft);
            mostOfLeft = (TreeNode<E>) mostOfLeft.right;
        }

        //结点替换工作
        current.element = mostOfLeft.element;

        //若为红结点，直接删除
        if (mostOfLeft.colour == TreeNode.RED) {
            if (path.peek().left == mostOfLeft) {
                path.peek().left = mostOfLeft.left;
            } else {
                path.peek().right = mostOfLeft.left;
            }
            size--;
            return true;
        }

        TreeNode<E> parent = path.pop();

        //对非红结点的处理
        if (mostOfLeft == parent.left) {
            parent.left = mostOfLeft.left;
            path.push(parent);
            path.push((TreeNode<E>) parent.left);
            removeAmendment(path);

        } else {
            parent.right = mostOfLeft.left;
            path.push(parent);
            path.push((TreeNode<E>) parent.right);
            removeAmendment(path);
        }

        size--;
        return true;
    }

    //进行删除的自平衡操作
    private void removeAmendment(Stack<TreeNode<E>> path) {
        TreeNode<E> current = path.pop();

        //若结点为红色直接涂黑后返回
        if (current != null && current.colour == TreeNode.RED) {
            current.colour = TreeNode.BLACK;
            return;
        }

        TreeNode<E> parent;
        TreeNode<E> grandParent;

        while (!path.empty()) {
            parent = path.pop();
            //用于定位parent是否在根节点处
            if (!path.empty()) {
                grandParent = path.peek();
            } else {
                grandParent = null;
            }

            if (current == parent.left) {
                TreeNode<E> brother = (TreeNode<E>) parent.right;

                //若N的兄弟结点为红色，先进行旋转
                if (brother.colour == TreeNode.RED) {
                    path.push(brother);
                    parent.colour = TreeNode.RED;
                    brother.colour = TreeNode.BLACK;
                    if (grandParent == null) {
                        rootLeftRotate();
                    } else {
                        leftRotate(parent, grandParent);
                    }
                    brother = (TreeNode<E>) parent.right;
                    grandParent = path.peek();
                }

                if (brother.right != null
                        && ((TreeNode<E>) brother.right).colour == TreeNode.RED) {
                    //若brother的右子树为红色
                    ((TreeNode) brother.right).colour = TreeNode.BLACK;
                    brother.colour = parent.colour;
                    parent.colour = TreeNode.BLACK;
                    path.clear();
                } else if (brother.left != null
                        && ((TreeNode) brother.left).colour == TreeNode.RED) {
                    //若brother的左子树为红色
                    ((TreeNode) brother.left).colour = parent.colour;
                    parent.colour = TreeNode.BLACK;
                    rightRotate(brother, parent);
                    path.clear();
                } else if (parent.colour == TreeNode.RED) {
                    //若parent为红色
                    brother.colour = TreeNode.RED;
                    parent.colour = TreeNode.BLACK;
                    return;
                } else {
                    //若全为黑色
                    brother.colour = TreeNode.RED;
                    current = parent;
                    continue;
                }

                if (grandParent == null) {
                    rootLeftRotate();
                } else {
                    leftRotate(parent, grandParent);
                }
            } else {
                TreeNode<E> brother = (TreeNode<E>) parent.left;

                //若N的兄弟结点为红色，先进行旋转
                if (brother.colour == TreeNode.RED) {
                    path.push(brother);
                    parent.colour = TreeNode.RED;
                    brother.colour = TreeNode.BLACK;
                    if (grandParent == null) {
                        rootRightRotate();
                    } else {
                        rightRotate(parent, grandParent);
                    }
                    brother = (TreeNode<E>) parent.left;
                    grandParent = path.peek();
                }

                if (brother.left != null
                        && ((TreeNode<E>) brother.left).colour == TreeNode.RED) {
                    //若brother的左子树为红色
                    ((TreeNode) brother.left).colour = TreeNode.BLACK;
                    brother.colour = parent.colour;
                    parent.colour = TreeNode.BLACK;
                    path.clear();
                } else if (brother.right != null
                        && ((TreeNode) brother.right).colour == TreeNode.RED) {
                    //若brother的右子树为红色
                    ((TreeNode) brother.right).colour = parent.colour;
                    parent.colour = TreeNode.BLACK;
                    leftRotate(brother, parent);
                    path.clear();
                } else if (parent.colour == TreeNode.RED) {
                    //若parent为红色
                    brother.colour = TreeNode.RED;
                    parent.colour = TreeNode.BLACK;
                    return;
                } else {
                    //若全为黑色
                    brother.colour = TreeNode.RED;
                    current = parent;
                    continue;
                }

                if (grandParent == null) {
                    rootRightRotate();
                } else {
                    rightRotate(parent, grandParent);
                }
            }
        }
    }

    //进行插入结点的自平衡操作
    private void insertAmendment(Stack<TreeNode<E>> path) {
        TreeNode<E> current = path.pop();
        TreeNode<E> parent = path.pop();

        //进行循环的自平衡操作
        while (parent.colour == TreeNode.RED) {
            //因为如果root必然为黑色，所以stack必然不为空
            if (parent == path.peek().left) {
                TreeNode<E> uncle = (TreeNode<E>) path.peek().right;
                if (uncle != null && uncle.colour == TreeNode.RED) {
                    path.peek().colour = TreeNode.RED;
                    parent.colour = TreeNode.BLACK;
                    uncle.colour = TreeNode.BLACK;
                    current = path.pop();
                    if (path.empty()) {
                        break;
                    }
                    parent = path.pop();
                    continue;
                } else if (current == parent.right) {
                    leftRotate(parent, path.peek());
                } else {
                    current = parent;
                }

                parent = path.pop();
                current.colour = TreeNode.BLACK;
                parent.colour = TreeNode.RED;
                if (path.empty()) {
                    rootRightRotate();
                } else {
                    rightRotate(parent, path.peek());
                }
                break;
            } else {
                //与上面的情况为对称关系，只是把左右节点调换一下
                TreeNode<E> uncle = (TreeNode<E>) path.peek().left;
                if (uncle != null && uncle.colour == TreeNode.RED) {
                    path.peek().colour = TreeNode.RED;
                    parent.colour = TreeNode.BLACK;
                    uncle.colour = TreeNode.BLACK;
                    current = path.pop();
                    if (path.empty()) {
                        break;
                    }
                    parent = path.pop();
                    continue;
                } else if (current == parent.left) {
                    rightRotate(parent, path.peek());
                } else {
                    current = parent;
                }

                parent = path.pop();
                current.colour = TreeNode.BLACK;
                parent.colour = TreeNode.RED;
                if (path.empty()) {
                    rootLeftRotate();
                } else {
                    leftRotate(parent, path.peek());
                }
                break;
            }
        }

        ((TreeNode<E>) root).colour = TreeNode.BLACK;
    }

    //左旋转
    private void leftRotate(Bintree.TreeNode<E> parent,
                            Bintree.TreeNode<E> grandParent) {
        if (grandParent.left == parent) {
            grandParent.left = parent.right;
            parent.right = parent.right.left;
            grandParent.left.left = parent;
        } else {
            grandParent.right = parent.right;
            parent.right = parent.right.left;
            grandParent.right.left = parent;
        }

    }

    //右旋转
    private void rightRotate(Bintree.TreeNode<E> parent,
                             Bintree.TreeNode<E> grandParent) {
        if (grandParent.left == parent) {
            grandParent.left = parent.left;
            parent.left = parent.left.right;
            grandParent.left.right = parent;
        } else {
            grandParent.right = parent.left;
            parent.left = parent.left.right;
            grandParent.right.right = parent;
        }

    }

    //根部左旋转
    private void rootLeftRotate() {
        TreeNode<E> temp = (TreeNode<E>) root;
        root = root.right;
        temp.right = root.left;
        root.left = temp;
    }

    //根部右旋转
    private void rootRightRotate() {
        TreeNode<E> temp = (TreeNode<E>) root;
        root = root.left;
        temp.left = root.right;
        root.right = temp;
    }

    protected class TreeNode<E extends Comparable<E>> extends Bintree.TreeNode<E> {
        int colour;
        static final int RED = 0;
        static final int BLACK = 1;

        public TreeNode(E element, int colour) {
            super(element);
            this.colour = colour;
        }
    }

}