package nz.ac.auckland.se281.datastructures;

public class VertexQueue<T> implements Queue<T> {

  private List<T> queue;

  public VertexQueue() {
    this.queue = new LinkedList<T>();
  }

  @Override
  public int size() {
    return queue.getSize();
  }

  @Override
  public boolean isEmpty() {
    if (queue.getSize() == 0) {
      return true;
    }
    return false;
  }

  /** Returns the front value of the queue without removing the node. */
  @Override
  public T front() {
    return queue.fetch(0);
  }

  /**
   * Adds the desired element to the end of the queue.
   *
   * @param element Desired element to be stored
   */
  @Override
  @SuppressWarnings("unchecked")
  public void enqueue(Object element) {

    queue.append(((T) element));
  }

  /** Returns the first element in the queue and removes the element from the queue. */
  @Override
  public T dequeue() {

    T first = queue.fetch(0);
    queue.remove(0);
    return first;
  }
}
