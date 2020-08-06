import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

import static org.junit.Assert.*;

/**
 * @author Tahlee Jaynes
 * @version 1.0
 *
 *
 * I HAVE MADE DIAGRAMS FOR EACH CASE FOR THE TESTLARGERTEXT CLASS, GO TO THE LINK BELOW
 *  https://docs.google.com/spreadsheets/d/1XaUXb1cq-1fOeGD8lwRTDxlLWffCU6ETJtgDnjvAPLk/edit?usp=sharing
 *
 * (If the text is a little hard to see, let the record show I made this while in dark mode ;D)
 *
 *
 */

@RunWith(JaynesJUnit07.class)
@Suite.SuiteClasses({JaynesJUnit07.TestSize0.class, JaynesJUnit07.TestSize1.class, JaynesJUnit07.TestSize2.class, JaynesJUnit07.TestLargerText.class,
        JaynesJUnit07.TestExceptions.class, JaynesJUnit07.TestOtherCases.class})
public class JaynesJUnit07 extends Suite {
    private static final int TIMEOUT = 200;

    private static CharacterComparator comp;

    public JaynesJUnit07(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        super(klass, builder);
    }

    public static class TestSize0 {
        @Before
        public void setUp() {
            comp = new CharacterComparator();
        }

        @Test (timeout = TIMEOUT)
        public void testKMPFailureTableEmptyPattern() {
            assertArrayEquals(new int[0], PatternMatching.buildFailureTable("", comp));
            assertEquals(0, comp.getComparisonCount());
        }

        @Test (timeout = TIMEOUT)
        public void testKMPFailureTable() {
            assertArrayEquals(new int[2], PatternMatching.buildFailureTable(":D", comp));
            assertEquals(1, comp.getComparisonCount());
        }

        @Test (timeout = TIMEOUT)
        public void testKMP() {
            assertArrayEquals(new Integer[]{}, PatternMatching.kmp(":D", "", comp).toArray());
            assertTrue(comp.getComparisonCount() == 0 || comp.getComparisonCount() == 1);
        }

        @Test (timeout = TIMEOUT)
        public void testBoyerMoore() {
            assertArrayEquals(new Integer[]{}, PatternMatching.boyerMoore(":D", "", comp).toArray());
            assertEquals(0, comp.getComparisonCount());
        }

        @Test (timeout = TIMEOUT)
        public void testRabinKarp() {
            assertArrayEquals(new Integer[]{}, PatternMatching.rabinKarp(":D", "", comp).toArray());
            assertEquals(0, comp.getComparisonCount());
        }
    }

    public static class TestSize1 {
        @Before
        public void setUp() {
            comp = new CharacterComparator();
        }

        @Test (timeout = TIMEOUT)
        public void testKMPFailureTable() {
            assertArrayEquals(new int[1], PatternMatching.buildFailureTable("a", comp));
            assertEquals(0, comp.getComparisonCount());
        }

        @Test (timeout = TIMEOUT)
        public void testKMPMatch() {
            assertArrayEquals(new Integer[]{0}, PatternMatching.kmp("a", "a", comp).toArray());
            assertTrue(comp.getComparisonCount() == 1 || comp.getComparisonCount() == 0);
        }

        @Test (timeout = TIMEOUT)
        public void testBoyerMooreMatch() {
            assertArrayEquals(new Integer[]{0}, PatternMatching.boyerMoore("a", "a", comp).toArray());
            assertEquals(1, comp.getComparisonCount());
        }

        @Test (timeout = TIMEOUT)
        public void testRabinKarpMatch() {
            assertArrayEquals(new Integer[]{0}, PatternMatching.rabinKarp("a", "a", comp).toArray());
            assertEquals(1, comp.getComparisonCount());
        }

        @Test (timeout = TIMEOUT)
        public void testKMPNoMatch() {
            assertArrayEquals(new Integer[0], PatternMatching.kmp("a", "b", comp).toArray());
            assertEquals(1, comp.getComparisonCount());
        }

        @Test (timeout = TIMEOUT)
        public void testBoyerMooreNoMatch() {
            assertArrayEquals(new Integer[0], PatternMatching.boyerMoore("a", "b", comp).toArray());
            assertEquals(1, comp.getComparisonCount());
        }

        @Test (timeout = TIMEOUT)
        public void testRabinKarpNoMatch() {
            assertArrayEquals(new Integer[0], PatternMatching.rabinKarp("a", "b", comp).toArray());
            assertEquals(0, comp.getComparisonCount());
        }
    }

    public static class TestSize2 {
        @Before
        public void setUp() {
            comp = new CharacterComparator();
        }

        @Test (timeout = TIMEOUT)
        public void testKMPFailureTable() {
            assertArrayEquals(new int[]{0, 1}, PatternMatching.buildFailureTable("aa", comp));
            assertEquals(1, comp.getComparisonCount());
        }

