import edu.princeton.cs.algs4.Queue;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        Queue<Queue<Item>> targetQueue = new Queue<>();
        for (Item i : items) {
            Queue<Item> q = new Queue<>();
            q.enqueue(i);
            targetQueue.enqueue(q);
        }
        return targetQueue;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> target = new Queue<>();
        while (!q1.isEmpty() || !q2.isEmpty()) {
            target.enqueue(getMin(q1, q2));
        }
        return target;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        if (items.size() == 0) {
            return items;
        } else if (items.size() == 1) {
            return items;
        } else if (items.size() == 2) {
            Queue<Queue<Item>> singleQ = makeSingleItemQueues(items);
            return mergeSortedQueues(singleQ.dequeue(), singleQ.dequeue());
        } else {
            Queue<Item> halfQ = new Queue<>();
            for (int i = 0; i < items.size() / 2; i++) {
                halfQ.enqueue(items.dequeue());
            }
            Queue<Item> leftQ = mergeSort(halfQ);
            Queue<Item> rightQ = mergeSort(items);
            return mergeSortedQueues(leftQ, rightQ);
        }
    }
    public static void main(String[] args) {
        Queue<Integer> test = new Queue<>();
        for (int i = 0; i < 20; i ++) {
            test.enqueue((int)(Math.random() * 50 + 1));
        }
        System.out.println(test.toString());
        Queue<Integer> merged = mergeSort(test);
        System.out.println(merged.toString());
    }
}
