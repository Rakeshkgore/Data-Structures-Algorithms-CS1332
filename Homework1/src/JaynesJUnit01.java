import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * @author Tahlee Jaynes
 * @version 1.0
 */

@RunWith(JaynesJUnit01.class)
@Suite.SuiteClasses({ JaynesJUnit01.TestSize0.class, JaynesJUnit01.TestSize1.class, JaynesJUnit01.TestSize2.class,
        JaynesJUnit01.TestExceptions.class, JaynesJUnit01.TestLargerData.class})

public class JaynesJUnit01 extends Suite {
    private static final int TIMEOUT = 200;

    public JaynesJUnit01(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        super(klass, builder);
    }

    public static class TestSize0 {
        private SinglyLinkedList<String> list;

        @Before
        public void setUp() {
            list = new SinglyLinkedList<>();
        }

        @Test (timeout = TIMEOUT)
        public void testInitialization() {
            assertEquals(0, list.size());
            assertTrue(list.isEmpty());
            assertNull(list.getHead());
            assertNull(list.getTail());
        }

        //Test adding to list size 0
        @Test (timeout = TIMEOUT)
        public void testAddToFront() {
            list.addToFront("a1");
            assertEquals(1, list.size());
            assertEquals("a1", list.getHead().getData());
            assertEquals("a1", list.getTail().getData());
        }

        @Test (timeout = TIMEOUT)
        public void testAddAtIndex() {
            list.addAtIndex(0, "a1");
            assertEquals(1, list.size());
            assertEquals("a1", list.getHead().getData());
            assertEquals("a1", list.getTail().getData());
        }

        @Test (timeout = TIMEOUT)
        public void testAddToBack() {
            list.addToBack("a1");
            assertEquals(1, list.size());
            assertEquals("a1", list.getHead().getData());
            assertEquals("a1", list.getTail().getData());
        }

        @Test (timeout = TIMEOUT)
        public void testToArray() {
            String[] expected = new String[0];

            assertArrayEquals(expected, list.toArray());
        }

        @Test (timeout = TIMEOUT)
        public void testClear() {
            list.clear();
            assertEquals(0, list.size());
        }
    }

    public static class TestSize1 {
        private SinglyLinkedList<String> list;

        @Before
        public void setUp() {
            list = new SinglyLinkedList<>();
            list.addToFront("a1");
        }

        @Test (timeout = TIMEOUT)
        public void testIsEmpty() {
            assertFalse(list.isEmpty());
        }

        @Test (timeout = TIMEOUT)
        public void testAddToFront() {
            list.addToFront("b2");
            assertEquals(2, list.size());
            assertEquals("b2", list.getHead().getData());
            assertEquals("a1", list.getTail().getData());
        }

        @Test (timeout = TIMEOUT)
        public void testAddAtIndex0() {
            list.addAtIndex(0, "b2");
            assertEquals(2, list.size());
            assertEquals("b2", list.getHead().getData());
            assertEquals("a1", list.getTail().getData());
        }

        @Test (timeout = TIMEOUT)
        public void testAddAtIndex1() {
            list.addAtIndex(1, "b2");
            assertEquals(2, list.size());
            assertEquals("a1", list.getHead().getData());
            assertEquals("b2", list.getTail().getData());
        }

        @Test (timeout = TIMEOUT)
        public void testAddToBack() {
            list.addToBack("b2");
            assertEquals(2, list.size());
            assertEquals("a1", list.getHead().getData());
            assertEquals("b2", list.getTail().getData());
        }

        @Test (timeout = TIMEOUT)
        public void testRemoveFromFront() {
            assertEquals("a1", list.removeFromFront());
            assertEquals(0, list.size());
            assertNull(list.getHead());
            assertNull(list.getTail());
        }

        @Test (timeout = TIMEOUT)
        public void testRemoveAtIndex() {
            assertEquals("a1", list.removeAtIndex(0));
            assertEquals(0, list.size());
            assertNull(list.getHead());
            assertNull(list.getTail());
        }

