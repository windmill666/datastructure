package cn.ouca506.datastructure.core.linkedlist;

import cn.ouca506.datastructure.core.api.ZooLinkedList;

import java.util.Objects;

/**
 * 双向链表
 * @author windmill666
 * @date 2020/4/1 22:46
 */

public class ZooRoundLinkedList<T> implements ZooLinkedList<T> {

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
    public void addFirst(T value) {
        this.add(value, 0);
    }

    @Override
    public void addLast(T value) {
        this.add(value, size);
    }

    public void addTail(T value) {
        this.addRe(value, 0);
    }

    @Override
    public void add(T value) {
        this.addLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index is" + index + "：out of bound");
        }
        if (index == 0) {
            if (size == 0) {
                Node node = new Node(value, null, null);
                this.head = node;
                this.tail = node;
            } else {
                Node node = new Node(value, null, head);
                this.head.pre = node;
                this.head = node;
            }
            this.size++;
            return;
        }
        Node cur = this.head;
        for (int i = 0; i < index - 1; i++) {
            cur = cur.next;
        }
        Node node = new Node(value, cur, cur.next);
        if (index == size) {
            this.tail = node;
            cur.next = node;
        }
        if (index < size) {
            cur.next.pre = node;
            cur.next = node;
        }
        this.size++;
    }

    public void addRe(T value, int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index is" + index + "：out of bound");
        }
        if (index == 0) {
            if (size == 0) {
                Node node = new Node(value, null, null);
                this.head = node;
                this.tail = node;
            } else {
                Node node = new Node(value, tail, null);
                this.tail.next = node;
                this.tail = node;
            }
            this.size++;
            return;
        }
        Node cur = this.tail;
        for (int i = 0; i < index - 1; i++) {
            cur = cur.pre;
        }
        Node node = new Node(value, cur.pre, cur);
        if (index == size) {
            this.head = node;
            cur.pre = node;
        }
        if (index < size) {
            cur.pre.next = node;
            cur.pre = node;
        }
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

    public void removeTail() {
        this.removeRe(0);
    }

    @Override
    public void remove() {
        this.removeLast();
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index is" + index + "：out of bound");
        }
        if (index == 0) {
            if (size == 1) {
                this.head = null;
                this.tail = null;
            } else {
                this.head = head.next;
                head.pre = null;
            }
            this.size--;
            return;
        }
        Node cur = this.head;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        if (index == size - 1) {
            this.tail = cur.pre;
            tail.next = null;
        }
        if (index < size - 1) {
            cur.pre.next = cur.next;
            cur.next.pre = cur.pre;
        }
        this.size--;
    }

    public void removeRe(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index is" + index + "：out of bound");
        }
        if (index == 0) {
            if (size == 1) {
                this.head = null;
                this.tail = null;
            } else {
                this.tail = tail.pre;
                tail.next = null;
            }
            this.size--;
            return;
        }
        Node cur = this.tail;
        for (int i = 0; i < index; i++) {
            cur = cur.pre;
        }
        if (index == size - 1) {
            this.head = cur.next;
            head.pre = null;
        }
        if (index < size - 1) {
            cur.pre.next = cur.next;
            cur.next.pre = cur.pre;
        }
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
        Node thirdParty = new Node(null, null, null);
        Node cur = this.head;
        this.tail = cur;
        while (cur != null) {
            Node next = cur.next;//**用来后移
            cur.next = thirdParty.next;//移花接木
            thirdParty.next = cur;//移花接木
            cur.pre = cur.next;
            cur = next;//**用来后移
        }
        this.head = thirdParty.next;
    }

    public void showLinkedListRe() {
        Node cur = this.tail;
        while (cur != null) {
            System.out.print(cur.value + "\t");
            cur = cur.pre;
        }
        System.out.println();
    }

    public boolean containsRe(T value) {
        Node node = this.tail;
        for (int i = 0; i < size; i++) {
            if (Objects.deepEquals(node.value, value)) {
                return true;
            }
            node = node.pre;
        }
        return false;
    }

    public void removeRe(T value) {
        Node node = this.tail;
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (Objects.deepEquals(node.value, value)) {
                index = i;
                break;
            }
            node = node.pre;
        }
        if (index == -1) {
            System.out.println("链表中不包含将要删除的元素（反向遍历）");
            return;
        }
        remove(index);
    }

    public void updateRe(T value, int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index is " + index + "：无法更新");
        }
        if (tail == null) {
            System.out.println("链表为空，更新失败");
            return;
        }
        Node cur = tail;
        for (int i = 0; i < index; i++) {
            cur = cur.pre;
        }
        cur.value = value;
    }
}