        @Test (timeout = TIMEOUT)
        public void testKMPMatchEquals() {
            assertArrayEquals(new Integer[]{0}, PatternMatching.kmp("aa", "aa", comp).toArray());
            assertTrue(comp.getComparisonCount() == 2 || comp.getComparisonCount() == 3);
        }

        @Test (timeout = TIMEOUT)
        public void testKMPMatchSingleOccurrence() {
            assertArrayEquals(new Integer[]{1}, PatternMatching.kmp("b", "ab", comp).toArray());
            assertEquals(2, comp.getComparisonCount());
        }

        @Test (timeout = TIMEOUT)
        public void testKMPMatchMultipleOccurrence() {
            assertArrayEquals(new Integer[]{0, 1}, PatternMatching.kmp("a", "aa", comp).toArray());
            assertEquals(2, comp.getComparisonCount());
        }

        @Test (timeout = TIMEOUT)
        public void testKMPNoMatch() {
            assertArrayEquals(new Integer[0], PatternMatching.kmp("a", "b", comp).toArray());
            assertEquals(1, comp.getComparisonCount());
        }

        @Test (timeout = TIMEOUT)
        public void testBoyerMooreMatchEquals() {
            assertArrayEquals(new Integer[]{0}, PatternMatching.boyerMoore("a", "a", comp).toArray());
            assertEquals(1, comp.getComparisonCount());
        }

        @Test (timeout = TIMEOUT)
        public void testBoyerMooreMatchSingleOccurrence() {
            assertArrayEquals(new Integer[]{1}, PatternMatching.boyerMoore("b", "ab", comp).toArray());
            assertEquals(2, comp.getComparisonCount());
        }

        @Test (timeout = TIMEOUT)
        public void testBoyerMooreMatchMultipleOccurrence() {
            assertArrayEquals(new Integer[]{0, 1}, PatternMatching.boyerMoore("a", "aa", comp).toArray());
            assertEquals(2, comp.getComparisonCount());
        }

        @Test (timeout = TIMEOUT)
        public void testBoyerMooreNoMatch() {
            assertArrayEquals(new Integer[0], PatternMatching.boyerMoore("a", "b", comp).toArray());
            assertEquals(1, comp.getComparisonCount());
        }

        @Test (timeout = TIMEOUT)
        public void testRabinKarpMatch() {
            assertArrayEquals(new Integer[]{0}, PatternMatching.rabinKarp("a", "a", comp).toArray());
            assertEquals(1, comp.getComparisonCount());
        }

        @Test (timeout = TIMEOUT)
        public void testRabinKarpSingleOccurrence() {
            assertArrayEquals(new Integer[]{1}, PatternMatching.rabinKarp("b", "ab", comp).toArray());
            assertEquals(1, comp.getComparisonCount());
        }

        @Test (timeout = TIMEOUT)
        public void testRabinKarpMultipleOccurrence() {
            assertArrayEquals(new Integer[]{0, 1}, PatternMatching.rabinKarp("a", "aa", comp).toArray());
            assertEquals(2, comp.getComparisonCount());
        }

        @Test (timeout = TIMEOUT)
        public void testRabinKarpNoMatch() {
            assertArrayEquals(new Integer[0], PatternMatching.rabinKarp("a", "b", comp).toArray());
            assertEquals(0, comp.getComparisonCount());
        }
    }

    public static class TestExceptions {
        @Before
        public void setUp() {
            comp = new CharacterComparator();
        }

