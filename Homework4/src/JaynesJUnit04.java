import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * @author Tahlee Jaynes
 * @version 1.0
 */

@RunWith(JaynesJUnit04.class)
@Suite.SuiteClasses({JaynesJUnit04.TestSize0.class, JaynesJUnit04.TestSize1.class, JaynesJUnit04.TestSize2.class, JaynesJUnit04.TestExceptions.class, JaynesJUnit04.TestLargerData.class, JaynesJUnit04.TestLargerDataChain.class})

public class JaynesJUnit04 extends Suite {
    private static final int TIMEOUT = 200;
    private static ExternalChainingHashMap<Integer, String> map;
    private static ExternalChainingMapEntry<Integer, String>[] expected;

    public JaynesJUnit04(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        super(klass, builder);
    }

    public static class TestSize0 {
        @Before
        public void setUp() {
            expected = new ExternalChainingMapEntry[13];
            map = new ExternalChainingHashMap<>();
        }

        @Test(timeout = TIMEOUT)
        public void init() {
            assertEquals(0, map.size());
            assertArrayEquals(expected, map.getTable());
        }

        @Test(timeout = TIMEOUT)
        public void testPut() {
            assertEquals(null, map.put(0, "0"));
            expected[0] = new ExternalChainingMapEntry<>(0, "0");

            assertEquals(1, map.size());
            assertArrayEquals(expected, map.getTable());
        }

        @Test(timeout = TIMEOUT)
        public void testKeySet() {
            assertEquals(new HashSet<>(), map.keySet());
        }

        @Test(timeout = TIMEOUT)
        public void testValues() {
            assertEquals(new ArrayList<>(), map.values());
        }

        @Test(timeout = TIMEOUT)
        public void testClear() {
            map.clear();

            assertEquals(0, map.size());
            assertArrayEquals(expected, map.getTable());
        }
    }

    public static class TestSize1 {
        @Before
        public void setUp() {
            map = new ExternalChainingHashMap<>();
            expected = new ExternalChainingMapEntry[13];

            map.put(0, "0");
            expected[0] = new ExternalChainingMapEntry<>(0, "0");
        }

        @Test(timeout = TIMEOUT)
        public void init() {
            assertEquals(1, map.size());
            assertArrayEquals(expected, map.getTable());
        }

        @Test(timeout = TIMEOUT)
        public void testPut() {
            assertEquals(null, map.put(1, "1"));
            expected[1] = new ExternalChainingMapEntry<>(1, "1");

            assertEquals(2, map.size());
            assertArrayEquals(expected, map.getTable());
        }

        @Test(timeout = TIMEOUT)
        public void testPutDuplicate() {
            assertEquals("0", map.put(0, "0.1"));

            expected[0].setValue("0.1");

            assertEquals(1, map.size());
            assertArrayEquals(expected, map.getTable());
        }

        @Test(timeout = TIMEOUT)
        public void testRemove() {
            assertEquals("0", map.remove(0));
            assertEquals(0, map.size());
        }

        @Test(timeout = TIMEOUT)
        public void testGet() {
            assertEquals("0", map.get(0));
        }

        @Test(timeout = TIMEOUT)
        public void testContains() {
            assertTrue(map.containsKey(0));
            assertFalse(map.containsKey(1));
        }

        @Test(timeout = TIMEOUT)
        public void testKeySet() {
            assertEquals(new HashSet<>(Arrays.asList(new Integer[]{0})), map.keySet());
        }

        @Test(timeout = TIMEOUT)
        public void testValues() {
            assertEquals(new ArrayList<>(Arrays.asList(new String[]{"0"})), map.values());
        }

        @Test(timeout = TIMEOUT)
        public void testClear() {
            map.clear();
            expected = new ExternalChainingMapEntry[13];

            assertEquals(0, map.size());
            assertArrayEquals(expected, map.getTable());
        }
    }

    public static class TestSize2 {
        @Before
        public void setUp() {
            map = new ExternalChainingHashMap<>();
            expected = new ExternalChainingMapEntry[13];

            map.put(0, "0");
            map.put(3, "3");
            expected[0] = new ExternalChainingMapEntry<>(0, "0");
            expected[3] = new ExternalChainingMapEntry<>(3, "3");
        }

        @Test(timeout = TIMEOUT)
        public void init() {
            assertEquals(2, map.size());
            assertArrayEquals(expected, map.getTable());
        }

        @Test(timeout = TIMEOUT)
        public void testPut() {
            assertEquals(null, map.put(2, "2"));

            expected[2] = new ExternalChainingMapEntry<>(2, "2");

            assertEquals(3, map.size());
            assertArrayEquals(expected, map.getTable());
        }

