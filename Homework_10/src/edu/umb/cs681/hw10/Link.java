package edu.umb.cs681.hw10;

import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;

public class Link extends FSElement {

    private FSElement target;

    public Link(Directory parent, String name, int size, LocalDateTime creationTime, FSElement target) {
        super(parent, name, size, creationTime);
        this.target = target;
    }

    public boolean isDirectory() {
            return false;
    }

    public boolean isFile() {
            return false;
    }

    public boolean isLink() {
            return true;
    }

    public FSElement getTarget() {
        lock.lock();
        try {
            return this.target;
        } finally {
            lock.unlock();
        }
    }

    public int getTargetSize() {
        lock.lock();
        try {
            return target.getSize();
        } finally {
            lock.unlock();
        }
    }

    public void setTarget(FSElement target) {
        lock.lock();
        try {
            this.target = target;
        } finally {
            lock.unlock();
        }
    }

    public boolean targetisDirectory() {
        lock.lock();
        try {
            return target.isDirectory();
        } finally {
            lock.unlock();
        }
    }

    public boolean targetisFile() {
        lock.lock();
        try {
            return target.isFile();
        } finally {
            lock.unlock();
        }
    }

    public boolean targetisLink() {
        lock.lock();
        try {
            return target.isLink();
        } finally {
            lock.unlock();
        }
    }
}
