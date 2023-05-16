package edu.umb.cs681.hw14;

import java.time.Duration;

class EntranceHandler implements Runnable {
    private AdmissionMonitor monitor;
    private volatile boolean done = false;

    public void setDone() {
        done = true;
    }
    public EntranceHandler(AdmissionMonitor monitor) {
        this.monitor = monitor;
    }

    public void run() {
        while (!done) {
            monitor.enter();
        }
        if(done){
            System.out.println("Stopped Entrance Thread #" + Thread.currentThread().getId());
        }
        try {
            Thread.sleep(1000);
        }
        catch(InterruptedException e) {
            System.out.println("Thread #"+Thread.currentThread().getId() + " Interrupted");
        }

    }



}