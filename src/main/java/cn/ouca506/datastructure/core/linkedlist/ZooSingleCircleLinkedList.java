package cn.ouca506.datastructure.core.linkedlist;

import cn.ouca506.datastructure.core.api.ZooCircleLinkedList;

import java.util.Objects;

/**
 * 单向环形链表
 * @author windmill666
 * @date 2020/4/1 22:41
 */

public class ZooSingleCircleLinkedList<T> implements ZooCircleLinkedList<T> {

    public class Node {
        private T value;
        private Node next;
        public Node(T value, Node next) {
            this.value = value;
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
    private int size;

    public Node getHead() {
        return head;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            this.head = new Node(value, null);
            this.head.next = head;
            size++;
            return;
        }
        Node cur = this.head;
        while (cur.next != head) {
            cur = cur.next;
        }
        cur.next = new Node(value, head);
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
                size--;
                return;
            }
            Node cur = this.head;
            while (cur.next != head) {
                cur = cur.next;
            }
            cur.next = head.next;
            this.head = head.next;
            size--;
            return;
        }
        Node dummy = new Node(null, head);
        Node cur = this.head;
        while (dummy.value == null || dummy.next != head) {
            if (Objects.deepEquals(cur.value, value)) {
                dummy.next = cur.next;
                size--;
                break;
            }
            dummy = dummy.next;
            cur = cur.next;
        }
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

}
