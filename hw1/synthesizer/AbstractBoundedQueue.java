
package synthesizer;
/**
 * This shows the abstract class AbstractBoundedQueue.
 * @author Yuanliang
 */

/**This is the AbstractBoundedQueue abstract class. */
public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {

    protected int fillCount;
    protected int capacity;

    public int capacity() {
        return capacity;
    }

    public int fillCount() {
        return fillCount;
    }



}
