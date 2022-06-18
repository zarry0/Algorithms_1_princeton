import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item>{
        // construct an empty randomized queue
        public RandomizedQueue() {}

        // is the randomized queue empty?
        public boolean isEmpty() { return false;}
    
        // return the number of items on the randomized queue
        public int size() { return 0;}
    
        // add the item
        public void enqueue(Item item) {}
    
        // remove and return a random item
        public Item dequeue() { return (Item) new Object();}
    
        // return a random item (but do not remove it)
        public Item sample() { return (Item) new Object(); }
    
        // return an independent iterator over items in random order
        public Iterator<Item> iterator() { return new RandomizedQueueIterator(); }

        private class RandomizedQueueIterator implements Iterator<Item> {
            public boolean hasNext() {return false;}
            public void remove() { throw new UnsupportedOperationException(); }
            public Item next() {
                return (Item) new Object();
            }
        }
    
        // unit testing (required)
        public static void main(String[] args) {}
}