package edu.umb.cs681.hw16.Thread_Unsafe;

public class AirportRunway {

    private boolean isFree;

    public AirportRunway() {
        isFree = true;
    }

    public void land() {
        if (isFree) {
            isFree = false;
            System.out.println(Thread.currentThread().getName() + " is landing on the runway.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " has landed.");
            isFree = true;
        } else {
            System.out.println("Collision Alert ! "+Thread.currentThread().getName() + " cannot land as runway is occupied.");
        }
    }

    public void takeOff() {
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
}
