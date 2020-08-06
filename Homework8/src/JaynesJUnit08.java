import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

import java.util.*;

import static org.junit.Assert.*;

/**
 * @author Tahlee Jaynes
 * @version 1.0
 *
 *
 * Here are the graphs I used!
 * https://docs.google.com/document/d/1WkoKk9JWakT6dUgjbg-ug5er0zRLXD26uUMelg6tNxY/edit?usp=sharing
 *
 * NOTE: The "invalid" graphs aren't truly invalid in any shape or form, they are simply graphs that contain self-loops and/or parallel edges
 *
 */

@RunWith(JaynesJUnit08.class)
@Suite.SuiteClasses({JaynesJUnit08.TestSize1.class, JaynesJUnit08.TestSize2.class, JaynesJUnit08.TestLargerGraph.class,
        JaynesJUnit08.TestExceptions.class, JaynesJUnit08.TestOtherCases.class})
public class JaynesJUnit08 extends Suite {
    private static final int TIMEOUT = 200;

    public JaynesJUnit08(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        super(klass, builder);
    }

    public static class TestSize1 {
        private static Set<Vertex<Character>> vertices = new HashSet<>(Arrays.asList(
                new Vertex<>('A')
        ));

        private static Set<Edge<Character>> realEdges = new HashSet<>(Arrays.asList());

        private static Set<Edge<Character>> invalidEdges = new HashSet<>(Arrays.asList(
                new Edge<>(new Vertex<>('A'), new Vertex<>('A'), 0)
        ));

        private static Graph<Character> graph;
        private static Graph<Character> invalidGraph;
        private static Vertex<Character> startVertex = new Vertex<>('A');

        private static ArrayList<Vertex<Character>> expectedDFS = new ArrayList<>(Arrays.asList(
                startVertex
        ));

        private static Map<Vertex<Character>, Integer> expectedDijkstras = new HashMap<>();
        private static Set<Edge<Character>> expectedPrims = new HashSet<>();

        @Before
        public void setUp() {
            graph = new Graph<>(vertices, realEdges);
            invalidGraph = new Graph<>(vertices, invalidEdges);

            expectedDijkstras.put(startVertex, 0);
        }

        @Test(timeout = TIMEOUT)
        public void testDFS() {
            assertEquals(expectedDFS, GraphAlgorithms.dfs(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testDijkstras() {
            assertEquals(expectedDijkstras, GraphAlgorithms.dijkstras(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testPrims() {
            assertEquals(expectedPrims, GraphAlgorithms.prims(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testPrimsSelfLoop() {
            assertEquals(expectedPrims, GraphAlgorithms.prims(startVertex, invalidGraph));
        }
    }

    public static class TestSize2 {
        private static Set<Vertex<Character>> vertices = new HashSet<>(Arrays.asList(
                new Vertex<>('A'),
                new Vertex<>('B')
        ));

        private static Set<Edge<Character>> realEdgesDirectedConnected = new HashSet<>(Arrays.asList(
                new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 2)
        ));

        private static Set<Edge<Character>> realEdgesDirectedDisconnected = new HashSet<>(Arrays.asList());

        private static Set<Edge<Character>> realEdgesUndirectedConnected = new HashSet<>(Arrays.asList(
                new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 2),
                new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 2)
        ));

        private static Set<Edge<Character>> realEdgesUndirectedDisconnected = new HashSet<>(Arrays.asList());

        private static Set<Edge<Character>> invalidEdgesDirectedConnected = new HashSet<>(Arrays.asList(
                new Edge<>(new Vertex<>('A'), new Vertex<>('A'), 0),
                new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 2),
                new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 4)
        ));

        private static Set<Edge<Character>> invalidEdgesUndirectedConnected = new HashSet<>(Arrays.asList(
                new Edge<>(new Vertex<>('A'), new Vertex<>('A'), 0),
                new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 2),
                new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 2),
                new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 4),
                new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 2)
        ));

