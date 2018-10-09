/*
* Programmer: Yuan Liang
* Date: 2018.10.7
* content: Use array to implement linkedList
*/


public class ArrayDeque<T> {
    private int size;
    private T[] item;
    private int nextFirst;
    private int nextLast;
    private int FRACTOR = 2;

    private int minusOne(int index) {
        return index - 1;
    }

    private int plusOne(int index) {
        return index + 1;
    }

    private int smaller(int a, int b) {
        if (a < b) {
            return a;
        } else {
            return b;
        }
    }

    private int rebuild(T [] newArray) {
        int headLength =  plusOne(nextLast);
        int tailLength = item.length - nextFirst;
        System.arraycopy(item, 0, newArray, 0, headLength);
        System.arraycopy(item, nextFirst, newArray, headLength + newArray.length - size,
                tailLength);
        return tailLength;
    }

    private void resizeFirst(int newSize) {
        T [] a = (T[]) new Object[newSize];
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
        T [] a = (T[]) new Object[newSize];
        nextFirst = plusOne(nextFirst);
        if (nextFirst == 8) {
            System.arraycopy(item, 0, a, 0, size);
            item = a;
            nextFirst = item.length - 1;
        } else {
            int tailLength = rebuild(a);
            nextFirst = minusOne(newSize) - tailLength;
            item = a;
        }
    }

    private void shrinkArray(int shrinkSize) {
        int newSize = item.length - shrinkSize;
        T [] a = (T[]) new Object[newSize];
        if (item[minusOne(item.length)] == null) {
            int frontEmpty = plusOne(nextFirst);
            int endEmpty = item.length - nextLast;
            if (endEmpty >= frontEmpty) {
            System.arraycopy(item, 0, a, 0, nextLast);
            } else {
                System.arraycopy(item, 0, a, shrinkSize, item.length -plusOne(nextFirst));
            }
            item = a;
        } else {
            int headLength = nextLast;
            int tailLength = item.length - plusOne(nextFirst);
            System.arraycopy(item, 0, a, 0, headLength);
            System.arraycopy(item, item.length - tailLength,
                    a, newSize - tailLength, tailLength);
            item = a;
        }
    }


    public ArrayDeque() {
        size = 0;
        item = (T[]) new Object[8];
        nextFirst = 4;
        nextLast = 5;
    }

    public void addFirst(T x) {
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

    public void addLast(T x) {
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

//    public T removeFirst() {
//        if (size == 0) {
//            return null;
//        }
//        nextFirst = plusOne(nextFirst);
//        if (nextFirst == item.length) {
//            nextFirst = 0;
//        }
//        T val = item[nextFirst];
//        item[nextFirst] = null;
//        size = minusOne(size);
//        return val;
//    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        nextFirst = plusOne(nextFirst);
        if (nextFirst == item.length) {
            nextFirst = 0;
        }
        T val = item[nextFirst];
        item[nextFirst] = null;
        size = minusOne(size);
        if ((float)size / item.length < 0.25 && item.length > 16) {
            int shrinked = smaller(item.length - 4 * size, item.length - 8);
            shrinkArray(shrinked);
        }
        return val;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        nextLast = minusOne(nextLast);
        if (nextLast < 0) {
            nextLast = minusOne(item.length);
        }
        T val = item[nextLast];
        item[nextLast] = null;
        size = minusOne(size);
        if ((float)size / item.length < 0.25 && item.length > 16) {
            int shrinked = smaller(item.length - 4 * size, item.length - 8);
            shrinkArray(shrinked);
        }
        return val;
    }

    public T get(int index) {
        if (index > minusOne(size)) {
            return null;
        }
        int target = plusOne(nextFirst) + index;
        if (target >= item.length) {
            target -= item.length;
        }
        return item[target];
    }

    public int size() {
        return size;
    }

        public static void main(String[] args) {
        ArrayDeque <Integer> a = new ArrayDeque <Integer> ();
        System.out.println(a.isEmpty());
        a.addFirst(1);
        a.addLast(2);
        a.addLast(3);
        a.addLast(4);
        a.addLast(5);
        a.addLast(6);
        a.addLast(7);
        a.addLast(8);

//        System.out.println(a.get(3));
//        System.out.println(a.isEmpty());
//        System.out.println();

        a.addFirst(10);
        a.addFirst(15);
        a.addFirst(19);
        a.addFirst(20);
//        a.printDeque();
        a.addLast(5);
        a.addLast(6);
        a.addLast(7);
        a.addLast(5);
        a.addLast(6);
        System.out.println(a.removeFirst());
        System.out.println(a.removeFirst());
        System.out.println(a.removeLast());
        System.out.println(a.removeFirst());
        System.out.println(a.removeFirst());
        System.out.println(a.removeLast());
        System.out.println(a.removeFirst());
        System.out.println(a.removeFirst());
        System.out.println(a.removeLast());
        System.out.println(a.removeFirst());
        System.out.println(a.removeFirst());
        System.out.println(a.removeLast());
        System.out.println(a.removeFirst());
        System.out.println(a.removeFirst());
        System.out.println(a.removeLast());
        System.out.println(a.removeFirst());
        System.out.println(a.removeFirst());
        System.out.println(a.removeLast());



    }
}

