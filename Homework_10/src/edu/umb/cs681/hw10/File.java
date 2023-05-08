package edu.umb.cs681.hw10;

import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;

public class File extends FSElement {


    public File(Directory parent, String name, int size, LocalDateTime creationTime) {
        super(parent, name, size, creationTime);
    }

    public boolean isDirectory() {
        lock.lock();
        try {
            return false;
        } finally {
            lock.unlock();
        }
    }

    public boolean isFile() {
        lock.lock();
        try {
            return true;
        } finally {
            lock.unlock();
        }
    }

    public boolean isLink() {
        lock.lock();
        try {
            return false;
        } finally {
            lock.unlock();
        }
    }
}
