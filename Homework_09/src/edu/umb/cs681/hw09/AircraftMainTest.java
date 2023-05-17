package edu.umb.cs681.hw09;

public class AircraftMainTest {
    public static void main(String[] args) throws InterruptedException {
        Aircraft aircraft = new Aircraft(new Position(0, 0, 0));

        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            //As per homework correction,
            // Now, each Thread is associated with a unique AircraftRunnable instance,
            // ensuring that they don't share the same Runnable object.
            Runnable aircraftRunnable = new AircraftRunnable(aircraft);
            threads[i] = new Thread(aircraftRunnable);
            threads[i].setName("Thread " + i);
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
