// LeetCode Linked list tutoria solution

// Implement the MyLinkedList class:

// MyLinkedList() Initializes the MyLinkedList object.
// int get(int index) Get the value of the indexth node in the linked list. If the index is invalid, return -1.
// void addAtHead(int val) Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
// void addAtTail(int val) Append a node of value val as the last element of the linked list.
// void addAtIndex(int index, int val) Add a node of value val before the indexth node in the linked list. If index equals the length of the linked list, the node will be appended to the end of the linked list. If index is greater than the length, the node will not be inserted.
// void deleteAtIndex(int index) Delete the indexth node in the linked list, if the index is valid.

//Example:
// Input
// ["MyLinkedList", "addAtHead", "addAtTail", "addAtIndex", "get", "deleteAtIndex", "get"]
// [[], [1], [3], [1, 2], [1], [1], [1]]
// Output
// [null, null, null, null, 2, null, 3]

// Explanation
// MyLinkedList myLinkedList = new MyLinkedList();
// myLinkedList.addAtHead(1);
// myLinkedList.addAtTail(3);
// myLinkedList.addAtIndex(1, 2);    // linked list becomes 1->2->3
// myLinkedList.get(1);              // return 2
// myLinkedList.deleteAtIndex(1);    // now the linked list is 1->3
// myLinkedList.get(1);              // return 3

// Constraints:
//  - 0 <= index, val <= 1000
//  - Please do not use the built-in LinkedList library.
//  - At most 2000 calls will be made to get, addAtHead, addAtTail, addAtIndex and deleteAtIndex.

public class MyLinkedList 
{
    Node head;
    int length;

    public MyLinkedList() {
        this.head = new Node();
        this.length = 0;
    }
    
    public int get(int index) {
        Node selectedNode = getNodeAtIndex(index);
        return (selectedNode != null) ? ((selectedNode.val == null) ? -1 : selectedNode.val) : -1;
    }
    
    public void addAtHead(int val) {
        Node newNode = new Node();
        newNode.val = val;        // give newNode its value
        if (this.head.val == null) { 
            this.head = newNode;
            this.length = 1;
        }else {
            newNode.next = this.head; // point the newNode to the old head
            this.head = newNode;      // asign the newNode as the new head
            this.length++;
        }
    }
    
    public void addAtTail(int val) {
        Node newTail = new Node();
        newTail.val = val;

        if (this.length == 0) { head = newTail; }
        else {
            Node i;
            for (i = head; i.next != null; i = i.next); //get tail
            i.next = newTail; //set new tail
        }
        this.length++;
    }
    
    public void addAtIndex(int index, int val) {
        if (index <= 0) { addAtHead(val); return;} 
        if (this.length < index) return;

        Node previous = getNodeAtIndex(index - 1);
        if (previous != null) {
            Node newNode = new Node();
            newNode.val = val;
            newNode.next = previous.next;
            previous.next = newNode;
            this.length++;
        }
        
    }
    
    public void deleteAtIndex(int index) {
        if (index >= this.length) return;
        Node previous = getNodeAtIndex(index - 1);
        if (previous == null) return; 
        Node selectedNode = previous.next;
        if (index == 0) { this.head = selectedNode; }
        else if (selectedNode != null) { previous.next = selectedNode.next; }
        this.length--;

    }

    public Node getNodeAtIndex(int index){
        index = (index <= 0) ? 0 : index;
        int counter = 0;
        for (Node i = head; i != null; i = i.next){
            if (counter == index) { return i; }
            counter++;
        }
        return null;
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

    public static void main(String[] args)
    {
        MyLinkedList list = new MyLinkedList();
        // System.out.println(list);
        
        // list.addAtHead(1);
        // System.out.println(list);

        // list.addAtTail(3);
        // System.out.println(list);

        // list.addAtIndex(1, 2);
        // System.out.println(list);

        // System.out.println(list.get(1));

        // list.deleteAtIndex(1);
        // System.out.println(list);

        // System.out.println(list.get(1));

        // System.out.println(list);
        // list.addAtHead(2);
        // System.out.println(list);
        // list.addAtIndex(0, 1);
        // System.out.println(list.get(1));
        // System.out.println(list);

        System.out.println(list);
        list.addAtTail(1);
        System.out.println(list);
        //list.addAtTail(3);
        //System.out.println(list);
        list.deleteAtIndex(0);
        System.out.println(list);
        
    }

    static class Node
    {
        Integer val;
        Node next;

        public Node() {
            this.val = null;
            this.next = null;
        }

        public Node(int val){
            this.val = val;
            this.next = null;
        }
    }
}
