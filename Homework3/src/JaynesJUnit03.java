import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * @author Tahlee Jaynes
 * @version 1.0
 */

@RunWith(JaynesJUnit03.class)
@Suite.SuiteClasses({JaynesJUnit03.TestSize0.class, JaynesJUnit03.TestSize1.class, JaynesJUnit03.TestSize2.class, JaynesJUnit03.TestExceptions.class, JaynesJUnit03.TestLargerData.class, JaynesJUnit03.TestDegenerate.class})

public class JaynesJUnit03 extends Suite {
    private static final int TIMEOUT = 200;
    private static String actual;
    private static String expected;

    public JaynesJUnit03(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        super(klass, builder);
    }

    public static class Visualizer {
        private static String binViz(BST<Integer> tree) {
            Integer[][] bst = new Integer[tree.height() + 1][];

            for (int i = 0; i < bst.length; i++) {
                bst[i] = new Integer[(int) Math.pow(2, i)];
            }

            binVizHelper(bst, 0, 0, tree.getRoot());

            return Arrays.deepToString(bst);
        }

        private static void binVizHelper(Integer[][] expected, int depth, int branch, BSTNode<Integer> current) {
            if (current != null) {
                expected[depth][branch] = current.getData();

                binVizHelper(expected, depth + 1, (2 * branch), current.getLeft());
                binVizHelper(expected, depth + 1, (2 * branch + 1), current.getRight());
            }
        }
    }

    public static class TestSize0 {
        private BST<Integer> tree;

        @Before
        public void setUp() {
            tree = new BST();
        }

        @Test(timeout = TIMEOUT)
        public void testInit() {
            assertEquals(0, tree.size());
            assertNull(tree.getRoot());
        }

        @Test(timeout = TIMEOUT)
        public void testAdd() {
            tree.add(0);

            assertEquals(1, tree.size());
            assertEquals((Integer) 0, tree.getRoot().getData());
        }

        @Test(timeout = TIMEOUT)
        public void testHeight() {
            assertEquals(-1, tree.height());
        }

        @Test(timeout = TIMEOUT)
        public void testPreOrder() {
            assertArrayEquals(new Integer[]{}, tree.preorder().toArray());
        }

        @Test(timeout = TIMEOUT)
        public void testInOrder() {
            assertArrayEquals(new Integer[]{}, tree.inorder().toArray());
        }

        @Test(timeout = TIMEOUT)
        public void testPostOrder() {
            assertArrayEquals(new Integer[]{}, tree.postorder().toArray());
        }

        @Test(timeout = TIMEOUT)
        public void testLevelOrder() {
            assertArrayEquals(new Integer[]{}, tree.levelorder().toArray());
        }
    }

    public static class TestSize1 {
        private BST<Integer> tree;

        @Before
        public void setUp() {
            tree = new BST();
            tree.add(0);
        }

        @Test(timeout = TIMEOUT)
        public void testAdd() {
            tree.add(1);

            assertEquals(2, tree.size());
            assertEquals((Integer) 0, tree.getRoot().getData());
        }

        @Test(timeout = TIMEOUT)
        public void testRemove() {
            assertEquals((Integer) 0, tree.remove(0));
            assertEquals(0, tree.size());
            assertNull(tree.getRoot());
        }

        @Test(timeout = TIMEOUT)
        public void testGet() {
            assertEquals((Integer) 0, tree.get(0));
        }

        @Test(timeout = TIMEOUT)
        public void testContains() {
            assertTrue(tree.contains(0));
            assertFalse(tree.contains(1));
        }

        @Test(timeout = TIMEOUT)
        public void testHeight() {
            assertEquals(0, tree.height());
        }

        @Test(timeout = TIMEOUT)
        public void testClear() {
            tree.clear();

            assertEquals(0, tree.size());
            assertNull(tree.getRoot());
        }

        @Test(timeout = TIMEOUT)
        public void testPreOrder() {
            assertArrayEquals(new Integer[]{0}, tree.preorder().toArray());
        }

