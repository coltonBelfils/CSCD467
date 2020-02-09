
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class SharedQueue {
    private LinkedList<String> underLyingQueue = new LinkedList<>();
    private final int maxSize = 100;
    //private final Object l1 = new Object();
    //private final Object l2 = new Object();
    private int count = 0;
    private boolean complete = false;

    public synchronized void enqueue(String toQueue) {
        while (underLyingQueue.size() >= maxSize) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        underLyingQueue.addFirst(toQueue);
        notifyAll();
    }

    public synchronized String deQueue() {
        String item;
        while (underLyingQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        item = underLyingQueue.removeLast();
        notifyAll();
        return item;
    }

    public synchronized void addCount(int count) {
        this.count += count;
    }

    public synchronized int getCount() {
        return count;
    }

    public synchronized boolean isComplete() {
        return complete && underLyingQueue.isEmpty();
    }

    public synchronized void setAsComplete() {
        complete = true;
    }
}
