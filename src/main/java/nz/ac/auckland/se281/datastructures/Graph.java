package nz.ac.auckland.se281.datastructures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * A graph that is composed of a set of verticies and edges.
 *
 * <p>You must NOT change the signature of the existing methods or constructor of this class.
 *
 * @param <T> The type of each vertex, that have a total ordering.
 */
public class Graph<T extends Comparable<T>> {

  private Set<T> verticies;
  private Set<Edge<T>> edges;

  public Graph(Set<T> verticies, Set<Edge<T>> edges) {
    this.verticies = verticies;
    this.edges = edges;
  }

  /**
   * Returns the roots of the graph. Checks is any of the verticies have an in degree of 0 and out
   * degree > 0 OR finds the least value in an EquivalenceClass.
   */
  public Set<T> getRoots() {
    // Comparator set to sort from least to highest integer value.
    Set<T> roots =
        new TreeSet<T>(
            new Comparator<T>() {
              @Override
              public int compare(T v1, T v2) {
                int value1 = Integer.parseInt((String) v1);
                int value2 = Integer.parseInt((String) v2);
                if (value1 < value2) {
                  return -1;
                } else if (value1 == value2) {
                  return 0;
                } else {
                  return 1;
                }
              }
            });
    int in;
    int out;
    for (T vertex : verticies) {
      in = 0;
      out = 0;
      if (getEquivalenceClass(vertex).isEmpty()) {
        for (Edge<T> edge : edges) {
          if (edge.getSource().equals(vertex)) {
            out++;
          }
          if (edge.getDestination().equals(vertex)) {
            in++;
          }
        }
        if (in == 0 && out > 0) {
          roots.add(vertex);
        }
      } else {
        roots.add(getEquivalenceClass(vertex).iterator().next());
      }
    }
    return roots;
  }

