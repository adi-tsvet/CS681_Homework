package edu.umb.cs681.hw16.Thread_Unsafe;

public class AirportMain {

    public static void main(String[] args) {
        System.out.println("Running AirportRunway Thread Unsafe File");
        AirportRunway runway = new AirportRunway();
        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                threads[i] = new Thread(() -> {
                    runway.land();
                }, "Airplane " + i + " (landing)");
            } else {
                threads[i] = new Thread(() -> {
                    runway.takeOff();
                }, "Airplane " + i + " (take off)");
            }
            threads[i].start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