        @Test(timeout = TIMEOUT)
        public void testInOrder() {
            assertArrayEquals(new Integer[]{0}, tree.inorder().toArray());
        }

        @Test(timeout = TIMEOUT)
        public void testPostOrder() {
            assertArrayEquals(new Integer[]{0}, tree.postorder().toArray());
        }

        @Test(timeout = TIMEOUT)
        public void testLevelOrder() {
            assertArrayEquals(new Integer[]{0}, tree.levelorder().toArray());
        }

        @Test(timeout = TIMEOUT)
        public void testPath() {
            assertArrayEquals(new Integer[]{0}, tree.findPathBetween(0, 0).toArray());
        }
    }

    public static class TestSize2 {
        private BST<Integer> tree;

        @Before
        public void setUp() {
            tree = new BST();
            tree.add(1);
            tree.add(0);
        }

        @Test(timeout = TIMEOUT)
        public void testAdd() {
            tree.add(2);

            assertEquals(3, tree.size());
        }

        @Test(timeout = TIMEOUT)
        public void testRemove() {
            assertEquals((Integer) 1, tree.remove(1));
            assertEquals(1, tree.size());
            assertEquals((Integer) 0, tree.getRoot().getData());
        }

        @Test(timeout = TIMEOUT)
        public void testGet() {
            assertEquals((Integer) 0, tree.get(0));
        }

        @Test(timeout = TIMEOUT)
        public void testContains() {
            assertTrue(tree.contains(1));
        }

        @Test(timeout = TIMEOUT)
        public void testHeight() {
            assertEquals(1, tree.height());
        }

        @Test(timeout = TIMEOUT)
        public void testPreOrder() {
            assertArrayEquals(new Integer[]{1, 0}, tree.preorder().toArray());
        }

        @Test(timeout = TIMEOUT)
        public void testInOrder() {
            assertArrayEquals(new Integer[]{0, 1}, tree.inorder().toArray());
        }

        @Test(timeout = TIMEOUT)
        public void testPostOrder() {
            assertArrayEquals(new Integer[]{0, 1}, tree.postorder().toArray());
        }

        @Test(timeout = TIMEOUT)
        public void testLevelOrder() {
            assertArrayEquals(new Integer[]{1, 0}, tree.levelorder().toArray());
        }

        @Test(timeout = TIMEOUT)
        public void testPath() {
            assertArrayEquals(new Integer[]{0, 1}, tree.findPathBetween(0, 1).toArray());
        }

        @Test(timeout = TIMEOUT)
        public void testClear() {
            tree.clear();
            assertEquals(0, tree.size());
            assertNull(tree.getRoot());
        }
    }

    public static class TestExceptions {
        private BST<Integer> tree;

