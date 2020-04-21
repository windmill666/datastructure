package cn.ouca506.datastructure.core.tree;

import lombok.Getter;

import java.util.Objects;

/**
 * @author windmill666
 * @date 2020/4/20 15:02
 */

public class ZooBinarySortTree {

    @Getter
    private Node root;

    @Getter
    private int[] arr;

    public ZooBinarySortTree(int[] arr) {
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

    public Integer preSearch(int value) {
        if (this.root != null) {
            Node node = this.root.preSearch(value);
            return node == null ? null : node.value;
        } else {
            System.out.println("空二叉树");
        }
        return null;
    }

    public Integer infixSearch(int value) {
        if (this.root != null) {
            Node node = this.root.infixSearch(value);
            return node == null ? null : node.value;
        } else {
            System.out.println("空二叉树");
        }
        return null;
    }

    public Integer postSearch(int value) {
        if (this.root != null) {
            Node node = this.root.postSearch(value);
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

        private Node preSearch(int value) {
            System.out.println("preSearch");
            if (Objects.deepEquals(this.value, value)) {
                return this;
            }
            Node node = null;
            if (this.leftNode != null) {
                node = this.leftNode.preSearch(value);
            }
            if (node != null) {
                return node;
            }
            if (this.rightNode != null) {
                node = this.rightNode.preSearch(value);
            }
            return node;
        }

        private Node infixSearch(int value) {
            Node node = null;
            if (this.leftNode != null) {
                node = this.leftNode.infixSearch(value);
            }
            if (node != null) {
                return node;
            }
            System.out.println("infixSearch");
            if (Objects.deepEquals(this.value, value)) {
                return this;
            }
            if (this.rightNode != null) {
                node = this.rightNode.infixSearch(value);
            }
            return node;
        }

        private Node postSearch(int value) {
            Node node = null;
            if (this.leftNode != null) {
                node = this.leftNode.postSearch(value);
            }
            if (node != null) {
                return node;
            }
            if (this.rightNode != null) {
                node = this.rightNode.postSearch(value);
            }
            if (node != null) {
                return node;
            }
            System.out.println("postSearch");
            if (Objects.deepEquals(this.value, value)) {
                return this;
            }
            return null;
        }

    }
}
