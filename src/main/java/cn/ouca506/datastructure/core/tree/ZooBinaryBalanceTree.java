package cn.ouca506.datastructure.core.tree;

import lombok.Getter;

/**
 * @author windmill666
 * @date 2020/4/21 8:12
 */

public class ZooBinaryBalanceTree {
    @Getter
    private Node root;

    @Getter
    private int[] arr;

    public ZooBinaryBalanceTree(int[] arr) {
        this.arr = arr;
        for (int value : arr) {
            add(new Node(value));
        }
    }

    public void add(Node node) {
        if (root == null) {
            this.root = node;
        } else {
            this.root.add(node);
        }
    }

    public void delete(int value) {
        if (root == null) {
            return;
        }
        if (root.value == value) {
            //删除的是根节点
            if (root.leftNode == null && root.rightNode == null) {
                this.root = null;
            } else if (root.leftNode != null && root.rightNode != null) {
                this.root.handle(root);
            } else {
                if (root.leftNode != null) {
                    this.root = root.leftNode;
                } else {
                    this.root = root.rightNode;
                }
            }
            return;
        }
        this.root.delete(value, null);
    }

    public void prePrint() {
        if (this.root != null) {
            this.root.prePrint();
            System.out.println();
        } else {
            System.out.println("空二叉树");
        }
    }

    public void infixPrint() {
        if (this.root != null) {
            this.root.infixPrint();
            System.out.println();
        } else {
            System.out.println("空二叉树");
        }
    }

    public void postPrint() {
        if (this.root != null) {
            this.root.postPrint();
            System.out.println();
        } else {
            System.out.println("空二叉树");
        }
    }

    public Integer binarySearch(int value) {
        if (this.root != null) {
            Node node = this.root.binarySearch(root, value);
            return node == null ? null : node.value;
        } else {
            System.out.println("空二叉树");
        }
        return null;
    }


    public static class Node {
        private int value;
        private Node leftNode;
        private Node rightNode;

        public Node(int value) {
            this.value = value;
        }

        public Node getLeftNode() {
            return leftNode;
        }

        public void setLeftNode(int value) {
            this.leftNode = new Node(value);
        }

        public Node getRightNode() {
            return rightNode;
        }

        public void setRightNode(int value) {
            this.rightNode = new Node(value);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }

        private void add(Node node) {
            if (node == null) {
                return;
            }
            if (node.value < this.value) {
                if (this.leftNode == null) {
                    this.leftNode = node;
                } else {
                    this.leftNode.add(node);
                }
            } else {
                if (this.rightNode == null) {
                    this.rightNode = node;
                } else {
                    this.rightNode.add(node);
                }
            }
            if (rightHeight() - leftHeight() > 1) {
                if (rightNode != null && rightNode.rightHeight() < rightNode.leftHeight()) {
                    //它的右子树比左子树低，要先对它进行右旋
                    rightNode.rightRotate();
                }
                leftRotate();
            } else if (leftHeight() - rightHeight() > 1) {
                if (leftNode != null && leftNode.rightHeight() > leftNode.leftHeight()) {
                    //它的右子树比左子树高，要先对它进行左旋
                    leftNode.leftRotate();
                }
                rightRotate();
            }
        }

        private void delete(int value, Node pre) {
            if (value == this.value) {
                if (pre == null) {
                    System.out.println("删除的是根节点，此处不会运行，已在上面代码中处理，此处为了方便理解");
                } else if (this.leftNode == null && this.rightNode == null) {
                    //父节点不为空，删除的是叶子节点
                    if (pre.leftNode == this) {
                        pre.leftNode = null;
                    } else if (pre.rightNode == this) {
                        pre.rightNode = null;
                    }
                } else if (this.leftNode != null && this.rightNode != null) {
                    //父节点不为空，删除的节点有两颗子树
                    handle(this);
                } else {
                    //父节点不为空，删除的节点只有一颗子树
                    if (this.leftNode != null) {
                        if (pre.leftNode == this) {
                            pre.leftNode = this.leftNode;
                        } else if (pre.rightNode == this) {
                            pre.rightNode = this.leftNode;
                        }
                    } else {
                        if (pre.leftNode == this) {
                            pre.leftNode = this.rightNode;
                        } else if (pre.rightNode == this) {
                            pre.rightNode = this.rightNode;
                        }
                    }
                }
            } else if (value < this.value) {
                if (this.leftNode != null) {
                    this.leftNode.delete(value, this);
                }
            } else {
                if (this.rightNode != null) {
                    this.rightNode.delete(value, this);
                }
            }
        }