        @Test (timeout = TIMEOUT)
        public void testRemoveFromBack() {
            assertEquals("a1", list.removeFromBack());
            assertEquals(0, list.size());
            assertNull(list.getHead());
            assertNull(list.getTail());
        }

        @Test (timeout = TIMEOUT)
        public void testRemoveLastOccurrence() {
            assertEquals("a1", list.removeLastOccurrence("a1"));
            assertEquals(0, list.size());
            assertNull(list.getHead());
            assertNull(list.getTail());
        }

        @Test (timeout = TIMEOUT)
        public void testToArray() {
            String[] expected = {"a1"};

            assertArrayEquals(expected, list.toArray());
        }

        @Test (timeout = TIMEOUT)
        public void testClear() {
            list.clear();
            assertEquals(0, list.size());
            assertNull(list.getHead());
            assertNull(list.getTail());
        }

        @Test (timeout = TIMEOUT)
        public void testGet() {
            assertEquals("a1", list.get(0));
        }
    }

    public static class TestSize2 {
        private SinglyLinkedList<String> list;

        @Before
        public void setUp() {
            list = new SinglyLinkedList<>();
            list.addToFront("a1");
            list.addToBack("b2");
        }

        @Test (timeout = TIMEOUT)
        public void testToArray() {
            String[] expected = {"a1", "b2"};

            assertArrayEquals(expected, list.toArray());
        }

        @Test (timeout = TIMEOUT)
        public void testGet() {
            assertEquals("a1", list.get(0));
            assertEquals("b2", list.get(1));
        }

        @Test (timeout = TIMEOUT)
        public void testAddToFront() {
            list.addToFront("c3");

            String[] expected = {"c3", "a1", "b2"};
            assertArrayEquals(expected, list.toArray());
        }

        @Test (timeout = TIMEOUT)
        public void testAddToBack() {
            list.addToBack("c3");

            String[] expected = {"a1", "b2", "c3"};
            assertArrayEquals(expected, list.toArray());
        }

        @Test (timeout = TIMEOUT)
        public void testAddAtIndex() {
            list.addAtIndex(1,"c3");

            String[] expected = {"a1", "c3", "b2"};
            assertArrayEquals(expected, list.toArray());
        }

        @Test (timeout = TIMEOUT)
        public void testRemoveFromFront() {
            assertEquals("a1", list.removeFromFront());
            assertEquals(1, list.size());
            assertEquals("b2", list.getHead().getData());
            assertEquals("b2", list.getTail().getData());
        }

        @Test (timeout = TIMEOUT)
        public void testRemoveAtIndex() {
            assertEquals("b2", list.removeAtIndex(1));
            assertEquals(1, list.size());
            assertEquals("a1", list.getHead().getData());
            assertEquals("a1", list.getTail().getData());
        }

        @Test (timeout = TIMEOUT)
        public void testRemoveFromBack() {
            assertEquals("b2", list.removeFromBack());
            assertEquals(1, list.size());
            assertEquals("a1", list.getHead().getData());
            assertEquals("a1", list.getTail().getData());
        }

        @Test (timeout = TIMEOUT)
        public void testRemoveLastOccurrenceFirst() {
            assertEquals("a1", list.removeLastOccurrence("a1"));
            assertEquals(1, list.size());
            assertEquals("b2", list.getHead().getData());
            assertEquals("b2", list.getTail().getData());
        }

        @Test (timeout = TIMEOUT)
        public void testRemoveLastOccurrenceLast() {
            assertEquals("b2", list.removeLastOccurrence("b2"));
            assertEquals(1, list.size());
            assertEquals("a1", list.getHead().getData());
            assertEquals("a1", list.getTail().getData());
        }
    }

    public static class TestExceptions {
        private SinglyLinkedList<String> list;