        @Test(timeout = TIMEOUT)
        public void testPutDuplicate() {
            assertEquals("3", map.put(3, "3.1"));

            expected[3].setValue("3.1");

            assertEquals(2, map.size());
            assertArrayEquals(expected, map.getTable());
        }

        @Test(timeout = TIMEOUT)
        public void testPutChain() {
            assertEquals(null, map.put(16, "16"));

            expected[3] = new ExternalChainingMapEntry<>(16, "16", expected[3]);

            assertEquals(3, map.size());
            assertArrayEquals(expected, map.getTable());
        }

        @Test(timeout = TIMEOUT)
        public void testRemove() {
            assertEquals("3", map.remove(3));

            expected[3] = null;

            assertEquals(1, map.size());
            assertArrayEquals(expected, map.getTable());

            assertEquals("0", map.remove(0));

            expected[0] = null;

            assertEquals(0, map.size());
            assertArrayEquals(expected, map.getTable());
        }

        @Test(timeout = TIMEOUT)
        public void testGet() {
            assertEquals("0", map.get(0));
            assertEquals("3", map.get(3));
        }

        @Test(timeout = TIMEOUT)
        public void testContains() {
            assertTrue(map.containsKey(0));
            assertTrue(map.containsKey(3));
        }

        @Test(timeout = TIMEOUT)
        public void testKeySet() {
            assertEquals(new HashSet<>(Arrays.asList(new Integer[]{0, 3})), map.keySet());
        }

        @Test(timeout = TIMEOUT)
        public void testValues() {
            assertEquals(new ArrayList<>(Arrays.asList(new String[]{"0", "3"})), map.values());
        }

        @Test(timeout = TIMEOUT)
        public void testClear() {
            map.clear();
            expected = new ExternalChainingMapEntry[13];

            assertEquals(0, map.size());
            assertArrayEquals(expected, map.getTable());
        }
    }

