package nz.ac.auckland.se281.datastructures;

/** Building blocks of the LinkedList. */
public class Node<T> {
  private T val;
  private Node<T> next;
  private Node<T> prev;

  // constructor

  public Node() {}

  /**
   * Constuctor for a node that sets the intended value and the next and previous node to null.
   *
   * @param v Intended value for the node.
   */
  public Node(T v) {
    val = v;
    next = null;
    prev = null;
  }

  // getters and setters

  public void setNext(Node<T> n) {
    next = n;
  }

  public Node<T> getNext() {
    return next;
  }

  public void setPrev(Node<T> n) {
    prev = n;
  }

  public Node<T> getPrev() {
    return prev;
  }

  public T getValue() {
    return val;
  }
}
