package edu.umb.cs681.hw18;

import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AccessCounter {
    private static AccessCounter instance = null;
    private static Lock lock = new ReentrantLock();
    private ConcurrentHashMap<Path, Integer> accessCount;

    private AccessCounter() {
        accessCount = new ConcurrentHashMap<>();
    }

    public static AccessCounter getInstance() {
        lock.lock();
        try {
            if (instance == null) {
                instance = new AccessCounter();
            }
        } finally {
            lock.unlock();
        }
        return instance;
    }

    public void increment(Path path) {
        accessCount.compute(path, (p, c) -> (c == null) ? 1 : c + 1);
    }

    public int getCount(Path path) {
        return accessCount.getOrDefault(path, 0);
    }
}