        private static Graph<Character> graph;
        private static Graph<Character> invalidGraph;
        private static Vertex<Character> startVertex = new Vertex<>('A');

        private static ArrayList<Vertex<Character>> expectedDirectedConnectedDFS = new ArrayList<>(Arrays.asList(
                new Vertex<>('A'),
                new Vertex<>('B')
        ));

        private static ArrayList<Vertex<Character>> expectedDirectedDisconnectedDFS = new ArrayList<>(Arrays.asList(
                startVertex
        ));

        private static ArrayList<Vertex<Character>> expectedUndirectedConnectedDFS = new ArrayList<>(Arrays.asList(
                new Vertex<>('A'),
                new Vertex<>('B')
        ));

        private static ArrayList<Vertex<Character>> expectedUndirectedDisconnectedDFS = new ArrayList<>(Arrays.asList(
                startVertex
        ));

        private static Map<Vertex<Character>, Integer> expectedDirectedConnectedDijkstras = new HashMap<>();
        private static Map<Vertex<Character>, Integer> expectedDirectedDisconnectedDijkstras = new HashMap<>();
        private static Map<Vertex<Character>, Integer> expectedUndirectedConnectedDijkstras = new HashMap<>();
        private static Map<Vertex<Character>, Integer> expectedUndirectedDisconnectedDijkstras = new HashMap<>();

