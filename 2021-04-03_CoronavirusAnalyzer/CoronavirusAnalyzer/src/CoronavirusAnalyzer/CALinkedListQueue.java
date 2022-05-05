package CoronavirusAnalyzer;

/**
 * Implementing the interface CAQueue to create a linked list queue.
 *
 * @author Vincent Zhang
 * @since 2021-03-24
 */
public class CALinkedListQueue implements CAQueue
{
    private CAQueueNode front;
    private CAQueueNode rear;

    /**
     * Set default values.
     */
    public CALinkedListQueue()
    {
        front = null;
        rear = null;
    }

    /**
     * Checks if the linked list queue is empty.
     * 
     * @return void
     */
    public boolean isEmpty()
    {
        return front == null;
    }

    /**
     * Dequeue an item from the linked list queue.
     * 
     * @return The item that was dequeued.
     */
    public Object dequeue()
    {
        if(!isEmpty())
        {
            Object value = front.getValue();
            if(front.getNext() == null)
                rear = null;
            front = front.getNext();
            return value;
        }
        return null;
    }

    /**
     * Enqueue an item to the linked list queue.
     * 
     * @param item The item to be enqueued
     * @return Whether enqueue was successful
     */
    public boolean enqueue(Object item)
    {
        CAQueueNode newItem = new CAQueueNode(item);
        if(isEmpty())
            front = newItem;
        else
            rear.setNext(newItem);
        rear = newItem;
        
        return true;
    }
}