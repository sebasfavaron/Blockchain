import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Sebas on 8/23/17.
 */
public class MyLinkedList<S> implements Iterable<S> {
    private Node first;
    private int length;

    /**
     * Gets the S element in the i-th Node or null if i>length
     * @param i, position in the List
     */
    public S get(int i){
        int index = 0;
        for(S elem : this){
            index++;
            if(i==index)
                return elem;
        }
        return null;
    }
    private class MyIterator implements Iterator<S> {
        private Node current;
        private Node head;

        public MyIterator() {
            this.head = first;
            this.current = first;
        }

        public Node getCurrent(){
            return current;
        }
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public S next() {
            if (current != null) {
                S ret = current.element;
                current = current.next;
                return ret;
            } else
                throw new NoSuchElementException();
        }
    }

    private class Node {
        private S element;
        private Node next;
        Node(S element){
            this.element = element;
            this.next = null;
        }
        public void setNext(Node n){
            this.next = n;
        }
        public S getElement(){
            return element;
        }
        public Node getNext(){
            return next;
        }
    }

    public MyLinkedList(){
        this.first = null;
        this.length=0;
    }

    public int getLength(){
        return this.length;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public boolean contains(Object o) {
        S aux = (S)o;
        for(S elem : this){
            if(elem == aux)
                return true;
        }
        return false;
    }

    public MyIterator iterator() {
        return new MyIterator();
    }

    public boolean add(S s) {
        length++;
        if(first == null) {
            first = new Node(s);
            return true;
        }
        Node current = first;
        MyIterator it = iterator();
        while(current.next != null){
            current = current.next;
        }
        current.setNext(new Node(s));
        return true;
    }

    public void reverse(){
        if(first.next != null)
            reverseRec(first, first.next);
    }
    public void reverseRec(Node n, Node next){
        Node aux;
        if(n == first){
            aux = next.next;
            next.next = first;
            first.next = null;
            reverseRec(next, aux);
        }
        else if(next != null) {
            aux = next.next;
            next.next = n;
            reverseRec(next, aux);
        }
        else{
            first = n;
        }
    }
    /**
     * Le da funcionalidad de cola, saca el primer nodo (poniendo al segundo como primero
     * y devuelve el elemento del primer nodo.
     * @return 1st element
     */
    public S removeFirst(){
        if(first == null)
            return null;
        S ret = first.element;
        first = first.next;
        return ret;
    }

    public int size(){
        int size = 0;
        for(S elem : this){
            size++;
        }
        return size;
    }

    public void print(){
        for(S elem : this){
            System.out.println(elem);
        }
    }

    public boolean addAll(Collection<? extends S> c) {
        return false;
    }
}
