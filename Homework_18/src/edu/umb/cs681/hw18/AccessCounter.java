package edu.umb.cs681.hw18;

import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;

public class AccessCounter {
    private static AccessCounter instance = new AccessCounter();
    private ConcurrentHashMap<Path, Integer> accessCount;

    private AccessCounter() {
        accessCount = new ConcurrentHashMap<>();
    }

    public static AccessCounter getInstance() {
        return instance;
    }

    public void increment(Path path) {
        accessCount.compute(path, (p, c) -> (c == null) ? 1 : c + 1);
    }

    public int getCount(Path path) {
        return accessCount.getOrDefault(path, 0);
    }
}
