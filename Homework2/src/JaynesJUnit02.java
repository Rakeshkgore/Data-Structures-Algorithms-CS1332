import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * @author Tahlee Jaynes
 * @version 1.0
 */

@RunWith(JaynesJUnit02.class)
@Suite.SuiteClasses({JaynesJUnit02.TestArrayQueue.class, JaynesJUnit02.TestLinkedQueue.class, JaynesJUnit02.TestArrayStack.class,
    JaynesJUnit02.TestLinkedStack.class})

public class JaynesJUnit02 extends Suite {
    private static final int TIMEOUT = 200;

    public JaynesJUnit02(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        super(klass, builder);
    }

    public static class TestArrayQueue {
        ArrayQueue<Integer> queue;

        @Before
        public void setUp() {
            queue = new ArrayQueue<>();
        }

        @Test (timeout = TIMEOUT)
        public void testInit() {
            assertEquals(0, queue.size());
            assertArrayEquals(new Integer[9], queue.getBackingArray());
        }

        @Test (timeout = TIMEOUT)
        public void testSize0() {
            queue.enqueue(0);

            assertEquals(1, queue.size());
            assertArrayEquals(new Integer[]{0, null, null, null, null, null, null, null, null}, queue.getBackingArray());
            assertEquals((Integer) 0, queue.peek());
        }

        @Test (timeout = TIMEOUT)
        public void testSize1() {
            queue.enqueue(0);
            queue.enqueue(1);

            assertEquals(2, queue.size());
            assertArrayEquals(new Integer[]{0, 1, null, null, null, null, null, null, null}, queue.getBackingArray());
            assertEquals((Integer) 0, queue.peek());

            assertEquals((Integer) 0, queue.dequeue());
            assertEquals(1, queue.size());
            assertArrayEquals(new Integer[]{null, 1, null, null, null, null, null, null, null}, queue.getBackingArray());
            assertEquals((Integer) 1, queue.peek());
        }

        @Test (timeout = TIMEOUT)
        public void testSize2() {
            queue.enqueue(0);
            queue.enqueue(1);
            queue.enqueue(2);

            assertEquals(3, queue.size());
            assertArrayEquals(new Integer[]{0, 1, 2, null, null, null, null, null, null}, queue.getBackingArray());
            assertEquals((Integer) 0, queue.peek());

            assertEquals((Integer) 0, queue.dequeue());
            assertEquals(2, queue.size());
            assertArrayEquals(new Integer[]{null, 1, 2, null, null, null, null, null, null}, queue.getBackingArray());
            assertEquals((Integer) 1, queue.peek());
        }

        @Test (timeout = TIMEOUT)
        public void testEnqueue() {
            Integer[] hold = new Integer[9];
            List<Integer> expected = new ArrayList<>(Arrays.asList(hold));

            // [0, 1, 2, 3, 4, 5, null, null, null]
            for (int i = 0; i < 6; i++) {
                queue.enqueue(i);
                expected.set(i, i);
            }

            assertEquals(6, queue.size());
            assertArrayEquals(expected.toArray(), queue.getBackingArray());
            assertEquals((Integer) 0, queue.peek());
        }

        @Test (timeout = TIMEOUT)
        public void testDequeue() {
            Integer[] hold = new Integer[9];
            List<Integer> expected = new ArrayList<>(Arrays.asList(hold));

            // [0, 1, 2, 3, 4, 5, null, null, null]
            for (int i = 0; i < 6; i++) {
                queue.enqueue(i);
                expected.set(i, i);
            }

            // [null, null, null, null, null, null, null, null, null]
            for (int i = 0; i < 6; i++) {
                queue.dequeue();
                expected.set(i, null);
            }

            assertEquals(0, queue.size());
            assertArrayEquals(expected.toArray(), queue.getBackingArray());
        }

        @Test (timeout = TIMEOUT)
        public void testResize() {
            Integer[] hold = new Integer[18];
            List<Integer> expected = new ArrayList<>(Arrays.asList(hold));

            // [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, , null, null, null, null, null, null, null, null, null]
            for (int i = 0; i < 10; i++) {
                queue.enqueue(i);
                expected.set(i, i);
            }

            assertEquals(10, queue.size());
            assertArrayEquals(expected.toArray(), queue.getBackingArray());
            assertEquals((Integer) 0, queue.peek());
        }

