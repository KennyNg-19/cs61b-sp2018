package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        assertTrue(arb.isEmpty());
        assertFalse(arb.isFull());
        arb.enqueue(5);
        arb.enqueue(6);
        assertEquals(arb.fillCount, 2);
        int peekVal = arb.peek();
        assertEquals(peekVal, 5);
        int removed = arb.dequeue();
        int removed2 = arb.dequeue();
        assertEquals(removed, 5);
        assertEquals(removed2, 6);
        assertTrue(arb.isEmpty());

    }

    @Test
    public void testIterator() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        arb.enqueue(5);
        arb.enqueue(6);
        arb.enqueue(7);
        arb.enqueue(8);
        arb.enqueue(9);
        arb.enqueue(10);
        Integer a = 1;
        for (Integer i : arb) {
            assertEquals(a, i);
            a += 1;
        }
    }
    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
