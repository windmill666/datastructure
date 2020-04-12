package cn.ouca506.datastructure.core;

import cn.ouca506.datastructure.core.api.ZooCircleLinkedList;

import java.util.Objects;

/**
 * 双向环形链表
 * @author windmill666
 * @date 2020/4/3 20:19
 */

public class ZooRoundCircleLinkedList<T> implements ZooCircleLinkedList<T> {

    public class Node {
        private T value;
        private Node pre;
        private Node next;

        public Node(T value, Node pre, Node next) {
            this.value = value;
            this.pre = pre;
            this.next = next;
        }

        public T getValue() {
            return value;
        }

        public Node getNext() {
            return next;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public Node getHead() {
        return head;
    }

    public Node getTail() {
        return tail;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            this.head = new Node(value, null, null);
            this.head.next = head;
            this.head.pre = head;
            this.tail = head;
            size++;
            return;
        }
        Node cur = this.head;
        while (cur.next != head) {
            cur = cur.next;
        }
        this.tail = new Node(value, cur, head);
        cur.next = tail;
        head.pre = tail;
        size++;
    }

    @Override
    public void remove(T value) {
        if (size == 0) {
            System.out.println("空，无法删除");
            return;
        }
        if (Objects.deepEquals(head.value, value)) {
            if (size == 1) {
                this.head = null;
                this.tail = null;
                size--;
                return;
            }
            this.tail.next = head.next;
            this.head = head.next;
            this.head.pre = tail;
            size--;
            return;
        }
        Node dummy = new Node(null, null, head);
        Node cur = this.head;
        while (dummy.value == null || dummy.next != head) {
            if (Objects.deepEquals(cur.value, value)) {
                dummy.next = cur.next;
                cur.next.pre = dummy;
                size--;
                break;
            }
            dummy = dummy.next;
            cur = cur.next;
        }
        this.tail = head.pre;
    }

    @Override
    public void showCircleLinkedList() {
        if (size == 0) {
            System.out.println("空");
            return;
        }
        Node cur = this.head;
        System.out.print(cur.value + "\t");
        while (cur.next != head) {
            cur = cur.next;
            System.out.print(cur.value + "\t");
        }
        System.out.println();
    }

    public void showCircleLinkedListRe() {
        if (size == 0) {
            System.out.println("空");
            return;
        }
        Node cur = this.tail;
        System.out.print(cur.value + "\t");
        while (cur.pre != tail) {
            cur = cur.pre;
            System.out.print(cur.value + "\t");
        }
        System.out.println();
    }
}
