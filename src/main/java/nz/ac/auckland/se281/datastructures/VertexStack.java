package nz.ac.auckland.se281.datastructures;

public class VertexStack<T> implements Stack<T> {

  private List<T> stack;

  public VertexStack() {
    this.stack = new LinkedList<T>();
  }

  @Override
  public int size() {
    return stack.getSize();
  }

  @Override
  public boolean isEmpty() {
    if (size() == 0) {
      return true;
    } else {
      return false;
    }
  }

  /** Returns to last value in the stack without removing the node from the linkedlist. */
  @Override
  public T peek() {
    return stack.fetch((size() - 1));
  }

  /**
   * Adds desired element to the end of the stack
   *
   * @param element Desired element to be stored
   */
  @Override
  public void push(T element) {
    stack.append(element);
  }

  /** Returns the last element in the stack and removes the element from the stack. */
  @Override
  public T pop() {
    T last = peek();
    stack.remove((size() - 1));
    return last;
  }
}
