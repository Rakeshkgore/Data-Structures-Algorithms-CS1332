import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.NoSuchElementException;
/**
 * Your implementation of a BST.
 *
 * @author Rakesh Gorrepati
 * @version 1.0
 * @userid rgorrepati2
 * @GTID 903254051
 *
 * Collaborators: N/A
 *
 * Resources: Grant Demo in lecture SaiKrishna Slides, Reality Checks, Recitation
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The elements should be added to the BST in the order in 
     * which they appear in the Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for-loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Can't insert null data into BST");
        } else {
            for (T value : data) {
                add(value);
            }
        }

    }

    /**
     * Adds the data to the tree.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Can't insert null data into BST");
        } else if (size == 0) {
            root = new BSTNode<>(data);
            size++;
        } else {
            root = addHelper(root, data);
        }
    }

    /**
     * My helper method for adding data to the tree with recursion
     * @param newN the node used in the recursive call
     * @param data the data to be added
     * @return node
     */

    private BSTNode<T> addHelper(BSTNode<T> newN, T data) {
        if (newN == null) {
            size++;
            return new BSTNode<T>(data);
        } else if (newN.getData().compareTo(data) > 0) {
            newN.setLeft(addHelper(newN.getLeft(), data));
        } else if (newN.getData().compareTo(data) < 0) {
            newN.setRight(addHelper(newN.getRight(), data));
        }
        return newN;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data. You MUST use recursion to find and remove the
     * predecessor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Can't insert null data into BST");
        } else if (!(contains(data))) {
            throw new NoSuchElementException("Data is not in the tree");
        } else {
            T removedData = get(data);
            root = removeHelper(root, data);
            size--;
            return removedData;
        }

    }
    /**
     * My helper method for removing data from the BST
     * @param newN the node used in the recursive call
     * @param data the data to be removed
     * @return the changed node
     */
    private BSTNode<T> removeHelper(BSTNode<T> newN, T data) {
        if (newN == null) {
            return null;
        } else if (newN.getData().compareTo(data) > 0) {
            newN.setLeft(removeHelper(newN.getLeft(), data));
        }  else if (newN.getData().compareTo(data) < 0) {
            newN.setRight(removeHelper(newN.getRight(), data));
        } else if (newN.getRight() != null && newN.getLeft() != null) {
            newN.setData(predecessorHelper(newN.getLeft()).getData());
            newN.setLeft(removeHelper(newN.getLeft(), newN.getData()));
        } else if (newN.getLeft() == null && newN.getRight() == null) {
            newN = null;
        } else if (newN.getLeft() == null) {
            newN = newN.getRight();
        } else {
            newN = newN.getLeft();
        }
        return newN;
    }

    /**
     * My helper method for finding the predecessor
     * @param point the first node int the call
     * @return the predecessor
     */
    private BSTNode<T> predecessorHelper(BSTNode<T> point) {
        if (point.getRight() == null) {
            return point;
        } else {
            return predecessorHelper(point.getRight());
        }
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Can't insert null data into BST");
        }
        return getHelper(root, data);
    }

    /**
     *
     * @param newN the node used in the recursive call
     * @param data the data needed to be found
     * @return the element that is equal to the data parameter
     */
    private T getHelper(BSTNode<T> newN, T data) {
        if (newN == null) {
            throw new NoSuchElementException("Data isn't in BST");
        } else if (newN.getData().equals(data)) {
            return newN.getData();
        } else if (newN.getData().compareTo(data) > 0) {
            return getHelper(newN.getLeft(), data);
        } else {
            return getHelper(newN.getRight(), data);
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Can't put null data into BST");
        }
        return containsHelper(root, data);
    }

    /**
     *
     * @param newN the node being used in the recursive call
     * @param data the data needed to be found
     * @return false if not found, true if found
     */
    private boolean containsHelper(BSTNode<T> newN, T data) {
        if (newN == null) {
            return false;
        } else if (newN.getData().equals(data)) {
            return true;
        } else if (newN.getData().compareTo(data) > 0) {
            return containsHelper(newN.getLeft(), data);
        } else {
            return containsHelper(newN.getRight(), data);
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> a = new ArrayList<T>();
        preorderHelper(root, a);
        return a;

    }

    /**
     *
     * @param newN the node being using in the recursive call
     * @param a list of data in traversal - preorder
     */
    private void preorderHelper(BSTNode<T> newN, List<T> a) {
        if (newN != null) {
            a.add(newN.getData());
            preorderHelper(newN.getLeft(), a);
            preorderHelper(newN.getRight(), a);
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> a = new ArrayList<T>();
        inorderHelper(root, a);
        return a;
    }

    /**
     *
     * @param newN the node being using in the recursive call
     * @param a list of data in traversal - inorder
     */
    private void inorderHelper(BSTNode<T> newN, List<T> a) {
        if (newN == null) {
            return;
        }
        inorderHelper(newN.getLeft(), a);
        a.add(newN.getData());
        inorderHelper(newN.getRight(), a);
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> a = new ArrayList<T>();
        postorderHelper(root, a);
        return a;
    }

    /**
     *
     * @param newN the node being using in the recursive call
     * @param a list of data in traversal - postorder
     */
    private void postorderHelper(BSTNode<T> newN, List<T> a) {
        if (newN == null) {
            return;
        }
        postorderHelper(newN.getLeft(), a);
        postorderHelper(newN.getRight(), a);
        a.add(newN.getData());

    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use. You may import java.util.Queue as well as an implmenting
     * class.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        ArrayList<T> a = new ArrayList<>();
        if (root == null) {
            return a;
        }
        Queue<BSTNode<T>> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            BSTNode<T> node = q.remove();
            a.add(node.getData());
            if (node.getLeft() != null) {
                q.add(node.getLeft());
            }
            if (node.getRight() != null) {
                q.add(node.getRight());
            }
        }
        return a;

    }


    /**
     * Returns the height of the root of the tree.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);
    }

    /**
     *
     * @param newN the node used in the recursive vall
     * @return height of root
     */
    private int heightHelper(BSTNode<T> newN) {
        if (newN == null) {
            return -1;
        } else {
            return 1 + Math.max(heightHelper(newN.getLeft()), heightHelper(newN.getRight()));
        }

    }
    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        if (size != 0) {
            root.setRight(null);
            root.setLeft(null);
            root = null;
            size = 0;
        }
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * To do this, you must first find the deepest common ancestor of both data
     * and add it to the list. Then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. Please note that there is no
     * relationship between the data parameters in that they may not belong
     * to the same branch. You will most likely have to split off and
     * traverse the tree for each piece of data.
     * *
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you will have to add to the front and
     * back of the list.
     *
     * This method only needs to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     *
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Hint: How can we use the order property of the BST to locate the deepest
     * common ancestor?
     *
     * Ex:
     * Given the following BST composed of Integers
     *              50
     *          /        \
     *        25         75
     *      /    \
     *     12    37
     *    /  \    \
     *   11   15   40
     *  /
     * 10
     * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        List<T> p = new LinkedList<>();
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Data 1 or 2 is null");
        }
        if (data1.equals(data2)) {
            p.add(data1);
        } else {
            BSTNode<T> deepestAncestor = findAncestor(root, data1, data2);
            p = findFirstPath(deepestAncestor, p, data1);
            p = findSecondPath(deepestAncestor, p, data2);
        }
        return p;
    }

    /**
     * My helper method to find the hald of the path. Data 1 to the deepest ancestor
     * @param newN the node being using in the recursion
     * @param p path to the data that is updated
     * @param data the data the path is made to
     * @return the list of nodes between data 1 and the deepest ancestor
     */
    private List<T> findFirstPath(BSTNode<T> newN, List<T> p, T data) {
        if (newN != null) {
            if (data.compareTo(newN.getData()) == 0) {
                ((LinkedList<T>) p).addFirst(newN.getData());
                ((LinkedList<T>) p).removeLast();
                return p;
            } else if (data.compareTo(newN.getData()) > 0) {
                ((LinkedList<T>) p).addFirst(newN.getData());
                return findFirstPath(newN.getRight(), p, data);
            } else {
                ((LinkedList<T>) p).addFirst(newN.getData());
                return findFirstPath(newN.getLeft(), p, data);
            }
        }
        throw new NoSuchElementException("Element is not in the BST");
    }

    /**
     *
     * @param newN the node being using in the recursion
     * @param p path to the data that is updated
     * @param data the data the path is made to
     * @return the list of nodes between data 2 and the deepest ancestor
     */
    private List<T> findSecondPath(BSTNode<T> newN, List<T> p, T data) {
        if (newN != null) {
            if (data.compareTo(newN.getData()) == 0) {
                ((LinkedList<T>) p).addLast(newN.getData());
                return p;
            } else if (data.compareTo(newN.getData()) > 0) {
                ((LinkedList<T>) p).addLast(newN.getData());
                return findSecondPath(newN.getRight(), p, data);
            } else {
                ((LinkedList<T>) p).addLast(newN.getData());
                return findSecondPath(newN.getLeft(), p, data);
            }
        }
        throw new NoSuchElementException("Element is not in the BST");
    }

    /**
     *
     * @param newN the node being using in the recursion
     * @param data1 data input number 1
     * @param data2 data input number 2
     * @return the node that contains the deepest common ancestor: hopefully :)
     */
    private BSTNode<T> findAncestor(BSTNode<T> newN, T data1, T data2) {
        if (data1.compareTo(data2) < 0) {
            if (newN.getData().compareTo(data1) >= 0
                    && newN.getData().compareTo(data2) <= 0) {
                return newN;
            } else if (newN.getData().compareTo(data1) < 0) {
                return findAncestor(newN.getLeft(), data1, data2);
            } else {
                return findAncestor(newN.getRight(), data1, data2);
            }
        } else {
            if (newN.getData().compareTo(data1) <= 0 && newN.getData().compareTo(data2) >= 0) {
                return newN;
            } else if (newN.getData().compareTo(data2) < 0) {
                return findAncestor(newN.getLeft(), data1, data2);
            } else {
                return findAncestor(newN.getRight(), data1, data2);
            }
        }
    }
    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
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
