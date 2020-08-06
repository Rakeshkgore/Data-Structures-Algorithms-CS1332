import java.util.NoSuchElementException;
/**
 * Your implementation of a non-circular SinglyLinkedList with a tail pointer.
 *
 * @author Rakesh Gorrepati
 * @version 1.0
 * @userid rgorrepati2
 * @GTID 903254051
 * Utilized SaiKrishna slides psuedocode and coding demo by Prof HB.
 */
public class SinglyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private SinglyLinkedListNode<T> head;
    private SinglyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index to add the new element
     * @param data  the data to add
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        SinglyLinkedListNode<T> newCurrent = new SinglyLinkedListNode<>(data);
        SinglyLinkedListNode<T> current = head;
        if (data == null) {
            throw new IllegalArgumentException("Unable to Insert Empty data");
        } else if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Can't insert element "
                    + "at index greater than " + size + " or at negative index.");
        } else if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            newCurrent.setNext(current.getNext());
            current.setNext(newCurrent);
            size++;

        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Unable to Insert Empty data");
        } else if (size == 0) {
            head = new SinglyLinkedListNode<>(data, null);
            tail = head;
            size++;
        } else {
            SinglyLinkedListNode<T> current = new SinglyLinkedListNode<>(data);
            current.setNext(head);
            head = current;
            size++;
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Unable to Insert Empty data");
        } else if (size == 0) {
            addToFront(data);
        } else {
            SinglyLinkedListNode<T> current = new SinglyLinkedListNode<>(data);
            tail.setNext(current);
            tail = current;
            size++;
        }
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other
     * cases.
     *
     * @param index the index of the element to remove
     * @return the data that was removed
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Can't insert element "
                    + "at index greater than " + size + " or at negative index.");
        } else if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else {
            SinglyLinkedListNode<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            SinglyLinkedListNode<T> oldCurrent = current.getNext();
            current.setNext(current.getNext().getNext());
            size--;
            return oldCurrent.getData();
        }
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        SinglyLinkedListNode<T> current = head;
        if (size == 0) {
            throw new NoSuchElementException("List is Empty");
        } else if (size == 1) {
            head = null;
            tail = null;
            size--;
            return current.getData();
        } else {
            head = head.getNext();
            size--;
            return current.getData();
        }
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        SinglyLinkedListNode<T> current = head;
        if (size == 0) {
            throw new NoSuchElementException("List is Empty");
        } else if (size == 1) {
            return removeFromFront();
        } else {
            for (int i = 0; i < size - 2; i++) {
                current = current.getNext();
            }
            current.setNext(null);
            SinglyLinkedListNode<T> oldCurrent = tail;
            tail = current;
            size--;
            return oldCurrent.getData();

        }
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Can't insert element "
                    + "at index greater than " + size + " or at negative index.");
        } else if (index == 0) {
            return head.getData();
        } else if (index == size) {
            return tail.getData();
        } else {
            SinglyLinkedListNode<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            return current.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        if (size != 0) {

            head = null;
            tail = null;
            size = 0;

        }

    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        SinglyLinkedListNode<T> temp = head;
        int count = 0;
        boolean p = false;
        if (data == null) {
            throw new IllegalArgumentException("Unable to Insert Empty data");
        } else if (temp != null) {
            while (temp != null) {
                if (temp.getData() == data) {

                    return removeAtIndex(count);

                }
                temp = temp.getNext();
                count++;
                if (count > size && (temp.getData() != data)) {
                    throw new NoSuchElementException("List is Empty");
                }
            }

        }

        return data;
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        Object[] arr = new Object[size];
        SinglyLinkedListNode<T> current = head;
        for (int i = 0; i < size; i++) {
            arr[i] = current.getData();
            current = current.getNext();
        }
        return (T[]) arr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public SinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public SinglyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
