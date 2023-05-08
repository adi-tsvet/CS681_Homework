package edu.umb.cs681.hw09;

import java.util.Random;

public class AircraftRunnable implements Runnable {
    private final Aircraft aircraft;
    private final Random rand = new Random();

    public AircraftRunnable(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            double newLat = rand.nextDouble() * 180 - 90;
            double newLong = rand.nextDouble() * 360 - 180;
            double newAlt = rand.nextDouble() * 100000;
            aircraft.setPosition(newLat, newLong, newAlt);
            System.out.println(Thread.currentThread().getName() + " set position to " + aircraft.getPosition());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrupted");
                return;
            }
        }
    }
}
