package edu.umb.cs681.hw09;

public class AircraftMainTest {
    public static void main(String[] args) throws InterruptedException {
        Aircraft aircraft = new Aircraft(new Position(0, 0, 0));
        Runnable aircraftRunnable = new AircraftRunnable(aircraft);

        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(aircraftRunnable);
            threads[i].setName("Thread " + i);
            threads[i].start();
        }
        Thread.sleep(7000);

    }
}
