import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
 *
 * @author Rakesh Gorrepati
 * @version 1.0
 * @userid rgorrepati2
 * @GTID 903254051
 *
 * Collaborators: N/A
 *
 * Resources: Video Demo, HW3, Notes
 */
public class AVL<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Can't insert null data");
        } else {
            for (T val : data) {
                add(val);
            }
        }

    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be added");
        } else {
            root = helperAdd(root, data);
        }

    }

    /**
     * My helper method for adding
     * @param newN node to be added
     * @param data data to be added
     * @return the node after being added
     */
    private AVLNode<T> helperAdd(AVLNode<T> newN, T data) {
        if (newN == null) {
            size++;
            return new AVLNode<>(data);
        }
        int curr = data.compareTo(newN.getData());
        if (curr < 0) {
            newN.setLeft(helperAdd(newN.getLeft(), data));
        } else if (curr > 0) {
            newN.setRight(helperAdd(newN.getRight(), data));
        } else {
            return newN;
        }
        helperCalculate(newN);
        return helperBalance(newN);
    }

    /**
     * My helper method for balancing
     * @param newN the node to be balanced
     * @return the balanced node
     */
    private AVLNode<T> helperBalance(AVLNode<T> newN) {
        if (newN.getBalanceFactor() < -1) {
            if (newN.getRight().getBalanceFactor() > 0) {
                newN.setRight(helperRotateRight(newN.getRight()));
            }
            newN = helperRotateLeft(newN);
        } else if (newN.getBalanceFactor() > 1) {
            if (newN.getLeft().getBalanceFactor() < 0) {
                newN.setLeft(helperRotateLeft(newN.getLeft()));
            }
            newN = helperRotateRight(newN);
        }
        return newN;
    }

    /**
     * My helper method for calculating the BF
     * @param newN the node to calculate BF
     */
    private void helperCalculate(AVLNode<T> newN) {
        int heightLeft = height(newN.getLeft());
        int heightRight = height(newN.getRight());
        newN.setHeight(Math.max(heightLeft, heightRight) + 1);
        newN.setBalanceFactor(heightLeft - heightRight);
    }

    /**
     * My helper method for rotating left
     * @param newN the node to rotate
     * @return node after rotating left
     */
    private AVLNode<T> helperRotateLeft(AVLNode<T> newN) {
        AVLNode<T> curr = newN.getRight();
        newN.setRight(curr.getLeft());
        curr.setLeft(newN);
        helperCalculate(newN);
        helperCalculate(curr);
        return curr;
    }

    /**
     * My helper method for rotating right
     * @param newN the node to rotate
     * @return node after rotating right
     */
    private AVLNode<T> helperRotateRight(AVLNode<T> newN) {
        AVLNode<T> curr = newN.getLeft();
        newN.setLeft(curr.getRight());
        curr.setRight(newN);
        helperCalculate(newN);
        helperCalculate(curr);
        return curr;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data, NOT predecessor. As a reminder, rotations can occur
     * after removing the successor node. Do NOT use the provided public 
     * predecessor method to remove a 2-child node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Can't remove null data");
        }
        if (size == 0) {
            throw new NoSuchElementException("AVL is empty can't remove");
        }
        AVLNode<T> removedNode = new AVLNode<>(null);
        root = helperRemove(root, removedNode, data);
        return removedNode.getData();
    }

    /**
     * My helper method to remove
     * @param curr the current node
     * @param removedN the node to be remove date
     * @param data the data to remove
     * @return the nde after removing the data
     */
    private AVLNode<T> helperRemove(AVLNode<T> curr, AVLNode<T> removedN, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Can't remove null data");
        }
        int dummy = data.compareTo(curr.getData());
        if (dummy > 0) {
            curr.setRight(helperRemove(curr.getRight(), removedN, data));
        } else if (dummy < 0) {
            curr.setLeft(helperRemove(curr.getLeft(), removedN, data));
        } else {
            size--;
            removedN.setData(curr.getData());
            if (curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            } else {
                AVLNode<T> newN = new AVLNode<>(null);
                curr.setRight(helperSuccessor(curr.getRight(), newN));
                curr.setData(newN.getData());
            }
        }
        helperCalculate(curr);
        return helperBalance(curr);
    }

    /**
     * My helper method to find successor
     * @param curr the node to be removed
     * @param newN the successor
     * @return the successor node
     */
    private AVLNode<T> helperSuccessor(AVLNode<T> curr, AVLNode<T> newN) {
        if (curr.getLeft() == null) {
            newN.setData(curr.getData());
            return curr.getRight();
        }
        curr.setLeft(helperSuccessor(curr.getLeft(), newN));
        helperCalculate(curr);
        return helperBalance(curr);
    }


    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Can't get null");
        }
        return helperGet(root, data);
    }

    /**
     * My helper for get
     * @param curr the node to get
     * @param data the data in the node to get
     * @return the data
     */
    private T helperGet(AVLNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Data isn't present");
        }
        if (data.compareTo(curr.getData()) > 0) {
            return helperGet(curr.getRight(), data);
        } else if (curr.getData().equals(data)) {
            return curr.getData();
        } else {
            return helperGet(curr.getLeft(), data);
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Can't check null data");
        }
        try {
            get(data);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns the height of the root of the tree. Do NOT compute the height
     * recursively. This method should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        int height = -1;
        if (root != null) {
            height = height(root);
        }
        return height;
    }

    /**
     * My helper method to find the node height
     * @param newN the node
     * @return the height of the node
     */
    private int height(AVLNode<T> newN) {

        int heightLeft = 0;
        int heightRight = 0;
        if (newN == null) {
            return -1;
        }
        if (newN.getLeft() == null) {
            heightRight = -1;
        }
        if (newN.getRight() == null) {
            heightLeft = -1;
        }
        if (newN.getLeft() != null) {
            heightLeft = newN.getLeft().getHeight();
        }
        if (newN.getRight() != null) {
            heightRight = newN.getRight().getHeight();
        }

        return Math.max(heightLeft, heightRight) + 1;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * In your BST homework, you worked with the concept of the predecessor, the
     * largest data that is smaller than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 3 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the deepest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     * 3: If the data passed in is the minimum data in the tree, return null.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *     76
     *   /    \
     * 34      90
     *  \    /
     *  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        if (!contains(data)) {
            throw new NoSuchElementException("The data is not in the tree");
        }
        return null;
    }

    /**
     * Finds and retrieves the k-smallest elements from the AVL in sorted order,
     * least to greatest.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *              50
     *            /    \
     *         25      75
     *        /  \     / \
     *      13   37  70  80
     *    /  \    \      \
     *   12  15    40    85
     *  /
     * 10
     * kSmallest(0) should return the list []
     * kSmallest(5) should return the list [10, 12, 13, 15, 25].
     * kSmallest(3) should return the list [10, 12, 13].
     *
     * @param k the number of smallest elements to return
     * @return sorted list consisting of the k smallest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > n, the number
     *                                            of data in the AVL
     */
    public List<T> kSmallest(int k) {
        if (k < 0 || k > size) {
            throw new IllegalArgumentException("K is less than 0 or greater than the size");
        }
        return null;
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
