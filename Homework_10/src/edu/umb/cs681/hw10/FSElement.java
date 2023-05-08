package edu.umb.cs681.hw10;

import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;

public abstract class FSElement {


    private String name;
    private int size;
    private LocalDateTime creationTime;
    private Directory parent;
    protected ReentrantLock lock;

    public FSElement(Directory parent, String name, int size, LocalDateTime creationTime) {
        if (parent != null) {
            parent.appendChild(this);
        } else {
            this.parent = null;
        }
        this.lock = new ReentrantLock();
        this.setName(name);
        this.setSize(size);
        this.setCreationTime(creationTime);
    }

    public Directory getParent() {
        lock.lock();
        try {return this.parent;}
        finally {
            lock.unlock();
        }

    }

    public int getSize() {
        lock.lock();
        try {
            return this.size;
        }
        finally {
            lock.unlock();
        }
    }

    public String getName() {
        lock.lock();
        try {
            return this.name;
        }
        finally {
            lock.unlock();
        }
    }

    public LocalDateTime getCreationTime() {
        lock.lock();
        try {
            return creationTime;}
        finally {
                lock.unlock();
            }
    }

    public void setParent(Directory parent) {
        lock.lock();
        try {
            this.parent = parent;
        }
        finally {
            lock.unlock();
        }
    }

    public void setSize(int size) {
        lock.lock();
        try {
            if (isDirectory()) {
                this.size = 0;
            } else {
                this.size = size;
            }
        }
        finally {
            lock.unlock();
        }

    }

    public void setName(String name) {
        lock.lock();
        try {
            this.name = name;
        }
        finally {
            lock.unlock();
        }
    }

    public void setCreationTime(LocalDateTime creationTime) {
        lock.lock();
        try { this.creationTime = creationTime;
        }
        finally {
            lock.unlock();
        }

    }

    abstract public boolean isDirectory();
    abstract public boolean isFile();
    abstract public boolean isLink();
}


