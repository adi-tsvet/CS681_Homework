package edu.umb.cs681.hw14;

import java.time.Duration;

class EntranceHandler implements Runnable {
    private AdmissionMonitor monitor;
    private volatile boolean done = false;

    public EntranceHandler(AdmissionMonitor monitor) {
        this.monitor = monitor;
    }

    public void run() {
        while (!done) {
            monitor.enter();
        }
        if(done){
            System.out.println("Terminated Entrance Thread...");
        }
    }

    public void setDone() {
        done = true;
    }

}