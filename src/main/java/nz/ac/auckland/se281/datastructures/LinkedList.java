package nz.ac.auckland.se281.datastructures;

/** Implements the interface List and to be used to construct the Stack and Queue. */
public class LinkedList<T> implements List<T> {
  private Node<T> head;
  private Node<T> tail;
  private int size;

  /** Constructor for LinkedList that sets nodes to null and size to 0. */
  public LinkedList() {
    head = null;
    tail = null;
    size = 0;
  }

  /**
   * Creates a node for the new data and attaches to the beginning of the linked list. New Node
   * becomes the head and sets previous head as the next node.
   *
   * @param data data that is being stored in the node.
   */
  public void prepend(T data) {
    Node<T> n = new Node<T>(data);
    n.setNext(head);
    head = n;
    size++;
  }

  /**
   * Creates a node for the new data and attaches to the end of the linked list. New Node becomes
   * the tail and sets previous tail as the previous node.
   *
   * @param data data that is being stored in the node.
   */
  public void append(T data) {
    Node<T> n = new Node<T>(data);
    n.setPrev(tail);
    // Sets both head and tail to the new node if the node is the first in the LinkedList.
    if (size == 0) {
      head = n;
    }
    if (size > 0) {
      tail.setNext(n);
    }
    tail = n;
    size++;
  }

  /**
   * Returns value of the node in a specified position in the linked list by comparing the
   * currentindex of a node and iterating until the desired index is reached. If the desired
   * position is either the head or tail, method will avoid iterating through the linkedlist.
   *
   * @param pos desired index in the linkedlist.
   * @return Value of the Node in position 'pos'.
   */
  public T fetch(int pos) {
    // checks validity of requested position and whether the requested node is the Head or Tail.
    if (pos >= size) {
      return null;
    } else if (pos == 0) {
      return head.getValue();
    } else if (pos == (size - 1)) {
      return tail.getValue();
    }
    // Iterates to find desired node.
    int currentIndex = 0;
    Node<T> currentNode = head;
    while (!(currentIndex == pos)) {
      currentNode = currentNode.getNext();
      currentIndex++;
    }
    return currentNode.getValue();
  }

  /**
   * Removes the desired node (out of the head or tail) depending on the index inputted. If input is
   * '0', the head will be set as the next node, and the previous node linked to the new node will
   * be set to null. If input matches the position of the tail, the tail will be set as the previous
   * node, and next node set to null for new node.
   *
   * @param pos Desired position, either head or tail.
   */
  public void remove(int pos) {
    // If statements checking whether the head or the tail is to be removed. Also replaces the head
    // or tail and changes the next/prev value in the new head/tail.
    if (pos == 0) {
      head = head.getNext();
      if (head != null) {
        head.setPrev(null);
      }
      size--;
      return;
    }
    if (pos == (size - 1)) {
      tail = tail.getPrev();
      if (tail != null) {
        tail.setNext(null);
      }
      size--;
    }
  }

  /**
   * Returns size of linkedlist (how many nodes there are).
   *
   * @return size of linkedlist.
   */
  public int getSize() {
    return size;
  }
}
