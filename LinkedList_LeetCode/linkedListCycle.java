public class linkedListCycle {
    // Return true if there is a cycle in the linked list. Otherwise, return false.
    public boolean hasCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow)  return true;
        }

        return false;
    }
    //Given the head of a linked list, return the node where the cycle begins. If there is no cycle, return null.
    public ListNode detectCycle(ListNode head){
        ListNode fast = head;
        ListNode slow = head;
        boolean hasCycle = false;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow) {
                hasCycle = true;
                break;
            }
        } 

        if (!hasCycle) return null;
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
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