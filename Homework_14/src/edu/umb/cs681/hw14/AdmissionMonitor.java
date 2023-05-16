package edu.umb.cs681.hw14;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class AdmissionMonitor {
    private int currentVisitors = 0;
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Condition condition = rwLock.writeLock().newCondition();

    public void enter() {
        rwLock.writeLock().lock();
        try {
            while (currentVisitors >= 10) {
                condition.await();
            }
            currentVisitors++;
            System.out.println("Visitor Entered !\n Count: "+ currentVisitors);
        }
        catch (InterruptedException exception){}
        finally {
            rwLock.writeLock().unlock();
        }
    }

    public void exit(){
        rwLock.writeLock().lock();
        try {
            if (currentVisitors > 0) {
                currentVisitors--;
                System.out.println("Visitor Exited!\nCount: " + currentVisitors);
            }
            condition.signalAll();
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public int countCurrentVisitors() {
        rwLock.readLock().lock();
        try {
            System.out.println("Current Visitor Count: "+ currentVisitors);
            return currentVisitors;
        } finally {
            rwLock.readLock().unlock();
        }
    }
}
