package cn.ouca506.datastructure.core;

import cn.ouca506.datastructure.core.api.ZooStack;

/**
 * @author windmill666
 * @date 2020/4/2 11:32
 */

public class ZooStringArrayStack implements ZooStack {

    private String[] stack;
    private int size;
    private int stackTop;

    public ZooStringArrayStack(int size) {
        this.size = size;
        this.stack = new String[size];
        this.stackTop = -1;
    }

    @Override
    public void push(String t) {
        if (isFull()) {
            System.out.println("栈已满");
            return;
        }
        stackTop++;
        stack[stackTop] = t;
    }

    @Override
    public String pop() {
        if (isEmpty()) {
            System.out.println("栈已空");
            return null;
        }
        String res = stack[stackTop];
        stackTop--;
        return res;
    }

    @Override
    public String peek() {
        if (isEmpty()) {
            System.out.println("栈已空");
            return null;
        }
        return stack[stackTop];
    }

    @Override
    public boolean isEmpty() {
        return stackTop == -1;
    }

    @Override
    public boolean isFull() {
        return stackTop == size - 1;
    }

    public static int symbolPropPriority(String str) {
        if (str.equals("*") || str.equals("/")) {
            return 1;
        } else if (str.equals("+") || str.equals("-")) {
            return 0;
        }
        return -1;
    }

    public boolean isOneElement() {
        return stackTop == 0;
    }
}
