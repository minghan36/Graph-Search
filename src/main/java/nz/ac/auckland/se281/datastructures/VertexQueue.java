package nz.ac.auckland.se281.datastructures;

public class VertexQueue<T> implements Queue<T> {

    private List<T> queue;
    
    public VertexQueue (){
        this.queue = new LinkedList<T>();
    }
    
    @Override
    public int size() {
        return queue.getSize();
    }

    @Override
    public boolean isEmpty() {
        if (size() == 0){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public T front() {
        return queue.fetch(0);
    }

    @Override
    public void enqueue(Object element) {

        queue.append(((T)element));
    }

    @Override
    public T dequeue() {
        
        T first = queue.fetch(0);
        queue.remove(0);
        return first;
    }
}
