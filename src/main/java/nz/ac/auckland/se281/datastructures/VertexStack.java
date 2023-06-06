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

  @Override
  public T peek() {
    return stack.fetch((size() - 1));
  }

  @Override
  public void push(T element) {
    stack.append(element);
  }

  @Override
  public T pop() {
    T last = peek();
    stack.remove((size() - 1));
    return last;
  }
}
