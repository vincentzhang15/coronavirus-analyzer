package CoronavirusAnalyzer;

/**
 * Interface for a custom queue.
 *
 * @author Vincent Zhang
 * @since 2021-03-24
 */
public interface CAQueue
{
    /**
     * Checks if the queue is empty.
     * 
     * @return Whether the queue is empty
     */
    public boolean isEmpty();

    /**
     * Dequeue an item from the queue.
     * 
     * @return The item that was dequeued from the queue
     */
    public Object dequeue();

    /**
     * Enqueue an item to queue.
     * 
     * @param item Item to be enqueued
     * @return Whether enqueue was successful
     */
    public boolean enqueue(Object item);
}