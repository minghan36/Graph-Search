package nz.ac.auckland.se281.datastructures;

public interface Queue<T> {

  public int size();

  public boolean isEmpty();

  public T front();

  public void enqueue(T element);

  public T dequeue();
}