        @Test (timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testFailureTableNullPattern() {
            PatternMatching.buildFailureTable(null, comp);
        }

        @Test (timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testFailureTableNullComparator() {
            PatternMatching.buildFailureTable(":D", null);
        }

        @Test (timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testKMPNullPattern() {
            PatternMatching.kmp(null, ":D", comp);
        }

        @Test (timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testKMPPatternLength0() {
            PatternMatching.kmp("", ":D", comp);
        }

        @Test (timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testKMPNullText() {
            PatternMatching.kmp(":D", null, comp);
        }

        @Test (timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testKMPNullComparator() {
            PatternMatching.kmp(":", "D", null);
        }

        @Test (timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testLastTableNullPattern() {
            PatternMatching.buildLastTable(null);
        }

        @Test (timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testBoyerMooreNullPattern() {
            PatternMatching.boyerMoore(null, ":D", comp);
        }

        @Test (timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testBoyerMoorePatternLength0() {
            PatternMatching.boyerMoore("", ":D", comp);
        }

        @Test (timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testBoyerMooreNullText() {
            PatternMatching.boyerMoore(":D", null, comp);
        }

        @Test (timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testBoyerMooreNullComparator() {
            PatternMatching.boyerMoore(":", "D", null);
        }

        @Test (timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testRabinKarpNullPattern() {
            PatternMatching.rabinKarp(null, ":D", comp);
        }

        @Test (timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testRabinKarpPatternLength0() {
            PatternMatching.rabinKarp("", ":D", comp);
        }

        @Test (timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testRabinKarpNullText() {
            PatternMatching.rabinKarp(":D", null, comp);
        }

        @Test (timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testRabinKarpNullComparator() {
            PatternMatching.rabinKarp(":", "D", null);
        }
    }

    public static class TestLargerText {
        private static String multipleText = "yabba dabba doo!";
        private static String multiplePatternFound = "abba";
        private static String multiplePatternNotFound = "abbab";
        private static String multiplePatternSingleOccurrence = "dab";
        private static Integer[] multipleResult = {1, 7};

        // Once again, here's the link to the diagrams I used... feel free to check against the CS 1332 Visualizer!
        // https://docs.google.com/spreadsheets/d/1XaUXb1cq-1fOeGD8lwRTDxlLWffCU6ETJtgDnjvAPLk/edit?usp=sharing

        @Before
        public void setUp() {
            comp = new CharacterComparator();
        }

        @Test(timeout = TIMEOUT)
        public void testBoyerMooreSingleMatch() {
            assertArrayEquals(new Integer[]{6}, PatternMatching.boyerMoore(multiplePatternSingleOccurrence, multipleText, comp).toArray());
            assertEquals(15, comp.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testBoyerMooreMultipleMatch() {
            assertArrayEquals(multipleResult, PatternMatching.boyerMoore(multiplePatternFound, multipleText, comp).toArray());
            assertEquals(13, comp.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testBoyerMooreNoMatch() {
            assertArrayEquals(new Integer[]{}, PatternMatching.boyerMoore(multiplePatternNotFound, multipleText, comp).toArray());
            assertEquals(4, comp.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testKMPSingleMatch() {
            assertArrayEquals(new Integer[]{6}, PatternMatching.kmp(multiplePatternSingleOccurrence, multipleText, comp).toArray());
            assertEquals(17, comp.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testKMPMultipleMatch() {
            assertArrayEquals(multipleResult, PatternMatching.kmp(multiplePatternFound, multipleText, comp).toArray());
            assertEquals(18, comp.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testKMPNoMatch() {
            assertArrayEquals(new Integer[]{}, PatternMatching.kmp(multiplePatternNotFound, multipleText, comp).toArray());
            assertEquals(20, comp.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testRabinKarpSingleMatch() {
            assertArrayEquals(new Integer[]{6}, PatternMatching.rabinKarp(multiplePatternSingleOccurrence, multipleText, comp).toArray());
            assertEquals(3, comp.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testRabinKarpMultipleMatch() {
            assertArrayEquals(multipleResult, PatternMatching.rabinKarp(multiplePatternFound, multipleText, comp).toArray());
            assertEquals(8, comp.getComparisonCount());
        }

        @Test(timeout = TIMEOUT)
        public void testRabinKarpNoMatch() {
            assertArrayEquals(new Integer[]{}, PatternMatching.rabinKarp(multiplePatternNotFound, multipleText, comp).toArray());
            assertEquals(0, comp.getComparisonCount());
        }
    }

    public static class TestOtherCases {
        private static String LongerPattern = "abc";
        private static String LongerText = "a";
        private static Integer[] LongerResult = new Integer[0];

        private static String LastPattern = "ab";
        private static String LastText = "acdab";
        private static Integer[] LastResult = {3};

        @Before
        public void setUp() {
            comp = new CharacterComparator();
        }

        @Test (timeout = TIMEOUT)
        public void testKMPPatternLongerThanText() {
            assertArrayEquals(LongerResult, PatternMatching.kmp(LongerPattern, LongerText, comp).toArray());
            assertEquals(0, comp.getComparisonCount());
        }

        @Test (timeout = TIMEOUT)
        public void testBoyerMoorePatternLongerThanText() {
            assertArrayEquals(LongerResult, PatternMatching.boyerMoore(LongerPattern, LongerText, comp).toArray());
            assertEquals(0, comp.getComparisonCount());
        }

        @Test (timeout = TIMEOUT)
        public void testRabinKarpPatternLongerThanText() {
            assertArrayEquals(LongerResult, PatternMatching.rabinKarp(LongerPattern, LongerText, comp).toArray());
            assertEquals(0, comp.getComparisonCount());
        }

        @Test (timeout = TIMEOUT)
        public void testKMPLastMatch() {
            assertArrayEquals(LastResult, PatternMatching.kmp(LastPattern, LastText, comp).toArray());
            assertEquals(7, comp.getComparisonCount());
        }

        @Test (timeout = TIMEOUT)
        public void testBoyerMooreLastMatch() {
            assertArrayEquals(LastResult, PatternMatching.boyerMoore(LastPattern, LastText, comp).toArray());
            assertEquals(4, comp.getComparisonCount());
        }

        @Test (timeout = TIMEOUT)
        public void testRabinKarpLastMatch() {
            assertArrayEquals(LastResult, PatternMatching.rabinKarp(LastPattern, LastText, comp).toArray());
            assertEquals(2, comp.getComparisonCount());
        }
    }
}