        private void handle(Node node) {
            Node tempNode = node.rightNode;
            //从右子树中找出最小值
            if (tempNode.leftNode == null) {
                node.value = tempNode.value;
                node.rightNode = tempNode.rightNode;
            } else {
                Node temPre = tempNode;
                while (tempNode.leftNode != null) {
                    if (temPre != tempNode) {
                        temPre = temPre.leftNode;
                    }
                    tempNode = tempNode.leftNode;
                }
                //将右子树中最小值赋值给当前节点
                node.value = tempNode.value;
                //删除tempNode，tempNode必没有左子树，分两种情况：1.tempNode是叶子节点；2.tempNode有右子树
                if (tempNode.rightNode == null) {
                    temPre.leftNode = null;
                } else {
                    temPre.leftNode = tempNode.rightNode;
                }
            }
        }

        private void prePrint() {
            System.out.print(this.value + " ");
            if (this.leftNode != null) {
                this.leftNode.prePrint();
            }
            if (this.rightNode != null) {
                this.rightNode.prePrint();
            }
        }

        private void infixPrint() {
            if (this.leftNode != null) {
                this.leftNode.infixPrint();
            }
            System.out.print(this.value + " ");
            if (this.rightNode != null) {
                this.rightNode.infixPrint();
            }
        }

        private void postPrint() {
            if (this.leftNode != null) {
                this.leftNode.postPrint();
            }
            if (this.rightNode != null) {
                this.rightNode.postPrint();
            }
            System.out.print(this.value + " ");
        }

        public int leftHeight() {
            return leftNode == null ? 0 : leftNode.height();
        }

        public int rightHeight() {
            return rightNode == null ? 0 : rightNode.height();
        }

        public int height() {
            return Math.max(leftNode == null ? 0 : leftNode.height(), rightNode == null ? 0 : rightNode.height()) + 1;
        }

        //左旋
        private void leftRotate() {
            //创建新的节点，以当前根节点的值
            Node newNode = new Node(value);
            //把新的节点的左子树设置成过去节点的左子树
            newNode.leftNode = leftNode;
            //把新的节点的右子树设置成过去节点的右子树的左子树
            newNode.rightNode = rightNode.leftNode;
            //把过去节点的值替换成右子节点的值
            value = rightNode.value;
            //把过去节点的右子树设置成当前节点的右子树的右子树
            rightNode = rightNode.rightNode;
            //把过去节点的左子树设置成新节点
            leftNode = newNode;
        }

        //右旋
        private void rightRotate() {
            //创建新的节点，以当前根节点的值
            Node newNode = new Node(value);
            //把新的节点的右子树设置成过去节点的右子树
            newNode.rightNode = rightNode;
            //把新的节点的左子树设置成过去节点的左子树的右子树
            newNode.leftNode = leftNode.rightNode;
            //把过去节点的值替换成左子节点的值
            value = leftNode.value;
            //把过去节点的左子树设置成当前节点的左子树的左子树
            leftNode = leftNode.leftNode;
            //把过去节点的右子树设置成新节点
            rightNode = newNode;
        }

        private Node binarySearch(Node node, int value) {
            while (node != null) {
                System.out.println("二分查找" + node.value);
                if (value == node.value) {
                    return node;
                } else if (value < node.value) {
                    node = node.leftNode;
                } else {
                    node = node.rightNode;
                }
            }
            return null;
        }
    }
}
