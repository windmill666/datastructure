package cn.ouca506.datastructure.core.api;

/**
 * @author windmill666
 * @date 2020/3/31 22:20
 */

public interface ZooQueue {

    boolean add(int e);

    /**
     * Inserts the specified element into this queue
     * @return {@code true} if the element was added to this queue, else
     * {@code false}
     * @param e the element to add
     */
    boolean offer(int e);

    Integer remove();

    /**
     * Retrieves and removes the head of this queue,
     * @return the head of this queue, or {@code null} if this queue is empty
     */
    Integer poll();

    Integer element();

    /**
     * Retrieves, but does not remove, the head of this queue,
     * @return the head of this queue, or {@code null} if this queue is empty
     */
    Integer peek();

    void showQueue();

}
