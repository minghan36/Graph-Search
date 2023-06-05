package nz.ac.auckland.se281.datastructures;

public class LinkedList<T> implements List<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public LinkedList() {
	  head = null;
      tail = null;
      size = 0;
    }


    public void prepend(T data) {
	  Node<T> n = new Node<T>(data);
	  n.setNext(head);
	  head = n;
      size++;
    }


    public void append(T data) {
    Node<T> n = new Node<T>(data);
	n.setPrev(tail);
	tail = n;
    size++;
    }


    public T fetch(int pos){
    if (pos>=size){
        return null;
    }
    int currentIndex = 0;
    Node<T> currentNode = head;
    while(!(currentIndex==pos)){
    currentNode = currentNode.getNext();
    currentIndex++;
    }
    return currentNode.getValue();
    }


    public void remove(int pos){
        if (pos == 0){
            head = head.getNext();
            return;
        }
        if (pos == (size-1)){
            tail = tail.getPrev();
        }
    }

}
