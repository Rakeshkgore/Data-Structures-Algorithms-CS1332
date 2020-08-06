import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various graph algorithms.
 *
 * @author Rakesh Gorrepati
 * @version 1.0
 * @userid rgorrepati2
 * @GTID 903254051
 *
 * Collaborators: N/A
 *
 * Resources: TA's, Modules, SriKrishna Slides PsuedoCode
 */
public class GraphAlgorithms {

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * NOTE: You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (graph == null || start == null || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Graph or start is null");
        }
        List<Vertex<T>> result = new ArrayList<>();
        Set<Vertex<T>> visited = new HashSet<>();

        dfs(start, graph, visited, result);
        return result;
    }

    /**
     *
     * @param vertex the vertex to begin the dfs on
     * @param graph the graph to search through
     * @param curr the visited vertex set
     * @param result the list that is returned
     * @param <T> the generic typing of the data
     */
    private static <T> void dfs(Vertex<T> vertex, Graph<T> graph, Set<Vertex<T>> curr, List<Vertex<T>> result) {
        result.add(vertex);
        curr.add(vertex);

        for (VertexDistance<T> curr1 : graph.getAdjList().get(vertex)) {
            if (!curr.contains(curr1.getVertex())) {
                dfs(curr1.getVertex(), graph, curr, result);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (graph == null || start == null || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Arguments can't be null");
        }
        Queue<VertexDistance<T>> queue = new PriorityQueue<>();
        Map<Vertex<T>, Integer> map = new HashMap<>();

        for (Vertex<T> v : graph.getAdjList().keySet()) {
            if (v.equals(start)) {
                map.put(v, 0);
            } else {
                map.put(v, Integer.MAX_VALUE);
            }
        }

        queue.add(new VertexDistance<>(start, 0));
        while (!queue.isEmpty()) {
            VertexDistance<T> temp = queue.remove();
            for (VertexDistance<T> vDistance : graph.getAdjList().get(temp.getVertex())) {
                int distance = temp.getDistance() + vDistance.getDistance();
                if (map.get(vDistance.getVertex()) > distance) {
                    map.put(vDistance.getVertex(), distance);
                    queue.add(new VertexDistance<>(vDistance.getVertex(), distance));
                }
            }
        }
        return map;

    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use java.util.PriorityQueue, java.util.Set, and any
     * class that implements the aforementioned interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of java.util.Map that you may use is the adjacency
     * list from graph. DO NOT create new instances of Map for this method
     * (storing the adjacency list in a variable is fine).
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Start or Graph are null");
        }
        PriorityQueue<Edge<T>> curr = new PriorityQueue<>(graph.getEdges());
        Set<Edge<T>> results = new HashSet<>();
        Set<Vertex<T>> visitedSet = new HashSet<>();

        visitedSet.add(start);
        while (!curr.isEmpty()) {
            Edge<T> temp = curr.remove();
            if (!visitedSet.contains(temp.getU()) || !visitedSet.contains(temp.getV())) {
                results.add(temp);
                results.add(new Edge<>(temp.getV(), temp.getU(), temp.getWeight()));
                visitedSet.add(temp.getV());
                for (Edge<T> edge : graph.getEdges()) {
                    if (edge.getV().equals(temp.getU())) {
                        results.add(edge);
                    } else {
                        curr.add(edge);
                    }
                }
            }
        }
        if (results.size() < graph.getVertices().size() - 1) {
            return null;
        }
        return results;
    }
}
