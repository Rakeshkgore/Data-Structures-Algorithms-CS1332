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

@RunWith(JaynesJUnit05.class)
@Suite.SuiteClasses({JaynesJUnit05.TestSize0.class, JaynesJUnit05.TestSize1.class, JaynesJUnit05.TestSize2.class, JaynesJUnit05.TestExceptions.class,
        JaynesJUnit05.TestLargerData.class, JaynesJUnit05.OtherTests.class, JaynesJUnit05.TestAddRotations.class, JaynesJUnit05.TestRemoveRotations.class})

public class JaynesJUnit05 extends Suite {
    private static final int TIMEOUT = 200;
    private static String actual;
    private static String expected;

    public JaynesJUnit05(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        super(klass, builder);
    }

    // This is a visualizer for your tree, it will print out a level order display with each level contained within a single array
    public static class Visualizer {
        private static String binViz(AVL<Integer> tree) {
            Integer[][] avl = new Integer[tree.height() + 1][];

            for (int i = 0; i < avl.length; i++) {
                avl[i] = new Integer[(int) Math.pow(2, i)];
            }

            binVizHelper(avl, 0, 0, tree.getRoot());

            return Arrays.deepToString(avl);
        }

        private static void binVizHelper(Integer[][] expected, int depth, int branch, AVLNode<Integer> current) {
            if (current != null) {
                expected[depth][branch] = current.getData();

                binVizHelper(expected, depth + 1, (2 * branch), current.getLeft());
                binVizHelper(expected, depth + 1, (2 * branch + 1), current.getRight());
            }
        }
    }

    public static class TestSize0 {
        private AVL<Integer> tree;

        @Before
        public void setUp() {
            tree = new AVL();
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
        public void testKSmallest() {
            assertArrayEquals(new Integer[]{}, tree.kSmallest(0).toArray());
        }

        @Test(timeout = TIMEOUT)
        public void TestClear() {
            tree.clear();

            assertNull(tree.getRoot());
            assertEquals(0, tree.size());
        }
    }

    public static class TestSize1 {
        private AVL<Integer> tree;

        @Before
        public void setUp() {
            tree = new AVL();
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
        public void testPredecessor() {
            assertNull(tree.predecessor(0));
        }

        @Test(timeout = TIMEOUT)
        public void testKSmallest() {
            assertArrayEquals(new Integer[]{0}, tree.kSmallest(1).toArray());
        }
    }

    public static class TestSize2 {
        private AVL<Integer> tree;

        @Before
        public void setUp() {
            tree = new AVL();
            tree.add(2);
            tree.add(1);
        }

        @Test(timeout = TIMEOUT)
        public void testAdd() {
            tree.add(0);

            assertEquals(3, tree.size());
            assertEquals("[[1], [0, 2]]", Visualizer.binViz(tree));
        }

        @Test(timeout = TIMEOUT)
        public void testRemove() {
            assertEquals((Integer) 1, tree.remove(1));
            assertEquals(1, tree.size());
            assertEquals((Integer) 2, tree.getRoot().getData());
        }

        @Test(timeout = TIMEOUT)
        public void testGet() {
            assertEquals((Integer) 1, tree.get(1));
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
        public void testPredecessor() {
            assertNull(tree.predecessor(1));
            assertEquals((Integer) 1, tree.predecessor(2));
        }

        @Test(timeout = TIMEOUT)
        public void testKSmallest() {
            assertArrayEquals(new Integer[]{1}, tree.kSmallest(1).toArray());
            assertArrayEquals(new Integer[]{1, 2}, tree.kSmallest(2).toArray());
        }

        @Test(timeout = TIMEOUT)
        public void testClear() {
            tree.clear();

            assertEquals(0, tree.size());
            assertNull(tree.getRoot());
        }
    }

    public static class TestExceptions {
        private AVL<Integer> tree;

