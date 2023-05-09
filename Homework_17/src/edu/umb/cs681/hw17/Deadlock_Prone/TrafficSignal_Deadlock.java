package edu.umb.cs681.hw17.Deadlock_Prone;

import java.util.concurrent.locks.ReentrantLock;


public class TrafficSignal_Deadlock {
    private ReentrantLock northLock;
    private ReentrantLock southLock;
    private ReentrantLock eastLock;
    private ReentrantLock westLock;

    public TrafficSignal_Deadlock() {
        northLock = new ReentrantLock();
        southLock = new ReentrantLock();
        eastLock = new ReentrantLock();
        westLock = new ReentrantLock();
    }

    public void northToWest() {
        northLock.lock();
        System.out.println(Thread.currentThread().getName() + " acquired North lock");
        westLock.lock();
        System.out.println(Thread.currentThread().getName() + " acquired West lock");
        System.out.println(Thread.currentThread().getName() + " is turning left from North towards West");
        northLock.unlock();
        westLock.unlock();
    }

    public void southToNorth() {
        southLock.lock();
        System.out.println(Thread.currentThread().getName() + " acquired South lock");
        northLock.lock();
        System.out.println(Thread.currentThread().getName() + " acquired North lock");
        System.out.println(Thread.currentThread().getName() + " is going straight from South towards North");
        southLock.unlock();
        northLock.unlock();
    }

    public void eastToWest() {
        eastLock.lock();
        System.out.println(Thread.currentThread().getName() + " acquired East lock");
        westLock.lock();
        System.out.println(Thread.currentThread().getName() + " acquired West lock");
        System.out.println(Thread.currentThread().getName() + " is going straight from East towards West");
        eastLock.unlock();
        westLock.unlock();
    }

    public void westToNorth() {
        westLock.lock();
        System.out.println(Thread.currentThread().getName() + " acquired West lock");
        northLock.lock();
        System.out.println(Thread.currentThread().getName() + " acquired North lock");
        System.out.println(Thread.currentThread().getName() + " is turning right from West towards North");
        westLock.unlock();
        northLock.unlock();
    }


    public static void main(String[] args) {
        System.out.println("Running Traffic Signal Deadlock Prone");
        TrafficSignal_Deadlock trafficSignal = new TrafficSignal_Deadlock();

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
            int counter = 0;
            while (car1.isAlive() || car2.isAlive() || car3.isAlive() || car4.isAlive()) {
                Thread.sleep(1000);
                counter++;
                if (counter > 10) {
                    System.out.println("Deadlock detected. Terminating...");
                    System.exit(0);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
