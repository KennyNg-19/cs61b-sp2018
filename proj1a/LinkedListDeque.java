/*
Programmer Yuan Liang
Date 2018.10.5
Description: This project is the implementation of DLList
*/


public class LinkedListDeque <T> {
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
        sentinel.prev.next = new IntNode(x, sentinel.prev.next, sentinel);
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


