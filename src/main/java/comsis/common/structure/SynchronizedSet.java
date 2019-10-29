package comsis.common.structure;

import java.util.HashSet;

public class SynchronizedSet<T> {
    private HashSet<T> set = new HashSet<>();
    private int size = 0;

    public synchronized void add(T item) {
        set.add(item);
        size++;
    }

    public synchronized boolean contains(String url) {
        return set.contains(url);
    }

    public synchronized int getSize() {
        return size;
    }

    public HashSet<T> asSet() {
        return set;
    }
}