        @Test (timeout = TIMEOUT)
        public void testCircular() {
            Integer[] hold = new Integer[9];
            List<Integer> expected = new ArrayList<>(Arrays.asList(hold));

            // [0, 1, 2, 3, 4, null, null, null, null] front -> 0
            for (int i = 0; i < 5; i++) {
                queue.enqueue(i);
                expected.set(i, i);
            }

            assertEquals(5, queue.size());
            assertArrayEquals(expected.toArray(), queue.getBackingArray());
            assertEquals((Integer) 0, queue.peek());

            // [null, null, null, null, null, null, null, null, null] front -> 5
            for (int i = 0; i < 5; i++) {
                queue.dequeue();
                expected.set(i, null);
            }

            assertEquals(0, queue.size());
            assertArrayEquals(expected.toArray(), queue.getBackingArray());

            // [9, 10, 11, 12, null, 5, 6, 7, 8] front -> 5
            for (int i = 5; i < 13; i++) {
                queue.enqueue(i);

                if (i < 9) {
                    expected.set(i, i);
                } else {
                    expected.set(i - 9, i);
                }
            }

            assertEquals(8, queue.size());
            assertArrayEquals(expected.toArray(), queue.getBackingArray());
            assertEquals((Integer) 5, queue.peek());

            // [9, 10, 11, 12, null, null, null, null, null] front -> 0
            for (int i = 5; i < 9; i++) {
                queue.dequeue();
                expected.set(i, null);
            }

            assertEquals(4, queue.size());
            assertArrayEquals(expected.toArray(), queue.getBackingArray());
            assertEquals((Integer) 9, queue.peek());
        }

        @Test (timeout = TIMEOUT)
        public void testCircularResize() {
            Integer[] hold = new Integer[9];
            List<Integer> expected = new ArrayList<>(Arrays.asList(hold));

            // [0, 1, 2, 3, 4, 5, 6, 7, 8] front -> 0
            for (int i = 0; i < 9; i++) {
                queue.enqueue(i);
                expected.set(i, i);
            }

            assertEquals(9, queue.size());
            assertArrayEquals(expected.toArray(), queue.getBackingArray());
            assertEquals((Integer) 0, queue.peek());

            // [null, null, null, null, 4, 5, 6, 7, 8] front -> 4
            for (int i = 0; i < 4; i++) {
                queue.dequeue();
                expected.set(i, null);
            }

            assertEquals(5, queue.size());
            assertArrayEquals(expected.toArray(), queue.getBackingArray());
            assertEquals((Integer) 4, queue.peek());

            // [9, 10, 11, 12, 4, 5, 6, 7, 8] front -> 5
            for (int i = 0; i < 4; i++) {
                queue.enqueue(i + 9);
                expected.set(i, i + 9);
            }

            assertEquals(9, queue.size());
            assertArrayEquals(expected.toArray(), queue.getBackingArray());
            assertEquals((Integer) 4, queue.peek());

            // [4, 5, 6, 7, 8, 9, 10, 11, 12, 13, null, null, null, null, null, null, null, null] front -> 0
            queue.enqueue(13);

            hold = new Integer[18];
            expected = new ArrayList<>(Arrays.asList(hold));

            for (int i = 0; i < 10; i++) {
                expected.set(i, i + 4);
            }

            assertEquals(10, queue.size());
            assertArrayEquals(expected.toArray(), queue.getBackingArray());
            assertEquals((Integer) 4, queue.peek());
        }

