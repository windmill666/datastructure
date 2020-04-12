package cn.ouca506.datastructure.core.api;

/**
 * @author windmill666
 * @date 2020/4/3 20:18
 */

public interface ZooCircleLinkedList<T> {

    int getSize();

    void add(T value);

    void remove(T value);

    void showCircleLinkedList();
}
