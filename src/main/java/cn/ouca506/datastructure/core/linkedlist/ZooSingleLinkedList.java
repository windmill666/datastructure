package cn.ouca506.datastructure.core.linkedlist;

import cn.ouca506.datastructure.core.api.ZooLinkedList;

import java.util.Objects;

/**
 * 单向链表
 * @author windmill666
 * @date 2020/4/1 20:20
 */

public class ZooSingleLinkedList<T> implements ZooLinkedList<T> {

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
    public void addFirst(T value) {
        this.add(value, 0);
    }

    @Override
    public void addLast(T value) {
        this.add(value, size);
    }

    @Override
    public void add(T value) {
        this.addLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index is" + index + "：无法增加");
        }
        Node dummy = new Node(null, head);//虚拟头节点
        Node cur = dummy;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        cur.next = new Node(value, cur.next);
        this.head = dummy.next;//删除虚拟头节点
        this.size++;
    }

    @Override
    public void removeFirst() {
        this.remove(0);
    }

    @Override
    public void removeLast() {
        this.remove(size - 1);
    }

    @Override
    public void remove() {
        this.removeLast();
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index is " + index + "：无法删除");
        }
        Node dummy = new Node(null, head);//虚拟头节点
        Node cur = dummy;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        cur.next = cur.next.next;
        this.head = dummy.next;//删除虚拟头节点
        this.size--;
    }

    @Override
    public void remove(T value) {
        Node node = this.head;
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (Objects.deepEquals(node.value, value)) {
                index = i;
                break;
            }
            node = node.next;
        }
        if (index == -1) {
            System.out.println("链表中不包含将要删除的元素");
            return;
        }
        remove(index);
    }

    @Override
    public void update(T value, int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index is " + index + "：无法更新");
        }
        if (head == null) {
            System.out.println("链表为空，更新失败");
            return;
        }
        Node cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        cur.value = value;
    }

    @Override
    public boolean contains(T value) {
        Node node = this.head;
        for (int i = 0; i < size; i++) {
            if (Objects.deepEquals(node.value, value)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public void showLinkedList() {
        Node cur = this.head;
        while (cur != null) {
            System.out.print(cur.value + "\t");
            cur = cur.next;
        }
        System.out.println();
    }

    @Override
    public void reverse() {
        if (head == null || head.next == null) {
            System.out.println("无需反转");
            return;
        }
        //利用第三方
        Node thirdParty = new Node(null, null);
        Node cur = this.head;
        while (cur != null) {
            Node next = cur.next;//**用来后移
            cur.next = thirdParty.next;//当前节点指向第三方的下一个节点，初始值为null
            thirdParty.next = cur;//第三方下一个节点要指向当前节点，移花接木
            cur = next;//**用来后移
        }
        this.head = thirdParty.next;
    }

    public T get(int index) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.value;
    }
}
