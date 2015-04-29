package myUtil;

public class LinkedQueue<T> implements Queue<T> {
    private Node head = null;
    private Node last = null;
    private int count = 0;

    public LinkedQueue() {
    }

    /**
     * Offer the item into this myUtil.Queue at the rear end.
     *
     * @param item
     * @return false if fail to do so, in case the capacity is restricted.
     */
    public boolean offer(T item) {
        if (size() == 0) {
            head = new Node(item);
            last = head;
        } else {
            last.next = new Node(item);
            last = last.next;
        }
        count++;
        return true;
    }

    /**
     * Remove the item from the myUtil.Queue at the front end.
     * Since we are not dealing with null data null is the same as empty
     *
     * @return the item that is removed.
     */
    public T poll() {
        if (empty()) {
            return null;
        }

        if (head == last) {
            last = null;
        }

        T temp = head.data;
        head = head.next;
        count--;
        return temp;
    }

    /**
     * Peek at the first item at the front end.
     * Since we are not dealing with null data null is the same as empty
     *
     * @return the first item at the front end, or null if this myUtil.Queue is empty.
     */
    public T peek() {
        if (empty()) {
            return null;
        }
        return head.data;
    }

    /**
     * @return the number of the element in the queue.
     */
    public int size() {
        return count;
    }

    /**
     * @return true if this myUtil.Queue is empty, false otherwise
     */
    public boolean empty() {
        return head == null;
    }

    private class Node {
        Node next;
        T data;

        /**
         * constructor
         *
         * @param data
         */
        public Node(T data) {
            this.data = data;
        }
    }

}
