package edu.umb.cs681.hw14;

import java.time.Duration;

class StatsHandler implements Runnable {
    private AdmissionMonitor monitor;
    private volatile boolean done = false;

    public StatsHandler(AdmissionMonitor monitor) {
        this.monitor = monitor;
    }

    public void run() {
        while (!done) {
            monitor.countCurrentVisitors();
        }
        if(done){
            System.out.println("Terminated Stats Thread...");
        }
    }

    public void setDone() {
        done = true;
    }
}