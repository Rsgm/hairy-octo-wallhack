public class LinkedQueue<T> implements Queue<T> {
    private Node head = null;
    private int count = 0;
    int capacity;

    public LinkedQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Offer the item into this Queue at the rear end.
     *
     * @param item
     * @return false if fail to do so, in case the capacity is restricted.
     */
    public boolean offer(T item) {
        if (size() == capacity) {
            return false;
        }
        if (size() == 0) {
            head = new Node(head, item);
        } else {
            Node temp = new Node(head, item);
            Node x = head;
            while (x.next != null) {
                x = x.next;
            }
            x.next = temp;
        }
        count++;
        return true;
    }

    /**
     * Remove the item from the Queue at the front end.
     * Since we are not dealing with null data null is the same as empty
     *
     * @return the item that is removed.
     */
    public T poll() {
        if (empty()) {
            return null;
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
     * @return the first item at the front end, or null if this Queue is empty.
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
     * @return true if this Queue is empty, false otherwise
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
         * @param next
         * @param data
         */
        public Node(Node next, T data) {
            this.next = next;
            this.data = data;
        }
    }

}