        @Before
        public void setUp() {
            tree = new BST();
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testCreateNull() {
            new BST(null);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testAddNull() {
            tree.add(null);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testRemoveNull() {
            tree.remove(null);
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testRemoveEmpty() {
            tree.remove(0);
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testRemoveNotInTree() {
            tree.add(0);
            tree.remove(1);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testGetNull() {
            tree.get(null);
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testGetEmpty() {
            tree.get(0);
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testGetNotInTree() {
            tree.add(0);
            tree.get(1);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testContainsNull() {
            tree.contains(null);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testPathNullData() {
            tree.findPathBetween(null, null);
            tree.findPathBetween(0, null);
            tree.findPathBetween(null, 0);
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testPathDataNotInTree() {
            tree.add(1);
            tree.add(0);
            tree.add(2);

            tree.findPathBetween(1, 3);
            tree.findPathBetween(3, 2);
            tree.findPathBetween(3, 4);
        }
    }

    public static class TestLargerData {
        BST<Integer> tree;

        @Before
        public void setUp() {
            /*                       100
                                /           \
                              75            145
                            /   \         /     \
                         64      89     127     161
                        /   \   /   \  /   \   /   \
                      33    71 85  92 106 139 156  193

             */

            ArrayList<Integer> list = new ArrayList<>(Arrays.asList(100, 75, 64, 145, 33, 71, 127, 161, 156, 193, 89, 92, 85, 106, 139));
            tree = new BST(list);
        }

        @Test(timeout = TIMEOUT)
        public void testAddDuplicates() {
            tree.add(85);
            tree.add(127);
            tree.add(75);
            tree.add(100);

            expected = "[[100], [75, 145], [64, 89, 127, 161], [33, 71, 85, 92, 106, 139, 156, 193]]";
            actual = Visualizer.binViz(tree);

            assertEquals(15, tree.size());
            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testRemove0Children() {
            assertEquals((Integer) 139, tree.remove(139));

            expected = "[[100], [75, 145], [64, 89, 127, 161], [33, 71, 85, 92, 106, null, 156, 193]]";
            actual = Visualizer.binViz(tree);

            assertEquals(14, tree.size());
            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testRemove1Child() {
            tree.add(77);
            assertEquals((Integer) 85, tree.remove(85));

            expected = "[[100], [75, 145], [64, 89, 127, 161], [33, 71, 77, 92, 106, 139, 156, 193]]";
            actual = Visualizer.binViz(tree);

            assertEquals(15, tree.size());
            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testRemove2Children() {
            assertEquals((Integer) 145, tree.remove(145));

            expected = "[[100], [75, 139], [64, 89, 127, 161], [33, 71, 85, 92, 106, null, 156, 193]]";
            actual = Visualizer.binViz(tree);

            assertEquals(14, tree.size());
            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveRoots() {
            assertEquals((Integer) 100, tree.remove(100));

            expected = "[[92], [75, 145], [64, 89, 127, 161], [33, 71, 85, null, 106, 139, 156, 193]]";
            actual = Visualizer.binViz(tree);

            assertEquals(14, tree.size());
            assertEquals((Integer) 92, tree.getRoot().getData());
            assertEquals(expected, actual);

            assertEquals((Integer) 92, tree.remove(92));

            expected = "[[89], [75, 145], [64, 85, 127, 161], [33, 71, null, null, 106, 139, 156, 193]]";
            actual = Visualizer.binViz(tree);

            assertEquals(13, tree.size());
            assertEquals((Integer) 89, tree.getRoot().getData());
            assertEquals(expected, actual);

            assertEquals((Integer) 89, tree.remove(89));

            expected = "[[85], [75, 145], [64, null, 127, 161], [33, 71, null, null, 106, 139, 156, 193]]";
            actual = Visualizer.binViz(tree);

            assertEquals(12, tree.size());
            assertEquals((Integer) 85, tree.getRoot().getData());
            assertEquals(expected, actual);

            assertEquals((Integer) 85, tree.remove(85));

            expected = "[[75], [64, 145], [33, 71, 127, 161], [null, null, null, null, 106, 139, 156, 193]]";
            actual = Visualizer.binViz(tree);

            assertEquals(11, tree.size());
            assertEquals((Integer) 75, tree.getRoot().getData());
            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testPreOrder() {
            expected = "[100, 75, 64, 33, 71, 89, 85, 92, 145, 127, 106, 139, 161, 156, 193]";
            actual = tree.preorder().toString();

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testInOrder() {
            expected = "[33, 64, 71, 75, 85, 89, 92, 100, 106, 127, 139, 145, 156, 161, 193]";
            actual = tree.inorder().toString();

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testPostOrder() {
            expected = "[33, 71, 64, 85, 92, 89, 75, 106, 139, 127, 156, 193, 161, 145, 100]";
            actual = tree.postorder().toString();

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testLevelOrder() {
            expected = "[100, 75, 145, 64, 89, 127, 161, 33, 71, 85, 92, 106, 139, 156, 193]";
            actual = tree.levelorder().toString();

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testFindPath1() {
            expected = "[85, 89, 75, 100, 145]";
            actual = tree.findPathBetween(85, 145).toString();

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testFindPath2() {
            expected = "[33, 64, 75, 100, 145, 161, 193]";
            actual = tree.findPathBetween(33, 193).toString();

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testFindPath3() {
            expected = "[127]";
            actual = tree.findPathBetween(127, 127).toString();

            assertEquals(expected, actual);
        }

        //It was kinda hard to come up with a name for this, but the "backwards" test evaluates the path where data1 > data2
        @Test(timeout = TIMEOUT)
        public void testFindPathBackwards1() {
            expected = "[85, 89, 75, 64, 71]";
            actual = tree.findPathBetween(85, 71).toString();

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testFindPathBackwards2() {
            expected = "[139, 127, 106]";
            actual = tree.findPathBetween(139, 106).toString();

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testFindPathBackwards3() {
            expected = "[156, 161, 145, 100, 75, 89, 92]";
            actual = tree.findPathBetween(156, 92).toString();
        }

        @Test(timeout = TIMEOUT)
        public void testHeight() {
            assertEquals(3, tree.height());
        }

        @Test(timeout = TIMEOUT)
        public void testContains() {
            assertTrue(tree.contains(139));
            assertTrue(tree.contains(33));
            assertTrue(tree.contains(100));
            assertFalse(tree.contains(194));
        }
    }

    public static class TestDegenerate {
        BST<Integer> tree;

        @Before
        public void setUp() {
            /*
                 1
                  \
                   2
                    \
                     3
                      \
                       4
                        \
                         5
             */
            tree = new BST<>(Arrays.asList(1, 2, 3, 4, 5));
        }

        @Test(timeout = TIMEOUT)
        public void testHeight() {
            assertEquals(4, tree.height());
        }

        @Test(timeout = TIMEOUT)
        public void testContains() {
            assertTrue(tree.contains(5));
            assertTrue(tree.contains(1));
            assertFalse(tree.contains(0));
        }

        @Test(timeout = TIMEOUT)
        public void testAdd() {
            tree.add(0);

            expected = "[[1], [0, 2], [null, null, null, 3], [null, null, null, null, null, null, null, 4], [null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 5]]";
            actual = Visualizer.binViz(tree);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveRoot() {
            assertEquals((Integer) 1, tree.remove(1));

            expected = "[[2], [null, 3], [null, null, null, 4], [null, null, null, null, null, null, null, 5]]";
            actual = Visualizer.binViz(tree);

            assertEquals(4, tree.size());
            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveMiddle() {
            assertEquals((Integer) 3, tree.remove(3));

            expected = "[[1], [null, 2], [null, null, null, 4], [null, null, null, null, null, null, null, 5]]";
            actual = Visualizer.binViz(tree);

            assertEquals(4, tree.size());
            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveAll() {
            for (int i = 1; i < 6; i++) {
                tree.remove(i);
            }

            expected = "[]";
            actual = Visualizer.binViz(tree);

            assertEquals(0, tree.size());
            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testPreOrder() {
            expected = "[1, 2, 3, 4, 5]";
            actual = tree.preorder().toString();

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testInOrder() {
            expected = "[1, 2, 3, 4, 5]";
            actual = tree.inorder().toString();

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testPostOrder() {
            expected = "[5, 4, 3, 2, 1]";
            actual = tree.postorder().toString();

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testLevelOrder() {
            expected = "[1, 2, 3, 4, 5]";
            actual = tree.inorder().toString();

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testFindPath() {
            expected = "[2, 3, 4]";
            actual = tree.findPathBetween(2, 4).toString();

            assertEquals(expected, actual);
        }

        //It was kinda hard to come up with a name for this, but the "backwards" test evaluates the path where data1 > data2
        @Test(timeout = TIMEOUT)
        public void testFindPathBackwards() {
            expected = "[5, 4, 3, 2, 1]";
            actual = tree.findPathBetween(5, 1).toString();

            assertEquals(expected, actual);
        }
    }
}