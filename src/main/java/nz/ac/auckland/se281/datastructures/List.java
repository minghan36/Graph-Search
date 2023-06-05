package nz.ac.auckland.se281.datastructures;

public interface List<T> {
    public void append(T item);

    public void prepend(T item);

    public T fetch(int pos);

    public void remove(int pos);

    public int getSize();

}