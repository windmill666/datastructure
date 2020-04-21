package cn.ouca506.datastructure.core.tree;

import lombok.Getter;

import java.util.Objects;
import java.util.Stack;

/**
 * @author windmill666
 * @date 2020/4/13 17:26
 */

public class ZooBinaryThreadTree<T> {

    @Getter
    private Node<T> root;

    private Node<T> pre;

    public ZooBinaryThreadTree(int id, T value) {
        this.root = new Node<>(id, value);
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

    public void prePrintThread() {
        if (this.root != null) {
            Node<T> node = this.root;
            while (node != null) {
                //输出root节点
                System.out.print(node.value + "\t");
                //搜索左子树，并依次输出，直到到达最底层
                while (node.getLeftNodeType() == 0) {
                    node = node.getLeftNode();
                    System.out.print(node.value + "\t");
                }
                //利用线索从左叶子节点跳到下一个节点，其实就是兄弟右叶子节点(父-左-右)
                //还可以再跳到下一个节点时，有可能是当前子树遍历完，这时候跳到当前子树的父节点的右叶子节点，就是兄弟子树
                while (node.getRightNodeType() == 1) {
                    node = node.getRightNode();
                    System.out.print(node.value + "\t");
                }
                //没有线索了(后继节点)怎么办？
                //如果跳到的节点有左叶子节点(有可能等于1：它没有左叶子节点，且前驱节点就是跳之前的那个)
                //node已经输出了
                //如果等于1，就去遍历它的右叶子节点
                if (node.getLeftNodeType() == 0) {
                    node = node.getLeftNode();
                } else {
                    node = node.getRightNode();
                }
                //然后再次进入循环，先输出当前，再遍历左子树，根据后继节点找下一个节点
                //基本上后继节点就是用来跳到右子树的(父-左-右)
                //最后会到达结束节点null
            }
            System.out.println();
        } else {
            System.out.println("空二叉树");
        }
    }

    public void infixPrintThread() {
        if (this.root != null) {
            Node<T> node = this.root;
            while (node != null) {
                //中序遍历比较简单
                //先去找到最左叶子节点，因为是输出的第一个
                while (node.getLeftNodeType() == 0) {
                    node = node.getLeftNode();
                }
                System.out.print(node.value + "\t");
                //然后根据后继节点来跳回到父节点
                //如果父节点没有右节点，但会变成后继节点，再跳回到父父节点
                while (node.getRightNodeType() == 1) {
                    node = node.getRightNode();
                    System.out.print(node.value + "\t");
                }
                //如果父节点没有后继线索了，直接跳到右叶子节点，如果是null，就说明已经遍历完，依旧可以赋值给node
                node = node.getRightNode();
            }
            System.out.println();
        } else {
            System.out.println("空二叉树");
        }
    }

    public void postPrintThread() {
        if (this.root != null) {
            Node<T> node = this.root;
            //后序遍历的时候发现不用parent节点，可以实现后序遍历的逆序输出
            Stack<T> res = new Stack<>();
            while (node != null) {
                res.push(node.value);
                if (node.getRightNodeType() == 0) {
                    node = node.getRightNode();
                } else {
                    //不管此时是前驱节点还是左子节点都执行
                    //此时右子节点是后继节点或者不存在，都没有使用价值
                    node = node.getLeftNode();
                }
            }
            while (!res.empty()) {
                System.out.print(res.pop() + "\t");
            }
            System.out.println();
        } else {
            System.out.println("空二叉树");
        }
    }

    public T preSearch(T value) {
        if (this.root != null) {
            Node<T> node = this.root.preSearch(value);
            return node == null ? null : node.value;
        } else {
            System.out.println("空二叉树");
        }
        return null;
    }

    public T infixSearch(T value) {
        if (this.root != null) {
            Node<T> node = this.root.infixSearch(value);
            return node == null ? null : node.value;
        } else {
            System.out.println("空二叉树");
        }
        return null;
    }

    public T postSearch(T value) {
        if (this.root != null) {
            Node<T> node = this.root.postSearch(value);
            return node == null ? null : node.value;
        } else {
            System.out.println("空二叉树");
        }
        return null;
    }

    public void preThread(Node<T> node) {
        if (node == null) {
            return;
        }
        //线索化当前节点
        if (node.leftNode == null) {
            node.leftNode = this.pre;
            node.leftNodeType = 1;
        }
        if (pre != null && pre.rightNode == null) {
            this.pre.rightNode = node;
            this.pre.rightNodeType = 1;
        }
        this.pre = node;
        //线索化左子树，之所以加判断是因为上面有修改node的左节点的操作！
        if (node.leftNodeType == 0) {
            preThread(node.leftNode);
        }
        //线索化右子树
        if (node.rightNodeType == 0) {
            preThread(node.rightNode);
        }
    }

    public void infixThread(Node<T> node) {
        if (node == null) {
            return;
        }
        //线索化左子树
        infixThread(node.leftNode);
        //线索化当前节点
        if (node.leftNode == null) {
            node.leftNode = this.pre;
            node.leftNodeType = 1;
        }
        if (pre != null && pre.rightNode == null) {
            this.pre.rightNode = node;
            this.pre.rightNodeType = 1;
        }
        this.pre = node;
        //线索化右子树
        infixThread(node.rightNode);
    }

    public void postThread(Node<T> node) {
        if (node == null) {
            return;
        }
        //线索化左子树
        postThread(node.leftNode);
        //线索化右子树
        postThread(node.rightNode);
        //线索化当前节点
        if (node.leftNode == null) {
            node.leftNode = this.pre;
            node.leftNodeType = 1;
        }
        if (pre != null && pre.rightNode == null) {
            this.pre.rightNode = node;
            this.pre.rightNodeType = 1;
        }
        this.pre = node;
    }

    public static class Node<T> {
        private int id;
        private T value;
        private Node<T> leftNode;
        private Node<T> rightNode;
        private Node<T> parentNode;
        @Getter
        private int leftNodeType;
        @Getter
        private int rightNodeType;

        public Node(int id, T value) {
            this.id = id;
            this.value = value;
        }

        public Node<T> getLeftNode() {
            return leftNode;
        }

        public void setLeftNode(int id, T value) {
            this.leftNode = new Node<>(id, value);
        }

        public Node<T> getRightNode() {
            return rightNode;
        }

        public void setRightNode(int id, T value) {
            this.rightNode = new Node<>(id, value);
        }

        public Node<T> getParentNode() {
            return parentNode;
        }

        public void setParentNode(Node<T> parentNode) {
            this.parentNode = parentNode;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "id=" + id +
                    ", value=" + value +
                    '}';
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

        private Node<T> preSearch(T value) {
            System.out.println("preSearch");
            if (Objects.deepEquals(this.value, value)) {
                return this;
            }
            Node<T> node = null;
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

        private Node<T> infixSearch(T value) {
            Node<T> node = null;
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

        private Node<T> postSearch(T value) {
            Node<T> node = null;
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
