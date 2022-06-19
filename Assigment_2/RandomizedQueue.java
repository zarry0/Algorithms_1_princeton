import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item>{

        private Item[] queue;
        private int itemCount, tail;

        // construct an empty randomized queue
        public RandomizedQueue() {
            queue = (Item[]) new Object[]{null};
            itemCount = tail = 0;
        }

        // is the randomized queue empty?
        public boolean isEmpty() { return itemCount == 0;}
    
        // return the number of items on the randomized queue
        public int size() { return itemCount; }
    
        // add the item
        public void enqueue(Item item) {
            if (item == null) throw new IllegalArgumentException("Can't enqueue a null value");
            if (tail == queue.length) resize(queue.length * 2);
            queue[tail++] = item;
            itemCount++;
        }
    
        // remove and return a random item
        public Item dequeue() { 
            if (isEmpty()) throw new NoSuchElementException("The queue is empty");
            Item selectedItem = null;
            int n = 0;
            while (selectedItem == null) {
                n = StdRandom.uniform(tail);
                selectedItem = queue[n];
            }
            queue[n] = null;
            itemCount--;
            if (!isEmpty() && itemCount == queue.length/4) resize(queue.length/2);
            return selectedItem;
        }
    
        // return a random item (but do not remove it)
        public Item sample() {
            if (isEmpty()) throw new NoSuchElementException("The queue is empty");
            int n;
            while (true) {
                n = StdRandom.uniform(tail);
                if (queue[n] != null) return queue[n];
            } 
        }
    
        // return an independent iterator over items in random order
        public Iterator<Item> iterator() { return new RandomizedQueueIterator(); }

        private class RandomizedQueueIterator implements Iterator<Item> {
            private int i;
            private Item[] shuffledQueue;
            RandomizedQueueIterator () {
                i = 0;
                shuffledQueue = copy();
                StdRandom.shuffle(shuffledQueue);
            }
            public boolean hasNext() {return i < itemCount;}
            public void remove() { throw new UnsupportedOperationException(); }
            public Item next() {
                if (!hasNext()) throw new NoSuchElementException("No more items in the queue");
                return shuffledQueue[i++];
            }
        }
    
        // unit testing (required)
        public static void main(String[] args) {
            RandomizedQueue<Integer> bag = new RandomizedQueue<>();

            int n = 10;
            StdOut.println(bag.queueToString());
            for (int i = 0; i < n; i++) {
                bag.enqueue(i);
                StdOut.println(bag.queueToString());
            }
            
            for (int i = 0; i < bag.queueLength(); i++){
                StdOut.println(bag.sample());
            }
            StdOut.println(bag.queueToString());

            for (int i = 0; i < n; i++){
                StdOut.println("\n");
                StdOut.println("Deleted: " + bag.dequeue());
                StdOut.println(bag.queueToString());
            }

            RandomizedQueue<String> queue = new RandomizedQueue<>();
            String[] words = new String[]{"to", "be", "or", "not", "to", "-", "be", "-", "-", "that", "-", "-", "-", "is"};
            for (int i = 0; i < words.length; i++) {
                if (words[i] == "-") StdOut.println(queue.dequeue());
                else queue.enqueue(words[i]);
                StdOut.println(queue.queueToString());
            }

            RandomizedQueue<Integer> q = new RandomizedQueue<>();
            for (int i = 0; i < 5; i++) q.enqueue(i);
            StdOut.println(q.queueToString());
            for (int i : q) StdOut.print(i +", ");
            StdOut.println("\n");

            int m = 5;
            RandomizedQueue<Integer> queueIterator = new RandomizedQueue<Integer>();
            for (int i = 0; i < m; i++)
                queueIterator.enqueue(i);
            for (int a : queueIterator) {
                for (int b : queueIterator)
                    StdOut.print(a + "-" + b + " ");
                StdOut.println();
            }
            
        } 

        private void resize(int capacity) {
            Item[] copy = (Item[]) new Object[capacity];
            int i = 0, j = 0;
            while (i < tail) {
                if (queue[i] != null) copy[j++] = queue[i++];  //if the item exists copy it and then move both pointers
                else i++; //if the item is null move only the pointer to the queue
            }
            queue = copy;
            tail = j;
        }

        private Item[] copy() {
            Item[] copy = (Item[]) new Object[itemCount];
            int i = 0, j = 0;
            while (i < tail) {
                if (queue[i] != null) copy[j++] = queue[i++];  //if the item exists copy it and then move both pointers
                else i++; //if the item is null move only the pointer to the queue
            }
            return copy;
        }

        //Returns queue array length
        private int queueLength() { return queue.length; }

        private String queueToString() {
            String str = "[ ";
            String tPtr = "";
            for (int i = 0; i < queue.length; i++){
                if (i == tail) tPtr = ":, ";
                else tPtr = ", ";
                if (queue[i] != null) str +=  queue[i] + tPtr;
                else str += "_" + tPtr;
            }
            str += "]\nlength = " + queue.length + "  item count = " + itemCount;
            return str;
        }
}