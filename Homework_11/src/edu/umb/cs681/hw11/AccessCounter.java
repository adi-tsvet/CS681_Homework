package edu.umb.cs681.hw11;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AccessCounter {
    private static AccessCounter instance = null;
    private Map<Path, Integer> accessCount;
    private static Lock lock = new ReentrantLock();
    private Lock nonStaticLock;
    private AccessCounter() {
        accessCount = new HashMap<>();
        nonStaticLock = new ReentrantLock();
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
        nonStaticLock.lock();
        try {
            if (accessCount.containsKey(path)) {
                accessCount.put(path, accessCount.get(path) + 1);
            } else {
                accessCount.put(path, 1);
            }
        } finally {
            nonStaticLock.unlock();
        }
    }

    public int getCount(Path path) {
        nonStaticLock.lock();
        try {
            if (accessCount.containsKey(path)) {
                return accessCount.get(path);
            } else {
                return 0;
            }
        } finally {
            nonStaticLock.unlock();
        }
    }
}