    public static class TestExceptions {
        @Before
        public void setUp() {
            map = new ExternalChainingHashMap<>();
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testPutNull() {
            map.put(null, ":D");
            map.put(0, null);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testRemoveNull() {
            map.remove(null);
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testRemoveEmpty() {
            map.remove(0);
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testRemoveNotInMap() {
            map.put(0, "0");
            map.remove(1);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testGetNull() {
            map.get(null);
        }

        @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
        public void testGetNotInMap() {
            map.put(0, "0");
            map.get(1);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testContainsNull() {
            map.containsKey(null);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testResizeSmallLength() {
            map.put(0, "0");
            map.resizeBackingTable(0);
        }
    }

    public static class TestLargerData {
        @Before
        public void setUp() {
            /*
                0 [0]
                1 [1]
                2 [2]
                3 [3]
                4 [4]
                5 [5]
                6 [6]
                7
                8
                9
                10
                11
                12

            */
            map = new ExternalChainingHashMap<>();
            expected = new ExternalChainingMapEntry[13];

            for (int i = 0; i < 7; i++) {
                map.put(i, "" + i + "");
                expected[i] = new ExternalChainingMapEntry<>(i, "" + i + "");
            }
        }

        @Test(timeout = TIMEOUT)
        public void init() {
            assertEquals(7, map.size());
            assertArrayEquals(expected, map.getTable());
        }


        @Test(timeout = TIMEOUT)
        public void testPutDuplicate() {
            /*
                0 [0]
                1 [1.1]
                2 [2.1]
                3 [3.1]
                4 [4.1]
                5 [5.1]
                6 [6]
                7
                8
                9
                10
                11
                12

            */
            for (int i = 1; i < 6; i++) {
                assertEquals("" + i + "", map.put(i, "" + (i + .1) + ""));
                expected[i].setValue("" + (i + .1) + "");
            }

            assertEquals(7, map.size());
            assertArrayEquals(expected, map.getTable());
        }

        @Test(timeout = TIMEOUT)
        public void testResize() {
             /*
                0 [0]           14
                1 [1]           15
                2 [2]           16
                3 [3]           17
                4 [4]           18
                5 [5]           19
                6 [6]           20
                7 [7]           21
                8 [8]           22
                9               23
                10              24
                11              25
                12              26
                13
            */
             map.put(7, "7");

             assertEquals(null, map.put(8, "8"));

             expected = new ExternalChainingMapEntry[27];

             for (int i = 0; i < 9; i++) {
                 expected[i] = new ExternalChainingMapEntry<>(i, "" + i + "");
             }

             assertEquals(9, map.size());
             assertArrayEquals(expected, map.getTable());
        }

        @Test(timeout = TIMEOUT)
        public void testResizeDuplicate() {
            /*
                0 [0]           14
                1 [1]           15
                2 [2]           16
                3 [3]           17
                4 [4]           18
                5 [5.1]         19
                6 [6]           20
                7 [7]           21
                8               22
                9               23
                10              24
                11              25
                12              26
                13
            */
            map.put(7, "7");

            assertEquals("5", map.put(5, "5.1"));

            expected = new ExternalChainingMapEntry[27];

            for (int i = 0; i < 8; i++) {
                if (i == 5) {
                    expected[5] = new ExternalChainingMapEntry<>(5, "5.1");
                } else {
                    expected[i] = new ExternalChainingMapEntry<>(i, "" + i + "");
                }
            }

            assertEquals(8, map.size());
            assertArrayEquals(expected, map.getTable());
        }

        @Test(timeout = TIMEOUT)
        public void testResizeChain() {
            /*
                0 [0]           14
                1 [1]           15
                2 [2]           16
                3 [3]           17
                4 [4]           18
                5 [32 -> 5]     19
                6 [6]           20
                7 [7]           21
                8               22
                9               23
                10              24
                11              25
                12              26
                13
            */
            map.put(7, "7");
            assertEquals(8, map.size());

            assertEquals(null, map.put(32, "32"));
            expected = new ExternalChainingMapEntry[27];

            for (int i = 0; i < 8; i++) {
                if (i == 5) {
                    expected[i] = new ExternalChainingMapEntry<>(32, "32", new ExternalChainingMapEntry<>(5, "5"));
                } else {
                    expected[i] = new ExternalChainingMapEntry<>(i, "" + i + "");
                }
            }

            assertEquals(9, map.size());
            assertArrayEquals(expected, map.getTable());
        }

        @Test(timeout = TIMEOUT)
        public void testRemove() {
            for (int i = 0; i < 7; i++) {
                assertEquals("" + i + "", map.remove(i));
                expected[i] = null;
            }

            assertEquals(0, map.size());
        }

        @Test(timeout = TIMEOUT)
        public void testGet() {
            for (int i = 0; i < 7; i++) {
                assertEquals("" + i + "", map.get(i));
            }
        }

        @Test(timeout = TIMEOUT)
        public void testContains() {
            for (int i = 0; i < 7; i++) {
                assertTrue(map.containsKey(i));
            }
        }

        @Test(timeout = TIMEOUT)
        public void testKeySet() {
            assertEquals(new HashSet<>(Arrays.asList(new Integer[]{0, 1, 2, 3, 4, 5, 6})), map.keySet());
        }

        @Test(timeout = TIMEOUT)
        public void testValues() {
            assertEquals(new ArrayList<>(Arrays.asList(new String[]{"0", "1", "2", "3", "4", "5", "6"})), map.values());
        }

        @Test(timeout = TIMEOUT)
        public void testClear() {
            map.clear();
            expected = new ExternalChainingMapEntry[13];

            assertEquals(0, map.size());
            assertArrayEquals(expected, map.getTable());
        }
    }

    public static class TestLargerDataChain {
        @Before
        public void setUp() {
            /*
                0 [0]
                1 [14 -> 1]
                2 [28 -> 15 -> 2]
                3
                4
                5 [31]
                6
                7
                8
                9
                10
                11
                12

            */
            map = new ExternalChainingHashMap<>();
            expected = new ExternalChainingMapEntry[13];

            for (int i = 0; i < 3; i++) {
                map.put(i, "" + i + "");
                expected[i] = new ExternalChainingMapEntry<>(i, "" + i + "");

                if (i > 0) {
                    map.put(i + 13, "" + (i + 13) + "");
                    expected[i] = new ExternalChainingMapEntry<>(i + 13, "" + (i + 13) + "", expected[i]);
                }

                if (i == 2) {
                    map.put(i + 26, "" + (i + 26) + "");
                    expected[i] = new ExternalChainingMapEntry<>(i + 26, "" + (i + 26) + "", expected[i]);
                }
            }

            map.put(31, "" + 31 + "");
            expected[5] = new ExternalChainingMapEntry<>(31, "31");
        }

        @Test(timeout = TIMEOUT)
        public void init() {
            assertEquals(7, map.size());
            assertArrayEquals(expected, map.getTable());
        }

        @Test(timeout = TIMEOUT)
        public void testPutDuplicate() {
            /*
                0 [0]
                1 [14 -> 1.1]
                2 [28 -> 15.1 -> 2.1]
                3
                4
                5 [31.1]
                6
                7
                8
                9
                10
                11
                12

            */
            assertEquals("1", map.put(1, "1.1"));
            expected[1].getNext().setValue("1.1");
            assertEquals(7, map.size());

            assertEquals("15", map.put(15, "15.1"));
            expected[2].getNext().setValue("15.1");
            assertEquals(7, map.size());

            assertEquals("31", map.put(31, "31.1"));
            expected[5].setValue("31.1");
            assertEquals(7, map.size());

            assertEquals("2", map.put(2, "2.1"));
            expected[2].getNext().getNext().setValue("2.1");
            assertEquals(7, map.size());
        }

        @Test(timeout = TIMEOUT)
        public void testResize() {
            /*
                0 [0]           14 [14]
                1 [28 -> 1]     15 [15]
                2 [2]           16
                3               17
                4 [31]          18
                5               19
                6               20
                7               21
                8               22
                9               23
                10              24
                11 [11]         25
                12 [12]         26
                13
            */
            map.put(11, "11");

            assertEquals(null, map.put(12, "12"));

            expected = new ExternalChainingMapEntry[27];

            for (int i = 0; i < 3; i++) {
                expected[i] = new ExternalChainingMapEntry<>(i, "" + i + "");

                if (i == 1) {
                    expected[1] = new ExternalChainingMapEntry<>(28, "28", expected[1]);
                }

                if (i > 0) {
                    expected[i + 10] = new ExternalChainingMapEntry<>(i + 10, "" + (i + 10) + "");
                    expected[i + 13] = new ExternalChainingMapEntry<>(i + 13, "" + (i + 13) + "");
                }
            }

            expected[4] = new ExternalChainingMapEntry<>(31, "31");

            assertEquals(9, map.size());
            assertArrayEquals(expected, map.getTable());
        }

        @Test(timeout = TIMEOUT)
        public void testResizeDuplicate() {
            /*
                0 [0]           14 [14]
                1 [28 -> 1.1]   15 [15]
                2 [2]           16
                3               17
                4 [31]          18
                5               19
                6               20
                7               21
                8               22
                9               23
                10              24
                11 [11]         25
                12              26
                13
            */
            map.put(11, "11");

            assertEquals("1", map.put(1, "1.1"));

            expected = new ExternalChainingMapEntry[27];

            for (int i = 0; i < 3; i++) {
                expected[i] = new ExternalChainingMapEntry<>(i, "" + i + "");

                if (i == 1) {
                    expected[1] = new ExternalChainingMapEntry<>(28, "28", expected[1]);
                    expected[i + 10] = new ExternalChainingMapEntry<>(i + 10, "" + (i + 10) + "");
                }

                if (i > 0) {
                    expected[i + 13] = new ExternalChainingMapEntry<>(i + 13, "" + (i + 13) + "");
                }
            }

            expected[4] = new ExternalChainingMapEntry<>(31, "31");
            expected[1].getNext().setValue("1.1");

            assertEquals(8, map.size());
            assertArrayEquals(expected, map.getTable());
        }

        @Test(timeout = TIMEOUT)
        public void testRemove() {
            expected = new ExternalChainingMapEntry[13];

            for (int i = 0; i < 3; i++) {
                assertEquals("" + i + "", map.remove(i));

                if (i > 0) {
                    assertEquals("" + (i + 13) + "", map.remove(i + 13));
                }
            }

            assertEquals("28", map.remove(28));
            assertEquals("31", map.remove(31));
            assertEquals(0, map.size());
            assertArrayEquals(expected, map.getTable());
        }

        @Test(timeout = TIMEOUT)
        public void testGet() {
            for (int i = 0; i < 3; i++) {
                assertEquals("" + i + "", map.get(i));

                if (i > 0) {
                    assertEquals("" + (i + 13) + "", map.get(i + 13));
                }
            }

            assertEquals("28", map.get(28));
            assertEquals("31", map.get(31));
        }

        @Test(timeout = TIMEOUT)
        public void testContains() {
            for (int i = 0; i < 3; i++) {
                assertTrue(map.containsKey(i));

                if (i > 0) {
                    assertTrue(map.containsKey(i + 13));
                }
            }

            assertTrue(map.containsKey(28));
            assertTrue(map.containsKey(31));
        }

        @Test(timeout = TIMEOUT)
        public void testKeySet() {
            assertEquals(new HashSet<>(Arrays.asList(new Integer[]{0, 14, 1, 28, 15, 2, 31})), map.keySet());
        }

        @Test(timeout = TIMEOUT)
        public void testValues() {
            assertEquals(new ArrayList<>(Arrays.asList(new String[]{"0", "14", "1", "28", "15", "2", "31"})), map.values());
        }

        @Test(timeout = TIMEOUT)
        public void testClear() {
            map.clear();
            expected = new ExternalChainingMapEntry[13];

            assertEquals(0, map.size());
            assertArrayEquals(expected, map.getTable());
        }
    }
}

