public class linkedListIntersection {
    //Given the heads of two singly linked-lists headA and headB, return the node at which the two lists intersect. If the two linked lists have no intersection at all, return null.
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int lenA = listLength(headA);
        int lenB = listLength(headB);
        int delta = Math.abs(lenA - lenB);
        ListNode startA;
        ListNode startB;
        if (lenA > lenB) {
            startA = headA;
            startB = headB;
            for (int i = 0; i < delta; i++) startA = startA.next;
        }else {
            startA = headA;
            startB = headB;
            for (int i = 0; i < delta; i++) startB = startB.next;
        }
        
        while (startA != startB){
            if (startA == null || startB == null) return null;
            startA = startA.next;
            startB = startB.next;
        }
        
        return startA;
            
    }
    //get the length of a given list
    public int listLength (ListNode head){
        int count = 0;
        for (ListNode i = head; i != null; i = i.next) count++;
        return count;
    }

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }
 
}