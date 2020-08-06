import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author Tahlee Jaynes
 * @version 1.0
 */

@RunWith(JaynesJUnit06.class)
@Suite.SuiteClasses({JaynesJUnit06.TestSize0.class, JaynesJUnit06.TestSize1.class, JaynesJUnit06.TestSize2.class, JaynesJUnit06.TestOddLengthNegative.class, JaynesJUnit06.TestOddLengthPositiveNegative.class,
        JaynesJUnit06.TestEvenLengthPositive.class, JaynesJUnit06.TestEvenLengthPositiveNegative.class, JaynesJUnit06.TestReverse.class, JaynesJUnit06.TestExceptions.class, JaynesJUnit06.TestDuplicates.class,
        JaynesJUnit06.TestSorted.class, JaynesJUnit06.TestLSDIntegerMinMaxValue.class})
public class JaynesJUnit06 extends Suite {
    private static final int TIMEOUT = 200;
    private static Integer[] expected;
    private static Integer[] original;
    private static int[] originalLSD;

    public JaynesJUnit06(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        super(klass, builder);
    }

    // This is an implementation of the Comparator interface that will allow you to pass in Comparator objects into your methods
    public static class Comp implements Comparator<Integer> {
        public int compare(Integer a, Integer b) {
            return (a.compareTo(b));
        }
    }

    public static class TestSize0 {
        @Before
        public void setUp() {
            originalLSD = new int[0];
            original = new Integer[0];
            expected = new Integer[0];
        }

        @Test(timeout = TIMEOUT)
        public void testInsertion() {
            Sorting.insertionSort(original, new Comp());
            assertEquals(Arrays.toString(expected), Arrays.toString(original));
        }

        @Test(timeout = TIMEOUT)
        public void testMergeSort() {
            Sorting.mergeSort(original, new Comp());
            assertEquals(Arrays.toString(expected), Arrays.toString(original));
        }

        @Test(timeout = TIMEOUT)
        public void testLSD() {
            Sorting.lsdRadixSort(originalLSD);
            assertEquals(Arrays.toString(expected), Arrays.toString(originalLSD));
        }
    }

    public static class TestSize1 {
        @Before
        public void setUp() {
            originalLSD = new int[]{1};
            original = new Integer[]{1};
            expected = new Integer[]{1};
        }

        @Test(timeout = TIMEOUT)
        public void testInsertion() {
            Sorting.insertionSort(original, new Comp());
            assertEquals(Arrays.toString(expected), Arrays.toString(original));
        }

        @Test(timeout = TIMEOUT)
        public void testMerge() {
            Sorting.mergeSort(original, new Comp());
            assertEquals(Arrays.toString(expected), Arrays.toString(original));
        }

        @Test(timeout = TIMEOUT)
        public void testLSD() {
            Sorting.lsdRadixSort(originalLSD);
            assertEquals(Arrays.toString(expected), Arrays.toString(originalLSD));
        }

        @Test(timeout = TIMEOUT)
        public void testK() {
            assertEquals((Integer) 1, Sorting.kthSelect(1, original, new Comp(), new Random()));
        }
    }

    public static class TestSize2 {
        @Before
        public void setUp() {
            originalLSD = new int[]{1, -2};
            original = new Integer[]{1, -2};
            expected = new Integer[]{-2, 1};
        }

        @Test(timeout = TIMEOUT)
        public void testInsertion() {
            Sorting.insertionSort(original, new Comp());
            assertEquals(Arrays.toString(expected), Arrays.toString(original));
        }

        @Test(timeout = TIMEOUT)
        public void testMerge() {
            Sorting.mergeSort(original, new Comp());
            assertEquals(Arrays.toString(expected), Arrays.toString(original));
        }

        @Test(timeout = TIMEOUT)
        public void testLSD() {
            Sorting.lsdRadixSort(originalLSD);
            assertEquals(Arrays.toString(expected), Arrays.toString(originalLSD));
        }

        @Test(timeout = TIMEOUT)
        public void testK() {
            assertEquals((Integer) (-2), Sorting.kthSelect(1, original, new Comp(), new Random()));
            assertEquals((Integer) 1, Sorting.kthSelect(2, original, new Comp(), new Random()));
        }
    }

