package edu.umb.cs681.hw13;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AccessCounter {
    private static AccessCounter instance = null;
    private Map<Path, Integer> accessCount;
    private static Lock lock = new ReentrantLock();
    private ReadWriteLock rwLock;
    private AccessCounter() {
        accessCount = new HashMap<>();
        rwLock = new ReentrantReadWriteLock();
    }

    public static AccessCounter getInstance() {
        if (instance == null) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new AccessCounter();
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public void increment(Path path) {
        rwLock.writeLock().lock();
        System.out.println("Write Lock Acquired !");
        try {
            if (accessCount.containsKey(path)) {
                accessCount.put(path, accessCount.get(path) + 1);
            } else {
                accessCount.put(path, 1);
            }
        } finally {
            System.out.println("Write Lock Released !");
            rwLock.writeLock().unlock();
        }
    }

    public int getCount(Path path) {
        rwLock.readLock().lock();
        System.out.println("Read Lock Acquired !");
        try {
            if (accessCount.containsKey(path)) {
                return accessCount.get(path);
            } else {
                return 0;
            }
        } finally {
            System.out.println("Read Lock Released !");
            rwLock.readLock().unlock();
        }
    }
}

