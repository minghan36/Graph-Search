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
      if (getEquivalenceClass(vertex).isEmpty()){
        for (Edge<T> edge: edges){
          if (edge.getSource().equals(vertex)){
            out++;
          }
          if (edge.getDestination().equals(vertex)){
            in++;
          }
        }
        if (in == 0 && out > 0){
          roots.add(vertex);
        }
      } else {
        roots.add(getEquivalenceClass(vertex).iterator().next());
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
    boolean symmetric;
    for (Edge<T> edge1: edges){
      symmetric = false;
      for (Edge<T> edge2: edges){
        if (edge1.getSource().equals(edge2.getDestination()) && edge2.getSource().equals(edge1.getDestination())){
          symmetric = true;
          continue;
        }
      }
      if (!symmetric){
        return false;
      }
    }
    return true;
  }

  public boolean isTransitive() {
    boolean transitive;
    T source;
    for (Edge<T> edge1: edges){
    source = edge1.getSource();
    for (Edge<T> edge2: edges){
      if (edge2.getSource().equals(edge1.getDestination())){
        transitive = false;
        for (Edge<T> edge3: edges){
          if (source.equals(edge3.getSource()) && edge2.getDestination().equals(edge3.getDestination())){
            transitive = true;
          }
        }
        if (!transitive){
          return false;
        }
      }
    }
  }
    return true;
  }

  public boolean isAntiSymmetric() {
    boolean antiSymmetric = true;
    for (Edge<T> edge1: edges){
      for (Edge<T> edge2: edges){
        if (edge1.getSource().equals(edge2.getDestination()) && edge2.getSource().equals(edge1.getDestination())){
          antiSymmetric = false;
          if(edge1.getSource().equals(edge1.getDestination())){
            antiSymmetric = true;
          }
        }
        if (!antiSymmetric){
          return false;
        }
      }
    }
    return true;
  }

  public boolean isEquivalence() {
    if (isReflexive() && isSymmetric() && isTransitive()){
      return true;
    } else {
      return false;
    }
  }

  public Set<T> getEquivalenceClass(T vertex) {    
    Set<T> equivalenceClass = new TreeSet<T>(new Comparator<T>() {
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

    if (!isEquivalence()){
      return equivalenceClass;
    }

    equivalenceClass.add(vertex);
    
    boolean finish = false;

    while (!finish){
      finish = true;
      for (Edge<T> edge1: edges){
        if (equivalenceClass.contains(edge1.getSource()) && !equivalenceClass.contains(edge1.getDestination())){
          equivalenceClass.add(edge1.getDestination());
          finish = false;
        }
        if (equivalenceClass.contains(edge1.getDestination()) && !equivalenceClass.contains(edge1.getSource())){
          equivalenceClass.add(edge1.getSource());
          finish = false;
        }
      }
    }

    return equivalenceClass;
  }

  public List<T> iterativeBreadthFirstSearch() {
    List<T> visited = new ArrayList<T>();
    Queue<T> queue = new VertexQueue<T>();
    Set<T> roots = getRoots();
    for (T root:roots){
    queue.enqueue(root);
    }
    while (!queue.isEmpty()){
      Set<T> temp = BFSHelper(queue.front(), visited);
      for (T vertex: temp){
        queue.enqueue(vertex);
      }
    
    if(visited.contains(queue.front())){
      queue.dequeue();
    } else{
    visited.add(queue.dequeue());
    }
    }
    return visited;
  }

  public List<T> iterativeDepthFirstSearch() {
    List<T> visited = new ArrayList<T>();
    Stack<T> stack = new VertexStack<T>();
    Set<T> roots = getRoots();
    Set<T> set = new TreeSet<T>(new Comparator<T>() {
      @Override
      public int compare(T v1, T v2){
        int value1 = Integer.parseInt((String) v1);
        int value2 = Integer.parseInt((String) v2);
        if (value1>value2){
          return -1;
        } else if (value1 == value2){
          return 0;
        } else {
          return 1;
        }
      }
    });
    for (T root:roots){
      set.add(root);
      }

    for (T root:set){
      stack.push(root);
    }
    while (!stack.isEmpty()){
      Set<T> temp = DFSHelper(stack.peek(), visited);
      if(visited.contains(stack.peek())){
        stack.pop();
      } else {
        visited.add(stack.pop());
      }
      for (T vertex: temp){
        stack.push(vertex);
      }
    }

    return visited;
  }

  public List<T> recursiveBreadthFirstSearch() {
    List<T> visited = new ArrayList<T>();
    Queue<T> queue = new VertexQueue<T>();
    Set<T> roots = getRoots();
    for (T root:roots){
      queue.enqueue(root);
    }

    return BFSRecursive(visited, queue);
      
  }

  public List<T> recursiveDepthFirstSearch() {
    // TODO: Task 3.
    throw new UnsupportedOperationException();
  }

  private Set<T> BFSHelper(T vertex, List<T> visited){
    Set<T> set = new TreeSet<T>(new Comparator<T>() {
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

    for (Edge<T> edge: edges){
      if (edge.getSource().equals(vertex) && !edge.getDestination().equals(vertex)){
        if (visited.contains(edge.getDestination())){
          continue;
        }
        set.add(edge.getDestination());
      }
    }

    return set;
  }

  private Set<T> DFSHelper(T vertex, List<T> visited){
    Set<T> set = new TreeSet<T>(new Comparator<T>() {
      @Override
      public int compare(T v1, T v2){
        int value1 = Integer.parseInt((String) v1);
        int value2 = Integer.parseInt((String) v2);
        if (value1>value2){
          return -1;
        } else if (value1 == value2){
          return 0;
        } else {
          return 1;
        }
      }
    });

    for (Edge<T> edge: edges){
      if (edge.getSource().equals(vertex) && !edge.getDestination().equals(vertex)){
        if (visited.contains(edge.getDestination())){
          continue;
        }
        set.add(edge.getDestination());
      }
    }

    return set;
  }

  private List<T> BFSRecursive (List<T> visited, Queue<T> queue){
    if(queue.isEmpty()){
      return visited;
    } else {
      Set<T> temp = BFSHelper(queue.front(), visited);
      for (T vertex: temp){
      queue.enqueue(vertex);
      }

      if(visited.contains(queue.front())){
        queue.dequeue();
        return visited;
      } else {
        visited.add(queue.dequeue());
        return BFSRecursive(visited, queue);
      }
    }
  }
}
