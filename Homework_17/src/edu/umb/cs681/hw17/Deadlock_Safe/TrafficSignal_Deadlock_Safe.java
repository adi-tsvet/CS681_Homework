package edu.umb.cs681.hw17.Deadlock_Safe;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.locks.ReentrantLock;

public class TrafficSignal_Deadlock_Safe {
    private final ReentrantLock northLock;
    private final ReentrantLock southLock;
    private final ReentrantLock eastLock;
    private final ReentrantLock westLock;


    public TrafficSignal_Deadlock_Safe() {
        northLock = new ReentrantLock();
        southLock = new ReentrantLock();
        eastLock = new ReentrantLock();
        westLock = new ReentrantLock();
    }

    public void northToWest() {
        acquireLocks(northLock, westLock);
        try {
            System.out.println(Thread.currentThread().getName() + " acquired North and West lock");
            System.out.println(Thread.currentThread().getName() + " is turning left from North towards West");
            System.out.println(Thread.currentThread().getName() + " released North and West lock");
        }
        finally {
            releaseLocks(northLock, westLock);
        }

    }

    public void southToNorth() {
        acquireLocks(southLock, northLock);
        try{
            System.out.println(Thread.currentThread().getName() + " acquired South and North lock");
            System.out.println(Thread.currentThread().getName() + " is going straight from South towards North");
            System.out.println(Thread.currentThread().getName() + " released South and North lock");
        }
        finally {
            releaseLocks(southLock, northLock);
        }

    }

    public void eastToWest() {
        acquireLocks(eastLock, westLock);
        try {
            System.out.println(Thread.currentThread().getName() + " acquired East and West lock");
            System.out.println(Thread.currentThread().getName() + " is going straight from East towards West");
            System.out.println(Thread.currentThread().getName() + " released East and West lock");
        }
        finally {
            releaseLocks(eastLock, westLock);
        }

    }

    public void westToNorth() {
        acquireLocks(westLock, northLock);
        try {
            System.out.println(Thread.currentThread().getName() + " acquired West and North lock");
            System.out.println(Thread.currentThread().getName() + " is turning right from West towards North");
            System.out.println(Thread.currentThread().getName() + " released West and North lock");
        }
        finally {
            releaseLocks(westLock, northLock);
        }

    }

    private void acquireLocks(ReentrantLock lock1, ReentrantLock lock2) {
//        An example of globally-fixed order
//        – First, acquire the lock of an account with a “smaller” ID
//        – Then, acquire the lock of an account with a “bigger” ID

        int lock1Id = lock1.hashCode();
        int lock2Id = lock2.hashCode();

        if( lock1Id < lock2Id ) {
            lock1.lock();
            lock2.lock();
        }
        else{
            lock2.lock();
            lock1.lock();
        }
    }

    private void releaseLocks(ReentrantLock lock1, ReentrantLock lock2) {
        int lock1Id = lock1.hashCode();
        int lock2Id = lock2.hashCode();

//        – First, release the lock of an account with a “bigger” ID
//        – Then, release the lock of an account with a “smaller” ID

        if( lock1Id > lock2Id ) {
            lock1.unlock();
            lock2.unlock();
        }
        else{
            lock2.unlock();
            lock1.unlock();
        }
    }

    public static void main(String[] args) {
        System.out.println("Running Traffic Signal Deadlock Safe");
        TrafficSignal_Deadlock_Safe trafficSignal = new TrafficSignal_Deadlock_Safe();

        // Thread 1: car approaching from the North turning left towards the West
        Thread car1 = new Thread(() -> {
            trafficSignal.northToWest();
        }, "Car 1");

        // Thread 2: car approaching from the South going straight towards the North
        Thread car2 = new Thread(() -> {
            trafficSignal.southToNorth();
        }, "Car 2");

        // Thread 3: car approaching from the East going straight towards the West
        Thread car3 = new Thread(() -> {
            trafficSignal.eastToWest();
        }, "Car 3");

        // Thread 4: car approaching from the West turning right towards the North
        Thread car4 = new Thread(() -> {
            trafficSignal.westToNorth();
        }, "Car 4");

        // start all the threads
        car1.start();
        car2.start();
        car3.start();
        car4.start();

        // wait for all the threads to finish
        try {
            car1.join();
            car2.join();
            car3.join();
            car4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
