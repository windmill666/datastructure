package cn.ouca506.datastructure.core;

import cn.ouca506.datastructure.core.api.ZooQueue;

/**
 * 用数组实现队列
 * @author windmill666
 * @date 2020/3/31 21:28
 */

public class ZooArrayQueue implements ZooQueue {

    /**
     * 用户定义的队列的大小
     */
    private int userSize;
    /**
     * 队列的实际大小
     */
    private int actualSize;
    /**
     * 指向队列的第一个元素
     */
    private int front;
    /**
     * 指向队列的最后一个元素的后一个元素
     */
    private int rear;
    /**
     * 用来存储队列的数组
     */
    private int[] queue;

    private boolean rearCrossBorder;
    private boolean frontCrossBorder;

    public ZooArrayQueue (int userSize) {
        this.userSize = userSize;
        this.actualSize = userSize + 1;
        this.queue = new int[actualSize];
        this.front = 0;
        this.rear = 0;
        this.rearCrossBorder = false;
        this.frontCrossBorder = false;
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
            if (rear == actualSize - 1) {
                this.rear = 0;
                this.rearCrossBorder = !rearCrossBorder;
            }
            this.queue[rear] = e;
            this.rear++;
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
            if (front == actualSize - 1) {
                this.front = 0;
                this.frontCrossBorder = !frontCrossBorder;
            }
            int res = this.queue[front];
            this.front++;
            return res;
        }
    }

    @Override
    public Integer element() {
        return null;
    }

    @Override
    public Integer peek() {
        return isEmpty() ? null : queue[front];
    }

    @Override
    public void showQueue() {
        if (isEmpty()) {
            System.out.println("队列已空");
            return;
        }
        System.out.print("当前队列内容：");
        if (rearCrossBorder == frontCrossBorder) {
            for (int i = front; i < rear; i++) {
                System.out.printf("%d\t", queue[i]);
            }
            System.out.println();
        } else {
            for (int i = front; i < userSize; i++) {
                System.out.printf("%d\t", queue[i]);
            }
            for (int i = 0; i < rear; i++) {
                System.out.printf("%d\t", queue[i]);
            }
            System.out.println();
        }

    }

    private boolean isFull() {
        return sizeEffective() == this.userSize;
    }

    private boolean isEmpty() {
        return sizeEffective() == 0;
    }

    private int sizeEffective() {
        if (rearCrossBorder == frontCrossBorder) {
            return rear - front;
        } else {
            return rear == front ? userSize : userSize - front + rear;
        }
    }

}
