package edu.umb.cs681.hw09;

import java.util.List;
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

            Position currentPosition = aircraft.getPosition();
            Position newPosition = currentPosition.change(newLat, newLong, newAlt);
            aircraft.setPosition(newLat, newLong, newAlt);

            System.out.println(Thread.currentThread().getName() + " set position to " + newPosition.coordinate());
            if (newPosition.higherAltThan(currentPosition)) {
                System.out.println(Thread.currentThread().getName() +" Aircraft is flying higher than before.");
            } else if (newPosition.lowerAltThan(currentPosition)) {
                System.out.println(Thread.currentThread().getName() + " Aircraft is flying lower than before.");
            }

            if (newPosition.northOf(currentPosition)) {
                System.out.println(Thread.currentThread().getName() + " Aircraft is flying further north.");
            } else if (newPosition.southOf(currentPosition)) {
                System.out.println(Thread.currentThread().getName() + " Aircraft is flying further south.");
            }
        }
    }
}
