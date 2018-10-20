/**
 * This is the BoundedQueue interface
 * @author YuanLiang
 */
package synthesizer;
import java.util.Iterator;
public interface BoundedQueue<T> extends Iterable<T> {

    public int capacity();
    public int fillCount();
    public void enqueue(T x);
    public T dequeue();
    public T peek();

    public Iterator<T> iterator();

    default boolean isEmpty() {
        return fillCount() == 0;
    }

    default boolean isFull() {
        return fillCount() == capacity();
    }

}