        @Test (timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testNullData() {
            queue.enqueue(null);
        }

        @Test (timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testEmpty() {
            queue.dequeue();
            queue.peek();
        }
    }

    public static class TestLinkedQueue {
        LinkedQueue<Integer> queue;

        @Before
        public void setUp() {
            queue = new LinkedQueue<>();
        }

        @Test (timeout = TIMEOUT)
        public void testInit() {
            assertNull(queue.getHead());
            assertNull(queue.getTail());
        }

        @Test (timeout = TIMEOUT)
        public void testSize0() {
            // 0
            queue.enqueue(0);

            assertEquals(1, queue.size());
            assertEquals((Integer) 0, queue.getHead().getData());
            assertEquals((Integer) 0, queue.getTail().getData());
            assertEquals((Integer) 0, queue.peek());
        }

        @Test (timeout = TIMEOUT)
        public void testSize1() {
            // 0, 1
            queue.enqueue(0);
            queue.enqueue(1);

            assertEquals(2, queue.size());
            assertEquals((Integer) 0, queue.getHead().getData());
            assertEquals((Integer) 1, queue.getTail().getData());
            assertEquals((Integer) 0, queue.peek());

            // 1
            assertEquals((Integer) 0, queue.dequeue());
            assertEquals(1, queue.size());
            assertEquals((Integer) 1, queue.getHead().getData());
            assertEquals((Integer) 1, queue.getTail().getData());
            assertEquals((Integer) 1, queue.peek());
        }

        @Test (timeout = TIMEOUT)
        public void testSize2() {
            // 0, 1, 2
            queue.enqueue(0);
            queue.enqueue(1);
            queue.enqueue(2);

            assertEquals(3, queue.size());
            assertEquals((Integer) 0, queue.getHead().getData());
            assertEquals((Integer) 2, queue.getTail().getData());
            assertEquals((Integer) 0, queue.peek());

            // 1, 2
            assertEquals((Integer) 0, queue.dequeue());
            assertEquals(2, queue.size());
            assertEquals((Integer) 1, queue.getHead().getData());
            assertEquals((Integer) 2, queue.getTail().getData());
            assertEquals((Integer) 1, queue.peek());
        }

        @Test (timeout = TIMEOUT)
        public void testEnqueue() {
            // 0, 1, 2, 3, 4, 5
            for (int i = 0; i < 6; i++) {
                queue.enqueue(i);
            }

            assertEquals(6, queue.size());
            assertEquals((Integer) 0, queue.getHead().getData());
            assertEquals((Integer) 5, queue.getTail().getData());
            assertEquals((Integer) 0, queue.peek());
        }

        @Test (timeout = TIMEOUT)
        public void testDequeue() {
            // 0, 1, 2, 3, 4, 5
            for (int i = 0; i < 6; i++) {
                queue.enqueue(i);
            }

            // {empty}
            for (int i = 0; i < 6; i++) {
                queue.dequeue();
            }

            assertEquals(0, queue.size());
            assertNull(queue.getHead());
            assertNull(queue.getTail());
        }

        @Test (timeout = TIMEOUT)
        public void testMixEnqueueDequeue() {
            // 0, 1, 2
            for (int i = 0; i < 3; i++) {
                queue.enqueue(i);
            }

            assertEquals(3, queue.size());
            assertEquals((Integer) 0, queue.getHead().getData());
            assertEquals((Integer) 2, queue.getTail().getData());
            assertEquals((Integer) 0, queue.peek());

            // 1, 2
            assertEquals((Integer) 0, queue.dequeue());
            // 2
            assertEquals((Integer) 1, queue.dequeue());

            assertEquals(1, queue.size());
            assertEquals((Integer) 2, queue.getHead().getData());
            assertEquals((Integer) 2, queue.getTail().getData());
            assertEquals((Integer) 2, queue.peek());

            // 2, 3, 4, 5, 6
            for (int i = 3; i < 7; i++) {
                queue.enqueue(i);
            }

            assertEquals(5, queue.size());
            assertEquals((Integer) 2, queue.getHead().getData());
            assertEquals((Integer) 6, queue.getTail().getData());
            assertEquals((Integer) 2, queue.peek());
        }

        @Test (timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testNullData() {
            queue.enqueue(null);
        }

        @Test (timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testEmpty() {
            queue.dequeue();
            queue.peek();
        }
    }

    public static class TestArrayStack {
        private ArrayStack<Integer> stack;

        @Before
        public void setUp() {
            stack = new ArrayStack<>();
        }

        @Test (timeout = TIMEOUT)
        public void testInit() {
            assertEquals(0, stack.size());
            assertArrayEquals(new Integer[9], stack.getBackingArray());
        }

        @Test (timeout = TIMEOUT)
        public void testSize0() {
            stack.push(0);

            assertEquals(1, stack.size());
            assertArrayEquals(new Integer[]{0, null, null, null, null, null, null, null, null}, stack.getBackingArray());
            assertEquals((Integer) 0, stack.peek());
        }

        @Test (timeout = TIMEOUT)
        public void testSize1() {
            stack.push(1);
            stack.push(0);

            assertEquals(2, stack.size());
            assertArrayEquals(new Integer[]{1, 0, null, null, null, null, null, null, null},stack.getBackingArray());
            assertEquals((Integer) 0, stack.peek());

            assertEquals((Integer) 0, stack.pop());
            assertEquals(1, stack.size());
            assertArrayEquals(new Integer[]{1, null, null, null, null, null, null, null, null}, stack.getBackingArray());
            assertEquals((Integer) 1, stack.peek());
        }

        @Test (timeout = TIMEOUT)
        public void testSize2() {
            stack.push(0);
            stack.push(1);
            stack.push(2);

            assertEquals(3, stack.size());
            assertArrayEquals(new Integer[]{0, 1, 2, null, null, null, null, null, null}, stack.getBackingArray());
            assertEquals((Integer) 2, stack.peek());

            assertEquals((Integer) 2, stack.pop());
            assertEquals(2, stack.size());
            assertArrayEquals(new Integer[]{0, 1, null, null, null, null, null, null, null}, stack.getBackingArray());
            assertEquals((Integer) 1, stack.peek());
        }

        @Test (timeout = TIMEOUT)
        public void testPush() {
            Integer[] hold = new Integer[9];
            List<Integer> expected = new ArrayList<>(Arrays.asList(hold));

            // [0, 1, 2, 3, 4, 5, null, null, null]
            for (int i = 0; i < 6; i++) {
                stack.push(i);
                expected.set(i, i);
            }

            assertEquals(6, stack.size());
            assertArrayEquals(expected.toArray(), stack.getBackingArray());
            assertEquals((Integer) 5, stack.peek());
        }

        @Test (timeout = TIMEOUT)
        public void testPop() {
            Integer[] hold = new Integer[9];
            List<Integer> expected = new ArrayList<>(Arrays.asList(hold));

            // [0, 1, 2, 3, 4, 5, null, null]
            for (int i = 0; i < 6; i++) {
                stack.push(i);
                expected.set(i, i);
            }

            // [null, null, null, null, null, null, null, null, null]
            for (int i = 0; i < 6; i++) {
                stack.pop();
                expected.set(i, null);
            }

            assertEquals(0, stack.size());
            assertArrayEquals(expected.toArray(), stack.getBackingArray());
        }

        @Test (timeout = TIMEOUT)
        public void testResize() {
            Integer[] hold = new Integer[18];
            List<Integer> expected = new ArrayList<>(Arrays.asList(hold));

            // [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, null, null, null, null, null, null, null, null]
            for (int i = 0; i < 10; i++) {
                stack.push(i);
                expected.set(i, i);
            }

            assertEquals(10, stack.size());
            assertArrayEquals(expected.toArray(), stack.getBackingArray());
            assertEquals((Integer) 9, stack.peek());
        }

        @Test (timeout = TIMEOUT)
        public void testMixPushPop() {
            Integer[] hold = new Integer[9];
            List<Integer> expected = new ArrayList<>(Arrays.asList(hold));

            // [0, 1, 2, 3, 4, null, null, null, null]
            for (int i = 0; i < 5; i++) {
                stack.push(i);
                expected.set(i, i);
            }

            assertEquals(5, stack.size());
            assertArrayEquals(expected.toArray(), stack.getBackingArray());
            assertEquals((Integer) 4, stack.peek());

            // [0, 1, null, null, null, null, null, null, null]
            for (int i = 4; i > 1; i--) {
                stack.pop();
                expected.set(i, null);
            }

            assertEquals(2, stack.size());
            assertArrayEquals(expected.toArray(), stack.getBackingArray());
            assertEquals((Integer) 1, stack.peek());

            // [0, 1, 5, null, null, null, null, null, null]
            stack.push(5);
            expected.set(2, 5);
            assertEquals(3, stack.size());
            assertArrayEquals(expected.toArray(), stack.getBackingArray());
            assertEquals((Integer) 5, stack.peek());

            // [0, 1, null, null, null, null, null, null, null]
            assertEquals((Integer) 5, stack.pop());
            expected.set(2, null);
            assertEquals(2, stack.size());
            assertArrayEquals(expected.toArray(), stack.getBackingArray());
            assertEquals((Integer) 1, stack.peek());
        }

        @Test (timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testNullData() {
            stack.push(null);
        }

        @Test (timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testEmpty() {
            stack.pop();
            stack.peek();
        }
    }

    public static class TestLinkedStack {
        LinkedStack<Integer> stack;

        @Before
        public void setUp() {
            stack = new LinkedStack<>();
        }

        @Test (timeout = TIMEOUT)
        public void testInit() {
            assertNull(stack.getHead());
        }

        @Test (timeout = TIMEOUT)
        public void testSize0() {
            stack.push(0);

            assertEquals(1, stack.size());
            assertEquals((Integer) 0, stack.getHead().getData());
            assertEquals((Integer) 0, stack.peek());
        }

        @Test (timeout = TIMEOUT)
        public void testSize1() {
            // 1, 0
            stack.push(1);
            stack.push(0);

            assertEquals(2, stack.size());
            assertEquals((Integer) 0, stack.getHead().getData());
            assertEquals((Integer) 0, stack.peek());

            // 1
            assertEquals((Integer) 0, stack.pop());
            assertEquals(1, stack.size());
            assertEquals((Integer) 1, stack.getHead().getData());
            assertEquals((Integer) 1, stack.peek());
        }

        @Test (timeout = TIMEOUT)
        public void testSize2() {
            // 0, 1, 2
            stack.push(2);
            stack.push(1);
            stack.push(0);

            assertEquals(3, stack.size());
            assertEquals((Integer) 0, stack.getHead().getData());
            assertEquals((Integer) 0, stack.peek());

            // 1, 2
            assertEquals((Integer) 0, stack.pop());
            assertEquals(2, stack.size());
            assertEquals((Integer) 1, stack.getHead().getData());
            assertEquals((Integer) 1, stack.peek());
        }

        @Test (timeout = TIMEOUT)
        public void testPush() {
            // 5, 4, 3, 2, 1, 0
            for (int i = 0; i < 6; i++) {
                stack.push(i);
            }

            assertEquals(6, stack.size());
            assertEquals((Integer) 5, stack.getHead().getData());
            assertEquals((Integer) 5, stack.peek());
        }

        @Test (timeout = TIMEOUT)
        public void testPop() {
            // 5, 4, 3, 2, 1, 0
            for (int i = 0; i < 6; i++) {
                stack.push(i);
            }

            // {empty}
            for (int i = 0; i < 6; i++) {
                stack.pop();
            }

            assertEquals(0, stack.size());
            assertNull(stack.getHead());
        }

        @Test (timeout = TIMEOUT)
        public void testMixPushPop() {
            // 2, 1, 0
            for (int i = 0; i < 3; i++) {
                stack.push(i);
            }

            assertEquals(3, stack.size());
            assertEquals((Integer) 2, stack.getHead().getData());
            assertEquals((Integer) 2, stack.peek());

            // 1, 0
            assertEquals((Integer) 2, stack.pop());
            // 0
            assertEquals((Integer) 1, stack.pop());

            assertEquals(1, stack.size());
            assertEquals((Integer) 0, stack.getHead().getData());
            assertEquals((Integer) 0, stack.peek());

            // 5, 4, 3, 2, 0
            for (int i = 2; i < 6; i++) {
                stack.push(i);
            }

            assertEquals(5, stack.size());
            assertEquals((Integer) 5, stack.getHead().getData());
            assertEquals((Integer) 5, stack.peek());
        }

        @Test (timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testNullData() {
            stack.push(null);
        }

        @Test (timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testEmpty() {
            stack.pop();
            stack.peek();
        }
    }
}