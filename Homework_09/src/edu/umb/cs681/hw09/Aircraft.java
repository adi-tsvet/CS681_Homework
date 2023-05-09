package edu.umb.cs681.hw09;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Aircraft {
    private final ReentrantLock lock = new ReentrantLock();
    private Position position; // Shared (non-final) variable

    public Aircraft(Position pos) {
        this.position = pos;
    }

    public void setPosition(double newLat, double newLong, double newAlt) {
        Position newPosition = position.change(newLat, newLong, newAlt);
        lock.lock();
        try {
            position = newPosition;
        } finally {
            lock.unlock();
        }
    }

    public Position getPosition() {
        lock.lock();
        try {
            return position;
        } finally {
            lock.unlock();
        }
    }
}
