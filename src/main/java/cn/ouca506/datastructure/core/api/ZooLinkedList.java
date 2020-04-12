package cn.ouca506.datastructure.core.api;

/**
 * @author windmill666
 * @date 2020/4/1 22:37
 */

public interface ZooLinkedList<T> {

    int getSize();

    void addFirst(T value);

    void addLast(T value);

    void add(T value);

    void add(T value, int index);

    void removeFirst();

    void removeLast();

    void remove();

    void remove(int index);

    void remove(T value);

    void update(T value, int index);

    boolean contains(T value);

    void showLinkedList();

    void reverse();
}
