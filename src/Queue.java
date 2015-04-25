
public interface Queue<T> {
    /**
     * Offer the item into this Queue at the rear end.
     * @param item
     * @return false if fail to do so, in case the capacity is restricted.
     */
    boolean offer(T item);

    /**
     * Remove the item at the front end. This is same as Remove but no exception will be thrown.
     * @return the item that is removed, or null if no item to be removed.
     */
    T poll();

    /**
     * Peek at the first item at the front end.
     * @return the first item at the front end, or null if this Queue is empty.
     */
    T peek();

    /**
     *
     * @return true if this Queue is empty, false otherwise
     */
    boolean empty();

    /**
     *
     * @return the number of the element in the queue.
     */
    int size();
}
