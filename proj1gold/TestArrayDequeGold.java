import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayDeque;

public class TestArrayDequeGold {
    private StudentArrayDeque<Integer> student = new StudentArrayDeque<>();
    private ArrayDequeSolution<Integer> solution = new ArrayDequeSolution<>();
    private int loopVal = 10;

    /* This is for task 1, which we have found that error appears in the removeLast method.*/
//    @Test
//    public void testDeque() {
//        for (int i = 0; i <= 10; i++) {
//            int first = StdRandom.uniform(10);
//            student.addFirst(first);
//            solution.addFirst(first);
//        }
//        //student.printDeque();
//        //solution.printDeque();
//        for (int i = 0; i <= 10; i++) {
//            assertEquals(student.removeFirst(), solution.removeFirst());
//        }
//        for (int i = 0; i <= 10; i++) {
//            int last = StdRandom.uniform(11, 20);
//            student.addLast(last);
//            solution.addLast(last);
//        }
//        //student.printDeque();
//        //solution.printDeque();
//        for (int i = 0; i <= 10; i++) {
//            assertEquals(student.removeLast(), solution.removeLast());
//        }
//    }
//    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            Integer first = StdRandom.uniform(10);
//            System.out.println(first);
//        }
//    }

    /* This is for task 2*/
    @Test
    public void RandomizedTesting() {
        for (int i = 0; i < loopVal; i++) {
            int item = StdRandom.uniform(10);
            student.addLast(item);
            solution.addLast(item);
        }

        String msg = helper(student);
        for (int i = 0; i < loopVal; i++) {
            Integer removed = student.removeLast();
            Integer removed1 = solution.removeLast();
            msg = helper1(msg);
            assertEquals(msg, removed1, removed);
        }
    }

    private String helper(StudentArrayDeque <Integer> array) {
        String msg = "";
        for (int i = 0; i < loopVal; i ++) {
            msg = msg + "addFirst(" + array.get(i).toString() + ")\n";
        }

        return msg;
    }

    private String helper1(String msg) {
        return msg + "removeLast()\n";
    }

}
