import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayQueue.
 *
 * @author Rakesh Gorrepati
 * @version 1.0
 * @userid rgorrepati2
 * @GTID 903254051
 *
 * Collaborators: N/A
 *
 * Resources: SaiKrishna Slides, Reality Checks, Recitation
 */
public class ArrayQueue<T> {

    /*
     * The initial capacity of the ArrayQueue.
     *
     * DO NOT MODIFY THIS VARIABLE.
     */
    public static final int INITIAL_CAPACITY = 9;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int front;
    private int size;

    /**
     * Constructs a new ArrayQueue with a backing array with capacity
     * INITIAL_CAPACITY.
     */
    public ArrayQueue() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    /**
     * Adds the data to the back of the queue.
     * Remember that the queue MUST behave circularly.
     *
     * If sufficient space is not available in the backing array, resize it to
     * double the current length. When resizing, copy elements to the
     * beginning of the new array. Do not forget to reset front to index 0 of
     * the new array.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the queue
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Unable to Insert Empty data");
        } else if (size == backingArray.length) {
            int x = backingArray.length;
            int capacity = x * 2;
            T[] current = (T[]) new Object[capacity];
            for (int i = front; i < size + front; i++) {
                current[i - front] = backingArray[i % size];
            }
            backingArray = current;
            front = 0;
            int back = (front + size) % backingArray.length;
            current[back] = data;
            size++;
        } else {
            int back = (front + size) % backingArray.length;
            backingArray[back] = data;
            size++;
        }
    }

    /**
     * Removes and returns the data from the front of the queue.
     *
     * Do not shrink the backing array.
     *
     * Replace any spots that you dequeue from with null.
     *
     * If the queue becomes empty as a result of this call, do not reset
     * front to 0.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("The queue is empty");
        } else if (size == 1) {
            T removedData = backingArray[front];
            backingArray[front] = null;
            size--;
            return removedData;
        } else {
            T removedData = backingArray[front];
            backingArray[front] = null;
            this.front = (this.front + 1) % backingArray.length;
            size--;
            return removedData;
        }

    }

    /**
     * Returns the data from the front of the queue without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("The queue is empty");
        } else {
            return backingArray[front];
        }
    }

    /**
     * Returns the backing array of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the queue
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the queue
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
