
/*
Programmer Yuan Liang
Date 2018.10.5
Description: This project is the implementation of DLList
*/


public class LinkedListDeque<T> {
    private IntNode sentinel;
    private int size;

    /* define IntNode class*/
    private class IntNode {
        private IntNode prev;
        private IntNode next;
        private T item;

        /* define value constructor of */
        private IntNode(T x, IntNode p, IntNode n) {
            item = x;
            prev = p;
            next = n;
        }
    }

    /* define the constructor for LinkedListDeque*/
    public LinkedListDeque() {
        sentinel = new IntNode(null, null, null);
        size = 0;
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }


    /* define addfirst method. When adding the first node,
     * Consider the sentinel.next.next is sentinel*/
    public void addFirst(T x) {
        sentinel.next = new IntNode(x, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    /* add node at the end of list*/
    public void addLast(T x) {
        sentinel.prev.next = new IntNode(x, sentinel.prev, sentinel);
        sentinel.prev = sentinel.prev.next;
        size += 1;
    }

    /* judge whether list is an empty list */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /* return the size of list */
    public int size() {
        return size;
    }

    /* print all elements in the list separated with whitespace */
    public void printDeque() {
        LinkedListDeque copyLink = this;
        for (int i = 0; i < size; i++) {
            System.out.print(copyLink.sentinel.next.item);
            System.out.print(" ");
            copyLink.sentinel = copyLink.sentinel.next;
        }
    }

    /* remove the front element of list */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T removedNode = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return removedNode;
    }


    /* remove the last element in the list */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T removedNode = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size -= 1;
        return removedNode;
    }

    /* get the item at the given index */
    public T get(int index) {
        if (index > size - 1) {
            return null;
        }
        IntNode copy = sentinel.next;
        for (int i = 0; i < index; i++) {
            copy = copy.next;
        }
        return copy.item;
    }

    /* helper function for getRecursive method */
    private T helper(IntNode node, int size) {
        if (size == 0) {
            return node.item;
        } else {
            return helper(node.next, size - 1);
        }
    }

    public T getRecursive(int index) {
        if (index > size - 1) {
            return null;
        }
        return  helper(sentinel.next, size);
    }

}





