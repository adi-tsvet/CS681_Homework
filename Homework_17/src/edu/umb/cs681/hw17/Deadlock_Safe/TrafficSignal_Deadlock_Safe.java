package edu.umb.cs681.hw17.Deadlock_Safe;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.locks.ReentrantLock;

public class TrafficSignal_Deadlock_Safe {
    private ReentrantLock northLock;
    private ReentrantLock southLock;
    private ReentrantLock eastLock;
    private ReentrantLock westLock;

    private ReentrantLock[] locks;

    public TrafficSignal_Deadlock_Safe() {
        northLock = new ReentrantLock();
        southLock = new ReentrantLock();
        eastLock = new ReentrantLock();
        westLock = new ReentrantLock();

        //Ordering Locks to avoid circular wait
        locks = new ReentrantLock[] {northLock, southLock, eastLock, westLock};
        Arrays.sort(locks, Comparator.comparingInt(lock -> System.identityHashCode(lock)));
    }

    public void northToWest() {
        acquireLocks(northLock, westLock);
        System.out.println(Thread.currentThread().getName() + " acquired North and West lock");
        System.out.println(Thread.currentThread().getName() + " is turning left from North towards West");
        System.out.println(Thread.currentThread().getName() + " released North and West lock");
        releaseLocks(northLock, westLock);
    }

    public void southToNorth() {
        acquireLocks(southLock, northLock);
        System.out.println(Thread.currentThread().getName() + " acquired South and North lock");
        System.out.println(Thread.currentThread().getName() + " is going straight from South towards North");
        System.out.println(Thread.currentThread().getName() + " released South and North lock");
        releaseLocks(southLock, northLock);
    }

    public void eastToWest() {
        acquireLocks(eastLock, westLock);
        System.out.println(Thread.currentThread().getName() + " acquired East and West lock");
        System.out.println(Thread.currentThread().getName() + " is going straight from East towards West");
        System.out.println(Thread.currentThread().getName() + " released East and West lock");
        releaseLocks(eastLock, westLock);
    }

    public void westToNorth() {
        acquireLocks(westLock, northLock);
        System.out.println(Thread.currentThread().getName() + " acquired West and North lock");
        System.out.println(Thread.currentThread().getName() + " is turning right from West towards North");
        System.out.println(Thread.currentThread().getName() + " released West and North lock");
        releaseLocks(westLock, northLock);
    }

    private void acquireLocks(ReentrantLock lock1, ReentrantLock lock2) {
        lock1.lock();
        lock2.lock();
    }

    private void releaseLocks(ReentrantLock lock1, ReentrantLock lock2) {
        lock1.unlock();
        lock2.unlock();
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
