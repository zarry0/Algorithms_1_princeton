import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class Deque<Item> implements Iterable<Item> {

    private Node head, tail;
    private int listLength;

    // construct an empty deque
    public Deque() {
        head = null;
        tail = head;
        listLength = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return head == null;
    }

    // return the number of items on the deque
    public int size() {
        return listLength;
    }

    // add the item to the front = head
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Can't add a null item");
        Node newItem = new Node();
        newItem.val = item;
        if (isEmpty()) {
            head = newItem;
            tail = head; 
        }else {
            newItem.next = head;
            head.prev = newItem;
            head = newItem;
        }
        listLength++;
    }

    // add the item to the back == tail
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Can't add a null item");
        Node newItem = new Node();
        newItem.val = item;
        if (isEmpty()) {
            tail = newItem;
            head = tail;
        }else {
            newItem.prev = tail;
            tail.next = newItem;
            tail = newItem;
        }
        listLength++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("The list is empty, there are no more items to remove");
        Node oldHead = head;
        head = head.next;
        if (listLength > 1) head.prev = null;
        else                tail = head;
        listLength--;
        return oldHead.val;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("The list is empty, there are no more items to remove");
        Node oldTail = tail;
        tail = tail.prev;
        if (listLength > 1) tail.next = null;
        else                head = tail;
        listLength--;
        return oldTail.val;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() { return new DequeIterator(); }

    private class DequeIterator implements Iterator<Item> 
    {
        private Node current = head;

        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException("Remove operation not supported");}
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No more items in the deque");
            Item currentVal = current.val;
            current         = current.next;
            return currentVal;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        StdOut.println("\nUnit tests\n");
        // StdOut.println("Enter the number of items to input");
        // int n = StdIn.readInt();
        int n = 20;

        Deque<Integer> deque =  new Deque<>();
        StdOut.println("Deque is empty: " + deque.isEmpty());
        StdOut.println("Deque length: " + deque.size());
        
        StdOut.println("\nAdd first/ remove first operations");
        for (int i = 0; i < n; i++) deque.addFirst(i);
        StdOut.println("Deque is empty: " + deque.isEmpty());
        StdOut.println("Deque length: " + deque.size());
        for (int i = 0; i < n; i++) StdOut.println(deque.removeFirst());
        StdOut.println("Deque is empty: " + deque.isEmpty());
        StdOut.println("Deque length: " + deque.size());
        
        StdOut.println("\nAdd last/ remove last operations");
        for (int i = 0; i < n; i++) deque.addLast(i);
        StdOut.println("Deque is empty: " + deque.isEmpty());
        StdOut.println("Deque length: " + deque.size());
        for (int i = 0; i < n; i++) StdOut.println(deque.removeLast());
        StdOut.println("Deque is empty: " + deque.isEmpty());
        StdOut.println("Deque length: " + deque.size());
        
        StdOut.println("\nAdd first/ remove last operations");
        for (int i = 0; i < n; i++) deque.addFirst(i);
        StdOut.println("Deque is empty: " + deque.isEmpty());
        StdOut.println("Deque length: " + deque.size());
        for (int i = 0; i < n; i++) StdOut.println(deque.removeLast());
        StdOut.println("Deque is empty: " + deque.isEmpty());
        StdOut.println("Deque length: " + deque.size());
        
        StdOut.println("\nAdd last/ remove first operations");
        for (int i = 0; i < n; i++) deque.addLast(i);
        StdOut.println("Deque is empty: " + deque.isEmpty());
        StdOut.println("Deque length: " + deque.size());
        for (int i = 0; i < n; i++) StdOut.println(deque.removeFirst());
        StdOut.println("Deque is empty: " + deque.isEmpty());
        StdOut.println("Deque length: " + deque.size());
        
        StdOut.println("\nAdd last & Add first / remove first & remove last operations");
        for (int i = 0; i < n; i++) {
            deque.addLast(i);
            deque.addFirst(i);
        }
        StdOut.println("Deque is empty: " + deque.isEmpty());
        StdOut.println("Deque length: " + deque.size());
        //StdOut.println(deque);
        for (int i = 0; i < n; i++) {
            StdOut.println(deque.removeLast());
            StdOut.println(deque.removeFirst());
        }
        StdOut.println("Deque is empty: " + deque.isEmpty());
        StdOut.println("Deque length: " + deque.size());

        StdOut.println("\nIterator\n");
        for (int i = 0; i < n; i++) deque.addLast(i);
        for (int i : deque) StdOut.println(i);

    }

    private class Node {
        private Item val;
        private Node next;
        private Node prev;
    }
    // For debugging
    // @Override
    // public String toString() {
    //     String linkedList = "head - ";
    //     for (Node i = head; i != null; i = i.next) {
    //         linkedList += i.val;
    //         linkedList += " -> ";
    //     };
    //     return linkedList + "null";
    // }
}