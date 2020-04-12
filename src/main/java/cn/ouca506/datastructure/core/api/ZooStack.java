package cn.ouca506.datastructure.core.api;

/**
 * @author windmill666
 * @date 2020/4/3 20:21
 */

public interface ZooStack {

    void push(String t);

    String pop();

    String peek();

    boolean isEmpty();

    boolean isFull();
}
