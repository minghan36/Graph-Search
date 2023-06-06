package nz.ac.auckland.se281.datastructures;

public class Node<T> {
  private T val;
  private Node<T> next;
  private Node<T> prev;

  // constructor

  public Node() {}

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