    public static class TestExceptions {
        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testNullArray() {
            Sorting.insertionSort(null, new Comp());
            Sorting.mergeSort(null, new Comp());
            Sorting.lsdRadixSort(null);
            Sorting.kthSelect(0, null, new Comp(), new Random());
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testNullComparator() {
            originalLSD = new int[1];
            original = new Integer[1];

            Sorting.insertionSort(original, null);
            Sorting.mergeSort(original, null);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testKNullRand() {
            original = new Integer[1];

            Sorting.kthSelect(1, original, new Comp(), null);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testKIllegal() {
            original = new Integer[1];

            Sorting.kthSelect(-1, original, new Comp(), new Random());
            Sorting.kthSelect(0, original, new Comp(), new Random());
            Sorting.kthSelect(2, original, new Comp(), new Random());
        }
    }

    // Odd length array with only negative numbers and zero
    public static class TestOddLengthNegative {
        @Before
        public void setUp() {
            originalLSD = new int[]{-26, -81, -3, -495, -16, 0, -2020};
            original = new Integer[]{-26, -81, -3, -495, -16, 0, -2020};
            expected = new Integer[]{-2020, -495, -81, -26, -16, -3, 0};
        }

        @Test(timeout = TIMEOUT)
        public void testInsertion() {
            Sorting.insertionSort(original, new Comp());
            assertEquals(Arrays.toString(expected), Arrays.toString(original));
        }

        @Test(timeout = TIMEOUT)
        public void testMerge() {
            Sorting.mergeSort(original, new Comp());
            assertEquals(Arrays.toString(expected), Arrays.toString(original));
        }

        @Test(timeout = TIMEOUT)
        public void testLSD() {
            Sorting.lsdRadixSort(originalLSD);
            assertEquals(Arrays.toString(expected), Arrays.toString(originalLSD));
        }

        @Test(timeout = TIMEOUT)
        public void testK() {
            assertEquals((Integer) (-2020), Sorting.kthSelect(1, original, new Comp(), new Random()));
            assertEquals((Integer) (-81), Sorting.kthSelect(3, original, new Comp(), new Random()));
            assertEquals((Integer) 0, Sorting.kthSelect(7, original, new Comp(), new Random()));
        }
    }

    // Odd length array with positive/negative numbers and 0
    public static class TestOddLengthPositiveNegative {
        @Before
        public void setUp() {
            originalLSD = new int[]{-26, 81, -3, 495, -16, 0, -2020};
            original = new Integer[]{-26, 81, -3, 495, -16, 0, -2020};
            expected = new Integer[]{-2020,  -26, -16, -3, 0, 81, 495};
        }

        @Test(timeout = TIMEOUT)
        public void testInsertion() {
            Sorting.insertionSort(original, new Comp());
            assertEquals(Arrays.toString(expected), Arrays.toString(original));
        }

        @Test(timeout = TIMEOUT)
        public void testMerge() {
            Sorting.mergeSort(original, new Comp());
            assertEquals(Arrays.toString(expected), Arrays.toString(original));
        }

        @Test(timeout = TIMEOUT)
        public void testLSD() {
            Sorting.lsdRadixSort(originalLSD);
            assertEquals(Arrays.toString(expected), Arrays.toString(originalLSD));
        }

        @Test(timeout = TIMEOUT)
        public void testK() {
            assertEquals((Integer) (-2020), Sorting.kthSelect(1, original, new Comp(), new Random()));
            assertEquals((Integer) (-16), Sorting.kthSelect(3, original, new Comp(), new Random()));
            assertEquals((Integer) 495, Sorting.kthSelect(7, original, new Comp(), new Random()));
        }
    }

    // Even length array with only positive numbers and zero
    public static class TestEvenLengthPositive {
        @Before
        public void setUp() {
            // Note there is a duplicate in here
            originalLSD = new int[]{36, 814, 3012, 7, 92, 3012, 678, 45};
            original = new Integer[]{36, 814, 3012, 7, 92, 3012, 678, 45};
            expected = new Integer[]{7, 36, 45, 92, 678, 814, 3012, 3012};
        }

        @Test(timeout = TIMEOUT)
        public void testInsertion() {
            Sorting.insertionSort(original, new Comp());
            assertEquals(Arrays.toString(expected), Arrays.toString(original));
        }

        @Test(timeout = TIMEOUT)
        public void testMerge() {
            Sorting.mergeSort(original, new Comp());
            assertEquals(Arrays.toString(expected), Arrays.toString(original));
        }

        @Test(timeout = TIMEOUT)
        public void testLSD() {
            Sorting.lsdRadixSort(originalLSD);
            assertEquals(Arrays.toString(expected), Arrays.toString(originalLSD));
        }

        @Test(timeout = TIMEOUT)
        public void testK() {
            assertEquals((Integer) 7, Sorting.kthSelect(1, original, new Comp(), new Random()));
            assertEquals((Integer) 92, Sorting.kthSelect(4, original, new Comp(), new Random()));
            assertEquals((Integer) 3012, Sorting.kthSelect(7, original, new Comp(), new Random()));
            assertEquals((Integer) 3012, Sorting.kthSelect(8, original, new Comp(), new Random()));
        }
    }

    // Even length array with positive/negative numbers and zero
    public static class TestEvenLengthPositiveNegative {
        @Before
        public void setUp() {
            originalLSD = new int[]{-36, 814, 3012, 7, 92, -3012, -678, 45};
            original = new Integer[]{-36, 814, 3012, 7, 92, -3012, -678, 45};
            expected = new Integer[]{-3012, -678, -36, 7, 45, 92, 814, 3012};
        }

        @Test(timeout = TIMEOUT)
        public void testInsertion() {
            Sorting.insertionSort(original, new Comp());
            assertEquals(Arrays.toString(expected), Arrays.toString(original));
        }

        @Test(timeout = TIMEOUT)
        public void testMerge() {
            Sorting.mergeSort(original, new Comp());
            assertEquals(Arrays.toString(expected), Arrays.toString(original));
        }

        @Test(timeout = TIMEOUT)
        public void testLSD() {
            Sorting.lsdRadixSort(originalLSD);
            assertEquals(Arrays.toString(expected), Arrays.toString(originalLSD));
        }

        @Test(timeout = TIMEOUT)
        public void testK() {
            assertEquals((Integer) (-3012), Sorting.kthSelect(1, original, new Comp(), new Random()));
            assertEquals((Integer) (-36), Sorting.kthSelect(3, original, new Comp(), new Random()));
            assertEquals((Integer) 814, Sorting.kthSelect(7, original, new Comp(), new Random()));
            assertEquals((Integer) 3012, Sorting.kthSelect(8, original, new Comp(), new Random()));
        }
    }

    // Tests an array with data pre-sorted in the reverse order, greatest to smallest
    public static class TestReverse {
        @Before
        public void setUp() {
            originalLSD = new int[]{3, 2, 1, 0, -1, -2, -3};
            original = new Integer[]{3, 2, 1, 0, -1, -2, -3};
            expected = new Integer[]{-3, -2, -1, 0, 1, 2, 3};
        }

        @Test(timeout = TIMEOUT)
        public void testInsertion() {
            Sorting.insertionSort(original, new Comp());
            assertEquals(Arrays.toString(expected), Arrays.toString(original));
        }

        @Test(timeout = TIMEOUT)
        public void testMerge() {
            Sorting.mergeSort(original, new Comp());
            assertEquals(Arrays.toString(expected), Arrays.toString(original));
        }

        @Test(timeout = TIMEOUT)
        public void testLSD() {
            Sorting.lsdRadixSort(originalLSD);
            assertEquals(Arrays.toString(expected), Arrays.toString(originalLSD));
        }

        @Test(timeout = TIMEOUT)
        public void testK() {
            assertEquals((Integer) (-3), Sorting.kthSelect(1, original, new Comp(), new Random()));
            assertEquals((Integer) (-1), Sorting.kthSelect(3, original, new Comp(), new Random()));
            assertEquals((Integer) 0, Sorting.kthSelect(4, original, new Comp(), new Random()));
            assertEquals((Integer) 3, Sorting.kthSelect(7, original, new Comp(), new Random()));
        }
    }

    // Tests an array with a hefty number of duplicates
    public static class TestDuplicates {
        @Before
        public void setUp() {
            originalLSD = new int[]{3, 2, 3, 4, 3, 2, 2, 3};
            original = new Integer[]{3, 2, 3, 4, 3, 2, 2, 3};
            expected = new Integer[]{2, 2, 2, 3, 3, 3, 3, 4};
        }

        @Test(timeout = TIMEOUT)
        public void testInsertion() {
            Sorting.insertionSort(original, new Comp());
            assertEquals(Arrays.toString(expected), Arrays.toString(original));
        }

        @Test(timeout = TIMEOUT)
        public void testMerge() {
            Sorting.mergeSort(original, new Comp());
            assertEquals(Arrays.toString(expected), Arrays.toString(original));
        }

        @Test(timeout = TIMEOUT)
        public void testLSD() {
            Sorting.lsdRadixSort(originalLSD);
            assertEquals(Arrays.toString(expected), Arrays.toString(originalLSD));
        }

        @Test(timeout = TIMEOUT)
        public void testK() {
            for (int i = 0; i < 3; i++) {
                assertEquals((Integer) 2, Sorting.kthSelect(i + 1, original, new Comp(), new Random()));
            }

            for (int i = 4; i < original.length; i++) {
                assertEquals((Integer) 3, Sorting.kthSelect(i, original, new Comp(), new Random()));
            }

            assertEquals((Integer) 4, Sorting.kthSelect(8, original, new Comp(), new Random()));
        }
    }

    // Tests an array whose elements are already in the correct order
    public static class TestSorted {
        @Before
        public void setUp() {
            originalLSD = new int[]{-3, -2, -1, 0 , 1, 2, 3};
            original = new Integer[]{-3, -2, -1, 0 , 1, 2, 3};
            expected = new Integer[]{-3, -2, -1, 0 , 1, 2, 3};
        }

        @Test(timeout = TIMEOUT)
        public void testInsertion() {
            Sorting.insertionSort(original, new Comp());
            assertEquals(Arrays.toString(expected), Arrays.toString(original));
        }

        @Test(timeout = TIMEOUT)
        public void testMerge() {
            Sorting.mergeSort(original, new Comp());
            assertEquals(Arrays.toString(expected), Arrays.toString(original));
        }

        @Test(timeout = TIMEOUT)
        public void testLSD() {
            Sorting.lsdRadixSort(originalLSD);
            assertEquals(Arrays.toString(expected), Arrays.toString(originalLSD));
        }

        @Test(timeout = TIMEOUT)
        public void testK() {
            for (int i = 0; i < original.length; i++) {
                assertEquals((Integer) (i - 3), Sorting.kthSelect(i + 1, original, new Comp(), new Random()));
            }
        }
    }

    public static class TestLSDIntegerMinMaxValue {
        @Test(timeout = TIMEOUT)
        public void testIntegerMinValue() {
            originalLSD = new int[]{-26, 81, -3, 495, Integer.MIN_VALUE, -16, 0, -2020};
            expected = new Integer[]{Integer.MIN_VALUE, -2020,  -26, -16, -3, 0, 81, 495};

            Sorting.lsdRadixSort(originalLSD);

            assertEquals(Arrays.toString(expected), Arrays.toString(originalLSD));
        }

        @Test(timeout = TIMEOUT)
        public void testIntegerMaxValue() {
            originalLSD = new int[]{-26, 81, -3, 495, Integer.MAX_VALUE, -16, 0, -2020};
            expected = new Integer[]{-2020,  -26, -16, -3, 0, 81, 495, Integer.MAX_VALUE};

            Sorting.lsdRadixSort(originalLSD);

            assertEquals(Arrays.toString(expected), Arrays.toString(originalLSD));
        }

        @Test(timeout = TIMEOUT)
        public void testIntegerMinAndMaxValue() {
            originalLSD = new int[]{-26, 81, -3, 495, Integer.MAX_VALUE, -16, 0, Integer.MIN_VALUE, -2020};
            expected = new Integer[]{Integer.MIN_VALUE, -2020,  -26, -16, -3, 0, 81, 495, Integer.MAX_VALUE};

            Sorting.lsdRadixSort(originalLSD);

            assertEquals(Arrays.toString(expected), Arrays.toString(originalLSD));
        }

        @Test(timeout = TIMEOUT)
        public void testIntegerMinAndCloseMinValue() {
            originalLSD = new int[]{-26, 81, Integer.MIN_VALUE + 1, -3, 495, -16, 0, Integer.MIN_VALUE, -2020};
            expected = new Integer[]{Integer.MIN_VALUE, Integer.MIN_VALUE + 1, -2020,  -26, -16, -3, 0, 81, 495};

            Sorting.lsdRadixSort(originalLSD);

            assertEquals(Arrays.toString(expected), Arrays.toString(originalLSD));
        }

        @Test(timeout = TIMEOUT)
        public void testIntegerMaxAndCloseMaxValue() {
            originalLSD = new int[]{-26, 81, -3, 495, Integer.MAX_VALUE, -16, 0, -2020, Integer.MAX_VALUE - 1};
            expected = new Integer[]{-2020,  -26, -16, -3, 0, 81, 495, Integer.MAX_VALUE - 1, Integer.MAX_VALUE};

            Sorting.lsdRadixSort(originalLSD);

            assertEquals(Arrays.toString(expected), Arrays.toString(originalLSD));
        }

        @Test(timeout = TIMEOUT)
        public void testIntegerMinMaxAndCloseValues() {
            originalLSD = new int[]{-26, 81, Integer.MIN_VALUE + 1, -3, 495, Integer.MAX_VALUE, -16, 0, Integer.MIN_VALUE, -2020, Integer.MAX_VALUE - 1};
            expected = new Integer[]{Integer.MIN_VALUE, Integer.MIN_VALUE + 1, -2020,  -26, -16, -3, 0, 81, 495, Integer.MAX_VALUE - 1, Integer.MAX_VALUE};

            Sorting.lsdRadixSort(originalLSD);

            assertEquals(Arrays.toString(expected), Arrays.toString(originalLSD));
        }
    }
}
