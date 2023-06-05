package nz.ac.auckland.se281.datastructures;

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

  public Set<T> getRoots() {
    Set<T> roots = new TreeSet<T>(new Comparator<T>() {
      @Override
      public int compare(T v1, T v2){
        int value1 = Integer.parseInt((String) v1);
        int value2 = Integer.parseInt((String) v2);
        if (value1<value2){
          return -1;
        } else if (value1 == value2){
          return 0;
        } else {
          return 1;
        }
      }
    });
    int in;
    int out;
    for (T vertex: verticies){
      in = 0;
      out = 0;
      for (Edge<T> edge: edges){

      }
    }
    return roots;
  }

  public boolean isReflexive() {
    boolean reflexive;
    for (T vertex: verticies){
      reflexive = false;
      for (Edge<T> edge: edges){
        if (edge.getSource().equals(vertex) && edge.getDestination().equals(vertex)){
          reflexive = true;
          continue;
        }
      }
      if (!reflexive){
        return false;
      }
    }
    return true;
  }

  public boolean isSymmetric() {
    // TODO: Task 1.
    throw new UnsupportedOperationException();
  }

  public boolean isTransitive() {
    // TODO: Task 1.
    throw new UnsupportedOperationException();
  }

  public boolean isAntiSymmetric() {
    // TODO: Task 1.
    throw new UnsupportedOperationException();
  }

  public boolean isEquivalence() {
    // TODO: Task 1.
    throw new UnsupportedOperationException();
  }

  public Set<T> getEquivalenceClass(T vertex) {
    // TODO: Task 1.
    throw new UnsupportedOperationException();
  }

  public List<T> iterativeBreadthFirstSearch() {
    // TODO: Task 2.
    throw new UnsupportedOperationException();
  }

  public List<T> iterativeDepthFirstSearch() {
    // TODO: Task 2.
    throw new UnsupportedOperationException();
  }

  public List<T> recursiveBreadthFirstSearch() {
    // TODO: Task 3.
    throw new UnsupportedOperationException();
  }

  public List<T> recursiveDepthFirstSearch() {
    // TODO: Task 3.
    throw new UnsupportedOperationException();
  }
}
