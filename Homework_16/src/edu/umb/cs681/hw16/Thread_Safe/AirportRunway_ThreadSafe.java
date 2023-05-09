package edu.umb.cs681.hw16.Thread_Safe;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AirportRunway_ThreadSafe {

    private boolean isFree;
    private final Lock lock;

    public AirportRunway_ThreadSafe() {
        isFree = true;
        lock = new ReentrantLock();
    }

    public void land() {
        lock.lock();
        try {
            if (isFree) {
                isFree = false;
                System.out.println(Thread.currentThread().getName() + " is landing on the runway.");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " has landed.");
                isFree = true;
            } else {
                System.out.println("Collision Alert ! "+Thread.currentThread().getName() + " cannot land as runway is occupied.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void takeOff() {
        lock.lock();
        try {
            if (isFree) {
                isFree = false;
                System.out.println(Thread.currentThread().getName() + " is taking off from the runway.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " has taken off.");
                isFree = true;
            } else {
                System.out.println("Collision Alert ! "+Thread.currentThread().getName() + " cannot take off as runway is occupied.");
            }
        }
        finally {
            lock.unlock();
            }
        }
    }