  /**
   * Checks reflexivity of the graph. Iterates through all the verticies and returns false if there
   * is no edge where the source and destination are both equal to the vertex.
   */
  public boolean isReflexive() {
    boolean reflexive;
    for (T vertex : verticies) {
      reflexive = false;
      for (Edge<T> edge : edges) {
        if (edge.getSource().equals(vertex) && edge.getDestination().equals(vertex)) {
          reflexive = true;
          continue;
        }
      }
      if (!reflexive) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks symmetry of graph. Iterates through edges and returns false if an edge does not have a
   * corresponding edge where the new source = original destination and new destination = original
   * source.
   */
  public boolean isSymmetric() {
    boolean symmetric;
    for (Edge<T> edge1 : edges) {
      symmetric = false;
      for (Edge<T> edge2 : edges) {
        if (edge1.getSource().equals(edge2.getDestination())
            && edge2.getSource().equals(edge1.getDestination())) {
          symmetric = true;
          continue;
        }
      }
      if (!symmetric) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks transitivity of graph. Iterates through edges, returns false if there is not a single
   * edge that represents the original source and final destination of two connected edges.
   */
  public boolean isTransitive() {
    boolean transitive;
    T source;
    for (Edge<T> edge1 : edges) {
      source = edge1.getSource();
      for (Edge<T> edge2 : edges) {
        if (edge2.getSource().equals(edge1.getDestination())) {
          transitive = false;
          for (Edge<T> edge3 : edges) {
            if (source.equals(edge3.getSource())
                && edge2.getDestination().equals(edge3.getDestination())) {
              transitive = true;
            }
          }
          if (!transitive) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * Checks antisymmetry of graph. Iterates through edges, returns false if an edge has a
   * corresponding symmetrical edge between two different verticies.
   */
  public boolean isAntiSymmetric() {
    boolean antiSymmetric = true;
    for (Edge<T> edge1 : edges) {
      for (Edge<T> edge2 : edges) {
        if (edge1.getSource().equals(edge2.getDestination())
            && edge2.getSource().equals(edge1.getDestination())) {
          antiSymmetric = false;
          if (edge1.getSource().equals(edge1.getDestination())) {
            antiSymmetric = true;
          }
        }
        if (!antiSymmetric) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Checks Equivalence of graph. Return true only if the graph is reflexive AND symmetric AND
   * transitive.
   */
  public boolean isEquivalence() {
    if (isReflexive() && isSymmetric() && isTransitive()) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Finds the Equivalence class for a given vertex. Returns an empty set if the graph is does not
   * have equivalent relations. Otherwise, iterates through edges to find all verticies that the
   * given vertex has a relation to.
   *
   * @param vertex The vertex for which the equivalence class is to be found for... [vertex]
   */
  public Set<T> getEquivalenceClass(T vertex) {
    Set<T> equivalenceClass =
        new TreeSet<T>(
            new Comparator<T>() {
              @Override
              public int compare(T v1, T v2) {
                int value1 = Integer.parseInt((String) v1);
                int value2 = Integer.parseInt((String) v2);
                if (value1 < value2) {
                  return -1;
                } else if (value1 == value2) {
                  return 0;
                } else {
                  return 1;
                }
              }
            });

    if (!isEquivalence()) {
      return equivalenceClass;
    }

    equivalenceClass.add(vertex);

    boolean finish = false;

    while (!finish) {
      finish = true;
      for (Edge<T> edge1 : edges) {
        if (equivalenceClass.contains(edge1.getSource())
            && !equivalenceClass.contains(edge1.getDestination())) {
          equivalenceClass.add(edge1.getDestination());
          finish = false;
        }
        if (equivalenceClass.contains(edge1.getDestination())
            && !equivalenceClass.contains(edge1.getSource())) {
          equivalenceClass.add(edge1.getSource());
          finish = false;
        }
      }
    }

    return equivalenceClass;
  }

  /**
   * Uses a queue data structure to perform an iterative breadth first search on the graph. One
   * vertex is analysed at a time to find the verticies associated with that vertex until a vertex
   * has no further relations.
   */
  public List<T> iterativeBreadthFirstSearch() {
    List<T> visited = new ArrayList<T>();
    Queue<T> queue = new VertexQueue<T>();
    Set<T> roots = getRoots();
    for (T root : roots) {
      queue.enqueue(root);
    }
    while (!queue.isEmpty()) {
      Set<T> temp = BFSHelper(queue.front(), visited);
      for (T vertex : temp) {
        queue.enqueue(vertex);
      }

      if (visited.contains(queue.front())) {
        queue.dequeue();
      } else {
        visited.add(queue.dequeue());
      }
    }
    return visited;
  }

  /**
   * Uses a stack data structure to perform an iterative depth first search on the graph. Edges are
   * analysed one at a time, once a vertex has been visited it is popped, and the process continues
   * on for the next edge.
   */
  public List<T> iterativeDepthFirstSearch() {
    List<T> visited = new ArrayList<T>();
    Stack<T> stack = new VertexStack<T>();
    Set<T> roots = getRoots();
    // Comparator sorts in descending order.
    Set<T> set =
        new TreeSet<T>(
            new Comparator<T>() {
              @Override
              public int compare(T v1, T v2) {
                int value1 = Integer.parseInt((String) v1);
                int value2 = Integer.parseInt((String) v2);
                if (value1 > value2) {
                  return -1;
                } else if (value1 == value2) {
                  return 0;
                } else {
                  return 1;
                }
              }
            });
    for (T root : roots) {
      set.add(root);
    }
    for (T root : set) {
      stack.push(root);
    }

    while (!stack.isEmpty()) {
      Set<T> temp = DFSHelper(stack.peek(), visited);
      if (visited.contains(stack.peek())) {
        stack.pop();
      } else {
        visited.add(stack.pop());
      }
      for (T vertex : temp) {
        stack.push(vertex);
      }
    }

    return visited;
  }

  /** Recursive form of the iterative breadth first search */
  public List<T> recursiveBreadthFirstSearch() {
    List<T> visited = new ArrayList<T>();
    Queue<T> queue = new VertexQueue<T>();
    Set<T> roots = getRoots();
    for (T root : roots) {
      queue.enqueue(root);
    }

    return BFSRecursive(visited, queue);
  }

  /** Recursive form of the iterative depth first search */
  public List<T> recursiveDepthFirstSearch() {
    List<T> visited = new ArrayList<T>();
    Stack<T> stack = new VertexStack<T>();
    Set<T> roots = getRoots();
    Set<T> set =
        new TreeSet<T>(
            new Comparator<T>() {
              @Override
              public int compare(T v1, T v2) {
                int value1 = Integer.parseInt((String) v1);
                int value2 = Integer.parseInt((String) v2);
                if (value1 > value2) {
                  return -1;
                } else if (value1 == value2) {
                  return 0;
                } else {
                  return 1;
                }
              }
            });
    for (T root : roots) {
      set.add(root);
    }
    for (T root : set) {
      stack.push(root);
    }

    return DFSRecursive(visited, stack);
  }

  /**
   * Helper method for Breadth First Search. Returns the set of verticies which is directly related
   * to a given vertex. Verticies that have already been visited are left out.
   *
   * @param vertex Desired vertex to be analysed
   * @param visited List of previously visited verticies
   */
  private Set<T> BFSHelper(T vertex, List<T> visited) {
    Set<T> set =
        new TreeSet<T>(
            new Comparator<T>() {
              @Override
              public int compare(T v1, T v2) {
                int value1 = Integer.parseInt((String) v1);
                int value2 = Integer.parseInt((String) v2);
                if (value1 < value2) {
                  return -1;
                } else if (value1 == value2) {
                  return 0;
                } else {
                  return 1;
                }
              }
            });

    for (Edge<T> edge : edges) {
      if (edge.getSource().equals(vertex) && !edge.getDestination().equals(vertex)) {
        if (visited.contains(edge.getDestination())) {
          continue;
        }
        set.add(edge.getDestination());
      }
    }

    return set;
  }

  /**
   * Helper method for Depth First Search. Returns the set of verticies which is directly related to
   * a given vertex. Verticies that have already been visited are left out.
   *
   * @param vertex Desired vertex to be analysed
   * @param visited List of previously visited verticies
   */
  private Set<T> DFSHelper(T vertex, List<T> visited) {
    Set<T> set =
        new TreeSet<T>(
            new Comparator<T>() {
              @Override
              public int compare(T v1, T v2) {
                int value1 = Integer.parseInt((String) v1);
                int value2 = Integer.parseInt((String) v2);
                if (value1 > value2) {
                  return -1;
                } else if (value1 == value2) {
                  return 0;
                } else {
                  return 1;
                }
              }
            });

    for (Edge<T> edge : edges) {
      if (edge.getSource().equals(vertex) && !edge.getDestination().equals(vertex)) {
        if (visited.contains(edge.getDestination())) {
          continue;
        }
        set.add(edge.getDestination());
      }
    }

    return set;
  }

  /**
   * Recursive BFS that calls itself until the queue is empty (all verticies have been analysed).
   *
   * @param visited Previously visited verticies
   * @param queue The queue needed to implement BFS. Queue of verticies.
   */
  private List<T> BFSRecursive(List<T> visited, Queue<T> queue) {
    if (queue.isEmpty()) {
      return visited;
    } else {
      Set<T> temp = BFSHelper(queue.front(), visited);
      for (T vertex : temp) {
        queue.enqueue(vertex);
      }

      if (visited.contains(queue.front())) {
        queue.dequeue();
        return visited;
      } else {
        visited.add(queue.dequeue());
        return BFSRecursive(visited, queue);
      }
    }
  }

  /**
   * Recursive DFS that calls itself until the Stack is empty (all edges have been analysed).
   *
   * @param visited Previously visited verticies
   * @param stack The stack needed to implement DFS. stack of verticies.
   */
  private List<T> DFSRecursive(List<T> visited, Stack<T> stack) {
    if (stack.isEmpty()) {
      return visited;
    } else {
      Set<T> temp = DFSHelper(stack.peek(), visited);
      if (visited.contains(stack.peek())) {
        stack.pop();
      } else {
        visited.add(stack.pop());
      }
      for (T vertex : temp) {
        stack.push(vertex);
      }
      return DFSRecursive(visited, stack);
    }
  }
}
