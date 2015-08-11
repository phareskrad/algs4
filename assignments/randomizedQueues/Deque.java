package assignments;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Leon on 7/6/15.
 */
public class Deque<Item> implements Iterable<Item>{
    private int N;
    private Node<Item> first;
    private Node<Item> last;

    public Deque() {
        first = null;
        last = null;
        N = 0;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        Node oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        if (isEmpty()) last = first;
        else oldfirst.previous = first;
        N++;
    }

    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        Node oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.previous = oldlast;
        if (isEmpty()) first = last;
        else oldlast.next = last;
        N++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        N--;
        if (isEmpty()) last = null;
        else first.previous = null;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        last = last.previous;
        N--;
        if (isEmpty()) first = null;
        else last.next = null;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {current = first;}
        public boolean hasNext() {return current != null;}
        public void remove() {throw new UnsupportedOperationException();}

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> previous;
    }

    public static void main(String[] args) {

    }  // unit testing
}