///**  Project 1A: Linked List Deque.
// *
// *   ref = "https://sp18.datastructur.es/materials/proj/proj1a/proj1a"
// *
// *   @author Zhenye Na 05/18/2018
// *
// * */
//
//public class LinkedListDeque<PlaceholderType> {
//
//    /**  private helper class for node in Linked-List-Deque.
//     *
//     *   Circular Sentinel
//     *
//     * */
//    private class ListNode {
//        private PlaceholderType item;
//        private ListNode prev;
//        private ListNode next;
//
//        private ListNode(ListNode p, PlaceholderType i, ListNode n) {
//            prev = p;
//            item = i;
//            next = n;
//        }
//
//    }
//
//
//    /** Declare variables. */
//    private ListNode sentinel;
//    private int size;
//
//
//    /**  Creates an empty linked list deque. */
//    public LinkedListDeque() {
//        sentinel = new ListNode(null, null, null);
//        sentinel.next = sentinel;
//        sentinel.prev = sentinel;
//        size = 0;
//    }
//
//
//    /**  Adds an item of type <PlaceholderType> to the front of the deque.
//     *
//     *   Args:
//     *       item (PlaceholderType): new first item.
//     *
//     *   Returns:
//     *       Nothing to return.
//     * */
//    public void addFirst(PlaceholderType item) {
//        sentinel.next = new ListNode(sentinel, item, sentinel.next);
//        sentinel.next.next.prev = sentinel.next;
//        size += 1;
//    }
//
//
//    /**  Adds an item of type <PlaceholderType> to the back of the deque.
//     *
//     *   Args:
//     *       item (PlaceholderType): new first item.
//     *
//     *   Returns:
//     *       Nothing to return.
//     * */
//    public void addLast(PlaceholderType item) {
//        sentinel.prev.next = new ListNode(sentinel.prev, item, sentinel);
//        sentinel.prev = sentinel.prev.next;
//        size += 1;
//    }
//
//
//    /**  Returns true if deque is empty, false otherwise.*/
//    public boolean isEmpty() {
//        return (this.size == 0);
//    }
//
//
//    /**  Returns the number of items in the deque in constant time. */
//    public int size() {
//        return size;
//    }
//
//
//    /**  Prints the items in the deque from first to last, separated by a space. */
//    public void printDeque() {
//        ListNode node = sentinel;
//        while (node.next != sentinel) {
//            System.out.print(node.next.item + " ");
//            node = node.next;
//        }
//    }
//
//
//    /**  Removes and returns the item at the front of the deque.
//     *   If no such item exists, returns null.
//     *
//     *   Args:
//     *       None
//     *
//     *   Returns:
//     *       First item in Linked-List-Deque or null
//     * */
//    public PlaceholderType removeFirst() {
//
//        if (size == 0) {
//            // no such item exists, return null
//            return null;
//        } else {
//            // remove first item and return that item
//            PlaceholderType firstItem = sentinel.next.item;
//            sentinel.next.next.prev = sentinel;
//            sentinel.next = sentinel.next.next;
//            size -= 1;
//
//            return firstItem;
//        }
//
//    }
//
//
//    /**  Removes and returns the item at the back of the deque.
//     *   If no such item exists, returns null.
//     *
//     *   Args:
//     *       None
//     *
//     *   Returns:
//     *       Last item in Linked-List-Deque or null
//     * */
//    public PlaceholderType removeLast() {
//
//        if (size == 0) {
//            // no such item exists, return null
//            return null;
//        } else {
//            // remove last item and return that item
//            PlaceholderType lastItem = sentinel.prev.item;
//            sentinel.prev.prev.next = sentinel;
//            sentinel.prev = sentinel.prev.prev;
//            size -= 1;
//
//            return lastItem;
//        }
//
//    }
//
//
//    /**  Gets the item at the given index using <iteration>
//     *   where 0 is the front, 1 is the next item, and so forth.
//     *   If no such item exists, returns null.
//     *
//     *   <Must not alter the deque!>
//     *
//     *   Args:
//     *       i (int): index.
//     *
//     *   Returns:
//     *       index^{th} item in the deque.
//     * */
//    public PlaceholderType getIterative(int index) {
//        int length = size;
//        ListNode target = sentinel.next;
//
//        // no such item exists, returns null.
//        if (index > length - 1) {
//            return null;
//        } else {
//            for (int i = 0; i < index; i++) {
//                target = target.next;
//            }
//
//            return target.item;
//        }
//
//    }
//
//
//    /**  Gets the item at the given index using <recursion>
//     *   where 0 is the front, 1 is the next item, and so forth.
//     *   If no such item exists, returns null.
//     *
//     *   <Must not alter the deque!>
//     *
//     *   Args:
//     *       i (int): index.
//     *
//     *   Returns:
//     *       index^{th} item in the deque.
//     * */
//    public PlaceholderType getRecursive(int index) {
//        int length = size;
//
//        // no such item exists, returns null.
//        if (index > length - 1) {
//            return null;
//        } else {
//            return traverse(sentinel.next, index);
//        }
//
//    }
//
//
//    /**  getRecursive helper function.
//     *
//     *   Args:
//     *       n (ListNode): current pointer.
//     *       i (int): index.
//     *
//     *   Returns:
//     *       target item (PlaceholderType).
//     * */
//    public PlaceholderType traverse(ListNode n, int i) {
//
//        if (i == 0) {
//            return n.item;
//        } else {
//            return traverse(n.next, i - 1);
//        }
//    }
//
//
//    /**  Dummy main function for test.
//     *
//     * */
////    public static void main(String[] args) {
////        LinkedListDeque<Integer> Dllist = new LinkedListDeque<>();
////        Dllist.addFirst(666);
////        Dllist.addLast(6666);
////        Dllist.addLast(66666);
////        Dllist.printDeque();                        // expected (666 6666 66666)
////        System.out.println("Test getIterative #1");
////        System.out.println(Dllist.getIterative(0)); // expected 666
////        System.out.println(Dllist.getIterative(1)); // expected 6666
////        System.out.println(Dllist.getIterative(5)); // expected null
////        System.out.println("Test getIterative #1");
////        System.out.println(Dllist.getRecursive(0)); // expected 666
////        System.out.println(Dllist.getRecursive(1)); // expected 6666
////        System.out.println("Test done!");
////
////        Dllist.removeFirst();
////        Dllist.printDeque();                        // expected (6666 66666)
////        System.out.println("Test getIterative #2 removeFirst");
////        System.out.println(Dllist.getIterative(0)); // expected 6666
////        System.out.println(Dllist.getIterative(1)); // expected 66666
////        System.out.println("Test getRecursive #2 removeFirst");
////        System.out.println(Dllist.getRecursive(0)); // expected 6666
////        System.out.println(Dllist.getRecursive(1)); // expected 66666
////
////        Dllist.removeLast();
////        Dllist.printDeque();                        // expected 6666
////        System.out.println("Test getIterative #3 removeLast");
////        System.out.println(Dllist.getIterative(0)); // expected 6666
////        System.out.println(Dllist.getIterative(1)); // expected null
////        System.out.println("Test getRecursive #3 removeFirst");
////        System.out.println(Dllist.getRecursive(0)); // expected 6666
////        System.out.println(Dllist.getRecursive(1)); // expected null
////    }
//}
