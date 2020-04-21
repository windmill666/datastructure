package cn.ouca506.datastructure.core.tree;

import lombok.Getter;

import java.util.Objects;

/**
 * @author windmill666
 * @date 2020/4/13 17:26
 */

public class ZooBinaryTree<T> {

    @Getter
    private Node<T> root;

    @Getter
    private T[] arr;

    public ZooBinaryTree(int id, T value) {
        this.root = new Node<>(id, value);
    }

    /**
     * 顺序存储二叉树
     * - 顺序二叉树通常只考虑完全二叉树
     * - 第n个元素的左子节点为2*n+1
     * - 第n个元素的右子节点为2*n+2
     * - 第n个元素的父节点为(n-1)/2
     * @param arr 一维数组
     */
    public ZooBinaryTree(T[] arr) {
        this.arr = arr;
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

    public void prePrintOrder(int index) {
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空");
        }
        System.out.print(arr[index] + "  ");
        if (index * 2 + 1 < arr.length) {
            prePrintOrder(index * 2 + 1);
        }
        if (index * 2 + 2 < arr.length) {
            prePrintOrder(index * 2 + 2);
        }
    }

    public void infixPrintOrder(int index) {
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空");
        }
        if (index * 2 + 1 < arr.length) {
            infixPrintOrder(index * 2 + 1);
        }
        System.out.print(arr[index] + "  ");
        if (index * 2 + 2 < arr.length) {
            infixPrintOrder(index * 2 + 2);
        }
    }

    public void postPrintOrder(int index) {
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空");
        }
        if (index * 2 + 1 < arr.length) {
            postPrintOrder(index * 2 + 1);
        }
        if (index * 2 + 2 < arr.length) {
            postPrintOrder(index * 2 + 2);
        }
        System.out.print(arr[index] + "  ");
    }

    public static class Node<T> {
        private int id;
        private T value;
        private Node<T> leftNode;
        private Node<T> rightNode;

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
