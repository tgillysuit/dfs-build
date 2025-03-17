import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;


public class Build {

  /**
   * Prints words that are reachable from the given vertex and are strictly shorter than k characters.
   * If the vertex is null or no reachable words meet the criteria, prints nothing.
   *
   * @param vertex the starting vertex
   * @param k the maximum word length (exclusive)
   */
  public static void printShortWords(Vertex<String> vertex, int k) {
    if (vertex == null) return;

    Set<Vertex<String>> visited = new HashSet<>();
    Stack<Vertex<String>> stack = new Stack<>();
    stack.push(vertex);

    while (!stack.isEmpty()) {
      Vertex<String> current = stack.pop();

      if (!visited.contains(current)) {
        visited.add(current);

        // Print only if the word length is less than k
        if (current.data.length() < k) {
          System.out.println(current.data);
        }

        for (Vertex<String> neighbor : current.neighbors) {
          stack.push(neighbor);
        }
      }
    }
  }

  /**
   * Returns the longest word reachable from the given vertex, including its own value.
   *
   * @param vertex the starting vertex
   * @return the longest reachable word, or an empty string if the vertex is null
   */
  public static String longestWord(Vertex<String> vertex) {
    if (vertex == null) return "";

    Set<Vertex<String>> visited = new HashSet<>();
    Stack<Vertex<String>> stack = new Stack<>();
    stack.push(vertex);
    
    String longestWord = "";
    
    while (!stack.isEmpty()) {
      Vertex<String> current = stack.pop();

      if (!visited.contains(current)) {
        visited.add(current);

        if (current.data.length() > longestWord.length()) {
          longestWord = current.data;
        }

        for (Vertex<String> neighbor : current.neighbors) {
          stack.push(neighbor);
        }
      }
    }
    
    return longestWord;
  }

  /**
   * Prints the values of all vertices that are reachable from the given vertex and 
   * have themself as a neighbor.
   *
   * @param vertex the starting vertex
   * @param <T> the type of values stored in the vertices
   */
  public static <T> void printSelfLoopers(Vertex<T> vertex) {
    if (vertex == null) return;

    Set<Vertex<T>> visited = new HashSet<>();
    Stack<Vertex<T>> stack = new Stack<>();
    stack.push(vertex);

    while (!stack.isEmpty()) {
      Vertex<T> current = stack.pop();
      
      if (!visited.contains(current)) {
        visited.add(current); 

        if(current.neighbors.contains(current)) {
          System.out.println(current.data);
        }

        for (Vertex<T> neighbor : current.neighbors) {
          stack.push(neighbor);
        }
      }
    }
  }

  /**
   * Determines whether it is possible to reach the destination airport through a series of flights
   * starting from the given airport. If the start and destination airports are the same, returns true.
   *
   * @param start the starting airport
   * @param destination the destination airport
   * @return true if the destination is reachable from the start, false otherwise
   */
  public static boolean canReach(Airport start, Airport destination) {
    if (start == null || destination == null) return false;
    if (start == destination) return true;
    
    Set<Airport> visited = new HashSet<>();
    Stack<Airport> stack = new Stack<>();
    stack.push(start);

    while (!stack.isEmpty()) {
      Airport current = stack.pop();

      if (!visited.contains(current)) {
        visited.add(current);

        if (current == destination) return true;

        for (Airport neighbor : current.getOutboundFlights()) {
          stack.push(neighbor);
        }
      }
    }
    
    return false;
  }

  /**
   * Returns the set of all values in the graph that cannot be reached from the given starting value.
   * The graph is represented as a map where each vertex is associated with a list of its neighboring values.
   *
   * @param graph the graph represented as a map of vertices to neighbors
   * @param starting the starting value
   * @param <T> the type of values stored in the graph
   * @return a set of values that cannot be reached from the starting value
   */
  public static <T> Set<T> unreachable(Map<T, List<T>> graph, T starting) {
    Set<T> visited = new HashSet<>();
    Stack<T> stack = new Stack<>();
    
    // Base Case:
    // If the starting node doesn't exist in the graph, return all nodes as unreachable
    if (!graph.containsKey(starting)) {
      return new HashSet<>(graph.keySet());
    }

    // Start DFS
    stack.push(starting);

    while (!stack.isEmpty()) {
      T current = stack.pop();

      if (!visited.contains(current)) {
        visited.add(current);

        List<T> neighbors = graph.get(current);
        if (neighbors != null) {
          for (T neighbor : neighbors) {
            stack.push(neighbor);
          }
        }
      }
    }

    // Determine the unreachable nodes from all the nodes and the visited one
    Set<T> unreachableNodes = new HashSet<>(graph.keySet());
    unreachableNodes.removeAll(visited);
    
    return unreachableNodes;
  }
}
