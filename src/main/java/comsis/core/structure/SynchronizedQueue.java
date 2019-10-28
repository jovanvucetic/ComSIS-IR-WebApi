package comsis.core.structure;

import java.util.LinkedList;

public class SynchronizedQueue<T> {
    private LinkedList<T> queue = new LinkedList<>();

    public synchronized void add(T t) {
        queue.add(t);
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public synchronized T removeFirst() {
        return queue.removeFirst();
    }
}
