
/**
 * Created by Ian_Leyden on 4/24/2015.
 */
public class LQueue<T> extends java.lang.Object implements Queue<T>{
    private Node head;
    private int count = 0;

    /**
     * Default constructor initializes an empty queue.
     */
    public LQueue(){
        head = null;
    }

    /**
     * Offer the item into this Queue at the rear end.
     * @param item
     * @return false if fail to do so, in case the capacity is restricted.
     */
    public void offer(T item){
        if (size() == 0) {
            head = new Node(head, item);
        }
        else {
            Node temp = new Node(head, item);
            Node x = head;
            for (int i = 0; i < size(); i++) {
                x = x.next;
            }
            x.next = temp;
        }
        count++;
    }

    /**
     * Remove the item from the Queue at the front end.
     * Since we are not dealing with null data null is the same as empty
     * @return the item that is removed.
     */
    public T poll(){
        if (empty()){
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
     * @return the first item at the front end, or null if this Queue is empty.
     */
    public T peek(){
        if (empty()){
            return null;
        }
        return head.data;
    }

    /**
     *
     * @return the number of the element in the queue.
     */
    public int size(){
        return count;
    }

    /**
     *
     * @return true if this Queue is empty, false otherwise
     */
    public boolean empty(){
        return head == null;
    }

    private class Node {
        Node next;
        T data;

        /**
         * constructor
         * @param next
         * @param data
         */
        public Node(Node next, T data) {
            this.next = next;
            this.data = data;
        }
    }

}
