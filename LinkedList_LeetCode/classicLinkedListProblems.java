public class classicLinkedListProblems {
    
    //Remove the nth node from the end using two pointers technique
    public ListNode removeNthFromEnd2ptr(ListNode head, int n) {

        // Algorithm
        // set a node before the head
        // set one pointer on the dummy Head set another pointer on the actual head
        // move the pointer on the head n times
        // after the second pointer has moved n times, move both pointers until one of them reaches null
        // At this point the node that started from the dummyHead is one node behind the node to delete
        // delete the node

        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode main = dummyHead;
        ListNode ref = dummyHead.next;
        
        for (int i = 1; i <= n; i++) ref = ref.next;
        
        while (ref != null) {
            main = main.next;
            ref = ref.next;
        }// At this point main is one node behind the node we want to delete
        
        main.next = main.next.next;
        return dummyHead.next;

    }

    //Returns the head of the reversed list
    public ListNode reverseList(ListNode head) {
        
        // Algorithm
        // Start from the head of the list until the end
        //      Take the current node and set its next node to the previous node
        //      update the previous node with the value of the current node
        //      move to the next node and repeat (at the begining of each iteration we need to save the value of next to use it here)
        // previous will now be the las node in the list making it the first node in the reversed list
        ListNode i = head;
        ListNode previous = null;
        ListNode next;
        while (i != null) {
            next = i.next;
            i.next = previous;
            previous = i;
            i = next;
        }
        return previous;
    }

    //Returns the head of the reversed list
    public ListNode reverseListWeird (ListNode head) {

        // ALgorithm
        // starting from the head
        // save the next node in a temp variable and then delete it
        // save the current head 
        // set the temp variable  as the new head
        // set temp.next as the old head

        ListNode i = head;
        ListNode temp;
        ListNode oldHead;

        while (i != null && i.next != null) {
            oldHead = head;
            temp = i.next;
            i.next = i.next.next;
            head = temp;
            head.next = oldHead;
        }
        
        return head;
    } 

    //Given the head of a linked list and an integer val, remove all the nodes of the linked list that has Node.val == val, and return the new head.
    public static ListNode removeListElements (ListNode head, int val) {

        // Algorithm
        // Set 2 pointers ptrA & ptrB
        // ptrA set to one node before head, ptrB set to head
        // repeat until ptrB is null:
        //      if ptrB.val equals val:
        //              move ptrB to next node and delete ptrA next node
        //      else:
        //              move both ptrA and ptrB to their respective next node

        ListNode dummyHead = new ListNode(0, head);
        ListNode ptrA = dummyHead;
        ListNode ptrB = dummyHead.next;
        while (ptrB != null) {
            if ( ptrB.val == val ) {
                ptrB = ptrB.next;
                ptrA.next = ptrB;
            } else {
                ptrA = ptrB;
                ptrB = ptrB.next;
            }
        }
         return dummyHead.next;
    }

    //Given the head of a singly linked list, group all the nodes with odd indices together followed by the nodes with even indices, and return the reordered list.
    public ListNode oddEvenList(ListNode head) {

        // Algorithm
        // Set a node before the head (dummyHead)
        // set a node to mark a empty list where the even nodes will be stored
        // set two pointers for the main list: one starts on dummyHead (previous) the other on head (current)
        // set one pointer to mark the last node in the even nodes list
        // set a counter starting with 1
        // Ultil current reaches the end:
        //      if the counter is even:
        //              append the node to the even list
        //              reset the last even node pointer
        //              delete the node form the main list
        //      else:
        //              move the previous and current pointers to their next node
        //      increase the counter
        // Set the next property in the last even node pointer to null to delete any leftovers from the main list
        // the previous pointer is now on the last odd node in the list, set its next property to the whole even list
        // return the head of the list

        ListNode dummyHead = new ListNode(0, head);
        ListNode evenNodes = new ListNode(0, null);
        ListNode lastEven = evenNodes;
        ListNode previous = dummyHead;
        ListNode current = dummyHead.next;        
        int i = 1;
        while (current != null) {
            if (i % 2 == 0) { // current index is even
                lastEven.next = current;
                lastEven = lastEven.next;
                
                previous.next = current.next;
            }else { //current index is odd
                previous = current;
            }
            current = current.next;
            i++;
        }
        lastEven.next = null;
        previous.next = evenNodes.next;
        return dummyHead.next;
        
    }

    public boolean isPalindrome(ListNode head) {

        // Algorithm
        // Get the length of the list
        // Copy every value of the list to an array
        // Traverse the array form start to finish and to finish to beginning comparing each value, if they're different return false
        // If it manage to traverse the whole array it must be a palindrome

        ListNode current = head;
        int length = 0;
        for (ListNode i = head; i != null; i = i.next) length++;
        int[] valArr = new int[length];
        int j = 0;
        for (current = head; current != null; current = current.next) {
            valArr[j] = current.val;
            j++;
        }//at this point i have an array containing every value in the list
        for (int k = 0; k < length; k++) if (valArr[k] != valArr[length-k-1]) return false;
        return true;
    }
    
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {this.val = val;}
        ListNode(int val, ListNode next) {this.val = val; this.next = next;}
        
    }
}