        @Before
        public void setUp() {
            list = new SinglyLinkedList<>();
        }

        @Test (timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testAddNull() {
            list.addAtIndex(0, null);
            list.addToFront(null);
            list.addToBack(null);
        }

        @Test (timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testRemoveLastOccurrenceNull() {
            list.removeLastOccurrence(null);
        }

        @Test (timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
        public void testGetNegativeIndex() {
            list.get(-1);
        }

        @Test (timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
        public void testAddNegativeIndex() {
            list.addAtIndex(-1, ":D");
        }

        @Test (timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
        public void testRemoveNegativeIndex() {
            list.removeAtIndex(-1);
        }

        @Test (timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
        public void testAddTooLargeIndex() {
            list.addAtIndex(1, ":D");
        }

        @Test (timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
        public void testRemoveIndexTooLarge() {
            list.addToFront("a1");
            list.removeAtIndex(1);
            list.removeAtIndex(2);
        }

        @Test (timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testRemoveEmptyList() {
            list.removeFromFront();
            list.removeFromBack();
            list.removeLastOccurrence(":D");
        }

        @Test (timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
        public void testGetIndexTooLarge() {
            list.addToFront("a1");
            list.get(1);
            list.get(2);
        }

        @Test (timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testRemoveNotFoundElement() {
            list.addToFront("a1");
            list.removeLastOccurrence("b2");
        }
    }

    public static class TestLargerData {
        private SinglyLinkedList<String> list;

        @Before
        public void setUp() {
            list = new SinglyLinkedList<>();
        }

        @Test (timeout = TIMEOUT)
        public void testAdds() {
            List<String> expected = new ArrayList<>();

            list.addToFront("d4");
            expected.add(0, "d4");
            assertArrayEquals(expected.toArray(), list.toArray());
            assertEquals(1, list.size());
            assertEquals("d4", list.getHead().getData());
            assertEquals("d4", list.getTail().getData());

            list.addToBack("b2");
            expected.add(1, "b2");
            assertArrayEquals(expected.toArray(), list.toArray());
            assertEquals(2, list.size());
            assertEquals("d4", list.getHead().getData());
            assertEquals("b2", list.getTail().getData());

            list.addToBack("a1");
            expected.add(2, "a1");
            assertArrayEquals(expected.toArray(), list.toArray());
            assertEquals(3, list.size());
            assertEquals("d4", list.getHead().getData());
            assertEquals("a1", list.getTail().getData());

            list.addAtIndex(0, "e5");
            expected.add(0, "e5");
            assertArrayEquals(expected.toArray(), list.toArray());
            assertEquals(4, list.size());
            assertEquals("e5", list.getHead().getData());
            assertEquals("a1", list.getTail().getData());

            list.addAtIndex(2, "c3");
            expected.add(2, "c3");
            assertArrayEquals(expected.toArray(), list.toArray());
            assertEquals(5, list.size());
            assertEquals("e5", list.getHead().getData());
            assertEquals("a1", list.getTail().getData());
        }

        @Test (timeout = TIMEOUT)
        public void testRemoves() {
            list.addToFront("a4");
            list.addToFront("a2");
            list.addToFront("a3");
            list.addToFront("a2");
            list.addToFront("a1");

            List<String> expected = new ArrayList<>();
            expected.addAll(Arrays.asList(new String[]{"a1", "a2", "a3", "a2", "a4"}));

            assertArrayEquals(expected.toArray(), list.toArray());
            assertEquals(5, list.size());
            assertEquals("a1", list.getHead().getData());
            assertEquals("a4", list.getTail().getData());

            assertEquals("a2", list.removeLastOccurrence("a2"));
            expected.remove(3);
            assertArrayEquals(expected.toArray(), list.toArray());
            assertEquals(4, list.size());
            assertEquals("a1", list.getHead().getData());
            assertEquals("a4", list.getTail().getData());

            assertEquals("a1", list.removeLastOccurrence("a1"));
            expected.remove(0);
            assertArrayEquals(expected.toArray(), list.toArray());
            assertEquals(3, list.size());
            assertEquals("a2", list.getHead().getData());
            assertEquals("a4", list.getTail().getData());

            assertEquals("a4", list.removeFromBack());
            expected.remove(2);
            assertArrayEquals(expected.toArray(), list.toArray());
            assertEquals(2, list.size());
            assertEquals("a2", list.getHead().getData());
            assertEquals("a3", list.getTail().getData());

            assertEquals("a2", list.removeFromFront());
            expected.remove(0);
            assertArrayEquals(expected.toArray(), list.toArray());
            assertEquals(1, list.size());
            assertEquals("a3", list.getHead().getData());
            assertEquals("a3", list.getTail().getData());


            assertEquals("a3", list.removeAtIndex(0));
            expected.remove(0);
            assertArrayEquals(expected.toArray(), list.toArray());
            assertEquals(0, list.size());
            assertNull(list.getHead());
            assertNull(list.getTail());
        }

        @Test (timeout = TIMEOUT)
        public void testRemoveLastOccurrenceDuplicateHead1() {
            list.addAtIndex(0, "a1");
            list.addAtIndex(1, "a2");
            list.addAtIndex(2, "a3");
            list.addAtIndex(3, "a1");
            list.addAtIndex(4, "a4");

            List<String> expected = new ArrayList<>();
            expected.addAll(Arrays.asList(new String[]{"a1", "a2", "a3", "a1", "a4"}));

            assertEquals("a1", list.removeLastOccurrence("a1"));
            expected.remove(3);
            assertArrayEquals(expected.toArray(), list.toArray());
            assertEquals(4, list.size());
            assertEquals("a1", list.getHead().getData());
            assertEquals("a4", list.getTail().getData());
        }

        @Test (timeout = TIMEOUT)
        public void testRemoveLastOccurrenceDuplicateHead2() {
            list.addAtIndex(0, "a1");
            list.addAtIndex(1, "a2");
            list.addAtIndex(2, "a3");
            list.addAtIndex(3, "a4");
            list.addAtIndex(4, "a1");

            List<String> expected = new ArrayList<>();
            expected.addAll(Arrays.asList(new String[]{"a1", "a2", "a3", "a4", "a1"}));

            assertEquals("a1", list.removeLastOccurrence("a1"));
            expected.remove(4);
            assertArrayEquals(expected.toArray(), list.toArray());
            assertEquals(4, list.size());
            assertEquals("a1", list.getHead().getData());
            assertEquals("a4", list.getTail().getData());
        }

        @Test (timeout = TIMEOUT)
        public void testRemoveLastOccurrenceMultiples() {
            list.addAtIndex(0, "a1");
            list.addAtIndex(1, "a2");
            list.addAtIndex(2, "a1");
            list.addAtIndex(3, "a1");
            list.addAtIndex(4, "a3");

            List<String> expected = new ArrayList<>();
            expected.addAll(Arrays.asList(new String[]{"a1", "a2", "a1", "a1", "a3"}));

            assertEquals("a1", list.removeLastOccurrence("a1"));
            expected.remove(3);
            assertArrayEquals(expected.toArray(), list.toArray());
            assertEquals(4, list.size());
            assertEquals("a1", list.getHead().getData());
            assertEquals("a3", list.getTail().getData());
        }

        @Test (timeout = TIMEOUT)
        public void testGet() {
            list.addToBack("a1");
            list.addToBack("b2");
            list.addToBack("c3");
            list.addToBack("d4");
            list.addToBack("e5");

            assertEquals("a1", list.get(0));
            assertEquals("b2", list.get(1));
            assertEquals("c3", list.get(2));
            assertEquals("d4", list.get(3));
            assertEquals("e5", list.get(4));
        }
    }
}