        @Before
        public void setUp() {
            tree = new AVL<>();
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testCreateNull() {
            new AVL<>(null);
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
        public void testPredecessorNullData() {
            tree.predecessor(null);
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testPredecessorDataNotInTree() {
            tree.add(1);
            tree.add(0);
            tree.add(2);

            tree.predecessor(3);
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testPredecessorEmpty() {
            tree.predecessor(0);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testKSmallestNegativeK() {
            tree.kSmallest(-1);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testKSmallestTooLargeK() {
            tree.kSmallest(1);
        }
    }

    public static class TestLargerData {
        AVL<Integer> tree;

        @Before
        public void setUp() {
            /*                       75
                                /           \
                              64            127
                            /   \         /     \
                         27      71     100     156
                        /   \   /   \  /   \   /   \
                      18    33 68  74 92  106 145  193

             */

            // The way this data was added would have produced a left rotation, a right-left rotation, a left-right rotation, and a right rotation

            ArrayList<Integer> list = new ArrayList<>(Arrays.asList(100, 75, 64, 145, 33, 71, 127, 27, 18, 156, 193, 68, 74, 92, 106));
            tree = new AVL(list);
        }

        @Test(timeout = TIMEOUT)
        public void testAddDuplicates() {
            tree.add(75);
            tree.add(127);
            tree.add(74);
            tree.add(18);

            expected = "[[75], [64, 127], [27, 71, 100, 156], [18, 33, 68, 74, 92, 106, 145, 193]]";
            actual = Visualizer.binViz(tree);

            assertEquals(15, tree.size());
            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testRemove0Children() {
            assertEquals((Integer) 106, tree.remove(106));

            expected = "[[75], [64, 127], [27, 71, 100, 156], [18, 33, 68, 74, 92, null, 145, 193]]";
            actual = Visualizer.binViz(tree);

            assertEquals(14, tree.size());
            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testRemove1Child() {
            tree.add(91);
            assertEquals((Integer) 92, tree.remove(92));

            expected = "[[75], [64, 127], [27, 71, 100, 156], [18, 33, 68, 74, 91, 106, 145, 193]]";
            actual = Visualizer.binViz(tree);

            assertEquals(15, tree.size());
            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testRemove2Children() {
            assertEquals((Integer) 64, tree.remove(64));

            expected = "[[75], [68, 127], [27, 71, 100, 156], [18, 33, null, 74, 92, 106, 145, 193]]";
            actual = Visualizer.binViz(tree);

            assertEquals(14, tree.size());
            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveRoots() {
            assertEquals((Integer) 75, tree.remove(75));

            expected = "[[92], [64, 127], [27, 71, 100, 156], [18, 33, 68, 74, null, 106, 145, 193]]";
            actual = Visualizer.binViz(tree);

            assertEquals(14, tree.size());
            assertEquals((Integer) 92, tree.getRoot().getData());
            assertEquals(expected, actual);

            assertEquals((Integer) 92, tree.remove(92));

            expected = "[[100], [64, 127], [27, 71, 106, 156], [18, 33, 68, 74, null, null, 145, 193]]";
            actual = Visualizer.binViz(tree);

            assertEquals(13, tree.size());
            assertEquals((Integer) 100, tree.getRoot().getData());
            assertEquals(expected, actual);

            assertEquals((Integer) 100, tree.remove(100));

            // This would produce a left rotation
            expected = "[[106], [64, 156], [27, 71, 127, 193], [18, 33, 68, 74, null, 145, null, null]]";
            actual = Visualizer.binViz(tree);

            assertEquals(12, tree.size());
            assertEquals((Integer) 106, tree.getRoot().getData());
            assertEquals(expected, actual);

            assertEquals((Integer) 106, tree.remove(106));

            expected = "[[127], [64, 156], [27, 71, 145, 193], [18, 33, 68, 74, null, null, null, null]]";
            actual = Visualizer.binViz(tree);

            assertEquals(11, tree.size());
            assertEquals((Integer) 127, tree.getRoot().getData());
            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testPredecessor() {
            // Minimum element
            assertNull(tree.predecessor(18));
            // Maximum element
            assertEquals((Integer) 156, tree.predecessor(193));
            // Root
            assertEquals((Integer) 74, tree.predecessor(75));
            // Internal node
            assertEquals((Integer) 92, tree.predecessor(100));
        }

        @Test(timeout = TIMEOUT)
        public void testKSmallest() {
            assertArrayEquals(new Integer[]{18, 27, 33}, tree.kSmallest(3).toArray());
            assertArrayEquals(new Integer[]{}, tree.kSmallest(0).toArray());
            assertArrayEquals(new Integer[]{18, 27, 33, 64, 68, 71}, tree.kSmallest(6).toArray());
            assertArrayEquals(new Integer[]{18, 27, 33, 64, 68, 71, 74, 75, 92, 100, 106, 127, 145, 156, 193}, tree.kSmallest(15).toArray());
            assertArrayEquals(new Integer[]{18}, tree.kSmallest(1).toArray());
        }

        @Test(timeout = TIMEOUT)
        public void testHeight() {
            assertEquals(3, tree.height());
        }

        @Test(timeout = TIMEOUT)
        public void testContains() {
            assertTrue(tree.contains(145));
            assertTrue(tree.contains(33));
            assertTrue(tree.contains(100));
            assertFalse(tree.contains(194));
        }
    }

    public static class OtherTests {
        AVL<Integer> tree;

        @Before
        public void setUp() {
            /*
                    66
                   /  \
                 42    76
                 \    / \
                 53  70 89

             */
            tree = new AVL<>(Arrays.asList(66, 42, 76, 53, 70, 89));
        }

        @Test(timeout = TIMEOUT)
        public void testHeight() {
            assertEquals(2, tree.height());
        }

        @Test(timeout = TIMEOUT)
        public void testContains() {
            assertTrue(tree.contains(53));
            assertTrue(tree.contains(89));
            assertFalse(tree.contains(77));
        }

        @Test(timeout = TIMEOUT)
        public void testAdd() {
            // This would produce a right-left rotation
            tree.add(47);

            expected = "[[66], [47, 76], [42, 53, 70, 89]]";
            actual = Visualizer.binViz(tree);

            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testRemoveRoot() {
            assertEquals((Integer) 66, tree.remove(66));

            expected = "[[70], [42, 76], [null, 53, null, 89]]";
            actual = Visualizer.binViz(tree);

            assertEquals(5, tree.size());
            assertEquals(expected, actual);
        }

        @Test(timeout = TIMEOUT)
        public void testPredecessor() {
            assertEquals((Integer) 76, tree.predecessor(89));
            assertEquals((Integer) 42, tree.predecessor(53));
            assertEquals((Integer) 66, tree.predecessor(70));
            assertEquals((Integer) 70, tree.predecessor(76));
            assertNull(tree.predecessor(42));
        }

        @Test(timeout = TIMEOUT)
        public void testKSmallest() {
            assertArrayEquals(new Integer[]{42, 53, 66}, tree.kSmallest(3).toArray());
            assertArrayEquals(new Integer[]{}, tree.kSmallest(0).toArray());
            assertArrayEquals(new Integer[]{42, 53, 66, 70}, tree.kSmallest(4).toArray());
            assertArrayEquals(new Integer[]{42, 53, 66, 70, 76, 89}, tree.kSmallest(6).toArray());
        }
    }

    public static class TestAddRotations {
        AVL<Integer> tree;

        @Before
        public void setUp() {
            tree = new AVL<>();
        }

        @Test(timeout = TIMEOUT)
        public void left() {
            /*
                2            1
                 \          / \
                  1   ->   0   2
                   \
                    0
             */
            tree.add(2);
            tree.add(1);
            tree.add(0);

            assertEquals((Integer) 1, tree.getRoot().getData());
            assertEquals((Integer) 0, tree.getRoot().getLeft().getData());
            assertEquals((Integer) 2, tree.getRoot().getRight().getData());
        }

        @Test(timeout = TIMEOUT)
        public void right() {
            /*
                       0         1
                      /         / \
                     1   ->    0   2
                    /
                   2
             */
            tree.add(0);
            tree.add(1);
            tree.add(2);

            assertEquals((Integer) 1, tree.getRoot().getData());
            assertEquals((Integer) 0, tree.getRoot().getLeft().getData());
            assertEquals((Integer) 2, tree.getRoot().getRight().getData());
        }

        @Test(timeout = TIMEOUT)
        public void leftRight() {
            /*
                     2
                    /           1
                   0     ->    / \
                   \          0   2
                    1
             */
            tree.add(2);
            tree.add(0);
            tree.add(1);

            assertEquals((Integer) 1, tree.getRoot().getData());
            assertEquals((Integer) 0, tree.getRoot().getLeft().getData());
            assertEquals((Integer) 2, tree.getRoot().getRight().getData());
        }

        @Test(timeout = TIMEOUT)
        public void rightLeft() {
            /*
                   1
                    \           1
                     2   ->    / \
                    /         0   2
                   0
             */
            tree.add(1);
            tree.add(0);
            tree.add(2);

            assertEquals((Integer) 1, tree.getRoot().getData());
            assertEquals((Integer) 0, tree.getRoot().getLeft().getData());
            assertEquals((Integer) 2, tree.getRoot().getRight().getData());
        }
    }

    public static class TestRemoveRotations {
        AVL<Integer> tree;

        @Before
        public void setUp() {
            tree = new AVL<>();
        }

        @Test(timeout = TIMEOUT)
        public void left() {
            /*
                    13                  25
                   /  \                /  \
                 10    25     ->     17    27
                      /  \
                    17   27
             */
            tree.add(13);
            tree.add(10);
            tree.add(25);
            tree.add(17);
            tree.add(27);

            assertEquals((Integer) 13, tree.remove(13));
            assertEquals((Integer) 10, tree.remove(10));

            assertEquals((Integer) 25, tree.getRoot().getData());
            assertEquals((Integer) 17, tree.getRoot().getLeft().getData());
            assertEquals((Integer) 27, tree.getRoot().getRight().getData());
        }

        @Test(timeout = TIMEOUT)
        public void right() {
            /*
                       56                  36
                      /  \                /  \
                    36    72     ->     18    56
                   / \
                 18  43
             */
            tree.add(56);
            tree.add(72);
            tree.add(36);
            tree.add(18);
            tree.add(43);

            assertEquals((Integer) 43, tree.remove(43));
            assertEquals((Integer) 72, tree.remove(72));

            assertEquals((Integer) 36, tree.getRoot().getData());
            assertEquals((Integer) 18, tree.getRoot().getLeft().getData());
            assertEquals((Integer) 56, tree.getRoot().getRight().getData());
        }

        @Test(timeout = TIMEOUT)
        public void leftRight() {
            /*
                    13                  17
                   /  \                /  \
                 10    25     ->     13    27
                      /  \
                    17   27
             */
            tree.add(13);
            tree.add(10);
            tree.add(25);
            tree.add(17);
            tree.add(27);

            assertEquals((Integer) 25, tree.remove(25));
            assertEquals((Integer) 10, tree.remove(10));

            assertEquals((Integer) 17, tree.getRoot().getData());
            assertEquals((Integer) 13, tree.getRoot().getLeft().getData());
            assertEquals((Integer) 27, tree.getRoot().getRight().getData());
        }

        @Test(timeout = TIMEOUT)
        public void rightLeft() {
            /*
                       56                  43
                      /  \                /  \
                    36    72     ->     36    56
                   / \
                 18  43
             */
            tree.add(56);
            tree.add(72);
            tree.add(36);
            tree.add(18);
            tree.add(43);

            assertEquals((Integer) 18, tree.remove(18));
            assertEquals((Integer) 72, tree.remove(72));

            assertEquals((Integer) 43, tree.getRoot().getData());
            assertEquals((Integer) 36, tree.getRoot().getLeft().getData());
            assertEquals((Integer) 56, tree.getRoot().getRight().getData());
        }
    }
}