package cn.ouca506.datastructure.core.queue;

import cn.ouca506.datastructure.core.linkedlist.ZooRoundLinkedList;
import cn.ouca506.datastructure.core.api.ZooQueue;

/**
 * 用链表实现队列
 * @author windmill666
 * @date 2020/4/1 22:43
 */

public class ZooLinkedQueue implements ZooQueue {

    private ZooRoundLinkedList<Integer> queue;
    private int userSize;

    public ZooLinkedQueue(int userSize) {
        this.userSize = userSize;
        this.queue = new ZooRoundLinkedList<>();
    }

    @Override
    public boolean add(int e) {
        return false;
    }

    @Override
    public boolean offer(int e) {
        if (isFull()) {
            System.out.println("队列已满");
            return false;
        } else {
            queue.add(e);
            return true;
        }
    }

    @Override
    public Integer remove() {
        return null;
    }

    @Override
    public Integer poll() {
        if (isEmpty()) {
            System.out.println("队列已空");
            return null;
        } else {
            Integer value = queue.getHead().getValue();
            queue.removeFirst();
            return value;
        }
    }

    @Override
    public Integer element() {
        return null;
    }

    @Override
    public Integer peek() {
        return queue.getHead().getValue();
    }

    @Override
    public void showQueue() {
        if (isEmpty()) {
            System.out.println("队列已空");
            return;
        }
        queue.showLinkedList();
    }

    private boolean isFull() {
        return queue.getSize() == this.userSize;
    }

    private boolean isEmpty() {
        return queue.getSize() == 0;
    }
}
