import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class Deque<Item> { //implements Iterable<Item> {

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
        else tail = head;
        listLength--;
        return oldHead.val;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("The list is empty, there are no more items to remove");
        Node oldTail = tail;
        tail = tail.prev;
        if (listLength > 1) tail.next = null;
        else head = tail;
        listLength--;
        return oldTail.val;
    }

    // return an iterator over items in order from front to back
//->public Iterator<Item> iterator() { return; }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> s = new Deque<>();

        StdOut.println("Add first operations\n");
        
        StdOut.println("Created empty deque");
        StdOut.println(s);
        StdOut.println("list length: " + s.size());
        StdOut.println("Head: " + s.head);
        StdOut.println("Tail prev: " + s.tail);
        StdOut.println("Tail: " + s.tail);

        StdOut.println("\nAdd 0 from head");
        s.addFirst(0);
        StdOut.println(s);
        StdOut.println("list length: " + s.size());
        StdOut.println("Head: " + s.head.val);
        StdOut.println("Tail prev: " + s.tail.prev);
        StdOut.println("Tail: " + s.tail.val);
       
        StdOut.println("\nAdd 1 from head");
        s.addFirst(1);
        StdOut.println(s);
        StdOut.println("list length: " + s.size());
        StdOut.println("Head: " + s.head.val);
        StdOut.println("Tail prev: " + s.tail.prev.val);
        StdOut.println("Tail: " + s.tail.val);
       
        StdOut.println("\nAdd 2 from head");
        s.addFirst(2);
        StdOut.println(s);
        StdOut.println("list length: " + s.size());
        StdOut.println("Head: " + s.head.val);
        StdOut.println("Tail prev: " + s.tail.prev.val);
        StdOut.println("Tail: " + s.tail.val);

        StdOut.println("\nAdd last operations\n");

        Deque<Integer> s2 = new Deque<>();
        
        StdOut.println("Created empty deque");
        StdOut.println(s2);
        StdOut.println("list length: " + s2.size());
        StdOut.println("Head: " + s2.head);
        StdOut.println("Tail prev: " + s2.tail);
        StdOut.println("Tail: " + s2.tail);
        

        StdOut.println("\nAdd 0 from tail");
        s2.addLast(0);
        StdOut.println(s2);
        StdOut.println("list length: " + s2.size());
        StdOut.println("Head: " + s2.head.val);
        StdOut.println("Tail prev: " + s2.tail.prev);
        StdOut.println("Tail: " + s2.tail.val);
       
        StdOut.println("\nAdd 1 from tail");
        s2.addLast(1);
        StdOut.println(s2);
        StdOut.println("list length: " + s2.size());
        StdOut.println("Head: " + s2.head.val);
        StdOut.println("Tail prev: " + s2.tail.prev.val);
        StdOut.println("Tail: " + s2.tail.val);
       
        StdOut.println("\nAdd 2 from tail");
        s2.addLast(2);
        StdOut.println(s2);
        StdOut.println("list length: " + s2.size());
        StdOut.println("Head: " + s2.head.val);
        StdOut.println("Tail prev: " + s2.tail.prev.val);
        StdOut.println("Tail: " + s2.tail.val); 

        StdOut.println("\nRemove first operations\n");
        StdOut.println(s2);
 
        StdOut.println("\nRemoved value: " + s2.removeFirst());
        StdOut.println(s2);
        StdOut.println("List length: " + s2.size());
        StdOut.println("Head: " + s2.head.val);
        StdOut.println("Head prev: " + s2.head.prev);
        StdOut.println("Tail: " + s2.tail.val);
        StdOut.println("Tail prev: " + s2.tail.prev.val);
 
        StdOut.println("\nRemoved value: " + s2.removeFirst());
        StdOut.println(s2);
        StdOut.println("List length: " + s2.size());
        StdOut.println("Head: " + s2.head.val);
        StdOut.println("Head prev: " + s2.head.prev);
        StdOut.println("Tail: " + s2.tail.val);
        StdOut.println("Tail prev: " + s2.tail.prev);
 
        StdOut.println("\nRemoved value: " + s2.removeFirst());
        StdOut.println(s2);
        StdOut.println("List length: " + s2.size());
        StdOut.println("Head: " + s2.head);
        StdOut.println("Head prev: " + s2.head);
        StdOut.println("Tail: " + s2.tail);
        StdOut.println("Tail prev: " + s2.tail);
 
        //StdOut.println("\nRemoved value: " + s2.removeFirst());

        StdOut.println("\nRemove last operations\n");
        StdOut.println(s);
 
        StdOut.println("\nRemoved value: " + s.removeFirst());
        StdOut.println(s);
        StdOut.println("List length: " + s.size());
        StdOut.println("Head: " + s.head.val);
        StdOut.println("Head prev: " + s.head.prev);
        StdOut.println("Tail: " + s.tail.val);
        StdOut.println("Tail prev: " + s.tail.prev.val);
 
        StdOut.println("\nRemoved value: " + s.removeFirst());
        StdOut.println(s);
        StdOut.println("List length: " + s.size());
        StdOut.println("Head: " + s.head.val);
        StdOut.println("Head prev: " + s.head.prev);
        StdOut.println("Tail: " + s.tail.val);
        StdOut.println("Tail prev: " + s.tail.prev);
 
        StdOut.println("\nRemoved value: " + s.removeFirst());
        StdOut.println(s);
        StdOut.println("List length: " + s.size());
        StdOut.println("Head: " + s.head);
        StdOut.println("Head prev: " + s.head);
        StdOut.println("Tail: " + s.tail);
        StdOut.println("Tail prev: " + s.tail);
 
        //StdOut.println("\nRemoved value: " + s.removeFirst());

        StdOut.println("\nUnit test\n");
        Deque<Integer> u =  new Deque<>();
        int n = StdIn.readInt();
        for (int i = 0; i < n; i++) u.addFirst(i);
        StdOut.println(u);
        for (int i = 0; i < n; i++) StdOut.println(u.removeLast() + "       List: " + u);
        StdOut.println(u);
        for (int i = 0; i < n; i++) u.addLast(i);
        StdOut.println(u);
        for (int i = 0; i < n; i++) StdOut.println(u.removeFirst() + "       List: " + u);
        StdOut.println(u);




    }

    private class Node {
        private Item val;
        private Node next;
        private Node prev;
    }

    @Override
    public String toString() {
        String linkedList = "head - ";
        for (Node i = head; i != null; i = i.next) {
            linkedList += i.val;
            linkedList += " -> ";
        };
        return linkedList + "null";
    }
}