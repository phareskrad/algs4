package assignments;

import stdlib.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Leon on 7/6/15.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int N;
    private Item[] q;

    public RandomizedQueue() {
        q = (Item[]) new Object[2];
        N = 0;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    private void resize(int max) {
        assert max >= N;
        Item[] temp = (Item[]) new Object[max];
        int k = 0;
        for (int i = 0; i < N; i++) {
            temp[i] = q[i];
        }
        q = temp;
    }

    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if (N == q.length) resize(2*q.length);
        q[N++] = item;
    }

    private void swap(Item[] a, int i, int j) {
        Item tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int r = StdRandom.uniform(N);
        swap(q, r, N - 1);
        Item item = q[N - 1];
        q[N - 1] = null;
        N--;
        if (N > 0 && N == q.length/4) resize(q.length/2);
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int r = StdRandom.uniform(N);
        return q[r];
    }                    // return (but do not remove) a random item

    public Iterator<Item> iterator() {return new ArrayIterator();}         // return an independent iterator over items in random order

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        private Item[] temp;

        public ArrayIterator() {
            temp = (Item[]) new Object[N];
            for (int i = 0; i < N; i++) {
                temp[i] = q[i];
            }
            StdRandom.shuffle(temp);
        }

        public boolean hasNext()  { return i < N; }

        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = temp[i++];
            return item;
        }

    }

    public static void main(String[] args) {

    }  // unit testing
}