        private static Set<Edge<Character>> expectedDirectedConnectedPrims = new HashSet<>(Arrays.asList(
                new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 2),
                new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 2)
        ));

        @Before
        public void setUp() {
            expectedDirectedConnectedDijkstras.put(new Vertex<>('A'), 0);
            expectedDirectedConnectedDijkstras.put(new Vertex<>('B'), 2);

            expectedDirectedDisconnectedDijkstras.put(new Vertex<>('A'), 0);
            expectedDirectedDisconnectedDijkstras.put(new Vertex<>('B'), Integer.MAX_VALUE);

            expectedUndirectedConnectedDijkstras.put(new Vertex<>('A'), 0);
            expectedUndirectedConnectedDijkstras.put(new Vertex<>('B'), 2);

            expectedUndirectedDisconnectedDijkstras.put(new Vertex<>('A'), 0);
            expectedUndirectedDisconnectedDijkstras.put(new Vertex<>('B'), Integer.MAX_VALUE);
        }

        @Test(timeout = TIMEOUT)
        public void testDirectedConnectedDFS() {
            graph = new Graph<>(vertices, realEdgesDirectedConnected);

            assertEquals(expectedDirectedConnectedDFS, GraphAlgorithms.dfs(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testDirectedDisconnectedDFS() {
            graph = new Graph<>(vertices, realEdgesDirectedDisconnected);

            assertEquals(expectedDirectedDisconnectedDFS, GraphAlgorithms.dfs(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testUndirectedConnectedDFS() {
            graph = new Graph<>(vertices, realEdgesUndirectedConnected);

            assertEquals(expectedUndirectedConnectedDFS, GraphAlgorithms.dfs(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testUndirectedDisconnectedDFS() {
            graph = new Graph<>(vertices, realEdgesUndirectedDisconnected);

            assertEquals(expectedUndirectedDisconnectedDFS, GraphAlgorithms.dfs(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testDirectedConnectedDijkstras() {
            graph = new Graph<>(vertices, realEdgesDirectedConnected);

            assertEquals(expectedDirectedConnectedDijkstras, GraphAlgorithms.dijkstras(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testDirectedDisconnectedDijkstras() {
            graph = new Graph<>(vertices, realEdgesDirectedDisconnected);

            assertEquals(expectedDirectedDisconnectedDijkstras, GraphAlgorithms.dijkstras(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testUndirectedConnectedDijkstras() {
            graph = new Graph<>(vertices, realEdgesUndirectedConnected);

            assertEquals(expectedUndirectedConnectedDijkstras, GraphAlgorithms.dijkstras(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testUndirectedDisconnectedDijkstras() {
            graph = new Graph<>(vertices, realEdgesUndirectedDisconnected);

            assertEquals(expectedUndirectedDisconnectedDijkstras, GraphAlgorithms.dijkstras(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testDirectedConnectedPrims() {
            graph = new Graph<>(vertices, realEdgesDirectedConnected);

            assertEquals(expectedDirectedConnectedPrims, GraphAlgorithms.prims(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testDirectedDisconnectedPrims() {
            graph = new Graph<>(vertices, realEdgesDirectedDisconnected);

            assertNull(GraphAlgorithms.prims(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testInvalidDirectedConnectedDFS() {
            invalidGraph = new Graph<>(vertices, invalidEdgesDirectedConnected);

            assertEquals(expectedDirectedConnectedDFS, GraphAlgorithms.dfs(startVertex, invalidGraph));
        }

        @Test(timeout = TIMEOUT)
        public void testInvalidUndirectedConnectedDFS() {
            invalidGraph = new Graph<>(vertices, invalidEdgesUndirectedConnected);

            assertEquals(expectedUndirectedConnectedDFS, GraphAlgorithms.dfs(startVertex, invalidGraph));
        }

        @Test(timeout = TIMEOUT)
        public void testInvalidDirectedConnectedDijkstras() {
            invalidGraph = new Graph<>(vertices, invalidEdgesDirectedConnected);

            assertEquals(expectedDirectedConnectedDijkstras, GraphAlgorithms.dijkstras(startVertex, invalidGraph));
        }

        @Test(timeout = TIMEOUT)
        public void testInvalidUndirectedConnectedDijkstras() {
            invalidGraph = new Graph<>(vertices, invalidEdgesUndirectedConnected);

            assertEquals(expectedUndirectedConnectedDijkstras, GraphAlgorithms.dijkstras(startVertex, invalidGraph));
        }

        @Test(timeout = TIMEOUT)
        public void testInvalidDirectedConnectedPrims() {
            invalidGraph = new Graph<>(vertices, invalidEdgesDirectedConnected);

            assertEquals(expectedDirectedConnectedPrims, GraphAlgorithms.prims(startVertex, invalidGraph));
        }
    }

    public static class TestExceptions {
        private static Set<Vertex<Character>> vertices = new HashSet<>(Arrays.asList(
                new Vertex<>('B')
        ));

        private static Graph<Character> graph;

        @Before
        public void setUp() {
            graph = new Graph<>(vertices, new HashSet<>(Arrays.asList()));
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testNullGraphDFS() {
            GraphAlgorithms.dfs(new Vertex<>('B'), null);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testNullGraphDijkstras() {
            GraphAlgorithms.dfs(new Vertex<>('B'), null);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testNullGraphPrims() {
            GraphAlgorithms.dfs(new Vertex<>('B'), null);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testNullStartVertexDFS() {
            GraphAlgorithms.dijkstras(null, graph);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testNullStartVertexDijkstras() {
            GraphAlgorithms.dijkstras(null, graph);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testNullStartVertexPrims() {
            GraphAlgorithms.dijkstras(null, graph);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testInvalidStartVertexDFS() {
            GraphAlgorithms.dfs(new Vertex<>('A'), graph);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testInvalidStartVertexDijkstras() {
            GraphAlgorithms.dijkstras(new Vertex<>('A'), graph);
        }

        @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
        public void testInvalidStartVertexPrims() {
            GraphAlgorithms.prims(new Vertex<>('A'), graph);
        }
    }

    public static class TestLargerGraph {
        private static Set<Vertex<Character>> vertices = new HashSet<>(Arrays.asList(
                new Vertex<>('A'),
                new Vertex<>('B'),
                new Vertex<>('C'),
                new Vertex<>('D'),
                new Vertex<>('E')

        ));

        private static Set<Edge<Character>> realEdgesDirectedConnected = new HashSet<>(Arrays.asList(
                new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 2),
                new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 3),
                new Edge<>(new Vertex<>('B'), new Vertex<>('D'), 4),
                new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 7),
                new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 1),
                new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 5),
                new Edge<>(new Vertex<>('E'), new Vertex<>('C'), 1)
        ));

        private static Set<Edge<Character>> realEdgesDirectedDisconnected = new HashSet<>(Arrays.asList(
                new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 2),
                new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 3),
                new Edge<>(new Vertex<>('B'), new Vertex<>('D'), 4),
                new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 7)
        ));

        private static Set<Edge<Character>> realEdgesUndirectedConnected = new HashSet<>(Arrays.asList(
                new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 2),
                new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 2),
                new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 3),
                new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 3),
                new Edge<>(new Vertex<>('B'), new Vertex<>('D'), 4),
                new Edge<>(new Vertex<>('D'), new Vertex<>('B'), 4),
                new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 7),
                new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 7),
                new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 1),
                new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 1),
                new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 5),
                new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 5),
                new Edge<>(new Vertex<>('E'), new Vertex<>('C'), 9),
                new Edge<>(new Vertex<>('C'), new Vertex<>('E'), 9)
        ));

        private static Set<Edge<Character>> realEdgesUndirectedDisconnected = new HashSet<>(Arrays.asList(
                new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 2),
                new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 2),
                new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 3),
                new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 3),
                new Edge<>(new Vertex<>('B'), new Vertex<>('D'), 4),
                new Edge<>(new Vertex<>('D'), new Vertex<>('B'), 4),
                new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 1),
                new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 1)
        ));

        private static Set<Edge<Character>> invalidEdgesDirectedConnected = new HashSet<>(Arrays.asList(
                new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 2),
                new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 3),
                new Edge<>(new Vertex<>('B'), new Vertex<>('D'), 4),
                new Edge<>(new Vertex<>('B'), new Vertex<>('D'), 6),
                new Edge<>(new Vertex<>('B'), new Vertex<>('B'), 8),
                new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 7),
                new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 1),
                new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 5),
                new Edge<>(new Vertex<>('E'), new Vertex<>('C'), 6),
                new Edge<>(new Vertex<>('E'), new Vertex<>('C'), 1),
                new Edge<>(new Vertex<>('E'), new Vertex<>('E'), 6)
        ));

        private static Set<Edge<Character>> invalidEdgesDirectedDisconnected = new HashSet<>(Arrays.asList(
                new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 2),
                new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 3),
                new Edge<>(new Vertex<>('B'), new Vertex<>('B'), 8),
                new Edge<>(new Vertex<>('B'), new Vertex<>('D'), 6),
                new Edge<>(new Vertex<>('B'), new Vertex<>('D'), 4),
                new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 7),
                new Edge<>(new Vertex<>('E'), new Vertex<>('E'), 6)
        ));

        private static Set<Edge<Character>> invalidEdgesUndirectedConnected = new HashSet<>(Arrays.asList(
                new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 2),
                new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 2),
                new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 3),
                new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 3),
                new Edge<>(new Vertex<>('B'), new Vertex<>('B'), 8),
                new Edge<>(new Vertex<>('B'), new Vertex<>('D'), 6),
                new Edge<>(new Vertex<>('D'), new Vertex<>('B'), 6),
                new Edge<>(new Vertex<>('B'), new Vertex<>('D'), 4),
                new Edge<>(new Vertex<>('D'), new Vertex<>('B'), 4),
                new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 7),
                new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 7),
                new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 1),
                new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 1),
                new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 2),
                new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 2),
                new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 5),
                new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 5),
                new Edge<>(new Vertex<>('E'), new Vertex<>('C'), 9),
                new Edge<>(new Vertex<>('C'), new Vertex<>('E'), 9),
                new Edge<>(new Vertex<>('E'), new Vertex<>('C'), 10),
                new Edge<>(new Vertex<>('C'), new Vertex<>('E'), 10),
                new Edge<>(new Vertex<>('E'), new Vertex<>('E'), 6)
        ));

        private static Set<Edge<Character>> invalidEdgesUndirectedDisconnected = new HashSet<>(Arrays.asList(
                new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 2),
                new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 2),
                new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 3),
                new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 3),
                new Edge<>(new Vertex<>('B'), new Vertex<>('D'), 6),
                new Edge<>(new Vertex<>('D'), new Vertex<>('B'), 6),
                new Edge<>(new Vertex<>('B'), new Vertex<>('D'), 4),
                new Edge<>(new Vertex<>('D'), new Vertex<>('B'), 4),
                new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 2),
                new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 2),
                new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 1),
                new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 1),
                new Edge<>(new Vertex<>('B'), new Vertex<>('B'), 8),
                new Edge<>(new Vertex<>('E'), new Vertex<>('E'), 6)
        ));

        private static Graph<Character> graph;
        private static Graph<Character> invalidGraph;
        private static Vertex<Character> startVertex = new Vertex<>('A');

        private static ArrayList<Vertex<Character>> expectedDirectedConnectedDFS = new ArrayList<>(Arrays.asList(
                new Vertex<>('A'),
                new Vertex<>('B'),
                new Vertex<>('D'),
                new Vertex<>('C'),
                new Vertex<>('E')
        ));

        private static ArrayList<Vertex<Character>> expectedDirectedDisconnectedDFS = new ArrayList<>(Arrays.asList(
                new Vertex<>('A'),
                new Vertex<>('B'),
                new Vertex<>('D'),
                new Vertex<>('C')
        ));

        private static ArrayList<Vertex<Character>> expectedUndirectedConnectedDFS = new ArrayList<>(Arrays.asList(
                new Vertex<>('A'),
                new Vertex<>('B'),
                new Vertex<>('D'),
                new Vertex<>('C'),
                new Vertex<>('E')
        ));

        private static ArrayList<Vertex<Character>> expectedUndirectedDisconnectedDFS = new ArrayList<>(Arrays.asList(
                new Vertex<>('A'),
                new Vertex<>('B'),
                new Vertex<>('D'),
                new Vertex<>('C')
        ));

        private static ArrayList<Vertex<Character>> expectedUndirectedConnectedDFSInvalid = new ArrayList<>(Arrays.asList(
                new Vertex<>('A'),
                new Vertex<>('C'),
                new Vertex<>('D'),
                new Vertex<>('B'),
                new Vertex<>('E')
        ));

        private static ArrayList<Vertex<Character>> expectedUndirectedDisconnectedDFSInvalid = new ArrayList<>(Arrays.asList(
                new Vertex<>('A'),
                new Vertex<>('C'),
                new Vertex<>('B'),
                new Vertex<>('D')
        ));

        private static Map<Vertex<Character>, Integer> expectedDirectedConnectedDijkstras = new HashMap<>();
        private static Map<Vertex<Character>, Integer> expectedDirectedDisconnectedDijkstras = new HashMap<>();
        private static Map<Vertex<Character>, Integer> expectedUndirectedConnectedDijkstras = new HashMap<>();
        private static Map<Vertex<Character>, Integer> expectedUndirectedDisconnectedDijkstras = new HashMap<>();

        private static Set<Edge<Character>> expectedDirectedConnectedPrims = new HashSet<>(Arrays.asList(
                new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 2),
                new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 2),
                new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 3),
                new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 3),
                new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 5),
                new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 5),
                new Edge<>(new Vertex<>('E'), new Vertex<>('C'), 1),
                new Edge<>(new Vertex<>('C'), new Vertex<>('E'), 1)
        ));

        @Before
        public void setUp() {
            expectedDirectedConnectedDijkstras.put(new Vertex<>('A'), 0);
            expectedDirectedConnectedDijkstras.put(new Vertex<>('B'), 2);
            expectedDirectedConnectedDijkstras.put(new Vertex<>('D'), 3);
            expectedDirectedConnectedDijkstras.put(new Vertex<>('C'), 9);
            expectedDirectedConnectedDijkstras.put(new Vertex<>('E'), 8);

            expectedDirectedDisconnectedDijkstras.put(new Vertex<>('A'), 0);
            expectedDirectedDisconnectedDijkstras.put(new Vertex<>('B'), 2);
            expectedDirectedDisconnectedDijkstras.put(new Vertex<>('D'), 3);
            expectedDirectedDisconnectedDijkstras.put(new Vertex<>('C'), 10);
            expectedDirectedDisconnectedDijkstras.put(new Vertex<>('E'), Integer.MAX_VALUE);

            expectedUndirectedConnectedDijkstras.put(new Vertex<>('A'), 0);
            expectedUndirectedConnectedDijkstras.put(new Vertex<>('C'), 1);
            expectedUndirectedConnectedDijkstras.put(new Vertex<>('B'), 2);
            expectedUndirectedConnectedDijkstras.put(new Vertex<>('D'), 3);
            expectedUndirectedConnectedDijkstras.put(new Vertex<>('E'), 8);

            expectedUndirectedDisconnectedDijkstras.put(new Vertex<>('A'), 0);
            expectedUndirectedDisconnectedDijkstras.put(new Vertex<>('C'), 1);
            expectedUndirectedDisconnectedDijkstras.put(new Vertex<>('B'), 2);
            expectedUndirectedDisconnectedDijkstras.put(new Vertex<>('D'), 3);
            expectedUndirectedDisconnectedDijkstras.put(new Vertex<>('E'), Integer.MAX_VALUE);
        }

        @Test(timeout = TIMEOUT)
        public void testDirectedConnectedDFS() {
            graph = new Graph<>(vertices, realEdgesDirectedConnected);

            assertEquals(expectedDirectedConnectedDFS, GraphAlgorithms.dfs(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testDirectedDisconnectedDFS() {
            graph = new Graph<>(vertices, realEdgesDirectedDisconnected);

            assertEquals(expectedDirectedDisconnectedDFS, GraphAlgorithms.dfs(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testUndirectedConnectedDFS() {
            graph = new Graph<>(vertices, realEdgesUndirectedConnected);

            assertEquals(expectedUndirectedConnectedDFS, GraphAlgorithms.dfs(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testUndirectedDisconnectedDFS() {
            graph = new Graph<>(vertices, realEdgesUndirectedDisconnected);

            assertEquals(expectedUndirectedDisconnectedDFS, GraphAlgorithms.dfs(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testDirectedConnectedDijkstras() {
            graph = new Graph<>(vertices, realEdgesDirectedConnected);

            assertEquals(expectedDirectedConnectedDijkstras, GraphAlgorithms.dijkstras(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testDirectedDisconnectedDijkstras() {
            graph = new Graph<>(vertices, realEdgesDirectedDisconnected);

            assertEquals(expectedDirectedDisconnectedDijkstras, GraphAlgorithms.dijkstras(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testUndirectedConnectedDijkstras() {
            graph = new Graph<>(vertices, realEdgesUndirectedConnected);

            assertEquals(expectedUndirectedConnectedDijkstras, GraphAlgorithms.dijkstras(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testUndirectedDisconnectedDijkstras() {
            graph = new Graph<>(vertices, realEdgesUndirectedDisconnected);

            assertEquals(expectedUndirectedDisconnectedDijkstras, GraphAlgorithms.dijkstras(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testDirectedConnectedPrims() {
            graph = new Graph<>(vertices, realEdgesDirectedConnected);

            assertEquals(expectedDirectedConnectedPrims, GraphAlgorithms.prims(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testDirectedDisconnectedPrims() {
            graph = new Graph<>(vertices, realEdgesDirectedDisconnected);

            assertNull(GraphAlgorithms.prims(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testInvalidDirectedConnectedDFS() {
            invalidGraph = new Graph<>(vertices, invalidEdgesDirectedConnected);

            assertEquals(expectedDirectedConnectedDFS, GraphAlgorithms.dfs(startVertex, invalidGraph));
        }

        @Test(timeout = TIMEOUT)
        public void testInvalidDirectedDisconnectedDFS() {
            invalidGraph = new Graph<>(vertices, invalidEdgesDirectedDisconnected);

            assertEquals(expectedDirectedDisconnectedDFS, GraphAlgorithms.dfs(startVertex, invalidGraph));
        }

        @Test(timeout = TIMEOUT)
        public void testInvalidUndirectedConnectedDFS() {
            invalidGraph = new Graph<>(vertices, invalidEdgesUndirectedConnected);

            assertEquals(expectedUndirectedConnectedDFSInvalid, GraphAlgorithms.dfs(startVertex, invalidGraph));
        }

        @Test(timeout = TIMEOUT)
        public void testInvalidUndirectedDisconnectedDFS() {
            invalidGraph = new Graph<>(vertices, invalidEdgesUndirectedDisconnected);

            assertEquals(expectedUndirectedDisconnectedDFSInvalid, GraphAlgorithms.dfs(startVertex, invalidGraph));
        }

        @Test(timeout = TIMEOUT)
        public void testInvalidDirectedConnectedDijkstras() {
            invalidGraph = new Graph<>(vertices, invalidEdgesDirectedConnected);

            assertEquals(expectedDirectedConnectedDijkstras, GraphAlgorithms.dijkstras(startVertex, invalidGraph));
        }

        @Test(timeout = TIMEOUT)
        public void testInvalidDirectedDisconnectedDijkstras() {
            invalidGraph = new Graph<>(vertices, invalidEdgesDirectedDisconnected);

            assertEquals(expectedDirectedDisconnectedDijkstras, GraphAlgorithms.dijkstras(startVertex, invalidGraph));
        }

        @Test(timeout = TIMEOUT)
        public void testInvalidUndirectedConnectedDijkstras() {
            invalidGraph = new Graph<>(vertices, invalidEdgesUndirectedConnected);

            assertEquals(expectedUndirectedConnectedDijkstras, GraphAlgorithms.dijkstras(startVertex, invalidGraph));
        }

        @Test(timeout = TIMEOUT)
        public void testInvalidUndirectedDisconnectedDijkstras() {
            invalidGraph = new Graph<>(vertices, invalidEdgesUndirectedDisconnected);

            assertEquals(expectedUndirectedDisconnectedDijkstras, GraphAlgorithms.dijkstras(startVertex, invalidGraph));
        }

        @Test(timeout = TIMEOUT)
        public void testInvalidDirectedConnectedPrims() {
            invalidGraph = new Graph<>(vertices, invalidEdgesDirectedConnected);

            assertEquals(expectedDirectedConnectedPrims, GraphAlgorithms.prims(startVertex, invalidGraph));
        }

        @Test(timeout = TIMEOUT)
        public void testInvalidDirectedDisconnectedPrims() {
            invalidGraph = new Graph<>(vertices, invalidEdgesDirectedDisconnected);

            assertNull(GraphAlgorithms.prims(startVertex, invalidGraph));
        }
    }

    public static class TestOtherCases {
        private static Vertex<Character> startVertex = new Vertex<>('A');

        private static Set<Vertex<Character>> verticesSemi = new HashSet<>(Arrays.asList(
                new Vertex<>('A'),
                new Vertex<>('B'),
                new Vertex<>('C')
        ));

        private static Set<Edge<Character>> edgesSemi = new HashSet<>(Arrays.asList(
                new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 2),
                new Edge<>(new Vertex<>('C'), new Vertex<>('B'), 5)
        ));

        private static ArrayList<Vertex<Character>> expectedDFSSemi = new ArrayList<>(Arrays.asList(
                new Vertex<>('A'),
                new Vertex<>('B')
        ));

        private static Map<Vertex<Character>, Integer> expectedDijkstrasSemi = new HashMap<>();

        private static Set<Vertex<Character>> verticesSame = new HashSet<>(Arrays.asList(
                new Vertex<>('A'),
                new Vertex<>('B'),
                new Vertex<>('C'),
                new Vertex<>('D')
        ));

        private static Set<Edge<Character>> edgesSame = new HashSet<>(Arrays.asList(
                new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 1),
                new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 1),
                new Edge<>(new Vertex<>('B'), new Vertex<>('D'), 1),
                new Edge<>(new Vertex<>('C'), new Vertex<>('B'), 1),
                new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 1),
                new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 1)
        ));

        private static ArrayList<Vertex<Character>> expectedDFSSame = new ArrayList<>(Arrays.asList(
                new Vertex<>('A'),
                new Vertex<>('B'),
                new Vertex<>('D'),
                new Vertex<>('C')
        ));

        private static Map<Vertex<Character>, Integer> expectedDijkstrasSame = new HashMap<>();

        private static Set<Edge<Character>> expectedPrimsSame = new HashSet<>(Arrays.asList(
                new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 1),
                new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 1),
                new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 1),
                new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 1),
                new Edge<>(new Vertex<>('B'), new Vertex<>('D'), 1),
                new Edge<>(new Vertex<>('D'), new Vertex<>('B'), 1)
        ));

        private static Graph<Character> graph;

        @Before
        public void setUp() {
            expectedDijkstrasSemi.put(new Vertex<>('A'), 0);
            expectedDijkstrasSemi.put(new Vertex<>('B'), 2);
            expectedDijkstrasSemi.put(new Vertex<>('C'), Integer.MAX_VALUE);

            expectedDijkstrasSame.put(new Vertex<>('A'), 0);
            expectedDijkstrasSame.put(new Vertex<>('B'), 1);
            expectedDijkstrasSame.put(new Vertex<>('D'), 2);
            expectedDijkstrasSame.put(new Vertex<>('C'), 1);
        }

        // A directed graph where one vertex has a single edge pointing outwards and that vertex is not the starting vertex
        @Test(timeout = TIMEOUT)
        public void testSemiDisconnectedDFS() {
            graph = new Graph<>(verticesSemi, edgesSemi);

            assertEquals(expectedDFSSemi, GraphAlgorithms.dfs(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testSemiDisconnectedDijkstras() {
            graph = new Graph<>(verticesSemi, edgesSemi);

            assertEquals(expectedDijkstrasSemi, GraphAlgorithms.dijkstras(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testSemiDisconnectedPrims() {
            graph = new Graph<>(verticesSemi, edgesSemi);

            assertNull(GraphAlgorithms.prims(startVertex, graph));
        }

        // A graph where every edge has the same weight
        @Test(timeout = TIMEOUT)
        public void testSameWeightsDFS() {
            graph = new Graph<>(verticesSame, edgesSame);

            assertEquals(expectedDFSSame, GraphAlgorithms.dfs(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testSameWeightsDijkstras() {
            graph = new Graph<>(verticesSame, edgesSame);

            assertEquals(expectedDijkstrasSame, GraphAlgorithms.dijkstras(startVertex, graph));
        }

        @Test(timeout = TIMEOUT)
        public void testSameWeightsPrims() {
            graph = new Graph<>(verticesSame, edgesSame);

            assertEquals(expectedPrimsSame, GraphAlgorithms.prims(startVertex, graph));
        }
    }
}