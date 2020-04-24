package cn.ouca506.datastructure.core.tree;

import lombok.Data;
import lombok.Getter;

/**
 * @author windmill666
 * @date 2020/4/21 8:12
 */

public class ZooRBTree<K extends Comparable<K>, V> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    @Getter
    private RBNode<K,V> root;

    public RBNode<K,V> parentOf(RBNode<K,V> node) {
        if (node != null) {
            return node.parent;
        }
        return null;
    }

    public boolean isRed(RBNode<K,V> node) {
        return node != null && node.color == RED;
    }

    public boolean isBlack(RBNode<K,V> node) {
        return node != null && node.color == BLACK;
    }

    public void setRed(RBNode<K,V> node) {
        if (node != null) {
            node.color = RED;
        }
    }

    public void setBlack(RBNode<K,V> node) {
        if (node != null) {
            node.color = BLACK;
        }
    }

    public void infixPrint() {
        if (this.root == null) {
            System.out.println("空树");
        } else {
            infixPrint(this.root);
        }
    }

    private void infixPrint(RBNode<K,V> node) {
        if (node != null) {
            infixPrint(node.leftNode);
            System.out.println("color：" + (node.color ? "RED" : "BLACK") + ", key: " + node.key + ", value: " + node.value);
            infixPrint(node.rightNode);
        }
    }

    /**
     * 左旋方法
     * 左旋示意图：左旋x节点
     *         P                          P
     *         |                          |
     *         x                          y
     *       /   \          ----->      /   \
     *      lx    y                    x     ry
     *          /   \                /   \
     *        ly     ry            lx    ly
     */
    public void leftRotate(RBNode<K,V> node) {
        if (node == null) {
            return;
        }
        RBNode<K,V> rightNode = node.rightNode;
        RBNode<K,V> rightLeftNode = rightNode.leftNode;
        //绑定 y 与 x的父节点
        if (node.parent != null) {
            rightNode.parent = node.parent;
            if (node == node.parent.leftNode) {
                node.parent.leftNode = rightNode;
            } else {
                node.parent.rightNode = rightNode;
            }
        } else {
            this.root = rightNode;
            rightNode.parent = null;
        }

        //绑定 x 与 ly
        node.rightNode = rightLeftNode;
        if (rightLeftNode != null) {
            rightLeftNode.parent = node;
        }

        //绑定 x 与 y
        node.parent = rightNode;
        rightNode.leftNode = node;
    }

    /**
     * 右旋方法
     * 右旋示意图：右旋x节点
     *         P                          P
     *         |                          |
     *         x                          y
     *       /   \          ----->      /   \
     *      y    rx                    ly    x
     *    /   \                            /   \
     *  ly     ry                         ry    rx
     */
    public void rightRotate(RBNode<K,V> node) {
        if (node == null) {
            return;
        }
        RBNode<K,V> leftNode = node.leftNode;
        RBNode<K,V> leftRightNode = leftNode.rightNode;
        //绑定 y 与 x的父节点
        if (node.parent != null) {
            leftNode.parent = node.parent;
            if (node == node.parent.leftNode) {
                node.parent.leftNode = leftNode;
            } else {
                node.parent.rightNode = leftNode;
            }
        } else {
            this.root = leftNode;
            leftNode.parent = null;
        }

        //绑定 x 与 ry
        node.leftNode = leftRightNode;
        if (leftRightNode != null) {
            leftRightNode.parent = node;
        }

        //绑定 x 与 y
        node.parent = leftNode;
        leftNode.rightNode = node;
    }

    public void insert(K key, V value) {
        RBNode<K,V> node = new RBNode<>(key, value);
        node.setKey(key);
        node.setValue(value);
        insert(node);
    }

    private void insert(RBNode<K,V> node) {
        //第一步：查找当前node的父节点
        RBNode<K, V> parent = null;
        RBNode<K, V> cur = this.root;
        while (cur != null) {
            parent = cur;
            int compare = node.key.compareTo(cur.key);
            if (compare == 0) {
                //node.key = cur.key 覆盖value
                cur.setValue(node.getValue());
                //直接返回，不需要插入操作
                return;
            } else if (compare > 0) {
                //node.key > cur.key 需要到右子树查找
                cur = cur.rightNode;
            } else {
                //node.key < cur.key 需要到左子树查找
                cur = cur.leftNode;
            }
        }

        node.parent = parent;
        if (parent != null) {
            int compare = node.key.compareTo(parent.key);
            if (compare > 0) {
                parent.rightNode = node;
            } else {
                parent.leftNode = node;
            }
        } else {
            this.root = node;
        }

        autoBalance(node);
    }

    private void autoBalance(RBNode<K,V> node) {
        //情景1：插入节点前，红黑树为空树，将根节点染黑
        setBlack(root);
        //情景2：插入节点的key已存在，不需要处理
        //情景3：插入节点的父节点为黑色，平衡未破坏，不需要处理
        RBNode<K, V> parent = parentOf(node);
        RBNode<K, V> grandparent = parentOf(parent);
        if (parent != null && isRed(parent)) {
            //情景4：插入节点的父节点为红色，平衡被破坏
            //注意：如果节点为红色，一定存在爷爷节点，因为根节点不可能是红色
            RBNode<K,V> uncle;
            if (parent == grandparent.leftNode) {
                uncle = grandparent.rightNode;
                if (uncle != null && isRed(uncle)) {
                    //————情景4.1：叔叔节点存在，并且为红色(叔-父双红)
                    //            叔、父染黑，爷染红，再以爷爷节点为当前节点做后续处理
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(grandparent);
                    autoBalance(grandparent);
                } else {
                    //————情景4.2：叔叔节点不存在或者为黑色，父节点为爷爷节点的左子树
                    if (node == parent.leftNode) {
                        //——————————情景4.2.1：插入节点为其父节点的左子节点(LL双红)
                        //父染黑，爷染红，爷右旋
                        setBlack(parent);
                        setRed(grandparent);
                        rightRotate(grandparent);
                        return;//已平衡，需要返回
                    }
                    if (node == parent.rightNode) {
                        //——————————情景4.2.2：插入节点为其父节点的右子节点(LR双红)
                        //父左旋变成LL双红
                        leftRotate(parent);
                        autoBalance(parent);
                    }
                }
            } else {
                uncle = grandparent.leftNode;
                if (uncle != null && isRed(uncle)) {
                    //————情景4.1：叔叔节点存在，并且为红色(叔-父双红)
                    //            叔、父染黑，爷染红，再以爷爷节点为当前节点做后续处理
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(grandparent);
                    autoBalance(grandparent);
                } else {
                    //————情景4.3：叔叔节点不存在或者为黑色，父节点为爷爷节点的右子树
                    if (node == parent.rightNode) {
                        //——————————情景4.3.1：插入节点为其父节点的右子节点(RR双红)
                        //父染黑，爷染红，爷左旋
                        setBlack(parent);
                        setRed(grandparent);
                        leftRotate(grandparent);
                        return;//已平衡，需要返回
                    }
                    if (node == parent.leftNode) {
                        //——————————情景4.3.2：插入节点为其父节点的左子节点(RL双红)
                        //父右旋变成RR双红
                        rightRotate(parent);
                        autoBalance(parent);
                    }

                }

            }

        }

    }

    @Data
    public static class RBNode<K extends Comparable<K>, V> {
        private K key;
        private V value;
        private RBNode<K,V> leftNode;
        private RBNode<K,V> rightNode;
        private RBNode<K,V> parent;
        private boolean color;

        public RBNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.color = RED;
        }

        public RBNode() {

        }
    }
}
