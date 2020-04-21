package cn.ouca506.datastructure.core.stack;

import cn.ouca506.datastructure.core.linkedlist.ZooSingleLinkedList;
import cn.ouca506.datastructure.core.api.ZooStack;

/**
 * @author windmill666
 * @date 2020/4/6 8:20
 */

public class ZooStringLinkedStack implements ZooStack {

    private ZooSingleLinkedList<String> stack;
    private int size;
    private int stackTop;

    public ZooStringLinkedStack(int size) {
        this.size = size;
        this.stackTop = -1;
        this.stack = new ZooSingleLinkedList<>();
    }

    @Override
    public void push(String t) {
        if (isFull()) {
            return;
        }
        stack.add(t);
        stackTop++;
    }

    @Override
    public String pop() {
        if (isEmpty()) {
            return null;
        }
        String res = stack.get(stackTop);
        stack.removeLast();
        stackTop--;
        return res;
    }

    @Override
    public String peek() {
        if (isEmpty()) {
            System.out.println("栈已空");
            return null;
        }
        return stack.get(stackTop);
    }

    @Override
    public boolean isEmpty() {
        return stackTop == -1;
    }

    @Override
    public boolean isFull() {
        return stackTop == size - 1;
    }
}
