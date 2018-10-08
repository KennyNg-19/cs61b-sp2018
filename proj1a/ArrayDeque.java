/*
* Programmer: Yuan Liang
* Date: 2018.10.7
* content: Use array to implement linkedList
*/


public class ArrayDeque <Item> {
    private int size;
    private Item[] item;
    private int nextFirst;
    private int nextLast;
    private int FRACTOR = 4;

    private int minusOne(int index) {
        return index - 1;
    }

    private int plusOne(int index) {
        return index + 1;
    }

    private int rebuild(Item [] newArray) {
        int headLength =  plusOne(nextLast);
        int tailLength = item.length - nextFirst;
        System.arraycopy(item, 0, newArray, 0, headLength);
        System.arraycopy(item, nextFirst, newArray, headLength + newArray.length - size, tailLength);
        return tailLength;
    }

    private void resizeFirst(int newSize) {
        Item [] a = (Item[]) new Object[newSize];
        nextLast = minusOne(nextLast);
        if (nextLast < 0) {
            System.arraycopy(item, 0, a, 0, size);
            nextLast = item.length;
            item = a;
        } else {
            /* split original array into head part(start from 0 to nextLast) and tail part
            start from nextFirst to the end of array*/
            int tailLength = rebuild(a);
            nextFirst = newSize - tailLength;
            item = a;
            nextLast = plusOne(nextLast);
        }
    }

    private void resizeLast(int newSize) {
        Item [] a = (Item[]) new Object[newSize];
        nextFirst = plusOne(nextFirst);
        if (nextFirst == 8) {
            System.arraycopy(item, 0, a, 0, size);
            item = a;
            nextFirst = item.length - 1;
        }
        else {
            int tailLength = rebuild(a);
            nextFirst = minusOne(newSize) - tailLength;
            item = a;
        }
    }


    public ArrayDeque() {
        size = 0;
        item = (Item[]) new Object[8];
        nextFirst = 4;
        nextLast = 5;
    }

    public void addFirst(Item x) {
        item[nextFirst] = x;
        size = plusOne(size);
        if (size == item.length) {
            resizeFirst(size * FRACTOR);
            }
        if (minusOne(nextFirst) < 0) {
            nextFirst = minusOne(item.length);
        } else {
            nextFirst = minusOne(nextFirst);
        }

    }

    public void addLast(Item x) {
        item[nextLast] = x;
        size = plusOne(size);
        if (size == item.length) {
            resizeLast(size * FRACTOR);
        }
        if (plusOne(nextLast) > minusOne(item.length)) {
            nextLast = 0;
        } else {
            nextLast = plusOne(nextLast);
        }
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public void printDeque() {
        int index = plusOne(nextFirst);
        for (int i = 0; i < size; i++) {
            if (index == item.length) {
                index = 0;
            }
            System.out.print(item[index]);
            System.out.print(" ");
            index += 1;
        }
    }

    public Item removeFirst() {
        if (size == 0) {
            return null;
        }
        nextFirst = plusOne(nextFirst);
        if (nextFirst == item.length) {
            nextFirst = 0;
        }
        Item val = item[nextFirst];
        item[nextFirst] = null;
        size = minusOne(size);
        return val;
    }

    public Item removeLast() {
        if (size == 0) {
            return null;
        }
        nextLast = minusOne(nextLast);
        if (nextLast < 0) {
            nextLast = minusOne(item.length);
        }
        Item val = item[nextLast];
        item[nextLast] = null;
        size = minusOne(size);
        return val;
    }

    public Item get(int index) {
        if (index > minusOne(size)) {
            return null;
        }
        int target = plusOne(nextFirst) + index;
        if (target >= item.length) {
            target -= item.length;
        }
        return item[target];
    }

    public static void main(String[] args) {
        ArrayDeque <Integer> a = new ArrayDeque <Integer> ();
        System.out.println(a.isEmpty());
        a.addFirst(1);
        a.addFirst(2);
        a.addFirst(3);
        a.addLast(4);
        a.addLast(5);
        a.addLast(6);
        a.addLast(7);
        a.addLast(8);
        System.out.println(a.get(3));
        System.out.println(a.isEmpty());
        System.out.println();
        System.out.println(a.removeFirst());
        System.out.println(a.removeFirst());
        System.out.println(a.removeLast());
        a.addFirst(10);
        a.addFirst(15);
        a.addFirst(19);
        a.addFirst(20);
        a.printDeque();


    }
}
