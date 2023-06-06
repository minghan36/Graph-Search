package nz.ac.auckland.se281.datastructures;

/** Stack interface to be implemented by the VertexStack. */
public interface Stack<T> {

  public int size();

  public boolean isEmpty();

  public T peek();

  public void push(T element);

  public T pop();
}
